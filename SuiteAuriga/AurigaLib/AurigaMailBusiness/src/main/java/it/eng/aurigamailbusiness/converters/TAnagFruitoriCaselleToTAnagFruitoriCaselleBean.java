/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.core.business.converter.IBeanPopulate;

public class TAnagFruitoriCaselleToTAnagFruitoriCaselleBean implements IBeanPopulate<TAnagFruitoriCaselle, TAnagFruitoriCaselleBean> {

	@Override
	public void populate(TAnagFruitoriCaselle src, TAnagFruitoriCaselleBean dest) throws Exception {
		if (src.getTAnagFruitoriCaselleByIdEnteAoo() != null) {
			dest.setIdEnteAOO(src.getTAnagFruitoriCaselleByIdEnteAoo().getIdFruitoreCasella());
		}
		if(src.getTAnagFruitoriCaselleByIdFruitoreSup()!=null){
			dest.setIdFruitoreSup(src.getTAnagFruitoriCaselleByIdFruitoreSup().getIdFruitoreCasella());
		}
	}

	@Override
	public void populateForUpdate(TAnagFruitoriCaselle src, TAnagFruitoriCaselleBean dest) throws Exception {
		if (src.getTAnagFruitoriCaselleByIdEnteAoo() != null) {
			dest.setIdEnteAOO(src.getTAnagFruitoriCaselleByIdEnteAoo().getIdFruitoreCasella());
		}
		if(src.getTAnagFruitoriCaselleByIdFruitoreSup()!=null){
			dest.setIdFruitoreSup(src.getTAnagFruitoriCaselleByIdFruitoreSup().getIdFruitoreCasella());
		}
	}

}