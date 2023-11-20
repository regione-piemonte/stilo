/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpddocudImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.DefaultConfigBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.restresource.bean.RequestA2ASalesforce;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.xml.XmlUtilitySerializer;

@Singleton
@Api
@Path("/a2a")
public class A2ARestService {

	@Context
	ServletContext context;

	private static final long DEFAULT_VALIDATE_HOURS = 24;
	private static final Logger logger = Logger.getLogger(StiloResource.class);
	
	//attualmente la password e' una variabile di classe, ma meglio implementarla come parametro di configurazione o parametro DB
	private static final String PASSWORD_TO_ACCESS_DEFAULT = "PaSSw0rd123";

	
	@POST
	@Path("/sendResponseA2ASalesforce")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response sendResponseA2ASalesforce(@Context HttpServletRequest request, RequestA2ASalesforce requestA2ASalesforce) throws Exception {
		// recupero la password di autenticazione dall header
		String password = request.getHeader("password");

		if(!checkPassword(password)) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("Accesso Negato").type(MediaType.TEXT_PLAIN).build();
		}
		
		/*
		 * 
		 * if(requestA2ASalesforce.getIdSFDC().isEmpty()) {
			return Response.status(Response.Status.NOT_ACCEPTABLE)
					.entity("IdSFDC deve essere valorizzato").type(MediaType.TEXT_PLAIN).build();
		}*/
		
		if(requestA2ASalesforce.getrObjectId().isEmpty()) {
			return Response.status(Response.Status.NOT_ACCEPTABLE)
					.entity("rObjectId deve essere valorizzato").type(MediaType.TEXT_PLAIN).build();
		}
		
		Session session = null;
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean();
			
			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(loginBean.getSchema());
			SubjectUtil.subject.set(subject);
			
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
							
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			
			SezioneCache sezioneCache = new SezioneCache();
			
			Variabile idSFDC = new Variabile();
			idSFDC.setNome("ID_SALESFORCE_Ud");
			idSFDC.setValoreSemplice(requestA2ASalesforce.getIdSFDC());
			sezioneCache.getVariabile().add(idSFDC);
			
			Variabile stato = new Variabile();
			stato.setNome("COD_ESITO_SALESFORCE_Ud");
			stato.setValoreSemplice(requestA2ASalesforce.getStato());
			sezioneCache.getVariabile().add(stato);
			
			Variabile descrizione = new Variabile();
			descrizione.setNome("ERR_MSG_SALESFORCE_Ud");
			descrizione.setValoreSemplice(requestA2ASalesforce.getDescrizione());
			sezioneCache.getVariabile().add(descrizione);
			
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
			lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCache);
			
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
			
			input.setIduddocin(new BigDecimal(requestA2ASalesforce.getrObjectId()));
			input.setFlgtipotargetin("U");
			
			final UpddocudImpl upddocudImpl = new UpddocudImpl();
			upddocudImpl.setBean(input);
			
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					upddocudImpl.execute(paramConnection);
				}
			});
			
			StoreResultBean<DmpkCoreUpddocudBean> result = new StoreResultBean<DmpkCoreUpddocudBean>();
			AnalyzeResult.analyze(input, result);
			result.setResultBean(input);
			
			session.flush();
			lTransaction.commit();
			
			if (result.isInError()) {
				logger.error(result.getErrorCode() + " " + result.getDefaultMessage());
				
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore salvataggio: " + "ErrorCode: " + result.getErrorCode() + "  -  ErrorMessage: " + result.getDefaultMessage())
						.build();
			}
			else {
				return Response.ok().entity("Record inserito").build();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore durante l'elaborazione: " + e.getMessage()).type(MediaType.TEXT_PLAIN)
						.build();			
		}
		finally {
			HibernateUtil.release(session);
		}
	}
	
	private boolean checkPassword(String password) {
//		SecurityWSBean securityWSBean = (SecurityWSBean) SpringAppContext.getContext().getBean("securityWSBean");
		String passwordToAcces = PASSWORD_TO_ACCESS_DEFAULT;

		/*Controllo se nelle config è specificata la password da usare, altrimenti utilizzio quella di default*/
//		if (securityWSBean != null && StringUtils.isNotBlank(securityWSBean.getPassword())) {
//			passwordToAcces = securityWSBean.getPassword();
//		}
		
		if (password != null && !"".equals(password) && password.equals(passwordToAcces)) {
			return true;
		}

		return false;
	
	}
	
	private AurigaLoginBean getAurigaLoginBean() {
		DefaultConfigBean defaultConfigBean = (DefaultConfigBean) SpringAppContext.getContext()
				.getBean("defaultConfigBean");
		
		AurigaLoginBean loginBean = new AurigaLoginBean();
		loginBean.setToken(defaultConfigBean.getCodIdConnectionToken());
		loginBean.setSchema(defaultConfigBean.getSchema());

		return loginBean;
	}
	
	private Locale getLocale(){
		return new Locale("it", "IT");
	}
	
}