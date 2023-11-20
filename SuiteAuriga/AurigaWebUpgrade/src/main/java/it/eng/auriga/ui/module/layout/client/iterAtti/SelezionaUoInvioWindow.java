/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class SelezionaUoInvioWindow extends Window {

	private SelezionaUoInvioWindow instance;
	private SelectItem assegnatarioItem;
	private DynamicForm form; 
	
	public SelezionaUoInvioWindow(final ServiceCallback<Record> callback){
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);		
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Scegli UO del Dirigente a cui inviare per validazione");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();
		form.setKeepInParentRect(true);
		form.setNumCols(2);
		form.setColWidths(200,200);
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setTop(50);
		
		GWTRestDataSource assegnatarioDS = new GWTRestDataSource("AltreStrutturePropostaAttoDataSource", "key", FieldType.TEXT);
		
		assegnatarioItem = new SelectItem("assegnatario");		
		assegnatarioItem.setShowTitle(false);
		assegnatarioItem.setColSpan(2);
		assegnatarioItem.setAlign(Alignment.CENTER);
		assegnatarioItem.setRequired(true);
		assegnatarioItem.setValueField("key");
		assegnatarioItem.setDisplayField("value");
		assegnatarioItem.setOptionDataSource(assegnatarioDS);
		
		form.setFields(assegnatarioItem);
				
		addItem(form);	
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					
					Record values = new Record(form.getValues());					
					if(callback != null) {
						callback.execute(values);
					}											
					markForDestroy();
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
				
				markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
	}
	
}
