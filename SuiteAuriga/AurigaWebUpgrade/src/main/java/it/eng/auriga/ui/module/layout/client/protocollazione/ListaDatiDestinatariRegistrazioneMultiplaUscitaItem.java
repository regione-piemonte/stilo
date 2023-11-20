/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.utility.ui.module.core.client.callback.UploadMultipleItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileMultipleUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;

public abstract class ListaDatiDestinatariRegistrazioneMultiplaUscitaItem extends GridItem {

	protected ListGridField id;	
	protected ListGridField uriExcel;	
	protected ListGridField denominazioneCognome;
	protected ListGridField nome;	
	protected ListGridField mezzoTrasmissione;
	protected ListGridField email;
	protected ListGridField inviaEmail;
//	protected ListGridField documentoDiDebito;
//	protected ListGridField beneficiarioPagamentoImporto;

	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	protected ControlListGridField detailButtonField;
	protected ControlListGridField downloadButtonField;
	protected ControlListGridField previewButtonField;
	protected GridMultiToolStripButton downloadZipMultiButton;
	
	private List<String> uriFilesExcelToDelete = new ArrayList<String>();
	
	protected int count = 0;
	private int indexLastAdd = -1;
	private RecordList listaRecordCaricatiInUploadMultiplo = new RecordList();
	private RecordList listaRecordCaricatiInError;
	
	private final int ALT_POPUP_ERR_MASS = 375;
	private final int LARG_POPUP_ERR_MASS = 620;
	
	private int numFileCaricatiInErrore = 0;
	private int numFileCaricatiInUploadMultiploGlobal = 0;
	
	public ListaDatiDestinatariRegistrazioneMultiplaUscitaItem(String name) {

		super(name, "listaDatiDestinatariRegistrazioneMultiplaUscita", true);
		
		setGridPkField("id");
		setShowPreference(true);
		setShowDeleteButton(true);
				
		// Nascoste
		id       = new ListGridField("id");       id.setHidden(true);       id.setCanHide(false);         id.setCanEdit(false);
		uriExcel = new ListGridField("uriExcel"); uriExcel.setHidden(true); uriExcel.setCanHide(false);   uriExcel.setCanEdit(false);
		
		// Visibili
		denominazioneCognome = new ListGridField("denominazione_cognome", "Cognome");  			denominazioneCognome.setCanSort(true);	 denominazioneCognome.setCanEdit(false);	denominazioneCognome.setCanHide(false);       
		nome               	 = new ListGridField("nome", "Nome");                             	nome.setCanSort(true);               	 nome.setCanEdit(false);                	nome.setCanHide(false);
		mezzoTrasmissione 	 = new ListGridField("mezzo_trasmissione", "Mezzo trasmissione");   mezzoTrasmissione.setCanSort(true); 	 mezzoTrasmissione.setCanEdit(false);  		mezzoTrasmissione.setCanHide(false);
		email          		 = new ListGridField("email", "Email");                         	email.setCanSort(true);          	 	 email.setCanEdit(false);           		email.setCanHide(false);
		inviaEmail  		 = new ListGridField("invia_email", "Invia email");          		inviaEmail.setCanSort(true);   			 inviaEmail.setCanEdit(false);    			inviaEmail.setCanHide(false);
		
		deleteButtonField = buildDeleteButtonField();
		
		setGridFields(
			// Nascoste
			id,
			uriExcel,
			// Visibili
			denominazioneCognome,
			nome,
			mezzoTrasmissione,
			email,
			inviaEmail,
			deleteButtonField
		);	
	}
	
	@Override
	protected VLayout buildLayout() {
		return super.buildLayout();
	}
	
	@Override
	public ListGrid buildGrid() {
		
		ListGrid grid = super.buildGrid();
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
		grid.addSort(new SortSpecifier("denominazione_cognome", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("nome", SortDirection.ASCENDING));

		return grid;
	}
	
	//************************************************************
	//* Bottoni per la selezione singola
	//* *********************************************************
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();
		
		if(isShowEditButtons()) {
			
			if(isShowModifyButton()) {
				// Bottone modify
				if(modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();					
				}
				buttonsList.add(modifyButtonField);
			}
			if(isShowDeleteButton()) {
				// Bottone delete
				if(deleteButtonField == null) {
					deleteButtonField = buildDeleteButtonField();					
				}
				buttonsList.add(deleteButtonField);
			}
		} else {
			// Bottone detail
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
		}

		return buttonsList;
	}
	
	//************************************************************
	//* FormItem custom
	//* *********************************************************
	@Override
	public List<FormItem> buildCustomFormItem() {
		
		List<FormItem> formItems = new ArrayList<>();
		
		FileMultipleUploadItemWithFirmaAndMimeType uploadButton = new FileMultipleUploadItemWithFirmaAndMimeType(new UploadMultipleItemCallBackHandler() {

			@Override
			public void uploadEnd(String displayFileName, String uri, String numFileCaricatiInUploadMultiplo) {
				grid.deselectAllRecords();
				int nroLastAllegato = grid.getRecords().length;				
				final Record newRecord = new Record();
				newRecord.setAttribute("id", "NEW_" + count++);
				newRecord.setAttribute("changed", true);
				newRecord.setAttribute("numeroProgrAllegato", nroLastAllegato + 1);
				newRecord.setAttribute("numeroProgrAllegatoAfterDrop", nroLastAllegato + 1);
				newRecord.setAttribute("numFileCaricatiInUploadMultiplo", numFileCaricatiInUploadMultiplo);	
				newRecord.setAttribute("nomeFile", displayFileName);	
				if(uri != null && !"".equals(uri)) {
					newRecord.setAttribute("uriFileImportExcel", uri);
				}
				// mi recupero il numero totale di file selezionati in upload
				int numFileCaricati = numFileCaricatiInUploadMultiplo != null && !"".equals(numFileCaricatiInUploadMultiplo) ? Integer.parseInt(numFileCaricatiInUploadMultiplo) : 0;
				// se il numero è maggiore di 1, e quindi si tratta di un upload multiplo
				if(numFileCaricati > 1) {
					// mi recupero il numero progressivo del file di cui sto facendo l'upload
					int nroProgrFile = listaRecordCaricatiInUploadMultiplo.getLength() + 1;
					if(nroProgrFile == 1) {
						// a partire dal primo upload mostro la waitPopup
						Layout.showWaitPopup("Caricamento file in corso...");
						numFileCaricatiInUploadMultiploGlobal = numFileCaricati;
					}
					// se non ho ancora terminato l'upload di tutti i file
					if(nroProgrFile <= numFileCaricati) {
						// aggiungo il mio file a listaRecordCaricatiInUploadMultiplo senza fare l'addData(), quella la farò soltanto alla fine quando avrò recuperato tutti i file con le relative info
						listaRecordCaricatiInUploadMultiplo.add(newRecord);								
					} 
				}
				// se invece è un upload singolo
				else {
					// in questo caso faccio direttamente l'addData() e resetto listaRecordCaricatiInUploadMultiplo
					listaRecordCaricatiInUploadMultiplo.add(newRecord);						
				}
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				
				numFileCaricatiInErrore++;
				int nroProgrFile = listaRecordCaricatiInUploadMultiplo.getLength();
				if(nroProgrFile + numFileCaricatiInErrore == numFileCaricatiInUploadMultiploGlobal) {
					addAllDataAndRefresh(listaRecordCaricatiInUploadMultiplo, numFileCaricatiInUploadMultiploGlobal);
					listaRecordCaricatiInUploadMultiplo = new RecordList();
					numFileCaricatiInErrore = 0;
				}
			}

			@Override
			public void cancelMultipleUpload() {
				listaRecordCaricatiInUploadMultiplo = new RecordList();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) { 

				grid.deselectAllRecords();
				final Record record;
				// se è in corso un upload multiplo, e quindi listaRecordCaricatiInUploadMultiplo contiene degli elementi
				if(listaRecordCaricatiInUploadMultiplo.getLength() > 0) {
					// il record su cui dovò aggiornare le info sarà l'ultimo aggiunto a listaRecordCaricatiInUploadMultiplo
					record = listaRecordCaricatiInUploadMultiplo.get(listaRecordCaricatiInUploadMultiplo.getLength() - 1);
				} else if (indexLastAdd != -1) {
					record = grid.getRecords()[indexLastAdd];
				} else {
					int nroLastAllegato = grid.getRecords().length;					
					record = grid.getRecords()[nroLastAllegato - 1];
				}				
				InfoFileRecord precInfo = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				String displayName = record.getAttribute("nomeFileAllegato");
				String displayNameOrig = record.getAttribute("nomeFileAllegatoOrig");			
				final Record newRecord = new Record(record.getJsObj());
				newRecord.setAttribute("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta()) || 
					(displayName != null && !"".equals(displayName) && displayNameOrig != null && !"".equals(displayNameOrig) && !displayName.equals(displayNameOrig))) {
					newRecord.setAttribute("isChanged", true);
				}
				newRecord.setAttribute("improntaVerPreFirma", info.getImpronta());
				newRecord.setAttribute("infoFileVerPreFirma", info);
				// se è in corso un upload multiplo, e quindi listaRecordCaricatiInUploadMultiplo contiene degli elementi
				if(listaRecordCaricatiInUploadMultiplo.getLength() > 0) {
					// mi recupero il numero totale di file selezionati in upload
					int numFileCaricati = newRecord.getAttribute("numFileCaricatiInUploadMultiplo") != null ? Integer.valueOf(newRecord.getAttribute("numFileCaricatiInUploadMultiplo")) : 0;
					
					// mi recupero il numero progressivo del file di cui ho appena fatto l'upload
					int nroProgrFile = listaRecordCaricatiInUploadMultiplo.getLength();				
					// aggiorno in listaRecordCaricatiInUploadMultiplo le info del file di cui ho appena fatto l'upload
					listaRecordCaricatiInUploadMultiplo.set(listaRecordCaricatiInUploadMultiplo.getLength() - 1, record);
					// se sono arrivato alla fine e ho ancora terminato l'upload di tutti i file
					if(nroProgrFile == numFileCaricati) {
						// aggiungo tutti i record alla grid con addAllData()
						addAllDataAndRefresh(listaRecordCaricatiInUploadMultiplo, numFileCaricati);
						listaRecordCaricatiInUploadMultiplo = new RecordList();
					}
				} 
				// se è un upload singolo con updateData() aggiorno direttamente le info del file di cui ho appena fatto l'upload
				else {
					listaRecordCaricatiInUploadMultiplo.add(newRecord);
					addAllDataAndRefresh(listaRecordCaricatiInUploadMultiplo, 1);
					listaRecordCaricatiInUploadMultiplo = new RecordList();
				}
			}
		});
		uploadButton.setCanFocus(false);
		uploadButton.setTabIndex(-1);
		uploadButton.setPickerIconSrc("setPickerIconSrc");
		uploadButton.setPrompt("Importa dati da Excel");
				
		formItems.add(uploadButton);
		
		return formItems;
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();		
		
		ToolStripButton downloadTemplateExcelButton = new ToolStripButton();   
		downloadTemplateExcelButton.setIcon("file/download_manager.png");   
		downloadTemplateExcelButton.setIconSize(16);
		downloadTemplateExcelButton.setPrefix("downloadTemplateExcel");
		downloadTemplateExcelButton.setPrompt("Download template excel lista dei destinatari");
		downloadTemplateExcelButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickDownloadTemplateExcelButton();   	
			}   
		});  
		
		buttons.add(downloadTemplateExcelButton);

		return buttons;
	}
	
	public void onClickDownloadTemplateExcelButton() {
		
		String uriFileExcel = AurigaLayout.getParametroDB("URI_TEMPLATE_DEST_DA_XLS");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", "Destinatari.xlsx");
		lRecord.setAttribute("uri", uriFileExcel);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "true");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");				
	}
	
	private ControlListGridField buildDetailButtonField() {
		
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(!isShowEditButtons() || !isEditable()) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(!isShowEditButtons() || !isEditable()) {			
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(!isShowEditButtons() || !isEditable()) {
					event.cancel();
					onClickDetailButton(event.getRecord());		
				}
			}
		});		
		
		return detailButtonField;
	}
	
	private ControlListGridField buildModifyButtonField() {
		
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().modifyButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				}
			}
		});		
		
		return modifyButtonField;
	}
	
	private ControlListGridField buildDeleteButtonField() {
		
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(record)) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(event.getRecord())) {
					event.cancel();
					onClickDeleteButton(event.getRecord());
				}
			}
		});	
		
		return deleteButtonField;
	}
	
	private ControlListGridField buildPreviewButtonField() {
		
		ControlListGridField previewButtonField = new ControlListGridField("previewButton");  
		previewButtonField.setAttribute("custom", true);	
		previewButtonField.setShowHover(true);		
		previewButtonField.setCanReorder(false);
		previewButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowPreviewButton(record)) {
					return buildImgButtonHtml("export/xls.png");
				}
				return null;
			}
		});
		previewButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowPreviewButton(record)) {
					return "Preview excel";	
				}
				return null;
			}
		});		
		previewButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowPreviewButton(event.getRecord())) {
					event.cancel();
					onClickPreviewButton(event.getRecord());
				}
			}
		});	
		
		return previewButtonField;
	}
	
	private ControlListGridField buildDownloadButtonField() {
		
		ControlListGridField downloadButtonField = new ControlListGridField("downloadButton");  
		downloadButtonField.setAttribute("custom", true);	
		downloadButtonField.setShowHover(true);		
		downloadButtonField.setCanReorder(false);
		downloadButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowDownloadButton(record)) {
					return buildImgButtonHtml("file/download_manager.png");
				}
				return null;
			}
		});
		downloadButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return "Download file";	
				}
				return null;
			}
		});		
		downloadButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowDownloadButton(event.getRecord())) {
					event.cancel();
					onClickDownloadButton(event.getRecord());
				}
			}
		});	
		
		return downloadButtonField;
	}
	
	private void onClickDownloadButton(ListGridRecord record) {
		
		final Record recordInfoFile = new Record();
		recordInfoFile.setAttribute("uriFile", record.getAttribute("uriExcel"));
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
		lGwtRestDataSource.executecustom("calcolaInfoFile", recordInfoFile, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {					
					Record record = response.getData()[0];
					InfoFileRecord infoFile = new InfoFileRecord(record);					
					String fileExt = "";
					if (infoFile!=null){
						String mimetype = infoFile.getAttribute("mimetype");
						if(mimetype !=null && !mimetype.equalsIgnoreCase("")){
							if(mimetype.equalsIgnoreCase("application/excel")){
								fileExt = ".xls";
							}
							if(mimetype.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
								fileExt = ".xlsx";
							}
						}
					}					
					String nomeFile = "Lista_Destinatari"+ fileExt;
					String uri    = recordInfoFile.getAttribute("uriFile");
					Record lRecord = new Record();
					lRecord.setAttribute("displayFilename", nomeFile);
					lRecord.setAttribute("uri", uri);
					lRecord.setAttribute("sbustato", false);
					lRecord.setAttribute("remoteUri", true);
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");					
				} 	
			}
		});
	}

	private void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	private void onClickImportXlsButton(RecordList listaDestinatari, final int numeroFileCaricati) {
		
		Layout.showWaitPopup("Caricamento destinatari in corso...");
		final GWTRestDataSource lContabilitaADSPDataSource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
		Record rec = new Record ();
		rec.setAttribute("listaDati", listaDestinatari);
		lContabilitaADSPDataSource.performCustomOperation("importaDestinatariFromExcel", rec, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					int numRecCaricati = 0;
					Record record = response.getData()[0];
					RecordList attributeAsRecordList = record.getAttributeAsRecordList("listaFileCaricati");
					for (int i=0; i < attributeAsRecordList.getLength(); i++) {
						Record fileCaricato = attributeAsRecordList.get(i);
						RecordList errorList = fileCaricato.getAttributeAsRecordList("listaDestintariInError");
						RecordList listaDestinatari = fileCaricato.getAttributeAsRecordList("listaDestinatari");
						numRecCaricati += listaDestinatari.getLength();
						if (fileCaricato.getAttributeAsBoolean("isInError") != null && fileCaricato.getAttributeAsBoolean("isInError")) {
							for (int j=0; j < errorList.getLength(); j++) {
								Record rec = new Record ();
								rec.setAttribute("descrizione", errorList.get(j).getAttribute("errorMessage") != null ? errorList.get(j).getAttribute("errorMessage") : null);
								rec.setAttribute("idError", errorList.get(j).getAttribute("nomeFile"));
								listaRecordCaricatiInError.add(rec);
							}
						} else {
							for (int j=0; j < listaDestinatari.getLength(); j++) {
								addData(listaDestinatari.get(j));
								grid.refreshRow(grid.getRecordIndex(fileCaricato));
								indexLastAdd = grid.getRecordIndex(fileCaricato);
							}
						}
					}
					if (listaRecordCaricatiInError.getLength() > 0) {
						ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(getName(), "Nome File", listaRecordCaricatiInError, numRecCaricati, LARG_POPUP_ERR_MASS,
								ALT_POPUP_ERR_MASS, "Righe in errore dell'excel importato");
						errorePopup.show();
					}
				}
			}
		}, new DSRequest());
	}

	private void onClickPreviewButton(ListGridRecord record) {
		
		final Record recordInfoFile = new Record();
		recordInfoFile.setAttribute("uriFile", record.getAttribute("uriExcel"));
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
		lGwtRestDataSource.executecustom("calcolaInfoFile", recordInfoFile, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {					
					String uri    = recordInfoFile.getAttribute("uriFile");
					String displayFilename =  "Lista_Destinatari.pdf";
					Record record = response.getData()[0];
					InfoFileRecord infoFile = new InfoFileRecord(record);
					PreviewControl.switchPreview(uri, false, infoFile, "FileToExtractBean", displayFilename);
				} 	
			}
		});
	}
		
	private void onClickDetailButton(final ListGridRecord record) {		
	}
	
	private void onClickModifyButton(final ListGridRecord record) {		
	}
	
	private void onClickDownloadZipMultiButton(){
		
		final RecordList listaUriExcel = new RecordList();
		for (int i = 0; i < grid.getSelectedRecords().length; i++) {
			listaUriExcel.add(grid.getSelectedRecords()[i]);
		}
		Record record = new Record();
		record.setAttribute("listaRecord", listaUriExcel);
		
		Layout.showWaitPopup("Download zip in corso: potrebbe richiedere qualche secondo. Attendere…");
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RegistrazioneMultiplaUscitaDatasource");
		lGwtRestDataSource.executecustom("createZipExportDestinatari", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {					
					Record record = response.getData()[0];
					String message = record.getAttribute("message");
					if (message != null && !"".equals(message)) {
						Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
					} else {
						String uri = record.getAttribute("storageZipRemoteUri");
						String fileName = record.getAttribute("zipName");
						Record lRecord = new Record();
						lRecord.setAttribute("displayFilename", fileName);
						lRecord.setAttribute("uri", uri);
						lRecord.setAttribute("sbustato", "false");
						lRecord.setAttribute("remoteUri", true);
						DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
					}					
				} 	
			}
		});
		grid.deselectAllRecords();
	}

	public void addDataAndRefresh(final Record record) {
		addDataAndRefresh(record, false);
	}
	
	private void addDataAndRefresh(final Record record, boolean skipRefreshNroAllegato) {
		addData(record);	
		grid.refreshRow(grid.getRecordIndex(record));
		indexLastAdd = grid.getRecordIndex(record);
		if(!skipRefreshNroAllegato) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	    		
				@Override
				public void execute() {
					grid.selectSingleRecord(record);
					refreshNroAllegato();
				}
	    	});	
		}
	}
	
	private void addAllDataAndRefresh(final RecordList listaRecord, int numeroFileCaricati) {
		addAllDataAndRefresh(listaRecord, false, numeroFileCaricati);
	}
	
	private void addAllDataAndRefresh(final RecordList listaRecord, boolean skipRefreshNroAllegato, int numeroFileCaricati) {
		listaRecordCaricatiInError = new RecordList();
		RecordList listaDestinatari = new RecordList();
		for(int i = 0; i < listaRecord.getLength(); i++) {
			Record record = listaRecord.get(i);
			Record destinatario = new Record();
			InfoFileRecord infoFile = record.getAttribute("infoFile") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFile")) : null;
			if (infoFile == null || infoFile.getMimetype() == null 	|| (!infoFile.getMimetype().equals("application/excel") 
					&& !infoFile.getMimetype().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
				GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non riconosciuto o non ammesso. I formati validi risultano essere xls/xlsx", "", MessageType.ERROR));
			}else {
				destinatario.setAttribute("mimeType",infoFile.getMimetype());
				destinatario.setAttribute("uriExcel",record.getAttribute("uriFileImportExcel"));
				destinatario.setAttribute("nomeFile",record.getAttribute("nomeFile"));
				listaDestinatari.add(destinatario);
			}
		}		
		onClickImportXlsButton(listaDestinatari, numeroFileCaricati);
	}
	
	private void refreshNroAllegato() {
		List<String> listIdRecordsSel = getIdSelectedRecords();
		grid.deselectAllRecords();
		int n = 1;
		for (ListGridRecord record : grid.getRecords()) {
			record.setAttribute("numeroProgrAllegato", n);
			record.setAttribute("numeroProgrAllegatoAfterDrop", n);
			n++;
		}
		updateGridItemValue();		
		reselectRecords(listIdRecordsSel);
	}
	
	private List<String> getIdSelectedRecords() {
		List<String> listIdRecordsSel = new ArrayList<String>();		
		ListGridRecord[] recordsSel = grid.getSelectedRecords();
		for (ListGridRecord listGridRecord : recordsSel) {
			listIdRecordsSel.add(listGridRecord.getAttribute("id"));
		}		
		return listIdRecordsSel;
	}

	private void reselectRecords(List<String> listIdRecordsSel) {
		for (String idRecordToSel : listIdRecordsSel) {
			for (ListGridRecord listGridRecord : grid.getRecords()) {
				if (listGridRecord.getAttribute("id").equals(idRecordToSel)) {
					grid.selectRecord(listGridRecord);
				}
			}
		}
	}

	private void preview(final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, null, false, false);
	}
	
	private boolean isShowDeleteButton(Record record) {
		return true;
	}
		
	private boolean isShowPreviewButton(Record record) {
		final boolean show = record.getAttribute("uriExcel") != null && !record.getAttributeAsString("uriExcel").equals("");
		return show;
	}
	
	private boolean isShowDownloadButton(Record record) {
		final boolean show = record.getAttribute("uriExcel") != null && !record.getAttributeAsString("uriExcel").equals("");
		return show;
	}
	
	@Override	
	public boolean isShowEditButtons() {
		return true;
	}
	
	@Override	
	public boolean isShowModifyButton() {
		return false;
	}

	@Override
	public boolean isShowDeleteButton() {
		return true;
	}

	@Override
	public boolean isShowNewButton() {
		return false;
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {

	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields(getNomeLista(), fields);
	}
	
}