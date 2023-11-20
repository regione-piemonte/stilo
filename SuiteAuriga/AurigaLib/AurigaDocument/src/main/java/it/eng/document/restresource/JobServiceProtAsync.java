/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.entity.TRichiestaProtocollazione;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.DefaultConfigBean;
import it.eng.document.restresource.AurigaJobService;
import it.eng.spring.utility.SpringAppContext;

@Path("/job/protAsync")
public class JobServiceProtAsync extends AurigaJobService {
	
	@Context
	ServletContext context;
	
	@Context
	HttpServletRequest request;
	
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/marcaLetturaErrore")
	public Response marcaLetturaErrore(@Context HttpServletRequest request) throws Exception {

		String idJobParam = request.getParameter("idJob");
		
		logger.info("MARCA JOB SERVICE - START");
		
		// recupero la password di autenticazione dall header
//		String password = request.getHeader("password");
//		if(!checkPassword(password)) {
//			return Response.status(Response.Status.UNAUTHORIZED)
//					.entity("Accesso Negato").type(MediaType.TEXT_PLAIN).build();
//		}
		
		BigDecimal idJob;
		if(StringUtils.isNotBlank(idJobParam)) {
			idJob = new BigDecimal(idJobParam);
			logger.info("idJob: " + idJobParam);
		}else {
			logger.error("idJob non specificato");
			logger.info("MARCA JOB SERVICE - END");
			return Response.status(Response.Status.BAD_REQUEST).entity("idJob non specificato").type(MediaType.TEXT_PLAIN).build();
		}
		
		try {
			DefaultConfigBean defaultConfigBean = (DefaultConfigBean) SpringAppContext.getContext()
					.getBean("defaultConfigBean");

			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setToken(defaultConfigBean.getCodIdConnectionToken());
			loginBean.setSchema(defaultConfigBean.getSchema());

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(loginBean.getSchema());
			SubjectUtil.subject.set(subject);

			marcaLetturaErrore(idJob);
			
		} catch (Exception e) {
			String errorMessage = "Errore durante l'aggiornamento di stato per il job: " + idJobParam + " - errore: " + e.getMessage();
			logger.error(errorMessage);
			logger.info("MARCA JOB SERVICE - END");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).type(MediaType.TEXT_PLAIN).build();
		}
		
		logger.info("MARCA JOB SERVICE - END");
		return Response.status(Response.Status.OK).entity("Il job " + idJobParam + "e' stato marcato e non verra piu reinserito nel report di errore").type(MediaType.TEXT_PLAIN).build();
		
	}
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/riprocessaJob")
	public Response riprocessaJob(@Context HttpServletRequest request) throws Exception {

		String idJobParam = request.getParameter("idJob");
		
		logger.info("RIPROCESSAJOB SERVICE - START");
		
		// recupero la password di autenticazione dall header
//		String password = request.getHeader("password");
//		if(!checkPassword(password)) {
//			return Response.status(Response.Status.UNAUTHORIZED)
//					.entity("Accesso Negato").type(MediaType.TEXT_PLAIN).build();
//		}
		
		BigDecimal idJob;
		if(StringUtils.isNotBlank(idJobParam)) {
			idJob = new BigDecimal(idJobParam);
			logger.info("idJob: " + idJobParam);
		}else {
			logger.error("idJob non specificato");
			logger.info("RIATTIVAJOB RIPROCESSAJOB - END");
			return Response.status(Response.Status.BAD_REQUEST).entity("idJob non specificato").type(MediaType.TEXT_PLAIN).build();
		}
		
		try {
			DefaultConfigBean defaultConfigBean = (DefaultConfigBean) SpringAppContext.getContext()
					.getBean("defaultConfigBean");

			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setToken(defaultConfigBean.getCodIdConnectionToken());
			loginBean.setSchema(defaultConfigBean.getSchema());

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(loginBean.getSchema());
			SubjectUtil.subject.set(subject);

			aggiornaStatoJob(idJob, JOBSTATUS_IN_INIZIALE, "");
			aggiornaStatoRichiesta(idJob, "inserita");
			
			
		} catch (Exception e) {
			String errorMessage = "Errore durante l'aggiornamento di stato per il job: " + idJobParam + " - errore: " + e.getMessage();
			logger.error(errorMessage);
			logger.info("RIPROCESSAJOB SERVICE - END");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).type(MediaType.TEXT_PLAIN).build();
		}
		
		logger.info("RIPROCESSAJOB SERVICE - END");
		return Response.status(Response.Status.OK).entity("Il job " + idJobParam + " e' stato messo nello stato I correttamente, verra riprocessato").type(MediaType.TEXT_PLAIN).build();
		
	}
	
	

	private void aggiornaStatoRichiesta(BigDecimal idJob, String stato) throws Exception {
		TRichiestaProtocollazione bean =  searchByIdJob(idJob);
		bean.setStato(stato);
		
		update(bean);
		
	}

	private void marcaLetturaErrore(BigDecimal idJob) throws Exception {
		TRichiestaProtocollazione bean =  searchByIdJob(idJob);
		bean.setNotificato(true);
		
		update(bean);
		
	}
	
	public TRichiestaProtocollazione searchByIdJob (BigDecimal idJob)
			throws Exception {

		Session session = null;
		TRichiestaProtocollazione result = new TRichiestaProtocollazione();
		try {
			session = HibernateUtil.begin();
			final Criteria criteria = session.createCriteria(TRichiestaProtocollazione.class);
			criteria.add(Restrictions.eq("idJob", idJob));

			List<TRichiestaProtocollazione> listResults = criteria.list();
			for (TRichiestaProtocollazione obj : listResults) {
				result = obj;
			}

			return result;

		} catch (Exception e) {
			logger.error("Errore " + e.getMessage(), e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}

	}
	
	public TRichiestaProtocollazione update(TRichiestaProtocollazione bean) throws Exception {
		Session session = null;
		Transaction ltransaction = null;
		try {			
			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			if(bean != null) {					
				session.update(bean);				
			}							
			session.flush();
			ltransaction.commit();
			return bean;
		} catch(Exception e){
			throw e;
		}finally{
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
	
}