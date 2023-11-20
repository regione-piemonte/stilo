/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;


public class MotiviOperazionePopup extends ModalWindow {

	private MotiviOperazionePopup window;
	
	private DynamicForm form;
	
	private TextAreaItem motivazioneItem;

	private HStack buttons;
	private Button confermaButton;
	private Button annullaButton;
	private ServiceCallback<Record> callback;

	
	/**
	 * 
	 * @param record
	 * @param callbackParam
	 */
	public MotiviOperazionePopup(Record record, ServiceCallback<Record> callbackParam) {
		
		super("motiviOperazionePopup", true);

		window = this;
		
		callback = callbackParam;

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
		createButtons(record);
		
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
	}

	private void setFormConfig() {
		
		setTitle("Compila motivi operazione");

		setAutoCenter(true);
		setAlign(Alignment.CENTER);
		setTop(50);
		
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

	private void createButtons(final Record record) {
		
		
		confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Prelevo la motivazione inserita
				String motivazioneText = motivazioneItem.getValueAsString();				
				markForDestroy();
				manageClickOnSave(record, motivazioneText);
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
		
		motivazioneItem = new TextAreaItem("Motivazione", "Motivo");
		motivazioneItem.setStartRow(true);
		motivazioneItem.setEndRow(true);
		motivazioneItem.setLength(4000);
		motivazioneItem.setColSpan(4);
		motivazioneItem.setHeight("100%");
		motivazioneItem.setWidth("100%");
				
	}
	
	
	
	
	private void manageClickOnSave( Record record, final String motivazioneText){
		
		record.setAttribute("messaggio", motivazioneText);
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AbilitaAnnullamentoInvioDataSource");
		lGwtRestService.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				if(callback != null){
					callback.execute(object);
				}
				
			}
		});
		
	}
	
	
}
