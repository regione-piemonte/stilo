/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioTipoFascicoliAggregatiWindow extends ModalWindow {

	private DettaglioTipoFascicoliAggregatiWindow _window;

	private TipoFascicoliAggregatiLayout portletLayout;

	public DettaglioTipoFascicoliAggregatiWindow(Record record, String title) {

		super("dettagliotipofascicoliaggregati", true);

		setTitle(title);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		portletLayout = new TipoFascicoliAggregatiLayout(null, null, true);
		portletLayout.setHeight100();
		portletLayout.setWidth100();
	
		setBody(portletLayout);
	
		Record recordToLoad = new Record();
		recordToLoad.setAttribute("idFolderType", record.getAttribute("idFolderType"));
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("TipoFascicoliAggregatiDataSource", "idFolderType", FieldType.TEXT);
		lGwtRestDataSource.getData(recordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record detailRecord = response.getData()[0];
					portletLayout.getDetail().editRecord(detailRecord);
					portletLayout.getDetail().getValuesManager().clearErrors(true);		
					portletLayout.viewMode();
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							_window.show();
						}
					});
				}
			}
		});
		
		setIcon("menu/tipo_fascicoli_aggr.png");
	}
	
	public void manageOnCloseClick() {
		if (portletLayout.getDetail().isSaved()) {
			manageOnCloseClickAfterSaved();
		} 		
		closePortlet();		
	}
	
	public void manageOnCloseClickAfterSaved() {
		
	}

}
