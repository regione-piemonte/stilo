/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.eng.entity.MailboxAccount;
import it.eng.job.exception.AurigaDAOException;
import it.eng.utils.EntityUtils;

public class MessaggiMailDAOImpl implements MessaggiMailDAO {

	private static final Logger log = Logger.getLogger(MessaggiMailDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAccount(String idAccount, Session session) throws AurigaDAOException {
		log.debug("Effettuo la query sulla tabella " + EntityUtils.getEntityName(MailboxAccount.class));

		if (StringUtils.isBlank(idAccount)) {
			throw new AurigaDAOException("getAccount: idAccount mancante");
		}

		String result = null;
		try {
			Criteria criteria = session.createCriteria(MailboxAccount.class).add(Restrictions.eq("idAccount", idAccount));

			List<MailboxAccount> listaAccount = criteria.list();
			if (listaAccount != null && listaAccount.size() > 0) {
				result = listaAccount.get(0).getAccount();
			} else {
				throw new Exception("Impossibile trovare un account per l'id: " + idAccount);
			}
		} catch (Exception e) {
			log.error("Errore in getAccount: ", e);
			throw new AurigaDAOException(e);
		}
		
		return result;
	}

}
