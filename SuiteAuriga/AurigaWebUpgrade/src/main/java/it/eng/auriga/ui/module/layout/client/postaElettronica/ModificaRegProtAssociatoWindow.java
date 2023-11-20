/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ModificaRegProtAssociatoWindow extends ModalWindow {

	private ModificaRegProtAssociatoWindow _window;

	private ProtocollazioneDetail portletLayout;

	public ModificaRegProtAssociatoWindow(Record record, String title, ServiceCallback<Record> afterUpdateCallback) {
		this(record, null, title, afterUpdateCallback);
	}
	
//	public DettaglioRegProtAssociatoWindow(Record record, String taskName, String title) {
//		this(record, taskName, title, null);
//	}
		
	//public DettaglioRegProtAssociatoWindow(Record record, String taskName, String title, ServiceCallback<Record> afterUpdateCallback) {
	public ModificaRegProtAssociatoWindow(Record record, String taskName, String title, ServiceCallback<Record> afterUpdateCallback) {

		super("modificaregprotassociato", true);

		setTitle(title);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
				
//		loadDettUD(record, taskName, afterUpdateCallback);
		loadDettUD(record, taskName, afterUpdateCallback);
		
		setIcon("blank.png");
	}

//	private void loadDettUD(Record record,String taskName, final ServiceCallback<Record> afterUpdateCallback) {
	private void loadDettUD(Record record, String taskName, final ServiceCallback<Record> afterUpdateCallback) {

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		if(taskName != null && !"".equals(taskName)) {
			lGwtRestDataSource.addParam("taskName", taskName);
		}
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
		lGwtRestDataSource.extraparam.put("listaIdUdScansioni", record.getAttribute("listaIdUdScansioni"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];					
					/*
					String lStringTipoProtocollo = lRecord.getAttribute("flgTipoProv") != null ? lRecord.getAttribute("flgTipoProv") : "";
					if (lStringTipoProtocollo.equals("E")) {
						portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailEntrata(lRecord);
						portletLayout.caricaDettaglio(null, lRecord);
					} else if (lStringTipoProtocollo.equals("U")) {
						portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailUscita(lRecord);
						portletLayout.caricaDettaglio(null, lRecord);
					} else if (lStringTipoProtocollo.equals("I")) {
						portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailInterna(lRecord);
						portletLayout.caricaDettaglio(null, lRecord);
					} else {
						portletLayout = ProtocollazioneUtil.buildProtocollazioneDetailBozze(lRecord);
						portletLayout.caricaDettaglio(null, lRecord);
					}
					*/
//					portletLayout = ProtocollazioneDetail.getInstance(lRecord, afterUpdateCallback);
					portletLayout = ProtocollazioneDetail.getInstance(lRecord, afterUpdateCallback);
					portletLayout.caricaDettaglio(null, lRecord);
					portletLayout.getValuesManager().clearErrors(true);
					portletLayout.setHeight100();
					portletLayout.setWidth100();
					portletLayout.caricaDatiImgAssocMode();						
					setBody(portletLayout);
					_window.show();
				}
			}
		});
	}
	
}
