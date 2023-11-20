/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.FolderCustomItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class OrganizzaPopup extends ModalWindow {
		
	protected OrganizzaPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected HiddenItem livelloRiservatezzaHiddenItem;
	protected FolderCustomItem folderCustomItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public OrganizzaPopup(Record pListRecord){
		
		super("organizza", true);
		
		_window = this;
		
		setTitle("Compila dati cartella/e");
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(120,1,1,1,1,1,"*","*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);		
		
		livelloRiservatezzaHiddenItem = new HiddenItem("livelloRiservatezza");
		
		folderCustomItem = new FolderCustomItem();	
		folderCustomItem.setName("listaFolderCustom");
		folderCustomItem.setShowTitle(false);
		folderCustomItem.setCanEdit(true);
		folderCustomItem.setAttribute("obbligatorio", true);
		if(pListRecord == null) {
			folderCustomItem.setFolderizzazioneMassiva(true);
		}
		
		_form.setFields(new FormItem[]{livelloRiservatezzaHiddenItem, folderCustomItem});
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(folderCustomItem.validate()) {	
					checkPropagaRiservatezzaCartelleAContenuti(new DSCallback() {	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							onClickOkButton(new DSCallback() {			
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									_window.markForDestroy();
								}
							});
						}																				
					});
				}
			}
		});
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				_window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		 		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(_form);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("archivio/organizza.png");
		
		if(pListRecord != null) {
			GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", pListRecord.getAttribute("idUdFolder"));
			lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record lDetailRecord = response.getData()[0];
					_form.editRecord(lDetailRecord);	
					_window.show();
				}
			});
		} else {		 
			_window.show();
		}
	}
		
	public void checkPropagaRiservatezzaCartelleAContenuti(final DSCallback callback) {
		RecordList lRecordList = _form.getValueAsRecordList("listaFolderCustom");
		String estremiCartelleRiservate = "";
		boolean cartelleRiservate = false;
		if(lRecordList != null && lRecordList.getLength() > 0) { 
			for(int i = 0; i < lRecordList.getLength(); i++) {
				Record lRecord = lRecordList.get(i);
				if(lRecord.getAttribute("livelloRiservatezza") != null && "1".equals(lRecord.getAttribute("livelloRiservatezza"))) {
					if(!"".equals(estremiCartelleRiservate)) {
						estremiCartelleRiservate += ", ";
					}
					estremiCartelleRiservate += getEstremiFolderCustom(lRecord);		
					cartelleRiservate = true;
				}
			}
		}		
		if(cartelleRiservate && (livelloRiservatezzaHiddenItem.getValue() == null || "".equals(livelloRiservatezzaHiddenItem.getValue()))) {
			SC.ask("Cartella/e " + estremiCartelleRiservate + " riservate: vuoi estendere la riservatezza al documento?", new BooleanCallback() {								
				@Override
				public void execute(Boolean value) {
					
					if(value == null) return;
					if(value) {
						livelloRiservatezzaHiddenItem.setValue("1");
					}	
					callback.execute(new DSResponse(), null, new DSRequest());					
				}
			});
		} else {
			callback.execute(new DSResponse(), null, new DSRequest());				
		}
	}
	
	public String getEstremiFolderCustom(Record record) {
		String estremi = "";
		if(record.getAttributeAsString("path") != null && !"".equals(record.getAttributeAsString("path"))) {
			estremi += record.getAttributeAsString("path") + " ";
		}			
		return estremi;
	}	

	public abstract void onClickOkButton(DSCallback callback);
	
}
