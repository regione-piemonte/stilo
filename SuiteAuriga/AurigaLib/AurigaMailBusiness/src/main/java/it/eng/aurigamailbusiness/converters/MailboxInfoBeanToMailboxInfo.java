/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.MailboxInfoBean;
import it.eng.aurigamailbusiness.database.mail.Mailbox;
import it.eng.aurigamailbusiness.database.mail.MailboxInfo;
import it.eng.aurigamailbusiness.database.mail.MailboxInfoId;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxInfoBeanToMailboxInfo implements IBeanPopulate<MailboxInfoBean, MailboxInfo> {

	@Override
	public void populate(MailboxInfoBean src, MailboxInfo dest) throws Exception {
		if (src.getIdMailbox() != null) {
			Mailbox mailbox = new Mailbox();
			MailboxInfoId id = new MailboxInfoId();
			mailbox.setIdMailbox(src.getIdMailbox());
			id.setIdMailbox(src.getIdMailbox());
			dest.setId(id);
			dest.setMailbox(mailbox);
		}
	}

	@Override
	public void populateForUpdate(MailboxInfoBean src, MailboxInfo dest) throws Exception {
		if (src.hasPropertyBeenModified("idMailbox")) {
			if (src.getIdMailbox() != null) {
				Mailbox mailbox = new Mailbox();
				MailboxInfoId id = new MailboxInfoId();
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
