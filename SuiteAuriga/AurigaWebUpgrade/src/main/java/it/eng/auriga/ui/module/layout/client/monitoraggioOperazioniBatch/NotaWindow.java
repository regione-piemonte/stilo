/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;

public class NotaWindow extends Window {
	
	private NotaWindow instance;
	private DynamicForm form; 
	private TextAreaItem notaItem;

	
public NotaWindow(String notaOld, final ServiceCallback<Record> callback){
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);		
		setTitle("Inserimento nota");
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
		
		
	
		// Nota
		notaItem = new TextAreaItem("nota");
		notaItem.setHint("Nota");
		notaItem.setShowHintInField(true);
		notaItem.setShowTitle(false);
		notaItem.setColSpan(2);
		notaItem.setHeight(100);
		notaItem.setWidth(450);
		notaItem.setAlign(Alignment.CENTER);
		notaItem.setValue(notaOld);
		notaItem.setStartRow(true);
		
		form.setFields(notaItem);
		
		addItem(form);	
				
		Button confermaButton = new Button("Salva");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
		_buttons.setBackgroundImage(CustomDetail.backgroundColor);
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
		draw();
	}
}
