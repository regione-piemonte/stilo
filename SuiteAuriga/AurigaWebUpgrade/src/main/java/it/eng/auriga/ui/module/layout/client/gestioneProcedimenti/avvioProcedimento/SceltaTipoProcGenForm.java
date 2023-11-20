/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

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
import com.smartgwt.client.widgets.grid.ListGridField;

public class SceltaTipoProcGenForm extends DynamicForm {

	private SceltaTipoProcGenPopup window;
	private DynamicForm instance;

	private SelectItem idTipoProc;
	private HiddenItem nomeTipoProc;
	private HiddenItem flowTypeId;

	public SceltaTipoProcGenForm(String flgDocFolder, String tipo, final SceltaTipoProcGenPopup pWindow, final ServiceCallback<Record> callback) {

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

		GWTRestDataSource idTipoProcDS = new GWTRestDataSource("LoadComboTipoProcGenDataSource", "idTipoProc", FieldType.TEXT, true);
		idTipoProcDS.addParam("flgDocFolder", flgDocFolder);
		idTipoProcDS.addParam("tipo", tipo);
		
		idTipoProc = new SelectItem("idTipoProc") {

			@Override
			public void onOptionClick(Record record) {
				idTipoProc.setValue(record.getAttribute("idTipoProc"));
				nomeTipoProc.setValue(record.getAttribute("nomeTipoProc"));
				flowTypeId.setValue(record.getAttribute("flowTypeId"));
			}
		};
		ListGridField idTipoProcField = new ListGridField("idTipoProc");
		idTipoProcField.setHidden(true);
		ListGridField flowTypeIdField = new ListGridField("flowTypeId");
		flowTypeIdField.setHidden(true);
		ListGridField nomeTipoProcField = new ListGridField("nomeTipoProc");
		idTipoProc.setPickListFields(idTipoProcField, flowTypeIdField, nomeTipoProcField);
		idTipoProc.setShowTitle(false);
		idTipoProc.setWidth(300);
		idTipoProc.setColSpan(2);
		idTipoProc.setAlign(Alignment.CENTER);
		idTipoProc.setValueField("idTipoProc");
		idTipoProc.setDisplayField("nomeTipoProc");
		idTipoProc.setOptionDataSource(idTipoProcDS);
		idTipoProc.setAllowEmptyValue(true);
		idTipoProc.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// se non ci sono tipi documento o ce ne uno solo lo seleziono e chiudo la popup
				manageOnDataArrived(event.getData().toArray(), callback);
			}
		});

		nomeTipoProc = new HiddenItem("nomeTipoProc");

		flowTypeId = new HiddenItem("flowTypeId");

		ButtonItem okButton = new ButtonItem("okButton", "Ok");
		okButton.setColSpan(2);
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageOnClick(callback);
			}
		});

		setFields(idTipoProc, nomeTipoProc, flowTypeId, okButton);

		idTipoProcDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				manageOnDataArrived(response.getData(), callback);
			}
		});

	}

	protected void manageOnDataArrived(Record[] data, ServiceCallback<Record> callback) {
		if (data.length <= 1) {
			if (data.length == 1) {
				idTipoProc.setValue(data[0].getAttribute("idTipoProc"));
				nomeTipoProc.setValue(data[0].getAttribute("nomeTipoProc"));
				flowTypeId.setValue(data[0].getAttribute("flowTypeId"));
			}
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
