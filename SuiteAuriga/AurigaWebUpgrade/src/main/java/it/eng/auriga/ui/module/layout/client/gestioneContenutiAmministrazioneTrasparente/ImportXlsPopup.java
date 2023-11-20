/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.IEditorItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;

public abstract class ImportXlsPopup extends ModalWindow {
	
	protected ImportXlsPopup window;	
	protected ValuesManager vm;
	protected DynamicForm form;
	protected DateItem dataInizioPubblicazioneItem;
	protected DateItem dataFinePubblicazioneItem;
	protected String uriFileImportExcel;
	protected Button confermaButton;
	protected Button annullaButton;
	protected RecordList dettAttrLista;
	protected FileUploadItemWithFirmaAndMimeType uploadFileXlsButton;
	protected ExtendedTextItem nomeFileExcelItem;
	
	private final int ALT_POPUP_ERR_MASS = 375;
	private final int LARG_POPUP_ERR_MASS = 620;
	
	public ImportXlsPopup(final RecordList dettAttrLista){
		
		super("import_xls_popup");
		
		window = this;
		
		vm = new ValuesManager();
		
		this.dettAttrLista = dettAttrLista;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(120);
		setWidth(390);	
		setKeepInParentRect(true);		
		setTitle("Importa dati da xls");
		setShowTitle(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
		setHeaderIcon("menu/import_excel.png");
		setAutoCenter(true);
		
	    form = new DynamicForm();
	    form.setValuesManager(vm);
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(5);
		form.setColWidths(10, 10, 10, 10, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		// Data inizio pubblicazione
		dataInizioPubblicazioneItem = new DateItem("dtInizioPubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataInizioPubblicazioneItem_title());		
    	dataInizioPubblicazioneItem.setRequired(true);
    	dataInizioPubblicazioneItem.setAttribute("obbligatorio", true);
		dataInizioPubblicazioneItem.setStartRow(true);		
		dataInizioPubblicazioneItem.setDefaultValue(getDefaultValueDataInizioPubbl());
		dataInizioPubblicazioneItem.setWidth(120);
		dataInizioPubblicazioneItem.setWrapTitle(false);
		
		// Data fine pubblicazione
    	dataFinePubblicazioneItem = new DateItem("dtFinePubblicazione", I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_dataFinePubblicazioneItem_title());		

    	CustomValidator lPeriodoPubblicazioneValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				Date dataInizioPubb = dataInizioPubblicazioneItem.getValueAsDate();
		    	Date dataFinePubb = dataFinePubblicazioneItem.getValueAsDate();
		    	if(dataInizioPubb != null && dataFinePubb != null) {
		    		Integer differenceDays = CalendarUtil.getDaysBetween(dataInizioPubb, dataFinePubb);
		    		if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
		    			return (differenceDays >= 0);
		    		} else {
		    			return (differenceDays > 0);
		    		}
		    	}		    	
		    	return true;
			}
		};
		if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
			lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine pubblicazione non puo' essere antecedente a quella di inizio pubblicazione");
		} else {
			lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine pubblicazione deve essere successiva a quella di inizio pubblicazione");
		}		
		// La data di fine pubblicazione non puo' essere una data passata
		dataFinePubblicazioneItem.setValidators(lPeriodoPubblicazioneValidator);
		dataFinePubblicazioneItem.setWrapTitle(false);
		dataFinePubblicazioneItem.setWidth(120);
		
		uploadFileXlsButton = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				uriFileImportExcel = uri;	
				form.setValue("nomeFileExcel", displayFileName);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				if (info == null || info.getMimetype() == null 	|| (!info.getMimetype().equals("application/excel") 
						&& !info.getMimetype().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
					GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non riconosciuto o non ammesso. I formati validi risultano essere xls/xlsx", "", MessageType.ERROR));
				} else {
					onClickImportXlsButtonAfterUpload(uriFileImportExcel, info.getMimetype(), dettAttrLista);	
				}				
			}
		});
		
		// Nome file excel
		nomeFileExcelItem = new ExtendedTextItem("nomeFileExcel", "File excel");
		nomeFileExcelItem.setWidth(290);
		nomeFileExcelItem.setShowTitle(true);
		nomeFileExcelItem.setCanEdit(false);
		nomeFileExcelItem.setColSpan(3);
		nomeFileExcelItem.setWrapTitle(false);
		
		// Bottone di upload
		uploadFileXlsButton.setShowTitle(true);
		uploadFileXlsButton.setVAlign(VerticalAlignment.CENTER);
		uploadFileXlsButton.setTextAlign(Alignment.RIGHT);
		uploadFileXlsButton.setWidth(16);
		uploadFileXlsButton.setAlign(Alignment.LEFT);
		
		form.setFields(dataInizioPubblicazioneItem, dataFinePubblicazioneItem, nomeFileExcelItem, uploadFileXlsButton);			
		
		addItem(form);
		
		// Bottoni			
		confermaButton = new Button("Import");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(form.validate()) {
					onClickOkButton(new Record(vm.getValues()));
					window.markForDestroy();				
				}
			}
		});	
		confermaButton.hide();
		
		annullaButton = new Button("Annulla");   
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
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);

		draw();
	}

    public Record getRecordToSave() {
    	final Record lRecordToSave = new Record();
    	addFormValues(lRecordToSave, form);
    	return lRecordToSave;
    }
    
	protected static void addFormValues(Record record, DynamicForm form) {

		if (form != null) {
			// lo commento perchè ci sono alcuni dati che devono essere passati anche se le sezioni relative sono nascoste:
			// per es. l'idEmail nella protocollazione di una mail in entrata, il tipoDocumento, il supportoOriginale ecc...
			// metto il controllo solo per quanto riguarda le ReplicableItem, che non devono essere passate quando sono nascoste
//			if(form.getDetailSection() == null || form.getDetailSection().isVisible()) {
				try {
					Record formRecord = form.getValuesAsRecord();
					for (FormItem item : form.getFields()) {
						if (item != null) {
							String fieldName = item.getName();
							if (item instanceof ReplicableItem) {
//								if(((ReplicableItem) item).getVisible()) { //TODO DA VERIFICARE SE VENGONO MESSI I VALORI SE LA SEZIONE E' CHIUSA
									final RecordList lRecordList = new RecordList();
									ReplicableCanvas[] allCanvas = ((ReplicableItem) item).getAllCanvas();
									if(allCanvas != null && allCanvas.length > 0) {
										for (ReplicableCanvas lReplicableCanvas : allCanvas) {
											if(lReplicableCanvas.hasValue(((ReplicableItem) item).getCanvasDefaultRecord())) {
												lRecordList.add(lReplicableCanvas.getFormValuesAsRecord());
											}
										}
										if(((ReplicableItem) item).isObbligatorio() && lRecordList.getLength() == 0) {
											lRecordList.add(allCanvas[0].getFormValuesAsRecord());
										}
									}
									formRecord.setAttribute((String) fieldName, lRecordList);
//								} else {
//									formRecord.setAttribute((String) fieldName, (Object) null);
//								}
							} else if (item instanceof GridItem) {
								formRecord.setAttribute((String) fieldName, ((GridItem) item).getValueAsRecordList());
							} else if (item instanceof IEditorItem) {							
								formRecord.setAttribute((String) fieldName, ((IEditorItem) item).getValue());
							}
						}
					}
					JSOHelper.addProperties(record.getJsObj(), formRecord.getJsObj());
				} catch (Exception e) {
				}
//			}			
		}
	}
	
	public void setFormValuesGridFromRecord(Record values) {
		form.editRecord(values);
	}

	private Date getDefaultValueDataInizioPubbl() {
    	Integer giorniDelay = Integer.parseInt(AurigaLayout.getParametroDB("GG_DELAY_INIZIO_PUBBL_ATTO") != null ? AurigaLayout.getParametroDB("GG_DELAY_INIZIO_PUBBL_ATTO") : "1");
    	Date dataInizioPubbl = new Date();
    	CalendarUtil.addDaysToDate(dataInizioPubbl, giorniDelay);
    	return dataInizioPubbl;
	}

    /**
	 * Metodo che indica se nel conteggio dei giorni di pubblicazione il giorno di pubblicazione viene sempre considerato come 1 giorno intero, altrimenti no
	 * 
	 */
	public boolean isConteggiaInteroGiornoCorrenteXPeriodoPubbl() {
		return AurigaLayout.getParametroDBAsBoolean("CONTEGGIA_INTERO_GIORNO_CORRENTE_X_PERIODO_PUBBL");
	}

	
	public void onClickImportXlsButtonAfterUpload(String uriFileImportExcel, String mimetype, RecordList dettAttrLista) {
		
		if (uriFileImportExcel!=null && !uriFileImportExcel.equalsIgnoreCase("")){		
			confermaButton.show();			
			final GWTRestDataSource lDatasource = new GWTRestDataSource("AttributiDinamiciDocDatasource");
			Record recordIn = getRecordToSave();
			recordIn.setAttribute("uriExcel", uriFileImportExcel);
			recordIn.setAttribute("mimeType", mimetype);
			recordIn.setAttribute("dettAttrLista", dettAttrLista);			
			lDatasource.performCustomOperation("importaDatiFromExcel", recordIn, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {							
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						RecordList valoriAttrLista = result.getAttributeAsRecordList("valoriAttrLista");						
						// Salvo i dati
						if (valoriAttrLista != null && valoriAttrLista.getLength() > 0) {
							Record values = new Record();
							values.setAttribute("tabellaAmmTrasparente", valoriAttrLista);
							values.setAttribute("tsPubblDal", dataInizioPubblicazioneItem.getValueAsDate());
							values.setAttribute("tsPubblAl", dataFinePubblicazioneItem.getValueAsDate());
							vm.setValue("datiExcel", values);
						}												
						RecordList listaDatiXlsInError = result.getAttributeAsRecordList("listaDatiXlsInError");
						if(listaDatiXlsInError != null && listaDatiXlsInError.getLength() > 0) {
							RecordList listaErrori = new RecordList();
							for (int i = 0; i < listaDatiXlsInError.getLength(); i++) {
								Record recordInError = listaDatiXlsInError.get(i);
								String numeroRiga = recordInError.getAttribute("numeroRiga");
								String motivo = recordInError.getAttribute("motivo");
								Record recordErrore = new Record();
								recordErrore.setAttribute("idError", numeroRiga);
								recordErrore.setAttribute("descrizione", motivo);
								listaErrori.add(recordErrore);										
							}									
							int numRecordTotali = valoriAttrLista!=null ? valoriAttrLista.getLength() + listaDatiXlsInError.getLength() : listaDatiXlsInError.getLength();
							ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Numero riga", listaErrori, numRecordTotali, LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS, "Righe in errore dell'excel importato");
							errorePopup.show();
						}
						else{
							//SC.say("Upload","Upload effettuato con successo.");
						}
							
					} 				
				}
			}, new DSRequest());
		}
		markForRedraw();
    }

	protected boolean validate() {	
		boolean vmValidate = true;
		vmValidate = form.validate();
		return vmValidate;
	}
	
	public abstract void onClickOkButton(Record object);
}