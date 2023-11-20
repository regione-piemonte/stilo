/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.Extractverdoc;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.DefaultConfigBean;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.ExtractVerDocOutBean;
//import it.eng.bean.SecurityWSBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.crypto.CryptoUtility;

@Singleton
@Api
@Path("/stilo")
public class StiloResource {

	@Context
	ServletContext context;

	private static final long DEFAULT_VALIDATE_HOURS = 24;
	private static final Logger logger = Logger.getLogger(StiloResource.class);
	
	//attualmente la password e' una variabile di classe, ma meglio implementarla come parametro di configurazione o parametro DB
	private static final String PASSWORD_TO_ACCESS_DEFAULT = "PaSSw0rd123";

	
	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/atti/download")
	public Response downloadAtti(@Context HttpServletRequest request) throws Exception {

		String idDoc = request.getParameter("uriFile");

		// recupero la password di autenticazione dall header
		String password = request.getHeader("password");

		if(!checkPassword(password)) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("Accesso Negato").type(MediaType.TEXT_PLAIN).build();
		}

		File fileExtract;
//		MimeTypeFirmaBean infoFile;
		ExtractVerDocOutBean versionBean;

		if (idDoc != null && !"".equals(idDoc)) {
			try {
				
				DefaultConfigBean defaultConfigBean = (DefaultConfigBean) SpringAppContext.getContext()
				.getBean("defaultConfigBean");
				
				AurigaLoginBean loginBean = new AurigaLoginBean();
				loginBean.setToken(defaultConfigBean.getCodIdConnectionToken());
				loginBean.setSchema(defaultConfigBean.getSchema());
				
				SubjectBean subject = new SubjectBean();
				subject.setIdDominio(loginBean.getSchema());
				SubjectUtil.subject.set(subject);
				
				versionBean = getVersionStore(loginBean, idDoc);
				
				fileExtract = DocumentStorage.extract(versionBean.getUriOut(), null);				
				
//				infoFile = new InfoFileUtility().getInfoFromFile(fileExtract.toURI().toString(), fileExtract.getName(),
//						false, null);
			} catch (Exception ex) {
				logger.error("ERRORE SERVIZIO DI SCARICO FILE: " + ex.getMessage(), ex);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Errore durante il recupero del File").type(MediaType.TEXT_PLAIN).build();
			}

			StreamingOutput stream = null;
			try {
				final InputStream in = new FileInputStream(fileExtract);
				stream = new StreamingOutput() {
					public void write(OutputStream out) throws IOException, WebApplicationException {
						try {
							int read = 0;
							byte[] bytes = new byte[1024];

							while ((read = in.read(bytes)) != -1) {
								out.write(bytes, 0, read);
							}
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							throw new WebApplicationException(e);
						}finally {
							if( in != null) {
								in.close();
							}
						}
					}
				};
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			return Response.ok(stream)
					.header("content-disposition", "attachment; filename = " + versionBean.getDisplayFilenameVerOut()).build();
		}

		return Response.status(Response.Status.BAD_REQUEST).entity("idDoc mancante").type(MediaType.TEXT_PLAIN)
				.build();
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
	
	private ExtractVerDocOutBean getVersionStore(AurigaLoginBean loginBean, String idDoc) throws Exception {

		DmpkCoreExtractverdocBean lDmpkCoreExtractverdocBean = new DmpkCoreExtractverdocBean();
		lDmpkCoreExtractverdocBean.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkCoreExtractverdocBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkCoreExtractverdocBean.setIddocin(new BigDecimal(idDoc));
//		lDmpkCoreExtractverdocBean.setNroprogrverio(StringUtils.isNotBlank(nroVers) ? new BigDecimal(nroVers) : null);

		// eseguo il servizio
		Extractverdoc lExtractverdoc = new Extractverdoc();

		StoreResultBean<DmpkCoreExtractverdocBean> lStoreResultBean2 = lExtractverdoc.execute(loginBean,lDmpkCoreExtractverdocBean);

		if (lStoreResultBean2.isInError()) {
			logger.error("ErrorStore: " + lStoreResultBean2.getDefaultMessage());
			throw new StoreException(lStoreResultBean2.getDefaultMessage());
		}

		// leggo output
		ExtractVerDocOutBean lExtractVerDocOutBean = new ExtractVerDocOutBean();
		lExtractVerDocOutBean.setNroProgrVerOut(lStoreResultBean2.getResultBean().getNroprogrverio());
		lExtractVerDocOutBean.setDisplayFilenameVerOut(lStoreResultBean2.getResultBean().getDisplayfilenameverout());
		lExtractVerDocOutBean.setUriOut(lStoreResultBean2.getResultBean().getUriverout());
		lExtractVerDocOutBean.setImprontaVerOut(lStoreResultBean2.getResultBean().getImprontaverout());
		lExtractVerDocOutBean.setAlgoritmoImprontaVerOut(lStoreResultBean2.getResultBean().getAlgoritmoimprontaverout());
		lExtractVerDocOutBean.setEncodingImprontaVerOut(lStoreResultBean2.getResultBean().getEncodingimprontaverout());
		lExtractVerDocOutBean.setDimensioneVerOut(lStoreResultBean2.getResultBean().getDimensioneverout());
		lExtractVerDocOutBean.setMimetypeVerOut(lStoreResultBean2.getResultBean().getMimetypeverout());
		lExtractVerDocOutBean.setFlgFirmataVerOut(lStoreResultBean2.getResultBean().getFlgfirmataverout());

		return lExtractVerDocOutBean;
	}
	

	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	private void validateToken(String token) throws Exception {
		if (token.length() == 0) {
			logger.error("Campo Token non presente.");
			throw new Exception("Campo Token non presente");
		} else if(StringUtils.isBlank(token)) {
			logger.error("Token di accesso non presente.");
			throw new Exception("Token di accesso non presente.");
		} else {
			String decoded = CryptoUtility.decrypt64FromString(token);
			Long timeGenLink = Long.parseLong(decoded);
			Long currentTime = System.currentTimeMillis();
			long hours = TimeUnit.MILLISECONDS.toHours(currentTime - timeGenLink);
			if (hours > retrieveValidateHours()) {
				logger.error("Il link risulta scaduto.");
				throw new Exception("Il link risulta scaduto.");
			}
		}
		logger.debug("Token valido");
	}
	
	private long retrieveValidateHours() {
		// TODO: da vedere come/dove recuperare. Valore che mi indica la validita del token espressa in ore
		return DEFAULT_VALIDATE_HOURS;
	}
	
}