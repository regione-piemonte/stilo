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

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class SceltaTipoFolderForm extends DynamicForm {
	
	private SceltaTipoFolderPopup window;
	
	private SelectItem idFolderType;	
	private HiddenItem descFolderType;
	private HiddenItem templateNomeFolder;
	private HiddenItem flgTipoFolderConVie;
	private HiddenItem dictionaryEntrySezione;

	private Record record;
	
	public SceltaTipoFolderForm(final SceltaTipoFolderPopup pWindow, final boolean required, final String idFolderTypeDefault, String descType, Record pRecord, final ServiceCallback<Record> callback) {
				
		window = pWindow;
		
		record = pRecord;
		
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(200,200);
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);
		
		final GWTRestDataSource idFolderTypeDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);
		idFolderTypeDS.addParam("idClassifica", record.getAttribute("idClassifica"));
		idFolderTypeDS.addParam("idFolderApp", record.getAttribute("idFolderApp"));
		idFolderTypeDS.addParam("idFolderType", record.getAttribute("idFolderType"));
		
		// lista tipi fascicoli
		idFolderType = new SelectItem("idFolderType", I18NUtil.getMessages().archivio_list_tipoField_title())
		{
			@Override
			public void onOptionClick(Record record) {
				
				idFolderType.setValue(record.getAttribute("idFolderType"));	
				descFolderType.setValue(record.getAttribute("descFolderType"));
				templateNomeFolder.setValue(record.getAttribute("templateNomeFolder"));
				flgTipoFolderConVie.setValue(record.getAttributeAsBoolean("flgTipoFolderConVie"));
				dictionaryEntrySezione.setValue(record.getAttribute("dictionaryEntrySezione"));
			}
		};
		
		idFolderType.setShowTitle(false);
		idFolderType.setWidth(350);
		idFolderType.setPickListWidth(450);
		idFolderType.setColSpan(2);
		idFolderType.setAlign(Alignment.CENTER);	
		idFolderType.setValueField("idFolderType");
		idFolderType.setDisplayField("descFolderType");
		if(descType!=null) {
			idFolderType.setValue(descType);
		}
		idFolderType.setOptionDataSource(idFolderTypeDS);  	
		if(required) {
			idFolderType.setRequired(true);
		} else {
			idFolderType.setAllowEmptyValue(true);
		}
		idFolderType.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// se non ci sono tipi folder o ce ne uno solo lo seleziono e chiudo la popup
				manageOnDataArrived(event.getData().toArray(), required, idFolderTypeDefault, callback);
			}
		});
			
		descFolderType = new HiddenItem("descFolderType");
		templateNomeFolder = new HiddenItem("templateNomeFolder");
		flgTipoFolderConVie = new HiddenItem("flgTipoFolderConVie");
		dictionaryEntrySezione = new HiddenItem("dictionaryEntrySezione");
		
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
		
		setFields(idFolderType, descFolderType, templateNomeFolder, flgTipoFolderConVie, dictionaryEntrySezione, okButton);
		
		idFolderTypeDS.fetchData(null, new DSCallback() {			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				manageOnDataArrived(response.getData(), required, idFolderTypeDefault, callback);
			}
		});
				
	}
	
	protected void manageOnDataArrived(Record[] data, boolean required, String idFolderTypeDefault, ServiceCallback<Record> callback) {
		if (data.length == 0 || (required && data.length == 1)) {
			if (required && data.length == 1) {	
				idFolderType.setValue(data[0].getAttribute("idFolderType"));	
				descFolderType.setValue(data[0].getAttribute("descFolderType"));
				templateNomeFolder.setValue(data[0].getAttribute("templateNomeFolder"));			
				flgTipoFolderConVie.setValue(data[0].getAttributeAsBoolean("flgTipoFolderConVie"));			
				dictionaryEntrySezione.setValue(data[0].getAttribute("dictionaryEntrySezione"));							
			}
			manageOnClick(callback);
		} else {
			if(idFolderTypeDefault != null && !"".equals(idFolderTypeDefault)) {
				for(int i = 0; i < data.length; i++) {
					if (idFolderTypeDefault.equals(data[i].getAttribute("idFolderType"))) {
						idFolderType.setValue(data[i].getAttribute("idFolderType"));	
						descFolderType.setValue(data[i].getAttribute("descFolderType"));
						templateNomeFolder.setValue(data[i].getAttribute("templateNomeFolder"));						
						flgTipoFolderConVie.setValue(data[i].getAttributeAsBoolean("flgTipoFolderConVie"));	
						dictionaryEntrySezione.setValue(data[i].getAttribute("dictionaryEntrySezione"));							
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
		if(callback != null) {
			record.setAttribute("idFolderType", getValue("idFolderType"));
			record.setAttribute("descFolderType", getValue("descFolderType"));
			record.setAttribute("templateNomeFolder", getValue("templateNomeFolder"));			
			record.setAttribute("flgTipoFolderConVie", getValue("flgTipoFolderConVie"));	
			record.setAttribute("dictionaryEntrySezione", getValue("dictionaryEntrySezione"));	
			callback.execute(record);		
		}
		window.markForDestroy();
		
	}
	
}	
