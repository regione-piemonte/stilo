/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.protocollazione.AcquisisciDaScannerHybridWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.AcquisisciDaScannerWindow;
import it.eng.utility.ui.module.core.client.BrowserUtility;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class ScanUtility {

	public interface ScanCallback {

		void execute(String filePdf, final String uriPdf, String fileTif, String uriTif, String record);

	}

	public static void openScan(final ScanCallback callback) {

		String modalitaScansione = AurigaLayout.getParametroDB("MODALITA_SCANSIONE");
		if (modalitaScansione == null || "".equalsIgnoreCase(modalitaScansione) || "APPLET".equalsIgnoreCase(modalitaScansione)
				|| BrowserUtility.detectIfIEBrowser()) {
			AcquisisciDaScannerWindow lAcquisisciDaScannerWindow = new AcquisisciDaScannerWindow(callback);
			lAcquisisciDaScannerWindow.show();
		} else {
			// Ricavo il JSESSIOID
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ServiceRestUserUtil");
			lGwtRestDataSource.executecustom("getSessionId", null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record data = response.getData()[0];
						String sessionId = data.getAttributeAsString("value");
						new AcquisisciDaScannerHybridWindow(callback, sessionId);
					} else {
						Layout.addMessage(new MessageBean("Errore nell'avvio del modulo di scansione", "", MessageType.ERROR));
					}
				}
			});
		}
	}

}
