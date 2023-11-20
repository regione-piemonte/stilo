/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

/**
 * 
 * @author DANCRIST
 *
 */

public class MonitoraggioPdVList extends CustomList {
	
	private ListGridField idPdVField;
	private ListGridField etichettaField;
	//private ListGridField nomeProcessoBancaProdField;
	private ListGridField numDocumentiPdVField;
	private ListGridField dimensioneField;
	private ListGridField statoField;
	private ListGridField dataUltimoAggStatoField;
	private ListGridField dataGenerazionePdVField;
	private ListGridField codErroreTrasmissionePdVField;
	private ListGridField messaggioErroreTrasmissionePdVField;
	private ListGridField codErroreRecuperoRdVField;
	private ListGridField messaggioErroreRecuperoRdVField;
	private ListGridField tempoRitornoRicevutaAccRifTrasmField;
	private ListGridField tempoRitornoRdVField;	
	private ListGridField flgFileIndiceXmlField;	
	private ListGridField flgFileInfPdVField;	
	private ListGridField flgFileRicevutaTrasmField;	
	private ListGridField flgFileRdVField;	
	
	protected ControlListGridField downloadFileButtonField;
	protected ControlListGridField documentiPdVButtonField;
	
	public MonitoraggioPdVList(String nomeEntita) {
	
		super(nomeEntita);
		
		idPdVField = new ListGridField("idPdV", I18NUtil.getMessages().monitoraggioPdV_list_idPdVField_title());
		idPdVField.setWrap(false);
		
		etichettaField = new ListGridField("etichetta", I18NUtil.getMessages().monitoraggioPdV_list_etichettaField_title());
		etichettaField.setWrap(false);
		
		statoField = new ListGridField("stato", I18NUtil.getMessages().monitoraggioPdV_list_statoField_title());
		statoField.setWrap(false);

		dataUltimoAggStatoField = new ListGridField("dataUltimoAggStato", I18NUtil.getMessages().monitoraggioPdV_list_dataUltimoAggStatoField_title());
		dataUltimoAggStatoField.setWrap(false);
		dataUltimoAggStatoField.setType(ListGridFieldType.DATE);
		dataUltimoAggStatoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		dataGenerazionePdVField = new ListGridField("dataGenerazionePdV", I18NUtil.getMessages().monitoraggioPdV_list_dataGenerazionePdVField_title());
		dataGenerazionePdVField.setWrap(false);
		dataGenerazionePdVField.setType(ListGridFieldType.DATE);
		dataGenerazionePdVField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		//nomeProcessoBancaProdField  = new ListGridField("nomeProcessoBancaProd", I18NUtil.getMessages().monitoraggioPdV_list_nomeProcessoBancaProdField_title());
		//nomeProcessoBancaProdField.setWrap(false);
		
		numDocumentiPdVField = new ListGridField("numDocumentiPdV", I18NUtil.getMessages().monitoraggioPdV_list_numDocumentiPdVField_title());
		numDocumentiPdVField.setWrap(false);
		numDocumentiPdVField.setType(ListGridFieldType.INTEGER);
			
		dimensioneField = new ListGridField("dimensione", I18NUtil.getMessages().monitoraggioPdV_list_dimensioneField_title());
		dimensioneField.setWrap(false);
		dimensioneField.setType(ListGridFieldType.INTEGER);
		
		codErroreTrasmissionePdVField = new ListGridField("codErroreTrasmissionePdV", I18NUtil.getMessages().monitoraggioPdV_list_codErroreTrasmissionePdVField_title());
		codErroreTrasmissionePdVField.setWrap(false);
		
		messaggioErroreTrasmissionePdVField = new ListGridField("messaggioErroreTrasmissionePdV", I18NUtil.getMessages().monitoraggioPdV_list_messaggioErroreTrasmissionePdVField_title());
		messaggioErroreTrasmissionePdVField.setWrap(false);
		
		codErroreRecuperoRdVField = new ListGridField("codErroreRecuperoRdV", I18NUtil.getMessages().monitoraggioPdV_list_codErroreRecuperoRdVField_title());
		codErroreRecuperoRdVField.setWrap(false);
		
		messaggioErroreRecuperoRdVField = new ListGridField("messaggioErroreRecuperoRdV", I18NUtil.getMessages().monitoraggioPdV_list_messaggioErroreRecuperoRdVField_title());
		messaggioErroreRecuperoRdVField.setWrap(false);
		
		tempoRitornoRicevutaAccRifTrasmField = new ListGridField("tempoRitornoRicevutaAccRifTrasm", I18NUtil.getMessages().monitoraggioPdV_list_tempoRitornoRicevutaAccRifTrasmField_title());
		tempoRitornoRicevutaAccRifTrasmField.setWrap(false);
		tempoRitornoRicevutaAccRifTrasmField.setType(ListGridFieldType.INTEGER);
		
		tempoRitornoRdVField = new ListGridField("tempoRitornoRdV", I18NUtil.getMessages().monitoraggioPdV_list_tempoRitornoRdVField_title());
		tempoRitornoRdVField.setWrap(false);
		tempoRitornoRdVField.setType(ListGridFieldType.INTEGER);
		
		flgFileIndiceXmlField = new ListGridField("flgFileIndiceXml");
		flgFileIndiceXmlField.setHidden(true);
		flgFileIndiceXmlField.setCanHide(false);
		flgFileIndiceXmlField.setCanGroupBy(false);
		flgFileIndiceXmlField.setCanReorder(false);
		flgFileIndiceXmlField.setCanSort(false);
		flgFileIndiceXmlField.setCanFreeze(false);
		flgFileIndiceXmlField.setCanExport(false);
		flgFileIndiceXmlField.setShowHover(false);
		
		flgFileInfPdVField = new ListGridField("flgFileInfPdV");
		flgFileInfPdVField.setHidden(true);
		flgFileInfPdVField.setCanHide(false);
		flgFileInfPdVField.setCanGroupBy(false);
		flgFileInfPdVField.setCanReorder(false);
		flgFileInfPdVField.setCanSort(false);
		flgFileInfPdVField.setCanFreeze(false);
		flgFileInfPdVField.setCanExport(false);
		flgFileInfPdVField.setShowHover(false);
		
		flgFileRicevutaTrasmField = new ListGridField("flgFileRicevutaTrasm");
		flgFileRicevutaTrasmField.setHidden(true);
		flgFileRicevutaTrasmField.setCanHide(false);
		flgFileRicevutaTrasmField.setCanGroupBy(false);
		flgFileRicevutaTrasmField.setCanReorder(false);
		flgFileRicevutaTrasmField.setCanSort(false);
		flgFileRicevutaTrasmField.setCanFreeze(false);
		flgFileRicevutaTrasmField.setCanExport(false);
		flgFileRicevutaTrasmField.setShowHover(false);
		
		flgFileRdVField = new ListGridField("flgFileRdV");
		flgFileRdVField.setHidden(true);
		flgFileRdVField.setCanHide(false);
		flgFileRdVField.setCanGroupBy(false);
		flgFileRdVField.setCanReorder(false);
		flgFileRdVField.setCanSort(false);
		flgFileRdVField.setCanFreeze(false);
		flgFileRdVField.setCanExport(false);
		flgFileRdVField.setShowHover(false);
		
		setFields(new ListGridField[] {
			idPdVField,
			etichettaField,
			statoField,
			dataUltimoAggStatoField,
			dataGenerazionePdVField,
			//nomeProcessoBancaProdField,
			numDocumentiPdVField,
			dimensioneField,
			codErroreTrasmissionePdVField,
			messaggioErroreTrasmissionePdVField,
			codErroreRecuperoRdVField,
			messaggioErroreRecuperoRdVField,
			tempoRitornoRicevutaAccRifTrasmField,
			tempoRitornoRdVField,
			flgFileIndiceXmlField,
			flgFileInfPdVField,
			flgFileRicevutaTrasmField,	
			flgFileRdVField	
		});
	}
	
	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}
	
	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

	@Override  
	protected List<ControlListGridField> getButtonsFields() {
		
		List<ControlListGridField> buttonsFields = super.getButtonsFields();	
		
		if (showDownloadFileButtonField()) {
			if (downloadFileButtonField == null) {
				downloadFileButtonField = buildDownloadFileButtonField();
			}
			buttonsFields.add(downloadFileButtonField);
		}
		
		if (showDocumentiPdVButtonField()) {
			if (documentiPdVButtonField == null) {
				documentiPdVButtonField = buildDocumentiPdVButtonField();
			}
			buttonsFields.add(documentiPdVButtonField);
		}	
		
		return buttonsFields;	
	}
	
	//******************************************************************************
	// Bottone DOCUMENTI PDV
	//******************************************************************************
		
	protected boolean showDocumentiPdVButtonField() {
		return true;
	}
	
	protected boolean isRecordAbilToDocumentiPdV(ListGridRecord record) {		
		String numDocumentiPdV = record != null ? record.getAttributeAsString("numDocumentiPdV").replace(".", "") : null;
		return numDocumentiPdV != null && !"".equals(numDocumentiPdV) && Long.valueOf(numDocumentiPdV).longValue() > 0;
	}
	
	protected ControlListGridField buildDocumentiPdVButtonField() {
		
		ControlListGridField documentiPdVButtonField = new ControlListGridField("documentiPdVButton");  
		documentiPdVButtonField.setAttribute("custom", true);	
		documentiPdVButtonField.setShowHover(true);		
		documentiPdVButtonField.setCanReorder(false);
		documentiPdVButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordAbilToDocumentiPdV(record)) {
					return buildImgButtonHtml("buttons/documenti.png");			
				} 
				return buildIconHtml("blank.png");		
			}
		});
		documentiPdVButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToDocumentiPdV(record)) {
					return I18NUtil.getMessages().monitoraggioPdV_list_documentiPdVButtonField_title("");
				}
				return null;
			}
		});		
		documentiPdVButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				if(isRecordAbilToDocumentiPdV(record)) {
					manageDocumentiPdVButtonClick(record);
				}
			}
		});		
		return documentiPdVButtonField;
	}
	
	public void manageDocumentiPdVButtonClick(ListGridRecord record){
		String idPdV = record != null ? record.getAttribute("idPdV") : null;
		DocumentiPdVWindow lDocumentiPdVWindow = new DocumentiPdVWindow(idPdV);
		lDocumentiPdVWindow.show();
	}
	
	// ******************************************************************************
	// Bottone SCARICA FILE INDICE
	// ******************************************************************************

	protected boolean showDownloadFileButtonField() {
		return true;
	}

	protected boolean isRecordAbilToDownloadFile(ListGridRecord record) {
		String flgFileIndiceXml = record != null ? record.getAttributeAsString("flgFileIndiceXml") : null;
		String flgFileInfPdV = record != null ? record.getAttributeAsString("flgFileInfPdV") : null;
		String flgFileRicevutaTrasm = record != null ? record.getAttributeAsString("flgFileRicevutaTrasm") : null;
		String flgFileRdV = record != null ? record.getAttributeAsString("flgFileRdV") : null;
		
		return (flgFileIndiceXml != null && !"".equals(flgFileIndiceXml) && "true".equalsIgnoreCase(flgFileIndiceXml)) ||
			   (flgFileInfPdV != null && !"".equals(flgFileInfPdV) && "true".equalsIgnoreCase(flgFileInfPdV)) ||
			   (flgFileRicevutaTrasm != null && !"".equals(flgFileRicevutaTrasm) && "true".equalsIgnoreCase(flgFileRicevutaTrasm)) ||
			   (flgFileRdV != null && !"".equals(flgFileRdV) && "true".equalsIgnoreCase(flgFileRdV));
	}

	protected ControlListGridField buildDownloadFileButtonField() {

		ControlListGridField downloadFileButtonField = new ControlListGridField("downloadFileButton");
		downloadFileButtonField.setAttribute("custom", true);
		downloadFileButtonField.setShowHover(true);
		downloadFileButtonField.setCanReorder(false);
		downloadFileButtonField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordAbilToDownloadFile(record)) {
					return buildImgButtonHtml("file/download_manager.png");
				}
				return buildIconHtml("blank.png");
			}
		});
		downloadFileButtonField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordAbilToDownloadFile(record)) {
					return I18NUtil.getMessages().monitoraggioPdV_list_downloadFileButtonField_title();
				}
				return null;
			}
		});
		downloadFileButtonField.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				if (isRecordAbilToDownloadFile(record)) {
					manageDownloadFileButtonClick(record);
				}
			}
		});
		return downloadFileButtonField;
	}

	public void manageDownloadFileButtonClick(final ListGridRecord record) {
		Menu downloadFileMenu = new Menu();
		String flgFileIndiceXml = record != null ? record.getAttributeAsString("flgFileIndiceXml") : null;
		if(flgFileIndiceXml != null && !"".equals(flgFileIndiceXml) && "true".equalsIgnoreCase(flgFileIndiceXml)) {
			MenuItem downloadFileIndiceXmlMenuItem = new MenuItem(I18NUtil.getMessages().monitoraggioPdV_list_downloadFileIndiceXmlMenuItem_title(), "blank.png");
			downloadFileIndiceXmlMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String rowIdRecord = record != null ? record.getAttribute("rowIdRecord") : null;
					String etichetta = record != null ? record.getAttribute("etichetta") : null;
					String nomeFile = " PdV_" + etichetta;
					scaricaBlob(nomeFile, ".xml", "DMT_PDV_X_CONSERV", "XML_PDV", rowIdRecord);
				}
			});
			downloadFileMenu.addItem(downloadFileIndiceXmlMenuItem);
		}
		String flgFileInfPdV = record != null ? record.getAttributeAsString("flgFileInfPdV") : null;
		if(flgFileInfPdV != null && !"".equals(flgFileInfPdV) && "true".equalsIgnoreCase(flgFileInfPdV)) {
			MenuItem downloadFileInfPdVMenuItem = new MenuItem(I18NUtil.getMessages().monitoraggioPdV_list_downloadFileInfPdVMenuItem_title(), "blank.png");
			downloadFileInfPdVMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String rowIdRecord = record != null ? record.getAttribute("rowIdRecord") : null;
					String etichetta = record != null ? record.getAttribute("etichetta") : null;
					String nomeFile = etichetta + "_inf_PdV";
					scaricaBlob(nomeFile, ".inf", "DMT_PDV_X_CONSERV", "FILE_INF_PDV", rowIdRecord);
				}
			});
			downloadFileMenu.addItem(downloadFileInfPdVMenuItem);
		}
		String flgFileRicevutaTrasm = record != null ? record.getAttributeAsString("flgFileRicevutaTrasm") : null;
		if(flgFileRicevutaTrasm != null && !"".equals(flgFileRicevutaTrasm) && "true".equalsIgnoreCase(flgFileRicevutaTrasm)) {
			MenuItem downloadFileRicevutaTrasmMenuItem = new MenuItem(I18NUtil.getMessages().monitoraggioPdV_list_downloadFileRicevutaTrasmMenuItem_title(), "blank.png");
			downloadFileRicevutaTrasmMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String rowIdRecord = record != null ? record.getAttribute("rowIdRecord") : null;
					String etichetta = record != null ? record.getAttribute("etichetta") : null;
					String nomeFile = etichetta + "_ricevuta_trasm";
					scaricaBlob(nomeFile, ".xml", "DMT_PDV_X_CONSERV", "RICEVUTA_TRASM", rowIdRecord);
				}
			});
			downloadFileMenu.addItem(downloadFileRicevutaTrasmMenuItem);
		}
		String flgFileRdV = record != null ? record.getAttributeAsString("flgFileRdV") : null;
		if(flgFileRdV != null && !"".equals(flgFileRdV) && "true".equalsIgnoreCase(flgFileRdV)) {
			
			final String urlRdv = record != null ? record.getAttributeAsString("urlRdV") : null;
			
			MenuItem downloadFileSbustatoRdVMenuItem = null;
			MenuItem downloadFileNonSbustatoRdVMenuItem = new MenuItem(I18NUtil.getMessages().monitoraggioPdV_list_downloadFileRdVMenuItem_title(), "blank.png");
			downloadFileNonSbustatoRdVMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					
					String rowIdRecord = record != null ? record.getAttribute("rowIdRecord") : null;
					String urlRdv = record != null ? record.getAttributeAsString("urlRdV") : null;
					String etichetta = record != null ? record.getAttribute("etichetta") : null;
					String nomeFile = "RdV_" + etichetta + ".xml.p7m";
					if(urlRdv != null && !"".equals(urlRdv)){
						downloadFile(nomeFile, urlRdv, "true");
					}else{
						scaricaBlob(nomeFile, ".xml", "DMT_PDV_X_CONSERV", "RICEVUTA_TRASM", rowIdRecord);
					}
				}
			});
			//downloadFileMenu.addItem(downloadFileNonSbustatoRdVMenuItem);
			if(urlRdv != null && !"".equals(urlRdv)){
				downloadFileSbustatoRdVMenuItem = new MenuItem(I18NUtil.getMessages().monitoraggioPdV_list_downloadFileSbustatoRdVMenuItem_title(), "blank.png");
				downloadFileSbustatoRdVMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						
						String etichetta = record != null ? record.getAttribute("etichetta") : null;
						String nomeFile = "RdV_"+ etichetta + ".xml";
						if(urlRdv != null && !"".equals(urlRdv)){
							downloadFileSbustato(nomeFile, urlRdv, "true");
						}
					}
				});
				//downloadFileMenu.addItem(downloadFileSbustatoRdVMenuItem);
			}
			Boolean isFileP7M = record != null && record.getAttributeAsString("fileP7M") != null &&
					"1".equalsIgnoreCase(record.getAttributeAsString("fileP7M")) ? true : false;
			Boolean isUrlRdv = urlRdv != null && !"".equals(urlRdv) ? true : false;			
			if(isUrlRdv && isFileP7M) {
				downloadFileMenu.addItem(downloadFileNonSbustatoRdVMenuItem);
				downloadFileMenu.addItem(downloadFileSbustatoRdVMenuItem);
			} else if(isUrlRdv && !isFileP7M){
				downloadFileMenu.addItem(downloadFileNonSbustatoRdVMenuItem);
			}
		}
		downloadFileMenu.showContextMenu();		
	}
	
	public void scaricaBlob(String nomeFile, String extFile, String nomeTabella, String nomeColonna,
			String rowIdRecord) {		
		
		Record record = new Record();
		record.setAttribute("nomeFile", nomeFile);
		record.setAttribute("extFile", extFile);
		record.setAttribute("nomeTabella", nomeTabella);
		record.setAttribute("nomeColonna", nomeColonna);
		record.setAttribute("rowIdRecord", rowIdRecord);
		
		GWTRestDataSource datasource = new GWTRestDataSource("GetClobFromTabColDataSource");
		datasource.executecustom("downloadBlob", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];
					if (result != null && result.getAttribute("message") != null) {
						Layout.addMessage(new MessageBean(result.getAttribute("message"), "", MessageType.ERROR));
					} else if (result.getAttribute("errorCode") == null || result.getAttribute("errorCode").isEmpty()) {
						String uriFile = response.getData()[0].getAttribute("uriFile");
						String nomeFile = response.getData()[0].getAttribute("nomeFile");
						downloadFile(nomeFile, uriFile, "true");
					}
				}else{
					Layout.addMessage(new MessageBean("Errore durante il download del file", "", MessageType.ERROR));
				}
			}
		});
	}	

	// Download del file
	public void downloadFile(String display, String uri, String remoteUri) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	// Download del file firmato sbustato
	public void downloadFileSbustato(String display, String uri, String remoteUri) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
}
