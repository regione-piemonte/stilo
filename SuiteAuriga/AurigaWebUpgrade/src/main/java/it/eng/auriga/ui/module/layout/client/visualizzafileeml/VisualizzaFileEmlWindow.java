/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

public class VisualizzaFileEmlWindow extends ModalWindow {

	private VisualizzaFileEmlWindow _window;
	private VisualizzaFileEmlDetail portletLayout;

	public VisualizzaFileEmlWindow(String nomeEntita, Record lRecordMail) {
		this(nomeEntita, lRecordMail, "");
	}

	public VisualizzaFileEmlWindow(String nomeEntita, Record lRecordMail, final String title) {

		super("visualizzafileeml", true);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		portletLayout = new VisualizzaFileEmlDetail(nomeEntita, this);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PreviewFileDatasource");
		lGwtRestDataSource.executecustom("recuperaDatiEmail", lRecordMail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					portletLayout.editRecord(record);
					String lTitle;
					if ((title == null) || (title.trim().length() == 0)) {
						lTitle = "Visualizza file " + record.getAttribute("nomeFileEml");
					} else {
						lTitle = "Visualizza " + title;
					}
					_window.setTitle(lTitle);
					_window.show();
				}
			}
		});
		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("mail/mail-reply2.png");
	}

}
