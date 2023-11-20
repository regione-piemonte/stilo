/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.form.validator.Validator;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupRubricaEmailPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.EditorEmailWindow;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class InvioMailMultiXlsForm extends DynamicForm {

	InvioMailMultiXlsForm _this = this;

	// HiddenItem
	private HiddenItem lHiddenItemIdEmail;
	private HiddenItem lHiddenItemIdEmailUD;
	private HiddenItem lHiddenItemIdUD;
	private HiddenItem casellaIsPecItem;
	private HiddenItem uriXlsItem;
	private HiddenItem infoFileXlsItem;

	private StaticTextItem noteAvvertimentoItem;
	private SelectItem lSelectItemMittente;
	private ImgButtonItem salvaMittenteDefaultImgButton;
	private ImgButtonItem editCorpoMailImgButton;
	private ImgButtonItem eliminaFileXlsButton;
	private TextItem lTextItemOggetto;
	private TextItem nomeFileXlsItem;
	private AttachmentReplicableItem lAttachmentItem;
	private IndirizziEmailXlsItem dettagliXlsIndirizziEmailReplicableItem;
	private ReplicableCanvas attachmentReplicableCanvas;
	private ReplicableCanvas dettagliXlsIndirizziEmailReplicableCanvas;
	private FileUploadItemWithFirmaAndMimeType uploadFileXlsButton;
	private FileUploadItemWithFirmaAndMimeType uploadButton;
	private NumericItem rigaXlsDaItem;
	private NumericItem rigaXlsAItem;
	private RadioGroupItem style;
	private TextAreaItem lTextAreaItemBody;
	private RichTextItem lRichTextItemBody;
	private int COMPONENT_WIDTH = 800;
	protected TipologiaMail tipologiaMailGestioneModelli;
	private String bodyText = "";
	private LinkedHashMap<String, String> styleMap;

	public InvioMailMultiXlsForm() {
		this(null, null, null);
	}

	/**
	 * 
	 * @param tipologiaMail
	 *            variabile utilizzata da GestioneModelli per identificare se si
	 *            tratta di una risposta o un inoltro. Se è una risposta non vengono
	 *            visualizzate le text relative ai destinatari principali e CC.
	 *            Altrimenti vengono visualizzate.
	 */
	public InvioMailMultiXlsForm(TipologiaMail tipologiaMail, String tipoRel, ValuesManager vm) {

		this.tipologiaMailGestioneModelli = tipologiaMail;

		setWrapItemTitles(false);
		setNumCols(18);

		setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, "*");
		//setCellBorder(1);

		lHiddenItemIdEmail = new HiddenItem("idEmail");
		lHiddenItemIdEmailUD = new HiddenItem("idEmailUD");
		lHiddenItemIdUD = new HiddenItem("idUD");
		casellaIsPecItem = new HiddenItem("casellaIsPec");

		// MITTENTE
		createMittente(tipoRel);

		// UPLOAD XLS
		createUploadFileXls();

		// RANGE RIGHE XLS
		createRigheXls();

		// DETTAGLIO CONTENUTI EXCEL
		createDettaglioExcel();

		// Oggetto
		createOggetto();
		
		// Attachment
		createAttachment();

		SpacerItem spacerAttach = new SpacerItem();
		spacerAttach.setWidth(20);
		spacerAttach.setColSpan(1);
		spacerAttach.setStartRow(true);
		spacerAttach.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				/*
				 * Nel caso in cui si tratti del form di gestione modelli (quindi tipologiaMailGestioneModelli != null)
				 * NON deve visualizzare questo campo.
				 * Altrimenti dipende dalle altre condizioni
				 */
				if ((lAttachmentItem != null) && (lAttachmentItem.getTotalMembers() > 0) && tipologiaMailGestioneModelli == null) {
					return true;
				} else {
					return false;
				}
			}
		});

		//Upload file allegato
		createUploadFile();

		// Radio Opzioni Testo
		createRadioBody();

		// Casella testo TEXT
		createTextArea();

		// Casella testo HTML
		createRichTextArea();

		// Botton per editare il corpo email
		createEditCorpoEmailButton();

		// Nota avvertimento statico
		createAvvertimento();

		SpacerItem lSpacerItem = new SpacerItem();
		lSpacerItem.setColSpan(1);
		lSpacerItem.setStartRow(true);

		setFields( 
			// Nascosti
			lHiddenItemIdEmail, lHiddenItemIdEmailUD, lHiddenItemIdUD, uriXlsItem, infoFileXlsItem, casellaIsPecItem,
			// visibili
			lSelectItemMittente, salvaMittenteDefaultImgButton, nomeFileXlsItem, uploadFileXlsButton,eliminaFileXlsButton,
			rigaXlsDaItem, rigaXlsAItem, 
			dettagliXlsIndirizziEmailReplicableItem,
			lTextItemOggetto, 
			lAttachmentItem, spacerAttach, uploadButton, 
			style, 
			lSpacerItem, lRichTextItemBody, lTextAreaItemBody, editCorpoMailImgButton,
			lSpacerItem, noteAvvertimentoItem
		);

		if (vm != null) {
			setValuesManager(vm);
		} else {
			setValuesManager(new ValuesManager());
		}
	}

	private void createEditCorpoEmailButton() {

		editCorpoMailImgButton = new ImgButtonItem("editCorpo", "buttons/view_editor.png",
				I18NUtil.getMessages().posta_elettronica_editor_view_title_new_message());
		editCorpoMailImgButton.setShowTitle(false);
		editCorpoMailImgButton.setAlwaysEnabled(true);
		editCorpoMailImgButton.setWidth(16);
		editCorpoMailImgButton.setValueIconSize(32);
		editCorpoMailImgButton.setColSpan(1);
		editCorpoMailImgButton.setStartRow(false);
		editCorpoMailImgButton.setEndRow(true);
		editCorpoMailImgButton.setPrompt("Editor corpo email");
		editCorpoMailImgButton.setVAlign(VerticalAlignment.TOP);
		editCorpoMailImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageEditViewerBody();
			}
		});
	}

	private void createRichTextArea() {

		lRichTextItemBody = new RichTextItem("bodyHtml");
		lRichTextItemBody.setTitle("Corpo");
		lRichTextItemBody.setDefaultValue("");
		lRichTextItemBody.setVisible(true);
		lRichTextItemBody.setShowTitle(false);
		lRichTextItemBody.setCellStyle(it.eng.utility.Styles.textItem);
		lRichTextItemBody.setHeight(600);
		lRichTextItemBody.setWidth(COMPONENT_WIDTH);
		lRichTextItemBody.setColSpan(8);
		lRichTextItemBody.setAttribute("obbligatorio", true);
		lRichTextItemBody.setStartRow(false);
		lRichTextItemBody.setEndRow(false);
		lRichTextItemBody.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");
		lRichTextItemBody.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return lRichTextItemBody.isVisible();
			}
		}));
	}

	private void createTextArea() {

		lTextAreaItemBody = new TextAreaItem("bodyText") {

			@Override
			public boolean showViewer() {
				return false;
			}
		};
		lTextAreaItemBody.setTitle("Corpo");
		lTextAreaItemBody.setDefaultValue("");
		lTextAreaItemBody.setVisible(false);
		lTextAreaItemBody.setShowTitle(false);
		lTextAreaItemBody.setHeight(200);
		lTextAreaItemBody.setWidth(COMPONENT_WIDTH);
		lTextAreaItemBody.setColSpan(8);
		lTextAreaItemBody.setStartRow(false);
		lTextAreaItemBody.setEndRow(false);
		lTextAreaItemBody.setAttribute("obbligatorio", true);
		lTextAreaItemBody.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return lTextAreaItemBody.isVisible();
			}
		}));
	}
	
	private void createUploadFile() {
		
		uploadButton = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				attachmentReplicableCanvas = lAttachmentItem.onClickNewButton();
				attachmentReplicableCanvas.getForm()[0].setValue("fileNameAttach", displayFileName);
				attachmentReplicableCanvas.getForm()[0].setValue("uriAttach", uri);
				((AttachmentCanvas) attachmentReplicableCanvas).changedEventAfterUpload(displayFileName, uri);
				uploadButton.redrawPrettyFileUploadInput();
				_this.markForRedraw();
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
				attachmentReplicableCanvas.getForm()[0].setValue("infoFileAttach", info);
			}
		});
		uploadButton.setTitle(I18NUtil.getMessages().invionotificainteropform_allegatiItem_title());
		uploadButton.setStartRow(false);
		uploadButton.setEndRow(true);
		uploadButton.setColSpan(5);
		uploadButton.setShowTitle(true);
		uploadButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				if ((lAttachmentItem != null) && (lAttachmentItem.getTotalMembers() > 0)) {
					uploadButton.setShowTitle(false);
				} else {
					uploadButton.setShowTitle(true);
				}
				
				if(tipologiaMailGestioneModelli == null){
					/*
					 * Nel caso NON si tratti del form di gestione dei modelli allora deve essere visibile
					 * tale pulsante
					 */
					return true;
				}else{
					/*
					 * Nel caso si tratti del form di gestione dei modelli allora non deve essere visibile
					 * tale pulsante
					 */
					return false;
				}
			}
		});
	}

	private void createRadioBody() {

		styleMap = new LinkedHashMap<String, String>();
		styleMap.put("text", I18NUtil.getMessages().invionotificainteropform_styleMap_TEXT_value());
		styleMap.put("html", I18NUtil.getMessages().invionotificainteropform_styleMap_HTML_value());

		style = new RadioGroupItem("textHtml");
		style.setDefaultValue("html");
		style.setTitle(I18NUtil.getMessages().invionotificainteropform_styleItem_title());
		style.setValueMap(styleMap);
		style.setVertical(false);
		style.setWrap(false);
		style.setWidth(50);
		style.setStartRow(true);
		style.setEndRow(false);
		style.setColSpan(15);
		style.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				manageChangeTextHtml(event);
			}
		});
	}

	private void createDettaglioExcel() {

		dettagliXlsIndirizziEmailReplicableItem = new IndirizziEmailXlsItem() {
			
			@Override
			public boolean isCasellaPec() {
				return isMittentePec();
			}
		};
		dettagliXlsIndirizziEmailReplicableItem.setName("dettagliXlsIndirizziEmail");
		dettagliXlsIndirizziEmailReplicableItem.setTitle(I18NUtil.getMessages().dettagli_xls_indirizzi_email_item_title());
		dettagliXlsIndirizziEmailReplicableItem.setShowNewButton(true);
		dettagliXlsIndirizziEmailReplicableItem.setWidth(COMPONENT_WIDTH);
		dettagliXlsIndirizziEmailReplicableItem.setColSpan(15);
		dettagliXlsIndirizziEmailReplicableItem.setStartRow(true);
//		dettagliXlsIndirizziEmailReplicableItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
//				if (uriXlsItem.getValue() != null && !"".equals(uriXlsItem.getValue())) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		});
	}

	private void createOggetto() {

		lTextItemOggetto = new TextItem("oggetto", I18NUtil.getMessages().invionotificainteropform_oggettoItem_title());
		lTextItemOggetto.setTitleColSpan(1);
		lTextItemOggetto.setWidth(COMPONENT_WIDTH);
		lTextItemOggetto.setColSpan(8);
		lTextItemOggetto.setStartRow(true);
		lTextItemOggetto.setEndRow(true);
		lTextItemOggetto.setRequired(true);
		lTextItemOggetto.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Deve essere visualizzata in tutti i casi a meno che il valore di
				 * tipologiaMail non sia INVIO_UD In questo caso vuol dire che la finestra è
				 * stata visualizzata dal Gestore dei modelli in questa modalita
				 * 
				 */
				return !(tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD);
			}
		});
	}
	
	/*
	 * Metodo per la creazione degli allegati
	 */
	private void createAttachment() {
		
		lAttachmentItem = new AttachmentReplicableItem(true, 250);
		lAttachmentItem.setName("attach");
		lAttachmentItem.setTitle(I18NUtil.getMessages().invionotificainteropform_allegatiItem_title());
		lAttachmentItem.setTitleColSpan(1);
		lAttachmentItem.setShowNewButton(false);
		lAttachmentItem.setWidth(COMPONENT_WIDTH);
		lAttachmentItem.setColSpan(5);
		lAttachmentItem.setStartRow(false);
		lAttachmentItem.setEndRow(true);
		lAttachmentItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				
				/*
				 * Nel caso in cui si tratti del form di gestione modelli (quindi tipologiaMailGestioneModelli != null)
				 * NON deve visualizzare questo campo. Altrimenti dipende dalle altre condizioni
				 */
				if ((lAttachmentItem != null) && (lAttachmentItem.getTotalMembers() > 0) && tipologiaMailGestioneModelli == null) {
					return true;
				} else {
					return false;
				}
			}
		});
	}

	/*
	 * Metodo per la creazione del Mittente
	 */
	private void createMittente(String tipoRel) {

		lSelectItemMittente = new SelectItem("mittente",I18NUtil.getMessages().invionotificainteropform_mittenteItem_title());
		lSelectItemMittente.setDisplayField("value");
		lSelectItemMittente.setValueField("key");
		lSelectItemMittente.setRequired(true);
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		
		if (tipoRel !=null && tipoRel.equalsIgnoreCase("nuovo_messaggio_multi_destinatari_xls")){
			accounts.addParam("finalita", "INVIO_NUOVO_MSG");
		}
		else{
			accounts.addParam("finalita", "INVIO");	
		}
		
		accounts.addParam("tipoAccount", "ID");
		lSelectItemMittente.setOptionDataSource(accounts);
		lSelectItemMittente.setColSpan(4);
		lSelectItemMittente.setWidth(COMPONENT_WIDTH);
		lSelectItemMittente.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (isMittentePec()) {
					casellaIsPecItem.setValue("true");
				} else {
					casellaIsPecItem.setValue("false");
				}
				popolaDettagliXls();
				_this.markForRedraw(); // Ad ogni selezione di un nuovo mittente viene ricaricato il form della mail
			}
		});

		final GWTRestDataSource mittenteDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		mittenteDefaultDS.addParam("prefKey", getConfiguredPrefKeyPrefix() + "mittenteDefault");

		AdvancedCriteria mittenteDefaultCriteria = new AdvancedCriteria();
		mittenteDefaultCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		mittenteDefaultDS.fetchData(mittenteDefaultCriteria, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS && data.length != 0) {
					String mittenteDefault = data[0].getAttributeAsString("value");
					setMittenteInziale(mittenteDefault);
				}
			}
		});

		// Inserimento del pulsante da affiancare alla select di selezione del mittente
		createSalvaMittenteDefaultImg(mittenteDefaultDS);
	}

	private void createSalvaMittenteDefaultImg(final GWTRestDataSource mittenteDefaultDS) {

		salvaMittenteDefaultImgButton = new ImgButtonItem("salvaMittenteDefault", "buttons/saveIn.png",
				I18NUtil.getMessages().invioudmail_detail_salvaMittenteDefault_title());
		salvaMittenteDefaultImgButton.setShowTitle(false);
		salvaMittenteDefaultImgButton.setAlwaysEnabled(true);
		salvaMittenteDefaultImgButton.setWidth(10);
		salvaMittenteDefaultImgButton.setValueIconSize(32);
		salvaMittenteDefaultImgButton.setColSpan(5);
		salvaMittenteDefaultImgButton.setStartRow(false);
		salvaMittenteDefaultImgButton.setEndRow(true);
		salvaMittenteDefaultImgButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_salvaMittenteDefault_title());
		salvaMittenteDefaultImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				if (lSelectItemMittente.getValueAsString() != null
						&& !"".equals(lSelectItemMittente.getValueAsString())) {
					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
					mittenteDefaultDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];
								record.setAttribute("value", lSelectItemMittente.getValueAsString());
								mittenteDefaultDS.updateData(record);
							} else {
								Record record = new Record();
								record.setAttribute("prefName", "DEFAULT");
								record.setAttribute("value", lSelectItemMittente.getValueAsString());
								mittenteDefaultDS.addData(record);
							}
							clearErrors(true);
							Layout.addMessage(new MessageBean(
									lSelectItemMittente.getValueAsString() + " salvato come mittente di default", "",
									MessageType.INFO));
						}
					});
				} else {
					setShowInlineErrors(true);
					Map<String, String> lMap = new HashMap<String, String>();
					lMap.put("mittente", "Nessun mittente selezionato come default");
					setErrors(lMap);
				}
			}
		});
	}

	/*
	 * Metodo per la creazione del caricamento del foglio xls
	 */
	private void createUploadFileXls() {

		uriXlsItem = new HiddenItem("uriXls");
		infoFileXlsItem = new HiddenItem("infoFileXls");

		CustomValidator lTipiAmmessiValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {

				final String uri = _this.getValueAsString("uri");

				if (uri != null && !uri.equals("")) {
					final InfoFileRecord infoFile = new InfoFileRecord(_this.getValue("infoFileXls"));
					return infoFile != null && infoFile.getMimetype() != null
							&& (infoFile.getMimetype().equals("application/excel") || infoFile.getMimetype()
									.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
				}
				return true;
			}
		};
		lTipiAmmessiValidator.setErrorMessage(
				"Formato non riconosciuto o non ammesso. Gli unici formati validi risultano essere xls/xlsx");

		CustomValidator lNomeFileValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				return (value != null && !"".equals(value)) && uriXlsItem.getValue() != null
						&& !"".equals(uriXlsItem.getValue());
			}
		};
		lNomeFileValidator.setErrorMessage("Attenzione, file non caricato correttamente");

		nomeFileXlsItem = new TextItem("nomeFileXls",
				I18NUtil.getMessages().invio_mail_form_multi_destinatari_xls_nomeFileXlsItem());
		nomeFileXlsItem.setShowTitle(true);
		nomeFileXlsItem.setWidth(COMPONENT_WIDTH);
		nomeFileXlsItem.setRequired(true);
		nomeFileXlsItem.setStartRow(true);
		nomeFileXlsItem.setEndRow(false);
		nomeFileXlsItem.setColSpan(4);
		nomeFileXlsItem.setValidators(lTipiAmmessiValidator, lNomeFileValidator);

		uploadFileXlsButton = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {

				_this.clearErrors(true);
				_this.setValue("nomeFileXls", displayFileName);
				_this.setValue("uriXls", uri);
				changedEventAfterUploadFileXls(displayFileName, uri, _this, nomeFileXlsItem, uriXlsItem);

				popolaDettagliXls();

				_this.markForRedraw();
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file xlsx";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
				uploadFileXlsButton.getCanvas().redraw();
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				if (info == null || info.getMimetype() == null ||
						(!info.getMimetype().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
					GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non riconosciuto o non ammesso. "
							+ "L'unico formato valido è xlsx", "", MessageType.ERROR));
					clickEliminaFileXls(_this, uploadFileXlsButton);
				} else {
					_this.setValue("infoFileXls", info);
				}
			}
		});
		uploadFileXlsButton.setColSpan(1);
		uploadFileXlsButton.setShowTitle(false);
		uploadFileXlsButton.setWidth(10);
		uploadFileXlsButton.setValueIconSize(32);
		uploadFileXlsButton.setStartRow(false);
		uploadFileXlsButton.setEndRow(false);

		eliminaFileXlsButton = new ImgButtonItem("eliminaFileXlsButton", "file/editdelete.png", "");
		eliminaFileXlsButton.setShowTitle(false);
		eliminaFileXlsButton.setAlwaysEnabled(true);
		eliminaFileXlsButton.setWidth(16);
		eliminaFileXlsButton.setValueIconSize(32);
		eliminaFileXlsButton.setColSpan(1);
		eliminaFileXlsButton.setStartRow(false);
		eliminaFileXlsButton.setEndRow(true);
		eliminaFileXlsButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_eliminaMenuItem_prompt());
		eliminaFileXlsButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (uriXlsItem.getValue() != null && !"".equals(uriXlsItem.getValue())) {
					return true;
				} else {
					return false;
				}
			}
		});
		eliminaFileXlsButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				clickEliminaFileXls();
			}
		});
	}

	public void clickEliminaFileXls() {

		uriXlsItem.setValue("");
		nomeFileXlsItem.setValue("");
		infoFileXlsItem.setValue("");
		uploadFileXlsButton.clearValue();
		uploadFileXlsButton.redraw();
		_this.markForRedraw();
	}

	/*
	 * Metodo per la creazione del range delle righe del foglio xls
	 */
	private void createRigheXls() {

		rigaXlsDaItem = new NumericItem("rigaXlsDa",
				I18NUtil.getMessages().invio_mail_form_multi_destinatari_xls_rigaXlsDaItem(), false);
		rigaXlsDaItem.setWidth(100);
		rigaXlsDaItem.setColSpan(1);
		rigaXlsDaItem.setStartRow(true);
		rigaXlsDaItem.setEndRow(false);
		rigaXlsDaItem.setAttribute("obbligatorio", true);
		rigaXlsDaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				if (uriXlsItem.getValue() != null && !"".equals(uriXlsItem.getValue())) {
					return true;
				} else {
					return false;
				}
			}
		});
		Validator requiredValidator = buildRigaXlsDaRequiredValidator();
		Validator positivoValidator = buildRigaXlsDaPositivoValidator();
		rigaXlsDaItem.setValidators(requiredValidator, positivoValidator);

		rigaXlsAItem = new NumericItem("rigaXlsA",
				I18NUtil.getMessages().invio_mail_form_multi_destinatari_xls_rigaXlsAItem(), false);
		rigaXlsAItem.setWidth(100);
		rigaXlsAItem.setColSpan(1);
		rigaXlsAItem.setEndRow(false);
		rigaXlsAItem.setStartRow(false);
		rigaXlsAItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				if (uriXlsItem.getValue() != null && !"".equals(uriXlsItem.getValue())) {
					return true;
				} else {
					return false;
				}
			}
		});
	}

	private void createAvvertimento() {

		noteAvvertimentoItem = new StaticTextItem("noteAvvertimento");
		noteAvvertimentoItem.setVisible(true);
		noteAvvertimentoItem.setDefaultValue("<span style=\"color:#ff0000\"><b>"
				+ I18NUtil.getMessages().invio_mail_form_multi_destinatari_xls_noteAvvertimentoItem() + "</span></b>");
		noteAvvertimentoItem.setShowTitle(false);
		noteAvvertimentoItem.setWrap(true);
		noteAvvertimentoItem.setWidth(COMPONENT_WIDTH);
		noteAvvertimentoItem.setColSpan(17);
	}

	private void manageEditViewerBody() {

		String body = "";
		if ("text".equals(style.getValueAsString())) {
			body = lTextAreaItemBody.getValueAsString();
		} else {
			body = (String) lRichTextItemBody.getValue();
		}

		final EditorEmailWindow editorEmailWindow = new EditorEmailWindow(
				I18NUtil.getMessages().posta_elettronica_editor_view_title_new_message(), body) {

			@Override
			public void manageOnCloseClick() {
				String body = getCurrentBody();
				if ("text".equals(style.getValueAsString())) {
					lTextAreaItemBody.setValue(body);
				} else {
					lRichTextItemBody.setValue(body);
				}
				markForDestroy();
			}
		};
		editorEmailWindow.show();
	}

	private void setMittenteInziale(final String mittenteIniziale) {

		if (mittenteIniziale != null && !"".equals(mittenteIniziale)) {
			if (lSelectItemMittente.getValue() == null || "".equals(lSelectItemMittente.getValue())) {
				lSelectItemMittente.fetchData(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (lSelectItemMittente.getClientPickListData().length == 0) {
							lSelectItemMittente.setValue("");
						} else {
							boolean trovato = false;
							if (mittenteIniziale != null && !"".equals(mittenteIniziale)) {
								for (int i = 0; i < lSelectItemMittente.getClientPickListData().length; i++) {
									Record recordItem = lSelectItemMittente.getClientPickListData()[i];
									if (recordItem.getAttributeAsString("key").equals(mittenteIniziale)) {
										trovato = true;
										break;
									}
								}
								if (trovato) {
									lSelectItemMittente.setValue(mittenteIniziale);

									if (isMittentePec()) {
										casellaIsPecItem.setValue("true");
									} else {
										casellaIsPecItem.setValue("false");
									}
									// Refresh del form per eseguire l'analisi della mail di default (PEC o PEO)
									markForRedraw();
									return;
								}
							}
							if (!trovato) {
								Layout.addMessage(new MessageBean(
										I18NUtil.getMessages()
												.postaElettronica_nuovomessaggio_mittente_default_warning(),
										"", MessageType.WARNING));
							}
						}
					}
				});
			}
		}
	}

	private String getConfiguredPrefKeyPrefix() {
		return Layout.getConfiguredPrefKeyPrefix();
	}

	public Record getRecord() {

		if (validate()) {
			Record lRecord = new Record(rememberValues());
			return lRecord;
		} else
			return null;
	}

	protected void manageChangeTextHtml(ChangedEvent event) {

		if (event.getValue().equals("text")) {
			// Si sta cambiando dal RichTextItem al TextItem
			if (AurigaLayout.getParametroDBAsBoolean("CANCELLA_TAG_VUOTI_CORPO_MAIL")) {
				try {
					Record recordBody = new Record();
					recordBody.setAttribute("bodyHtml",
							lRichTextItemBody.getValue() != null ? ((String) lRichTextItemBody.getValue()) : "");
					// Chiamata al datasource per controllare il contenuto del corpo della mail
					GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
					corpoMailDataSource.performCustomOperation("checkIfBodyIsEmpty", recordBody, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

								Record record = response.getData()[0];
								bodyText = record.getAttribute("bodyText");
								lTextAreaItemBody.setValue(bodyText);
								// Nascondo la text area per l'html
								lRichTextItemBody.hide();
								// Visualizzo la text area
								lTextAreaItemBody.show();
								markForRedraw();
							}
						}
					}, new DSRequest());
				} catch (Exception e) {
					lTextAreaItemBody.setValue(bodyText);
					// Nascondo la text area per l'html
					lRichTextItemBody.hide();
					// Visualizzo la text area
					lTextAreaItemBody.show();
					markForRedraw();
				}
			} else {
				bodyText = lTextAreaItemBody.getValue().toString();
				lTextAreaItemBody.setValue(bodyText);
				// Nascondo la text area per l'html
				lRichTextItemBody.hide();
				// Visualizzo la text area
				lTextAreaItemBody.show();
				markForRedraw();
			}
		} else {
			// Nascondo la text area per l'html
			lTextAreaItemBody.hide();

			// Visualizzo la text area
			lRichTextItemBody.show();
			markForRedraw();
		}
	}

	// Appende a str la stringa strToAppend, preceduta dal carattere ; se questo non
	// è presente alla fine di str
	public String appendIndirizzoEmail(String str, String strToAppend) {

		String res = "";
		if (str == null || str.equals("")) {
			res = strToAppend;
		} else if (strToAppend != null && !strToAppend.equals("")
				&& !str.toLowerCase().contains(strToAppend.toLowerCase())) {
			String lastChar = str.substring(str.length() - 1);
			if (lastChar.equalsIgnoreCase(";")) {
				res = str + strToAppend;
			} else {
				res = str + ";" + strToAppend;
			}
		} else {
			res = str;
		}
		return res;
	}

	public String removeIndirizzoEmail(String str, String strToRemove) {
		String res = "";
		if (str != null && !str.equals("") && strToRemove != null && !strToRemove.equals("")
				&& str.toLowerCase().contains(strToRemove.toLowerCase())) {
			StringSplitterClient st = new StringSplitterClient(str, ";");
			for (int i = 0; i < st.getTokens().length; i++) {
				if (!st.getTokens()[i].equalsIgnoreCase(strToRemove)) {
					res += st.getTokens()[i];
					if (i < (st.getTokens().length - 1)) {
						res += ";";
					}
				}
			}
		} else {
			res = str;
		}
		return res;
	}

	public class InvioMailMultiLookupRubricaEmailPopup extends LookupRubricaEmailPopup {

		private String fieldName;
		private HashMap<String, Integer> indirizzoRefCount = new HashMap<String, Integer>();

		public InvioMailMultiLookupRubricaEmailPopup(String pFieldName) {
			super(false);
			this.fieldName = pFieldName;
			String value = getValueAsString(fieldName);
			if (value != null && !value.equals("")) {
				StringSplitterClient st = new StringSplitterClient(value, ";");
				for (int i = 0; i < st.getTokens().length; i++) {
					String indirizzo = st.getTokens()[i];
					incrementaIndirizzoRefCount(indirizzo);
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			if ("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource",
						"idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if (listaMembri != null && listaMembri.getLength() > 0) {
								for (int i = 0; i < listaMembri.getLength(); i++) {
									String indirizzo = listaMembri.get(i).getAttribute("indirizzoEmail");
									incrementaIndirizzoRefCount(indirizzo);
									setValue(fieldName, appendIndirizzoEmail(getValueAsString(fieldName), indirizzo));
								}
							}
						}
					}
				}, new DSRequest());
			} else {
				String indirizzo = record.getAttribute("indirizzoEmail");
				incrementaIndirizzoRefCount(indirizzo);
				setValue(fieldName, appendIndirizzoEmail(getValueAsString(fieldName), indirizzo));
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			if ("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource",
						"idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if (listaMembri != null && listaMembri.getLength() > 0) {
								for (int i = 0; i < listaMembri.getLength(); i++) {
									String indirizzo = listaMembri.get(i).getAttribute("indirizzoEmail");
									decrementaIndirizzoRefCount(indirizzo);
									if (!indirizzoRefCount.containsKey(indirizzo)) {
										setValue(fieldName,
												removeIndirizzoEmail(getValueAsString(fieldName), indirizzo));
									}
								}
							}
						}
					}
				}, new DSRequest());
			} else {
				String indirizzo = record.getAttribute("indirizzoEmail");
				decrementaIndirizzoRefCount(indirizzo);
				if (!indirizzoRefCount.containsKey(indirizzo)) {
					setValue(fieldName, removeIndirizzoEmail(getValueAsString(fieldName), indirizzo));
				}
			}
		}

		private void incrementaIndirizzoRefCount(String indirizzo) {
			if (indirizzoRefCount.containsKey(indirizzo)) {
				indirizzoRefCount.put(indirizzo, indirizzoRefCount.get(indirizzo) + 1);
			} else {
				indirizzoRefCount.put(indirizzo, new Integer(1));
			}
		}

		private void decrementaIndirizzoRefCount(String indirizzo) {
			if (indirizzoRefCount.containsKey(indirizzo) && indirizzoRefCount.get(indirizzo).intValue() > 1) {
				indirizzoRefCount.put(indirizzo, indirizzoRefCount.get(indirizzo) - 1);
			} else {
				indirizzoRefCount.remove(indirizzo);
			}
		}
	}

	public void verifyChangeStyle() {
		if ((getValuesManager().getValue("textHtml") != null)
				&& (getValuesManager().getValue("textHtml").equals("text"))) {
			lRichTextItemBody.clearValue();
			lRichTextItemBody.hide();
			lTextAreaItemBody.show();
		} else {
			lTextAreaItemBody.clearValue();
			lTextAreaItemBody.hide();
			lRichTextItemBody.show();
		}
		markForRedraw();
	}

	public boolean validateMittente() {
		return lSelectItemMittente.validate();
	}

	public ReplicableItem getAttachmentReplicableItem() {
		return lAttachmentItem;
	}

	private boolean isMittentePec() {
		if (lSelectItemMittente != null && lSelectItemMittente.getDisplayValue() != null
				&& !"".equals(lSelectItemMittente.getDisplayValue())
				&& lSelectItemMittente.getDisplayValue().contains("PEC")) {
			return true;
		} else {
			return false;
		}
	}

	public String getStyleText() {
		return (String) style.getValue();
	}

	protected void changedEventAfterUploadFileXls(final String displayFileName, final String uri,
			final DynamicForm formFile, final TextItem nomeFileItem, final HiddenItem uriItem) {
		ChangedEvent lChangedEventDisplayXls = new ChangedEvent(nomeFileItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return nomeFileXlsItem;
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUriXls = new ChangedEvent(uriItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return uriXlsItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFileXlsItem.fireEvent(lChangedEventDisplayXls);
		uriXlsItem.fireEvent(lChangedEventUriXls);
	}

	protected void changedEventAfterUpload(final String displayFileName, final String uri, final DynamicForm formFile,
			final TextItem nomeFileItem, final HiddenItem uriItem) {
		ChangedEvent lChangedEventDisplay = new ChangedEvent(nomeFileItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return nomeFileItem;
			}

			@Override
			public Object getValue() {
				return displayFileName;
			}
		};
		ChangedEvent lChangedEventUri = new ChangedEvent(uriItem.getJsObj()) {

			@Override
			public DynamicForm getForm() {
				return formFile;
			};

			@Override
			public FormItem getItem() {
				return uriItem;
			}

			@Override
			public Object getValue() {
				return uri;
			}
		};
		nomeFileItem.fireEvent(lChangedEventDisplay);
		uriItem.fireEvent(lChangedEventUri);
	}

	protected void clickEliminaFileXls(DynamicForm formFile, FileUploadItemWithFirmaAndMimeType uploadFileItem) {
		formFile.setValue("nomeFileXls", "");
		formFile.setValue("uriXls", "");
		formFile.clearValue("infoFileXls");
		uploadFileXlsButton.getCanvas().redraw();
	}

	protected void clickEliminaFile(DynamicForm formFile, FileUploadItemWithFirmaAndMimeType uploadFileItem) {
		formFile.setValue("nomeFile", "");
		formFile.setValue("uri", "");
		formFile.clearValue("infoFile");
		uploadFileItem.getCanvas().redraw();
	}

	protected void popolaDettagliXls() {

		rigaXlsDaItem.setValue("1");
		
		dettagliXlsIndirizziEmailReplicableItem.drawAndSetValue(new RecordList());
	
		if (casellaIsPecItem != null && casellaIsPecItem.getValue() != null) {
			if ("true".equals((String) casellaIsPecItem.getValue())) {

				dettagliXlsIndirizziEmailReplicableCanvas = dettagliXlsIndirizziEmailReplicableItem.onClickNewButton();
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("campoXls", "IndirizziEmailTo");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("colonnaXls", "");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("tipoMittente","PEC");
				
				dettagliXlsIndirizziEmailReplicableCanvas = dettagliXlsIndirizziEmailReplicableItem.onClickNewButton();
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("campoXls", "IndirizziEmailCC");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("colonnaXls", "");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("tipoMittente","PEC");
			} else {
				dettagliXlsIndirizziEmailReplicableCanvas = dettagliXlsIndirizziEmailReplicableItem.onClickNewButton();
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("campoXls", "IndirizziEmailTo");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("colonnaXls", "");
				
				dettagliXlsIndirizziEmailReplicableCanvas = dettagliXlsIndirizziEmailReplicableItem.onClickNewButton();
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("campoXls", "IndirizziEmailCC");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("colonnaXls", "");
				
				dettagliXlsIndirizziEmailReplicableCanvas = dettagliXlsIndirizziEmailReplicableItem.onClickNewButton();
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("campoXls", "IndirizziEmailCCN");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("colonnaXls", "");
			}
		} else {
				dettagliXlsIndirizziEmailReplicableCanvas = dettagliXlsIndirizziEmailReplicableItem.onClickNewButton();
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("campoXls", "IndirizziEmailTo");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("colonnaXls", "");
				
				dettagliXlsIndirizziEmailReplicableCanvas = dettagliXlsIndirizziEmailReplicableItem.onClickNewButton();
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("campoXls", "IndirizziEmailCC");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("colonnaXls", "");
				
				dettagliXlsIndirizziEmailReplicableCanvas = dettagliXlsIndirizziEmailReplicableItem.onClickNewButton();
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("campoXls", "IndirizziEmailCCN");
				dettagliXlsIndirizziEmailReplicableCanvas.getForm()[0].setValue("colonnaXls", "");
		}
	}

	private RequiredIfValidator buildRigaXlsDaRequiredValidator() {

		RequiredIfValidator requiredValidator = new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if (uriXlsItem.getValue() != null && !"".equals(uriXlsItem.getValue())) {
					if (value == null) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		});
		requiredValidator.setErrorMessage(
				I18NUtil.getMessages().invio_mail_form_multi_destinatari_xls_rigaXlsDaItem_obbligatorio_errorMessage());
		return requiredValidator;
	}

	private CustomValidator buildRigaXlsDaPositivoValidator() {

		CustomValidator requiredValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {

				if (uriXlsItem.getValue() != null && !"".equals(uriXlsItem.getValue())) {
					if (value == null) {
						return true;
					}
					Integer intValue = Integer.valueOf((String) value);
					return intValue.intValue() > 0;
				} else {
					return false;
				}
			}
		};
		requiredValidator.setErrorMessage(
				I18NUtil.getMessages().invio_mail_form_multi_destinatari_xls_rigaXlsDaItem_nonValido_errorMessage());
		return requiredValidator;
	}
}