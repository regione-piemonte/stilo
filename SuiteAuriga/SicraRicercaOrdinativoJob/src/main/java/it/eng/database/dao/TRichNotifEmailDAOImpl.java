/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.eng.database.utility.HibernateUtil;
import it.eng.entity.TRichNotifEmail;

public class TRichNotifEmailDAOImpl implements TRichNotifEmailDAO {
	
	private static final Logger logger = Logger.getLogger(TRichNotifEmailDAOImpl.class);
	
	@Override
	public List<TRichNotifEmail> getRecordsByStato(String stato, int tryNumber, Session session) throws Exception {
		logger.debug("Metodo getRecordsByStato con parametro di ricerca stato=" + stato + " e tryNumber=" + tryNumber);
		
		List<TRichNotifEmail> records = null;
		try {
			String sql = " FROM TRichNotifEmail rne WHERE rne.stato = '" + stato + "' "
					   + " and rne.tryNum < " + tryNumber;
			logger.info("sql: " + sql);
			
			Query query = session.createQuery(sql);
			
			// acquisizione lista record con stato=TO_SEND
			records = query.list();
			
			// parametri di ricerca
			//Criteria criteria = session.createCriteria(TRichNotifEmail.class);
			//criteria.add(Restrictions.eq("stato", stato));
			//criteria.add(Restrictions.sizeLe("tryNum", tryNumber));
			
			// acquisizione lista record con stato=TO_SEND
			//records = criteria.list();
			
			logger.debug("select con " + records.size() + " risultati");
		} catch(Exception e){ 
			logger.error("", e);
			throw e;
		}
		
		return records;
	}
	
	@Override
	public void save(TRichNotifEmail bean, Session session) throws Exception {
		logger.debug("save");
		
		Transaction ltransaction = null;
		try {
			ltransaction = session.beginTransaction();
			session.save(bean);
			session.flush();
			ltransaction.commit();
			
			logger.debug("save eseguito");
		} catch(Exception e){ 
			logger.error("", e);
			
			if (ltransaction != null) {	
				ltransaction.rollback();
			}
			
			throw e;
		}
	}
	
	@Override
	public void update(TRichNotifEmail bean, Session session) throws Exception {
		logger.debug("update");
		
		Transaction ltransaction = null;
		try {
			ltransaction = session.beginTransaction();
			session.update(bean);
			session.flush();
			ltransaction.commit();
			
			logger.debug("update eseguito");
		} catch(Exception e){ 
			logger.error("", e);
			
			if (ltransaction != null) {	
				ltransaction.rollback();
			}
			
			throw e;
		}
	}
	
	@Override
	public void delete(TRichNotifEmail bean, Session session) throws Exception {
		logger.debug("delete");
		
		Transaction ltransaction = null;
		try {
			ltransaction = session.beginTransaction();
			session.delete(bean);
			session.flush();
			ltransaction.commit();
			
			logger.debug("delete eseguito");
		} catch(Exception e){ 
			logger.error("", e);
			
			if (ltransaction != null) {	
				ltransaction.rollback();
			}
			
			throw e;
		}
	}
	
}
