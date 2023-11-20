/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public class MessaggioTaskWindow extends Window {

	private MessaggioTaskWindow instance;
	private DynamicForm form; 	
	private TextAreaItem messaggioItem;
	
	public MessaggioTaskWindow(String esito, final ServiceCallback<Record> callback){
		this(null, false, esito, callback);
	}
	
	public MessaggioTaskWindow(String msgTaskDaPreimpostare, boolean required, String esito, final ServiceCallback<Record> callback){
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);
		if(esito != null && !"".equals(esito)) {
			setTitle("Messaggio di completamento attività con esito " + esito);
		} else {
			setTitle("Messaggio di completamento attività");
		}
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
		form.setNumCols(1);
		form.setColWidths("*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		messaggioItem = new TextAreaItem("messaggio");
		messaggioItem.setHint(required ? "Compila messaggio di completamento attività" : "Compila eventuale messaggio di completamento attività");
		messaggioItem.setShowHintInField(true);
		messaggioItem.setShowTitle(false);
		messaggioItem.setColSpan(1);
		messaggioItem.setHeight(100);
		messaggioItem.setWidth(450);
		messaggioItem.setAlign(Alignment.CENTER);
		messaggioItem.setRequired(required);
		if(msgTaskDaPreimpostare != null && !"".equals(msgTaskDaPreimpostare)) {
			messaggioItem.setValue(msgTaskDaPreimpostare);
		}		
		
		form.setFields(messaggioItem);
		
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
					if(callback != null) {
						callback.execute(formRecord);										
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
		
		draw();
		
	}
	
}
