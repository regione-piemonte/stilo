/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public abstract class WarningMsgDoppiaRegPopup extends ModalWindow {

	private WarningMsgDoppiaRegPopup window;
	private DynamicForm form;	
	private StaticTextItem messagePopupItem;
	private HStack buttons;
	private Button confermaButton;
	private Button annullaButton;
	private Button annullaChiudiMailButton;

	public WarningMsgDoppiaRegPopup(String messagePopupIn) {
		
		super("warningMsgDoppiaRegPopup", true);

		window = this;
		
		//Impostazioni del form
		setFormConfig();
		
		//Creo gli item del form
		createFormItem(messagePopupIn);
				
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth("100%");
		spacer.setStartRow(true);

		//Aggiungo gli item al form
		form.setFields(//spacer, 
				messagePopupItem);
	
		//Creo i due pulsanti
		createButtons();
		
		//Creo la bottoniera
		buttons = createButtonStack();

		setLayout();
	}

	private void setFormConfig() {
		
		setTitle("Attenzione");

		setAutoCenter(true);
		setAlign(Alignment.CENTER);
		setTop(50);
		setHeight(110);
		setWidth(490);
		
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
		form.setNumCols(1);
		form.setColWidths("*");
		form.setCellPadding(5);
		form.setWrapItemTitles(false);
	}
	
	private void createFormItem(String messagePopupIn) {
		messagePopupItem = new StaticTextItem();
		messagePopupItem.setShowTitle(false);
		messagePopupItem.setStartRow(true);
		messagePopupItem.setWrap(true);
		messagePopupItem.setValue(messagePopupIn);
		messagePopupItem.setCanEdit(false);
	}
	
	private void createButtons() {
		
		confermaButton = new Button("Procedi");
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

		
		annullaChiudiMailButton = new Button("Annulla e chiudi e-mail");
		annullaChiudiMailButton.setIcon("annulla.png");
		annullaChiudiMailButton.setIconSize(16);
		annullaChiudiMailButton.setAutoFit(true);
		annullaChiudiMailButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					final Record formRecord = new Record(form.getValues());
					onClickAnnullaChiudiMailButton(formRecord, new DSCallback() {			
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
		
		setIcon("message/warning.png");
	}

	private HStack createButtonStack() {
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaChiudiMailButton);
		_buttons.addMember(annullaButton);		
		return _buttons;
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
	public abstract void onClickAnnullaChiudiMailButton(Record object, DSCallback callback);
}
