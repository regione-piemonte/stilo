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
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.SceltaTipoDocForm;
import it.eng.auriga.ui.module.layout.client.protocollazione.SceltaTipoDocPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

public class SceltaTipoFirmaPopup extends Window {

	private SceltaTipoFirmaPopup instance;
	private SceltaTipoFirmaForm form;
	private ValuesManager vm;

	public SceltaTipoFirmaPopup(String defaultTipoFirma, final ServiceCallback<Record> callback) {

		vm = new ValuesManager();
		instance = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(150);
		setKeepInParentRect(true);
		setTitle("Seleziona tipologia di firma");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setAlign(Alignment.CENTER);
		setLayoutAlign(Alignment.CENTER);

		form = new SceltaTipoFirmaForm(defaultTipoFirma, instance, callback);
		form.setAlign(Alignment.CENTER);
		form.setValuesManager(vm);
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(vm.validate()) {
					manageOnClick(callback);
				}					
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
        
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
		portletLayout.addMember(_buttons);

		addItem(portletLayout);

		setShowTitle(true);
		setHeaderIcon("blank.png");

	}
	
	protected void manageOnClick(final ServiceCallback<Record> callback) {
		if (callback != null) {
			callback.execute(form.getValuesAsRecord());
		}
		markForDestroy();	
	}

}
