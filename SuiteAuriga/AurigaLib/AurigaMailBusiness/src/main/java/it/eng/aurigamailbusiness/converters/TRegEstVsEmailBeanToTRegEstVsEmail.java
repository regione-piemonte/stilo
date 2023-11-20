/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRegEstVsEmailBean;
import it.eng.aurigamailbusiness.database.mail.TDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TRegEstVsEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TRegEstVsEmailBeanToTRegEstVsEmail implements IBeanPopulate<TRegEstVsEmailBean, TRegEstVsEmail> {

	@Override
	public void populate(TRegEstVsEmailBean src, TRegEstVsEmail dest) throws Exception {
		if (src.getIdEmailRicevuta() != null) {
			TEmailMgo email1 = new TEmailMgo();
			email1.setIdEmail(src.getIdEmailRicevuta());
			dest.setTEmailMgoByIdEmailRicevuta(email1);
		}
		if (src.getIdEmailInviata() != null) {
			TEmailMgo email2 = new TEmailMgo();
			email2.setIdEmail(src.getIdEmailInviata());
			dest.setTEmailMgoByIdEmailInviata(email2);
		}
		if (src.getIdDestinatarioEmail() != null) {
			TDestinatariEmailMgo destina = new TDestinatariEmailMgo();
			destina.setIdDestinatarioEmail(src.getIdDestinatarioEmail());
			dest.setTDestinatariEmailMgo(destina);
		}
	}

	@Override
	public void populateForUpdate(TRegEstVsEmailBean src, TRegEstVsEmail dest) throws Exception {
		if (src.getIdEmailRicevuta() != null) {
			TEmailMgo email1 = new TEmailMgo();
			email1.setIdEmail(src.getIdEmailRicevuta());
			dest.setTEmailMgoByIdEmailRicevuta(email1);
		}
		if (src.getIdEmailInviata() != null) {
			TEmailMgo email2 = new TEmailMgo();
			email2.setIdEmail(src.getIdEmailInviata());
			dest.setTEmailMgoByIdEmailInviata(email2);
		}
		if (src.getIdDestinatarioEmail() != null) {
			TDestinatariEmailMgo destina = new TDestinatariEmailMgo();
			destina.setIdDestinatarioEmail(src.getIdDestinatarioEmail());
			dest.setTDestinatariEmailMgo(destina);
		}
	}

}