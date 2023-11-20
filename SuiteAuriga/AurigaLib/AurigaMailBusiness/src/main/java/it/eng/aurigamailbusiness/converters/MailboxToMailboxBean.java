/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.MailboxBean;
import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxToMailboxBean implements IBeanPopulate<Mailbox, MailboxBean> {

	@Override
	public void populate(Mailbox src, MailboxBean dest) throws Exception {
		if (src.getMailboxAccount() != null) {
			dest.setIdAccount(src.getMailboxAccount().getIdAccount());
		}
	}

	@Override
	public void populateForUpdate(Mailbox src, MailboxBean dest) throws Exception {
		if (src.getMailboxAccount() != null) {
			dest.setIdAccount(src.getMailboxAccount().getIdAccount());
		}
	}

}
