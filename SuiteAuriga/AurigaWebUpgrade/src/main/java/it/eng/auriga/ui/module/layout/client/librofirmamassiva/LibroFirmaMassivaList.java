/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class LibroFirmaMassivaList extends CustomList {
	
	private ListGridField idDocField;
	private ListGridField nroVerField;
	
	private ListGridField estremiNroProvvisorioField;
	private ListGridField estremiNroProvvisorioXOrdField;
	private ListGridField estremiNroAttoField;
	private ListGridField estremiNroAttoXOrdField;
	private ListGridField tipoDocumentoField;
	private ListGridField tsInvioFirmaField;
	private ListGridField autoreInvioInFirmaField;
	private ListGridField nomeTaskAutocompletamentoField;
	private ListGridField bustaCrittograficaFattaDaAurigaField;
	private ListGridField firmeNonValideBustaCrittograficaField;
	private ListGridField tipoBustaCrittograficaField;
	private ListGridField flgFirmatoField;
	private ListGridField idUdField;
	private ListGridField idProcessField;
	private ListGridField displayFilenameField;
	private ListGridField uriFileField;
	private ListGridField mimetypeField;
	private ListGridField flgPdfizzabileField;
	private ListGridField activityName;
	private ListGridField nroAllegato;
	private ListGridField flgAtto;

	protected ControlListGridField verificaFirmaButton;
	protected ControlListGridField scaricaButton;
	protected ControlListGridField previewButton;
	
	public LibroFirmaMassivaList(String nomeEntita) {
		
		super(nomeEntita);			
		
		activityName = new ListGridField("activityName");
		activityName.setHidden(true);
		activityName.setCanHide(false);
		
		idDocField = new ListGridField("idDoc");
		idDocField.setHidden(true);
		idDocField.setCanHide(false);

		nroVerField = new ListGridField("nroVer");
		nroVerField.setHidden(true);
		nroVerField.setCanHide(false);

		flgFirmatoField = new ListGridField("flgFirmato");
		flgFirmatoField.setHidden(true);
		flgFirmatoField.setCanHide(false);

		idUdField = new ListGridField("idUd");
		idUdField.setHidden(true);
		idUdField.setCanHide(false);

		idProcessField = new ListGridField("idProcess");
		idProcessField.setHidden(true);
		idProcessField.setCanHide(false);

		displayFilenameField = new ListGridField("displayFilename");
		displayFilenameField.setHidden(true);
		displayFilenameField.setCanHide(false);

		uriFileField = new ListGridField("uriFile");
		uriFileField.setHidden(true);
		uriFileField.setCanHide(false);

		mimetypeField = new ListGridField("mimetype");
		mimetypeField.setHidden(true);
		mimetypeField.setCanHide(false);

		flgPdfizzabileField = new ListGridField("flgPdfizzabile");
		flgPdfizzabileField.setHidden(true);
		flgPdfizzabileField.setCanHide(false);

		estremiNroProvvisorioField = new ListGridField("estremiNroProvvisorio", "N.ro provvisorio atto");
		estremiNroProvvisorioField.setHidden(true);
		estremiNroProvvisorioField.setCanHide(false);
		
		estremiNroProvvisorioXOrdField = new ListGridField("estremiNroProvvisorioXOrd", "N.ro provvisorio atto");
		estremiNroProvvisorioXOrdField.setDisplayField("estremiNroProvvisorio");
		estremiNroProvvisorioXOrdField.setSortByDisplayField(false);
		estremiNroProvvisorioXOrdField.setCanSort(true);
		
		estremiNroAttoField = new ListGridField("estremiNroAtto", "N.ro definitivo atto");
		estremiNroAttoField.setHidden(true);
		estremiNroAttoField.setCanHide(false);
		
		estremiNroAttoXOrdField = new ListGridField("estremiNroAttoXOrd", "N.ro definitivo atto");
		estremiNroAttoXOrdField.setDisplayField("estremiNroAtto");
		estremiNroAttoXOrdField.setSortByDisplayField(false);
		estremiNroAttoXOrdField.setCanSort(true);
		
		bustaCrittograficaFattaDaAurigaField = new ListGridField("bustaCrittograficaFattaDaAuriga");
		bustaCrittograficaFattaDaAurigaField.setHidden(true);
		bustaCrittograficaFattaDaAurigaField.setCanHide(false);
		
		firmeNonValideBustaCrittograficaField = new ListGridField("firmeNonValideBustaCrittografica");
		firmeNonValideBustaCrittograficaField.setHidden(true);
		firmeNonValideBustaCrittograficaField.setCanHide(false);
		
		tipoBustaCrittograficaField = new ListGridField("tipoBustaCrittografica");
		tipoBustaCrittograficaField.setHidden(true);
		tipoBustaCrittograficaField.setCanHide(false);

		tipoDocumentoField = new ListGridField("tipoDocumento", "Tipo documento da firmare");
		
		tsInvioFirmaField = new ListGridField("tsInvioFirma", "Invio al libro firma del");
		tsInvioFirmaField.setType(ListGridFieldType.DATETIME);
		
		autoreInvioInFirmaField = new ListGridField("autoreInvioInFirma", "Inviato in firma da");
		
		nomeTaskAutocompletamentoField = new ListGridField("nomeTaskAutocompletamento", "Passo da completare con firma");		

		nroAllegato = new ListGridField("nroAllegato");
		nroAllegato.setHidden(true);
		nroAllegato.setCanHide(false);
		
		flgAtto = new ListGridField("flgAtto");
		flgAtto.setHidden(true);
		flgAtto.setCanHide(false);

		setFields(new ListGridField[] {
				idDocField,
				nroVerField,
				flgFirmatoField,
				idUdField,
				idProcessField,
				displayFilenameField,
				uriFileField,
				mimetypeField,
				flgPdfizzabileField,
				estremiNroProvvisorioField,
				estremiNroProvvisorioXOrdField,
				estremiNroAttoField,
				estremiNroAttoXOrdField,
				bustaCrittograficaFattaDaAurigaField,
				firmeNonValideBustaCrittograficaField,
				tipoBustaCrittograficaField,
				tipoDocumentoField,
				tsInvioFirmaField,
				autoreInvioInFirmaField,
				nomeTaskAutocompletamentoField,
				activityName,
				nroAllegato,
				flgAtto
		});  
		
	}
	
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}
	
	@Override
	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButton = new ControlListGridField("detailButton");  
		detailButton.setAttribute("custom", true);	
		detailButton.setShowHover(true);		
		detailButton.setCanReorder(false);
		detailButton.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				return buildImgButtonHtml("buttons/detail.png");								
			}
		});
		detailButton.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					
				return I18NUtil.getMessages().detailButton_prompt();					
			}
		});		
		detailButton.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				detailButtonClick(record);
			}
		});
		return detailButton;
	}
	
	protected void detailButtonClick(ListGridRecord record) {
		String title = "";
		if (record.getAttribute("estremiNroAtto") != null && !"".equals(record.getAttributeAsString("estremiNroAtto"))) {
			title = "Dettaglio atto N° " + record.getAttributeAsString("estremiNroAtto");
		} else {
			title = "Dettaglio proposta N° " + record.getAttributeAsString("estremiNroProvvisorio");
		}	
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttributeAsString("idUd"));		
		if(record.getAttributeAsString("idProcess") != null && !"".equals(record.getAttributeAsString("idProcess"))) {
			lRecord.setAttribute("idProcess", record.getAttributeAsString("idProcess"));
			lRecord.setAttribute("activityName", record.getAttributeAsString("activityName"));
			AurigaLayout.apriDettaglioPraticaSenzaFlusso(lRecord, title, false);
		} else {
			new DettaglioRegProtAssociatoWindow(lRecord, title);		
		}
	
	}

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();
		
		buttonsList = super.getButtonsFields();
		
		if (verificaFirmaButton == null) {
			verificaFirmaButton = new ControlListGridField("verificaFirmaButton");  
			verificaFirmaButton.setAttribute("custom", true);	
			verificaFirmaButton.setShowHover(true);		
			verificaFirmaButton.setCanReorder(false);
			verificaFirmaButton.setCellFormatter(new CellFormatter() {			
				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
					if(isRecordAbilVerificaFirma(record)) {
						if (isRecordFirmeNonValideBustaCrittografica(record)) {
							// Mostro ugualmente la coccarda normale
//							return buildImgButtonHtml("firma/firmaNonValida.png");
							return buildImgButtonHtml("firma/firma.png");
						} else {
							return buildImgButtonHtml("firma/firma.png");
						}
					}
					return null;
				}
			});
			verificaFirmaButton.setHoverCustomizer(new HoverCustomizer() {				
				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						
					return "Verifica firma";					
				}
			});		
			verificaFirmaButton.addRecordClickHandler(new RecordClickHandler() {				
				@Override
				public void onRecordClick(RecordClickEvent event) {
					
					event.cancel();
					ListGridRecord record = getRecord(event.getRecordNum());
					verificaFirma(record);
				}
			});
		}
		buttonsList.add(verificaFirmaButton);

		if (scaricaButton == null) {
			scaricaButton = new ControlListGridField("scaricaButton");  
			scaricaButton.setAttribute("custom", true);	
			scaricaButton.setShowHover(true);		
			scaricaButton.setCanReorder(false);
			scaricaButton.setCellFormatter(new CellFormatter() {			
				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
					return buildImgButtonHtml("file/download_manager.png");								
				}
			});
			scaricaButton.setHoverCustomizer(new HoverCustomizer() {				
				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						
					return "Scarica file";					
				}
			});		
			scaricaButton.addRecordClickHandler(new RecordClickHandler() {				
				@Override
				public void onRecordClick(RecordClickEvent event) {
					
					event.cancel();
					ListGridRecord record = getRecord(event.getRecordNum());
					scaricaFile(record);
				}
			});
		}
		
		buttonsList.add(scaricaButton);

		if (previewButton == null) {
			previewButton = new ControlListGridField("anteprimaVRCButton");
			previewButton.setAttribute("custom", Boolean.TRUE);
			previewButton.setShowHover(Boolean.TRUE);
			previewButton.setCanReorder(Boolean.FALSE);
			previewButton.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if(isRecordAbilToAnteprima(record)) {
						return buildImgButtonHtml("file/preview.png");
					} 
					return null;
				}
			});
			previewButton.setHoverCustomizer(new HoverCustomizer() {

				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					if(isRecordAbilToAnteprima(record)) {
						return "Anteprima file";							
					}
					return null;
				}
			});
			previewButton.addRecordClickHandler(new RecordClickHandler() {

				@Override
				public void onRecordClick(RecordClickEvent event) {
					event.cancel();
					final ListGridRecord record = getRecord(event.getRecordNum());
					if(isRecordAbilToAnteprima(record)) {						
						preview(record);
					}
				}
			});	
		}
	
		buttonsList.add(previewButton);
		
		return buttonsList;
	}

	protected void verificaFirma(final ListGridRecord pRecord) {
		
		getLayout().getDatasource().performCustomOperation("getFile", pRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (dsResponse.getData() != null && dsResponse.getData().length > 0 ) {
						Record lRecordFile = dsResponse.getData()[0];
						if (lRecordFile != null && lRecordFile.getAttribute("uri") != null) {

							InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(lRecordFile.getAttributeAsRecord("infoFile").getJsObj()));
							Record record = new Record();
							record.setAttribute("infoFileAttach", infoFilePdf);
							record.setAttribute("uriAttach", pRecord.getAttributeAsString("uriFile"));
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
							lGwtRestDataSource.extraparam.put("aggiungiFirma", "false");
							lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Record data = response.getData()[0];
									InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
									String filename = infoFile.getCorrectFileName();
									String uri = data.getAttribute("tempFileOut");
									previewFile(uri, infoFile, filename);
								}
							});
						}
					}
				}
			}
		});
		
	
		
	}

	protected boolean isRecordAbilVerificaFirma(ListGridRecord record) {
		return record.getAttribute("flgFirmato") != null &&  "1".equals(record.getAttribute("flgFirmato"));
	}
	
	protected boolean isRecordFirmeNonValideBustaCrittografica(ListGridRecord record) {
		return record.getAttribute("firmeNonValideBustaCrittografica") != null &&  "1".equals(record.getAttribute("firmeNonValideBustaCrittografica"));
	}
	
	protected boolean isRecordAbilToAnteprima(ListGridRecord record) {
		return record.getAttribute("flgPdfizzabile") != null &&  "1".equals(record.getAttribute("flgPdfizzabile"));
	}
	
	public void scaricaFile(Record pRecord) {
		String nomeFile = pRecord.getAttributeAsString("displayFilename");
		String uriFile = pRecord.getAttributeAsString("uriFile");
		
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", nomeFile);
		lRecord.setAttribute("uri", uriFile);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "true");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");		
	}
	
	public void preview(Record lRecord) {
		getLayout().getDatasource().performCustomOperation("getFile", lRecord, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record lRecordFile = response.getData()[0];
					if (lRecordFile != null && lRecordFile.getAttribute("uri") != null) {
						InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(lRecordFile.getAttributeAsRecord("infoFile").getJsObj()));
						previewFile(lRecordFile.getAttributeAsString("uri"), infoFile, lRecordFile.getAttributeAsString("nomeFile"));
					} else {
						Layout.addMessage(new MessageBean("Errore durante il recupero del file", "", MessageType.ERROR));
					}
				}
			}
		}, new DSRequest());
	}

	protected void previewFile(String uriFile, InfoFileRecord infoFile, String nomeFile) {
		PreviewControl.switchPreview(uriFile, false, infoFile, "FileToExtractBean", nomeFile);
	}
	
}
