/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ErroreMassivoPopup extends ModalWindow {

	public ErroreMassivoPopup(String nomeEntita, String intestazionePrimaColonna, RecordList listaErrori, int numeroRecordSelezionati, Integer larghezzaPopup, Integer altezzaPopup) {
		this(nomeEntita, intestazionePrimaColonna, listaErrori, numeroRecordSelezionati, larghezzaPopup, altezzaPopup, "Dettaglio errori operazione", null, 0, "Descrizione errore");
	}
	
	public ErroreMassivoPopup(String nomeEntita, String intestazionePrimaColonna, RecordList listaErrori, int numeroRecordSelezionati, Integer larghezzaPopup, Integer altezzaPopup, String title) {
		this(nomeEntita, intestazionePrimaColonna, listaErrori, numeroRecordSelezionati, larghezzaPopup, altezzaPopup, title, null, 0, "Descrizione errore");
	}
	
	public ErroreMassivoPopup(String nomeEntita, String intestazionePrimaColonna, RecordList listaErrori, int numeroRecordSelezionati, Integer larghezzaPopup, Integer altezzaPopup, String title, int recordInWarning, String intestazioneSecondaColonna) {
		this(nomeEntita, intestazionePrimaColonna, listaErrori, numeroRecordSelezionati, larghezzaPopup, altezzaPopup, title, null, recordInWarning, intestazioneSecondaColonna);
	}
	
	public ErroreMassivoPopup(String nomeEntita, String intestazionePrimaColonna, RecordList listaErrori, int numeroRecordSelezionati, Integer larghezzaPopup, Integer altezzaPopup, final ServiceCallback<Record> callback) {
		this(nomeEntita, intestazionePrimaColonna, listaErrori, numeroRecordSelezionati, larghezzaPopup, altezzaPopup, "Dettaglio errori operazione", callback, 0, "Descrizione errore");
	}
		
	public ErroreMassivoPopup(String nomeEntita, String intestazionePrimaColonna, RecordList listaErrori, int numeroRecordSelezionati, Integer larghezzaPopup, Integer altezzaPopup, String title, final ServiceCallback<Record> callback) {
			this(nomeEntita, intestazionePrimaColonna, listaErrori, numeroRecordSelezionati, larghezzaPopup, altezzaPopup, title, callback, 0, "Descrizione errore");
	}
	
	public ErroreMassivoPopup(String nomeEntita, String intestazionePrimaColonna, RecordList listaErrori, int numeroRecordSelezionati, Integer larghezzaPopup, Integer altezzaPopup, String title, final ServiceCallback<Record> callback, int recordInWarning, String intestazioneSecondaColonna) {
		
		super(nomeEntita, true, false);
	
		setModalMaskOpacity(50);
		setWidth(larghezzaPopup != null ? larghezzaPopup.intValue() : 600);		
		setHeight(altezzaPopup != null ? altezzaPopup.intValue() : 300);
		setKeepInParentRect(true);
		setTitle(title);
		setShowModalMask(true);
		setShowCloseButton(true);
		
		setShowMaximizeButton(true);
		setShowMinimizeButton(false);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		DynamicForm form = new DynamicForm();
		form.setItemLayout(FormLayoutType.ABSOLUTE);
		form.setHeight("5%");
		form.setWidth("80%");
		form.setLayoutAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		
		StaticTextItem messageItem = new StaticTextItem();			
		messageItem.setTextAlign(Alignment.LEFT);		
		messageItem.setValue("<span style=\"font-size:13;\">Sono andati in errore " + (listaErrori.getLength() - recordInWarning) + " record su " + numeroRecordSelezionati +". Di seguito il dettaglio: </span>");
		messageItem.setWidth(450);
		messageItem.setWrap(false);
		messageItem.setTop(15);
		messageItem.setShowTitle(false);
		
		form.setItems(messageItem);
		
		ListGrid errorsGrid = new ListGrid(){
			
			@Override
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				return "font-size:12;padding-right:20px;padding-left:5px";
			}		
		};

		errorsGrid.setLayoutAlign(Alignment.CENTER);
		errorsGrid.setHeight("30%");
		errorsGrid.setWidth("80%");
		errorsGrid.setShowAllRecords(true); 
		errorsGrid.setWrapCells(true);
		errorsGrid.setFixedRecordHeights(false); 
		
		ListGridField idError = new ListGridField("idError", intestazionePrimaColonna);
		idError.setAutoFitWidth(false);
		idError.setWidth("35%");
		idError.setAlign(Alignment.LEFT);
		idError.setWrap(true);
//		idError.setCellFormatter(new CellFormatter() {			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
//				return setWarningFormatter(value, record);
//			}
//		});

		ListGridField descrizione = new ListGridField("descrizione", intestazioneSecondaColonna);
		descrizione.setWidth("*");
		descrizione.setAlign(Alignment.LEFT);
		descrizione.setWrap(true);
//		descrizione.setCellFormatter(new CellFormatter() {			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
//				return setWarningFormatter(value, record);
//			}
//		});
		
		errorsGrid.setFields(idError, descrizione);
		errorsGrid.setData(listaErrori);
		errorsGrid.setWrapCells(true);
		
		IButton closeButton = new IButton();
		closeButton.setLayoutAlign(Alignment.CENTER);
		closeButton.setTitle("Chiudi");
		closeButton.setIcon("ok.png");
		closeButton.setIconHeight(16);
		closeButton.setIconWidth(16);
		closeButton.setWidth(100);
		closeButton.setBottom(20);
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				if(callback != null) {
					callback.execute(new Record());
				}
				markForDestroy();
			}
		});
		
		VLayout layout = new VLayout();  
		layout.setMembersMargin(10);
		layout.setPadding(10);
		layout.setHeight("90%");
		layout.setWidth100();
		layout.addMember(form);
		layout.addMember(errorsGrid);
		layout.addMember(closeButton);
		
		setBody(layout);
		setIcon("message/error.png");
	}

	/**
	 * @param value
	 * @param record
	 * @return
	 */
	public String setWarningFormatter(Object value, ListGridRecord record) {
		if(record.getAttribute("descrizione")!=null && !"".equalsIgnoreCase(record.getAttribute("descrizione")) &&  record.getAttribute("descrizione").startsWith("WARNING: ")) {
			record.setCustomStyle(it.eng.utility.Styles.formCellVariazione);
		}
		return (String) value;
	}

}
