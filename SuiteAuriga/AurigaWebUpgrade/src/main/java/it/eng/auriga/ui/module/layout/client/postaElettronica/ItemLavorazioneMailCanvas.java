/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.ScanUtility;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewImageWindow;
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
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class ItemLavorazioneMailCanvas extends ReplicableCanvas {

	protected int index;
	protected ReplicableCanvasForm tipoItemForm;
	protected ReplicableCanvasForm annotazioniLibereForm;
	protected ReplicableCanvasForm filePrimarioForm;
	protected ReplicableCanvasForm traceCanvasForm;

	protected RadioGroupItem tipoItemLavorazioneItem;
	protected TextItem numeroProgressivoTextItem;
	protected TextItem caricatoDaTextItem;
	protected DateTimeItem dataOraCaricamentoItem;
	protected TextItem modificatoDaTextItem;
	protected DateTimeItem dataOraModificaItem;
	protected SelectItem tagNotaLiberaSelectItem;
	protected TextAreaItem notaLiberaTextArea;
	protected HiddenItem itemLavNrItem;
	protected FileUploadItemWithFirmaAndMimeType fileUploadItem;

	protected HiddenItem idDocPrimarioHiddenItem;
	protected ExtendedTextItem nomeFilePrimarioItem;
	protected HiddenItem nomeFilePrimarioOrigItem;
	protected HiddenItem itemLavNomeFileTifItem;
	protected HiddenItem uriFilePrimarioTifItem;
	protected HiddenItem uriFilePrimarioItem;
	protected HiddenItem remoteUriFilePrimarioItem;
	protected FileUploadItemWithFirmaAndMimeType uploadFilePrimarioItem;
	protected HiddenItem uriFileVerPreFirmaItem;
	protected HiddenItem nomeFileVerPreFirmaItem;
	protected HiddenItem improntaVerPreFirmaItem;
	protected HiddenItem infoFileItem;
	protected HiddenItem itemLavFileSaveModeItem;
	protected HiddenItem uriFileSavedItem; // Salvo la uri del file che arriva dal server

	protected ImgButtonItem previewButton;
	protected ImgButtonItem previewEditButton;
	protected ImgButtonItem stampaFileItem;
	protected ImgButtonItem fileFirmatoDigButton;
	protected ImgButtonItem firmaNonValidaButton;
	protected ImgButtonItem fileMarcatoTempButton;
	protected ImgButtonItem trasformaInAllegatoButton;
	protected ImgButtonItem altreOpButton;
	protected NestedFormItem filePrimarioButtons;

	protected CheckboxItem flgNonModCancLockedItem;
	protected ImgItem motivoLockedIconItem;
	protected HiddenItem motivoNonModCancItem;
	protected HiddenItem flgNonModCancItem;

	protected HiddenItem flgCommentoObbligatorioTagItem;

	// Indica se il record può essere editabile
	private boolean canEdit;

	public ItemLavorazioneMailCanvas(ItemLavorazioneMailItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		preparaTipoItemForm();

		preparaItemAnnotazioniLibereForm();

		creaFormFileAllegato();

		preparaItemTraceForm();

		// LAYOUT MAIN
		VLayout mainLayout = new VLayout();
		mainLayout.setWidth100();
		
		// Layout generale, con tipo item e flag di lock
		VLayout tipoItemLayout = new VLayout();
		tipoItemLayout.setWidth100();
		
		// Layout con il file o la nota
		VLayout datiItemLayout = new VLayout();
		datiItemLayout.setWidth100();
		
		// Layout con le informazioni di trace
		VLayout treceFormLayout = new VLayout();
		treceFormLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(20);

		tipoItemLayout.addMember(tipoItemForm);

		datiItemLayout.addChild(annotazioniLibereForm);
		datiItemLayout.addChild(filePrimarioForm);
		treceFormLayout.addMember(traceCanvasForm);

		mainLayout.addMember(tipoItemLayout);
		mainLayout.addMember(datiItemLayout);
		mainLayout.addMember(treceFormLayout);
		mainLayout.addMember(lVLayoutSpacer);

		addChild(mainLayout);

	}

	private void preparaTipoItemForm() {
		itemLavNrItem = new HiddenItem("itemLavNrItem");
		// Record non modificabile e/o cancellabile
		flgNonModCancItem = new HiddenItem("flgNonModCanc");
		motivoNonModCancItem = new HiddenItem("motivoNonModCanc");

		tipoItemForm = new ReplicableCanvasForm();
		tipoItemForm.setWrapItemTitles(false);
		tipoItemForm.setNumCols(10);
		tipoItemForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		LinkedHashMap<String, String> statoInvioMap = new LinkedHashMap<String, String>();
		statoInvioMap.put(TipoItemLavorazione.FILE_ALLEGATO.getValue(), "File");
		statoInvioMap.put(TipoItemLavorazione.NOTA_LIBERA.getValue(), "Annotazioni libere e/o tag");

		numeroProgressivoTextItem = new TextItem("itemLavNumProgressivo", setTitleAlign(I18NUtil.getMessages().item_inlavorazione_itemLavNumProgressivo()));
		numeroProgressivoTextItem.setWidth(30);
		numeroProgressivoTextItem.setShowTitle(true);
		numeroProgressivoTextItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				tipoItemForm.setValue("itemLavNumProgressivo", "" + getCounter());
				return getItem().getTotalMembers() > 1;
			}
		});

		tipoItemLavorazioneItem = new RadioGroupItem("itemLavTipo");
		tipoItemLavorazioneItem.setDefaultValue(TipoItemLavorazione.NOTA_LIBERA.getValue());
		tipoItemLavorazioneItem.setTitle("Tipologia");
		tipoItemLavorazioneItem.setValueMap(statoInvioMap);
		tipoItemLavorazioneItem.setVertical(false);
		tipoItemLavorazioneItem.setWrap(false);
		tipoItemLavorazioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		SpacerItem spazio = new SpacerItem();
		spazio.setWidth(20);

		SpacerItem spazioPerAllineamento = new SpacerItem();
		spazioPerAllineamento.setWidth(1);
		spazioPerAllineamento.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgNonModCancItem != null && flgNonModCancItem.getValue() != null && flgNonModCancItem.getValue().equals(1);
			}
		});

		motivoLockedIconItem = new ImgItem("motivoLockedIcon", "lock.png", "");
		motivoLockedIconItem.setShowValueIconOnly(true);
		motivoLockedIconItem.setShowTitle(true);
		motivoLockedIconItem.setTitle("c");
		motivoLockedIconItem.setWidth(30);
		motivoLockedIconItem.setHeight(16);
		motivoLockedIconItem.setValueIconSize(16);
		motivoLockedIconItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		motivoLockedIconItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgNonModCancItem != null && flgNonModCancItem.getValue() != null && flgNonModCancItem.getValue().equals(1);
			}
		});
		motivoLockedIconItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				String result = "Inibita modifica: ";
				String motivo = motivoNonModCancItem != null && motivoNonModCancItem.getValue() != null && !"".equals((String) motivoNonModCancItem.getValue())
						? (String) motivoNonModCancItem.getValue() : "";
				motivoLockedIconItem.setPrompt(result.concat(motivo));
				return result.concat(motivo);
			}
		});

		flgNonModCancLockedItem = new CheckboxItem("flgNonModCancLocked", "Inibita modifica/cancellazione");
		flgNonModCancLockedItem.setDefaultValue(false);
		flgNonModCancLockedItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		flgNonModCancLockedItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isVisible = false;
				if (flgNonModCancItem == null || flgNonModCancItem.getValue() == null || !flgNonModCancItem.getValue().equals(1)) {
					isVisible = true;
				}

				return isVisible;
			}
		});

		tipoItemForm.setFields(itemLavNrItem, flgNonModCancItem, motivoNonModCancItem, numeroProgressivoTextItem, spazio, tipoItemLavorazioneItem, spazio,
				spazioPerAllineamento, motivoLockedIconItem, flgNonModCancLockedItem);
	}

	private void preparaItemAnnotazioniLibereForm() {
		annotazioniLibereForm = new ReplicableCanvasForm();
		annotazioniLibereForm.setWrapItemTitles(false);
		annotazioniLibereForm.setWidth100();
		annotazioniLibereForm.setNumCols(10);
		annotazioniLibereForm.setColWidths("60", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		GWTRestDataSource tagDS = new GWTRestDataSource("LoadComboTagBozzeMailDataSource", "key", FieldType.TEXT);
		tagDS.addParam("ignoraVociPerFiltro", "true");
//		tagDS.addParam("flgSoloVldIn", "true");

		flgCommentoObbligatorioTagItem = new HiddenItem("flgCommentoObbligatorioTag");

		tagNotaLiberaSelectItem = new SelectItem("itemLavTag", setTitleAlign(I18NUtil.getMessages().item_inlavorazione_itemLavTag())) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				annotazioniLibereForm.setValue("flgCommentoObbligatorioTag", record.getAttribute("flgNoteObbl"));
				annotazioniLibereForm.markForRedraw();
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || value.equals("")) {
					annotazioniLibereForm.setValue("flgCommentoObbligatorioTag", "");
				}
				annotazioniLibereForm.markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				annotazioniLibereForm.setValue("flgCommentoObbligatorioTag", "");
				annotazioniLibereForm.markForRedraw();
			};
		};
		tagNotaLiberaSelectItem.setStartRow(true);
		tagNotaLiberaSelectItem.setOptionDataSource(tagDS);
		tagNotaLiberaSelectItem.setDisplayField("value");
		tagNotaLiberaSelectItem.setValueField("key");
		tagNotaLiberaSelectItem.setWidth(350);
		tagNotaLiberaSelectItem.setWrapTitle(false);
		tagNotaLiberaSelectItem.setStartRow(true);
		tagNotaLiberaSelectItem.setAllowEmptyValue(true);
		tagNotaLiberaSelectItem.setAutoFetchData(false);
		ListGridField keyField = new ListGridField("key", "Nome");
		keyField.setHidden(true);
		ListGridField valueField = new ListGridField("value");
		ListGridField flgNoteObblField = new ListGridField("flgNoteObbl");
		flgNoteObblField.setHidden(true);
		tagNotaLiberaSelectItem.setPickListFields(keyField, valueField, flgNoteObblField);
		tagNotaLiberaSelectItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return !isSceltoTipofile();
			}
		});
		tagNotaLiberaSelectItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// notaLiberaTextArea.validate();
			}
		});

		notaLiberaTextArea = new TextAreaItem("itemLavCommento", setTitleAlign(I18NUtil.getMessages().item_inlavorazione_itemLavCommento()));
		notaLiberaTextArea.setLength(4000);
		notaLiberaTextArea.setStartRow(true);
		notaLiberaTextArea.setHeight(40);
		notaLiberaTextArea.setColSpan(1);
		notaLiberaTextArea.setWidth(646);
		notaLiberaTextArea.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return !isSceltoTipofile();
			}
		});
		CustomValidator lCustomValidatorNota = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				boolean isValid = true;
				if (annotazioniLibereForm != null && annotazioniLibereForm.getValue("flgCommentoObbligatorioTag") != null
						&& (annotazioniLibereForm.getValue("flgCommentoObbligatorioTag").equals(1)
								|| annotazioniLibereForm.getValue("flgCommentoObbligatorioTag").equals("1"))) {
					if (annotazioniLibereForm.getValue("itemLavCommento") == null || annotazioniLibereForm.getValue("itemLavCommento").equals("")
							|| annotazioniLibereForm.getValue("itemLavCommento").equals(" ")) {
						isValid = false;
					}
				}

				return isValid;
			}
		};
		lCustomValidatorNota.setErrorMessage("Attenzione, occorre valorizzare il campo Note per il Tag scelto");
		notaLiberaTextArea.setValidators(lCustomValidatorNota);

		annotazioniLibereForm.setFields(flgCommentoObbligatorioTagItem, tagNotaLiberaSelectItem, notaLiberaTextArea);
	}

	private void preparaItemTraceForm() {
		traceCanvasForm = new ReplicableCanvasForm();
		traceCanvasForm.setWrapItemTitles(false);
		traceCanvasForm.setNumCols(10);
		traceCanvasForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		caricatoDaTextItem = new TextItem("itemLavCaricatoDa", setTitleAlign(I18NUtil.getMessages().item_inlavorazione_itemLavCaricatoDa()));
		caricatoDaTextItem.setStartRow(true);
		caricatoDaTextItem.setWidth(150);
		caricatoDaTextItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return contieneTesto(arg0);
			}
		});

		dataOraCaricamentoItem = new DateTimeItem("itemLavDataOraCaricamento", I18NUtil.getMessages().item_inlavorazione_itemLavDataOraCaricamento());
		dataOraCaricamentoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return contieneTesto(arg0);
			}
		});

		modificatoDaTextItem = new TextItem("itemLavModificatoDa", I18NUtil.getMessages().item_inlavorazione_itemLavModificatoDa());
		modificatoDaTextItem.setStartRow(false);
		modificatoDaTextItem.setWidth(150);
		modificatoDaTextItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return contieneTesto(arg0);
			}
		});

		dataOraModificaItem = new DateTimeItem("itemLavDataOraModifica", I18NUtil.getMessages().item_inlavorazione_itemLavDataOraModifica());
		dataOraModificaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return contieneTesto(arg0);
			}
		});

		traceCanvasForm.setFields(caricatoDaTextItem, dataOraCaricamentoItem, modificatoDaTextItem, dataOraModificaItem);
	}

	private void creaFormFileAllegato() {
		// Form file allegato
		filePrimarioForm = new ReplicableCanvasForm();
		filePrimarioForm.setWidth100();
		filePrimarioForm.setNumCols(14);
		filePrimarioForm.setColWidths(50, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		filePrimarioForm.setWrapItemTitles(false);

		uriFilePrimarioItem = new HiddenItem("itemLavUriFile");
		nomeFilePrimarioOrigItem = new HiddenItem("itemLavNomeFileOrig");
		itemLavNomeFileTifItem = new HiddenItem("itemLavNomeFileTif"); // NOME del Tif
		uriFilePrimarioTifItem = new HiddenItem("itemLavUriFileTif"); // URI del Tif
		remoteUriFilePrimarioItem = new HiddenItem("itemLavRemoteUriFile");
		itemLavFileSaveModeItem = new HiddenItem("itemLavRemoteUriFile"); // Indica se ho cambiato un file originale
		uriFileSavedItem = new HiddenItem("itemLavUriFileSaved");

		// File primario
		nomeFilePrimarioItem = new ExtendedTextItem("itemLavNomeFile", setTitleAlign(I18NUtil.getMessages().item_inlavorazione_itemLavNomeFile()));
		nomeFilePrimarioItem.setWidth(250);
		nomeFilePrimarioItem.setStartRow(true);
		nomeFilePrimarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (nomeFilePrimarioItem.getValue() == null || ((String) nomeFilePrimarioItem.getValue()).trim().equals("")) {
					clickEliminaFile();
				} else if (filePrimarioForm.getValueAsString("itemLavNomeFileOrig") != null
						&& !"".equals(filePrimarioForm.getValueAsString("itemLavNomeFileOrig"))
						&& !nomeFilePrimarioItem.getValue().equals(filePrimarioForm.getValueAsString("itemLavNomeFileOrig"))) {
					manageChangedDocPrimario();
				}
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
			}
		});

		nomeFilePrimarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isSceltoTipofile();
			}
		});

		uploadFilePrimarioItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				filePrimarioForm.setValue("itemLavNomeFile", displayFileName);
				filePrimarioForm.setValue("displayFileName", displayFileName);
				filePrimarioForm.setValue("itemLavUriFile", uri);
				filePrimarioForm.setValue("itemLavNomeFileTif", "");
				filePrimarioForm.setValue("itemLavUriFileTif", "");
				filePrimarioForm.setValue("itemLavRemoteUriFile", false);
				filePrimarioForm.setValue("itemLavNomeFileVerPreFirma", displayFileName);
				filePrimarioForm.setValue("itemLavUriFileVerPreFirma", uri);
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
				InfoFileRecord precInfo = filePrimarioForm.getValue("itemLavInfoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("itemLavInfoFile", info);
				filePrimarioForm.setValue("itemLavImprontaFile", info.getImpronta());
				String nomeFilePrimario = filePrimarioForm.getValueAsString("itemLavNomeFile");
				String nomeFilePrimarioOrig = filePrimarioForm.getValueAsString("itemLavNomeFileOrig");
				if (precImpronta == null || !precImpronta.equals(info.getImpronta()) || (nomeFilePrimario != null && !"".equals(nomeFilePrimario)
						&& nomeFilePrimarioOrig != null && !"".equals(nomeFilePrimarioOrig) && !nomeFilePrimario.equals(nomeFilePrimarioOrig))) {
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
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
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

		previewEditButton = new ImgButtonItem("previewEditButton", "file/previewEdit.png",
				I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt());
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
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
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

		stampaFileItem = new ImgButtonItem("flgStampaFile", "postaElettronica/print_file.png", "");
		stampaFileItem.setShowTitle(false);
		stampaFileItem.setAlwaysEnabled(true);
		stampaFileItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Stampa";
			}
		});
		stampaFileItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				Boolean isAbilShowPrint = ((ItemLavorazioneMailItem) getItem()).showStampaFileButton();
				boolean toShowPreview = false;
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")) {
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
					toShowPreview = !PreviewWindow.isToShowEml(lInfoFileRecord, filePrimarioForm.getValueAsString("itemLavNomeFile"))
							&& PreviewWindow.canBePreviewed(lInfoFileRecord);
				}
				return isAbilShowPrint && toShowPreview;
			}
		});
		stampaFileItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				clickStampaFile();
			}
		});

		fileFirmatoDigButton = new ImgButtonItem("fileFirmatoDigButton", "firma/firma.png",
				I18NUtil.getMessages().protocollazione_detail_fileFirmatoDigButton_prompt());
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
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
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

		firmaNonValidaButton = new ImgButtonItem("firmaNonValidaButton", "firma/firmaNonValida.png",
				I18NUtil.getMessages().protocollazione_detail_firmaNonValidaButton_prompt());
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
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
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
					InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
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

		trasformaInAllegatoButton = new ImgButtonItem("trasformaInAllegatoButton", "buttons/exchange.png", "Trasforma in allegato");
		trasformaInAllegatoButton.setAlwaysEnabled(true);
		trasformaInAllegatoButton.setColSpan(1);
		trasformaInAllegatoButton.setIconWidth(16);
		trasformaInAllegatoButton.setIconHeight(16);
		trasformaInAllegatoButton.setIconVAlign(VerticalAlignment.BOTTOM);
		trasformaInAllegatoButton.setAlign(Alignment.LEFT);
		trasformaInAllegatoButton.setWidth(16);
		trasformaInAllegatoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("") && isBozza()) {
					return true;
				} else
					return false;
			}
		});
		trasformaInAllegatoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickTrasformaInAllegato();
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

					/**
					 * @return true se il componente dev'essere abilitato, false se non deve essere abilitato
					 */
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						/*
						 * Si utilizza canEdit per verificare se il componente dev'essere abilitato o meno
						 */
						return canEdit;
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

					/**
					 * @return true se il componente dev'essere abilitato, false se non deve essere abilitato
					 */
					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {

						/*
						 * Si utilizza canEdit per verificare se il componente dev'essere abilitato o meno
						 */
						return (canEdit && (uriFilePrimarioItem.getValue() != null && !uriFilePrimarioItem.getValue().equals("")));
					}
				});
				firmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						clickFirma();
					}
				});

				MenuItem downloadMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(), "file/download_manager.png");
				InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
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

				// MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem_prompt(), "file/timbra.gif");

				MenuItem eliminaMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt(), "file/editdelete.png");
				eliminaMenuItem.setEnableIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						if ((canEdit) && (uriFilePrimarioItem.getValue() != null)) {
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
				altreOpMenu.addItem(acquisisciDaScannerMenuItem);
				altreOpMenu.addItem(firmaMenuItem);
				altreOpMenu.addItem(downloadMenuItem);
				altreOpMenu.addItem(eliminaMenuItem);

				altreOpMenu.showContextMenu();
			}
		});

		filePrimarioButtons.setNestedFormItems(uploadFilePrimarioItem, previewButton, previewEditButton, stampaFileItem, fileFirmatoDigButton,
				firmaNonValidaButton, fileMarcatoTempButton, trasformaInAllegatoButton, altreOpButton);

		filePrimarioButtons.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return isSceltoTipofile();
			}
		});

		infoFileItem = new HiddenItem("itemLavInfoFile");
		infoFileItem.setVisible(false);

		uriFileVerPreFirmaItem = new HiddenItem("itemLavUriFileVerPreFirma");
		nomeFileVerPreFirmaItem = new HiddenItem("itemLavNomeFileVerPreFirma");
		improntaVerPreFirmaItem = new HiddenItem("itemLavImprontaFile");

		filePrimarioForm.setFields(nomeFilePrimarioOrigItem, nomeFilePrimarioItem, uriFilePrimarioItem, filePrimarioButtons, infoFileItem,
				itemLavNomeFileTifItem, uriFilePrimarioTifItem, remoteUriFilePrimarioItem, uriFileVerPreFirmaItem, nomeFileVerPreFirmaItem, improntaVerPreFirmaItem, uriFileSavedItem);

	}

	// Preview del file
	public void clickPreviewFile() {
		String uri = filePrimarioForm.getValueAsString("itemLavUriFile");
		if (uri.equals("_noUri")) {
			((ItemLavorazioneMailItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((ItemLavorazioneMailItem) getItem()).getName()).get(i);
					realClickPreviewFile(lRecord.getAttribute("itemLavUriFile"));
				}
			});
		} else {
			realClickPreviewFile(filePrimarioForm.getValueAsString("itemLavUriFile"));
		}
	}

	protected void realClickPreviewFile(final String uri) {
		final String display = filePrimarioForm.getValueAsString("displayFileName");
		final Boolean remoteUri = true;
		final InfoFileRecord info = filePrimarioForm.getValue("itemLavInfoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile")) : null;

		if (info != null) {
			if (info.getMimetype() != null && info.getMimetype().equals("application/xml") && !"Segnatura.xml".equals(display)
					&& !"segnatura.xml".equals(display) && !"Conferma.xml".equals(display) && !"conferma.xml".equals(display)
					&& !"eccezione.xml".equals(display) && !"Eccezione.xml".equals(display) && !"Aggiornamento.xml".equals(display)
					&& !"aggiornamento.xml".equals(display) && !"annullamento.xml".equals(display) && !"Annullamento.xml".equals(display)) {
				Record lRecordFattura = new Record();
				lRecordFattura.setAttribute("uri", uri);
				lRecordFattura.setAttribute("uriFile", uri);
				lRecordFattura.setAttribute("remoteUri", true);
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
							preview(uri, display, remoteUri, info);
						}
					}
				});
			} else {
				preview(uri, display, remoteUri, info);
			}
		} else {
			SC.say("Non è possibile accedere alla preview del documento allegato");
		}
	}

	public void preview(final String uri, String display, Boolean remoteUri, InfoFileRecord info) {
		String view = ((ItemLavorazioneMailItem) getItem()).getAttribute("closeViewer");

		if (info != null && info.getMimetype() != null && info.getMimetype().startsWith("image") && !info.getMimetype().equals("image/tiff")) {
			new PreviewImageWindow(uri, Boolean.valueOf(remoteUri), info);
		} else {
			if (!Layout.getOpenedViewers().keySet().contains(uri)) {
				PreviewWindow lPreviewWindow = new PreviewWindow(view, "", uri, remoteUri, info, "FileToExtractBean", display) {

					@Override
					public boolean isModal() {
						return false;
					}
				};
				lPreviewWindow.addCloseClickHandler(new CloseClickHandler() {

					@Override
					public void onCloseClick(CloseClickEvent event) {
						Layout.getOpenedViewers().remove(uri);
					}
				});
				Layout.getOpenedViewers().put(uri, lPreviewWindow);
			} else {
				Layout.getOpenedViewers().get(uri).bringToFront();
			}
		}
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

	private boolean isSceltoTipofile() {
		if ((tipoItemLavorazioneItem != null) && (tipoItemLavorazioneItem.getValueAsString() != null)) {
			return TipoItemLavorazione.FILE_ALLEGATO.getValue().equalsIgnoreCase(tipoItemLavorazioneItem.getValueAsString());
		}
		return false;
	}

	public void clickAcquisisciDaScanner() {
		ScanUtility.openScan(new ScanCallback() {

			@Override
			public void execute(final String filePdf, final String uriPdf, String fileTif, String uriTif, String record) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("itemLavInfoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
				filePrimarioForm.setValue("itemLavInfoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedDocPrimario();
				}
				filePrimarioForm.setValue("itemLavNomeFile", filePdf);
				filePrimarioForm.setValue("displayFileName", filePdf);
				filePrimarioForm.setValue("itemLavUriFile", uriPdf);
				filePrimarioForm.setValue("itemLavNomeFileTif", fileTif);
				filePrimarioForm.setValue("itemLavUriFileTif", uriTif);
				filePrimarioForm.setValue("itemLavRemoteUriFile", false);
				filePrimarioForm.setValue("itemLavNomeFileVerPreFirma", filePdf);
				filePrimarioForm.setValue("itemLavUriFileVerPreFirma", uriPdf);
				filePrimarioForm.setValue("itemLavImprontaFile", info.getImpronta());
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
				changedEventAfterUpload(filePdf, uriPdf);
			}
		});
	}

	public void clickFirma() {
		String display = filePrimarioForm.getValueAsString("itemLavNomeFile");
		String uri = filePrimarioForm.getValueAsString("itemLavUriFile");
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
		FirmaUtility.firmaMultipla(uri, display, lInfoFileRecord, new FirmaCallback() {

			@Override
			public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
				InfoFileRecord precInfo = filePrimarioForm.getValue("itemLavInfoFile") != null
						? new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile")) : null;
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				filePrimarioForm.setValue("itemLavInfoFile", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					manageChangedDocPrimario();
				}
				filePrimarioForm.setValue("itemLavNomeFile", nomeFileFirmato);
				filePrimarioForm.setValue("itemLavUriFile", uriFileFirmato);
				filePrimarioForm.setValue("itemLavNomeFileTif", "");
				filePrimarioForm.setValue("itemLavUriFileTif", "");
				filePrimarioForm.setValue("itemLavRemoteUriFile", false);
				filePrimarioForm.markForRedraw();
				filePrimarioButtons.markForRedraw();
				changedEventAfterUpload(nomeFileFirmato, uriFileFirmato);
			}
		});
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

	public void manageChangedDocPrimario() {
		filePrimarioForm.setValue("isDocPrimarioChanged", true);
	}

	// Cancellazione del file
	public void clickEliminaFile() {
		filePrimarioForm.setValue("itemLavNomeFile", "");
		filePrimarioForm.setValue("itemLavUriFile", "");
		filePrimarioForm.setValue("itemLavNomeFileTif", "");
		filePrimarioForm.setValue("itemLavUriFileTif", "");
		filePrimarioForm.markForRedraw();
		filePrimarioButtons.markForRedraw();
		uploadFilePrimarioItem.getCanvas().redraw();
		filePrimarioForm.setValue("itemLavRemoteUriFile", false);
		filePrimarioForm.setValue("itemLavNomeFileVerPreFirma", "");
		filePrimarioForm.setValue("itemLavUriFileVerPreFirma", "");
		filePrimarioForm.setValue("itemLavImprontaFile", "");
		infoFileItem.clearValue();
	}

	// Download del file
	public void clickDownloadFile() {
		String display = filePrimarioForm.getValueAsString("itemLavNomeFile");
		String uri = filePrimarioForm.getValueAsString("itemLavUriFile");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", filePrimarioForm.getValueAsString("itemLavRemoteUriFile"));
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// Download del file firmato sbustato
	public void clickDownloadFileSbustato() {
		String display = filePrimarioForm.getValueAsString("itemLavNomeFile");
		String uri = filePrimarioForm.getValueAsString("itemLavUriFile");
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", filePrimarioForm.getValueAsString("itemLavRemoteUriFile"));
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	protected void manageFileFirmatoButtonClick() {

		final InfoFileRecord infoFileRecord = InfoFileRecord.buildInfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
		final String uriFilePrimario = filePrimarioForm.getValueAsString("itemLavUriFile");

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

	public Record getFormValuesAsRecord() {
		return tipoItemForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { tipoItemForm, annotazioniLibereForm, filePrimarioForm, traceCanvasForm };
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		// Salvo l'uri del file originale		
		filePrimarioForm.setValue("itemLavUriFileSaved", filePrimarioForm.getValueAsString("itemLavUriFile"));
		// Popolo la select dei tag
		if ( (record.getAttribute("itemLavTag") != null && !"".equalsIgnoreCase(record.getAttribute("itemLavTag"))) &&
			 (record.getAttribute("itemLavDesTag") != null && !"".equalsIgnoreCase(record.getAttribute("itemLavDesTag"))) ){
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("itemLavTag"), record.getAttribute("itemLavDesTag"));
			tagNotaLiberaSelectItem.setValueMap(valueMap);
		}
	}

	public void setFormValuesFromRecordImportaFilePrimario(Record record, boolean collegaDocumentiFileImportati) {
		clearValue();
		String displayFileName = record.getAttribute("itemLavNomeFile");
		String uri = record.getAttribute("itemLavUriFile");
		String descrizione = record.getAttribute("segnatura") != null ? record.getAttribute("segnatura") : "";
		filePrimarioForm.setValue("nomeFileAllegato", displayFileName);
		filePrimarioForm.setValue("uriFileAllegato", uri);
		filePrimarioForm.setValue("displayNameTif", "");
		filePrimarioForm.setValue("uriTif", "");
		filePrimarioForm.setValue("remoteUri", false);
		filePrimarioForm.setValue("fileImportato", true);
		filePrimarioForm.setValue("collegaDocumentoImportato", collegaDocumentiFileImportati);
		filePrimarioForm.setValue("descrizioneFileAllegato", descrizione);
		filePrimarioForm.setValue("isChanged", true);
		InfoFileRecord ifr = new InfoFileRecord(record.getAttributeAsRecord("itemLavInfoFile"));
		filePrimarioForm.setValue("itemLavInfoFile", ifr);
		changedEventAfterUpload(displayFileName, uri);
	}

	protected void clearValue() {
		Record record = filePrimarioForm.getValuesAsRecord();
		record.setAttribute("flgParteDispositivo", false);
		record.setAttribute("flgNoPubblAllegato", true);
		record.setAttribute("flgPubblicaSeparato", false);
		record.setAttribute("flgDatiSensibili", false);
		record.setAttribute("isChanged", false);
		record.setAttribute("flgOriginaleCartaceo", false);
		record.setAttribute("flgCopiaSostitutiva", false);
		filePrimarioForm.setValues(record.toMap());
	}

//	public void updateNumeroProgressivo() {
//		numeroProgressivoTextItem.setValue(getCounter());
//	}

	private boolean contieneTesto(FormItem formItem) {
		String value = formItem.getValueField();
		return ((value != null) && (value.length() > 0)) || true;
	}

	private String setTitleAlign(String title) {
		return "<span style=\"width: 120px; display: inline-block;\">" + title + "</span>";
	}

	public void clickTrasformaInAllegato() {
		Record lRecordAttuale = new Record();
		lRecordAttuale.setAttribute("uriAttach", filePrimarioForm.getValueAsString("itemLavUriFile"));
		lRecordAttuale.setAttribute("fileNameAttach", filePrimarioForm.getValueAsString("itemLavNomeFile"));
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
		lRecordAttuale.setAttribute("infoFile", lInfoFileRecord);
		((ItemLavorazioneMailItem) getItem()).trasformaInAllegatoEmail(lRecordAttuale);
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		this.canEdit = canEdit;
		// Devo verificare se posso editare l'item in lavorazione
		if (flgNonModCancItem != null && flgNonModCancItem.getValue() != null && flgNonModCancItem.getValue().equals(1)) {
			super.setCanEdit(false);
			this.canEdit = false;
		}
		dataOraCaricamentoItem.setCanEdit(false);
		dataOraModificaItem.setCanEdit(false);
		caricatoDaTextItem.setCanEdit(false);
		modificatoDaTextItem.setCanEdit(false);
		numeroProgressivoTextItem.setCanEdit(false);
		numeroProgressivoTextItem.setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
		// canEdit indica se i vari componenti devono essere abilitati o meno

	}

	@Override
	public boolean validate() {
		boolean valid = true;
		annotazioniLibereForm.setShowInlineErrors(true);
		filePrimarioForm.setShowInlineErrors(true);
		Map<String, String> lMap = new HashMap<String, String>();

		InfoFileRecord infoFileAllegato = filePrimarioForm.getValue("infoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("infoFile")) : null;
		// Se la nota è di tipo file devo valorizzare il file
		if ((tipoItemForm.getValueAsString("itemLavTipo") == null)
				|| (tipoItemForm.getValueAsString("itemLavTipo").equalsIgnoreCase(TipoItemLavorazione.FILE_ALLEGATO.getValue()))) {
			// Controllo la validazione del file
			if (filePrimarioForm.getValue("uriFileAllegato") != null && !"".equals(filePrimarioForm.getValue("uriFileAllegato"))) {
				if (infoFileAllegato == null || infoFileAllegato.getMimetype() == null || infoFileAllegato.getMimetype().equals("")) {
					HashMap<String, String> errors = new HashMap<String, String>();
					errors.put("itemLavNomeFile", "Formato file non riconosciuto");
					filePrimarioForm.setErrors(errors);
					valid = false;
				}
				if (infoFileAllegato.getBytes() <= 0) {
					HashMap<String, String> errors = new HashMap<String, String>();
					errors.put("itemLavNomeFile", "Il file ha dimensione 0");
					filePrimarioForm.setErrors(errors);
					valid = false;
				}
			}
			if ((filePrimarioForm.getValue("itemLavNomeFile") != null) && (!"".equalsIgnoreCase(filePrimarioForm.getValueAsString("itemLavNomeFile")))
					&& ((filePrimarioForm.getValue("itemLavUriFile")) == null || ("".equalsIgnoreCase(filePrimarioForm.getValueAsString("itemLavUriFile"))))) {
				HashMap<String, String> errors = new HashMap<String, String>();
				errors.put("itemLavNomeFile", "File non caricato");
				filePrimarioForm.setErrors(errors);
				valid = false;
			}
		}
		return valid;
	}

	private boolean isBozza() {
		boolean isBozza = false;

		if (((ItemLavorazioneMailItem) getItem()).getDetailRecord() != null) {
			Record record = ((ItemLavorazioneMailItem) getItem()).getDetailRecord();
			if (record.getAttributeAsRecord("invioMailBean") != null
					&& (record.getAttributeAsString("id") != null && record.getAttributeAsString("id").endsWith(".B"))) {
				isBozza = true;
			}
		}
		return isBozza;
	}

	// Stampa del file
	public void clickStampaFile() {
		String uri = filePrimarioForm.getValueAsString("itemLavUriFile");
		if (uri.equals("_noUri")) {
			((ItemLavorazioneMailItem) getItem()).downloadFile(new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					int i = getCounter() - 1;
					Record lRecord = object.getAttributeAsRecordList(((ItemLavorazioneMailItem) getItem()).getName()).get(i);
					manageStampaFile(lRecord.getAttribute("itemLavUriFile"));
				}
			});
		} else {
			manageStampaFile(filePrimarioForm.getValueAsString("itemLavUriFile"));
		}
	}

	protected void manageStampaFile(final String uri) {
		final String display = filePrimarioForm.getValueAsString("displayFileName");
		final Boolean remoteUri = true;
		final InfoFileRecord info = filePrimarioForm.getValue("itemLavInfoFile") != null ? new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile")) : null;
		if (info != null) {
			if (info.getMimetype() != null && info.getMimetype().equals("application/xml") && !"Segnatura.xml".equals(display)
					&& !"segnatura.xml".equals(display) && !"Conferma.xml".equals(display) && !"conferma.xml".equals(display)
					&& !"eccezione.xml".equals(display) && !"Eccezione.xml".equals(display) && !"Aggiornamento.xml".equals(display)
					&& !"aggiornamento.xml".equals(display) && !"annullamento.xml".equals(display) && !"Annullamento.xml".equals(display)) {
				Record lRecordFattura = new Record();
				lRecordFattura.setAttribute("uri", uri);
				lRecordFattura.setAttribute("uriFile", uri);
				lRecordFattura.setAttribute("remoteUri", true);
				GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
				if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
					lGwtRestService.addParam("sbustato", "true");
				} else {
					lGwtRestService.addParam("sbustato", "false");
				}
				lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {

						stampaFile(uri, display, remoteUri, info);

					}
				});
			} else {
				stampaFile(uri, display, remoteUri, info);
			}
		} else {
			SC.say(I18NUtil.getMessages().posta_elettronica_replicable_canvas_errore_stampa_file());
		}
	}

	private void stampaFile(String uri, String nomeFile, Boolean remoteUri, InfoFileRecord info) {

		Record lRecord = new Record();
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("nomeFile", nomeFile);
		lRecord.setAttribute("infoFile", info);
		lRecord.setAttribute("remoteUri", remoteUri);

		RecordList listaAllegati = new RecordList();
		listaAllegati.add(lRecord);

		Record recordToPrint = new Record();
		recordToPrint.setAttribute("listaAllegati", listaAllegati);

		StampaFileUtility.stampaFile(recordToPrint, null);
	}
	
	public void clickPreviewEditFile() {
		final Record detailRecord = new Record(filePrimarioForm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		String idDocPrimario = filePrimarioForm.getValueAsString("itemLavIdDocPrimario");
		addToRecent(idUd, idDocPrimario);
		final String display = filePrimarioForm.getValueAsString("itemLavNomeFile");
		final String uri = filePrimarioForm.getValueAsString("itemLavUriFile");
		final Boolean remoteUri = Boolean.valueOf(filePrimarioForm.getValueAsString("itemLavRemoteUriFile"));
		final InfoFileRecord info = new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile"));
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
	
	public void previewWithJPedal(final Record detailRecord, String idUd, final String display, final String uri, final Boolean remoteUri,
			InfoFileRecord info) {
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd
				,rotazioneTimbroPref,posizioneTimbroPref);
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
						InfoFileRecord precInfo = filePrimarioForm.getValue("itemLavInfoFile") != null
								? new InfoFileRecord(filePrimarioForm.getValue("itemLavInfoFile")) : null;
						String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
						InfoFileRecord info = InfoFileRecord.buildInfoFileString(record);
						filePrimarioForm.setValue("itemLavInfoFile", info);
						if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
							manageChangedDocPrimario();
						}
						filePrimarioForm.setValue("itemLavNomeFile", filePdf);
						filePrimarioForm.setValue("itemLavUriFile", uriPdf);
						filePrimarioForm.setValue("itemLavNomeFileTif", "");
						filePrimarioForm.setValue("itemLavUriFileTif", "");
						filePrimarioForm.setValue("itemLavRemoteUriFile", false);
						filePrimarioForm.setValue("itemLavNomeFileVerPreFirma", filePdf);
						filePrimarioForm.setValue("itemLavUriFileVerPreFirma", uriPdf);
						filePrimarioForm.setValue("itemLavImprontaFile", info.getImpronta());
						filePrimarioForm.markForRedraw();
						filePrimarioButtons.markForRedraw();
						changedEventAfterUpload(filePdf, uriPdf);
					}
				};
				previewDocWindow.show();
			}
		});
	}

}
