/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class EventoAMCPopup extends ModalWindow {

	private EventoAMCPopup window;
	
	private DynamicForm form;
	
	private SelectItem eventoAMCItem;

	private HStack buttons;
	private Button confermaButton;
	private Button annullaButton;
	
	/**
	 * 
	 * @param record
	 * @param callback
	 */
	public EventoAMCPopup(String title, final Record record, final ServiceCallback<Record> callback) {
		
		super("eventoAMCPopup", true);

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
		form.setNumCols(2);
		form.setCellPadding(5);
		form.setWrapItemTitles(false);
		
		GWTRestDataSource eventoAMCDS = new GWTRestDataSource("EventoAMCDataSource", "key", FieldType.TEXT);
		
		eventoAMCItem = new SelectItem("eventoAMC");
		eventoAMCItem.setShowTitle(false);
		eventoAMCItem.setStartRow(true);
		eventoAMCItem.setOptionDataSource(eventoAMCDS);
		eventoAMCItem.setAutoFetchData(true);
		eventoAMCItem.setDisplayField("value");
		eventoAMCItem.setValueField("key");
		eventoAMCItem.setWidth(344);
		eventoAMCItem.setWrapTitle(false);
		eventoAMCItem.setColSpan(1);
		eventoAMCItem.setAllowEmptyValue(true);
		eventoAMCItem.setRequired(true);
		
		form.setFields(eventoAMCItem);
		
		confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					String eventoAMC = eventoAMCItem.getValueAsString();
					record.setAttribute("eventoAMC", eventoAMC);
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
		
		setWidth(350);
		setHeight(100);
	}

	
	public DynamicForm getForm() {
		return form;
	}

}
