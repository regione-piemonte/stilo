/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Clob;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.eng.core.annotation.Service;
import it.eng.job.aggiungiMarca.entity.CodaFileDaMarcare;
import it.eng.job.aggiungiMarca.entity.FileDaMarcare;

@Service(name="DaoCodaFileDaMarcare")
public class DaoCodaFileDaMarcare  {

	private static final Logger logger = Logger.getLogger(DaoCodaFileDaMarcare.class.getName());
		
	public List<CodaFileDaMarcare> getRecordsDaMarcare(Boolean escludiRecordConErrore, int numMaxTentativi) throws Exception {
		logger.debug("Metodo getRecordsDaMarcare con parametri di ricerca tra quelli in errore pari a " + !escludiRecordConErrore + 
				" e numMaxTentativi " + numMaxTentativi);

		Session session = null;
		try {

			session = HibernateUtil.openSession("getRecords");
			Criteria criteria = session.createCriteria(CodaFileDaMarcare.class);
			if( escludiRecordConErrore!=null && escludiRecordConErrore==true){
				criteria.add(Restrictions.isNull("errMsg"));
			} else {
				//criteria.add(Restrictions.isNotNull("msgErrore"));
			}
			if( numMaxTentativi!=0 ){
				criteria.add(Restrictions.lt("numTry", numMaxTentativi ));
			}
			criteria.addOrder(Order.asc("tsFirma"));
			
			List<CodaFileDaMarcare> records =  criteria.list();
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
	public void updateErrore(CodaFileDaMarcare bean, String msgErrore) throws Exception {
		logger.debug("update");

		Session session = null;
		Transaction ltransaction = null;
		try {

			session = HibernateUtil.begin();
			ltransaction = session.beginTransaction();
			
			LobHelper lobHelper = session.getLobHelper();
			Clob clob = lobHelper.createClob(msgErrore);
			bean.setErrMsg(clob);
			
			bean.setTsLastTry( new Date() );
			logger.debug("----- " + bean.getNumTry());
			if( bean.getNumTry()==null || bean.getNumTry()==0 ){
				bean.setNumTry( 1 );
			} else {
				bean.setNumTry( bean.getNumTry() +1 );
			}
			
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
	public void delete(CodaFileDaMarcare bean) throws Exception {
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
