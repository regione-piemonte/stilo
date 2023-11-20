/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TAssegnEmailBean;
import it.eng.aurigamailbusiness.database.mail.TAssegnEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TAssegnEmailToTAssegnEmailBean implements IBeanPopulate<TAssegnEmail, TAssegnEmailBean> {

	@Override
	public void populate(TAssegnEmail src, TAssegnEmailBean dest) throws Exception {
		if (src.getTEmailMgo().getIdEmail() != null) {
			dest.setIdEmail(src.getTEmailMgo().getIdEmail());
		}
		if (src.getTUtentiModPecByIdUtenteDest() != null && src.getTUtentiModPecByIdUtenteDest().getIdUtente() != null) {
			dest.setIdUtenteDest(src.getTUtentiModPecByIdUtenteDest().getIdUtente());
		}
		if (src.getTUtentiModPecByIdUtenteMitt() != null && src.getTUtentiModPecByIdUtenteMitt().getIdUtente() != null) {
			dest.setIdUtenteMitt(src.getTUtentiModPecByIdUtenteMitt().getIdUtente());
		}
		if (src.getTAnagFruitoriCaselle() != null && src.getTAnagFruitoriCaselle().getIdFruitoreCasella() != null) {
			dest.setIdFruitoreDest(src.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
	}

	@Override
	public void populateForUpdate(TAssegnEmail src, TAssegnEmailBean dest) throws Exception {
		if (src.getTEmailMgo().getIdEmail() != null) {
			dest.setIdEmail(src.getTEmailMgo().getIdEmail());
		}
		if (src.getTUtentiModPecByIdUtenteDest() != null && src.getTUtentiModPecByIdUtenteDest().getIdUtente() != null) {
			dest.setIdUtenteDest(src.getTUtentiModPecByIdUtenteDest().getIdUtente());
		}
		if (src.getTUtentiModPecByIdUtenteMitt() != null && src.getTUtentiModPecByIdUtenteMitt().getIdUtente() != null) {
			dest.setIdUtenteMitt(src.getTUtentiModPecByIdUtenteMitt().getIdUtente());
		}
		if (src.getTAnagFruitoriCaselle() != null && src.getTAnagFruitoriCaselle().getIdFruitoreCasella() != null) {
			dest.setIdFruitoreDest(src.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
	}

}