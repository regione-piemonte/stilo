/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TProfiliFruitoriMgoBean;
import it.eng.aurigamailbusiness.database.mail.TProfiliFruitoriMgo;
import it.eng.aurigamailbusiness.database.mail.TProfiliFruitoriMgoId;
import it.eng.core.business.converter.IBeanPopulate;

public class TProfiliFruitoriMgoBeanToTProfiliFruitoriMgo implements IBeanPopulate<TProfiliFruitoriMgoBean, TProfiliFruitoriMgo> {

	@Override
	public void populate(TProfiliFruitoriMgoBean src, TProfiliFruitoriMgo dest) throws Exception {
		TProfiliFruitoriMgoId id = new TProfiliFruitoriMgoId();
		if (src.getProfilo() != null) {
			id.setProfilo(src.getProfilo());
		}
		if (src.getIdRelFruitoreCasella() != null) {
			id.setIdRelFruitoreCasella(src.getIdRelFruitoreCasella());
		}
		dest.setId(id);

	}

	@Override
	public void populateForUpdate(TProfiliFruitoriMgoBean src, TProfiliFruitoriMgo dest) throws Exception {
		if (src.hasPropertyBeenModified("profilo")) {
			TProfiliFruitoriMgoId id = new TProfiliFruitoriMgoId();
			id.setProfilo(src.getProfilo());
			dest.setId(id);
		} else {
			dest.setId(null);
		}
		if (src.hasPropertyBeenModified("idRelFruitoreCasella")) {
			TProfiliFruitoriMgoId id = new TProfiliFruitoriMgoId();
			id.setIdRelFruitoreCasella(src.getIdRelFruitoreCasella());
			dest.setId(id);
		} else {
			dest.setId(null);
		}
	}

}