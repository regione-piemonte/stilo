/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.core.business.converter.IBeanPopulate;

public class MailboxAccountBeanToMailboxAccount implements IBeanPopulate<MailboxAccountBean, MailboxAccount> {

	@Override
	public void populate(MailboxAccountBean src, MailboxAccount dest) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateForUpdate(MailboxAccountBean src, MailboxAccount dest) throws Exception {
		// TODO Auto-generated method stub

	}

}
