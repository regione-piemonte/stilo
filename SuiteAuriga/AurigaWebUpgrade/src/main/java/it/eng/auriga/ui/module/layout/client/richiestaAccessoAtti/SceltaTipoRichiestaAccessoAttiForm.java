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

public class SceltaTipoRichiestaAccessoAttiForm extends DynamicForm {
	
	private SceltaTipoRichiestaAccessoAttiPopup window;
	private DynamicForm instance;

	private SelectItem idTipoDocumento;
	private HiddenItem descTipoDocumento;
	private HiddenItem flgTipoDocConVie;
	private HiddenItem siglaPraticaSuSistUfficioRichiedente;

	public SceltaTipoRichiestaAccessoAttiForm(final String idTipoDocDefault, SceltaTipoRichiestaAccessoAttiPopup pWindow, final ServiceCallback<Record> callback){
		
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
				
		GWTRestDataSource idTipoRichiestaAccessoAttiDS = new GWTRestDataSource("LoadComboTipoRichiestaAccessoAttiDataSource", "idTipoDocumento", FieldType.TEXT, true);
		
		idTipoDocumento = new SelectItem("idTipoDocumento") {

			@Override
			public void onOptionClick(Record record) {
				idTipoDocumento.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumento.setValue(record.getAttribute("descTipoDocumento"));
				flgTipoDocConVie.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				siglaPraticaSuSistUfficioRichiedente.setValue(record.getAttribute("siglaPraticaSuSistUfficioRichiedente"));
			}
		};
		idTipoDocumento.setShowTitle(false);
		idTipoDocumento.setWidth(300);
		idTipoDocumento.setColSpan(2);
		idTipoDocumento.setAlign(Alignment.CENTER);
		idTipoDocumento.setValueField("idTipoDocumento");
		idTipoDocumento.setDisplayField("descTipoDocumento");
		idTipoDocumento.setOptionDataSource(idTipoRichiestaAccessoAttiDS);
		idTipoDocumento.setRequired(true);		
		idTipoDocumento.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// se non ci sono tipi documento o ce ne uno solo lo seleziono e chiudo la popup
				manageOnDataArrived(event.getData().toArray(), idTipoDocDefault, callback);
			}
		});

		descTipoDocumento = new HiddenItem("descTipoDocumento");
		flgTipoDocConVie = new HiddenItem("flgTipoDocConVie");
		siglaPraticaSuSistUfficioRichiedente = new HiddenItem("siglaPraticaSuSistUfficioRichiedente");
		
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

		setFields(idTipoDocumento, descTipoDocumento, flgTipoDocConVie, siglaPraticaSuSistUfficioRichiedente, okButton);

		idTipoRichiestaAccessoAttiDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				manageOnDataArrived(response.getData(), idTipoDocDefault, callback);
			}
		});

	}

	protected void manageOnDataArrived(Record[] data, String idTipoDocDefault, ServiceCallback<Record> callback) {
		if (data.length == 1) {
			idTipoDocumento.setValue(data[0].getAttribute("idTipoDocumento"));
			descTipoDocumento.setValue(data[0].getAttribute("descTipoDocumento"));
			flgTipoDocConVie.setValue(data[0].getAttributeAsBoolean("flgTipoDocConVie"));	
			siglaPraticaSuSistUfficioRichiedente.setValue(data[0].getAttribute("siglaPraticaSuSistUfficioRichiedente"));			
			manageOnClick(callback);
		} else {
			if(idTipoDocDefault != null && !"".equals(idTipoDocDefault)) {
				for(int i = 0; i < data.length; i++) {
					if (idTipoDocDefault.equals(data[i].getAttribute("idTipoDocumento"))) {
						idTipoDocumento.setValue(data[i].getAttribute("idTipoDocumento"));
						descTipoDocumento.setValue(data[i].getAttribute("descTipoDocumento"));
						flgTipoDocConVie.setValue(data[i].getAttributeAsBoolean("flgTipoDocConVie"));
						siglaPraticaSuSistUfficioRichiedente.setValue(data[i].getAttribute("siglaPraticaSuSistUfficioRichiedente"));						
						break;
					}
				}
			}
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
