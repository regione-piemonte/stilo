/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupRubricaEmailPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.EditorEmailWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.SceltaDestinatarioWindow;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
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
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class InvioMailFormNew extends DynamicForm {

	private static final int COMPONENT_WIDTH = 800;
	
	private InvioMailFormNew _this = this;

	private HiddenItem lHiddenItemIdEmail;
	private HiddenItem lHiddenItemIdEmailUD;
	private HiddenItem lHiddenItemIdUD;

	private SelectItem lSelectItemMittente;
	private HiddenItem casellaIsPecItem;
	private ImgButtonItem salvaMittenteDefaultImgButton;
	
	private HiddenItem recordFiltriDestinatariItem;
	private FormItemIcon filtroDestinatariItem;
	private ComboBoxItem lComboBoxDestinatari;
	private ImgButtonItem lookupRubricaEmailDestinatariImgButton;

	private HiddenItem recordFiltriDestinatariCCItem;
	private FormItemIcon filtroDestinatariCCItem;
	private ComboBoxItem lComboBoxDestinatariCC;
	private ImgButtonItem lookupRubricaEmailDestinatariCCImgButton;
	
	private HiddenItem recordFiltriDestinatariCCNItem;
	private FormItemIcon filtroDestinatariCCNItem;
	private ComboBoxItem lComboBoxDestinatariCCN;
	private ImgButtonItem lookupRubricaEmailDestinatariCCNImgButton;

	private TextItem lTextItemOggetto;

	private AttachmentReplicableItem lAttachmentItem;
	private FileUploadItemWithFirmaAndMimeType uploadButton;
	private ReplicableCanvas actual;

	private RadioGroupItem style;
	private TextAreaItem lTextAreaItemBody;
	private RichTextItem lRichTextItemBody;
	private ImgButtonItem editCorpoMailImgButton;

	private CheckboxItem flgSalvaInviatiItem;
	private CheckboxItem flgConfermaDiLetturaItem;
	private CheckboxItem flgInvioSeparatoItem;

	protected TipologiaMail tipologiaMailGestioneModelli;

	private String bodyHtml = "";
	private String bodyText = "";
	
	private GWTRestDataSource mittenteDefaultDS;
	
	private LinkedHashMap<String, String> styleMap;

	/**
	 * 
	 * @param tipologiaMail variabile utilizzata da GestioneModelli per identificare se si tratta di una risposta o un inoltro.
	 * Se è una risposta non vengono visualizzate le text relative ai destinatari principali e CC.
	 * Altrimenti vengono visualizzate.
	 */
	public InvioMailFormNew(TipologiaMail tipologiaMail, String tipoRel, ValuesManager vm) {
		
		this.tipologiaMailGestioneModelli = tipologiaMail;
		
		setWidth100();
		setHeight100();
		setWrapItemTitles(false);
		setNumCols(20);
		setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, "*", "*");
		
		lHiddenItemIdEmail = new HiddenItem("idEmail");
		lHiddenItemIdEmailUD = new HiddenItem("idEmailUD"); 
		lHiddenItemIdUD = new HiddenItem("idUD"); 
		casellaIsPecItem = new HiddenItem("casellaIsPec");
		
		/**
		 * FILTRI DESTINATARI
		 */
		recordFiltriDestinatariItem = new HiddenItem("recordFiltriDestinatari");
		recordFiltriDestinatariCCItem = new HiddenItem("recordFiltriDestinatariCC");
		recordFiltriDestinatariCCNItem = new HiddenItem("recordFiltriDestinatariCCN");
		
		/**
		 *  MITTENTE
		 */
		mittenteDefaultDS = createMittente(tipoRel);
		
		//Inserimento del pulsante da affiancare alla select di selezione del mittente
		createSalvaMittenteDefaultImg(mittenteDefaultDS);
		
		// Creazione filtro destinatari
		creaFiltroDestinatariPrimari();
	
		//Creazione destinatari primari
		createDestinatariPrimari(getValidatorEmail());
				
		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario
		createLookupRubricaEmailDestinatari();
		
		// Creazione filtro destinatari
		creaFiltroDestinatariCC();
		
		//Creazione destinatari in CC
		createDestinaratiCC(getValidatorEmail());
		
		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario CC
		createLookupRubricaEmailDestinatariCC();
		
		// Creazione filtro destinatari
		creaFiltroDestinatariCCN();
		
		//Creazione destinatari in CCN
		createDestinaratiCCN(getValidatorEmail());
		
		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario CCN
		createLookupRubricaEmailDestinatariCCN();
		
		// Oggetto
		createOggetto();
		
		// Attachment
		createAttachment();

		SpacerItem spacerAttach = new SpacerItem();
		spacerAttach.setWidth(20);
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

		//Radio Opzioni Testo
		createRadioBody();
		
		//Checkbox conferma di lettursa
		createConfermaDiLettura();
		
		//Chekbox invio separato
		createInvioSeparato();

		// Casella testo TEXT
		createTextArea();

		// Casella testo HTML
		createRichTextArea();
		
		//Bottone per editare il corpo email
		createEditCorpoEmailButton();

		// Salva email
		createSalvaEmailButton();

		SpacerItem lSpacerItem = new SpacerItem();
		lSpacerItem.setStartRow(true);
		lSpacerItem.setColSpan(1);

		setFields(
			  lHiddenItemIdEmail, lHiddenItemIdEmailUD, lHiddenItemIdUD, casellaIsPecItem,
			  recordFiltriDestinatariItem,recordFiltriDestinatariCCItem,recordFiltriDestinatariCCNItem,
			  lSelectItemMittente, salvaMittenteDefaultImgButton,
			  lComboBoxDestinatari, lookupRubricaEmailDestinatariImgButton,
			  lComboBoxDestinatariCC, lookupRubricaEmailDestinatariCCImgButton,
			  lComboBoxDestinatariCCN, lookupRubricaEmailDestinatariCCNImgButton,
			  lTextItemOggetto,
			  lAttachmentItem, spacerAttach, uploadButton,
			  style,flgConfermaDiLetturaItem,flgInvioSeparatoItem,  				  
			  lSpacerItem,lRichTextItemBody,lTextAreaItemBody,editCorpoMailImgButton,			  
			  lSpacerItem,flgSalvaInviatiItem  
		);
		
		if(vm != null){
			setValuesManager(vm);			
		} else {
			setValuesManager(new ValuesManager());
		}
	}

	/**
	 * Validator per il campo email
	 */
	private CustomValidator getValidatorEmail() {
		CustomValidator lEmailRegExpValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || value.equals(""))
					return true;
				RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
				String lString = (String) value;
				String[] list = IndirizziEmailSplitter.split(lString);
				boolean res = true;
				for(int i=0; i < list.length; i++){
					if (list[i] == null || list[i].equals(""))
						res = res && true;
					else
						res = res && regExp.test(list[i].trim());
				}
				return res;
			}
		};
		lEmailRegExpValidator.setErrorMessage(I18NUtil.getMessages().invionotificainteropform_destinatariValidatorErrorMessage());
		return lEmailRegExpValidator;
	}

	private void createSalvaEmailButton() {
		
		flgSalvaInviatiItem = new CheckboxItem("salvaInviati",
				I18NUtil.getMessages().invionotificainteropform_salvaInviatiItem_title());
		flgSalvaInviatiItem.setWrapTitle(true);
		flgSalvaInviatiItem.setColSpan(1);
		flgSalvaInviatiItem.setWidth("*");
		flgSalvaInviatiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Posta la condizione tipologiaMailGestioneModelli == null perchè se si tratta della schermata
				 * di gestioneModelli (sia in risposta che in inoltro) questa variabile ha valore diverso da null
				 * e NON deve essere visualizzato tale flag
				 */
				if(tipologiaMailGestioneModelli != null) {
					return false;
				} else if(AurigaLayout.getParametroDBAsBoolean("HIDE_SAVE_SENT_EMAIL")){
					return false;
				}else{
					if(isMittentePec()){
						return false;
					}else{
						return true;
					}
				}
			}
		});
	}

	private void createEditCorpoEmailButton() {
		
		editCorpoMailImgButton = new ImgButtonItem("editCorpo", "buttons/view_editor.png", I18NUtil.getMessages().posta_elettronica_editor_view_title_new_message());
		editCorpoMailImgButton.setShowTitle(false);
		editCorpoMailImgButton.setAlwaysEnabled(true);
		editCorpoMailImgButton.setWidth(10);
		editCorpoMailImgButton.setValueIconSize(32);
		editCorpoMailImgButton.setStartRow(false);
		editCorpoMailImgButton.setEndRow(true);
		editCorpoMailImgButton.setColSpan(1);
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
		lRichTextItemBody.setDefaultValue("");
		lRichTextItemBody.setVisible(true);
		lRichTextItemBody.setShowTitle(false);
		// lRichTextItemBody.setCellStyle(it.eng.utility.Styles.textItem); // lo commento perchè sennò il RichTextItem compare spostato a dx, così facendo però mi mette il bordino 
		lRichTextItemBody.setHeight(600);
		lRichTextItemBody.setWidth(COMPONENT_WIDTH);
		lRichTextItemBody.setColSpan(8);
		lRichTextItemBody.setStartRow(false);
		lRichTextItemBody.setEndRow(false);
		lRichTextItemBody.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");
	}

	private void createTextArea() {
		
		lTextAreaItemBody = new TextAreaItem("bodyText") {
			
			@Override
			public boolean showViewer() {
				return false;
			}
		};
		lTextAreaItemBody.setDefaultValue("");
		lTextAreaItemBody.setVisible(false);
		lTextAreaItemBody.setShowTitle(false);
		lTextAreaItemBody.setHeight(600);
		lTextAreaItemBody.setWidth(COMPONENT_WIDTH);
		lTextAreaItemBody.setColSpan(8);
		lTextAreaItemBody.setStartRow(false);
		lTextAreaItemBody.setEndRow(false);
	}

	private void createInvioSeparato() {
		
		flgInvioSeparatoItem = new CheckboxItem("flgInvioSeparato", I18NUtil.getMessages().inviomailform_flgInvioSeparato_title());
		flgInvioSeparatoItem.setWrapTitle(true);
		flgInvioSeparatoItem.setWidth("*");
		flgInvioSeparatoItem.setColSpan(1);
	}

	private void createConfermaDiLettura() {
		
		flgConfermaDiLetturaItem = new CheckboxItem("confermaLettura",I18NUtil.getMessages().inviomailformnew_confermalettura_title());
		flgConfermaDiLetturaItem.setWrapTitle(true);
		flgConfermaDiLetturaItem.setWidth("*");
		flgConfermaDiLetturaItem.setColSpan(1);
		flgConfermaDiLetturaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return checkShowConfermaDiLettura();
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
		style.setWidth(250);
		style.setColSpan(1);
		style.setStartRow(true);
		style.setEndRow(false);
		style.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				manageChangeTextHtml(event);
			}
		});
	}

	private void createUploadFile() {
		
		uploadButton = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				actual = lAttachmentItem.onClickNewButton();
				actual.getForm()[0].setValue("fileNameAttach", displayFileName);
				actual.getForm()[0].setValue("uriAttach", uri);
				((AttachmentCanvas) actual).changedEventAfterUpload(displayFileName, uri);
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
				actual.getForm()[0].setValue("infoFileAttach", info);
			}
		});
		uploadButton.setTitle(I18NUtil.getMessages().invionotificainteropform_allegatiItem_title());
		uploadButton.setStartRow(false);
		uploadButton.setEndRow(false);
		uploadButton.setColSpan(1);
		uploadButton.setShowTitle(true);
		uploadButton.setVAlign(VerticalAlignment.CENTER);
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

	private void createAttachment() {
		
		lAttachmentItem = new AttachmentReplicableItem(true, 250);
		lAttachmentItem.setName("attach");
		lAttachmentItem.setTitle(I18NUtil.getMessages().invionotificainteropform_allegatiItem_title());
		lAttachmentItem.setShowNewButton(false);
		lAttachmentItem.setWidth(COMPONENT_WIDTH);
		lAttachmentItem.setColSpan(8);
		lAttachmentItem.setShowIfCondition(new FormItemIfFunction() {

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
	}

	private void createOggetto() {
		lTextItemOggetto = new TextItem("oggetto", I18NUtil.getMessages().invionotificainteropform_oggettoItem_title());
		lTextItemOggetto.setWidth(COMPONENT_WIDTH);
		lTextItemOggetto.setColSpan(8);
		lTextItemOggetto.setStartRow(true);
		lTextItemOggetto.setEndRow(true);
		lTextItemOggetto.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia INVIO_UD
				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
				 *  
				 */
				return !(tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD);
			}
		});
	}

	private void createLookupRubricaEmailDestinatari() {
		
		lookupRubricaEmailDestinatariImgButton = new ImgButtonItem("lookupRubricaEmailDestinatari", "lookup/rubricaemail.png", I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariImgButton.setShowTitle(false);
		lookupRubricaEmailDestinatariImgButton.setAlwaysEnabled(true);
		lookupRubricaEmailDestinatariImgButton.setWidth(16);
		lookupRubricaEmailDestinatariImgButton.setColSpan(1);
		lookupRubricaEmailDestinatariImgButton.setValueIconSize(32);
		lookupRubricaEmailDestinatariImgButton.setStartRow(false);
		lookupRubricaEmailDestinatariImgButton.setEndRow(true);
		lookupRubricaEmailDestinatariImgButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup(
						"destinatari");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailDestinatariImgButton.setShowIfCondition(new FormItemIfFunction() {
	
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
				 *  
				 */
				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD);
			}
		});
	}
	
	private void createLookupRubricaEmailDestinatariCC() {
		
		lookupRubricaEmailDestinatariCCImgButton = new ImgButtonItem("lookupRubricaEmailDestinatariCC", "lookup/rubricaemail.png", I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariCCImgButton.setShowTitle(false);
		lookupRubricaEmailDestinatariCCImgButton.setAlwaysEnabled(true);
		lookupRubricaEmailDestinatariCCImgButton.setWidth(16);
		lookupRubricaEmailDestinatariCCImgButton.setValueIconSize(32);
		lookupRubricaEmailDestinatariCCImgButton.setStartRow(false);
		lookupRubricaEmailDestinatariCCImgButton.setEndRow(true);
		lookupRubricaEmailDestinatariCCImgButton.setColSpan(1);
		lookupRubricaEmailDestinatariCCImgButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariCCImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup(
						"destinatariCC");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailDestinatariCCImgButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
				 *  
				 */
				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD);
			}
		});
	}
	
	private void createLookupRubricaEmailDestinatariCCN() {
		
		lookupRubricaEmailDestinatariCCNImgButton = new ImgButtonItem("lookupRubricaEmailDestinatariCCN", "lookup/rubricaemail.png", I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariCCNImgButton.setShowTitle(false);
		lookupRubricaEmailDestinatariCCNImgButton.setAlwaysEnabled(true);
		lookupRubricaEmailDestinatariCCNImgButton.setWidth(16);
		lookupRubricaEmailDestinatariCCNImgButton.setValueIconSize(32);
		lookupRubricaEmailDestinatariCCNImgButton.setStartRow(false);
		lookupRubricaEmailDestinatariCCNImgButton.setEndRow(true);
		lookupRubricaEmailDestinatariCCNImgButton.setColSpan(1);
		lookupRubricaEmailDestinatariCCNImgButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariCCNImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup(
						"destinatariCCN");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailDestinatariCCNImgButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
				 *  
				 */
				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD
						|| isMittentePec());
			}
		});
	}

	private GWTRestDataSource createMittente(String tipoRel) {
		
		lSelectItemMittente = new SelectItem("mittente", I18NUtil.getMessages().invionotificainteropform_mittenteItem_title());
		lSelectItemMittente.setDisplayField("value");
		lSelectItemMittente.setValueField("key");
		lSelectItemMittente.setRequired(true);
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		
		if (tipoRel !=null && tipoRel.equalsIgnoreCase("nuovo_messaggio_cruscotto")){
			accounts.addParam("finalita", "INVIO_NUOVO_MSG");
		} else{
			accounts.addParam("finalita", "INVIO");	
		}
		
		lSelectItemMittente.setOptionDataSource(accounts);
		lSelectItemMittente.setColSpan(8);
		lSelectItemMittente.setWidth(COMPONENT_WIDTH); 
		lSelectItemMittente.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if(isMittentePec()){
					if(!(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD)){
						lComboBoxDestinatariCCN.hide();
						lookupRubricaEmailDestinatariCCNImgButton.hide();
					}
					flgSalvaInviatiItem.hide();
					casellaIsPecItem.setValue("true");
				}else{
					if(!AurigaLayout.getParametroDBAsBoolean("HIDE_SAVE_SENT_EMAIL")){
						flgSalvaInviatiItem.show();
					}
					casellaIsPecItem.setValue("false");
					//Se provengo da GestoreModelli non viene visualizzata la combo dei destinatari
					if(!(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD)){
						lComboBoxDestinatariCCN.show();
						lookupRubricaEmailDestinatariCCNImgButton.show();
					}
				}
				flgConfermaDiLetturaItem.setValue(false); // Ogni volta che si cambia mittente nel form il flag di Conferma lettura si deve resettare.
				markForRedraw(); // Ad ogni selezione di un nuovo mittente viene ricaricato il form della mail
			}
		});

		final GWTRestDataSource lMittenteDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		lMittenteDefaultDS.addParam("prefKey", getConfiguredPrefKeyPrefix() + "mittenteDefault");

		return lMittenteDefaultDS;
	}

	private void createSalvaMittenteDefaultImg(final GWTRestDataSource lMittenteDefaultDS) {
		
		salvaMittenteDefaultImgButton = new ImgButtonItem("salvaMittenteDefault", "buttons/saveIn.png", I18NUtil.getMessages().invioudmail_detail_salvaMittenteDefault_title());
		salvaMittenteDefaultImgButton.setShowTitle(false);
		salvaMittenteDefaultImgButton.setAlwaysEnabled(true);
		salvaMittenteDefaultImgButton.setWidth(10);
		salvaMittenteDefaultImgButton.setValueIconSize(32);
		salvaMittenteDefaultImgButton.setColSpan(1);
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
					lMittenteDefaultDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];
								record.setAttribute("value", lSelectItemMittente.getValueAsString());
								lMittenteDefaultDS.updateData(record);
							} else {
								Record record = new Record();
								record.setAttribute("prefName", "DEFAULT");
								record.setAttribute("value", lSelectItemMittente.getValueAsString());
								lMittenteDefaultDS.addData(record);
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
	
	private void creaFiltroDestinatariPrimari() {
		
		filtroDestinatariItem = new FormItemIcon();
		filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_off.png");  
		filtroDestinatariItem.setWidth(16);  
		filtroDestinatariItem.setHeight(16);  
	    filtroDestinatariItem.setInline(true);  
	    filtroDestinatariItem.setName("filtroDestinatari");
	    filtroDestinatariItem.setPrompt("Filtro sulla scelta destinatari");
		filtroDestinatariItem.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent  event) {
				new SceltaDestinatarioWindow("Filtro sulla scelta destinatari",new Record(_this.getValues()).getAttributeAsRecord("recordFiltriDestinatari")) {
					
					@Override
					public void salvaFiltri(Record record) {
						
						if(filtroPresente(record)) {
							filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_on.png");
						} else {
							filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_off.png");
						}
						
						setValue("recordFiltriDestinatari", record);
						_this.markForRedraw();
					}
				};
			}
		});
//		filtroDestinatariItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				/*
//				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
//				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
//				 */
//				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD);
//			}
//		});
	}
	
	/*
	 *  Metodo per la creazione dei Destinatari Principali
	 */
	private void createDestinatariPrimari(CustomValidator validator) {
		
		GWTRestDataSource proposteDestinatariPrimariDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatari = new ComboBoxItem("destinatari",I18NUtil.getMessages().invionotificainteropform_destinatariItem_title());
		lComboBoxDestinatari.setValueField("indirizzoEmail");
		lComboBoxDestinatari.setDisplayField("indirizzoEmail");
		lComboBoxDestinatari.setShowPickerIcon(false);
		lComboBoxDestinatari.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatari.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatari.setRequired(true);
		lComboBoxDestinatari.setCanEdit(true);
		lComboBoxDestinatari.setColSpan(8);
		lComboBoxDestinatari.setEndRow(false);
		lComboBoxDestinatari.setStartRow(true);
		lComboBoxDestinatari.setValidators(validator);
		lComboBoxDestinatari.setAutoFetchData(false);
		lComboBoxDestinatari.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatari.setAddUnknownValues(true);
		lComboBoxDestinatari.setOptionDataSource(proposteDestinatariPrimariDS);
		lComboBoxDestinatari.setFetchDelay(500);
		lComboBoxDestinatari.setValidateOnChange(false);
        lComboBoxDestinatari.setIcons(filtroDestinatariItem);  
		lComboBoxDestinatari.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				return StringUtil.asHTML((String) lComboBoxDestinatari.getValue());
			}
		});
		lComboBoxDestinatari.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				
				lComboBoxDestinatari.validate();
			}
		});
		lComboBoxDestinatari.setShowIfCondition(new FormItemIfFunction() {
		
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
				 */
				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD);
			}
		});
		ListGridField destinatariPrefNameField = new ListGridField("nomeContatto");
		destinatariPrefNameField.setWidth("100%");
		destinatariPrefNameField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (record != null) {
					String res = null;
					String nomeContatto = record.getAttribute("descVoceRubrica") != null ? record.getAttributeAsString("descVoceRubrica") : null;
					if (record.getAttributeAsString("tipoIndirizzo") != null && "C".equals(record.getAttributeAsString("tipoIndirizzo"))) {
						res = buildIconHtml("coccarda.png", nomeContatto);
						return res;
					} else if (record.getAttributeAsString("tipoIndirizzo") != null && "O".equals(record.getAttributeAsString("tipoIndirizzo"))) { 
						res = buildIconHtml("mail/PEO.png", nomeContatto);
						return res;
					} else {
						res = buildIconHtml("mail/mailingList.png", nomeContatto);
						return res;
					}
				}
				return null;
			}
		});
		lComboBoxDestinatari.setPickListFields(destinatariPrefNameField);
		
		ListGrid destinatariPickListProperties = new ListGrid();
		destinatariPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		destinatariPickListProperties.setShowHeader(false);
		destinatariPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				event.cancel();
				selezionaDestinatarioPrimario(event.getRecord());
			}
		});
		destinatariPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				
				String nome = lComboBoxDestinatari != null && lComboBoxDestinatari.getValue() != null ? (String) lComboBoxDestinatari.getValue() : null;
				GWTRestDataSource lComboBoxDestinatariDS = (GWTRestDataSource) lComboBoxDestinatari.getOptionDataSource();
				lComboBoxDestinatariDS.addParam("destinatario", nome);
				
				populateFiltriAutoCompletamento(lComboBoxDestinatariDS, getValuesAsRecord().getAttributeAsRecord("recordFiltriDestinatari"));
				
				lComboBoxDestinatari.setOptionDataSource(lComboBoxDestinatariDS);
				lComboBoxDestinatari.invalidateDisplayValueCache();
			}
		});
		lComboBoxDestinatari.setPickListProperties(destinatariPickListProperties);
	}
	
	public void selezionaDestinatarioPrimario(Record record) {
		
		lComboBoxDestinatari.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		
		_this.markForRedraw();
	}
	
	public void selezionaDestinatarioSecondario(Record record,Boolean isCC) {
		if(isCC){	
			lComboBoxDestinatariCC.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		}else{
			lComboBoxDestinatariCCN.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		}
		_this.markForRedraw();
	}

	protected String buildIconHtml(String src, String value) {
		return "<div align=\"left\"><img src=\"images/" + src + "\" height=\"10\" width=\"10\" alt=\"\" />&nbsp;&nbsp;" + value + "</div>";
	}
	
	private void creaFiltroDestinatariCC() {
		
		filtroDestinatariCCItem = new FormItemIcon();
		filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_off.png");  
		filtroDestinatariCCItem.setWidth(16);  
		filtroDestinatariCCItem.setHeight(16);  
		filtroDestinatariCCItem.setInline(true);  
		filtroDestinatariCCItem.setName("filtroDestinatariCC");
		filtroDestinatariCCItem.setPrompt("Filtro sulla scelta destinatari");
		filtroDestinatariCCItem.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent  event) {
				new SceltaDestinatarioWindow("Filtro sulla scelta destinatari",new Record(_this.getValues()).getAttributeAsRecord("recordFiltriDestinatariCC")) {
					
					@Override
					public void salvaFiltri(Record record) {
						
						if(filtroPresente(record)) {
							filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_on.png");
						} else {
							filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_off.png");
						}
						
						setValue("recordFiltriDestinatariCC", record);
						_this.markForRedraw();
					}
				};
			}
		});
//		filtroDestinatariCCItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				/*
//				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
//				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
//				 *  
//				 */
//				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD);
//			}
//		});
	}
	
	private void createDestinaratiCC(CustomValidator validator){
		
		/*
		 *  Destinatari CC
		 */
		
		GWTRestDataSource proposteDestinatariCCDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatariCC = new ComboBoxItem("destinatariCC", I18NUtil.getMessages().invionotificainteropform_destinatariCCItem_title());
		lComboBoxDestinatariCC.setValueField("indirizzoEmail");
		lComboBoxDestinatariCC.setDisplayField("indirizzoEmail");
		lComboBoxDestinatariCC.setShowPickerIcon(false);
		lComboBoxDestinatariCC.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatariCC.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatariCC.setColSpan(8);
		lComboBoxDestinatariCC.setEndRow(false);
		lComboBoxDestinatariCC.setStartRow(true);
		lComboBoxDestinatariCC.setValidators(validator);
		lComboBoxDestinatariCC.setAutoFetchData(false);
		lComboBoxDestinatariCC.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatariCC.setAddUnknownValues(true);
		lComboBoxDestinatariCC.setValidateOnChange(false);
		lComboBoxDestinatariCC.setOptionDataSource(proposteDestinatariCCDS);
		lComboBoxDestinatariCC.setFetchDelay(500);
		lComboBoxDestinatariCC.setIcons(filtroDestinatariCCItem);
		lComboBoxDestinatariCC.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				return StringUtil.asHTML((String) lComboBoxDestinatariCC.getValue());
			}
		});
		lComboBoxDestinatariCC.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				
				lComboBoxDestinatariCC.validate();
			}
		});
		lComboBoxDestinatariCC.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
				 *  
				 */
				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD);
			}
		});
		
		ListGridField destinatariPrefNameField = new ListGridField("nomeContatto");
		destinatariPrefNameField.setWidth("100%");
		destinatariPrefNameField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (record != null) {
					String res = null;
					String nomeContatto = record.getAttribute("descVoceRubrica") != null ? record.getAttributeAsString("descVoceRubrica") : null;
					if (record.getAttributeAsString("tipoIndirizzo") != null && "C".equals(record.getAttributeAsString("tipoIndirizzo"))) {
						res = buildIconHtml("coccarda.png", nomeContatto);
						return res;
					} else if (record.getAttributeAsString("tipoIndirizzo") != null && "O".equals(record.getAttributeAsString("tipoIndirizzo"))) { 
						res = buildIconHtml("mail/PEO.png", nomeContatto);
						return res;
					} else {
						res = buildIconHtml("mail/mailingList.png", nomeContatto);
						return res;
					}
				}
				return null;
			}
		});
		lComboBoxDestinatariCC.setPickListFields(destinatariPrefNameField);
		
		ListGrid destinatariPickListProperties = new ListGrid();
		destinatariPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		destinatariPickListProperties.setShowHeader(false);
		destinatariPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				event.cancel();
				selezionaDestinatarioSecondario(event.getRecord(),true);
			}
		});
		destinatariPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				
				String nome = lComboBoxDestinatariCC != null && lComboBoxDestinatariCC.getValue() != null ? (String) lComboBoxDestinatariCC.getValue() : null;
				GWTRestDataSource lComboBoxDestinatariCCDS = (GWTRestDataSource) lComboBoxDestinatariCC.getOptionDataSource();
				lComboBoxDestinatariCCDS.addParam("destinatario", nome);
				
				populateFiltriAutoCompletamento(lComboBoxDestinatariCCDS, getValuesAsRecord().getAttributeAsRecord("recordFiltriDestinatariCC"));
				
				lComboBoxDestinatariCC.setOptionDataSource(lComboBoxDestinatariCCDS);
				lComboBoxDestinatariCC.invalidateDisplayValueCache();
			}
		});
		lComboBoxDestinatariCC.setPickListProperties(destinatariPickListProperties);
	}
	
	private void creaFiltroDestinatariCCN() {
		
		filtroDestinatariCCNItem = new FormItemIcon();
		filtroDestinatariCCNItem.setSrc("postaElettronica/filtro_destinatari_off.png");  
		filtroDestinatariCCNItem.setWidth(16);  
		filtroDestinatariCCNItem.setHeight(16);  
		filtroDestinatariCCNItem.setInline(true);  
		filtroDestinatariCCNItem.setName("filtroDestinatariCCN");
		filtroDestinatariCCNItem.setPrompt("Filtro sulla scelta destinatari");
		filtroDestinatariCCNItem.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent  event) {
				new SceltaDestinatarioWindow("Filtro sulla scelta destinatari",new Record(_this.getValues()).getAttributeAsRecord("recordFiltriDestinatariCCN")) {
					
					@Override
					public void salvaFiltri(Record record) {
						
						if(filtroPresente(record)) {
							filtroDestinatariCCNItem.setSrc("postaElettronica/filtro_destinatari_on.png");
						} else {
							filtroDestinatariCCNItem.setSrc("postaElettronica/filtro_destinatari_off.png");
						}
						
						setValue("recordFiltriDestinatariCCN", record);
						_this.markForRedraw();
					}
				};
			}
		});
//		filtroDestinatariCCNItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				/*
//				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
//				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita
//				 *  
//				 */
//				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD
//						|| isMittentePec());
//			}
//		});
	}
	
	private void createDestinaratiCCN(CustomValidator validator){
		
		/*
		 *  Destinatari CCN
		 */
		
		GWTRestDataSource proposteDestinatariCCNDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatariCCN = new ComboBoxItem("destinatariCCN", I18NUtil.getMessages().invionotificainteropform_destinatariCCNItem_title());
		lComboBoxDestinatariCCN.setValueField("indirizzoEmail");
		lComboBoxDestinatariCCN.setDisplayField("indirizzoEmail");
		lComboBoxDestinatariCCN.setShowPickerIcon(false);
		lComboBoxDestinatariCCN.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatariCCN.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatariCCN.setColSpan(8);
		lComboBoxDestinatariCCN.setEndRow(false);
		lComboBoxDestinatariCCN.setStartRow(false);
		lComboBoxDestinatariCCN.setValidators(validator);
		lComboBoxDestinatariCCN.setAutoFetchData(false);
		lComboBoxDestinatariCCN.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatariCCN.setAddUnknownValues(true);
		lComboBoxDestinatariCCN.setValidateOnChange(false);
		lComboBoxDestinatariCCN.setOptionDataSource(proposteDestinatariCCNDS);
		lComboBoxDestinatariCCN.setFetchDelay(500);
		lComboBoxDestinatariCCN.setIcons(filtroDestinatariCCNItem);
		lComboBoxDestinatariCCN.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {

				return StringUtil.asHTML((String) lComboBoxDestinatariCCN.getValue());
			}
		});
		lComboBoxDestinatariCCN.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				
				lComboBoxDestinatariCCN.validate();
			}
		});
		lComboBoxDestinatariCCN.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				/*
				 * Deve essere visualizzata in tutti i casi a meno che il valore di tipologiaMail non sia RISPOSTA o INVIO_UD
				 * In questo caso vuol dire che la finestra è stata visualizzata dal Gestore dei modelli in questa modalita  
				 */
				return !(tipologiaMailGestioneModelli == TipologiaMail.RISPOSTA || tipologiaMailGestioneModelli == TipologiaMail.INVIO_UD
						|| isMittentePec());
			}
		});
		
		ListGridField destinatariPrefNameField = new ListGridField("nomeContatto");
		destinatariPrefNameField.setWidth("100%");
		destinatariPrefNameField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (record != null) {
					String res = null;
					String nomeContatto = record.getAttribute("descVoceRubrica") != null ? record.getAttributeAsString("descVoceRubrica") : null;
					if (record.getAttributeAsString("tipoIndirizzo") != null && "C".equals(record.getAttributeAsString("tipoIndirizzo"))) {
						res = buildIconHtml("coccarda.png", nomeContatto);
						return res;
					} else if (record.getAttributeAsString("tipoIndirizzo") != null && "O".equals(record.getAttributeAsString("tipoIndirizzo"))) { 
						res = buildIconHtml("mail/PEO.png", nomeContatto);
						return res;
					} else {
						res = buildIconHtml("mail/mailingList.png", nomeContatto);
						return res;
					}
				}
				return null;
			}
		});
		lComboBoxDestinatariCCN.setPickListFields(destinatariPrefNameField);
		
		ListGrid destinatariPickListProperties = new ListGrid();
		destinatariPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		destinatariPickListProperties.setShowHeader(false);
		destinatariPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				event.cancel();
				selezionaDestinatarioSecondario(event.getRecord(),false);
			}
		});
		destinatariPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				
				String nome = lComboBoxDestinatariCCN != null && lComboBoxDestinatariCCN.getValue() != null ? (String) lComboBoxDestinatariCCN.getValue() : null;
				GWTRestDataSource lComboBoxDestinatariCCNDS = (GWTRestDataSource) lComboBoxDestinatariCCN.getOptionDataSource();
				lComboBoxDestinatariCCNDS.addParam("destinatario", nome);
				
				populateFiltriAutoCompletamento(lComboBoxDestinatariCCNDS, getValuesAsRecord().getAttributeAsRecord("recordFiltriDestinatariCCN"));
				
				lComboBoxDestinatariCCN.setOptionDataSource(lComboBoxDestinatariCCNDS);
				lComboBoxDestinatariCCN.invalidateDisplayValueCache();
			}
		});
		lComboBoxDestinatariCCN.setPickListProperties(destinatariPickListProperties);
	}

	@Override
	public void editNewRecord() {
		super.editNewRecord();
		flgSalvaInviatiItem.setValue(getFlgSalvaInviatiDefaultValue());
		showDestinatariCCN();
	}

	@Override
	public void editNewRecord(Map initialValues) {

		// Se arriva un mittente da preimpostare lo tolgo dall'editNewRecord, lo devo preimpostare al momento della fetch
		String mittentePreimpostato = initialValues.get("mittente") != null ? initialValues.get("mittente").toString() : null;
		initialValues.remove("mittente");
		super.editNewRecord(initialValues);
		if (!initialValues.containsKey("salvaInviati")) {
			flgSalvaInviatiItem.setValue(getFlgSalvaInviatiDefaultValue());
		}
		// Se ho un mittente preimpostato lo imposto, altrimenti prendo il default da db
		if(mittentePreimpostato != null && !"".equals(mittentePreimpostato)){
			setMittenteIniziale(mittentePreimpostato);
		} else {
			AdvancedCriteria mittenteDefaultCriteria = new AdvancedCriteria();
			mittenteDefaultCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
			mittenteDefaultDS.fetchData(mittenteDefaultCriteria, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record[] data = response.getData();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS && data.length != 0) {
						String mittenteDefault = data[0].getAttributeAsString("value");
						setMittenteIniziale(mittenteDefault);
					}
				}
			});
		}
		showDestinatariCCN();
	}
	
	public boolean getFlgSalvaInviatiDefaultValue() {
		return AurigaLayout.getParametroDBAsBoolean("DEFAULT_SAVE_SENT_EMAIL");
	}

	private void manageEditViewerBody(){
		String body = "";
		if ("text".equals(style.getValueAsString())) {
			body = lTextAreaItemBody.getValueAsString();
		} else {
			body = (String) lRichTextItemBody.getValue();
		}
		
		final EditorEmailWindow editorEmailWindow = new EditorEmailWindow(I18NUtil.getMessages().posta_elettronica_editor_view_title_new_message(),body){
			
			@Override
			public void manageOnCloseClick() {
				String body = getCurrentBody();
				if ("text".equals(style.getValueAsString())) {
					lTextAreaItemBody.setValue(body);
				} else {
					lRichTextItemBody.setValue(body);
				}
				markForDestroy();
			};
		};
		editorEmailWindow.show();
	}

	private void setMittenteIniziale(final String mittenteIniziale) {

		if (mittenteIniziale != null && !"".equals(mittenteIniziale)) {
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
								// Refresh del form per eseguire l'analisi della mail di default (PEC o PEO)
								markForRedraw();
								return;
							}
						}
						if (!trovato) {
							lSelectItemMittente.setValue("");
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().postaElettronica_nuovomessaggio_mittente_default_warning(), "", MessageType.WARNING));
						}
					}
				}
			});
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
			if(AurigaLayout.getParametroDBAsBoolean("CANCELLA_TAG_VUOTI_CORPO_MAIL")){
				try {
					Record recordBody = new Record();
					recordBody.setAttribute("bodyHtml", lRichTextItemBody.getValue() != null ? ((String)lRichTextItemBody.getValue()) : "");					
					// Chiamata al datasource per controllare il contenuto del corpo della mail
					GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
					corpoMailDataSource.performCustomOperation("checkIfBodyIsEmpty", recordBody, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								
								Record record = response.getData()[0];	
								bodyHtml = record.getAttribute("bodyHtml");
								bodyText = record.getAttribute("bodyText");	
								lTextAreaItemBody.setValue(bodyText);
								
								//Nascondo la text area per l'html
								lRichTextItemBody.hide();
								
								//Visualizzo la text area
								lTextAreaItemBody.show();								
								markForRedraw();
							}
						}
					}, new DSRequest());
	
				} catch (Exception e) {	
					
					/*
					 * In caso di errori  
					 */
					lTextAreaItemBody.setValue(bodyText);	
					
					//Nascondo la text area per l'html
					lRichTextItemBody.hide();
					
					//Visualizzo la text area
					lTextAreaItemBody.show();					
					markForRedraw();	
				}
			} else {
				//Imposto nella text il valore del corpo html
				bodyHtml = lRichTextItemBody.getValue().toString();
				bodyText = lTextAreaItemBody.getValue().toString();
				
				lTextAreaItemBody.setValue(bodyText);
				
				//Nascondo la text area per l'html
				lRichTextItemBody.hide();
				
				//Visualizzo la text area
				lTextAreaItemBody.show();			
				markForRedraw();
			}
		} else {
			/**
			 * Rev 63523
			 * Sistemato il problema per cui non visualizzava correttamente il corpo html nel momento del cambio tra testo semplice e html (in chrome) e in ie generava un eccezione
			 */
			//bodyHtml = lRichTextItemBody.getValue().toString();
			//bodyText = lTextAreaItemBody.getValue().toString();
			
			//lRichTextItemBody.setValue(bodyHtml);
			//lRichTextItemBody.setValue(lTextAreaItemBody.getValue());
			//lTextAreaItemBody.clearValue();
			
			//Nascondo la text area per l'html
			lTextAreaItemBody.hide();
			
			//Visualizzo la text area
			lRichTextItemBody.show();		
			markForRedraw();
		}
	}

	// Appende a str la stringa strToAppend, preceduta dal carattere ; se questo
	// non è presente alla fine di str
	public String appendIndirizzoEmail(String str, String strToAppend) {
		String res = "";
		if (str == null || str.equals("")) {
			res = strToAppend;
		} else if (strToAppend != null && !strToAppend.equals("") && !str.toLowerCase().contains(strToAppend.toLowerCase())) {
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
		if (str != null && !str.equals("") && strToRemove != null && !strToRemove.equals("") && str.toLowerCase().contains(strToRemove.toLowerCase())) {
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
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
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
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
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
										setValue(fieldName, removeIndirizzoEmail(getValueAsString(fieldName), indirizzo));
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
		if ((getValuesManager().getValue("textHtml") != null) && (getValuesManager().getValue("textHtml").equals("text"))) {
			lRichTextItemBody.clearValue();
			lRichTextItemBody.hide();
			lTextAreaItemBody.show();
		} else {
			lTextAreaItemBody.clearValue();
			lTextAreaItemBody.hide();
			lRichTextItemBody.show();

		}
		showDestinatariCCN();
		
		markForRedraw();
	}

	public boolean validateMittente() {
		return lSelectItemMittente.validate();
	}

	public ReplicableItem getAttachmentReplicableItem() {
		return lAttachmentItem;
	}

	/**
	 * Metodo che ritorna un valore booleano che indica se visualizzare o meno la checkbox "Conferma di Lettura"
	 * @return true se si tratta di posta ordinaria (PEO) e il flag HIDE_CONF_LETTURA_MAIL sul DB è uguale a false
	 * @return false se si tratta di posta certificata (PEC) o il flag HIDE_CONF_LETTURA_MAIL sul DB è uguale a true
	 */
	private boolean checkShowConfermaDiLettura() {
		if (AurigaLayout.getParametroDBAsBoolean("HIDE_CONF_LETTURA_MAIL")) {
			return false;
		} else {
			return !isMittentePec();
		}
	}

	private boolean isMittentePec() {
		if (lSelectItemMittente != null && lSelectItemMittente.getDisplayValue() != null 
			&& !"".equals(lSelectItemMittente.getDisplayValue()) && 
			lSelectItemMittente.getDisplayValue().contains("PEC") ) {
			return true;
		} else {
			return false;
		}
	} 
	
	public String getStyleText(){
		return (String) style.getValue();
	}
	
	private void showDestinatariCCN() {
		if(isMittentePec()){
			lComboBoxDestinatariCCN.hide();
			lookupRubricaEmailDestinatariCCNImgButton.hide();
		} else {
			lComboBoxDestinatariCCN.show();
			lookupRubricaEmailDestinatariCCNImgButton.show();
		}
	}
	
	private void populateFiltriAutoCompletamento(GWTRestDataSource gWTRestDataSource, Record record) {
		
		String tipoIndirizzo = "";
		String tipoDestinatario = "";
		String idSoggetto = "";
		if(record != null) {
			tipoIndirizzo = record.getAttributeAsString("tipoIndirizzo") != null ?
					record.getAttributeAsString("tipoIndirizzo") : "";
			tipoDestinatario = record.getAttributeAsString("tipoDestinatario") != null ?
					record.getAttributeAsString("tipoDestinatario") : "";
			if(record.getAttributeAsRecordList("listaMittenti") != null &&
					!record.getAttributeAsRecordList("listaMittenti").isEmpty()) {
				idSoggetto = record.getAttributeAsRecordList("listaMittenti").get(0).getAttribute("idSoggetto");
			}
		}
		gWTRestDataSource.addParam("tipoIndirizzo", tipoIndirizzo);
		gWTRestDataSource.addParam("tipoDestinatario", tipoDestinatario);
		gWTRestDataSource.addParam("idSoggetto", idSoggetto);
	}
	
	private Boolean filtroPresente(Record record) {
		Boolean verify = false;
		if(record != null) {
			if(!"ALL".equalsIgnoreCase(record.getAttribute("tipoIndirizzo"))) {
				verify = true;
			} else if(!"ALL".equalsIgnoreCase(record.getAttribute("tipoDestinatario"))) {
				verify = true;
			}
		}
		return verify;
	}
	
	public Boolean filtroPresenteFromModello(Map initialValues) {
		
		Boolean verifyPrimari = false;
		Boolean verifyCC = false;
		Boolean verifyCCN = false;
		if(initialValues != null && !initialValues.isEmpty()) {
			if(initialValues.get("recordFiltriDestinatari") != null) {				
				Record destP = new Record(initialValues).getAttributeAsRecord("recordFiltriDestinatari");
				if(destP.getAttribute("tipoIndirizzo") != null && !"ALL".equalsIgnoreCase(destP.getAttribute("tipoIndirizzo"))) {
					verifyPrimari = true;
				} else if(destP.getAttribute("tipoDestinatario") != null && !"ALL".equalsIgnoreCase(destP.getAttribute("tipoDestinatario"))) {
					verifyPrimari = true;
				}
				if(verifyPrimari) {
					if(filtroDestinatariItem != null) {
						filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_on.png");
					}
				} else {
					if(filtroDestinatariItem != null) {
						filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_off.png");
					}
				}				
			} else {
				if(filtroDestinatariItem != null) {
					filtroDestinatariItem.setSrc("postaElettronica/filtro_destinatari_off.png");
				}
			}
			
			if(initialValues.get("recordFiltriDestinatariCC") != null) {				
				Record destCC = new Record(initialValues).getAttributeAsRecord("recordFiltriDestinatariCC");
				if(destCC.getAttribute("tipoIndirizzo") != null && !"ALL".equalsIgnoreCase(destCC.getAttribute("tipoIndirizzo"))) {
					verifyCC = true;
				} else if(destCC.getAttribute("tipoDestinatario") != null && !"ALL".equalsIgnoreCase(destCC.getAttribute("tipoDestinatario"))) {
					verifyCC = true;
				}
				if(verifyCC) {
					if(filtroDestinatariCCItem != null) {						
						filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_on.png");
					}
				} else {
					if(filtroDestinatariCCItem != null) {						
						filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_off.png");
					}
				}
			} else {
				if(filtroDestinatariCCItem != null) {						
					filtroDestinatariCCItem.setSrc("postaElettronica/filtro_destinatari_off.png");
				}
			} 
			
			if(initialValues.get("recordFiltriDestinatariCCN") != null) {				
				Record destCCN = new Record(initialValues).getAttributeAsRecord("recordFiltriDestinatariCCN");
				if(destCCN.getAttribute("tipoIndirizzo") != null && !"ALL".equalsIgnoreCase(destCCN.getAttribute("tipoIndirizzo"))) {
					verifyCCN = true;
				} else if(destCCN.getAttribute("tipoDestinatario") != null && !"ALL".equalsIgnoreCase(destCCN.getAttribute("tipoDestinatario"))) {
					verifyCCN = true;
				}
				if(verifyCCN) {
					if(filtroDestinatariCCNItem != null) {						
						filtroDestinatariCCNItem.setSrc("postaElettronica/filtro_destinatari_on.png");
					}
				} else {
					if(filtroDestinatariCCNItem != null) {						
						filtroDestinatariCCNItem.setSrc("postaElettronica/filtro_destinatari_off.png");
					}
				}
			} else {
				if(filtroDestinatariCCNItem != null) {						
					filtroDestinatariCCNItem.setSrc("postaElettronica/filtro_destinatari_off.png");
				}
			}
		}
		return verifyPrimari || verifyCC || verifyCCN;
	}
}