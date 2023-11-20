/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.stampante.SelezionaStampante;
import it.eng.auriga.ui.module.layout.client.stampante.SelezionaStampanteHybridWindow;
import it.eng.utility.ui.module.core.client.BrowserUtility;

public class PrinterScannerUtility {

	public interface PrinterScannerCallback {

		void execute(String nomeStampante);

	}

	public static void printerScanner(String nomeStampante, PrinterScannerCallback callback, PrinterScannerCallback callbackCancel) {

		String modalitaScansioneStampanti = AurigaLayout.getParametroDB("MODALITA_SELEZIONE_STAMPANTE");
		
		if (isStampaEtichetteHybridDisattiva()) {
			if (callback != null) {
				callback.execute(nomeStampante);
			}
		} else if (modalitaScansioneStampanti == null || "".equalsIgnoreCase(modalitaScansioneStampanti) || "APPLET".equalsIgnoreCase(modalitaScansioneStampanti) || BrowserUtility.detectIfIEBrowser()) {
			SelezionaStampante lSelezionaStampante = new SelezionaStampante(nomeStampante, callback, callbackCancel);
			lSelezionaStampante.show();
		} else {
			new SelezionaStampanteHybridWindow(nomeStampante, callback, callbackCancel);
			// Non serve fare lo show come nelle applet
		}
	}
	
	private static boolean isStampaEtichetteHybridDisattiva () {
		return AurigaLayout.isBottoniStampaEtichetteHybridDisattivi();
	}
}
