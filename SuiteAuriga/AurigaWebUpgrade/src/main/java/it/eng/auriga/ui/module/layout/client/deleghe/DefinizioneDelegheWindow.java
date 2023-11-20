/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DefinizioneDelegheWindow extends ModalWindow {

	protected DefinizioneDelegheWindow _window;
	protected DynamicForm _form;	

	private DelegheVsUtentiItem delegheVsUtentiItem;

	public DefinizioneDelegheWindow(){
		
		super("definizione_deleghe", true);
		
		_window = this;
		
		setTitle("Deleghe vs altri utenti");
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
				
		_form = new DynamicForm();
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(120,1,1,1,1,1,"*","*");		
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);	
				
		delegheVsUtentiItem = new DelegheVsUtentiItem();
		delegheVsUtentiItem.setName("listaDelegheVsUtenti");
		delegheVsUtentiItem.setShowTitle(false);			
		delegheVsUtentiItem.setCanEdit(true);
		delegheVsUtentiItem.setColSpan(4);		
		delegheVsUtentiItem.setAttribute("obbligatorio", true);
		
		_form.setFields(new FormItem[]{delegheVsUtentiItem});
				
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaDelegheVsUtentiDataSource");
		lGwtRestService.call(new Record(), new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				_form.editRecord(object);
			}
		});
		
		Button salvaButton = new Button("Salva");
		salvaButton.setIcon("buttons/save.png");
		salvaButton.setIconSize(16); 
		salvaButton.setAutoFit(false);
		salvaButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				manageSalvaClick();
			}
		});
				
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(salvaButton);
		 		
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
		
		layout.addMember(_form);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("menu/deleghe_definizione.png");
	}	

	protected void manageSalvaClick() {
		if(delegheVsUtentiItem.validate()) {
			final Record lRecord = _form.getValuesAsRecord();
			if (lRecord!=null){
				final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaDelegheVsUtentiDataSource");
				lGWTRestDataSource.executecustom("salva", lRecord, new DSCallback() {			
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {					
						if (response.getStatus() == DSResponse.STATUS_SUCCESS){
							Layout.addMessage(new MessageBean("Le impostazioni di delega verso gli altri utenti sono state salvate correttamente", "", MessageType.INFO));
							_window.markForDestroy();
						} else {
							Layout.addMessage(new MessageBean("Si è verificato un errore durante il salvataggio delle impostazioni di delega", "", MessageType.ERROR));
						}
					}
				});				
			}
		}
	}
	
}
