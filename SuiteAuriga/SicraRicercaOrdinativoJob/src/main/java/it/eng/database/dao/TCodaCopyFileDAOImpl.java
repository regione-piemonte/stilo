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
import it.eng.entity.TCodaCopyFile;
import it.eng.mail.bean.MailAddress;

public class TCodaCopyFileDAOImpl {

	private static final Logger log = Logger.getLogger(TCodaCopyFileDAOImpl.class);
	private static final String CLASS_NAME = TCodaCopyFileDAOImpl.class.getName();
	
	
	public List<TCodaCopyFile> getAllTCodaCopyFile(String JOB_CLASS_NAME,String esitoElab)  {
		log.debug("Effettuo la query sulla tabella TCodaCopyFile");
		
		List<TCodaCopyFile> result = null;
		Session session = null;
		try {
			
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			
			Criteria criteria = session.createCriteria(TCodaCopyFile.class);
			Criterion esitoElabFalse = Restrictions.eq("esitoElab", esitoElab);
			Criterion esitoElabNull =  Restrictions.isNull("esitoElab");
	        LogicalExpression orExp = Restrictions.or(esitoElabFalse,esitoElabNull);
	        criteria.add(orExp);
			criteria.addOrder(Order.asc("idOggetto"));

			result = criteria.list();
		} catch (Exception e) {
			log.error("Errore in getAllTCodaCopyFile(): ", e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return result;
	}
	public TCodaCopyFile getTCodaCopyFile(String JOB_CLASS_NAME,TCodaCopyFile obj)  {
		log.debug("Effettuo la query sulla tabella TCodaCopyFile");
		
		TCodaCopyFile result = null;
		Session session = null;
		try {
			
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			
			Criteria criteria = session.createCriteria(TCodaCopyFile.class)
					.add(Restrictions.eq("idOggetto", obj.getIdOggetto()));
			
			result = (TCodaCopyFile) criteria.uniqueResult();
		} catch (Exception e) {
			log.error("Errore in getAllTCodaCopyFile(): ", e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return result;
	}
	public TCodaCopyFile update(String JOB_CLASS_NAME,TCodaCopyFile obj) throws Exception {

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
			log.error("Errore "+CLASS_NAME+" - update: "+e.getMessage());
		} finally {
			
			HibernateUtil.closeSession(session);
		}
		log.info(CLASS_NAME+" - update - INIZIO");
		return obj;
	}
	public TCodaCopyFile updateError(String JOB_CLASS_NAME,TCodaCopyFile obj,String message) throws Exception {

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
			
			log.error("Errore "+CLASS_NAME+" - update: "+e.getMessage());
		} finally {
			
			HibernateUtil.closeSession(session);
		}
		log.info(CLASS_NAME+" - update - INIZIO");
		return obj;
	}
	public boolean delete(String JOB_CLASS_NAME,TCodaCopyFile obj) throws Exception {

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
	public TCodaCopyFile save(String JOB_CLASS_NAME,MailAddress indirizziMail,TCodaCopyFile obj,String file) throws Exception {

		Session session = null;
		Transaction transaction = null;
		log.info(CLASS_NAME+" - save - INIZIO");
		try {
			session = HibernateUtil.begin(JOB_CLASS_NAME);
			transaction = session.beginTransaction();
			session.save(obj);
			transaction.commit();

		} catch (Throwable e) {
			if (transaction != null)
			{	
				transaction.rollback();
			}
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
