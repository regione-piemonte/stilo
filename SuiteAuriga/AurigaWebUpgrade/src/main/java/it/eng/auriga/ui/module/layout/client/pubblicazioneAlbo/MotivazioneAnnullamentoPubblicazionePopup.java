/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class MotivazioneAnnullamentoPubblicazionePopup extends ModalWindow {
	
	protected MotivazioneAnnullamentoPubblicazionePopup window;
	
	// DynamicForm
	protected DynamicForm form;
	
	// HStack
	protected HStack _buttons;
	
	// TextAreaItem
	protected TextAreaItem motivazioneItem;
	
	// Button
	protected Button confermaButton;
	protected Button annullaButton;
		
	public MotivazioneAnnullamentoPubblicazionePopup(String title) {
		
		super("richiesta_annullamento_pubblicazione_popup", true);
		window = this;
        setTitle(title);
		setIcon("buttons/annullamento_pubblicazione.png");
		setAutoCenter(true);
		setAlign(Alignment.CENTER);
		setTop(50);
		setHeight(220);
		setWidth(530);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);	
		createForm();		
		createButton();
		addItem(form);
		addItem(_buttons);
		show();		
	}
	
	protected void createForm(){
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths("*", "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		// Motivazione
		motivazioneItem = new TextAreaItem("motivoAnnPubblicazione");		
		motivazioneItem.setShowHintInField(true);
		motivazioneItem.setShowTitle(false);
		motivazioneItem.setColSpan(2);
		motivazioneItem.setWidth(500);
		motivazioneItem.setAlign(Alignment.LEFT);
		motivazioneItem.setRequired(true);
		motivazioneItem.setStartRow(true);
				
		form.setFields(motivazioneItem);
	}
	
	protected void createButton(){
		confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					final Record formRecord = new Record(form.getValues());
					onClickOkButton(formRecord, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {							
							window.markForDestroy();
						}
					});					
				}
			}
		});		
		annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				window.markForDestroy();	
			}
		});
		_buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(false);
	}
	
	public abstract void onClickOkButton(Record object, DSCallback callback);
}