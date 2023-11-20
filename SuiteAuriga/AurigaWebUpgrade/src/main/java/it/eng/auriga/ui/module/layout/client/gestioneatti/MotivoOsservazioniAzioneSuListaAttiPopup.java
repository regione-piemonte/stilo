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
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class MotivoOsservazioniAzioneSuListaAttiPopup extends ModalWindow {

	private MotivoOsservazioniAzioneSuListaAttiPopup window;
	
	private DynamicForm form;
	
	private TextAreaItem motivoOsservazioniItem;

	private HStack buttons;
	private Button confermaButton;
	private Button annullaButton;
	
	/**
	 * 
	 * @param record
	 * @param callback
	 */
	public MotivoOsservazioniAzioneSuListaAttiPopup(String title, final Record record, final ServiceCallback<Record> callback) {
		this(title, false, record, callback);
	}
	
	public MotivoOsservazioniAzioneSuListaAttiPopup(String title, boolean required, final Record record, final ServiceCallback<Record> callback) {
		
		super("motiviAzioneSuListaAttiPopup", true);

		window = this;
		
		setTitle(title);

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
		
		motivoOsservazioniItem = new TextAreaItem("motivoOsservazioni");
		motivoOsservazioniItem.setShowTitle(false);
		motivoOsservazioniItem.setStartRow(true);
		motivoOsservazioniItem.setEndRow(true);
		motivoOsservazioniItem.setLength(4000);
		motivoOsservazioniItem.setColSpan(4);
		motivoOsservazioniItem.setHeight("100%");
		motivoOsservazioniItem.setWidth("100%");
		motivoOsservazioniItem.setRequired(required);
		
		form.setFields(motivoOsservazioniItem);
		
		confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					String motivoOsservazioni = motivoOsservazioniItem.getValueAsString();
					record.setAttribute("motivoOsservazioni", motivoOsservazioni);
					if(callback != null){
						callback.execute(record);
					}
					markForDestroy();		
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
				markForDestroy();
			}
		});
		
		buttons = new HStack(5);
		buttons.setHeight(30);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(confermaButton);
		buttons.addMember(annullaButton);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(form);

		portletLayout.addMember(layout);
		portletLayout.addMember(buttons);

		setBody(portletLayout);
	}

	
	public DynamicForm getForm() {
		return form;
	}

}
