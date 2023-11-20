/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.MailboxConfigBean;
import it.eng.aurigamailbusiness.database.mail.MailboxConfig;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxConfigToMailboxConfigBean implements IBeanPopulate<MailboxConfig, MailboxConfigBean> {

	@Override
	public void populate(MailboxConfig src, MailboxConfigBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setIdMailbox(src.getId().getIdMailbox());
		}
	}

	@Override
	public void populateForUpdate(MailboxConfig src, MailboxConfigBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setIdMailbox(src.getId().getIdMailbox());
		}
	}

}
