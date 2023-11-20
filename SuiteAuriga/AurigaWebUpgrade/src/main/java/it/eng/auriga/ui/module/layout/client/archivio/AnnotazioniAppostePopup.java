/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public abstract class AnnotazioniAppostePopup extends Window {
	
	private AnnotazioniAppostePopup window;
	
	private DynamicForm form; 
	private TextAreaItem noteItem;
	
	private Button modificaButton;
	private Button confermaButton;
	private Button annullaButton;
	
	private boolean _isEditable=false;
	private String _noteOld;
	
	public AnnotazioniAppostePopup(String titolo, String noteOld, String causaleAggNoteOld, boolean isEditable){
		
		window = this;
		_isEditable = isEditable;
		_noteOld = noteOld;
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);		
		setTitle(titolo);
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
		
		// Note
		noteItem = new TextAreaItem("note");
		noteItem.setHint("Inserire qui i commenti");
		noteItem.setShowHintInField(true);
		noteItem.setShowTitle(false);
		noteItem.setColSpan(2);
		noteItem.setHeight(100);
		noteItem.setWidth(450);
		noteItem.setAlign(Alignment.CENTER);
		noteItem.setValue(noteOld);
		noteItem.setStartRow(true);
				
		form.setFields(noteItem);
		
		addItem(form);	
				
		confermaButton = new Button("Salva");   
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
		
		Button chiudiButton = new Button("Chiudi");   
		chiudiButton.setIcon("annulla.png");
		chiudiButton.setIconSize(16); 
		chiudiButton.setAutoFit(false);
		chiudiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				window.markForDestroy();	
			}
		});
		
		annullaButton = new Button("Annulla");   
		annullaButton.setIcon("buttons/undo.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				noteItem.setValue(_noteOld);
				setCanEdit(false);	
			}
		});
		
		modificaButton = new Button("Modifica");   
		modificaButton.setIcon("buttons/modify.png");
		modificaButton.setIconSize(16); 
		modificaButton.setAutoFit(false);
		modificaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				setCanEdit(true);
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
        _buttons.addMember(modificaButton);
        _buttons.addMember(annullaButton);
		_buttons.addMember(chiudiButton);	
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
		draw();
	}
	
	public void setCanEdit(boolean canEdit) {
		if (canEdit) {
			noteItem.setCanEdit(true);
			annullaButton.show();
			modificaButton.hide();
			confermaButton.show();
		} else {
			noteItem.setCanEdit(false);
			annullaButton.hide();
			if (_isEditable) {
				modificaButton.show();
			} else {
				modificaButton.hide();
			}
			confermaButton.hide();
		}
	}
	
	public abstract void onClickOkButton(Record object, DSCallback callback);
	
}