/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRelCaselleFruitoriBean;
import it.eng.aurigamailbusiness.database.mail.TRelCaselleFruitori;
import it.eng.core.business.converter.IBeanPopulate;

public class TRelCaselleFruitoriToTRelCaselleFruitoriBean implements IBeanPopulate<TRelCaselleFruitori, TRelCaselleFruitoriBean> {

	@Override
	public void populate(TRelCaselleFruitori src, TRelCaselleFruitoriBean dest) throws Exception {
		if (src.getTAnagFruitoriCaselle() != null) {
			dest.setIdFruitoreCasella(src.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
		if (src.getMailboxAccount() != null) {
			dest.setIdCasella(src.getMailboxAccount().getIdAccount());
		}
	}

	@Override
	public void populateForUpdate(TRelCaselleFruitori src, TRelCaselleFruitoriBean dest) throws Exception {
		if (src.getTAnagFruitoriCaselle() != null) {
			dest.setIdFruitoreCasella(src.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
		if (src.getMailboxAccount() != null) {
			dest.setIdCasella(src.getMailboxAccount().getIdAccount());
		}
	}

}