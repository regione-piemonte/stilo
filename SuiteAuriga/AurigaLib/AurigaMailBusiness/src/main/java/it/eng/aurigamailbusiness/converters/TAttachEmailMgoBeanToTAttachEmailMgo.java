/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.Normalizer;

import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TAttachEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TRegProtVsEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TAttachEmailMgoBeanToTAttachEmailMgo implements IBeanPopulate<TAttachEmailMgoBean, TAttachEmailMgo> {

	@Override
	public void populate(TAttachEmailMgoBean src, TAttachEmailMgo dest) throws Exception {
		if (src.getIdEmail() != null) {
			TEmailMgo email = new TEmailMgo();
			email.setIdEmail(src.getIdEmail());
			dest.setTEmailMgo(email);
		}
		if (src.getIdRegProtEmail() != null) {
			TRegProtVsEmail regProt = new TRegProtVsEmail();
			regProt.setIdRegProtEmail(src.getIdRegProtEmail());
			dest.setTRegProtVsEmail(regProt);

		}

		// Cacco Federico 18-11-2015
		// Normalizzo la stringa per risolvere i problemi di charset di UNICODE (usato da alcuni client)
		String normalizedStringDisplayName = Normalizer.normalize(src.getDisplayFilename(), Normalizer.Form.NFC);
		String normalizedStringNomeOriginale = Normalizer.normalize(src.getNomeOriginale(), Normalizer.Form.NFC);

		dest.setDisplayFilename(normalizedStringDisplayName);
		dest.setNomeOriginale(normalizedStringNomeOriginale);
	}

	@Override
	public void populateForUpdate(TAttachEmailMgoBean src, TAttachEmailMgo dest) throws Exception {
		if (src.getIdEmail() != null) {
			TEmailMgo email = new TEmailMgo();
			email.setIdEmail(src.getIdEmail());
			dest.setTEmailMgo(email);
		} else {
			dest.setTEmailMgo(null);
		}
		if (src.getIdRegProtEmail() != null) {
			TRegProtVsEmail regProt = new TRegProtVsEmail();
			regProt.setIdRegProtEmail(src.getIdRegProtEmail());
			dest.setTRegProtVsEmail(regProt);
		} else {
			dest.setTRegProtVsEmail(null);
		}
	}

}