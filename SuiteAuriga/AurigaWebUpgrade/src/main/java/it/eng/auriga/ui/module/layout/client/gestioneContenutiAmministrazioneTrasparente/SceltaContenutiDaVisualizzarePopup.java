/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.layout.HStack;

public abstract class SceltaContenutiDaVisualizzarePopup extends Window {
	
	private SceltaContenutiDaVisualizzarePopup window;	
	private DynamicForm form; 
	private RadioGroupItem statoContenutiItem;
	
	public SceltaContenutiDaVisualizzarePopup(){
		
		window = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);		
		setTitle("Scelta contenuti da visualizzare");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(100, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		LinkedHashMap<String, String> statoContenutiMap = new LinkedHashMap<String, String>();
		statoContenutiMap.put("solo_da_pubblicare", "solo quelli da pubblicare o in pubblicazione");
		statoContenutiMap.put("anche_fuori_corso", "anche contenuti fuori corso di pubblicazione");
		statoContenutiItem = new RadioGroupItem("statoContenuti");
		statoContenutiItem.setValueMap(statoContenutiMap);
		statoContenutiItem.setVertical(false);
		statoContenutiItem.setWrap(false);
		statoContenutiItem.setShowTitle(false);
		statoContenutiItem.setWidth(120);
		statoContenutiItem.setColSpan(2);
		statoContenutiItem.setStartRow(true);
		statoContenutiItem.setDefaultValue("solo_da_pubblicare");
				
		form.setFields(statoContenutiItem);
		
		addItem(form);	
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					
					final Record formRecord = new Record(form.getValues());
					onClickOkButton(formRecord);
					window.markForDestroy();
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
				window.markForDestroy();	
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
		
		draw();
	}
	
	public abstract void onClickOkButton(Record object);	
}