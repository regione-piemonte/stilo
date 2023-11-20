/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public abstract class DestUOProtocollazioneWindow extends ModalWindow {
	
	protected DestUOProtocollazioneWindow _window;
	
	protected ValuesManager vm;
	protected DynamicForm formMessaggio;
	protected TextAreaItem messaggioInvioItem;
	
	
	public DestUOProtocollazioneWindow() {
		super("dest_uo_protocollazione", true);

		_window = this;
		
		vm = new ValuesManager();
		
		setTitle("Compila dati invio al protocollo");
		
		formMessaggio = new DynamicForm();
		formMessaggio.setValuesManager(vm);
		formMessaggio.setKeepInParentRect(true);
		formMessaggio.setWidth100();
		formMessaggio.setHeight100();
		formMessaggio.setNumCols(2);
		formMessaggio.setColWidths(1, "*");
		formMessaggio.setCellPadding(5);
		formMessaggio.setWrapItemTitles(false);
		
		messaggioInvioItem = new TextAreaItem("messaggioInvio", "Messaggio");
		messaggioInvioItem.setShowTitle(false);
		messaggioInvioItem.setStartRow(true);
		messaggioInvioItem.setRequired(true);
		messaggioInvioItem.setLength(4000);
		messaggioInvioItem.setHeight(60);
		messaggioInvioItem.setColSpan(5);		
		messaggioInvioItem.setWidth(780);
		
		formMessaggio.setFields(messaggioInvioItem);
		
		Button confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
					@Override
					public void execute() {
						
						if(formMessaggio != null && formMessaggio.validate()) {
							final Record record = new Record(vm.getValues());
							onClickOkButton(record, new DSCallback() {
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									_window.markForDestroy();
								}
							});	
						}
					}
				});					
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
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
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
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		DetailSection detailSectionMessaggio = new DetailSection("Messaggio", true, true, true, formMessaggio);
	
		layout.addMember(detailSectionMessaggio);

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/assegna.png");
	}
	
	public abstract void onClickOkButton(Record record, DSCallback callback);
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {}
		}
		super.onDestroy();
	}

}