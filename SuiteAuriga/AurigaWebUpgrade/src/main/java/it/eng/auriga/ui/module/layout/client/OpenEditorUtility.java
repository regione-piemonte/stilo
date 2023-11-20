/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.protocollazione.EditFileHybridWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.EditFileWindow;
import it.eng.utility.ui.module.core.client.BrowserUtility;

public class OpenEditorUtility {

	public interface OpenEditorCallback {

		void execute(String nomeFileFirmato, String uriFileFirmato, String record);
	}

	public static void openEditor(String display, String uri, Boolean remoteUri, String estensione, String impronta, OpenEditorCallback callback) {
		String modalitaOpenEditor = AurigaLayout.getParametroDB("MODALITA_EDIT_ONLINE");
		if (modalitaOpenEditor == null || "".equalsIgnoreCase(modalitaOpenEditor) || "APPLET".equalsIgnoreCase(modalitaOpenEditor)
				|| BrowserUtility.detectIfIEBrowser()) {
			EditFileWindow lEditFileWindow = new EditFileWindow(display, uri, remoteUri, estensione, impronta, callback);
			lEditFileWindow.show();
		} else {
			new EditFileHybridWindow(display, uri, remoteUri, estensione, impronta, callback);
			// Non serve fare lo show come nelle applet
		}
	}

}
