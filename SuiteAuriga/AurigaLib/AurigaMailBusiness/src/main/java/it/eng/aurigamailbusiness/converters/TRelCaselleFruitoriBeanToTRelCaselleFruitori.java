/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRelCaselleFruitoriBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.mail.TRelCaselleFruitori;
import it.eng.core.business.converter.IBeanPopulate;

public class TRelCaselleFruitoriBeanToTRelCaselleFruitori implements IBeanPopulate<TRelCaselleFruitoriBean, TRelCaselleFruitori> {

	@Override
	public void populate(TRelCaselleFruitoriBean src, TRelCaselleFruitori dest) throws Exception {
		if (src.getIdFruitoreCasella() != null) {
			TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
			fruitore.setIdFruitoreCasella(src.getIdFruitoreCasella());
			dest.setTAnagFruitoriCaselle(fruitore);
		}
		if (src.getIdCasella() != null) {
			MailboxAccount account = new MailboxAccount();
			account.setIdAccount(src.getIdCasella());
			dest.setMailboxAccount(account);
		}

	}

	@Override
	public void populateForUpdate(TRelCaselleFruitoriBean src, TRelCaselleFruitori dest) throws Exception {
		if (src.hasPropertyBeenModified("idFruitoreCasella")) {
			if (src.getIdFruitoreCasella() != null) {
				TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
				fruitore.setIdFruitoreCasella(src.getIdFruitoreCasella());
				dest.setTAnagFruitoriCaselle(fruitore);
			} else {
				dest.setTAnagFruitoriCaselle(null);
			}
		}
		if (src.hasPropertyBeenModified("idCasella")) {
			if (src.getIdCasella() != null) {
				MailboxAccount account = new MailboxAccount();
				account.setIdAccount(src.getIdCasella());
				dest.setMailboxAccount(account);
			} else {
				dest.setMailboxAccount(null);
			}
		}
	}

}