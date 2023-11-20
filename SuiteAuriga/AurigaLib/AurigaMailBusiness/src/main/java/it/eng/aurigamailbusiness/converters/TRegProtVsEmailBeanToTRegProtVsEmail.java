/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TRegProtVsEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TRegProtVsEmailBeanToTRegProtVsEmail implements IBeanPopulate<TRegProtVsEmailBean, TRegProtVsEmail> {

	@Override
	public void populate(TRegProtVsEmailBean src, TRegProtVsEmail dest) throws Exception {
		if (src.getIdEmail() != null) {
			TEmailMgo email = new TEmailMgo();
			email.setIdEmail(src.getIdEmail());
			dest.setTEmailMgo(email);
		}
	}

	@Override
	public void populateForUpdate(TRegProtVsEmailBean src, TRegProtVsEmail dest) throws Exception {
		if (src.hasPropertyBeenModified("idEmail")) {
			if (src.getIdEmail() != null) {
				TEmailMgo email = new TEmailMgo();
				email.setIdEmail(src.getIdEmail());
				dest.setTEmailMgo(email);
			}
		}

	}

}