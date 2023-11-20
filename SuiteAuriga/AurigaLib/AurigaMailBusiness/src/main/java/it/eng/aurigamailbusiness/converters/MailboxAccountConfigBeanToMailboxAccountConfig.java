/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.MailboxAccountConfigBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfig;
import it.eng.aurigamailbusiness.database.mail.MailboxAccountConfigId;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxAccountConfigBeanToMailboxAccountConfig implements IBeanPopulate<MailboxAccountConfigBean, MailboxAccountConfig> {

	@Override
	public void populate(MailboxAccountConfigBean src, MailboxAccountConfig dest) throws Exception {
		if (src.getIdAccount() != null) {
			MailboxAccount account = new MailboxAccount();
			MailboxAccountConfigId id = new MailboxAccountConfigId();
			account.setIdAccount(src.getIdAccount());
			id.setIdAccount(src.getIdAccount());
			dest.setMailboxAccount(account);
			dest.setId(id);

		} else {
			dest.setMailboxAccount(null);
			dest.setId(null);
		}
		if (src.getConfigKey() != null) {
			MailboxAccountConfigId id = new MailboxAccountConfigId();
			id.setConfigKey(src.getConfigKey());
			dest.setId(id);
		} else {
			dest.setId(null);
		}
	}

	@Override
	public void populateForUpdate(MailboxAccountConfigBean src, MailboxAccountConfig dest) throws Exception {
		if (src.hasPropertyBeenModified("idAccount")) {
			if (src.getIdAccount() != null) {
				MailboxAccount account = new MailboxAccount();
				MailboxAccountConfigId id = new MailboxAccountConfigId();
				account.setIdAccount(src.getIdAccount());
				id.setIdAccount(src.getIdAccount());
				dest.setMailboxAccount(account);
				dest.setId(id);

			} else {
				dest.setMailboxAccount(null);
				dest.setId(null);
			}
		}
		if (src.hasPropertyBeenModified("configKey")) {
			if (src.getConfigKey() != null) {
				MailboxAccountConfigId id = new MailboxAccountConfigId();
				id.setConfigKey(src.getConfigKey());
				dest.setId(id);
			} else {
				dest.setId(null);
			}
		}
	}

}
