/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class ListaAnagraficaSicraPopup extends ModalWindow {
	
	protected ListaAnagraficaSicraPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected ListaAnagraficaSicraItem listaAnagraficaSicraItem;
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public ListaAnagraficaSicraPopup(){
		
		super("lista_anagrafica_sicra_popup", true);
		
		_window = this;
		
		setTitle(getWindowTitle());  	
		setAutoCenter(true);
		setWidth(1000);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths("*");		
		_form.setCellPadding(2);
		_form.setWrapItemTitles(false);		
		
		listaAnagraficaSicraItem = new ListaAnagraficaSicraItem("listaAnagraficaSicra") {
			@Override
			void onRecordSelected(Record record) {
				_window.markForDestroy();	
				manageRecordSelection(record);
			}
		};
		listaAnagraficaSicraItem.setStartRow(true);
		
		_form.setFields(listaAnagraficaSicraItem);
		
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
				
		setIcon("menu/soggetti.png");
		
	}
	
	public String getWindowTitle() {
		return "Seleziona una voce della rubrica";
	}
	
	public void initContent(Record record) {
		_form.setValue("listaAnagraficaSicra", record.getAttributeAsRecordArray("listaAnagraficaSicra"));
		listaAnagraficaSicraItem.sort("nroEmendamento");
	}

	public abstract void manageRecordSelection(Record record);
	
}
