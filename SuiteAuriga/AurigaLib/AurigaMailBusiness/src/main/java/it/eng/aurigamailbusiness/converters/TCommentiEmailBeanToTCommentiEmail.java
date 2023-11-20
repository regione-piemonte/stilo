/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TCommentiEmailBean;
import it.eng.aurigamailbusiness.database.mail.TCommentiEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TCommentiEmailBeanToTCommentiEmail implements IBeanPopulate<TCommentiEmailBean, TCommentiEmail> {

	@Override
	public void populate(TCommentiEmailBean src, TCommentiEmail dest) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateForUpdate(TCommentiEmailBean src, TCommentiEmail dest) throws Exception {
		// TODO Auto-generated method stub

	}

}