/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TDestinatariEmailMgo;
import it.eng.core.business.converter.IBeanPopulate;

public class TDestinatariEmailMgoToTDestinatariEmailMgoBean implements IBeanPopulate<TDestinatariEmailMgo, TDestinatariEmailMgoBean> {

	@Override
	public void populate(TDestinatariEmailMgo src, TDestinatariEmailMgoBean dest) throws Exception {
		if (src.getTEmailMgo() != null) {
			dest.setIdEmail(src.getTEmailMgo().getIdEmail());
		}
		if (src.getTRubricaEmail() != null) {
			dest.setIdVoceRubricaDest(src.getTRubricaEmail().getIdVoceRubricaEmail());
		}
	}

	@Override
	public void populateForUpdate(TDestinatariEmailMgo src, TDestinatariEmailMgoBean dest) throws Exception {
		if (src.getTEmailMgo() != null) {
			dest.setIdEmail(src.getTEmailMgo().getIdEmail());
		}
		if (src.getTRubricaEmail() != null) {
			dest.setIdVoceRubricaDest(src.getTRubricaEmail().getIdVoceRubricaEmail());
		}
	}

}