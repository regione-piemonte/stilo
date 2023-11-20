/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SelezionaRuoloPerApposizioneFirmaPopup extends ModalWindow {
	
	protected SelezionaRuoloPerApposizioneFirmaPopup popup;
	
	protected DynamicForm titleForm;
	protected DynamicForm ruoliForm;
	List<FormItem> listaFormItem;
	CheckboxItem[] checkBoxRuoli;
	
	
	
	public SelezionaRuoloPerApposizioneFirmaPopup(Record record, final ServiceCallback<List<String>> callback){
			
		super("selezione_ruolo_apposizione_firma_popup", true);
		
		popup = this;
		
		setTitle("Selezione ruoli firmatario");
		
		setAutoCenter(true);
		setHeight(240);
		setWidth(430);	

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		titleForm = new DynamicForm();
		titleForm.setKeepInParentRect(true);
		titleForm.setWidth100();
		titleForm.setNumCols(8);
		titleForm.setColWidths(1,1,1,1,1,1,"*","*");		
		titleForm.setCellPadding(5);
		titleForm.setWrapItemTitles(false);
		
		StaticTextItem testoSelezioneRuoliItem = new StaticTextItem("testoSelezioneRuoli", "Seleziona i ruoli per i quali apporre la firma:");
		testoSelezioneRuoliItem.setValue("");
		testoSelezioneRuoliItem.setStartRow(true);
		testoSelezioneRuoliItem.setShowTitle(true);
		testoSelezioneRuoliItem.setColSpan(5);
		testoSelezioneRuoliItem.setWidth(10);

		titleForm.setItems(testoSelezioneRuoliItem);
		
		ruoliForm = new DynamicForm();
		ruoliForm.setKeepInParentRect(true);
		ruoliForm.setHeight100();
		ruoliForm.setNumCols(8);
		ruoliForm.setColWidths(1,1,1,1,1,1,"*","*");		
		ruoliForm.setCellPadding(5);
		ruoliForm.setWrapItemTitles(false);		
		
		Map mappaInfoFirmaGrafica = record.getAttributeAsMap("mappaInfoFirmaGrafica");
		Set setFirmeGrafiche = mappaInfoFirmaGrafica.keySet();
		
		listaFormItem = new ArrayList<>();
				
		checkBoxRuoli = new CheckboxItem[setFirmeGrafiche.size()];
		int pos = 0;
		for (Object keyFirmaGrafica : setFirmeGrafiche) {
			SpacerItem spacerItem = new SpacerItem();
			spacerItem.setStartRow(true);
			spacerItem.setWidth(10);
			listaFormItem.add(spacerItem);
			Map infoFirmaGrafica = (Map) mappaInfoFirmaGrafica.get(keyFirmaGrafica);
			
			CheckboxItem lCheckboxItem = new CheckboxItem((String) keyFirmaGrafica, infoFirmaGrafica.get("ruolo") != null ? (String) infoFirmaGrafica.get("ruolo") : (String) keyFirmaGrafica);
			
			CustomValidator validatoreRuoli = new CustomValidator() {
				
				@Override
				protected boolean condition(Object value) {
					if (checkBoxRuoli != null) {
						boolean ruoloSelezionato = false;
						for (int i = 0; i < checkBoxRuoli.length; i++) {
							CheckboxItem checkBoxRuoloItem = checkBoxRuoli[i];
							if (checkBoxRuoloItem.getValueAsBoolean() != null && checkBoxRuoloItem.getValueAsBoolean().booleanValue()) {
								ruoloSelezionato = true;
							}
						}
						return ruoloSelezionato;
					}
					return true;
				}
			};			
			
			validatoreRuoli.setErrorMessage("Selezionare almeno un ruolo");
			lCheckboxItem.setValidators(validatoreRuoli);
			
			lCheckboxItem.setDefaultValue(false);
			listaFormItem.add(lCheckboxItem);
			checkBoxRuoli[pos] = lCheckboxItem;
			pos++;
		}
		
		ruoliForm.setFields(listaFormItem.toArray(new FormItem[listaFormItem.size()]));
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(ruoliForm.validate()) {
					List<String> listaRuoliSelezionati = new ArrayList();
					for (int i = 0; i < checkBoxRuoli.length; i++) {
						CheckboxItem checkBoxRuoloItem = checkBoxRuoli[i];
						if (checkBoxRuoloItem.getValueAsBoolean() != null && checkBoxRuoloItem.getValueAsBoolean().booleanValue()) {
							listaRuoliSelezionati.add(checkBoxRuoloItem.getName());
						}
					}
					popup.markForDestroy();
					callback.execute(listaRuoliSelezionati);											
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
				popup.markForDestroy();	
			}
		});
		
		HStack buttons = new HStack(5);
		buttons.setWidth100();
		buttons.setHeight(30);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
        buttons.addMember(confermaButton);
		buttons.addMember(annullaButton);	
		
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
		
		layout.addMember(titleForm);
		layout.addMember(ruoliForm);
				
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight(20);
		spacerLayout.setWidth100();
		
		portletLayout.addMember(spacerLayout);
		portletLayout.addMember(layout);
		portletLayout.addMember(buttons);
						
		setBody(portletLayout);
				
		setIcon("blank.png");
		
		setCanEdit(true);
		
		show();
	}
	
	public void setCanEdit(boolean canEdit) {
		if (ruoliForm != null) {
			ruoliForm.setCanEdit(true);
		}
	}
	
}

