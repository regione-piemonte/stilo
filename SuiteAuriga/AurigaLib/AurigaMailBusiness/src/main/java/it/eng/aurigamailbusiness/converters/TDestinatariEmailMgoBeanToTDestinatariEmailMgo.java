/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TDestinatariEmailMgoBeanToTDestinatariEmailMgo implements IBeanPopulate<TDestinatariEmailMgoBean, TDestinatariEmailMgo> {

	@Override
	public void populate(TDestinatariEmailMgoBean src, TDestinatariEmailMgo dest) throws Exception {
		if (src.getIdEmail() != null) {
			TEmailMgo email = new TEmailMgo();
			email.setIdEmail(src.getIdEmail());
			dest.setTEmailMgo(email);
		}
		if (src.getIdVoceRubricaDest() != null) {
			TRubricaEmail rubrica = new TRubricaEmail();
			rubrica.setIdVoceRubricaEmail(src.getIdVoceRubricaDest());
			dest.setTRubricaEmail(rubrica);
		}
	}

	@Override
	public void populateForUpdate(TDestinatariEmailMgoBean src, TDestinatariEmailMgo dest) throws Exception {
		if (src.hasPropertyBeenModified("idEmail")) {
			if (src.getIdEmail() != null) {
				TEmailMgo email = new TEmailMgo();
				email.setIdEmail(src.getIdEmail());
				dest.setTEmailMgo(email);
			} else {
				dest.setTEmailMgo(null);
			}
		}
		if (src.hasPropertyBeenModified("idVoceRubricaDest")) {
			if (src.getIdVoceRubricaDest() != null) {
				TRubricaEmail rubrica = new TRubricaEmail();
				rubrica.setIdVoceRubricaEmail(src.getIdVoceRubricaDest());
				dest.setTRubricaEmail(rubrica);
			} else {
				dest.setTRubricaEmail(null);
			}
		}
	}

}