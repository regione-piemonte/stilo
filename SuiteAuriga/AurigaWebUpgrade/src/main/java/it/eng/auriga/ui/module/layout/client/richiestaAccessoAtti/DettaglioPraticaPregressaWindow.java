/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.archivio.PraticaPregressaDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioPraticaPregressaWindow extends ModalWindow {

	private DettaglioPraticaPregressaWindow _window;

	private PraticaPregressaDetail portletLayout;

	public DettaglioPraticaPregressaWindow(String idFolder) {

		super("dettaglio_pratica_pregressa", true);

		setTitle("Dettaglio pratica pregressa");

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		Record record = new Record();
		record.setAttribute("idUdFolder", idFolder);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lGwtRestDataSource.getData(record, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record detailRecord = response.getData()[0];						
					portletLayout = new PraticaPregressaDetail("dettaglio_pratica_pregressa");
					portletLayout.editRecord(detailRecord);
					portletLayout.getValuesManager().clearErrors(true);
					portletLayout.setHeight100();
					portletLayout.setWidth100();
					portletLayout.viewMode();
					setBody(portletLayout);
					_window.show();
				}
			}
		});
	
		setIcon("menu/carica_pratica_pregressa.png");
	}
	
	public PraticaPregressaDetail getPortletLayout() {
		return portletLayout;
	}

	public void setPortletLayout(PraticaPregressaDetail portletLayout) {
		this.portletLayout = portletLayout;
	}

}
