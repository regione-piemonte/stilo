/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TProfiliUtentiMgoBean;
import it.eng.aurigamailbusiness.database.mail.TProfiliUtentiMgo;
import it.eng.core.business.converter.IBeanPopulate;

public class TProfiliUtentiMgoToTProfiliUtentiMgoBean implements IBeanPopulate<TProfiliUtentiMgo, TProfiliUtentiMgoBean> {

	@Override
	public void populate(TProfiliUtentiMgo src, TProfiliUtentiMgoBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setProfilo(src.getId().getProfilo());
			dest.setIdRelFruitoreCasella(src.getId().getIdRelFruitoreCasella());
			dest.setIdRelUtenteFruitore(src.getId().getIdRelUtenteFruitore());
		}

	}

	@Override
	public void populateForUpdate(TProfiliUtentiMgo src, TProfiliUtentiMgoBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setProfilo(src.getId().getProfilo());
			dest.setIdRelFruitoreCasella(src.getId().getIdRelFruitoreCasella());
			dest.setIdRelUtenteFruitore(src.getId().getIdRelUtenteFruitore());
		}
	}

}