/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class SceltaTipoFlussoIstanzaForm extends DynamicForm {
	
	private SceltaTipoFlussoIstanzaPopup window;
	private DynamicForm instance;

	private SelectItem flowTypeId;
	private HiddenItem idProcessType;
	private HiddenItem nomeProcessType;	
	private HiddenItem idDocTypeFinale;
	private HiddenItem nomeDocTypeFinale;
		

	public SceltaTipoFlussoIstanzaForm(final String tipoIstanza, SceltaTipoFlussoIstanzaPopup pWindow, final ServiceCallback<Record> callback){
		
		instance = this;
		
		window = pWindow;
		
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(200, 200);
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);
				
		GWTRestDataSource idTipoFlussoIstanzaDS = new GWTRestDataSource("LoadComboTipoFlussoIstanzaDataSource", "flowTypeId", FieldType.TEXT);
		idTipoFlussoIstanzaDS.addParam("tipoIstanza", tipoIstanza);
		
		flowTypeId = new SelectItem("flowTypeId") {

			@Override
			public void onOptionClick(Record record) {
				flowTypeId.setValue(record.getAttribute("flowTypeId"));
				idProcessType.setValue(record.getAttribute("idProcessType"));	
				nomeProcessType.setValue(record.getAttribute("nomeProcessType"));					
				idDocTypeFinale.setValue(record.getAttribute("idDocTypeFinale"));
				nomeDocTypeFinale.setValue(record.getAttribute("nomeDocTypeFinale"));
			}
		};
		flowTypeId.setShowTitle(false);
		flowTypeId.setWidth(300);
		flowTypeId.setColSpan(2);
		flowTypeId.setAlign(Alignment.CENTER);
		flowTypeId.setValueField("flowTypeId");
		flowTypeId.setDisplayField("flowTypeId");
		flowTypeId.setOptionDataSource(idTipoFlussoIstanzaDS);
		flowTypeId.setRequired(true);		
		flowTypeId.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// se non ci sono tipi flussi o ce ne uno solo lo seleziono e chiudo la popup
				manageOnDataArrived(event.getData().toArray(), callback);
			}
		});

		idProcessType = new HiddenItem("idProcessType");
		nomeProcessType = new HiddenItem("nomeProcessType");
		idDocTypeFinale = new HiddenItem("idDocTypeFinale");
		nomeDocTypeFinale = new HiddenItem("nomeDocTypeFinale");
		
		ButtonItem okButton = new ButtonItem("okButton", "Ok");
		okButton.setColSpan(2);
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(validate()) {
					manageOnClick(callback);
				}
			}
		});

		setFields(flowTypeId, idProcessType, nomeProcessType, idDocTypeFinale, nomeDocTypeFinale, okButton);

		idTipoFlussoIstanzaDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				manageOnDataArrived(response.getData(), callback);
			}
		});

	}

	protected void manageOnDataArrived(Record[] data, ServiceCallback<Record> callback) {
		if (data.length == 1) {
			flowTypeId.setValue(data[0].getAttribute("flowTypeId"));
			idProcessType.setValue(data[0].getAttribute("idProcessType"));	
			nomeProcessType.setValue(data[0].getAttribute("nomeProcessType"));	
			idDocTypeFinale.setValue(data[0].getAttribute("idDocTypeFinale"));		
			nomeDocTypeFinale.setValue(data[0].getAttribute("nomeDocTypeFinale"));		
			manageOnClick(callback);
		} else {			
			if ((!window.isDrawn()) || (!window.isVisible())) {
				window.show();
			}
		}
	}

	protected void manageOnClick(final ServiceCallback<Record> callback) {
		if (callback != null) {
			callback.execute(getValuesAsRecord());
		}
		window.markForDestroy();		
	}

}
