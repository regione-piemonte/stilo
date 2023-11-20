/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.stampanteCartacea.SelezionaPortaHybridWindow;
import it.eng.utility.ui.module.core.client.BrowserUtility;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;

public class PortScannerUtility {

	public interface PortScannerCallback {

		void execute(String nomeStampante);

	}

	public static void portScanner(String nomePorta, PortScannerCallback callback, PortScannerCallback callbackCancel) {

		String modalitaStampaEtichettaCartaceo = AurigaLayout.getParametroDB("MODALITA_STAMPA_ETICHETTA_CARTACEO");
		
		if (isStampaEtichetteHybridDisattiva()) {
			if (callback != null) {
				callback.execute(nomePorta);
			}
		} else if (modalitaStampaEtichettaCartaceo == null || "".equalsIgnoreCase(modalitaStampaEtichettaCartaceo) || "APPLET".equalsIgnoreCase(modalitaStampaEtichettaCartaceo) || BrowserUtility.detectIfIEBrowser()) {
			AurigaLayout.addMessage(new MessageBean("Applet non implementata per questa funzionalità	", "", MessageType.WARNING));
		} else {
			new SelezionaPortaHybridWindow(nomePorta, callback, callbackCancel);
			// Non serve fare lo show come nelle applet
		}
	}
	
	private static boolean isStampaEtichetteHybridDisattiva () {
		return AurigaLayout.isBottoniStampaEtichetteHybridDisattivi();
	}
}
