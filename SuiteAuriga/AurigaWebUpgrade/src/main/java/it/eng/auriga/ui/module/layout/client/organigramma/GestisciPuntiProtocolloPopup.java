/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class GestisciPuntiProtocolloPopup extends ModalWindow {
	
	protected GestisciPuntiProtocolloPopup _window;
	protected DynamicForm associaPuntiProtocolloForm;
	// protected DetailSection gestisciPuntiProtocolloDetailSection;
	
	public DynamicForm getForm() {
		return associaPuntiProtocolloForm;
	}
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	protected CheckboxItem flgPuntoDiProtocollo;
	protected PuntoProtocolloItem puntoProtocolloItem;
	
	public GestisciPuntiProtocolloPopup(Record pListRecord, String title){
		
		super("gestisci_punto_protocollo", true);
		
		_window = this;
	
		setTitle(title);
		
		setAutoCenter(true);
		
		setHeight(300);
		setWidth(700);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setStartRow(true);
		spacerItem.setColSpan(1);
		
		puntoProtocolloItem = new PuntoProtocolloItem();
		puntoProtocolloItem.setStartRow(true);
		puntoProtocolloItem.setName("listaPuntiProtocollo");
		puntoProtocolloItem.setShowTitle(false);
		puntoProtocolloItem.setAttribute("obbligatorio", true);
		
		associaPuntiProtocolloForm = new DynamicForm();													
		associaPuntiProtocolloForm.setKeepInParentRect(true);
		associaPuntiProtocolloForm.setNumCols(5);
		associaPuntiProtocolloForm.setColWidths(120,"*","*","*","*");		
		associaPuntiProtocolloForm.setCellPadding(5);		
		associaPuntiProtocolloForm.setWrapItemTitles(false);
		associaPuntiProtocolloForm.setWidth("100%");
		associaPuntiProtocolloForm.setHeight("5");
		associaPuntiProtocolloForm.setPadding(5);
		associaPuntiProtocolloForm.setItems(spacerItem, puntoProtocolloItem);
		
		// gestisciPuntiProtocolloDetailSection = new DetailSection(I18NUtil.getMessages().organigramma_popup_section_puntiProtocollo(), false, true, false, associaPuntiProtocolloForm);
		// gestisciPuntiProtocolloDetailSection.setVisible(true);
			
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				final Record formRecord = new Record(associaPuntiProtocolloForm.getValues());
				onClickOkButton(formRecord, new DSCallback() {			
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						_window.markForDestroy();
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
		
		layout.addMember(associaPuntiProtocolloForm);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon("archivio/assegna.png");

	}

	public abstract void onClickOkButton(Record formRecord, DSCallback callback);
	
	public void editRecordPerModificaInvio(Record record) {
		associaPuntiProtocolloForm.editRecord(record);
		markForRedraw();
	}
	
	public List<String> getListaUoPuntiProtocolloSelezionati(){
		RecordList listaPuntiProtocolloSelezionati = associaPuntiProtocolloForm.getValueAsRecordList("listaPuntiProtocollo");
		// Se imposto puntoProtocolloItem.setAttribute("obbligatorio", true) 
		// e cerco di prendere i valori in questo modo non funziona. Non so perchè
		// RecordList listaPuntiProtocolloSelezionati = associaPuntiProtocolloForm.getValuesAsRecord().getAttributeAsRecordList("listaPuntiProtocollo");
		List<String> listaUoPuntiProtocollo = new ArrayList<String>();
		if (listaPuntiProtocolloSelezionati != null){
			for (int i=0; i < listaPuntiProtocolloSelezionati.getLength(); i++) {
				String idPP = listaPuntiProtocolloSelezionati.get(i).getAttributeAsString("idUo");
				if ((idPP != null) && !("".equalsIgnoreCase(idPP)) && !("null".equalsIgnoreCase(idPP)) && !(listaUoPuntiProtocollo.contains(idPP))){
					listaUoPuntiProtocollo.add(idPP);	
				}
			}
		}
		return listaUoPuntiProtocollo;
	}
	
}
