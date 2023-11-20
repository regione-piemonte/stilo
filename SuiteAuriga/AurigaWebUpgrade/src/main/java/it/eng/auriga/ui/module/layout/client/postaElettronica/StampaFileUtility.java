/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.BrowserUtility;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;

/**
 * 
 * @author DANCRIST
 *
 */

public class StampaFileUtility {

	public interface StampaFileCallback {

		void execute(String nomeStampante);

	}

	public static void stampaFile(final Record pRecord, StampaFileCallback pStampaFileCallback) {

		if (isNotPDF(pRecord)) {

			new GWTRestService<Record, Record>("StampaFileDataSource").performCustomOperation("trasformaFile", pRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						buildStampaFile(result);
					}
				}
			}, new DSRequest());

		} else {
			buildStampaFile(pRecord);
		}
	}

	private static Boolean isNotPDF(Record input) {
		boolean verifiy = false;
		if (input != null && input.getAttributeAsRecordList("listaAllegati") != null && input.getAttributeAsRecordList("listaAllegati").getLength() > 0
				&& !input.getAttributeAsRecordList("listaAllegati").isEmpty()) {
			for (int i = 0; i < input.getAttributeAsRecordList("listaAllegati").getLength(); i++) {
				Record item = input.getAttributeAsRecordList("listaAllegati").get(i);
				if (item != null) {
					if (item.getAttributeAsObject("infoFile") != null && !"application/pdf".equals(item.getAttributeAsObject("infoFile"))) {
						verifiy = true;
						break;
					}
				}
			}
		}
		return verifiy;
	}

	private static void buildStampaFile(final Record pRecord) {
		String modalitaStampaFileWindow = AurigaLayout.getParametroDB("MODALITA_STAMPA_FILE");
		if (modalitaStampaFileWindow == null || "".equalsIgnoreCase(modalitaStampaFileWindow) || "APPLET".equalsIgnoreCase(modalitaStampaFileWindow)
				|| BrowserUtility.detectIfIEBrowser()) {

			StampaFileWindow lStampaFileWindow = new StampaFileWindow(pRecord) {

				public void closeCallBack() {

				}
			};
			lStampaFileWindow.setVisible(false);
		} else {
			new StampaFileHybridWindow(pRecord);
		}
	}

}
