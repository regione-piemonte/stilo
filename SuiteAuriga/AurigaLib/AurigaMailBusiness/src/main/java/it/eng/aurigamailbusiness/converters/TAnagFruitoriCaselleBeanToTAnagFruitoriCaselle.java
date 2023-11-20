/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.core.business.converter.IBeanPopulate;

public class TAnagFruitoriCaselleBeanToTAnagFruitoriCaselle implements IBeanPopulate<TAnagFruitoriCaselleBean, TAnagFruitoriCaselle> {

	@Override
	public void populate(TAnagFruitoriCaselleBean src, TAnagFruitoriCaselle dest) throws Exception {
		if (src.getIdEnteAOO() != null) {
			TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
			fruitore.setIdFruitoreCasella(src.getIdEnteAOO());
			dest.setTAnagFruitoriCaselleByIdEnteAoo(fruitore);
		}
		if (src.getIdFruitoreSup() != null) {
			TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
			fruitore.setIdFruitoreCasella(src.getIdFruitoreSup());
			dest.setTAnagFruitoriCaselleByIdFruitoreSup(fruitore);
		}

	}

	@Override
	public void populateForUpdate(TAnagFruitoriCaselleBean src, TAnagFruitoriCaselle dest) throws Exception {
		if (src.hasPropertyBeenModified("idEnteAOO")) {
			if (src.getIdEnteAOO() != null) {
				TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
				fruitore.setIdFruitoreCasella(src.getIdEnteAOO());
				dest.setTAnagFruitoriCaselleByIdEnteAoo(fruitore);
			} else {
				dest.setTAnagFruitoriCaselleByIdEnteAoo(null);
			}
		}
		if (src.hasPropertyBeenModified("idFruitoreSup")) {
			if (src.getIdFruitoreSup() != null) {
				TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
				fruitore.setIdFruitoreCasella(src.getIdFruitoreSup());
				dest.setTAnagFruitoriCaselleByIdFruitoreSup(fruitore);
			} else {
				dest.setTAnagFruitoriCaselleByIdFruitoreSup(null);
			}
		}
	}

}