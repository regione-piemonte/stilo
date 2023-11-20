/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import it.eng.entity.TNotifAllEmailInv;

public class TNotifAllEmailInvDAOImpl implements TNotifAllEmailInvDAO {
	
	private static final Logger logger = Logger.getLogger(TNotifAllEmailInvDAOImpl.class);
	
	@Override
	public int getAllegatiEmailByIdRich(String idRic, Session session) throws Exception {
		logger.debug("Metodo getAllegatiEmailByIdRich con parametro di ricerca idRichNotifica=" + idRic);
		
		int result = 0;
		try {
			// parametri di ricerca
			Criteria criteria = session.createCriteria(TNotifAllEmailInv.class);
			criteria.add(Restrictions.eq("idRichNotifica", idRic));
			
			// acquisizione lista record con idRichiesta
			List<TNotifAllEmailInv> records =  criteria.list();
			
			if (records != null) {
				result = records.size();
			}
			
			logger.debug("select con " + result + " risultati");
		} catch(Exception e){ 
			logger.error("", e);
			throw e;
		}
		
		return result;
	}
	
	@Override
	public void save(TNotifAllEmailInv bean, Session session) throws Exception {
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
	
}
