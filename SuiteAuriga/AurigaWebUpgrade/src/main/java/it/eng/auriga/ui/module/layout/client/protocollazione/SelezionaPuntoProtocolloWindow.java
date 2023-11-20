/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

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

public class SelezionaPuntoProtocolloWindow extends Window {

	private SelezionaPuntoProtocolloWindow instance;
	private SelectItem puntoProtocolloItem;
	private DynamicForm form; 
	
	public SelezionaPuntoProtocolloWindow(Record[] data, final ServiceCallback<String> callback){
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);		
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Seleziona il punto di protocollo a cui inviare il documento");
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
		
		puntoProtocolloItem = new SelectItem("puntoProtocollo");		
		puntoProtocolloItem.setShowTitle(false);
		puntoProtocolloItem.setColSpan(2);
		puntoProtocolloItem.setAlign(Alignment.CENTER);
		puntoProtocolloItem.setRequired(true);
		puntoProtocolloItem.setValueField("key");
		puntoProtocolloItem.setDisplayField("value");
		
		LinkedHashMap<String, String> puntiProtocolloValueMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < data.length; i++) {
			puntiProtocolloValueMap.put(data[i].getAttribute("key"), data[i].getAttribute("value"));
		}		
		puntoProtocolloItem.setValueMap(puntiProtocolloValueMap);
		
		form.setFields(puntoProtocolloItem);
				
		addItem(form);	
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					if(callback != null) {
						callback.execute(puntoProtocolloItem.getValueAsString());
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
