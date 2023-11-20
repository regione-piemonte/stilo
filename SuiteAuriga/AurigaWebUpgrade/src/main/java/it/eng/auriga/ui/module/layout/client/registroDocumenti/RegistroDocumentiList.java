/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class RegistroDocumentiList extends CustomList {

	private ListGridField idUd;
	private ListGridField codDocumento;
	private ListGridField nome;
	private ListGridField idDoc;
	private ListGridField oggetto;
	private ListGridField stato;
	private ListGridField codiceSocieta;
	private ListGridField codiceCliente;

	private ListGridField codFiscPIVA;
	private ListGridField descrizioneCliente;
	private ListGridField codiceTipoDocumento;
	private ListGridField codiceProcesso;
	private ListGridField tipoServizio;
	private ListGridField codicePRD_POD;
	private ListGridField dataDocumento;
	private ListGridField numeroDocumento;
	private ListGridField tipoFattura;
	private ListGridField codiceContratto;
	private ListGridField codiceFornitura;
	private ListGridField sezionale;
	private ListGridField statoConservazione;

	private ControlListGridField visualizzaDocumentoButtonField;
	private ControlListGridField scaricaDocumentoButtonField;

	public RegistroDocumentiList(String nomeEntita) {

		super(nomeEntita);

		idUd = new ListGridField("idUd", I18NUtil.getMessages().registro_documenti_idud());
		idUd.setHidden(true);
		idUd.setCanHide(false);

		codDocumento = new ListGridField("codDocumento", I18NUtil.getMessages().registro_documenti_codice_documento());
		codDocumento.setHidden(true);
		codDocumento.setCanHide(false);

		idDoc = new ListGridField("idDoc");
		idDoc.setHidden(true);
		idDoc.setCanHide(false);

		oggetto = new ListGridField("oggetto", I18NUtil.getMessages().registro_documenti_oggetto());
		nome = new ListGridField("nome", I18NUtil.getMessages().registro_documenti_nome());
		stato = new ListGridField("stato", I18NUtil.getMessages().registro_documenti_stato());
		codiceSocieta = new ListGridField("codiceSocieta", I18NUtil.getMessages().registro_documenti_codice_societa());
		codFiscPIVA = new ListGridField("codFiscPIVA", I18NUtil.getMessages().registro_documenti_cod_fisc_PIVA());
		dataDocumento = new ListGridField("dataDocumento", I18NUtil.getMessages().registro_documenti_data_documento());
		codiceCliente = new ListGridField("codiceCliente", I18NUtil.getMessages().registro_documenti_codice_cliente());
		descrizioneCliente = new ListGridField("descrizioneCliente", I18NUtil.getMessages().registro_documenti_descrizione_cliente());
		codiceTipoDocumento = new ListGridField("codiceTipoDocumento", I18NUtil.getMessages().registro_documenti_codice_tipo_documento());
		codiceProcesso = new ListGridField("codiceProcesso", I18NUtil.getMessages().registro_documenti_codice_processo());
		tipoServizio = new ListGridField("tipoServizio", I18NUtil.getMessages().registro_documenti_tipo_servizio());
		numeroDocumento = new ListGridField("numeroDocumento", I18NUtil.getMessages().registro_documenti_numero_documento());
		tipoFattura = new ListGridField("tipoFattura", I18NUtil.getMessages().registro_documenti_tipo_fattura());
		codiceContratto = new ListGridField("codiceContratto", I18NUtil.getMessages().registro_documenti_codice_contratto());
		codiceFornitura = new ListGridField("codiceFornitura", I18NUtil.getMessages().registro_documenti_codice_fornitura());
		sezionale = new ListGridField("sezionale", I18NUtil.getMessages().registro_documenti_sezionale());
		codicePRD_POD = new ListGridField("codicePRD_POD", I18NUtil.getMessages().registro_documenti_codice_prd_pod());
		statoConservazione = new ListGridField("statoConservazione", I18NUtil.getMessages().registro_documenti_stato_conservazione());

		setFields(idUd, idDoc, codDocumento, nome, stato, oggetto, codiceSocieta, codiceCliente, descrizioneCliente, codiceContratto, codFiscPIVA,
				dataDocumento, numeroDocumento, codiceTipoDocumento, codiceProcesso, tipoServizio, tipoFattura, codiceFornitura, sezionale, codicePRD_POD,
				statoConservazione);
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 75;
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

	public void scaricaFileVersDocumento(Record detailRecord, Record versioneRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDoc");
		String display = versioneRecord.getAttributeAsString("nomeFile");
		String uri = versioneRecord.getAttributeAsString("uriFile");
		String remoteUri = versioneRecord.getAttributeAsString("remoteUri");
		scaricaFile(idUd, idDoc, display, uri, remoteUri);
	}

	public void scaricaFileVersDocumentoSbustato(Record detailRecord, Record versioneRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDoc");
		String display = versioneRecord.getAttributeAsString("nomeFile");
		String uri = versioneRecord.getAttributeAsString("uriFile");
		String remoteUri = versioneRecord.getAttributeAsString("remoteUri");
		Object infoFile = versioneRecord.getAttributeAsObject("infoFile");
		scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void scaricaFileSbustato(String idUd, String idDoc, String display, String uri, String remoteUri, Object infoFile) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void scaricaFile(String idUd, String idDoc, String display, String uri, String remoteUri) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void visualizzaFileVersDocumento(Record detailRecord, Record versioneRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDoc");
		String display = versioneRecord.getAttributeAsString("nomeFile");
		String uri = versioneRecord.getAttributeAsString("uriFile");
		String remoteUri = versioneRecord.getAttributeAsString("remoteUri");
		Object infoFile = versioneRecord.getAttributeAsObject("infoFile");
		visualizzaFile(idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFile(String idUd, String idDoc, final String display, final String uri, final String remoteUri, Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
	}

	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

				}
			});
		}
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
		return RegistroDocumentiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return RegistroDocumentiLayout.isAbilToDel();
	}

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();
		if (!isDisableRecordComponent()) {
			super.getButtonsFields();
		} else {
			if (visualizzaDocumentoButtonField == null) {
				visualizzaDocumentoButtonField = buildVisualizzaDocumentoButtonField();
			}
			buttonsList.add(visualizzaDocumentoButtonField);

			if (scaricaDocumentoButtonField == null) {
				scaricaDocumentoButtonField = buildScaricaDocumentoButtonField();
			}
			buttonsList.add(scaricaDocumentoButtonField);
		}
		return buttonsList;
	}

	protected boolean isRecordWithFile(ListGridRecord record) {
		Integer nroDocConFile = (record.getAttributeAsString("nroDocConFile") != null && !"".equals(record.getAttributeAsString("nroDocConFile"))) ? new Integer(
				record.getAttributeAsString("nroDocConFile")) : 0;
		return (nroDocConFile > 0);
	}

	public ControlListGridField buildScaricaDocumentoButtonField() {
		ControlListGridField scaricaDocumentoButtonField = new ControlListGridField("scaricaDocumentoButton");
		scaricaDocumentoButtonField.setAttribute("custom", true);
		scaricaDocumentoButtonField.setShowHover(true);
		scaricaDocumentoButtonField.setCanReorder(false);
		scaricaDocumentoButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordWithFile(record)) {
					return buildImgButtonHtml("file/download_manager.png");
				}
				return null;
			}
		});
		scaricaDocumentoButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordWithFile(record)) {
					return I18NUtil.getMessages().registro_documenti_scarica_documento_title();
				}
				return null;
			}
		});
		scaricaDocumentoButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				if (isRecordWithFile(record)) {
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RegistroDocumentiDataSource");
					lGwtRestDataSource.getData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							final Record detailRecord = response.getData()[0];
							RecordList listaVersioni = detailRecord.getAttributeAsRecordList("listaVersioni");
							if (listaVersioni != null && listaVersioni.getLength() > 0) {
								if (listaVersioni.getLength() == 1) {
									final Record versioneRecord = listaVersioni.get(0);
									String nomeFile = versioneRecord.getAttribute("nomeFile");
									boolean isFirmato = versioneRecord.getAttribute("flgFirmato") != null
											&& "1".equals(versioneRecord.getAttribute("flgFirmato"));
									if (isFirmato && nomeFile.toUpperCase().endsWith("P7M") || nomeFile.toUpperCase().endsWith("TSD")) {
										Menu scaricaFileSubMenu = new Menu();
										MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
										firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												scaricaFileVersDocumento(detailRecord, versioneRecord);
											}
										});
										MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
										sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												scaricaFileVersDocumentoSbustato(detailRecord, versioneRecord);
											}
										});
										scaricaFileSubMenu.setItems(firmato, sbustato);
										scaricaFileSubMenu.showContextMenu();
									} else {
										scaricaFileVersDocumento(detailRecord, versioneRecord);
									}
								} else {
									Menu versioniMenu = new Menu();
									MenuItem[] versioniMenuItems = new MenuItem[listaVersioni.getLength()];
									for (int i = 0; i < listaVersioni.getLength(); i++) {
										final Record versioneRecord = listaVersioni.get(i);
										String nroVersione = versioneRecord.getAttribute("nroVersione");
										String nomeFile = versioneRecord.getAttribute("nomeFile");
										boolean isFirmato = versioneRecord.getAttribute("flgFirmato") != null
												&& "1".equals(versioneRecord.getAttribute("flgFirmato"));
										boolean isValida = versioneRecord.getAttribute("flgValida") != null
												&& "1".equals(versioneRecord.getAttribute("flgValida"));
										String title = "";
										if (listaVersioni.getLength() > 1) {
											title += "Vers.&nbsp;" + nroVersione + "&nbsp;-&nbsp;";
										}
										title += nomeFile;
										if (isFirmato) {
											title += "&nbsp;<img src=\"images/coccarda.png\" height=\"12\" width=\"12\" />";
										}
										if (!isValida) {
											title += "&nbsp;<img src=\"images/ko.png\" height=\"12\" width=\"12\" />";
										}
										versioniMenuItems[i] = new MenuItem(title);
										if (isFirmato && nomeFile.toUpperCase().endsWith("P7M") || nomeFile.toUpperCase().endsWith("TSD")) {
											Menu scaricaFileSubMenu = new Menu();
											MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
											firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

												@Override
												public void onClick(MenuItemClickEvent event) {
													scaricaFileVersDocumento(detailRecord, versioneRecord);
												}
											});
											MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
											sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

												@Override
												public void onClick(MenuItemClickEvent event) {
													scaricaFileVersDocumentoSbustato(detailRecord, versioneRecord);
												}
											});
											scaricaFileSubMenu.setItems(firmato, sbustato);
											versioniMenuItems[i].setSubmenu(scaricaFileSubMenu);
										} else {
											versioniMenuItems[i].addClickHandler(new ClickHandler() {

												@Override
												public void onClick(MenuItemClickEvent event) {
													
													scaricaFileVersDocumento(detailRecord, versioneRecord);
												}
											});
										}
										versioniMenu.setItems(versioniMenuItems);
									}
									versioniMenu.showContextMenu();
								}
							}
						}
					});
				}
			}
		});
		return scaricaDocumentoButtonField;
	}

	public ControlListGridField buildVisualizzaDocumentoButtonField() {
		ControlListGridField visualizzaDocumentoButtonField = new ControlListGridField("visualizzaDocumentoButton");
		visualizzaDocumentoButtonField.setAttribute("custom", true);
		visualizzaDocumentoButtonField.setShowHover(true);
		visualizzaDocumentoButtonField.setCanReorder(false);
		visualizzaDocumentoButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (isRecordWithFile(record)) {
					return buildImgButtonHtml("buttons/view.png");
				}
				return null;
			}
		});
		visualizzaDocumentoButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordWithFile(record)) {
					return I18NUtil.getMessages().registro_documenti_visualizza_documento_title();
				}
				return null;
			}
		});
		visualizzaDocumentoButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				if (isRecordWithFile(record)) {
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RegistroDocumentiDataSource");
					lGwtRestDataSource.getData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							final Record detailRecord = response.getData()[0];
							RecordList listaVersioni = detailRecord.getAttributeAsRecordList("listaVersioni");
							if (listaVersioni != null && listaVersioni.getLength() > 0) {
								if (listaVersioni.getLength() == 1) {
									final Record versioneRecord = listaVersioni.get(0);
									visualizzaFileVersDocumento(detailRecord, versioneRecord);
								} else {
									Menu versioniMenu = new Menu();
									MenuItem[] versioniMenuItems = new MenuItem[listaVersioni.getLength()];
									for (int i = 0; i < listaVersioni.getLength(); i++) {
										final Record versioneRecord = listaVersioni.get(i);
										String nroVersione = versioneRecord.getAttribute("nroVersione");
										String nomeFile = versioneRecord.getAttribute("nomeFile");
										boolean isFirmato = versioneRecord.getAttribute("flgFirmato") != null
												&& "1".equals(versioneRecord.getAttribute("flgFirmato"));
										boolean isValida = versioneRecord.getAttribute("flgValida") != null
												&& "1".equals(versioneRecord.getAttribute("flgValida"));
										String title = "";
										if (listaVersioni.getLength() > 1) {
											title += "Vers.&nbsp;" + nroVersione + "&nbsp;-&nbsp;";
										}
										title += nomeFile;
										if (isFirmato) {
											title += "&nbsp;<img src=\"images/coccarda.png\" height=\"12\" width=\"12\" />";
										}
										if (!isValida) {
											title += "&nbsp;<img src=\"images/ko.png\" height=\"12\" width=\"12\" />";
										}
										versioniMenuItems[i] = new MenuItem(title);
										versioniMenuItems[i].addClickHandler(new ClickHandler() {

											@Override
											public void onClick(MenuItemClickEvent event) {
												
												visualizzaFileVersDocumento(detailRecord, versioneRecord);
											}
										});
										versioniMenu.setItems(versioniMenuItems);
									}
									versioniMenu.showContextMenu();
								}
							}
						}
					});
				}
			}
		});
		return visualizzaDocumentoButtonField;
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}
