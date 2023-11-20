/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.MailboxBean;
import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxBeanToMailbox implements IBeanPopulate<MailboxBean, Mailbox> {

	@Override
	public void populate(MailboxBean src, Mailbox dest) throws Exception {
		if (src.getIdAccount() != null) {
			MailboxAccount account = new MailboxAccount();
			account.setIdAccount(src.getIdAccount());
			dest.setMailboxAccount(account);
		}
	}

	@Override
	public void populateForUpdate(MailboxBean src, Mailbox dest) throws Exception {
		if (src.hasPropertyBeenModified("idAccount")) {
			if (src.getIdAccount() != null) {
				MailboxAccount account = new MailboxAccount();
				account.setIdAccount(src.getIdAccount());
				dest.setMailboxAccount(account);
			} else {
				dest.setMailboxAccount(null);
			}
		}

	}

}
