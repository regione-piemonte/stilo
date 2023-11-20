/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.istanze.IstanzeWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
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
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class IstanzePortaleRiscossioneDaIstruireList extends CustomList {

	private ListGridField nrIstanzaField;
	private ListGridField oggettoField;
//	private ListGridField nrAllegatiField;
	private ListGridField tipoIstanzaField;
	private ListGridField tsIstanzaField;
	private ListGridField tributoField;
	private ListGridField annoRiferimentoField;
	private ListGridField numeroDocRiferimentoField;
	private ListGridField classificazioneField;
	private ListGridField nomeContribuenteField;
	private ListGridField cfContribuenteField;
	private ListGridField codACSContribuenteField;
	private ListGridField idUdField;
	private ListGridField uriDocElettronicoField;
	private ListGridField nomeDocElettronicoField;
	private ListGridField nroDocConFileField;
	//private ListGridField mezzoTrasmissioneField;

	private ControlListGridField visualizzaDocButtonField;
	private ControlListGridField visualizzaAllegatiButtonField;

	public IstanzePortaleRiscossioneDaIstruireList(String nomeEntita) {

		super(nomeEntita);

		// Colonne invisibili
		idUdField = new ListGridField("idUdFolder");
		idUdField.setHidden(true);
		idUdField.setCanHide(false);
		uriDocElettronicoField = new ListGridField("uriDocElettronico");
		uriDocElettronicoField.setHidden(true);
		uriDocElettronicoField.setCanHide(false);
		nomeDocElettronicoField = new ListGridField("nomeDocElettronico");
		nomeDocElettronicoField.setHidden(true);
		nomeDocElettronicoField.setCanHide(false);
		nroDocConFileField = new ListGridField("nroDocConFile");
		nroDocConFileField.setHidden(true);
		nroDocConFileField.setCanHide(false);

		// Colonne visibili
		nrIstanzaField = new ListGridField("nrIstanza", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_nrIstanzaField_title());
		
		oggettoField = new ListGridField("oggetto", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_oggettoField_title());
		
//		nrAllegatiField = new ListGridField("nrAllegati", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_nrAllegatiField_title());
		
		tipoIstanzaField = new ListGridField("tipoIstanza", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_tipoIstanzaField_title());
		
		tributoField = new ListGridField("tributo", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_tributoField_title());
		
		annoRiferimentoField = new ListGridField("annoRiferimento", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_annoRiferimentoField_title());
		annoRiferimentoField.setType(ListGridFieldType.INTEGER);
		
		numeroDocRiferimentoField = new ListGridField("numeroDocRiferimento", I18NUtil.getMessages()
				.istanze_portale_riscossione_da_istruire_numeroDocRiferimentoField_title());
		
		classificazioneField = new ListGridField("classificazione", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_classificazioneField_title());
		
		tsIstanzaField = new ListGridField("tsIstanza", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_tsIstanzaField_title());
		tsIstanzaField.setType(ListGridFieldType.DATE);
		tsIstanzaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		nomeContribuenteField = new ListGridField("nomeContribuente", I18NUtil.getMessages()
				.istanze_portale_riscossione_da_istruire_nomeContribuenteField_title());
		
		cfContribuenteField = new ListGridField("cfContribuente", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_cfContribuenteField_title());
		
		codACSContribuenteField = new ListGridField("codACSContribuente", I18NUtil.getMessages()
				.istanze_portale_riscossione_da_istruire_codACSContribuenteField_title());
		
		//mezzoTrasmissioneField = new ListGridField("mezzoTrasmissione", I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_mezzoTrasmissioneField_title());

		setFields(new ListGridField[] { idUdField, uriDocElettronicoField, nomeDocElettronicoField, nroDocConFileField, nrIstanzaField, oggettoField,
				/*nrAllegatiField,*/ tipoIstanzaField, tributoField, annoRiferimentoField, numeroDocRiferimentoField, classificazioneField, tsIstanzaField,
				nomeContribuenteField, cfContribuenteField, codACSContribuenteField/*, mezzoTrasmissioneField*/});
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 100;
	}

	public void manageLoadDetail(final String mode, final Record record, final int recordNum, final DSCallback callback) {

		if (record.getAttribute("flgUdFolder").equals("U")) {
			String idUd = record.getAttribute("idUdFolder");

			String nomeEntita = "";
			if (record.getAttribute("tipoIstanza").equals("CED")) {
				nomeEntita = "istanze_ced";
			}
			if (record.getAttribute("tipoIstanza").equals("AUTOTUTELA")) {
				nomeEntita = "istanze_autotutela";
			}
			IstanzeWindow lIstanzeWindow = new IstanzeWindow(nomeEntita, null) {

				@Override
				public void manageAfterCloseWindow() {
					layout.doSearch();
				}
			};

			if (mode.equalsIgnoreCase("view"))
				lIstanzeWindow.viewRecord(idUd);

			if (mode.equalsIgnoreCase("edit"))
				lIstanzeWindow.editRecord(idUd);		
		}
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();
		if (!isDisableRecordComponent()) {
			super.getButtonsFields();
		} else {
			if (showViewDocButtonField()) {
				if (visualizzaDocButtonField == null) {
					visualizzaDocButtonField = buildVisualizzaDocButtonField();
				}
				buttonsList.add(visualizzaDocButtonField);
			}

			if (showDetailButtonField()) {
				if (detailButtonField == null) {
					detailButtonField = buildDetailButtonField();
				}
				buttonsList.add(detailButtonField);
			}
			if (showModifyButtonField()) {
				if (modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();
				}
				buttonsList.add(modifyButtonField);
			}
			if (showViewAllegatiButtonField()) {
				if (visualizzaAllegatiButtonField == null) {
					visualizzaAllegatiButtonField = buildVisualizzaAllegatiButtonField();
				}
				buttonsList.add(visualizzaAllegatiButtonField);
			}
		}
		return buttonsList;
	}

	@Override
	protected boolean showDetailButtonField() {
		return IstanzePortaleRiscossioneDaIstruireLayout.isAbilToViewDetail();
	}

	protected boolean showViewAllegatiButtonField() {
		return IstanzePortaleRiscossioneDaIstruireLayout.isAbilToViewAllegati();
	}

	protected boolean showViewDocButtonField() {
		return IstanzePortaleRiscossioneDaIstruireLayout.isAbilToViewDoc();
	}

	@Override
	protected boolean showModifyButtonField() {
		return IstanzePortaleRiscossioneDaIstruireLayout.isAbilToMod();
	}

	protected boolean isRecordWithDoc(ListGridRecord listRecord) {
		if (listRecord.getAttributeAsString("uriDocElettronico") != null && !"".equals(listRecord.getAttributeAsString("uriDocElettronico"))) {
			return true;
		}
		return false;
	}

	protected boolean isRecordWithAllegati(ListGridRecord listRecord) {
		int nroDocConFile = listRecord.getAttribute("nroDocConFile") != null && !listRecord.getAttribute("nroDocConFile").isEmpty() ? Integer
				.valueOf(listRecord.getAttribute("nroDocConFile")) : 0;
		if (nroDocConFile > 0) {
			return true;
		}
		return false;
	}

	protected void visualizzaAllegatiButtonClick(ListGridRecord record) {
		showRowContextMenu(record, null);
	}

	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("IstanzePortaleRiscossioneDaIstruireDatasource");
		lGwtRestDataSource.addParam("flgSoloAbilAzioni", "1");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
		lGwtRestDataSource.executecustom("getAbilContextMenu", lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				final Menu contextMenu = createVisualizzaAllegatiMenu(record, response.getData()[0]);
				contextMenu.showContextMenu();
			}
		});
	}

	public Menu createVisualizzaAllegatiMenu(final ListGridRecord listRecord, final Record detailRecord) {
		Menu visualizzaAllegatiMenu = new Menu();
		visualizzaAllegatiMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");
		// int nrAllegati = listRecord.getAttribute("nrAllegati") != null && !listRecord.getAttribute("nrAllegati").isEmpty() ?
		// Integer.valueOf(listRecord.getAttribute("nrAllegati")) : 0;

		// *******************************************
		// MENU' : FILE
		// *******************************************
		// if (nrAllegati > 0) {
		if (isRecordWithAllegati(listRecord)) {

			// =====================================================
			// Sub Menu' : FILE PRIMARIO
			// =====================================================

			if (detailRecord.getAttributeAsString("uriFilePrimario") != null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) {

				// -----------------------------------------------------
				// Voce : VISUALIZZA
				// -----------------------------------------------------
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
				MenuItem filePrimarioMenuItem = new MenuItem(detailRecord.getAttributeAsString("nomeFilePrimario"));
				Menu operazioniFilePrimarioSubmenu = new Menu();
				MenuItem visualizzaFilePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(),
						"file/preview.png");
				visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						visualizzaFilePrimario(detailRecord);
					}
				});
				visualizzaFilePrimarioMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
				operazioniFilePrimarioSubmenu.addItem(visualizzaFilePrimarioMenuItem);

				MenuItem visualizzaEditFilePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(),
						"file/previewEdit.png");
				visualizzaEditFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						visualizzaEditFilePrimario(detailRecord);
					}
				});
				visualizzaEditFilePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				operazioniFilePrimarioSubmenu.addItem(visualizzaEditFilePrimarioMenuItem);

				// -----------------------------------------------------
				// Voce : SCARICA
				// -----------------------------------------------------
				MenuItem scaricaFilePrimarioMenuItem = new MenuItem("Scarica", "file/download_manager.png");
				// Se è firmato P7M
				if (lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
					Menu scaricaFilePrimarioSubMenu = new Menu();
					MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
					firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							scaricaFilePrimario(detailRecord);
						}
					});
					MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
					sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							scaricaFilePrimarioSbustato(detailRecord);
						}
					});
					scaricaFilePrimarioSubMenu.setItems(firmato, sbustato);
					scaricaFilePrimarioMenuItem.setSubmenu(scaricaFilePrimarioSubMenu);
				} else {
					scaricaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							scaricaFilePrimario(detailRecord);
						}
					});
				}
				operazioniFilePrimarioSubmenu.addItem(scaricaFilePrimarioMenuItem);
				filePrimarioMenuItem.setSubmenu(operazioniFilePrimarioSubmenu);
				visualizzaAllegatiMenu.addItem(filePrimarioMenuItem);
			}

			// =====================================================
			// Sub Menu' : FILE SECONDARIO
			// =====================================================
			RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
			if (listaAllegati != null) {
				for (int n = 0; n < listaAllegati.getLength(); n++) {
					final int nroAllegato = n;
					final Record allegatoRecord = listaAllegati.get(n);
					MenuItem fileAllegatoMenuItem = new MenuItem(allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					Menu operazioniFileAllegatoSubmenu = new Menu();
					InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));

					// -----------------------------------------------------
					// Voce : VISUALIZZA
					// -----------------------------------------------------
					MenuItem visualizzaFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(),
							"file/preview.png");
					visualizzaFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							visualizzaFileAllegato(detailRecord, nroAllegato);
						}
					});
					visualizzaFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
					operazioniFileAllegatoSubmenu.addItem(visualizzaFileAllegatoMenuItem);

					MenuItem visualizzaEditFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(),
							"file/previewEdit.png");
					visualizzaEditFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							visualizzaEditFileAllegato(detailRecord, nroAllegato);
						}
					});
					visualizzaEditFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
					operazioniFileAllegatoSubmenu.addItem(visualizzaEditFileAllegatoMenuItem);

					// -----------------------------------------------------
					// Voce : SCARICA
					// -----------------------------------------------------
					MenuItem scaricaFileAllegatoMenuItem = new MenuItem("Scarica", "file/download_manager.png");
					// Se è firmato P7M
					if (lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
						Menu scaricaFileAllegatoSubMenu = new Menu();
						MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
						firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								scaricaFileAllegato(detailRecord, nroAllegato);
							}
						});
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								scaricaFileAllegatoSbustato(detailRecord, nroAllegato);
							}
						});
						scaricaFileAllegatoSubMenu.setItems(firmato, sbustato);
						scaricaFileAllegatoMenuItem.setSubmenu(scaricaFileAllegatoSubMenu);
					} else {
						scaricaFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								scaricaFileAllegato(detailRecord, nroAllegato);
							}
						});
					}
					operazioniFileAllegatoSubmenu.addItem(scaricaFileAllegatoMenuItem);
					fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);
					visualizzaAllegatiMenu.addItem(fileAllegatoMenuItem);
				}
			}
		}
		
		return visualizzaAllegatiMenu;
	}

	public ControlListGridField buildVisualizzaDocButtonField() {
		ControlListGridField visualizzaDocButtonField = new ControlListGridField("visualizzaDocButton");
		visualizzaDocButtonField.setAttribute("custom", true);
		visualizzaDocButtonField.setShowHover(true);
		visualizzaDocButtonField.setCanReorder(false);
		visualizzaDocButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordWithDoc(record)) {
					return buildImgButtonHtml("file/preview.png");
				}
				return null;
			}
		});
		visualizzaDocButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordWithDoc(record)) {
					return I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_visualizzaFilePrimarioMenuItem_title();
				}
				return null;
			}
		});
		visualizzaDocButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("IstanzePortaleRiscossioneDaIstruireDatasource");
				lGwtRestDataSource.addParam("flgSoloAbilAzioni", "1");
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
				lGwtRestDataSource.executecustom("getAbilContextMenu", lRecordToLoad, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record detailRecord = response.getData()[0];
						visualizzaFilePrimario(detailRecord);
					}
				});
			}
		});
		return visualizzaDocButtonField;
	}

	public ControlListGridField buildVisualizzaAllegatiButtonField() {
		ControlListGridField visualizzaAllegatiButtonField = new ControlListGridField("altreOpButton");
		visualizzaAllegatiButtonField.setAttribute("custom", true);
		visualizzaAllegatiButtonField.setShowHover(true);
		visualizzaAllegatiButtonField.setCanReorder(false);
		visualizzaAllegatiButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordWithAllegati(record)) {
					return buildImgButtonHtml("allegati.png");
				}
				return null;
			}
		});
		visualizzaAllegatiButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isRecordWithAllegati(record)) {
					return I18NUtil.getMessages().istanze_portale_riscossione_da_istruire_visualizzaAllegatiMenuItem_title();
				}
				return null;
			}
		});
		visualizzaAllegatiButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				visualizzaAllegatiButtonClick(getRecord(event.getRecordNum()));
			}
		});
		return visualizzaAllegatiButtonField;
	}

	public void visualizzaFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaEditFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void scaricaFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		scaricaFile(idUd, idDoc, display, uri, remoteUri);
	}

	public void scaricaFilePrimarioSbustato(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFileAllegato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaEditFileAllegato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void scaricaFileAllegato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		scaricaFile(idUd, idDoc, display, uri, remoteUri);
	}

	public void scaricaFileAllegatoSbustato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), InfoFileRecord.buildInfoFileRecord(infoFile), "FileToExtractBean", display);
	}

	public void visualizzaEditFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, Boolean.valueOf(remoteUri), false, null) {

			@Override
			public void uploadCallBack(String filePdf, String uriPdf, String record) {
			}
		};
		previewDocWindow.show();
	}
	
	@Override
	public void manageContextClick(final Record record) {
		if(record != null) {
			buildMenu(record);
		}
	}
	
	/**
	 * Crea il menu a tendina a seguito di un click destro su un record della lista
	 * @param pRecordIstanza record contenente le informazioni dell'istanza
	 * 
	 */
	protected void buildMenu(final Record pRecordIstanza){
		final Menu altreOpMenu = new Menu();
		altreOpMenu.setOverflow(Overflow.VISIBLE);
		altreOpMenu.setShowIcons(true);
		altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
		altreOpMenu.setCanDragRecordsOut(false);
		altreOpMenu.setWidth("*");
		altreOpMenu.setHeight("*");
		final ListGridRecord pGridListRecordInstanza = getRecord(getRecordIndex(pRecordIstanza));
		
		// Visualizza File Primario
		if (showViewDocButtonField() && isRecordWithDoc(pGridListRecordInstanza)) {
			MenuItem visualizzaFilePrimarioMenuItem = new MenuItem("Visualizza file primario", "file/preview.png");
			visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(
							"IstanzePortaleRiscossioneDaIstruireDatasource");
					lGwtRestDataSource.addParam("flgSoloAbilAzioni", "1");
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", pGridListRecordInstanza.getAttribute("idUdFolder"));
					lGwtRestDataSource.executecustom("getAbilContextMenu", lRecordToLoad, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record detailRecord = response.getData()[0];
							visualizzaFilePrimario(detailRecord);
						}
					});
				}
			});
			altreOpMenu.addItem(visualizzaFilePrimarioMenuItem);
		}
		
		// Visualizza Dettaglio
		if(showDetailButtonField()) {
			MenuItem visualizzaDettaglioMenuItem = new MenuItem("Visualizza dettaglio", "buttons/detail.png");
			visualizzaDettaglioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageDetailButtonClick(pGridListRecordInstanza);		
				}
			});
			altreOpMenu.addItem(visualizzaDettaglioMenuItem);
		}
		
		// Modifica
		if (showModifyButtonField()) {
			MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
			modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageModifyButtonClick(pGridListRecordInstanza);
				}
			});
			altreOpMenu.addItem(modificaMenuItem);
		}
		
		// Visualizza allegati
		if (showViewAllegatiButtonField() && isRecordWithAllegati(pGridListRecordInstanza)) {
			MenuItem visualizzaAllegatiMenuItem = new MenuItem("Visualizza allegati", "allegati.png");
			visualizzaAllegatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//TODO: fare meglio il menu a cascata?
					visualizzaAllegatiButtonClick(pGridListRecordInstanza);
				}
			});
			altreOpMenu.addItem(visualizzaAllegatiMenuItem);
		}
		
		altreOpMenu.showContextMenu();
	}
	

	public void scaricaFile(String idUd, String idDoc, String display, String uri, String remoteUri) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void scaricaFileSbustato(String idUd, String idDoc, String display, String uri, String remoteUri, Object infoFile) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	@Override
	protected void modifyClick(final Record record, final int recordNum) {
		if (layout != null) {
			layout.getDetail().markForRedraw();
			manageLoadDetail("edit", record, recordNum, null);
		}
	}

	@Override
	protected void detailClick(final Record record, final int recordNum) {
		if (layout != null) {
			layout.getDetail().markForRedraw();
			manageLoadDetail("view", record, recordNum, null);
		}
	}
}