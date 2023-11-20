/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.FormatConverterInterface;
import it.eng.utility.formati.FormatiUtil;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class AurigaFormatPdfConverter implements FormatConverterInterface {

	private static Logger mLogger = Logger.getLogger(AurigaFormatPdfConverter.class);
	@Override
	public boolean isValidFormat(String mimetype, HttpSession session) {
		mLogger.debug("Start isValidFormat");
		try {
			return FormatiUtil.isConvertibile(session, mimetype);
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			return false;
		}
	}


}
