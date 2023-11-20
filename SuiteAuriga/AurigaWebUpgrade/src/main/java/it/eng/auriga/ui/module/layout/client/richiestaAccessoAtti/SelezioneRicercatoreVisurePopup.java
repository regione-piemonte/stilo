/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public abstract class SelezioneRicercatoreVisurePopup extends ModalWindow {
	
	protected SelezioneRicercatoreVisurePopup _window;
	protected DynamicForm _form;
	protected SelectItem utenteRicercatoreItem;
	protected HiddenItem cognomeNomeRicercatoreItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public DynamicForm getForm() {
		return _form;
	}
	
	public SelezioneRicercatoreVisurePopup(Record pListRecord){
		
		super("selezione_ricercatore_visure", true);
		
		_window = this;
		
		setTitle("Seleziona ricercatore");
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);	
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(5);
		_form.setColWidths(1,1,1,1,"*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);	
		
		final GWTRestDataSource utenteRicercatoreDS = new GWTRestDataSource("RicercatoriVisureDataSource", "idUtenteRicercatore", FieldType.TEXT);

		utenteRicercatoreItem = new SelectItem("idUtenteRicercatore", "Ricercatore incaricato") {

			@Override
			public void onOptionClick(Record record) {
				cognomeNomeRicercatoreItem.setValue(record.getAttributeAsString("cognomeNomeRicercatore"));
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					cognomeNomeRicercatoreItem.setValue("");
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				cognomeNomeRicercatoreItem.setValue("");
			};
		};
		utenteRicercatoreItem.setShowTitle(true);
		utenteRicercatoreItem.setWidth(300);
		//utenteRicercatoreItem.setColSpan(4);
		utenteRicercatoreItem.setAlign(Alignment.CENTER);
		utenteRicercatoreItem.setValueField("idUtenteRicercatore");
		utenteRicercatoreItem.setDisplayField("cognomeNomeRicercatore");
		utenteRicercatoreItem.setOptionDataSource(utenteRicercatoreDS);
		utenteRicercatoreItem.setAutoFetchData(false);
		utenteRicercatoreItem.setAlwaysFetchMissingValues(true);
		utenteRicercatoreItem.setFetchMissingValues(true);
		utenteRicercatoreItem.setAllowEmptyValue(false);
		utenteRicercatoreItem.setRequired(true);

		cognomeNomeRicercatoreItem = new HiddenItem("cognomeNomeRicercatore");
		
		_form.setItems(utenteRicercatoreItem, cognomeNomeRicercatoreItem);
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {	
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(_form.validate()) {
					onClickOkButton(new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							_window.markForDestroy();
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
		
		setIcon("protocollazione/flagSoggettiGruppo/O2.png");
	}
	
	public abstract void onClickOkButton(DSCallback callback);
	
	public void editRecordPerModificaInvio(Record record) {
		_form.editRecord(record);
		markForRedraw();
	}	
}