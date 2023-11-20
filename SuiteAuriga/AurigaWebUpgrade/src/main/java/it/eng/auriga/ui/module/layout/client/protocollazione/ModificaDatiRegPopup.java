/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public abstract class ModificaDatiRegPopup extends Window {
	
	protected ModificaDatiRegPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected TextAreaItem motivoItem;	
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public ModificaDatiRegPopup(String motivoVariazione){
		
		_window = this;
		
		setTitle("Conferma variazione dati registrazione");
		
		setShowTitle(true);		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(850);
		setHeight(125);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);				
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(120,1,1,1,1,1,"*","*");		
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);		
		
		motivoItem = new TextAreaItem("motivo", "Motivo variazione dati");
		motivoItem.setStartRow(true);
		motivoItem.setLength(4000);
		motivoItem.setHeight(40);
		motivoItem.setColSpan(8);
		motivoItem.setWidth(650);
		motivoItem.setRequired(true);
		CustomValidator lRequiredValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				String valueStr = (String) value;
				return valueStr != null && !"".equals(valueStr.trim());
			}
		};
		lRequiredValidator.setErrorMessage("Campo obbligatorio");		
		motivoItem.setValidators(lRequiredValidator);
		if(motivoVariazione != null && !"".equals(motivoVariazione)) {
			motivoItem.setDefaultValue(motivoVariazione);
		}
		
		_form.setFields(new FormItem[]{motivoItem});
		
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
				onClickAnnullaButton();
			}
		});
		
		HStack _buttons = new HStack(5);
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
		VLayout mainLayout = new VLayout();  
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		mainLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(_form);
			
		mainLayout.addMember(layout);		
		mainLayout.addMember(_buttons);
		
		addItem(mainLayout);
		
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);	
		
		addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				_window.markForDestroy();
				onClickAnnullaButton();
			}
		});
		
	}

	public abstract void onClickOkButton(DSCallback callback);
	
	public void onClickAnnullaButton() {
		
	}
	
}
