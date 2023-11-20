/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.eng.core.annotation.Service;
import it.eng.job.aggiungiMarca.entity.FileDaMarcare;

@Service(name="DaoFileDaMarcare")
public class DaoFileDaMarcare  {

	private static final Logger logger = Logger.getLogger(DaoFileDaMarcare.class.getName());
		
	public List<FileDaMarcare> getRecords(Boolean escludiRecordConErrore) throws Exception {
		logger.debug("Metodo getRecords con parametri di ricerca tra quelli in errore pari a " + !escludiRecordConErrore);

		Session session = null;
		try {

			session = HibernateUtil.openSession("getRecords");
			Criteria criteria = session.createCriteria(FileDaMarcare.class);
			if( escludiRecordConErrore!=null && escludiRecordConErrore==true){
				criteria.add(Restrictions.isNull("msgErrore"));
			} else {
				//criteria.add(Restrictions.isNotNull("msgErrore"));
			}
			
			List<FileDaMarcare> records =  criteria.list();
			logger.info("Trovati " + records.size() + " risultati");
			return records;
		} catch(Exception e){ 
			logger.error("", e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}
	
//	public void save(DmtCodaSportelli bean) throws Exception {
//		logger.debug("save");
//
//		Session session = null;
//		Transaction ltransaction = null;
//		try {
//
//			session = HibernateUtil.begin();
//			ltransaction = session.beginTransaction();
//			session.save(bean);
//			session.flush();
//			ltransaction.commit();
//
//		} catch(Exception e){ 
//			logger.error("", e);
//			throw e;
//		} finally {
//			HibernateUtil.release(session);
//		}
//	}
//	
	public void update(FileDaMarcare bean, String msgErrore) throws Exception {
		logger.debug("update");

		Session session = null;
		Transaction ltransaction = null;
		try {

			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			
			bean.setMsgErrore(msgErrore);
			
			session.update(bean);
			session.flush();
			ltransaction.commit();

		} catch(Exception e){ 
			logger.error("", e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}
//	
	public void delete(FileDaMarcare bean) throws Exception {
		logger.debug("delete");

		Session session = null;
		Transaction ltransaction = null;
		try {

			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			session.delete(bean);
			session.flush();
			ltransaction.commit();

		} catch(Exception e){ 
			logger.error("", e);
			throw e;
		} finally {
			HibernateUtil.release(session);
		}
	}
	

}
