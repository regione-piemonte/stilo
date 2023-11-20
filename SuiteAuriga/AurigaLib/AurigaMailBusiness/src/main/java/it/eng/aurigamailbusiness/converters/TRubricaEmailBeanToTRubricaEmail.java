/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TRubricaEmailBeanToTRubricaEmail implements IBeanPopulate<TRubricaEmailBean, TRubricaEmail> {

	@Override
	public void populate(TRubricaEmailBean src, TRubricaEmail dest) throws Exception {
		if (src.getIdFruitoreCasella() != null) {
			TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
			fruitore.setIdFruitoreCasella(src.getIdFruitoreCasella());
			dest.setTAnagFruitoriCaselle(fruitore);
		}

	}

	@Override
	public void populateForUpdate(TRubricaEmailBean src, TRubricaEmail dest) throws Exception {
		if (src.hasPropertyBeenModified("idFruitoreCasella")) {
			if (src.getIdFruitoreCasella() != null) {
				TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
				fruitore.setIdFruitoreCasella(src.getIdFruitoreCasella());
				dest.setTAnagFruitoriCaselle(fruitore);
			} else {
				dest.setTAnagFruitoriCaselle(null);
			}
		}

	}

}