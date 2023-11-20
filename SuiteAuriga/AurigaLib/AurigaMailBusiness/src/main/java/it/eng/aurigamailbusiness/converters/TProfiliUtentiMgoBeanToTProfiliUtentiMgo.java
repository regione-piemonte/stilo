/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TProfiliUtentiMgoBean;
import it.eng.aurigamailbusiness.database.mail.TProfiliUtentiMgo;
import it.eng.aurigamailbusiness.database.mail.TProfiliUtentiMgoId;
import it.eng.core.business.converter.IBeanPopulate;

public class TProfiliUtentiMgoBeanToTProfiliUtentiMgo implements IBeanPopulate<TProfiliUtentiMgoBean, TProfiliUtentiMgo> {

	@Override
	public void populate(TProfiliUtentiMgoBean src, TProfiliUtentiMgo dest) throws Exception {
		TProfiliUtentiMgoId id = new TProfiliUtentiMgoId();
		if (src.getProfilo() != null) {
			id.setProfilo(src.getProfilo());
		}
		if (src.getIdRelFruitoreCasella() != null) {
			id.setIdRelFruitoreCasella(src.getIdRelFruitoreCasella());
		}
		if (src.getIdRelUtenteFruitore() != null) {
			id.setIdRelUtenteFruitore(src.getIdRelUtenteFruitore());
		}
		dest.setId(id);
	}

	@Override
	public void populateForUpdate(TProfiliUtentiMgoBean src, TProfiliUtentiMgo dest) throws Exception {
		if (src.hasPropertyBeenModified("profilo")) {
			TProfiliUtentiMgoId id = new TProfiliUtentiMgoId();
			id.setProfilo(src.getProfilo());
			dest.setId(id);
		} else {
			dest.setId(null);
		}
		if (src.hasPropertyBeenModified("idRelFruitoreCasella")) {
			TProfiliUtentiMgoId id = new TProfiliUtentiMgoId();
			id.setIdRelFruitoreCasella(src.getIdRelFruitoreCasella());
			dest.setId(id);
		} else {
			dest.setId(null);
		}
		if (src.hasPropertyBeenModified("idRelUtenteFruitore")) {
			TProfiliUtentiMgoId id = new TProfiliUtentiMgoId();
			id.setIdRelUtenteFruitore(src.getIdRelUtenteFruitore());
			dest.setId(id);
		} else {
			dest.setId(null);
		}
	}

}