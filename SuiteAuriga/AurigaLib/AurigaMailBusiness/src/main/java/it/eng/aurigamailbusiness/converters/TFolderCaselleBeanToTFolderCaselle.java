/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.aurigamailbusiness.database.mail.TFolderCaselle;
import it.eng.core.business.converter.IBeanPopulate;

public class TFolderCaselleBeanToTFolderCaselle implements IBeanPopulate<TFolderCaselleBean, TFolderCaselle> {

	@Override
	public void populate(TFolderCaselleBean src, TFolderCaselle dest) throws Exception {
		if (src.getIdCasella() != null) {
			MailboxAccount ma = new MailboxAccount();
			ma.setIdAccount(src.getIdCasella());
			dest.setMailboxAccount(ma);
		}
		if (src.getIdFolderSup() != null) {
			TFolderCaselle fc = new TFolderCaselle();
			fc.setIdFolderCasella(src.getIdFolderSup());
			dest.setTFolderCaselle(fc);
		}

	}

	@Override
	public void populateForUpdate(TFolderCaselleBean src, TFolderCaselle dest) throws Exception {
		if (src.hasPropertyBeenModified("idCasella")) {
			MailboxAccount ma = new MailboxAccount();
			ma.setIdAccount(src.getIdCasella());
			dest.setMailboxAccount(ma);
		}
		if (src.hasPropertyBeenModified("idFolderSup")) {
			TFolderCaselle fc = new TFolderCaselle();
			fc.setIdFolderCasella(src.getIdFolderSup());
			dest.setTFolderCaselle(fc);
		}
	}

}