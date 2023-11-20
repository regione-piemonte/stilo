/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.MailboxConfigBean;
import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxConfigId;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxConfigBeanToMailboxConfig implements IBeanPopulate<MailboxConfigBean, MailboxConfig> {

	@Override
	public void populate(MailboxConfigBean src, MailboxConfig dest) throws Exception {
		if (src.getIdMailbox() != null) {
			Mailbox mailbox = new Mailbox();
			MailboxConfigId id = new MailboxConfigId();
			mailbox.setIdMailbox(src.getIdMailbox());
			id.setIdMailbox(src.getIdMailbox());
			dest.setId(id);
			dest.setMailbox(mailbox);
		}
	}

	@Override
	public void populateForUpdate(MailboxConfigBean src, MailboxConfig dest) throws Exception {
		if (src.hasPropertyBeenModified("idMailbox")) {
			if (src.getIdMailbox() != null) {
				Mailbox mailbox = new Mailbox();
				MailboxConfigId id = new MailboxConfigId();
				mailbox.setIdMailbox(src.getIdMailbox());
				id.setIdMailbox(src.getIdMailbox());
				dest.setId(id);
				dest.setMailbox(mailbox);
			} else {
				dest.setMailbox(null);
				dest.setId(null);
			}
		}

	}

}
