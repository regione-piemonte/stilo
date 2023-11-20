/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioAttoAutorizzazioneAnnRegWindow extends ModalWindow {

	private DettaglioAttoAutorizzazioneAnnRegWindow _window;

	private AttiAutorizzazioneAnnRegDetail portletLayout;

	public DettaglioAttoAutorizzazioneAnnRegWindow(Record record, String title) {

		super("dettaglioattoautannreg", true);

		setTitle(title);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttiAutorizzazioneAnnRegDatasource", true, "idAtto", FieldType.TEXT);
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idAtto", record.getAttribute("idAtto"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					portletLayout = new AttiAutorizzazioneAnnRegDetail("dettaglioattoautannreg");
					portletLayout.editRecord(lRecord);
					portletLayout.getValuesManager().clearErrors(true);
					portletLayout.setHeight100();
					portletLayout.setWidth100();
					portletLayout.viewMode();
					setBody(portletLayout);
					_window.show();
				}
			}
		});

		setIcon("blank.png");
	}

}
