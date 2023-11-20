/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Session;
import it.eng.job.exception.AurigaDAOException;

public interface MessaggiMailDAO {
	
	public String getAccount(String idAccount, Session session) throws AurigaDAOException;

}
