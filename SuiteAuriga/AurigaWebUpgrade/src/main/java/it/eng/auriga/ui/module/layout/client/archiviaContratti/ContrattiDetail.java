/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.ScanUtility;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ContrattiDetail extends CustomDetail {

	protected ContrattiDetail instance;

	private DynamicForm testataForm;
	private DetailSection testataDetailSection;
	private TextItem nroContrattoItem;
	private DateItem dataStipulaContrattoItem;

	private DynamicForm contraenteForm;
	private DetailSection contraenteDetailSection;
	protected ContraenteItem contraenteItem;

	private DynamicForm contenutiForm;
	private DetailSection contenutiDetailSection;
	private TextAreaItem oggettoItem;
	private DynamicForm filePrimarioForm;
	private HiddenItem nomeFilePrimarioOrigItem;
	private HiddenItem nomeFilePrimarioTifItem;
	private HiddenItem uriFilePrimarioTifItem;
	private HiddenItem uriFilePrimarioItem;
	private HiddenItem remoteUriFilePrimarioItem;
	private HiddenItem isDocPrimarioChangedItem;
	private FileUploadItemWithFirmaAndMimeType uploadFilePrimarioItem;
	private HiddenItem uriFileVerPreFirmaItem;
	private HiddenItem nomeFileVerPreFirmaItem;
	private HiddenItem improntaVerPreFirmaItem;
	private HiddenItem infoFileVerPreFirmaItem;
	private ExtendedTextItem nomeFilePrimarioItem;
	protected HiddenItem infoFileItem;
	protected HiddenItem idDocPrimarioHiddenItem;

	private NestedFormItem filePrimarioButtons;
	protected ImgButtonItem previewButton;
	private DynamicForm fileAllegatiForm;
	private DetailSection fileAllegatiDetailSection;
	private AllegatiItem fileAllegatiItem;
	protected ImgButtonItem previewEditButton;
	protected ImgButtonItem editButton;
	protected ImgButtonItem fileFirmatoDigButton;
	protected ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem fileMarcatoTempButton;
	protected ImgButtonItem altreOpButton;

	private DynamicForm protocolloForm;
	private DetailSection protocolloDetailSection;
	private TextItem siglaProtocolloItem;
	private ExtendedTextItem numProtocolloItem;
	private AnnoItem annoProtocolloItem;
	private DateItem dataProtocolloItem;

	private DynamicForm conservazioneSostitutivaForm;
	private DetailSection conservazioneSostitutivaDetailSection;
	private TextItem statoConservazioneItem;
	private TextItem dataInvioInConservazioneItem;
	private TextAreaItem erroreInInvioItem;

	protected String mode;

	public ContrattiDetail(String nomeEntita) {

		super(nomeEntita);

		VLayout vLayout = new VLayout(6);

		buildTestataForm();

		buildContraentiForm();

		buildContenutiForm();

		buildFileAllegatiForm();

		buildProtocolloForm();

		buildConservazioneSostitutivaForm();

		testataDetailSection = new DetailSection(I18NUtil.getMessages().archivio_contratto_testata_section_title(), true, true, false, testataForm);
		vLayout.addMember(testataDetailSection);

		contraenteDetailSection = new DetailSection(I18NUtil.getMessages().archivio_contratto_contraenti_section_title(), true, true, true, contraenteForm);
		vLayout.addMember(contraenteDetailSection);

		contenutiDetailSection = new DetailSection(I18NUtil.getMessages().archivio_contratto_contenuti_section_title(), true, true, false, contenutiForm,
				filePrimarioForm);
		vLayout.addMember(contenutiDetailSection);

		fileAllegatiDetailSection = new DetailSection(I18NUtil.getMessages().archivio_contratto_allegati_section_title(), true, true, false, fileAllegatiForm);
		vLayout.addMember(fileAllegatiDetailSection);

		protocolloDetailSection = new DetailSection(I18NUtil.getMessages().archivio_contratto_protocollo_section_title(), true, true, false, protocolloForm);
		vLayout.addMember(protocolloDetailSection);

		conservazioneSostitutivaDetailSection = new DetailSection(I18NUtil.getMessages().archivio_contratto_conservazione_sostitutiva_section_title(), true,
				true, false, conservazioneSostitutivaForm);
		vLayout.addMember(conservazioneSostitutivaDetailSection);

		addMember(vLayout);

		editNewRecord();

	}

	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		conservazioneSostitutivaDetailSection.setVisible(true);

	}

	@Override
	public void editNewRecord() {

		super.editNewRecord();

		conservazioneSostitutivaDetailSection.setVisible(false);

	}

	private void buildTestataForm() {

		testataForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};

		testataForm.setValuesManager(vm);
		testataForm.setWidth100();
		testataForm.setPadding(5);
		testataForm.setWrapItemTitles(false);
		testataForm.setNumCols(18);
		testataForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		nroContrattoItem = new TextItem("nroContratto", I18NUtil.getMessages().archivio_contratto_numero());
		nroContrattoItem.setColSpan(1);
		nroContrattoItem.setWidth(150);
		nroContrattoItem.setRequired(true);

		dataStipulaContrattoItem = new DateItem("dataStipulaContratto", I18NUtil.getMessages().archivio_contratto_data_stipula());
		dataStipulaContrattoItem.setWrapTitle(false);
		dataStipulaContrattoItem.setColSpan(1);
		dataStipulaContrattoItem.setRequired(true);

		testataForm.setFields(nroContrattoItem, dataStipulaContrattoItem);

	}

	private void buildContraentiForm() {

		contraenteForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};

		contraenteForm.setValuesManager(vm);
		contraenteForm.setWidth100();
		contraenteForm.setPadding(5);
		contraenteForm.setWrapItemTitles(false);
		contraenteForm.setNumCols(18);
		contraenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		contraenteItem = new ContraenteItem() {
			// @Override
			// public boolean isNewMode() {
			// 
			// return (getLayout() == null || (getLayout().getMode() != null && "new".equals(getLayout().getMode())));
			// }
		};
		contraenteItem.setName("listaContraenti");
		contraenteItem.setShowTitle(false);
		contraenteItem.setAttribute("obbligatorio", true);

		contraenteForm.setFields(contraenteItem);

	}

	private void buildContenutiForm() {

		contenutiForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};

		contenutiForm.setValuesManager(vm);
		contenutiForm.setWidth100();
		contenutiForm.setPadding(5);
		contenutiForm.setWrapItemTitles(false);
		contenutiForm.setNumCols(18);
		contenutiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		oggettoItem = new TextAreaItem("oggetto", I18NUtil.getMessages().archivio_contratto_oggetto());
		oggettoItem.setRequired(true);
		oggettoItem.setLength(4000);
		oggettoItem.setHeight(80);
		oggettoItem.setColSpan(1);
		oggettoItem.setWidth(750);

		contenutiForm.setFields(oggettoItem);

		nomeFilePrimarioItem = new ExtendedTextItem("nomeFilePrimario", I18NUtil.getMessages().archivio_contratto_nome_file_primario());
		nomeFilePrimarioItem.setWidth(250);
		nomeFilePrimarioItem.setStartRow(true);
		nomeFilePrimarioItem.setColSpan(1);
		nomeFilePrimarioItem.setRequired(true);

		nomeFilePrimarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (nomeFilePrimarioItem.getValue() == null || ((String) nomeFilePrimarioItem.getValue()).trim().equals("")) {
					clickEliminaFile();
				} else if (filePrimarioForm.getValueAsString("nomeFilePrimarioOrig") != null
						&& !"".equals(filePrimarioForm.getValueAsString("nomeFilePrimarioOrig"))
						&& !nomeFilePrimarioItem.getValue().equals(filePrimarioForm.getValueAsString("nomeFilePrimarioOrig"))) {
					manageChangedDocPrimario();
				}
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
			}
		});

		uriFilePrimarioItem = new HiddenItem("uriFilePrimario");
		nomeFilePrimarioOrigItem = new HiddenItem("nomeFilePrimarioOrig");
		nomeFilePrimarioTifItem = new HiddenItem("nomeFilePrimarioTif"); // NOME del Tif
		uriFilePrimarioTifItem = new HiddenItem("uriFilePrimarioTif"); // URI del Tif
		remoteUriFilePrimarioItem = new HiddenItem("remoteUriFilePrimario");
		isDocPrimarioChangedItem = new HiddenItem("isDocPrimarioChanged");
		infoFileItem = new HiddenItem("infoFile");
		uriFileVerPreFirmaItem = new HiddenItem("uriFileVerPreFirma");
		nomeFileVerPreFirmaItem = new HiddenItem("nomeFileVerPreFirma");
		improntaVerPreFirmaItem = new HiddenItem("improntaVerPreFirma");
		infoFileVerPreFirmaItem = new HiddenItem("infoFileVerPreFirma");
		idDocPrimarioHiddenItem = new HiddenItem("idDocPrimario");

		isDocPrimarioChangedItem.setDefaultValue(false);

		uploadFilePrimarioItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				filePrimarioForm.setValue("nomeFilePrimario", displayFileName);
				filePrimarioForm.setValue("uriFilePrimario", uri);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", displayFileName);
				filePrimarioForm.setValue("uriFileVerPreFirma", uri);
				changedEventAfterUpload(displayFileName, uri);
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
				uploadFilePrimarioItem.getCanvas().redraw();

			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				String nomeFilePrimario = filePrimarioForm.getValueAsString("nomeFilePrimario");
				String nomeFilePrimarioOrig = filePrimarioForm.getValueAsString("nomeFilePrimarioOrig");
				if (precImpronta == null
						|| !precImpronta.equals(info.getImpronta())
						|| (nomeFilePrimario != null && !"".equals(nomeFilePrimario) && nomeFilePrimarioOrig != null && !"".equals(nomeFilePrimarioOrig) && !nomeFilePrimario
								.equals(nomeFilePrimarioOrig))) {
					manageChangedDocPrimario();
				}
				if (info.isFirmato() && !info.isFirmaValida()) {
					GWTRestDataSource.printMessage(new MessageBean("Il file presenta una firma non valida alla data odierna", "", MessageType.WARNING));
				}
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
			}
		});
		uploadFilePrimarioItem.setColSpan(1);

		filePrimarioButtons = new NestedFormItem("filePrimarioButtons");
		filePrimarioButtons.setWidth(10);
		filePrimarioButtons.setOverflow(Overflow.VISIBLE);
		filePrimarioButtons.setNumCols(7);
		filePrimarioButtons.setColWidths(16, 16, 16, 16, 16, 16, 16);
		filePrimarioButtons.setColSpan(1);

		previewButton = new ImgButtonItem("previewButton", "file/preview.png", I18NUtil.getMessages().protocollazione_detail_previewButton_prompt());
		previewButton.setAlwaysEnabled(true);
		previewButton.setColSpan(1);
		previewButton.setIconWidth(16);
		previewButton.setIconHeight(16);
		previewButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewButton.setAlign(Alignment.LEFT);
		previewButton.setWidth(16);
		previewButton.setRedrawOnChange(true);
		previewButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return PreviewWindow.canBePreviewed(lInfoFileRecord);
				} else
					return false;
			}
		});
		previewButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewFile();
			}
		});

		previewEditButton = new ImgButtonItem("previewEditButton", "file/previewEdit.png", I18NUtil.getMessages()
				.protocollazione_detail_previewEditButton_prompt());
		previewEditButton.setAlwaysEnabled(true);
		previewEditButton.setColSpan(1);
		previewEditButton.setIconWidth(16);
		previewEditButton.setIconHeight(16);
		previewEditButton.setIconVAlign(VerticalAlignment.BOTTOM);
		previewEditButton.setAlign(Alignment.LEFT);
		previewEditButton.setWidth(16);
		previewEditButton.setRedrawOnChange(true);
		previewEditButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")
						&& (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""))) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
				} else
					return false;
			}
		});
		previewEditButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickPreviewEditFile();
			}
		});

		editButton = new ImgButtonItem("editButton", "file/editDoc.png", "Modifica documento");
		editButton.setAlwaysEnabled(false);
		editButton.setColSpan(1);
		editButton.setIconWidth(16);
		editButton.setIconHeight(16);
		editButton.setIconVAlign(VerticalAlignment.BOTTOM);
		editButton.setAlign(Alignment.LEFT);
		editButton.setWidth(16);
		editButton.setRedrawOnChange(true);
		editButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showEditButton();
			}
		});
		editButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickEditFile();
			}
		});

		fileFirmatoDigButton = new ImgButtonItem("fileFirmatoDigButton", "firma/firma.png", I18NUtil.getMessages()
				.protocollazione_detail_fileFirmatoDigButton_prompt());
		fileFirmatoDigButton.setAlwaysEnabled(true);
		fileFirmatoDigButton.setColSpan(1);
		fileFirmatoDigButton.setIconWidth(16);
		fileFirmatoDigButton.setIconHeight(16);
		fileFirmatoDigButton.setIconVAlign(VerticalAlignment.BOTTOM);
		fileFirmatoDigButton.setAlign(Alignment.LEFT);
		fileFirmatoDigButton.setWidth(16);
		fileFirmatoDigButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && lInfoFileRecord.isFirmaValida();
				} else
					return false;
			}
		});
		fileFirmatoDigButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				manageFileFirmatoButtonClick();

			}
		});

		firmaNonValidaButton = new ImgButtonItem("firmaNonValidaButton", "firma/firmaNonValida.png", I18NUtil.getMessages()
				.protocollazione_detail_firmaNonValidaButton_prompt());
		firmaNonValidaButton.setAlwaysEnabled(true);
		firmaNonValidaButton.setColSpan(1);
		firmaNonValidaButton.setIconWidth(16);
		firmaNonValidaButton.setIconHeight(16);
		firmaNonValidaButton.setIconVAlign(VerticalAlignment.BOTTOM);
		firmaNonValidaButton.setAlign(Alignment.LEFT);
		firmaNonValidaButton.setWidth(16);
		firmaNonValidaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					return lInfoFileRecord != null && lInfoFileRecord.isFirmato() && !lInfoFileRecord.isFirmaValida();
				} else
					return false;
			}
		});
		firmaNonValidaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageFileFirmatoButtonClick();

			}
		});
		
		fileMarcatoTempButton = new ImgButtonItem("fileMarcatoTempButton", "marca/marcaNonValida.png", I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
		fileMarcatoTempButton.setAlwaysEnabled(true);
		fileMarcatoTempButton.setColSpan(1);
		fileMarcatoTempButton.setIconWidth(16);
		fileMarcatoTempButton.setIconHeight(16);
		fileMarcatoTempButton.setIconVAlign(VerticalAlignment.BOTTOM);
		fileMarcatoTempButton.setAlign(Alignment.LEFT);
		fileMarcatoTempButton.setWidth(16);
		fileMarcatoTempButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
					if (lInfoFileRecord != null && lInfoFileRecord.getInfoFirmaMarca() != null && lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
						if (lInfoFileRecord.getInfoFirmaMarca().isMarcaTemporaleNonValida()) {
							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaNonValida_prompt());
							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleNonValida.png");
							return true;
						} else {
							Date dataOraMarcaTemporale = lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale();
							DateTimeFormat display_datetime_format = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
							fileMarcatoTempButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_fileMarcatoTempButton_marcaValida_prompt(display_datetime_format.format(dataOraMarcaTemporale)));
							fileMarcatoTempButton.setIcon("marcaTemporale/marcaTemporaleValida.png");
							return true;
						}
					} else {
						return false;
					}
				}
				return false;
			}
		});

		altreOpButton = new ImgButtonItem("altreOpButton", "buttons/altreOp.png", I18NUtil.getMessages().altreOpButton_prompt());
		altreOpButton.setAlwaysEnabled(true);
		altreOpButton.setColSpan(1);
		altreOpButton.setIconWidth(16);
		altreOpButton.setIconHeight(16);
		altreOpButton.setIconVAlign(VerticalAlignment.BOTTOM);
		altreOpButton.setAlign(Alignment.LEFT);
		altreOpButton.setWidth(16);
		altreOpButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				MenuItem acquisisciDaScannerMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(),
						"file/scanner.png");
				acquisisciDaScannerMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return true;
					}
				});
				acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickAcquisisciDaScanner();
					}
				});

				MenuItem firmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_firmaMenuItem_prompt(), "file/mini_sign.png");
				firmaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""));
					}
				});
				firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickFirma();
					}
				});

				MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
				if (lInfoFileRecord != null) {
					// Se è firmato P7M
					if (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
						Menu showFirmatoAndSbustato = new Menu();
						MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
						firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFile();
							}
						});
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFileSbustato();
							}
						});
						showFirmatoAndSbustato.setItems(firmato, sbustato);
						downloadMenuItem.setSubmenu(showFirmatoAndSbustato);
					} else {
						downloadMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								clickDownloadFile();
							}
						});
					}
				}
				downloadMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""));
					}
				});

				MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(), "file/editdelete.png");
				eliminaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						if (uriFilePrimarioItem.getValue() != null) {
							return !uriFilePrimarioItem.getValue().equals("");
						} else
							return false;
					}
				});
				eliminaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						clickEliminaFile();
					}
				});

				final Menu altreOpMenu = new Menu();
				altreOpMenu.setOverflow(Overflow.VISIBLE);
				altreOpMenu.setShowIcons(true);
				altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
				altreOpMenu.setCanDragRecordsOut(false);
				altreOpMenu.setWidth("*");
				altreOpMenu.setHeight("*");

				if (filePrimarioButtons.isEditing()) {
					altreOpMenu.addItem(acquisisciDaScannerMenuItem);
					altreOpMenu.addItem(firmaMenuItem);
				}
				altreOpMenu.addItem(downloadMenuItem);

				if (filePrimarioButtons.isEditing()) {
					altreOpMenu.addItem(eliminaMenuItem);
				}

				altreOpMenu.showContextMenu();
			}
		});

		filePrimarioButtons.setNestedFormItems(uploadFilePrimarioItem, previewButton, previewEditButton, editButton, fileFirmatoDigButton,
				firmaNonValidaButton, fileMarcatoTempButton, altreOpButton);

		infoFileItem.setVisible(false);

		filePrimarioForm = new DynamicForm();
		filePrimarioForm.setValuesManager(vm);
		filePrimarioForm.setWidth100();
		filePrimarioForm.setPadding(5);
		filePrimarioForm.setNumCols(14);
		filePrimarioForm.setColWidths(50, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		filePrimarioForm.setWrapItemTitles(false);

		filePrimarioForm.setFields(idDocPrimarioHiddenItem, nomeFilePrimarioOrigItem, nomeFilePrimarioItem, uriFilePrimarioItem, filePrimarioButtons,
				infoFileItem, remoteUriFilePrimarioItem, isDocPrimarioChangedItem, uriFileVerPreFirmaItem, nomeFileVerPreFirmaItem, improntaVerPreFirmaItem,infoFileVerPreFirmaItem);

	}

	private void buildFileAllegatiForm() {

		fileAllegatiForm = new DynamicForm();
		fileAllegatiForm.setValuesManager(vm);
		fileAllegatiForm.setWidth("100%");
		fileAllegatiForm.setPadding(5);
		fileAllegatiItem = new AllegatiItem() {

			@Override
			public boolean sonoModificaVisualizza() {
				return !mode.equals("new");
			}

		};

		fileAllegatiItem.setHideTimbraInAltreOperazioniButton(true);
		fileAllegatiItem.setName("listaAllegati");
		fileAllegatiItem.setShowTitle(false);
		fileAllegatiForm.setFields(fileAllegatiItem);

	}

	private void buildProtocolloForm() {

		protocolloForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};

		protocolloForm.setValuesManager(vm);
		protocolloForm.setWidth100();
		protocolloForm.setPadding(5);
		protocolloForm.setWrapItemTitles(false);
		protocolloForm.setNumCols(18);
		protocolloForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		CustomValidator lValorizzatoSeAltriValorizzatiValidatorProtRicevuto = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				String name = getFormItem().getName();
				if (isBlank(value)) {
					boolean valid = true;
					if ("nroProtocollo".equals(name)) {
						valid = valid && isBlank(annoProtocolloItem.getValue()) && isBlank(dataProtocolloItem.getValue());
					} else if ("annoProtocollo".equals(name)) {
						valid = valid && (isBlank(numProtocolloItem.getValue()) || !isBlank(dataProtocolloItem.getValue()));
					} else if ("dataProtocollo".equals(name)) {
						valid = valid && (isBlank(numProtocolloItem.getValue()) || !isBlank(annoProtocolloItem.getValue()));
					}
					return valid;
				} else
					return true;
			}

			private boolean isBlank(Object value) {
				return (value == null || ((value instanceof String) && "".equals(value.toString().trim())));
			}
		};

		siglaProtocolloItem = new TextItem("siglaProtocollo", I18NUtil.getMessages().archivio_contratto_sigla_protocollo());
		siglaProtocolloItem.setColSpan(1);
		siglaProtocolloItem.setWidth(80);

		numProtocolloItem = new ExtendedTextItem("nroProtocollo", I18NUtil.getMessages().archivio_contratto_numero_protocollo());
		numProtocolloItem.setWidth(100);
		numProtocolloItem.setLength(50);
		numProtocolloItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		numProtocolloItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				numProtocolloItem.validate();
				annoProtocolloItem.validate();
				dataProtocolloItem.validate();
			}
		});

		annoProtocolloItem = new AnnoItem("annoProtocollo", I18NUtil.getMessages().archivio_contratto_anno_protocollo());
		annoProtocolloItem.setWidth(100);
		annoProtocolloItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		annoProtocolloItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				numProtocolloItem.validate();
				annoProtocolloItem.validate();
				dataProtocolloItem.validate();
			}
		});

		dataProtocolloItem = new DateItem("dataProtocollo", I18NUtil.getMessages().archivio_contratto_data_protocollo());
		dataProtocolloItem.setColSpan(1);
		dataProtocolloItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		dataProtocolloItem.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				numProtocolloItem.validate();
				annoProtocolloItem.validate();
				dataProtocolloItem.validate();
			}
		});

		protocolloForm.setFields(siglaProtocolloItem, numProtocolloItem, annoProtocolloItem, dataProtocolloItem);

	}

	private void buildConservazioneSostitutivaForm() {

		conservazioneSostitutivaForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};

		conservazioneSostitutivaForm.setValuesManager(vm);
		conservazioneSostitutivaForm.setWidth100();
		conservazioneSostitutivaForm.setPadding(5);
		conservazioneSostitutivaForm.setWrapItemTitles(false);
		conservazioneSostitutivaForm.setNumCols(18);
		conservazioneSostitutivaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		statoConservazioneItem = new TextItem("statoConservazione", I18NUtil.getMessages().archivio_contratto_stato_conservazione());
		statoConservazioneItem.setColSpan(1);
		statoConservazioneItem.setWidth(200);

		dataInvioInConservazioneItem = new TextItem("dataInvioInConservazione", I18NUtil.getMessages().archivio_contratto_inviato_conservazione());
		dataInvioInConservazioneItem.setColSpan(1);

		erroreInInvioItem = new TextAreaItem("erroreInInvio", I18NUtil.getMessages().archivio_contratto_errore_invio());
		erroreInInvioItem.setLength(4000);
		erroreInInvioItem.setHeight(40);
		erroreInInvioItem.setColSpan(4);
		erroreInInvioItem.setWidth(450);
		erroreInInvioItem.setStartRow(true);

		erroreInInvioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return value != null && !value.equals("");
			}
		});

		conservazioneSostitutivaForm.setFields(statoConservazioneItem, dataInvioInConservazioneItem, erroreInInvioItem);

	}

	private void clickEliminaFile() {
		nomeFilePrimarioItem.setValue("");
		uriFilePrimarioItem.setValue("");
		nomeFilePrimarioTifItem.setValue("");
		uriFilePrimarioTifItem.setValue("");
		filePrimarioForm.markForRedraw();
		filePrimarioButtons.markForRedraw();
		uploadFilePrimarioItem.getCanvas().redraw();
		remoteUriFilePrimarioItem.setValue(false);
		nomeFileVerPreFirmaItem.setValue("");
		uriFileVerPreFirmaItem.setValue("");
		improntaVerPreFirmaItem.setValue("");
		infoFileItem.clearValue();
	}

	public void manageChangedDocPrimario() {
		filePrimarioForm.setValue("isDocPrimarioChanged", true);
	}

	protected void changedEventAfterUpload(final String displayFileName, final String uri) {

		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFilePrimarioItem.getJsObj()) {

			public DynamicForm getForm() {
				return filePrimarioForm;
			};

			@Override
			public FormItem getItem() {
				return nomeFilePrimarioItem;
			}

			@Override
			public Object getValue() {
				
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriFilePrimarioItem.getJsObj()) {

			public DynamicForm getForm() {
				return filePrimarioForm;
			};

			@Override
			public FormItem getItem() {
				return uriFilePrimarioItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};

		nomeFilePrimarioItem.fireEvent(lChangedEventDisplay);
		uriFilePrimarioItem.fireEvent(lChangedEventUri);

	}

	public void clickPreviewFile() {
		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		final String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
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
						preview(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	public void clickPreviewEditFile() {
		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		final String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
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
						previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			previewWithJPedal(detailRecord, idUd, display, uri, remoteUri, info);
		}
	}

	protected boolean showEditButton() {
		final String display = filePrimarioForm.getValueAsString("nomeFileVerPreFirma");
		final String uri = filePrimarioForm.getValueAsString("uriFileVerPreFirma");
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
			if (uri != null && !uri.equals("")) {
				String estensione = null;
				/**
				 * File corrente non firmato
				 */
				Object infoFile = filePrimarioForm.getValue("infoFile");
				if (infoFile != null) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
					if (lInfoFileRecord.getCorrectFileName() != null && !lInfoFileRecord.getCorrectFileName().trim().equals("")) {
						estensione = lInfoFileRecord.getCorrectFileName().substring(lInfoFileRecord.getCorrectFileName().lastIndexOf(".") + 1);
					}
					if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
						estensione = display.substring(display.lastIndexOf(".") + 1);
					}
					if (estensione.equalsIgnoreCase("p7m")) {
						String displaySbustato = display.substring(0, display.lastIndexOf("."));
						estensione = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
					}
					for (String lString : Layout.getGenericConfig().getEditableExtension()) {
						if (lString.equals(estensione)) {
							return true;
						}
					}
				}
				/**
				 * File pre versione di quello firmato
				 */
				Object infoFilePreVer = filePrimarioForm.getValue("infoFileVerPreFirma");
				if(infoFilePreVer != null) {
					InfoFileRecord lInfoFilePreVerRecord = new InfoFileRecord(infoFilePreVer);
					if (lInfoFilePreVerRecord.getCorrectFileName() != null && !lInfoFilePreVerRecord.getCorrectFileName().trim().equals("")) {
						estensione = lInfoFilePreVerRecord.getCorrectFileName().substring(lInfoFilePreVerRecord.getCorrectFileName().lastIndexOf(".") + 1);
					}
					if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
						estensione = display.substring(display.lastIndexOf(".") + 1);
					}
					if (estensione.equalsIgnoreCase("p7m")) {
						String displaySbustato = display.substring(0, display.lastIndexOf("."));
						estensione = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
					}
					for (String lString : Layout.getGenericConfig().getEditableExtension()) {
						if (lString.equals(estensione)) {
							return true;
						}
					}
				} 
				return false;
			} else
				return false;
		} else
			return false;
	}

	protected void clickEditFile() {
		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFileVerPreFirma");
		final String uri = filePrimarioForm.getValueAsString("uriFileVerPreFirma");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));

		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		
		if (info.isFirmato()) {
			SC.ask("Modificando il file perderai la/le firme già apposte. Vuoi comunque procedere?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {

					if (value){
						InfoFileRecord infoPreFirma = new InfoFileRecord(filePrimarioForm.getValue("infoFileVerPreFirma"));
						editFile(infoPreFirma, display, uri, remoteUri);
					}
				}
			});
		}
		else
			editFile(info, display, uri, remoteUri);
	}

	public void clickAcquisisciDaScanner() {
		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(final String filePdf, final String uriPdf, String fileTif, String uriTif, String record) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedDocPrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", filePdf);
				filePrimarioForm.setValue("uriFilePrimario", uriPdf);
				filePrimarioForm.setValue("nomeFilePrimarioTif", fileTif);
				filePrimarioForm.setValue("uriFilePrimarioTif", uriTif);
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", filePdf);
				filePrimarioForm.setValue("uriFileVerPreFirma", uriPdf);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
				changedEventAfterUpload(filePdf, uriPdf);
			}
		});
	}

	public void clickFirma() {
		String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		FirmaUtility.firmaMultipla(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedDocPrimario();
				}
				filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
				filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});
	}

	public void clickDownloadFile() {
		String idUd = new Record(vm.getValues()).getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Download del file firmato sbustato
	public void clickDownloadFileSbustato() {
		String idUd = new Record(vm.getValues()).getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
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

	public void preview(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri, info, "FileToExtractBean", display);
	}

	public void previewWithJPedal(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), 
				idUd,rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv")) && detailRecord.getAttribute("idUd") != null
						&& !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, remoteUri, timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {
						InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile"))
								: null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
						filePrimarioForm.setValue("infoFile", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							manageChangedDocPrimario();
						}
						filePrimarioForm.setValue("nomeFilePrimario", filePdf);
						filePrimarioForm.setValue("uriFilePrimario", uriPdf);
						filePrimarioForm.setValue("nomeFilePrimarioTif", "");
						filePrimarioForm.setValue("uriFilePrimarioTif", "");
						filePrimarioForm.setValue("remoteUriFilePrimario", false);
						filePrimarioForm.setValue("nomeFileVerPreFirma", filePdf);
						filePrimarioForm.setValue("uriFileVerPreFirma", uriPdf);
						filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
						filePrimarioForm.markForRedraw();
						filePrimarioButtons.markForRedraw();
						changedEventAfterUpload(filePdf, uriPdf);
					}
				};
				previewDocWindow.show();
			}
		});
	}

	private void editFile(InfoFileRecord info, String display, String uri, Boolean remoteUri) {
		String estensione = null;
		if (info.getCorrectFileName() != null && !info.getCorrectFileName().trim().equals("")) {
			estensione = info.getCorrectFileName().substring(info.getCorrectFileName().lastIndexOf(".") + 1);
		}
		if (estensione == null || estensione.equals("") || estensione.equalsIgnoreCase("p7m")) {
			estensione = display.substring(display.lastIndexOf(".") + 1);
		}
		String impronta = filePrimarioForm.getValueAsString("improntaVerPreFirma");
		if (impronta == null || "".equals(impronta)) {
			impronta = info.getImpronta();
		}
		if (estensione.equalsIgnoreCase("p7m")) {
			Record lRecordDaSbustare = new Record();
			lRecordDaSbustare.setAttribute("displayFilename", display);
			lRecordDaSbustare.setAttribute("uri", uri);
			lRecordDaSbustare.setAttribute("remoteUri", remoteUri);
			new GWTRestService<Record, Record>("SbustaFileService").call(lRecordDaSbustare, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record lRecordSbustato) {
					String displaySbustato = lRecordSbustato.getAttribute("displayFilename");
					String uriSbustato = lRecordSbustato.getAttribute("uri");
					String estensioneSbustato = displaySbustato.substring(displaySbustato.lastIndexOf(".") + 1);
					String improntaSbustato = lRecordSbustato.getAttribute("impronta");
					openEditFileWindow(displaySbustato, uriSbustato, false, estensioneSbustato, improntaSbustato);
				}
			});
		} else {
			openEditFileWindow(display, uri, remoteUri, estensione, impronta);
		}
	}

	public void openEditFileWindow(String display, String uri, Boolean remoteUri, String estensione, String impronta) {

		OpenEditorUtility.openEditor(display, uri, remoteUri, estensione, impronta, new OpenEditorCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, String record) {
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				filePrimarioForm.setValue("infoFile", info);

				manageChangedDocPrimario();

				filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
				filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
				filePrimarioForm.setValue("nomeFileVerPreFirma", nomeFileFirmato);
				filePrimarioForm.setValue("uriFileVerPreFirma", uriFileFirmato);
				filePrimarioForm.setValue("improntaVerPreFirma", info.getImpronta());
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);

			}
		});

	}

	public void archiviaContratto() {

		if (validate()) {

			final Record record = getRecordToSave();

			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioContrattiDataSource", "idUd", FieldType.TEXT);

			Layout.showWaitPopup("Archiviazione in corso: potrebbe richiedere qualche secondo. Attendere…");

			try {

				if (vm.getValue("idUd") != null && !"".equals(vm.getValue("idUd"))) {

					record.setAttribute("idUd", vm.getValue("idUd"));

					lGwtRestDataSource.updateData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
								if (layout != null) {
									Record lRecordToLoad = new Record();
									// lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
									try {
										layout.getList().manageLoadDetail(record, getRecordNum(), new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												layout.viewMode();
												Layout.hideWaitPopup();
											}
										});
									} catch (Exception e) {
										Layout.hideWaitPopup();
									}
									if (!layout.getFullScreenDetail()) {
										layout.reloadListAndSetCurrentRecord(record);
									}
								}
							}

						}

					});
				} else {

					lGwtRestDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
								if (layout != null) {
									Record lRecordToLoad = new Record();
									lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));

									// Record record = new Record(vm.getValues());

									try {
										layout.getList().manageLoadDetail(record, getRecordNum(), new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												layout.viewMode();
												Layout.hideWaitPopup();
											}
										});
									} catch (Exception e) {
										Layout.hideWaitPopup();
									}
									if (!layout.getFullScreenDetail()) {
										layout.reloadListAndSetCurrentRecord(record);
									}
								}
							}
						}
					});
				}
			} catch (Exception e) {
				Layout.hideWaitPopup();
			}

		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}

	}

	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate();
		if (filePrimarioForm.getValue("uriFilePrimario") != null && !"".equals(filePrimarioForm.getValue("uriFilePrimario"))) {
			InfoFileRecord infoFilePrimario = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
			if (infoFilePrimario == null || infoFilePrimario.getMimetype() == null || infoFilePrimario.getMimetype().equals("")) {
				HashMap errors = new HashMap();
				errors.put("nomeFilePrimario", "Formato file non riconosciuto");
				filePrimarioForm.setErrors(errors);
				valid = false;
			}
			if (infoFilePrimario.getBytes() <= 0) {
				HashMap errors = new HashMap();
				errors.put("nomeFilePrimario", "Il file ha dimensione 0");
				filePrimarioForm.setErrors(errors);
				valid = false;
			}
		}		
		return valid;
	}

	public Record getRecordToSave() {

		Record lRecordToSave = new Record();

		RecordList lRecordListAllegati = null;
		if (fileAllegatiForm != null) {
			if (fileAllegatiForm.getValue("listaAllegati") != null) {
				RecordList lRecordListNew = fileAllegatiForm.getValueAsRecordList("listaAllegati");
				lRecordListAllegati = new RecordList();
				Record lRecordDef = new Record();
				for (int i = 0; i < lRecordListNew.getLength(); i++) {
					lRecordDef = lRecordListNew.get(i);
					InfoFileRecord lRecordInfo = InfoFileRecord.buildInfoFileRecord(lRecordListNew.get(i).getAttributeAsJavaScriptObject("infoFile"));
					lRecordDef.setAttribute("infoFile", lRecordInfo);
					lRecordListAllegati.add(lRecordDef);
				}
			}
		}

		lRecordToSave.setAttribute("listaAllegati", lRecordListAllegati);

		addFormValues(lRecordToSave, testataForm);
		addFormValues(lRecordToSave, contraenteForm);
		addFormValues(lRecordToSave, contenutiForm);
		addFormValues(lRecordToSave, filePrimarioForm);
		addFormValues(lRecordToSave, protocolloForm);
		addFormValues(lRecordToSave, conservazioneSostitutivaForm);

		return lRecordToSave;
	}

	protected static void addFormValues(Record record, DynamicForm form) {
		if (form != null) {
			try {
				Record formRecord = form.getValuesAsRecord();
				for (Object fieldName : form.getValues().keySet()) {
					FormItem item = form.getItem((String) fieldName);
					if (item != null && (item instanceof ReplicableItem)) {						
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
					} 
				}
				JSOHelper.addProperties(record.getJsObj(), formRecord.getJsObj());
			} catch (Exception e) {
			}
		}		
	}

	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);

		setCanEdit(false, conservazioneSostitutivaForm);
	}

	protected void manageFileFirmatoButtonClick() {

		MenuItem informazioniFirmaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_dettaglioCertificazione_prompt(), "buttons/detail.png");

		final InfoFileRecord infoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
		final String uriFilePrimario = filePrimarioForm.getValueAsString("uriFilePrimario");

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
		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				String filename = infoFile.getCorrectFileName();
				String uri = data.getAttribute("tempFileOut");
				// String url = (GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename) + "&url=" +
				// URL.encode(uri));
				preview(null, null, filename, uri, false, infoFile);
			}
		});

	}
}
