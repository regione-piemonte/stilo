/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.eng.entity.TNotifAllEmail;
import it.eng.entity.TRichNotifEmail;

public class TNotifAllEmailDAOImpl implements TNotifAllEmailDAO {
	
	private static final Logger logger = Logger.getLogger(TNotifAllEmailDAOImpl.class);
	
	@Override
	public List<TNotifAllEmail> getAllegatiByIdRich(String idRichiesta, Session session) throws Exception {
		logger.debug("Metodo getAllegatiByIdRich con parametro di ricerca idRichiesta=" + idRichiesta);
		
		List<TNotifAllEmail> records = null;
		try {
			// parametri di ricerca
			Criteria criteria = session.createCriteria(TNotifAllEmail.class);
			criteria.add(Restrictions.eq("idRichNotifica", idRichiesta));
			
			// acquisizione lista record con stato=TO_SEND
			records = criteria.list();
			
			logger.debug("select con " + records.size() + " risultati");
		} catch(Exception e){ 
			logger.error("", e);
			throw e;
		}
		
		return records;
	}
	
	@Override
	public void update(TNotifAllEmail bean, Session session) throws Exception {
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
	public void delete(TNotifAllEmail bean, Session session) throws Exception {
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
