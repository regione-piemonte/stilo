/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;


public abstract class RigettoRichiestaPopup extends ModalWindow {

	private RigettoRichiestaPopup window;
	
	private DynamicForm form;
	
	private TextAreaItem motivazioneItem;

	private HStack buttons;
	private Button confermaButton;
	private Button annullaButton;

	
	public RigettoRichiestaPopup() {
		
		super("rigettoRichiestaPopup", true);

		window = this;
		
		//Impostazioni del form
		setFormConfig();
		
		//Creo gli item del form
		createTextItem();
		
		
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth("100%");
		spacer.setStartRow(true);

		//Aggiungo gli item al form
		form.setFields(spacer, motivazioneItem);
		form.setTitleOrientation(TitleOrientation.TOP);
	
		//Creo i due pulsanti
		createButtons();
		
		//Creo la bottoniera
		buttons = createButtonStack();

		setLayout();
	}

	
	public DynamicForm getForm() {
		return form;
	}
	
	
	private void setLayout(){
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(form);

		portletLayout.addMember(layout);
		portletLayout.addMember(buttons);

		setBody(portletLayout);
		
		setIcon("blank.png");
	}

	private void setFormConfig() {
		
		setTitle("Motivazioni rigetto");

		setAutoCenter(true);
		setAlign(Alignment.CENTER);
		setTop(50);
		setHeight(250);
		setWidth(550);
		
		//Per la rimozione degli elementi dal pulsante con la rotellina in alto a destra
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});

		form = new DynamicForm();
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(4);
		form.setColWidths("*","*","*","*");
		form.setCellPadding(5);
		form.setWrapItemTitles(false);
	}

	private void createButtons() {
		
		
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
				//Chiudo la finestra
				markForDestroy();
			}
		});
		
	}

	private HStack createButtonStack() {
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);
		
		return _buttons;
	}
	

	private void createTextItem() {
		
		motivazioneItem = new TextAreaItem("motivazione", "Motivo");
		motivazioneItem.setAlign(Alignment.CENTER);	
		motivazioneItem.setLength(4000);
//		motivazioneItem.setColSpan(4);
		motivazioneItem.setHeight(82);
		motivazioneItem.setWidth(480);
		motivazioneItem.setRequired(true);
		motivazioneItem.setAttribute("obbligatorio", true);
		CustomValidator lRequiredValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				String valueStr = (String) value;
				return valueStr != null && !"".equals(valueStr.trim());
			}
		};
		lRequiredValidator.setErrorMessage("Campo obbligatorio");		
		motivazioneItem.setValidators(lRequiredValidator);
				
	}
	
	@Override
	public void manageOnCloseClick() {
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}

	public abstract void onClickOkButton(Record object, DSCallback callback);
}
