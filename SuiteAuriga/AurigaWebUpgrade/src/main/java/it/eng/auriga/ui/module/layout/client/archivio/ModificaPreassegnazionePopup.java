/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class ModificaPreassegnazionePopup extends ModalWindow {
	
	protected ModificaPreassegnazionePopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected String flgUdFolder;
	
	protected AssegnazioneItem assegnazioneItem;
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public ModificaPreassegnazionePopup(String pFlgUdFolder){
		
		super("modifica_preassegnazione", true);
		
		_window = this;
		
		flgUdFolder = pFlgUdFolder;
		
		setTitle("Nuova assegnazione");
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);				
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(5);
		_form.setColWidths(120,"*","*","*","*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);	
//		_form.setCellBorder(1);
		
		assegnazioneItem = new AssegnazioneItem();
		assegnazioneItem.setName("listaAssegnazioni");
		assegnazioneItem.setTitle("Destinatario/i");	
		assegnazioneItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		assegnazioneItem.setCanEdit(true);
		assegnazioneItem.setColSpan(5);		
		assegnazioneItem.setFlgUdFolder(flgUdFolder);
		assegnazioneItem.setAttribute("obbligatorio", true);
					
		_form.setFields(new FormItem[]{assegnazioneItem});			
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(assegnazioneItem.validate()) {
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
		
		layout.addMember(_form);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("archivio/modificaPreassegnazione.png");

	}

	public abstract void onClickOkButton(DSCallback callback);
	
}
