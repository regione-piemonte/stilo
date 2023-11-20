/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
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
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.ScanUtility;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaEtichettaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroBean;
import it.eng.auriga.ui.module.layout.client.timbra.CopertinaTimbroWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroCopertinaUtil;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class TaskStandardDetail extends TaskDetail {

	protected DynamicForm dataPeriodoEventoForm;
	protected HiddenItem flgEventoDurativoItem;
	protected DateTimeItem dataEventoItem;
	protected DateTimeItem dataInizioEventoItem;
	protected DateTimeItem dataFineEventoItem;

	protected DynamicForm registrazioneForm;
	protected HiddenItem idUdItem;
	protected HiddenItem flgDocEntrataItem;
	protected HiddenItem flgTipoProvItem;
	protected SelectItem tipoRegistrazioneItem;
	protected TextItem siglaRegistrazioneItem;
	protected ExtendedTextItem nroRegistrazioneItem;
	protected AnnoItem annoRegistrazioneItem;
	protected DateItem dataRegistrazioneItem;
	protected DynamicForm filePrimarioForm;
	protected HiddenItem idDocPrimarioItem;
	protected ExtendedTextItem nomeFilePrimarioItem;
	protected HiddenItem nomeFilePrimarioOrigItem;
	protected HiddenItem nomeFilePrimarioTifItem;
	protected HiddenItem uriFilePrimarioTifItem;
	protected HiddenItem uriFilePrimarioItem;
	protected HiddenItem infoFileItem;
	protected HiddenItem remoteUriFilePrimarioItem;
	protected HiddenItem isDocPrimarioChangedItem;
	protected FileUploadItemWithFirmaAndMimeType uploadFilePrimarioItem;
	protected DynamicForm altriDatiDocForm;
	protected TextAreaItem oggettoItem;
	protected DateItem dataDocumentoItem;
	protected ImgButtonItem previewButton;
	protected ImgButtonItem previewEditButton;
	protected ImgButtonItem editButton;
	protected ImgButtonItem fileFirmatoDigButton;
	protected ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem fileMarcatoTempButton;
	protected ImgButtonItem altreOpButton;
	protected NestedFormItem filePrimarioButtons;

	protected DynamicForm protRicevutoForm;
	protected TextItem rifOrigineProtRicevutoItem;
	protected ExtendedTextItem nroProtRicevutoItem;
	protected AnnoItem annoProtRicevutoItem;
	protected DateItem dataProtRicevutoItem;

	protected DynamicForm fileAllegatiForm;
	protected AllegatiItem fileAllegatiItem;

	protected DynamicForm noteForm;
	protected TextAreaItem noteItem;

	protected DynamicForm esitoForm;
	protected SelectItem esitoItem;

	protected DetailSection detailSectionDocumento;
	protected DetailSection detailSectionAllegati;
	protected DetailSection detailSectionProtRicevuto;
	protected DetailSection detailSectionNote;
	protected DetailSection detailSectionEsito;

	boolean forceToShowElimina = false;

	public TaskStandardDetail(String nomeEntita, RecordList attributiAdd, Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista,
			Map mappaDocumenti) {

		super(nomeEntita, attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista, mappaDocumenti);

	}

	@Override
	public void addDetailMembers() {

		dataPeriodoEventoForm = new DynamicForm();
		dataPeriodoEventoForm.setValuesManager(vm);
		dataPeriodoEventoForm.setWidth100();
		dataPeriodoEventoForm.setPadding(5);
		dataPeriodoEventoForm.setWrapItemTitles(false);
		dataPeriodoEventoForm.setNumCols(8);
		dataPeriodoEventoForm.setColWidths(1, 1, 1, 1, 1, 1, "*", "*");

		flgEventoDurativoItem = new HiddenItem("flgEventoDurativo");

		dataEventoItem = new DateTimeItem("dataEvento", "Data evento");
		dataEventoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return flgEventoDurativoItem.getValue() == null || "false".equals(String.valueOf(flgEventoDurativoItem.getValue()));
			}
		});

		dataInizioEventoItem = new DateTimeItem("dataInizioEventoItem", "Periodo dal");
		dataInizioEventoItem.setEndRow(false);
		dataInizioEventoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return flgEventoDurativoItem.getValue() != null && "true".equals(String.valueOf(flgEventoDurativoItem.getValue()));
			}
		});

		dataFineEventoItem = new DateTimeItem("dataFineEvento", "al");
		dataFineEventoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return flgEventoDurativoItem.getValue() != null && "true".equals(String.valueOf(flgEventoDurativoItem.getValue()));
			}
		});

		dataPeriodoEventoForm.setItems(dataEventoItem, dataInizioEventoItem, dataFineEventoItem);

		registrazioneForm = new DynamicForm();
		registrazioneForm.setValuesManager(vm);
		registrazioneForm.setWidth100();
		registrazioneForm.setPadding(5);
		registrazioneForm.setWrapItemTitles(false);
		registrazioneForm.setNumCols(14);
		registrazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		idUdItem = new HiddenItem("idUd");

		flgDocEntrataItem = new HiddenItem("flgDocEntrata");

		flgTipoProvItem = new HiddenItem("flgTipoProv");
		flgTipoProvItem.setValue(getFlgTipoProv());

		CustomValidator lValorizzatoSeAltriValorizzatiValidatorRegistrazione = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				String name = getFormItem().getName();
				if (isBlank(value)) {
					boolean valid = true;
					if ("nroRegistrazione".equals(name)) {
						valid = valid && isBlank(annoRegistrazioneItem.getValue()) && isBlank(dataRegistrazioneItem.getValue());
					} else if ("annoRegistrazione".equals(name)) {
						valid = valid && (isBlank(nroRegistrazioneItem.getValue()) || !isBlank(dataRegistrazioneItem.getValue()));
					} else if ("dataRegistrazione".equals(name)) {
						valid = valid && (isBlank(nroRegistrazioneItem.getValue()) || !isBlank(annoRegistrazioneItem.getValue()));
					}
					return valid;
				} else
					return true;
			}

			private boolean isBlank(Object value) {
				return (value == null || ((value instanceof String) && "".equals(value.toString().trim())));
			}
		};

		tipoRegistrazioneItem = new SelectItem("tipoRegistrazione", "Tipo reg.");
		tipoRegistrazioneItem.setWidth(100);
		LinkedHashMap<String, String> tipoRegistrazioneValueMap = new LinkedHashMap<String, String>();
		tipoRegistrazioneValueMap.put("PG", "Prot. Generale");
		tipoRegistrazioneValueMap.put("R", "Repertorio");
		tipoRegistrazioneValueMap.put("PI", "Prot. interno");
		tipoRegistrazioneValueMap.put("A", "Altro");
		tipoRegistrazioneItem.setValueMap(tipoRegistrazioneValueMap);
		tipoRegistrazioneItem.setDefaultValue("PG");
		tipoRegistrazioneItem.setHint("&nbsp;&nbsp;&nbsp;");
		tipoRegistrazioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				registrazioneForm.markForRedraw();
			}
		});

		siglaRegistrazioneItem = new TextItem("siglaRegistrazione", "Registro");
		siglaRegistrazioneItem.setWidth(150);
		siglaRegistrazioneItem.setLength(50);
		siglaRegistrazioneItem.setWrapTitle(false);
		siglaRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return tipoRegistrazioneItem.getValueAsString() != null && !"PG".equals(tipoRegistrazioneItem.getValueAsString());
			}
		});

		nroRegistrazioneItem = new ExtendedTextItem("nroRegistrazione", "N°");
		nroRegistrazioneItem.setWidth(100);
		nroRegistrazioneItem.setLength(50);
		nroRegistrazioneItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorRegistrazione);
		nroRegistrazioneItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroRegistrazioneItem.validate();
				annoRegistrazioneItem.validate();
				dataRegistrazioneItem.validate();
			}
		});

		annoRegistrazioneItem = new AnnoItem("annoRegistrazione", "/ Anno");
		annoRegistrazioneItem.setWidth(100);
		annoRegistrazioneItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorRegistrazione);
		annoRegistrazioneItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroRegistrazioneItem.validate();
				annoRegistrazioneItem.validate();
				dataRegistrazioneItem.validate();
			}
		});

		dataRegistrazioneItem = new DateItem("dataRegistrazione", "del");
		dataRegistrazioneItem.setColSpan(1);
		dataRegistrazioneItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorRegistrazione);
		dataRegistrazioneItem.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroRegistrazioneItem.validate();
				annoRegistrazioneItem.validate();
				dataRegistrazioneItem.validate();
			}
		});

		registrazioneForm.setItems(idUdItem, flgDocEntrataItem, flgTipoProvItem, tipoRegistrazioneItem, siglaRegistrazioneItem, nroRegistrazioneItem,
				annoRegistrazioneItem, dataRegistrazioneItem);

		filePrimarioForm = new DynamicForm();
		filePrimarioForm.setValuesManager(vm);
		filePrimarioForm.setWidth100();
		filePrimarioForm.setPadding(5);
		filePrimarioForm.setNumCols(14);
		filePrimarioForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		filePrimarioForm.setWrapItemTitles(false);

		idDocPrimarioItem = new HiddenItem("idDocPrimario");

		// file primario
		nomeFilePrimarioItem = new ExtendedTextItem("nomeFilePrimario", I18NUtil.getMessages().protocollazione_detail_nomeFilePrimarioItem_title());
		nomeFilePrimarioItem.setWidth(250);
		nomeFilePrimarioItem.setStartRow(true);
		nomeFilePrimarioItem.setColSpan(4);
		nomeFilePrimarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				if (nomeFilePrimarioItem.getValue() == null || ((String) nomeFilePrimarioItem.getValue()).trim().equals("")) {
					clickEliminaFile();
				} else if (filePrimarioForm.getValueAsString("nomeFilePrimarioOrig") != null
						&& !"".equals(filePrimarioForm.getValueAsString("nomeFilePrimarioOrig"))
						&& !nomeFilePrimarioItem.getValue().equals(filePrimarioForm.getValueAsString("nomeFilePrimarioOrig"))) {
					filePrimarioForm.setValue("isDocPrimarioChanged", true);
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
		isDocPrimarioChangedItem.setDefaultValue(false);

		uploadFilePrimarioItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				filePrimarioForm.setValue("nomeFilePrimario", displayFileName);
				filePrimarioForm.setValue("uriFilePrimario", uri);
				filePrimarioForm.setValue("nomeFilePrimarioTif", "");
				filePrimarioForm.setValue("uriFilePrimarioTif", "");
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
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
				String nomeFilePrimario = filePrimarioForm.getValueAsString("nomeFilePrimario");
				String nomeFilePrimarioOrig = filePrimarioForm.getValueAsString("nomeFilePrimarioOrig");
				if (precImpronta == null
						|| !precImpronta.equals(info.getImpronta())
						|| (nomeFilePrimario != null && !"".equals(nomeFilePrimario) && nomeFilePrimarioOrig != null && !"".equals(nomeFilePrimarioOrig) && !nomeFilePrimario
								.equals(nomeFilePrimarioOrig))) {
					filePrimarioForm.setValue("isDocPrimarioChanged", true);
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
				
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
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
				if (canEditByApplet() && uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_EDITING_FILE")) {
						if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
							InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
							String estensione = null;
							if (lInfoFileRecord.getCorrectFileName() != null && !lInfoFileRecord.getCorrectFileName().trim().equals("")) {
								estensione = lInfoFileRecord.getCorrectFileName().substring(lInfoFileRecord.getCorrectFileName().lastIndexOf(".") + 1);
							} else {
								estensione = nomeFilePrimarioItem.getValueAsString().substring(nomeFilePrimarioItem.getValueAsString().lastIndexOf(".") + 1);
							}
							for (String lString : Layout.getGenericConfig().getEditableExtension()) {
								if (lString.equals(estensione)) {
									return true;
								}
							}
							return false;
						} else
							return false;
					} else
						return false;
				} else
					return false;
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
				
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
				generaFileFirmaAndPreview(lInfoFileRecord, uriFilePrimarioItem.getValue().toString(), true);

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
				
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));
				generaFileFirmaAndPreview(lInfoFileRecord, uriFilePrimarioItem.getValue().toString(), true);
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
				
				final Menu altreOpMenu = new Menu();
				altreOpMenu.setOverflow(Overflow.VISIBLE);
				altreOpMenu.setShowIcons(true);
				altreOpMenu.setSelectionType(SelectionStyle.SINGLE);
				altreOpMenu.setCanDragRecordsOut(false);
				altreOpMenu.setWidth("*");
				altreOpMenu.setHeight("*");
				
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("infoFile"));

				if(filePrimarioButtons.isEditing()) {
					MenuItem acquisisciDaScannerMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_acquisisciDaScannerMenuItem_prompt(),
							"file/scanner.png");
					acquisisciDaScannerMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickAcquisisciDaScanner();
						}
					});
					altreOpMenu.addItem(acquisisciDaScannerMenuItem);
	
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
					altreOpMenu.addItem(firmaMenuItem);
			}

				MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
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
				altreOpMenu.addItem(downloadMenuItem);
				
//				if() {     TODO: timbro
					boolean flgAddSubMenuTimbra = false;
	
					MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
					Menu timbraSubMenu = new Menu();
	
					MenuItem apponiSegnaturaRegistrazioneFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiSegnatura(), "file/timbra.gif");
					apponiSegnaturaRegistrazioneFileAllegatoMenuItem.addClickHandler(new ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							clickTimbra("");
						}
					});
					apponiSegnaturaRegistrazioneFileAllegatoMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
	
						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							
							if (!mode.equals("new") && (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""))) {
								InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
								return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
							}
							return false;
						}
					});
					timbraSubMenu.addItem(timbraMenuItem);
					
					if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
						if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && 
								(lInfoFileRecord.getMimetype().equals("application/pdf") || lInfoFileRecord.getMimetype().startsWith("image"))) {
							MenuItem timbroConformitaOriginaleMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(), "file/timbra.gif");
							timbroConformitaOriginaleMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
	
								@Override
								public boolean execute(Canvas target, Menu menu, MenuItem item) {
									
									if (!mode.equals("new") && (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""))) {
										InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
										return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
									}
									return false;
								}
							});
							timbroConformitaOriginaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
								
								@Override
								public void onClick(MenuItemClickEvent event) {
									clickTimbra("CONFORMITA_ORIG_CARTACEO");
								}
							});
							flgAddSubMenuTimbra=true;
							timbraSubMenu.addItem(timbroConformitaOriginaleMenuItem);
						}
					}
					
					if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
						flgAddSubMenuTimbra=true;
						
						MenuItem timbroCopiaConformeMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
						timbroCopiaConformeMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
	
							@Override
							public boolean execute(Canvas target, Menu menu, MenuItem item) {
								
								if (!mode.equals("new") && (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals(""))) {
									InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
									return lInfoFileRecord != null && lInfoFileRecord.isConvertibile();
								}
								return false;
							}
						});
						
						Menu sottoMenuCopiaConformeItem = new Menu();
						
						MenuItem timbroCopiaConformeStampaItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
						timbroCopiaConformeStampaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								clickTimbra("CONFORMITA_ORIG_DIGITALE_STAMPA");
							}
						});
						sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeStampaItem);
						
						MenuItem timbroCopiaConformeDigitaleItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");	
						timbroCopiaConformeDigitaleItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							
							@Override
							public void onClick(MenuItemClickEvent event) {
								clickTimbra("CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
							}
						});
						sottoMenuCopiaConformeItem.addItem(timbroCopiaConformeDigitaleItem);
						
						timbroCopiaConformeMenuItem.setSubmenu(sottoMenuCopiaConformeItem);			
						timbraSubMenu.addItem(timbroCopiaConformeMenuItem);
					}
					
					//Se ho piu voci aggiungo il sottoMenu Timbra
					if(flgAddSubMenuTimbra) {
						timbraMenuItem.setSubmenu(timbraSubMenu);
						altreOpMenu.addItem(timbraMenuItem);
						//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
					}else {
						apponiSegnaturaRegistrazioneFileAllegatoMenuItem.setTitle("Timbra");
						altreOpMenu.addItem(apponiSegnaturaRegistrazioneFileAllegatoMenuItem);
					}
				
				
					//Copertina con segnatura di registrazione
					MenuItem copertinaConSegnatureRegistrazioneMenuItem = new MenuItem(AurigaLayout.getParametroDBAsBoolean("TIMBRO_PROFILO_DATI_SPEC_TIPO")
							? I18NUtil.getMessages().protocollazione_detail_copertinaConSegnatura()
							: I18NUtil.getMessages().protocollazione_detail_copertinaConTimbro(), "blank.png");
					copertinaConSegnatureRegistrazioneMenuItem.addClickHandler(new ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							
							clickCopertinaConSegnaturaReg();
						}
					});
					copertinaConSegnatureRegistrazioneMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
						
						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							
							String idUd = new Record(vm.getValues()).getAttribute("idUd");
							if (idUd != null && !"".equals(idUd)) {
								return true;
							}
							return false;
						}
					});
					altreOpMenu.addItem(copertinaConSegnatureRegistrazioneMenuItem);
					
					//Copertina con etichetta
					MenuItem copertinaConEtichettaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_copertinaConEtichetta(),
							"protocollazione/stampaEtichetta.png");
					copertinaConEtichettaMenuItem.addClickHandler(new ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							//TODO
							clickCopertinaConEtichetta();
						}
					});
					copertinaConEtichettaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
						
						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							
							String idUd = new Record(vm.getValues()).getAttribute("idUd");
							if (idUd != null && !"".equals(idUd)) {
								return true;
							}
							return false;
						}
					});
					altreOpMenu.addItem(copertinaConEtichettaMenuItem);
					
					//Copertina con etichetta multipla
					MenuItem copertinaConEtichettaMultiplaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_copertinaConEtichettaMultipla(),
							"protocollazione/stampaEtichetta.png");
					copertinaConEtichettaMultiplaMenuItem.addClickHandler(new ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							//TODO
							clickCopertinaConEtichettaMultipla();
						}
					});
					copertinaConEtichettaMultiplaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {
						
						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							
							String idUd = new Record(vm.getValues()).getAttribute("idUd");
							if (idUd != null && !"".equals(idUd)) {
								return true;
							}
							return false;
						}
					});
					altreOpMenu.addItem(copertinaConEtichettaMultiplaMenuItem);
//				}
					
				if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
					String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
					MenuItem timbroConformitaCustomMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
					timbroConformitaCustomMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
					timbroConformitaCustomMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									clickTimbra("COPIA_CONFORME_CUSTOM");
								}
							});

					altreOpMenu.addItem(timbroConformitaCustomMenuItem);

				}

				if (filePrimarioButtons.isEditing() || forceToShowElimina) {
					MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(), "file/editdelete.png");
					eliminaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

						@Override
						public boolean execute(Canvas target, Menu menu, MenuItem item) {
							// Se è valorizzato il file
							if (uriFilePrimarioItem.getValue() != null) {
								// Se il file è vuoto
								if (uriFilePrimarioItem.getValue().equals("")) {
									// Non mostro elimina
									return false;
								} else {
									return true;
								}
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
					altreOpMenu.addItem(eliminaMenuItem);
				}

				altreOpMenu.showContextMenu();
			}
		});

		filePrimarioButtons.setNestedFormItems(previewButton, previewEditButton, editButton, fileFirmatoDigButton, firmaNonValidaButton, fileMarcatoTempButton, altreOpButton);

		infoFileItem = new HiddenItem("infoFile");
		infoFileItem.setVisible(false);

		filePrimarioForm.setItems(idDocPrimarioItem, nomeFilePrimarioOrigItem, nomeFilePrimarioItem, uploadFilePrimarioItem, uriFilePrimarioItem,
				filePrimarioButtons, infoFileItem, remoteUriFilePrimarioItem, isDocPrimarioChangedItem);

		altriDatiDocForm = new DynamicForm();
		altriDatiDocForm.setValuesManager(vm);
		altriDatiDocForm.setWidth100();
		altriDatiDocForm.setPadding(5);
		altriDatiDocForm.setNumCols(14);
		altriDatiDocForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		altriDatiDocForm.setWrapItemTitles(false);

		oggettoItem = new TextAreaItem("oggetto", I18NUtil.getMessages().protocollazione_detail_oggettoItem_title());
		oggettoItem.setLength(4000);
		oggettoItem.setHeight(40);
		oggettoItem.setColSpan(8);
		oggettoItem.setWidth(650);
		oggettoItem.setStartRow(true);

		dataDocumentoItem = new DateItem("dataDocumento", I18NUtil.getMessages().protocollazione_detail_dataDocumentoItem_title());
		dataDocumentoItem.setStartRow(false);
		dataDocumentoItem.setColSpan(1);

		altriDatiDocForm.setItems(oggettoItem, dataDocumentoItem);

		protRicevutoForm = new DynamicForm();
		protRicevutoForm.setValuesManager(vm);
		protRicevutoForm.setWidth("100%");
		protRicevutoForm.setPadding(5);
		protRicevutoForm.setWrapItemTitles(false);
		protRicevutoForm.setNumCols(14);
		protRicevutoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		CustomValidator lValorizzatoSeAltriValorizzatiValidatorProtRicevuto = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				String name = getFormItem().getName();
				if (isBlank(value)) {
					boolean valid = true;
					if ("nroProtRicevuto".equals(name)) {
						valid = valid && isBlank(annoProtRicevutoItem.getValue()) && isBlank(dataProtRicevutoItem.getValue());
					} else if ("annoProtRicevuto".equals(name)) {
						valid = valid && (isBlank(nroProtRicevutoItem.getValue()) || !isBlank(dataProtRicevutoItem.getValue()));
					} else if ("dataProtRicevuto".equals(name)) {
						valid = valid && (isBlank(nroProtRicevutoItem.getValue()) || !isBlank(annoProtRicevutoItem.getValue()));
					}
					return valid;
				} else
					return true;
			}

			private boolean isBlank(Object value) {
				return (value == null || ((value instanceof String) && "".equals(value.toString().trim())));
			}
		};

		rifOrigineProtRicevutoItem = new TextItem("rifOrigineProtRicevuto", I18NUtil.getMessages().protocollazione_detail_rifOrigineProtRicevutoItem_title());
		rifOrigineProtRicevutoItem.setWidth(150);
		rifOrigineProtRicevutoItem.setLength(50);
		rifOrigineProtRicevutoItem.setWrapTitle(false);

		nroProtRicevutoItem = new ExtendedTextItem("nroProtRicevuto", I18NUtil.getMessages().protocollazione_detail_nroProtRicevutoItem_title());
		nroProtRicevutoItem.setWidth(100);
		nroProtRicevutoItem.setLength(50);
		nroProtRicevutoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		nroProtRicevutoItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroProtRicevutoItem.validate();
				annoProtRicevutoItem.validate();
				dataProtRicevutoItem.validate();
			}
		});

		annoProtRicevutoItem = new AnnoItem("annoProtRicevuto", I18NUtil.getMessages().protocollazione_detail_annoProtRicevutoItem_title());
		annoProtRicevutoItem.setWidth(100);
		annoProtRicevutoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		annoProtRicevutoItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroProtRicevutoItem.validate();
				annoProtRicevutoItem.validate();
				dataProtRicevutoItem.validate();
			}
		});

		dataProtRicevutoItem = new DateItem("dataProtRicevuto", I18NUtil.getMessages().protocollazione_detail_dataProtRicevutoItem_title());
		dataProtRicevutoItem.setColSpan(1);
		dataProtRicevutoItem.setValidators(lValorizzatoSeAltriValorizzatiValidatorProtRicevuto);
		dataProtRicevutoItem.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				nroProtRicevutoItem.validate();
				annoProtRicevutoItem.validate();
				dataProtRicevutoItem.validate();
			}
		});

		protRicevutoForm.setItems(rifOrigineProtRicevutoItem, nroProtRicevutoItem, annoProtRicevutoItem, dataProtRicevutoItem);

		fileAllegatiForm = new DynamicForm();
		fileAllegatiForm.setValuesManager(vm);
		fileAllegatiForm.setWidth("100%");
		fileAllegatiForm.setPadding(5);

		fileAllegatiItem = new AllegatiItem();
		fileAllegatiItem.setName("listaAllegati");
		fileAllegatiItem.setShowTitle(false);

		fileAllegatiForm.setItems(fileAllegatiItem);

		// Note
		noteForm = new DynamicForm();
		noteForm.setValuesManager(vm);
		noteForm.setWidth100();
		noteForm.setPadding(5);
		noteForm.setWrapItemTitles(false);
		noteForm.setNumCols(8);
		noteForm.setColWidths(1, 1, 1, 1, 1, 1, "*", "*");

		noteItem = new TextAreaItem("note");
		noteItem.setShowTitle(false);
		noteItem.setHeight(40);
		noteItem.setWidth(650);
		noteItem.setStartRow(true);
		noteItem.setColSpan(5);

		noteForm.setItems(noteItem);

		esitoForm = new DynamicForm();
		esitoForm.setValuesManager(vm);
		esitoForm.setWidth100();
		esitoForm.setPadding(5);
		esitoForm.setWrapItemTitles(false);
		esitoForm.setNumCols(8);
		esitoForm.setColWidths(1, 1, 1, 1, 1, 1, "*", "*");

		esitoItem = new SelectItem("esito");
		esitoItem.setShowTitle(false);
		esitoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return detailSectionEsito.isVisible();
			}
		}));

		esitoForm.setItems(esitoItem);

		addMember(dataPeriodoEventoForm);

		detailSectionDocumento = new DetailSection("Documento", true, true, false, registrazioneForm, filePrimarioForm, altriDatiDocForm);
		detailSectionDocumento.setVisible(false);
		addMember(detailSectionDocumento);

		detailSectionProtRicevuto = new DetailSection("Prot. ricevuto", true, true, false, protRicevutoForm);
		detailSectionProtRicevuto.setVisible(false);
		addMember(detailSectionProtRicevuto);

		detailSectionAllegati = new DetailSection("Allegati", true, true, false, fileAllegatiForm);
		addMember(detailSectionAllegati);

		for (DetailSection detailSection : attributiDinamiciDetailSections) {
			addMember(detailSection);
		}

		detailSectionNote = new DetailSection("Messaggio", true, true, false, noteForm);
		addMember(detailSectionNote);

		detailSectionEsito = new DetailSection("Esito", true, true, false, esitoForm);
		detailSectionEsito.setVisible(false);
		addMember(detailSectionEsito);
	}

	@Override
	public void editRecord(Record record) {
		
		LinkedHashMap<String, String> esitoValueMap = new LinkedHashMap<String, String>();
		if (record.getAttributeAsMap("valoriEsito") != null) {
			final Iterator iterator = record.getAttributeAsMap("valoriEsito").keySet().iterator();
			while (iterator.hasNext()) {
				final String key = (String) iterator.next();
				esitoValueMap.put(key, (String) record.getAttributeAsMap("valoriEsito").get(key));
			}
		}
		esitoItem.setValueMap(esitoValueMap);
		super.editRecord(record);
		if (record.getAttributeAsString("nomeDocType") != null && !"".equals(record.getAttributeAsString("nomeDocType"))) {
			detailSectionDocumento.setTitle("Documento: " + record.getAttributeAsString("nomeDocType"));
		}
		detailSectionDocumento.setVisible(record.getAttributeAsString("idDocType") != null && !"".equals(record.getAttributeAsString("idDocType")));
		detailSectionProtRicevuto.setVisible(record.getAttributeAsString("idDocType") != null && !"".equals(record.getAttributeAsString("idDocType"))
				&& record.getAttributeAsBoolean("flgDocEntrata") != null && record.getAttributeAsBoolean("flgDocEntrata"));
		detailSectionAllegati.setVisible(record.getAttributeAsString("idDocType") != null && !"".equals(record.getAttributeAsString("idDocType")));
		detailSectionNote.setVisible(false);
		detailSectionEsito.setVisible(record.getAttributeAsBoolean("flgPresenzaEsito") != null && record.getAttributeAsBoolean("flgPresenzaEsito"));
	}

	@Override
	public void editNewRecord(Map initialValues) {
		
		Record record = new Record(initialValues);
		LinkedHashMap<String, String> esitoValueMap = new LinkedHashMap<String, String>();
		if (record.getAttributeAsMap("valoriEsito") != null) {
			final Iterator iterator = record.getAttributeAsMap("valoriEsito").keySet().iterator();
			while (iterator.hasNext()) {
				final String key = (String) iterator.next();
				esitoValueMap.put(key, (String) record.getAttributeAsMap("valoriEsito").get(key));
			}
		}
		esitoItem.setValueMap(esitoValueMap);
		super.editNewRecord(initialValues);
		if (record.getAttributeAsString("nomeDocType") != null && !"".equals(record.getAttributeAsString("nomeDocType"))) {
			detailSectionDocumento.setTitle("Documento: " + record.getAttributeAsString("nomeDocType"));
		}
		detailSectionDocumento.setVisible(record.getAttributeAsString("idDocType") != null && !"".equals(record.getAttributeAsString("idDocType")));
		detailSectionProtRicevuto.setVisible(record.getAttributeAsString("idDocType") != null && !"".equals(record.getAttributeAsString("idDocType"))
				&& record.getAttributeAsBoolean("flgDocEntrata") != null && record.getAttributeAsBoolean("flgDocEntrata"));
		detailSectionAllegati.setVisible(record.getAttributeAsString("idDocType") != null && !"".equals(record.getAttributeAsString("idDocType")));
		detailSectionNote.setVisible(true);
		detailSectionEsito.setVisible(record.getAttributeAsBoolean("flgPresenzaEsito") != null && record.getAttributeAsBoolean("flgPresenzaEsito"));
	}

	// Cancellazione del file
	public void clickEliminaFile() {
		nomeFilePrimarioItem.setValue("");
		uriFilePrimarioItem.setValue("");
		nomeFilePrimarioTifItem.setValue("");
		uriFilePrimarioTifItem.setValue("");
		filePrimarioForm.markForRedraw();
		filePrimarioButtons.markForRedraw();
		uploadFilePrimarioItem.getCanvas().redraw();
		remoteUriFilePrimarioItem.setValue(false);
		infoFileItem.clearValue();
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

	public void clickAcquisisciDaScanner() {
		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(final String filePdf, final String uriPdf, String fileTif, String uriTif, String record) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					filePrimarioForm.setValue("isDocPrimarioChanged", true);
				}
				filePrimarioForm.setValue("nomeFilePrimario", filePdf);
				filePrimarioForm.setValue("uriFilePrimario", uriPdf);
				filePrimarioForm.setValue("nomeFilePrimarioTif", fileTif);
				filePrimarioForm.setValue("uriFilePrimarioTif", uriTif);
				filePrimarioForm.setValue("remoteUriFilePrimario", false);
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
		// FirmaWindow firmaWindow = new FirmaWindow(uri, display, lInfoFileRecord) {
		// @Override
		// public void firmaCallBack(String nomeFileFirmato, String uriFileFirmato, String record) {
		// InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
		// String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
		// InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
		// filePrimarioForm.setValue("infoFile", info);
		// if(precImpronta == null || !precImpronta.equals(info.getImpronta())) {
		// filePrimarioForm.setValue("isDocPrimarioChanged", true);
		// }
		// filePrimarioForm.setValue("nomeFilePrimario", nomeFileFirmato);
		// filePrimarioForm.setValue("uriFilePrimario", uriFileFirmato);
		// filePrimarioForm.setValue("nomeFilePrimarioTif", "");
		// filePrimarioForm.setValue("uriFilePrimarioTif", "");
		// filePrimarioForm.setValue("remoteUriFilePrimario", false);
		// filePrimarioForm.markForRedraw();
		// filePrimarioButtons.markForRedraw();
		// changedEventAfterUpload(nomeFileFirmato,uriFileFirmato);
		// }
		// };
		// firmaWindow.show();
		FirmaUtility.firmaMultipla(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("infoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					filePrimarioForm.setValue("isDocPrimarioChanged", true);
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

	public void clickTimbra(String finalita) {
		String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		String remote = filePrimarioForm.getValueAsString("remoteUriFilePrimario");
		InfoFileRecord precInfo = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
		String idUd = new Record(vm.getValues()).getAttribute("idUd");
		String idDoc = filePrimarioForm != null ? filePrimarioForm.getValueAsString("idDocPrimario") : null;
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina") != null ?
				AurigaLayout.getImpostazioneTimbro("tipoPagina") : "";
				
		/*Controllo introdotto per la nuova modalità di timbratura per i file firmati che devono saltare la scelta della preferenza*/
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		boolean flgBustaPdfTimbratura = false;
		
		if(precInfo.isFirmato() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_BUSTA_PDF_FILE_FIRMATO") && !finalita.equalsIgnoreCase("CONFORMITA_ORIG_DIGITALE_STAMPA")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = true;
		}		

		if(finalita.equalsIgnoreCase("COPIA_CONFORME_CUSTOM")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = false;
		}
		
		if(skipSceltaTimbratura){
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			record.setAttribute("nomeFile", display);
			record.setAttribute("uri", uri);
			record.setAttribute("remote", remote);
			record.setAttribute("mimetype", precInfo.getMimetype());
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			if (finalita.equals("CONFORMITA_ORIG_CARTACEO")) {
				record.setAttribute("tipoPagina", "tutte");
			}else {
				record.setAttribute("tipoPagina", tipoPaginaPref);
			}
			record.setAttribute("finalita", finalita);
						
			if(flgBustaPdfTimbratura) {
				TimbroUtil.callStoreLoadFilePerTimbroConBusta(record);
			}else {
				TimbroUtil.buildDatiSegnatura(record);
			}
		}else{
						
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remote), 
				precInfo.getMimetype(), idUd, idDoc, rotazioneTimbroPref,posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}	
	}
	
	public void clickCopertinaConSegnaturaReg() {
		
		Record detailRecord = new Record(vm.getValues());
		String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		String numeroAllegato = "";
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		
		if(AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniCopertina")){
			
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("numeroAllegato", numeroAllegato);
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			
			TimbroCopertinaUtil.buildDatiSegnatura(record);
			
		}else{
			
			Record copertinaTimbroRecord = new Record();
			copertinaTimbroRecord.setAttribute("idUd", idUd);
			copertinaTimbroRecord.setAttribute("numeroAllegato", numeroAllegato);
			copertinaTimbroRecord.setAttribute("tipoTimbroCopertina", "");
			copertinaTimbroRecord.setAttribute("flgDocFolder", "D");
			copertinaTimbroRecord.setAttribute("rotazioneTimbro", rotazioneTimbroPref);
			copertinaTimbroRecord.setAttribute("posizioneTimbro", posizioneTimbroPref);
			copertinaTimbroRecord.setAttribute("provenienza", "A");
			copertinaTimbroRecord.setAttribute("posizionale", "");
			
			CopertinaTimbroBean copertinaTimbroBean = new CopertinaTimbroBean(copertinaTimbroRecord);
			CopertinaTimbroWindow copertinaTimbroWindow = new CopertinaTimbroWindow("copertina",true,copertinaTimbroBean);
			copertinaTimbroWindow.show();
		}

	}

	protected void clickEditFile() {
		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("idDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("nomeFilePrimario");
		final String uri = filePrimarioForm.getValueAsString("uriFilePrimario");
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("infoFile"));
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("remoteUriFilePrimario"));
		String estensione = null;
		if (info.getCorrectFileName() != null && !info.getCorrectFileName().trim().equals("")) {
			estensione = info.getCorrectFileName().substring(info.getCorrectFileName().lastIndexOf(".") + 1);
		} else {
			estensione = display.substring(display.lastIndexOf(".") + 1);
		}
		String impronta = info.getImpronta();
		OpenEditorUtility.openEditor(display, uri, remoteUri, estensione, impronta, new OpenEditorCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, String record) {
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				filePrimarioForm.setValue("infoFile", info);
				filePrimarioForm.setValue("isDocPrimarioChanged", true);
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

	public void preview(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, remoteUri, info, "FileToExtractBean", display);
	}

	public void previewWithJPedal(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd,
				rotazioneTimbroPref,posizioneTimbroPref);
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
							filePrimarioForm.setValue("isDocPrimarioChanged", true);
						}
						filePrimarioForm.setValue("nomeFilePrimario", filePdf);
						filePrimarioForm.setValue("uriFilePrimario", uriPdf);
						filePrimarioForm.setValue("nomeFilePrimarioTif", "");
						filePrimarioForm.setValue("uriFilePrimarioTif", "");
						filePrimarioForm.setValue("remoteUriFilePrimario", false);
						filePrimarioForm.markForRedraw();
						filePrimarioButtons.markForRedraw();
						changedEventAfterUpload(filePdf, uriPdf);
					}
				};
				previewDocWindow.show();
			}
		});
	}

	// Download del file
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
	
	private void generaFileFirmaAndPreview(final InfoFileRecord infoFileAllegato, String uriFileAllegato, boolean aggiungiFirma) {

		Record record = new Record();
		record.setAttribute("infoFileAttach", infoFileAllegato);
		record.setAttribute("uriAttach", uriFileAllegato);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.extraparam.put("aggiungiFirma", Boolean.toString(aggiungiFirma));
		lGwtRestDataSource.executecustom("stampaFileCertificazione", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record data = response.getData()[0];
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
				String filename = infoFile.getCorrectFileName();
				String uri = data.getAttribute("tempFileOut");
				PreviewControl.switchPreview(uri, false, infoFile, "FileToExtractBean", filename);
			}
		});
	}

	public String getFlgTipoProv() {
		return null; // per disabilitare la timbratura sui file
	}

	public boolean canEditByApplet() {
		return false;
	}
	
	//Copertina con etichetta
	public void clickCopertinaConEtichetta() {
			
		Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
		
		final Record recordToPrint = new Record();
		recordToPrint.setAttribute("idUd", idUd);
		recordToPrint.setAttribute("numeroAllegato", "");
		recordToPrint.setAttribute("nomeStampantePred", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
		
		/**
		 * Se non è presente la stampante per le etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
			CopertinaEtichettaUtil.stampaEtichettaDatiSegnatura(recordToPrint);
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
					
				@Override
				public void execute(String nomeStampante) {
				
					CopertinaEtichettaUtil.stampaEtichettaDatiSegnatura(recordToPrint);
				}
			}, null);
		}
	}
		
	//Copertina con etichetta
	public void clickCopertinaConEtichettaMultipla() {
				
		Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord != null ? detailRecord.getAttribute("idUd") : null;
			
			
		final Record record = new Record();
		record.setAttribute("idUd", idUd);
		record.setAttribute("isMultiple", "true");
		/**
		 * Se non è presente la stampante per le etichette predefinita, allora propone la scelta
		 */
		if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null
				&& !"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
				
			record.setAttribute("nomeStampante", AurigaLayout.getImpostazioneStampa("stampanteEtichette"));
				
			StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(record);
			stampaEtichettaPopup.show();
		}else{
			PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {
						
				@Override
				public void execute(String nomeStampante) {
						
					record.setAttribute("nomeStampante", nomeStampante);
						
					StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(record);
					stampaEtichettaPopup.show();
				}
			}, null);
		}
	}

}
