/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public abstract class OpzioniAttiDaScaricarePopup extends ModalWindow {
	
	protected OpzioniAttiDaScaricarePopup _window;
	protected DynamicForm _form;
	
	protected RadioGroupItem flgInclusiPareriItem;
	protected RadioGroupItem flgInclAllegatiPIItem;
	protected RadioGroupItem flgIntXPubblRadioItem;
	
	public OpzioniAttiDaScaricarePopup() {
		
		super("opzioni_atti_da_scaricare", true);
		
		_window = this;
		
		setTitle("Opzioni di scarico");
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);				
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(1,1,1,1,1,1,1,"*");		
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);		
		
		flgInclusiPareriItem = new RadioGroupItem("flgInclusiPareri");
		LinkedHashMap<String, String> flgInclusiPareriValueMap = new LinkedHashMap<String, String>();
		flgInclusiPareriValueMap.put("SI", "SI");
		flgInclusiPareriValueMap.put("NO", "NO");
		flgInclusiPareriItem.setValueMap(flgInclusiPareriValueMap);
		flgInclusiPareriItem.setDefaultValue("SI");
		flgInclusiPareriItem.setVertical(false);
		flgInclusiPareriItem.setWrap(false);
		flgInclusiPareriItem.setTitle("Includi pareri");
		flgInclusiPareriItem.setShowTitle(true);
		flgInclusiPareriItem.setWidth(120);
		flgInclusiPareriItem.setColSpan(5);
		flgInclusiPareriItem.setStartRow(true);
		
		flgInclAllegatiPIItem = new RadioGroupItem("flgInclAllegatiPI");
		LinkedHashMap<String, String> flgInclAllegatiPIValueMap = new LinkedHashMap<String, String>();
		flgInclAllegatiPIValueMap.put("SEPARATI", "SI");
		flgInclAllegatiPIValueMap.put("NO", "NO");
		flgInclAllegatiPIItem.setValueMap(flgInclAllegatiPIValueMap);
		flgInclAllegatiPIItem.setStartRow(true);
		flgInclAllegatiPIItem.setDefaultValue("NO");
		flgInclAllegatiPIItem.setVertical(false);
		flgInclAllegatiPIItem.setWrap(false);
		flgInclAllegatiPIItem.setTitle("Includi allegati parte int. separati");
		flgInclAllegatiPIItem.setShowTitle(true);
		flgInclAllegatiPIItem.setWidth(120);
		flgInclAllegatiPIItem.setColSpan(5);
		flgInclAllegatiPIItem.setStartRow(true);
		
		
		flgIntXPubblRadioItem = new RadioGroupItem("flgIntXPubbl");
		LinkedHashMap<String, String> flgIntXPubblValueMap = new LinkedHashMap<String, String>();
		flgIntXPubblValueMap.put("INT", "integrale");
		flgIntXPubblValueMap.put("PUBBL", "per pubblicazione (con eventuali omissis)");
		flgIntXPubblValueMap.put("entrambe", "entrambe");
		flgIntXPubblRadioItem.setValueMap(flgIntXPubblValueMap);
		flgIntXPubblRadioItem.setDefaultValue("INT");
		flgIntXPubblRadioItem.setVertical(false);
		flgIntXPubblRadioItem.setWrap(false);
		flgIntXPubblRadioItem.setTitle("Versione da scaricare");
		flgIntXPubblRadioItem.setShowTitle(true);
		flgIntXPubblRadioItem.setWidth(120);
		flgIntXPubblRadioItem.setColSpan(5);
		flgIntXPubblRadioItem.setStartRow(true);
		
		_form.setFields(new FormItem[]{flgInclusiPareriItem, flgInclAllegatiPIItem, flgIntXPubblRadioItem });
		
		Button downloadButton = new Button("Scarica");   
		downloadButton.setIcon("file/download_manager.png");
		downloadButton.setIconSize(16); 
		downloadButton.setAutoFit(false);
		downloadButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				onClickOkButton(new DSCallback() {	
					
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
        _buttons.addMember(downloadButton);
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
				
		setIcon("buttons/download_zip.png");
		
	}
	
	public DynamicForm getForm() {
		return _form;
	}

	public abstract void onClickOkButton(DSCallback callback);
	
}