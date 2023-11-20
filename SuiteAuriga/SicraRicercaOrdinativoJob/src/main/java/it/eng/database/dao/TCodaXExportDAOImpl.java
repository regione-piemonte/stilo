/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.math.BigInteger;
import java.sql.Clob;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LobHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;


import it.eng.database.utility.HibernateUtil;
import it.eng.entity.TCodaXExport;
import it.eng.job.codaEXport.CodaEXportJob_ATM2BU;
import it.eng.job.codaEXport.util.EntityUtils;
import it.eng.mail.bean.MailAddress;
//import it.eng.mail.bean.SimpleMailSender;

public class TCodaXExportDAOImpl {

	private static final Logger log = Logger.getLogger(TCodaXExportDAOImpl.class);
	private static final String CLASS_NAME = TCodaXExportDAOImpl.class.getName();
	//SimpleMailSender   simpleMailSender = new SimpleMailSender();
	
	public List<TCodaXExport> getAllTCodaXExport(String JOB_CLASS_NAME,String esitoElab,String fruitore)  {
		log.debug("Effettuo la query sulla tabella " + EntityUtils.getEntityName(TCodaXExport.class));
		
		List<TCodaXExport> result = null;
		Session session = null;
		try {
			
			session = HibernateUtil.openSession(JOB_CLASS_NAME);
			Criteria criteria = session.createCriteria(TCodaXExport.class);
			Criterion esitoElabFalse = Restrictions.eq("esitoElab", esitoElab);
			Criterion esitoElabNull =  Restrictions.isNull("esitoElab");
	        LogicalExpression orExp = Restrictions.or(esitoElabFalse,esitoElabNull);
	        criteria.add(orExp);
			criteria.add(Restrictions.eq("fruitore", fruitore));
			criteria.addOrder(Order.asc("idOggetto"));

			result = criteria.list();
			
/*			 String hqlStrWfOnly = " FROM TCodaXExport ct WHERE ct.fruitore = :fruitore  "
						+ " AND (esitoElab is null OR esitoElab='KO' ) "
						+ " ORDER BY ct.idOggetto ";


				Query queryWf = session.createQuery(hqlStrWfOnly);

				queryWf.setString("fruitore", fruitore);
				result = queryWf.list();*/
			
		} catch (Exception e) {
			log.error("Errore in getAllTCodaXExport(): ", e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return result;
	}
	public TCodaXExport getTCodaXExport(String JOB_CLASS_NAME,TCodaXExport obj)  {
		log.debug("Effettuo la query sulla tabella " + EntityUtils.getEntityName(TCodaXExport.class));
		
		TCodaXExport result = null;
		Session session = null;
		try {
			
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			
			Criteria criteria = session.createCriteria(TCodaXExport.class)
					.add(Restrictions.eq("idOggetto", obj.getIdOggetto()));
			
			result = (TCodaXExport) criteria.uniqueResult();
		} catch (Exception e) {
			log.error("Errore in getAllTCodaXExport(): ", e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return result;
	}
	public TCodaXExport update(String JOB_CLASS_NAME,MailAddress indirizziMail,TCodaXExport obj) throws Exception {

		Session session = null;
		Transaction transaction = null;
		log.info(CLASS_NAME+" - update - INIZIO");
		try {
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			transaction = session.beginTransaction();

			session.update(obj);
			transaction.commit();

		} catch (Throwable e) {
			if (transaction != null)
			{	
				transaction.rollback();
			}	
			//simpleMailSender.mailErroreCaricamento(JOB_CLASS_NAME,
            //        indirizziMail, 
            //        obj, 
            //        e.getMessage());
			log.error("Errore "+CLASS_NAME+" - update: "+e.getMessage());
		} finally {
			
			HibernateUtil.closeSession(session);
		}
		log.info(CLASS_NAME+" - update - INIZIO");
		return obj;
	}
	public TCodaXExport updateError(String JOB_CLASS_NAME,MailAddress indirizziMail,TCodaXExport obj,String message) throws Exception {

		Session session = null;
		Transaction transaction = null;
		log.info(CLASS_NAME+" - update - INIZIO");
		try {
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			transaction = session.beginTransaction();
			LobHelper lobHelper = session.getLobHelper();
			Clob clob = lobHelper.createClob(message);
			obj.setErrMsg(clob);
			session.update(obj);
			transaction.commit();

		} catch (Throwable e) {
				if (transaction != null)
				{	
					transaction.rollback();
				}	
				//simpleMailSender.mailErroreCaricamento(JOB_CLASS_NAME,
	            //        indirizziMail, 
	            //        obj, 
	            //        e.getMessage());
			
			log.error("Errore "+CLASS_NAME+" - update: "+e.getMessage());
		} finally {
			
			HibernateUtil.closeSession(session);
		}
		log.info(CLASS_NAME+" - update - INIZIO");
		return obj;
	}
	public boolean delete(String JOB_CLASS_NAME,TCodaXExport obj) throws Exception {

		Session session = null;
		Transaction transaction = null;
		boolean res = false;
		log.info(CLASS_NAME+" - remove - INIZIO");
		try {
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			transaction = session.beginTransaction();

			session.delete(obj);
			transaction.commit();
			res=true;

		} catch (Throwable e) {
			if (transaction != null)
				transaction.rollback();
			log.error("Errore "+CLASS_NAME+" - remove: "+e.getMessage());
		} finally {
			
			HibernateUtil.closeSession(session);
		}
		log.info(CLASS_NAME+" - remove - INIZIO");
		return res;
	}
	public TCodaXExport save(String JOB_CLASS_NAME,MailAddress indirizziMail,TCodaXExport obj,String file) throws Exception {

		Session session = null;
		Transaction transaction = null;
		log.info(CLASS_NAME+" - save - INIZIO");
		try {
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			transaction = session.beginTransaction();
			LobHelper lobHelper = session.getLobHelper();
			Clob clob = lobHelper.createClob(file);
			obj.setOggDaEsportare(clob);
			session.save(obj);
			transaction.commit();

		} catch (Throwable e) {
			if (transaction != null)
			{	
				transaction.rollback();
			}	
			//simpleMailSender.mailErroreCaricamento(JOB_CLASS_NAME,
            //        indirizziMail, 
            //        obj, 
            //        e.getMessage());
			log.error("Errore "+CLASS_NAME+" - save: "+e.getMessage());
			
		} finally {
			HibernateUtil.closeSession(session);
		}
		log.info(CLASS_NAME+" - save - INIZIO");
		return obj;
	}
	public Long getNextValue(String JOB_CLASS_NAME) throws Exception {
		log.debug("Effettuo la query sulla sequence DMS_T_CODA_X_EXPORT");
		Long result = null;
		Session session = null;
		try {
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			Query query = session.createSQLQuery("select DMS_T_CODA_X_EXPORT.nextval as num from dual").addScalar("num",
					StandardBasicTypes.BIG_INTEGER);

			result = ((BigInteger) query.uniqueResult()).longValue();
		} catch (Exception e) {
			log.error("Errore in getSequence: ", e);
			throw e;
		} finally {
			HibernateUtil.closeSession(session);
		}
		return result;
	}
}
