/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.Canvas;

public class EmailCollegateWindow extends ModalWindow {

	private EmailCollegateWindow _window;
	private String windowTitle;

	private Canvas portletLayout;

	public EmailCollegateWindow(final String tipoRel, String idEmail, String numeroMail, Record record) {

		super("emailcollegate", true);

		if (tipoRel.equalsIgnoreCase("notifica")) {
			windowTitle = numeroMail != null && "1".equals(numeroMail) ? "Notifica collegata" : "Notifiche collegate";
		} else if (tipoRel.equalsIgnoreCase("inoltro")) {
			windowTitle = numeroMail != null && "1".equals(numeroMail) ? "E-mail di inoltro collegata" : "E-mail di inoltro collegate";
		} else if (tipoRel.equalsIgnoreCase("risposta")) {
			windowTitle = numeroMail != null && "1".equals(numeroMail) ? "E-mail di risposta collegata" : "E-mail di risposta collegate";
		}
		setTitle(windowTitle);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		/**
		 * Apro direttamente il dettaglio della mail poichè in lista ne è presente solo una
		 */
		if (numeroMail != null && "1".equals(numeroMail)) {
			if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
				portletLayout = new PostaElettronicaDetail("emailcollegate", tipoRel);
				((PostaElettronicaDetail) portletLayout).editRecord(record);
				portletLayout.getValuesManager().clearErrors(true);
			} else {
				portletLayout = new DettaglioPostaElettronica("emailcollegate", tipoRel);
				Record lRecord = new Record();
				lRecord.setAttribute("idEmail", idEmail);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
				lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							((DettaglioPostaElettronica) portletLayout).editRecord(record);
							((DettaglioPostaElettronica) portletLayout).getValuesManager().clearErrors(true);
							((DettaglioPostaElettronica) portletLayout).markForRedraw();
						}
					}
				}, new DSRequest());
			}
		} else {
			portletLayout = new EmailCollegateLayout(idEmail, tipoRel) {

				@Override
				public void showDetail() {
					
					super.showDetail();
					if (fullScreenDetail) {
						String title = "";
						if (mode != null) {
							if (mode.equals("new")) {
								title = getNewDetailTitle();
							} else if (mode.equals("edit")) {
								title = getEditDetailTitle();
							} else if (mode.equals("view")) {
								title = getViewDetailTitle();
							}
						}
						_window.setTitle(title);
					}
				}

				@Override
				public void hideDetail(boolean reloadList) {
					
					super.hideDetail(reloadList);
					if (fullScreenDetail) {
						_window.setTitle(windowTitle);
					}
				}

				@Override
				public boolean isForcedToAutoSearch() {
					
					return true;
				}
			};

			((EmailCollegateLayout) portletLayout).setLookup(false);
		}

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("mail/mail-reply2.png");
	}
}
