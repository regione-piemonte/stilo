/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.hibernate.Session;

import it.eng.entity.MailboxAccountConfig;

public interface MailboxAccountConfigDAO {
	
	public List<MailboxAccountConfig> getRecordsByIdAccount(String idAccount, Session session) throws Exception;
	
}
