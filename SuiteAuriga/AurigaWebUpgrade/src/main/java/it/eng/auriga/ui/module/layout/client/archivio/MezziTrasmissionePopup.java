/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class MezziTrasmissionePopup extends ModalWindow {
	
	protected MezziTrasmissionePopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}
	
	protected SelectItem mezzoTrasmissioneItem;
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public MezziTrasmissionePopup(Record pListRecord){
		
		super("mezzitrasmissione", true);
		
		_window = this;
				
		setTitle("Elenco dei mezzi di trasmissione filtrati");
		
		setAutoCenter(true);
		setHeight(150);
		setWidth(500);	
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(2);
		_form.setColWidths(120,"*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);	
		//_form.setCellBorder(1);
		
		
		
		GWTRestDataSource mezziTrasmissioneDS = new GWTRestDataSource("LoadComboMezziTrasmissioneFilterDataSource", "key", FieldType.TEXT);
		mezzoTrasmissioneItem = new SelectItem("listaMezziTrasmissione", I18NUtil.getMessages().protocollazione_detail_mezzoTrasmissioneItem_title());
		mezzoTrasmissioneItem.setName("listaMezziTrasmissione");
		mezzoTrasmissioneItem.setMultiple(true);
		
		mezzoTrasmissioneItem.setOptionDataSource(mezziTrasmissioneDS);  
		mezzoTrasmissioneItem.setAutoFetchData(true);
		mezzoTrasmissioneItem.setDisplayField("value");
		mezzoTrasmissioneItem.setValueField("key");			
		mezzoTrasmissioneItem.setWrapTitle(false);
		mezzoTrasmissioneItem.setStartRow(true);
		mezzoTrasmissioneItem.setAllowEmptyValue(true);
		mezzoTrasmissioneItem.setClearable(false);
		mezzoTrasmissioneItem.setRedrawOnChange(true);
		mezzoTrasmissioneItem.setWidth(300);   		
        mezzoTrasmissioneItem.setColSpan(1);

		_form.setFields(new FormItem[]{mezzoTrasmissioneItem});
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				//final RecordList records = new RecordList();
				
				//for(int i = 0; i < mezzoTrasmissioneItem.getSelectedRecords().length; i++) 
					//records.add(mezzoTrasmissioneItem.getSelectedRecords()[i]);
				
				//if(mezzoTrasmissioneItem.validate()) {
					
				//if (!records.isEmpty()){
					//_window.markForDestroy();	
					onClickOkButton(new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							_window.markForDestroy();
						}
					});	
					//}
				//else{
				//	SC.warn("E' obbligatorio selezionare almeno un mezzo di trasmissione");
				//}
						
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
		
		setIcon("protocollazione/stampaDistintaSpedizione.png");
	}

	public abstract void onClickOkButton(DSCallback callback);
	
	
}
