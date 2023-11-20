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
 * @author dbe4235
 *
 */

public abstract class AssegnatarioTSOVisurePopup extends ModalWindow {
	
	protected AssegnatarioTSOVisurePopup _window;
	protected DynamicForm _form;
	protected SelectItem assegnatarioItem;
	protected HiddenItem descrizioneAssegnatarioItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public DynamicForm getForm() {
		return _form;
	}
	
	public AssegnatarioTSOVisurePopup(Record pListRecord){
		
		super("assegnatario_tso_popup", true);
		
		_window = this;
		
		setTitle("Assegnatario");
		
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
		
		final GWTRestDataSource utenteRicercatoreDS = new GWTRestDataSource("AssegnatariTSODataSource", "idAssegnatario", FieldType.TEXT);

		assegnatarioItem = new SelectItem("idAssegnatario", "Assegnatario") {

			@Override
			public void onOptionClick(Record record) {
				descrizioneAssegnatarioItem.setValue(record.getAttributeAsString("descrizioneAssegnatario"));
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					descrizioneAssegnatarioItem.setValue("");
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				descrizioneAssegnatarioItem.setValue("");
			}
		};
		assegnatarioItem.setShowTitle(true);
		assegnatarioItem.setWidth(300);
		assegnatarioItem.setAlign(Alignment.CENTER);
		assegnatarioItem.setValueField("idAssegnatario");
		assegnatarioItem.setDisplayField("descrizioneAssegnatario");
		assegnatarioItem.setOptionDataSource(utenteRicercatoreDS);
		assegnatarioItem.setAutoFetchData(false);
		assegnatarioItem.setAlwaysFetchMissingValues(true);
		assegnatarioItem.setFetchMissingValues(true);
		assegnatarioItem.setAllowEmptyValue(false);
		assegnatarioItem.setRequired(true);

		descrizioneAssegnatarioItem = new HiddenItem("descrizioneAssegnatario");
		
		_form.setItems(assegnatarioItem, descrizioneAssegnatarioItem);
		
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
		
		setIcon("archivio/assegna.png");
	}
	
	public abstract void onClickOkButton(DSCallback callback);
	
	public void editRecordPerModificaInvio(Record record) {
		_form.editRecord(record);
		markForRedraw();
	}	
}