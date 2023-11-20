/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TCommentiEmailBean;
import it.eng.aurigamailbusiness.database.mail.TCommentiEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TCommentiEmailToTCommentiEmailBean implements IBeanPopulate<TCommentiEmail, TCommentiEmailBean> {

	@Override
	public void populate(TCommentiEmail src, TCommentiEmailBean dest) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateForUpdate(TCommentiEmail src, TCommentiEmailBean dest) throws Exception {
		// TODO Auto-generated method stub

	}

}