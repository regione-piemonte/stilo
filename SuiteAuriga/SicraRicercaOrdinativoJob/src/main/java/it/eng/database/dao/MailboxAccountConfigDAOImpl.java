/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.eng.entity.MailboxAccountConfig;

public class MailboxAccountConfigDAOImpl implements MailboxAccountConfigDAO {
	
	private static final Logger logger = Logger.getLogger(MailboxAccountConfigDAOImpl.class);
	
	@Override
	public List<MailboxAccountConfig> getRecordsByIdAccount(String idAccount, Session session) throws Exception {
		logger.debug("Metodo getRecordsByIdAccount con parametro di ricerca id_account=" + idAccount);
		
		List<MailboxAccountConfig> records = null;
		try {
			// parametri di ricerca
			Criteria criteria = session.createCriteria(MailboxAccountConfig.class);
			criteria.add(Restrictions.eq("idAccount", idAccount));
			
			// acquisizione lista record
			records = criteria.list();
			
			logger.debug("select con " + records.size() + " risultati");
		} catch(Exception e){ 
			logger.error("", e);
			throw e;
		}
		
		return records;
	}
	
}
