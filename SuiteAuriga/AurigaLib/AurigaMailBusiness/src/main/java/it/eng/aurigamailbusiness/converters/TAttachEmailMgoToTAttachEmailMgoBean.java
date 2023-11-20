/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.Normalizer;

import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TAttachEmailMgo;
import it.eng.core.business.converter.IBeanPopulate;

public class TAttachEmailMgoToTAttachEmailMgoBean implements IBeanPopulate<TAttachEmailMgo, TAttachEmailMgoBean> {

	@Override
	public void populate(TAttachEmailMgo src, TAttachEmailMgoBean dest) throws Exception {
		if (src.getTEmailMgo() != null) {
			dest.setIdEmail(src.getTEmailMgo().getIdEmail());
		}
		if (src.getTRegProtVsEmail() != null) {
			dest.setIdRegProtEmail(src.getTRegProtVsEmail().getIdRegProtEmail());
		}

		// Cacco Federico 18-11-2015
		// Normalizzo la stringa per risolvere i problemi di charset di UNICODE (usato da alcuni client)
		String normalizedStringDisplayName = Normalizer.normalize(src.getDisplayFilename(), Normalizer.Form.NFC);
		String normalizedStringNomeOriginale = Normalizer.normalize(src.getNomeOriginale(), Normalizer.Form.NFC);

		dest.setDisplayFilename(normalizedStringDisplayName);
		dest.setNomeOriginale(normalizedStringNomeOriginale);
	}

	@Override
	public void populateForUpdate(TAttachEmailMgo src, TAttachEmailMgoBean dest) throws Exception {
		if (src.getTEmailMgo() != null) {
			dest.setIdEmail(src.getTEmailMgo().getIdEmail());
		}
		if (src.getTRegProtVsEmail() != null) {
			dest.setIdRegProtEmail(src.getTRegProtVsEmail().getIdRegProtEmail());
		}
	}

}