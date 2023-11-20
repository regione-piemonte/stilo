/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TRubricaEmailToTRubricaEmailBean implements IBeanPopulate<TRubricaEmail, TRubricaEmailBean> {

	@Override
	public void populate(TRubricaEmail src, TRubricaEmailBean dest) throws Exception {
		if (src.getTAnagFruitoriCaselle() != null) {
			dest.setIdFruitoreCasella(src.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
	}

	@Override
	public void populateForUpdate(TRubricaEmail src, TRubricaEmailBean dest) throws Exception {
		if (src.getTAnagFruitoriCaselle() != null) {
			dest.setIdFruitoreCasella(src.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
	}

}