/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class VisualizzaVersioniFileList extends CustomList {

	private ListGridField nr;
	private ListGridField nome;
	private ListGridField iconaFirmata;
	private ListGridField iconaAcquisitaDaScanner;
	private ListGridField creataDa;
	private ListGridField creataIl;
	private ListGridField ultimoAggEffDa;
	private ListGridField dtUltimoAggEff;
	private ListGridField dimensione;

	protected ControlListGridField viewVersionButtonField;
	protected ControlListGridField downloadVersionButtonField;
	protected ControlListGridField attestatoConformitaOriginaleButtonField;
	private Record recordProtocollo;
	private boolean abilAttestatoConformitaButton;

	public VisualizzaVersioniFileList(String nomeEntita, Record estremi) {
		this(nomeEntita, estremi, true);
	}
	
	public VisualizzaVersioniFileList(String nomeEntita, Record recordProtocollo, boolean abilAttestatoConformitaButton) {
		super(nomeEntita);

		this.recordProtocollo = recordProtocollo;
		this.abilAttestatoConformitaButton = abilAttestatoConformitaButton;

		nr = new ListGridField("nr", I18NUtil.getMessages().versioni_file_list_numero());

		nome = new ListGridField("nome", I18NUtil.getMessages().versioni_file_list_nome());
		nome.setAttribute("custom", true);
		nome.setSortByDisplayField(false);
		nome.setDisplayField("nome");
		nome.setCanGroupBy(false);
		nome.setCanReorder(false);
		nome.setCanFreeze(false);
		nome.setShowDefaultContextMenu(false);
		nome.setCanDragResize(true);
		nome.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return "<div style=\"cursor:pointer;text-decoration:underline;\" align=\"center\">" + record.getAttribute("nome") + "</div>";
			}
		});
		nome.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return record.getAttribute("nome");
			}
		});
		nome.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				Record record = event.getRecord();
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					if (record != null && record.getAttributeAsString("uriFile") != null && !"".equals(record.getAttributeAsString("uriFile"))) {
						clickPreviewFile(record);
					}
				}
			}
		});

		iconaFirmata = new ListGridField("iconaFirmata", I18NUtil.getMessages().versioni_file_list_iconaFirmata());
		iconaFirmata.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFirmata.setType(ListGridFieldType.ICON);
		iconaFirmata.setAlign(Alignment.CENTER);
		iconaFirmata.setWrap(false);
		iconaFirmata.setWidth(30);
		iconaFirmata.setIconWidth(16);
		iconaFirmata.setIconHeight(16);
		iconaFirmata.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgConvertibilePdf = (String) record.getAttribute("iconaFirmata");
				if (flgConvertibilePdf != null && "1".equals(flgConvertibilePdf)) {
					return buildIconHtml("firma/firma.png");
				}
				return null;
			}
		});
		iconaFirmata.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String flgConvertibilePdf = (String) record.getAttribute("iconaFirmata");
				if (flgConvertibilePdf != null && "1".equals(flgConvertibilePdf)) {
					return "Firmata";
				}
				return null;
			}
		});
		iconaFirmata.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				Record record = event.getRecord();
				manageFileFirmatoButtonClick(record);
			}
		});

		iconaAcquisitaDaScanner = new ListGridField("iconaAcquisitaDaScanner", I18NUtil.getMessages().versioni_file_list_acq_scanner());
		iconaAcquisitaDaScanner.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaAcquisitaDaScanner.setType(ListGridFieldType.ICON);
		iconaAcquisitaDaScanner.setAlign(Alignment.CENTER);
		iconaAcquisitaDaScanner.setWrap(false);
		iconaAcquisitaDaScanner.setWidth(30);
		iconaAcquisitaDaScanner.setIconWidth(16);
		iconaAcquisitaDaScanner.setIconHeight(16);
		iconaAcquisitaDaScanner.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String iconaAcquisitaDaScanner = (String) record.getAttribute("iconaAcquisitaDaScanner");
				if (iconaAcquisitaDaScanner != null && "1".equals(iconaAcquisitaDaScanner)) {
					return buildIconHtml("file/scanner.png");
				}
				return null;
			}
		});
		iconaAcquisitaDaScanner.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String iconaAcquisitaDaScanner = (String) record.getAttribute("iconaAcquisitaDaScanner");
				if (iconaAcquisitaDaScanner != null && "1".equals(iconaAcquisitaDaScanner)) {
					return "Acquisita da scanner";
				}
				return null;
			}
		});

		creataDa = new ListGridField("creataDa", I18NUtil.getMessages().versioni_file_list_creataDa());

		creataIl = new ListGridField("creataIl", I18NUtil.getMessages().versioni_file_list_creataIl());
		creataIl.setType(ListGridFieldType.DATE);
		creataIl.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		ultimoAggEffDa = new ListGridField("ultimoAggEffDa", I18NUtil.getMessages().versioni_file_list_modDa());

		dtUltimoAggEff = new ListGridField("dtUltimoAggEff", I18NUtil.getMessages().versioni_file_list_modIl());
		dtUltimoAggEff.setType(ListGridFieldType.DATE);
		dtUltimoAggEff.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		dimensione = new ListGridField("dimensione", I18NUtil.getMessages().versioni_file_list_dim());

		setFields(nr, nome, iconaFirmata, iconaAcquisitaDaScanner, creataDa, creataIl, ultimoAggEffDa, dtUltimoAggEff, dimensione);

	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {
		
		return false;
	}

	@Override
	protected boolean showDeleteButtonField() {
		
		return false;
	}

	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		return false;
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		return false;
	}

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();
		if (viewVersionButtonField == null) {
			viewVersionButtonField = buildViewVersionButtonField();
		}

		if (downloadVersionButtonField == null) {
			downloadVersionButtonField = buildDownloadVersionButtonField();
		}

		if (attestatoConformitaOriginaleButtonField == null) {
			attestatoConformitaOriginaleButtonField = buildAttestatoConformitaOriginanButtonField();
		}

		buttonsFields.add(0, viewVersionButtonField);
		buttonsFields.add(1, downloadVersionButtonField);
		
		if (isAbilAttestatoConformitaButton())
			buttonsFields.add(2, attestatoConformitaOriginaleButtonField);
		
		return buttonsFields;
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	public void clickPreviewFile(final Record record) {

		final String display = record.getAttributeAsString("nome");
		final String uri = record.getAttributeAsString("uriFile");
		final Boolean remoteUri = true;
		Record infoRecord = getInfoFileRecord(record);
		final InfoFileRecord info = new InfoFileRecord(infoRecord);
		String idUd = recordProtocollo.getAttributeAsString("idUd");
		String idDoc = recordProtocollo.getAttributeAsString("idDocIn");
		String numeroVersione = record.getAttributeAsString("nr");
		addToRecent(idUd, idDoc, numeroVersione);
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {

					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						preview(record, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(record, display, uri, remoteUri, info);
		}
	}

	private Record getInfoFileRecord(final Record record) {
		Record infoRecord = new Record();
		infoRecord.setAttribute("tipoFirma", record.getAttributeAsString("nome") != null && record.getAttributeAsString("nome").toLowerCase().endsWith(".p7m") ? "CAdES" : "");
		infoRecord.setAttribute("firmato", record.getAttributeAsString("iconaFirmata") != null && "1".equals(record.getAttributeAsString("iconaFirmata")) ? true : false);
		infoRecord.setAttribute("mimetype", record.getAttributeAsString("mimetype"));
		infoRecord.setAttribute("impronta", record.getAttributeAsString("impronta"));
		infoRecord.setAttribute("bytes", new Long(record.getAttributeAsString("dimensione")));
		infoRecord.setAttribute("convertibile", record.getAttributeAsString("flgConvertibilePdf") != null && "1".equals(record.getAttributeAsString("flgConvertibilePdf")) ? true : false);
		infoRecord.setAttribute("correctFileName", record.getAttributeAsString("nome"));
		return infoRecord;
	}

	public void preview(final Record detailRecord, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {

		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// in realtà il costruttore effettua anche lo show, quindi non è necessario inizializzare una variabile
		// @SuppressWarnings("unused")
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri, info, "FileToExtractBean", display);
	}

	protected void manageFileFirmatoButtonClick(Record record) {

		Record recordInfoFile = getInfoFileRecord(record);
		final InfoFileRecord infoFileRecord = new InfoFileRecord(recordInfoFile);
		final String uriFilePrimario = record.getAttributeAsString("uriFile");

		MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");
		informazioniFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uriFilePrimario, false);
			}
		});

		MenuItem stampaFileCertificazioneMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_stampaFileCertificazione_prompt(),
				"protocollazione/stampaEtichetta.png");
		stampaFileCertificazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				generaFileFirmaAndPreview(infoFileRecord, uriFilePrimario, true);
			}
		});

		final Menu fileFirmatoMenu = new Menu();
		fileFirmatoMenu.setOverflow(Overflow.VISIBLE);
		fileFirmatoMenu.setShowIcons(true);
		fileFirmatoMenu.setSelectionType(SelectionStyle.SINGLE);
		fileFirmatoMenu.setCanDragRecordsOut(false);
		fileFirmatoMenu.setWidth("*");
		fileFirmatoMenu.setHeight("*");

		fileFirmatoMenu.addItem(informazioniFirmaMenuItem);
		fileFirmatoMenu.addItem(stampaFileCertificazioneMenuItem);

		fileFirmatoMenu.showContextMenu();
	}

	private void generaFileFirmaAndPreview(final InfoFileRecord infoFilePrimario, String uriFilePrimario, boolean aggiungiFirma) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFilePrimario);
		record.setAttribute("uriAttach", uriFilePrimario);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));
		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
					String filename = infoFile.getCorrectFileName();
					String uri = data.getAttribute("tempFileOut");
					preview(null, null, filename, uri, false, infoFile);
				} else {
					Layout.addMessage(new MessageBean("Attenzione,non è possibile visualizzare le informazioni della firma del file", "", MessageType.WARNING));
				}
			}
		});
	}

	public void preview(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// in realtà il costruttore effettua anche lo show, quindi non è necessario inizializzare una variabile
		// @SuppressWarnings("unused")
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri, info, "FileToExtractBean", display);
	}

	public ControlListGridField buildViewVersionButtonField() {
		ControlListGridField viewButtonField = new ControlListGridField("viewVersion");
		viewButtonField.setAttribute("custom", true);
		viewButtonField.setShowHover(true);
		viewButtonField.setCanReorder(false);
		viewButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					return buildImgButtonHtml("buttons/view.png");
				}
				return null;
			}
		});
		viewButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					return "Visualizza";
				}
				return null;
			}
		});
		viewButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					if (record != null && record.getAttributeAsString("uriFile") != null && !"".equals(record.getAttributeAsString("uriFile"))) {
						clickPreviewFile(record);
					}
				}
			}
		});
		return viewButtonField;
	}

	public ControlListGridField buildAttestatoConformitaOriginanButtonField() {
		ControlListGridField attestatoConformitaButtonField = new ControlListGridField("attestatoConformita");
		attestatoConformitaButtonField.setAttribute("custom", true);
		attestatoConformitaButtonField.setShowHover(true);
		attestatoConformitaButtonField.setCanReorder(false);
		attestatoConformitaButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					return buildImgButtonHtml("file/attestato.png");
				}
				return null;
			}
		});
		attestatoConformitaButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					return I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem();
				}
				return null;
			}
		});
		attestatoConformitaButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					if (record != null && record.getAttributeAsString("uriFile") != null && !"".equals(record.getAttributeAsString("uriFile"))) {
						clickAttestatoConformita(record);
					}
				}
			}
		});
		return attestatoConformitaButtonField;
	}

	public ControlListGridField buildDownloadVersionButtonField() {
		ControlListGridField downloadButtonField = new ControlListGridField("downloadVersion");
		downloadButtonField.setAttribute("custom", true);
		downloadButtonField.setShowHover(true);
		downloadButtonField.setCanReorder(false);
		downloadButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					return buildImgButtonHtml("file/download_manager.png");
				}
				return null;
			}
		});
		downloadButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					return "Scarica";
				}
				return null;
			}
		});
		downloadButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				if (record != null && record.getAttributeAsString("flgConvertibilePdf") != null
						&& "1".equals(record.getAttributeAsString("flgConvertibilePdf"))) {
					if (record != null && record.getAttributeAsString("uriFile") != null && !"".equals(record.getAttributeAsString("uriFile"))) {
						clickDownloadFile(record);
					}
				}
			}
		});
		return downloadButtonField;
	}

	public void clickAttestatoConformita(Record record) {
		Record infoFileRecord = getInfoFileRecord(record);
		final InfoFileRecord fileAllegato = infoFileRecord != null ? new InfoFileRecord(infoFileRecord) : null;
		final String uri = record.getAttributeAsString("uriFile");
		final String idUd = recordProtocollo.getAttributeAsString("idUd");
		final String idDoc = recordProtocollo.getAttributeAsString("idDocIn");

		SC.ask("Vuoi firmare digitalmente l’attestato ?", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {

				if (value) {
					creaAttestato(idUd, idDoc, fileAllegato, uri, true);
				} else
					creaAttestato(idUd, idDoc, fileAllegato, uri, false);

			}
		});
	}

	protected void creaAttestato(final String idUd, String idDoc, InfoFileRecord infoFileAllegato, String uriFileAllegato, final boolean attestatoFirmato) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFileAllegato);
		record.setAttribute("uriAttach", uriFileAllegato);
		if(recordProtocollo != null) {
			record.setAttribute("siglaProtocollo", recordProtocollo.getAttribute("siglaProtocollo"));
			record.setAttribute("nroProtocollo", recordProtocollo.getAttribute("nroProtocollo"));
			record.setAttribute("desUserProtocollo", recordProtocollo.getAttribute("desUserProtocollo"));
			record.setAttribute("desUOProtocollo", recordProtocollo.getAttribute("desUOProtocollo"));
			try {
				Date dataProtocollo = recordProtocollo != null ? recordProtocollo.getAttributeAsDate("dataProtocollo") : null;
				String dataProtocolloStr = DateUtil.formatAsShortDatetime(dataProtocollo);
				record.setAttribute("dataProtocollo", dataProtocolloStr);
			} catch(Exception e) {
				record.setAttribute("dataProtocollo", recordProtocollo.getAttributeAsString("dataProtocollo"));
			}
		}
		record.setAttribute("idUd", idUd);
		record.setAttribute("idDoc", idDoc);
		record.setAttribute("attestatoFirmatoDigitalmente", attestatoFirmato);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
		lGwtRestDataSource.extraparam.put("attestatoFirmato", Boolean.toString(attestatoFirmato));
		lGwtRestDataSource.extraparam.put("urlContext", GWT.getHostPageBaseURL());

		lGwtRestDataSource.executecustom("stampaAttestato", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record data = response.getData()[0];

				final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				final String filename = infoFile.getCorrectFileName();
				final String uri = data.getAttribute("tempFileOut");
				if (!attestatoFirmato) {

					Record lRecord = new Record();
					lRecord.setAttribute("displayFilename", filename);
					lRecord.setAttribute("uri", uri);
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", false);
					lRecord.setAttribute("infoFile", infoFile);

					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				} else {
					FirmaUtility.firmaMultipla(uri, filename, infoFile, new FirmaCallback() {

						@Override
						public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {

							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", nomeFileFirmato);
							lRecord.setAttribute("uri", uriFileFirmato);
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", false);
							lRecord.setAttribute("infoFile", info);

							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						}

					});
				}
			}
		});
	}

	// Download del file
	public void clickDownloadFile(ListGridRecord record) {
		String display = record.getAttributeAsString("nome");
		String uri = record.getAttributeAsString("uriFile");
		String dimensioneString = record.getAttribute("dimensione");
		long dimensione = 0;
		try {
			dimensione = Long.parseLong(dimensioneString);
		}catch (Exception e) {
			dimensione = 0;
		}
		
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", true);
		// La dimensione nel campo dimensione è in KB
		Record infoFileRecord = new Record();
		infoFileRecord.setAttribute("bytes", dimensione * 1000);
		lRecord.setAttribute("infoFile", infoFileRecord);
		String idUd = recordProtocollo.getAttributeAsString("idUd");
		String idDoc = recordProtocollo.getAttributeAsString("idDocIn");
		String numeroVersione = record.getAttributeAsString("nr");
		addToRecent(idUd, idDoc, numeroVersione);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}


	

	public boolean isAbilAttestatoConformitaButton() {
		return abilAttestatoConformitaButton;
	}

	public void setAbilAttestatoConformitaButton(
			boolean abilAttestatoConformitaButton) {
		this.abilAttestatoConformitaButton = abilAttestatoConformitaButton;
	}
	
	public void addToRecent(String idUd, String idDoc, String numeroVerione) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			record.setAttribute("nroProgrVer", numeroVerione);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
				}
			});
		}
	}

}
