/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public abstract class AzioneSuRichAccessoAttiPopup extends Window {
	
	protected AzioneSuRichAccessoAttiPopup azioneSuRichAccessoAttiPopup;
	
	private ValuesManager vm = new ValuesManager();
	
	protected DynamicForm datiAppuntamentoForm;
	private DateItem dataAppuntamentoItem;
	private TimeItem orarioAppuntamentoItem;
	
	protected DynamicForm datiPrelievoForm;
	private DateItem dataPrelievoItem;
	
	protected DynamicForm datiRestituzioneForm;
	private DateItem dataRestituzioneItem;
	
	protected DynamicForm motiviForm;
	private TextAreaItem motiviItem;
	
	private StaticTextItem alertItem;
	
	public AzioneSuRichAccessoAttiPopup(Record record){
		
		azioneSuRichAccessoAttiPopup = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);				
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		
		final String codOperazione = record.getAttribute("codOperazione");
		final int numRichiesteConScansioneTotOParz  = record.getAttributeAsInt("numRichiesteConScansioneTotOParz") != null ? record.getAttributeAsInt("numRichiesteConScansioneTotOParz") : 0;
		final RecordList listaRecord = record.getAttributeAsRecordList("listaRecord");
		
		if(codOperazione.equalsIgnoreCase("SET_APPUNTAMENTO")) {
			
			setTitle("Dati appuntamento");
		
			datiAppuntamentoForm = new DynamicForm();
			datiAppuntamentoForm.setValuesManager(vm);
			datiAppuntamentoForm.setKeepInParentRect(true);
			datiAppuntamentoForm.setWidth100();
			datiAppuntamentoForm.setHeight100();
			datiAppuntamentoForm.setCellPadding(5);
			datiAppuntamentoForm.setOverflow(Overflow.VISIBLE);
			
			dataAppuntamentoItem = new DateItem("dataAppuntamento", "Data appuntamento");
			dataAppuntamentoItem.setRequired(true);
			
			orarioAppuntamentoItem = new TimeItem("orarioAppuntamento", "Orario appuntamento");
			orarioAppuntamentoItem.setTimeFormatter(TimeDisplayFormat.TOSHORTPADDED24HOURTIME);
			orarioAppuntamentoItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
			orarioAppuntamentoItem.setRequired(true);
					
			datiAppuntamentoForm.setFields(dataAppuntamentoItem, orarioAppuntamentoItem);
			
			addItem(datiAppuntamentoForm);	
		
		} else if(codOperazione.equalsIgnoreCase("REGISTRA_PRELIEVO")) {
			
			setTitle("Dati prelievo");
			
			datiPrelievoForm = new DynamicForm();
			datiPrelievoForm.setValuesManager(vm);
			datiPrelievoForm.setKeepInParentRect(true);
			datiPrelievoForm.setWidth100();
			datiPrelievoForm.setHeight100();
			datiPrelievoForm.setCellPadding(5);
			datiPrelievoForm.setOverflow(Overflow.VISIBLE);
			
			dataPrelievoItem = new DateItem("dataPrelievo", "Data prelievo");
			dataPrelievoItem.setRequired(true);
			dataPrelievoItem.setDefaultValue(new Date());
			
			datiPrelievoForm.setFields(dataPrelievoItem);
			
			addItem(datiPrelievoForm);	
		
		} else if(codOperazione.equalsIgnoreCase("REGISTRA_RESTITUZIONE")) {
			
			setTitle("Dati restituzione");
			
			datiRestituzioneForm = new DynamicForm();
			datiRestituzioneForm.setValuesManager(vm);
			datiRestituzioneForm.setKeepInParentRect(true);
			datiRestituzioneForm.setWidth100();
			datiRestituzioneForm.setHeight100();			
			datiRestituzioneForm.setCellPadding(5);
			datiRestituzioneForm.setOverflow(Overflow.VISIBLE);
			
			dataRestituzioneItem = new DateItem("dataRestituzione", "Data restituzione");
			dataRestituzioneItem.setRequired(true);
			dataRestituzioneItem.setWrapTitle(false);
			dataRestituzioneItem.setDefaultValue(new Date());
			
			datiRestituzioneForm.setFields(dataRestituzioneItem);
			
			addItem(datiRestituzioneForm);	
		
		} else if (codOperazione.equalsIgnoreCase("CHIUSURA")) {
            setTitle("Compila eventuali motivazioni operazione");			
			motiviForm = new DynamicForm();
			motiviForm.setValuesManager(vm);
			motiviForm.setKeepInParentRect(true);
			motiviForm.setWidth100();
			motiviForm.setHeight100();
			motiviForm.setNumCols(1);
			motiviForm.setColWidths("*");
			motiviForm.setCellPadding(5);
			motiviForm.setOverflow(Overflow.VISIBLE);			
			motiviItem = new TextAreaItem("motivi");			
			motiviItem.setShowTitle(false);
			motiviItem.setHeight(100);
			motiviItem.setWidth(450);
			motiviForm.setFields(motiviItem);
			addItem(motiviForm);
			
		} else if (codOperazione.equalsIgnoreCase("RIAPERTURA")) {
			setTitle("Compila eventuali motivazioni operazione");			
			motiviForm = new DynamicForm();
			motiviForm.setValuesManager(vm);
			motiviForm.setKeepInParentRect(true);
			motiviForm.setWidth100();
			motiviForm.setHeight100();
			motiviForm.setNumCols(1);
			motiviForm.setColWidths("*");
			motiviForm.setCellPadding(5);
			motiviForm.setOverflow(Overflow.VISIBLE);			
			motiviItem = new TextAreaItem("motivi");			
			motiviItem.setShowTitle(false);
			motiviItem.setHeight(100);
			motiviItem.setWidth(450);
			motiviForm.setFields(motiviItem);
			addItem(motiviForm);
			
		} else if (codOperazione.equalsIgnoreCase("RIPRISTINO")) {
			setTitle("Compila eventuali motivazioni operazione");			
			motiviForm = new DynamicForm();
			motiviForm.setValuesManager(vm);
			motiviForm.setKeepInParentRect(true);
			motiviForm.setWidth100();
			motiviForm.setHeight100();
			motiviForm.setNumCols(1);
			motiviForm.setColWidths("*");
			motiviForm.setCellPadding(5);
			motiviForm.setOverflow(Overflow.VISIBLE);			
			motiviItem = new TextAreaItem("motivi");			
			motiviItem.setShowTitle(false);
			motiviItem.setHeight(100);
			motiviItem.setWidth(450);
			motiviForm.setFields(motiviItem);
			addItem(motiviForm);
		}
		else {
		
			setTitle("Motivazioni/dettagli operazione");
			
			motiviForm = new DynamicForm();
			motiviForm.setValuesManager(vm);
			motiviForm.setKeepInParentRect(true);
			motiviForm.setWidth100();
			motiviForm.setHeight100();
			motiviForm.setNumCols(1);
			motiviForm.setColWidths("*");
			motiviForm.setCellPadding(5);
			motiviForm.setOverflow(Overflow.VISIBLE);
			
			motiviItem = new TextAreaItem("motivi");			
//			motiviItem.setHint("Motivazioni/dettagli operazione");
//			motiviItem.setShowHintInField(true);
			motiviItem.setShowTitle(false);
			motiviItem.setHeight(100);
			motiviItem.setWidth(450);
			
			if ( (numRichiesteConScansioneTotOParz > 0) && (codOperazione != null) && (codOperazione.equalsIgnoreCase("INVIO_IN_APPROVAZIONE") || codOperazione.equalsIgnoreCase("APPROVAZIONE"))){
				alertItem = new StaticTextItem();
				alertItem.setShowTitle(false);
				alertItem.setWidth("*");
				String testo =  "<b>Atto/i richiesti risultano scansiti parzialmente o completamente</b>"; 
				alertItem.setValue("<img src=\"images/warning.png\" height=\"16\" width=\"16\" align=\"top\"/>&nbsp;&nbsp;  " + testo);
				alertItem.setAlign(Alignment.CENTER);
				motiviForm.setFields(alertItem, motiviItem);
			} else {
				motiviForm.setFields(motiviItem);
			}
			
			addItem(motiviForm);
		
		}
			
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(vm.validate()) {
					onClickOkButton(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
				
							markForDestroy();
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
				
				markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
		show();
	}
		
	public abstract void onClickOkButton(DSCallback service);
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
//	private void manageError(Record object) {
//		
//		RecordList listaErrori = new RecordList();
//		Map errorMessages = object.getAttributeAsMap("errorMessages");
//		String errorMsg = null;
//		if (errorMessages != null && errorMessages.size() > 0) {
//			if (listaRecord.getLength() > errorMessages.size()) {
//				errorMsg = "Alcuni dei record selezionati per l''operazione sono andati in errore!";
//			} else {
//				errorMsg = "Tutti i record selezionati per l''operazione sono andati in errore!";
//			}
//			for (int i = 0; i < listaRecord.getLength(); i++) {
//				Record record = listaRecord.get(i);
//				
//				errorMsg += "<br/>" + errorMessages.get(record.getAttribute("idUd"));
//				
//				record.setAttribute("idError", record.getAttribute("idUd"));
//				record.setAttribute("descrizione", "protocollo");
//				listaErrori.add(record);
//				
//			}
//		}
//
//		if (errorMsg != null) {
//			//Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
//			ErroreMassivoPopup errorePopup = new ErroreMassivoPopup("", "N* atto", listaErrori, listaRecord.getLength(),
//					600, 300);
//			errorePopup.show();
//		} else {
//			Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));										
//		}
//		
//	}
		
}
