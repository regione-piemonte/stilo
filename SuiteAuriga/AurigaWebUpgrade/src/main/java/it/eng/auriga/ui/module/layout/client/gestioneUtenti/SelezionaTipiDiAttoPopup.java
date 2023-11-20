/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Arrays;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class SelezionaTipiDiAttoPopup extends ModalWindow {

	private SelezionaTipiDiAttoPopup window;
	
	// DynamicForm
	private DynamicForm formMain;
	
	private String mode;
	
	private String tipoLista;
	
	private boolean isEditMode;
	
	private SelezionaTipiAttoList selezionaTipiAttoList;
	
	public SelezionaTipiDiAttoPopup(Record record, String title, String mode) {
		super("seleziona_tipi_atto", true);
		
		window = this;
		
		this.tipoLista = record.getAttribute("tipoLista");
		
		setHeight(350);
		setWidth(800);	
		setTitle(title);
        settingsMenu.removeItem(separatorMenuItem);
        settingsMenu.removeItem(autoSearchMenuItem);
		
		setEditMode((mode!=null && !mode.equalsIgnoreCase("") && (mode.equalsIgnoreCase("edit") || mode.equalsIgnoreCase("new")) ));
		
		disegnaForm(record);
		
		Button okButton = new Button("Ok");   
		okButton.setIcon("ok.png");
		okButton.setIconSize(16); 
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(formMain.validate()) {
					final Record formRecord = new Record();					
					if (tipoLista.equals("tipiGestAtti")) {
						// Costruisco la lista con le tipologie scelte
						String listaTipiGestAttiSelezionati = "";
						for (ListGridRecord r : selezionaTipiAttoList.getRecords()) {
							String idtipoAtto = r.getAttribute("idtipoAtto");
							if (r.getAttribute("flgAbilitazione").equals("true")) {
								listaTipiGestAttiSelezionati = listaTipiGestAttiSelezionati + idtipoAtto + ";";
							}
						}
						formRecord.setAttribute("listaTipiGestAttiSelezionati", listaTipiGestAttiSelezionati);
					} else {
						// Costruisco la lista con le tipologie scelte
						String listaTipiVisPropAttiInIterSelezionati = "";
						for (ListGridRecord r : selezionaTipiAttoList.getRecords()) {
							String idtipoAtto = r.getAttribute("idtipoAtto");
							if (r.getAttribute("flgAbilitazione").equals("true")) {
								listaTipiVisPropAttiInIterSelezionati = listaTipiVisPropAttiInIterSelezionati + idtipoAtto + ";";
							}
						}
						formRecord.setAttribute("listaTipiVisPropAttiInIterSelezionati", listaTipiVisPropAttiInIterSelezionati);
					}
					onClickOkButton(formRecord, new DSCallback() {			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							window.markForDestroy();
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
				window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(okButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(true);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		layout.addMember(formMain);
		layout.addMember(selezionaTipiAttoList);
		
		addMember(layout);
		addMember(_buttons);
		
		okButton.setVisible(isEditMode());
		annullaButton.setVisible(isEditMode());
	}
	
	private void disegnaForm(Record record) {

		formMain = new DynamicForm();
		formMain.setKeepInParentRect(true);
		formMain.setAlign(Alignment.CENTER);
		formMain.setOverflow(Overflow.VISIBLE);
		formMain.setWrapItemTitles(false);
		
		selezionaTipiAttoList = new SelezionaTipiAttoList("selezionaTipiAtto");
		selezionaTipiAttoList.setCanEdit(isEditMode());
		RecordList listaSelezionaTipiAtto = record.getAttributeAsRecordList("listaSelezionaTipiAtto");
		selezionaTipiAttoList.setData(listaSelezionaTipiAtto);
		
		settaFlag(record);
	}
	
	private void settaFlag(Record record) {
		String listaTipiSelezionati = "";
		if(record.getAttribute("listaTipiGestAttiSelezionati")!=null && !record.getAttribute("listaTipiGestAttiSelezionati").equals("")) {
			listaTipiSelezionati = record.getAttribute("listaTipiGestAttiSelezionati");
		} else if (record.getAttribute("listaTipiVisPropAttiInIterSelezionati")!=null && !record.getAttribute("listaTipiVisPropAttiInIterSelezionati").equals("")) {
			listaTipiSelezionati = record.getAttribute("listaTipiVisPropAttiInIterSelezionati");
		}
		if (!listaTipiSelezionati.equals("")) {
			String[] listaValoriString = listaTipiSelezionati.split(";");
			List<String> listaValoriList = Arrays.asList(listaValoriString);
			for (String idAtto : listaValoriList) {
				for (ListGridRecord r : selezionaTipiAttoList.getRecords()) {
					String idtipoAtto = r.getAttribute("idtipoAtto");
					if (idAtto.equals(idtipoAtto)) {
						r.setAttribute("flgAbilitazione", "true");
					}
				}
			}
		}
	}
	
	public Record getFormValuesAsRecord() {
		return formMain.getValuesAsRecord();
	}
	
	public abstract void onClickOkButton(Record formRecord, DSCallback callback);
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}
}
