/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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

public abstract class UOCollegatePuntoProtocolloPopup extends ModalWindow {
	
	private UOCollegatePuntoProtocolloPopup window;
		
	// DynamicForm
	private DynamicForm formMain;
	
	// HiddenItem
	private HiddenItem listaUOPuntoProtocolloEscluseItem;
	private HiddenItem listaUOPuntoProtocolloIncluseItem;
	private HiddenItem listaUOPuntoProtocolloEreditarietaAbilitataItem;
	 
	// Lista UO
	private UoCollegatePuntoProtocolloList newUoPuntoProtocolloGrid;
	
	private String mode;
	
	private boolean isEditMode;
	
	public UOCollegatePuntoProtocolloPopup(Record record, String title, String mode){
		
		super("uo_collegate_puntoprotocollo", true);
		
		window = this;
		
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
					// Costruisco la lista con le UO ESCLUSE e INCLUSE
					String listaUOPuntoProtocolloIncluse = "";
					String listaUOPuntoProtocolloEscluse = "";
					String listaUOPuntoProtocolloEreditarietaAbilitata = "";
					for (ListGridRecord r : newUoPuntoProtocolloGrid.getRecords()) {						
						  String idUO = r.getAttribute("idUO");						
						  if (r.getAttribute("flgAbilitazione").equals("true") ){
							  listaUOPuntoProtocolloIncluse = listaUOPuntoProtocolloIncluse + idUO + ";";
						  }
						  else{
								listaUOPuntoProtocolloEscluse = listaUOPuntoProtocolloEscluse + idUO + ";";
						  }
						  //FIXME controllare
						  if (r.getAttribute("flgAbilitaEreditarieta").equals("true")) {
							  listaUOPuntoProtocolloEreditarietaAbilitata = listaUOPuntoProtocolloEreditarietaAbilitata + idUO + ";";
						  }
					}					
					formRecord.setAttribute("listaUOPuntoProtocolloIncluse", listaUOPuntoProtocolloIncluse);
					formRecord.setAttribute("listaUOPuntoProtocolloEscluse", listaUOPuntoProtocolloEscluse);
					formRecord.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", listaUOPuntoProtocolloEreditarietaAbilitata);
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
		layout.addMember(newUoPuntoProtocolloGrid);
		
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
			
		// HIDDEN
		listaUOPuntoProtocolloEscluseItem                = new HiddenItem("listaUOPuntoProtocolloEscluse");
		listaUOPuntoProtocolloIncluseItem                = new HiddenItem("listaUOPuntoProtocolloIncluse");
		listaUOPuntoProtocolloEreditarietaAbilitataItem  = new HiddenItem("listaUOPuntoProtocolloEreditarietaAbilitata");
		
		newUoPuntoProtocolloGrid = new UoCollegatePuntoProtocolloList("listaUoCollegate");
		newUoPuntoProtocolloGrid.setCanEdit(isEditMode());
		RecordList listaUOCollegatePuntoProtocolloRec = record.getAttributeAsRecordList("listaUOCollegatePuntoProtocollo");
		newUoPuntoProtocolloGrid.setData(listaUOCollegatePuntoProtocolloRec);
		
		formMain.setFields( // Hidden
				            listaUOPuntoProtocolloEscluseItem,
				            listaUOPuntoProtocolloIncluseItem,
				            listaUOPuntoProtocolloEreditarietaAbilitataItem
				           );
	}
	
	private void settaFlag() {
		for (ListGridRecord r : newUoPuntoProtocolloGrid.getRecords()) {
			if (Boolean.valueOf(r.getAttribute("flgAbilitazioneEreditata"))) {
				// r.setEnabled(false);
			}
		}	
	}
		
//	public void setFormValuesFromRecord(Record record) {
//			// Creo la lista delle UO ESCLUSE
//			String listaUOPuntoProtocolloEscluse = "";
//			formMain.setValue("listaUOPuntoProtocolloEscluse", listaUOPuntoProtocolloEscluse);		
//			formMain.clearErrors(true);
//			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//				@Override
//				public void execute() {
//					
//				}
//			});
//	}

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