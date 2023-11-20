/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.events.HiddenValidationErrorsEvent;
import com.smartgwt.client.widgets.form.events.HiddenValidationErrorsHandler;
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
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
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
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class InvioUDMailForm extends DynamicForm {

	private static final int COMPONENT_WIDTH = 900;
	
	private InvioUDMailForm _this = this;

	private ValuesManager lValuesManager;
	private String tipoMail;
	private String bodyHtml = "";
	private String bodyText = "";	

	private SelectItem lSelectItemMittente;
	private ImgButtonItem salvaMittenteDefaultImgButton;
	private String mittenteDefaultPrefItem;
	private String casellaIsPec;
	
	private HiddenItem recordFiltriDestinatariItem;
	private FormItemIcon filtroDestinatariItem;
	private ComboBoxItem lComboBoxDestinatari;
	private ImgButtonItem lookupRubricaEmailButtonDestinatari;
	
	private HiddenItem recordFiltriDestinatariCCItem;
	private FormItemIcon filtroDestinatariCCItem;
	private ComboBoxItem lComboBoxDestinatariCC;
	private ImgButtonItem lookupRubricaEmailButtonDestinatariCC;
	
	private HiddenItem recordFiltriDestinatariCCNItem;
	private FormItemIcon filtroDestinatariCCNItem;
	private ComboBoxItem lComboBoxDestinatariCCN;
	private ImgButtonItem lookupRubricaEmailButtonDestinatariCCN;
	
	private TextItem lTextItemOggetto;
	private CheckboxItem lCheckboxItemSalvaInviati;
	private CheckboxItem lCheckboxRichiestaConferma;
	private SpacerItem lSpacerRichiestaConfermaItem;
	private SpacerItem lSpacerConfermaLetturaItem;
	private CheckboxItem lCheckboxConfermaLettura;
	private CheckboxItem flgInvioSeparato;
	private RadioGroupItem style; 
	private TextAreaItem lTextAreaItemBody;
	private RichTextItem lRichTextItemBody;
	private TextAreaItem lTextAreaItemAvvertimenti;
	private ImgButtonItem editCorpoMailImgButton;
	
	private InvioUDAttachmentItem lAttachmentItem;
	private InvioUDDestinatariItem lInvioUDDestinatariItem;
	
	private HiddenItem idCasellaMittenteItem;
	private HiddenItem lHiddenItemInvioMailFromAtti;
	private HiddenItem lHiddenItemConfermaLetturaShow;
	private HiddenItem lHiddenItemSegnaturaPresente;
	private HiddenItem lHiddenItemIdUD;
	private HiddenItem lHiddenItemFlgTipoProv;
	private HiddenItem lHiddenItemTipoMail;
	private HiddenItem lHiddenItemIdMailPartenza;
	
	private Boolean invioMailFromAtti;
	
	public InvioUDMailForm(String pTipoMail, Boolean invioMailFromAtti) {

		setOverflow(Overflow.AUTO);
		setWidth100();
		setHeight100();

		this.invioMailFromAtti = invioMailFromAtti;

		setWrapItemTitles(false);
		setNumCols(20);
		setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, "*", "*");

		this.tipoMail = pTipoMail;  // Identifica il tipo di Maschera da caricare (Invio Email, Invio Interop) e NON il tipo di Mail del Mittente.

		casellaIsPec = null;
		
		createHiddenItem();

		createMittente();
		
		// Creazione filtro destinatari
		creaFiltroDestinatariPrimari();
		
		createDestinatariPrimari(getValidatorEmail());
		
		// Creazione filtro destinatari
		creaFiltroDestinatariCC();
		
		createDestinatariCC(getValidatorEmail());
		
		// Creazione filtro destinatari
		creaFiltroDestinatariCCN();
		
		createDestinatariCCN(getValidatorEmail());

		createOggetto();

		createAttachment();

		SpacerItem lSpacerBodyItem = new SpacerItem();
		lSpacerBodyItem.setColSpan(1);
		lSpacerBodyItem.setStartRow(true);
		
		createBodyText();

		createAvvertimenti();
		
		createSalvaInviati();

		createRichiestaConferma();

		createSpacerConfermaLettura();
		
		createFlgConfermaLettura();

		createFlgInvioSeparato();

		createInvioUDDDestinatari();

		lValuesManager = new ValuesManager();
		setValuesManager(lValuesManager);

		setFields(
			idCasellaMittenteItem,lSelectItemMittente,salvaMittenteDefaultImgButton,lHiddenItemInvioMailFromAtti,
			lInvioUDDestinatariItem, 
			recordFiltriDestinatariItem,recordFiltriDestinatariCCItem,recordFiltriDestinatariCCNItem,
			lComboBoxDestinatari, lookupRubricaEmailButtonDestinatari,
			lComboBoxDestinatariCC, lookupRubricaEmailButtonDestinatariCC,
			lComboBoxDestinatariCCN, lookupRubricaEmailButtonDestinatariCCN,
			lTextItemOggetto, 
			lAttachmentItem,style,lCheckboxConfermaLettura,flgInvioSeparato, 
			lSpacerBodyItem,
			lTextAreaItemBody,
			lRichTextItemBody,
			editCorpoMailImgButton,
			lSpacerRichiestaConfermaItem,lCheckboxRichiestaConferma, 
			lTextAreaItemAvvertimenti,
			lHiddenItemIdUD,lHiddenItemFlgTipoProv,lHiddenItemTipoMail, 
			lHiddenItemIdMailPartenza,lHiddenItemSegnaturaPresente,
			lHiddenItemConfermaLetturaShow
		);
	}

	/**
	 * Visualizzata solo in presenza di PEC
	 */
	private void createInvioUDDDestinatari() {
		
		lInvioUDDestinatariItem = new InvioUDDestinatariItem();
		lInvioUDDestinatariItem.setName("destinatariPec");
		lInvioUDDestinatariItem.setTitle(I18NUtil.getMessages().invioudmail_detail_lInvioUDDestinatariItem_title());
		lInvioUDDestinatariItem.setWrapTitle(false);
		lInvioUDDestinatariItem.setStartRow(true);
		lInvioUDDestinatariItem.setEndRow(true);
		lInvioUDDestinatariItem.setCanEdit(true);
		lInvioUDDestinatariItem.setWidth(COMPONENT_WIDTH);
		lInvioUDDestinatariItem.setTitleColSpan(1);
		lInvioUDDestinatariItem.setColSpan(8);
		lInvioUDDestinatariItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoMail != null && tipoMail.equals("PEC");
			}
		});
	}

	private void createFlgInvioSeparato() {
		
		flgInvioSeparato = new CheckboxItem("flgInvioSeparato", I18NUtil.getMessages().inviomailform_flgInvioSeparato_title());
		flgInvioSeparato.setWrapTitle(true);
		flgInvioSeparato.setColSpan(1);
		flgInvioSeparato.setWidth(100);
		flgInvioSeparato.setEndRow(true);
	}

	private void createFlgConfermaLettura() {
		
		lCheckboxConfermaLettura = new CheckboxItem("confermaLettura", I18NUtil.getMessages().invioudmail_detail_lCheckboxConfermaLettura_title());
		lCheckboxConfermaLettura.setWrapTitle(false);
		lCheckboxConfermaLettura.setWidth(100);
	}

	private void createSpacerConfermaLettura() {
		
		lSpacerConfermaLetturaItem = new SpacerItem();
		lSpacerConfermaLetturaItem.setStartRow(true);
	}

	private void createRichiestaConferma() {
		//ATTENZIONE: Questo NON è il flag di conferma lettura
		lCheckboxRichiestaConferma = new CheckboxItem("richiestaConferma", I18NUtil.getMessages().invioudmail_detail_lCheckboxRichiestaConferma_title());
		lCheckboxRichiestaConferma.setWrapTitle(false);
		lCheckboxRichiestaConferma.setColSpan(1);
		lCheckboxRichiestaConferma.setWidth("*");
		lCheckboxRichiestaConferma.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean segnaturaPresente = lHiddenItemSegnaturaPresente.getValue() != null ? (Boolean) lHiddenItemSegnaturaPresente.getValue() : false;
				return tipoMail != null && tipoMail.equals("PEC") && segnaturaPresente;
			}
		});
		
		lSpacerRichiestaConfermaItem = new SpacerItem();
		lSpacerRichiestaConfermaItem.setColSpan(1);
		lSpacerRichiestaConfermaItem.setStartRow(true);
		lSpacerRichiestaConfermaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean segnaturaPresente = lHiddenItemSegnaturaPresente.getValue() != null ? (Boolean) lHiddenItemSegnaturaPresente.getValue() : false;
				return tipoMail != null && tipoMail.equals("PEC") && segnaturaPresente;
			}
		});
	}

	private void createSalvaInviati() {
		
		SpacerItem lSpacerSalvaInviatiItem = new SpacerItem();
		lSpacerSalvaInviatiItem.setColSpan(1);
		lSpacerSalvaInviatiItem.setStartRow(true);
		lSpacerSalvaInviatiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return false;
			}
		});

		lCheckboxItemSalvaInviati = new CheckboxItem("salvaInviati", I18NUtil.getMessages().invioudmail_detail_lCheckboxItemSalvaInviati_title());
		lCheckboxItemSalvaInviati.setWrapTitle(false);
		lCheckboxItemSalvaInviati.setColSpan(1);
		lCheckboxItemSalvaInviati.setWidth("*");
		lCheckboxItemSalvaInviati.setStartRow(true);
		lCheckboxItemSalvaInviati.setEndRow(true);
		lCheckboxItemSalvaInviati.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return false;
			}
		});
	}

	private void createAvvertimenti() {
		// avvertimenti
		lTextAreaItemAvvertimenti = new TextAreaItem("avvertimenti",
				"&nbsp;&nbsp;&nbsp;<img src=\"images/warning.png\" style=\"vertical-align:bottom\"/>&nbsp;<font color=\"red\"><b>Avvertimenti<b/></font>");
		lTextAreaItemAvvertimenti.setStartRow(true);
		lTextAreaItemAvvertimenti.setEndRow(true);
		lTextAreaItemAvvertimenti.setColSpan(8);
		lTextAreaItemAvvertimenti.setHeight(400);
		lTextAreaItemAvvertimenti.setWidth(COMPONENT_WIDTH);
		lTextAreaItemAvvertimenti.setCanEdit(false);
		lTextAreaItemAvvertimenti.setVisible(true);
		lTextAreaItemAvvertimenti.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !((String) value).trim().equals(""); 
			}
		});
	}

	private void createBodyText() {
		// Opzioni Testo
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		styleMap.put("text", I18NUtil.getMessages().invionotificainteropform_styleMap_TEXT_value());
		styleMap.put("html", I18NUtil.getMessages().invionotificainteropform_styleMap_HTML_value());

		style = new RadioGroupItem("textHtml");
		style.setDefaultValue("html");
		style.setTitle(I18NUtil.getMessages().invionotificainteropform_styleItem_title());
		style.setValueMap(styleMap);
		style.setVertical(false);
		style.setWrap(false);
		style.setWidth(250);
		style.setStartRow(true);
		style.setEndRow(false);
		style.setColSpan(1);
		style.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				manageChangeTextHtml(event);
			}
		});
		
		// Casella testo TEXT
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
		lTextAreaItemBody.setIconVAlign(VerticalAlignment.CENTER);

		// Casella testo HTML
		lRichTextItemBody = new RichTextItem("bodyHtml");
		lRichTextItemBody.setDefaultValue("");
		lRichTextItemBody.setVisible(true);
		lRichTextItemBody.setShowTitle(false);
		lRichTextItemBody.setHeight(600);
		lRichTextItemBody.setWidth(COMPONENT_WIDTH);
		lRichTextItemBody.setColSpan(8);
		lRichTextItemBody.setStartRow(false);
		lRichTextItemBody.setEndRow(false);
		lRichTextItemBody.setIconVAlign(VerticalAlignment.CENTER);	
		lRichTextItemBody.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");

		editCorpoMailImgButton = new ImgButtonItem("viewerCorpo", "buttons/view_editor.png", "Edit corpo");
		editCorpoMailImgButton.setShowTitle(false);
		editCorpoMailImgButton.setAlwaysEnabled(true);
		editCorpoMailImgButton.setStartRow(false);
		editCorpoMailImgButton.setWidth(10);
		editCorpoMailImgButton.setColSpan(1);
		editCorpoMailImgButton.setValueIconSize(32);
		editCorpoMailImgButton.setPrompt("Edit corpo");
		editCorpoMailImgButton.setVAlign(VerticalAlignment.TOP);
		editCorpoMailImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageEditViewerBody();
			}
		});
	}

	private void createAttachment() {
		// file attachment
		lAttachmentItem = new InvioUDAttachmentItem(tipoMail, new AttachmentDownloadAction() {

			@Override
			public void manageIconClick(String display, String uri, boolean remoteUri) {
				if (uri == null && display.equalsIgnoreCase("segnatura.xml")) {
					// Sono in segnatura
					downloadSegnatura(display);
				} else
					_manageIconClick(display, uri, remoteUri);
			}
		});
		lAttachmentItem.setName("attach");
		lAttachmentItem.setTitle("Allegati");
		lAttachmentItem.setTitleColSpan(1);
		lAttachmentItem.setWidth(1);
		lAttachmentItem.setStartRow(true);
		lAttachmentItem.setColSpan(8);
	}

	private void createOggetto() {
		// oggetto
		lTextItemOggetto = new TextItem("oggetto", I18NUtil.getMessages().invioudmail_detail_lTextItemOggetto_title());
		lTextItemOggetto.setTitleColSpan(1);
		lTextItemOggetto.setStartRow(true);
		lTextItemOggetto.setEndRow(true);
		lTextItemOggetto.setColSpan(8);
		lTextItemOggetto.setWidth(COMPONENT_WIDTH);
	}
	
	/**
	 * Validator per il campo email
	 */
	private CustomValidator getValidatorEmail() {
		/**
		 * Validator per il campo email
		 */
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
//				return tipoMail != null && tipoMail.equals("PEO");
//			}
//		});
	}
	
	/**
	 * Destinatari principali
	 */
	private void createDestinatariPrimari(CustomValidator lCustomValidator) {
		
		
		GWTRestDataSource proposteDestinatariPrimariDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatari = new ComboBoxItem("destinatari", I18NUtil.getMessages().invioudmail_detail_lTextItemDestinatari_title());
		lComboBoxDestinatari.setColSpan(8);
		lComboBoxDestinatari.setStartRow(true);
		lComboBoxDestinatari.setEndRow(false);
		lComboBoxDestinatari.setShowPickerIcon(false);
		lComboBoxDestinatari.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatari.setRequired(true);
		lComboBoxDestinatari.setValueField("indirizzoEmail");
		lComboBoxDestinatari.setDisplayField("indirizzoEmail");
		lComboBoxDestinatari.setTextBoxStyle(it.eng.utility.Styles.textItem);
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
		lComboBoxDestinatari.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoMail != null && tipoMail.equals("PEO");
			}
		});
		lComboBoxDestinatari.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return formItem.getForm().getValueAsString("tipoMail") != null && formItem.getForm().getValueAsString("tipoMail").equals("PEO");
			}
		}), lCustomValidator);
		lComboBoxDestinatari.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				
				lComboBoxDestinatari.validate();
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
		
		// Bottone lookup per aprire la RUBRICA EMAIL
		lookupRubricaEmailButtonDestinatari = new ImgButtonItem("lookupRubricaEmailButtonDestinatari", "lookup/rubricaemail.png",
				I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailButtonDestinatari.setStartRow(false);
		lookupRubricaEmailButtonDestinatari.setEndRow(true);
		lookupRubricaEmailButtonDestinatari.setColSpan(1);
		lookupRubricaEmailButtonDestinatari.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioUDMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioUDMailMultiLookupRubricaEmailPopup("destinatari");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailButtonDestinatari.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoMail != null && tipoMail.equals("PEO");
			}
		});
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
//				return tipoMail != null && tipoMail.equals("PEO");
//			}
//		});
	}

	/**
	 *  Destinatario CC
	 */
	private void createDestinatariCC(CustomValidator lCustomValidator) {
		
		GWTRestDataSource proposteDestinatariCCDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatariCC = new ComboBoxItem("destinatariCC", I18NUtil.getMessages().invioudmail_detail_lTextItemDestinatariCC_title());
		lComboBoxDestinatariCC.setColSpan(8);
		lComboBoxDestinatariCC.setStartRow(false);
		lComboBoxDestinatariCC.setEndRow(false);
		lComboBoxDestinatariCC.setShowPickerIcon(false);
		lComboBoxDestinatariCC.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatariCC.setValueField("indirizzoEmail");
		lComboBoxDestinatariCC.setDisplayField("indirizzoEmail");
		lComboBoxDestinatariCC.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatariCC.setValidators(lCustomValidator);
		lComboBoxDestinatariCC.setAutoFetchData(false);
		lComboBoxDestinatariCC.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatariCC.setAddUnknownValues(true);
		lComboBoxDestinatariCC.setValidateOnChange(false);
		lComboBoxDestinatariCC.setOptionDataSource(proposteDestinatariCCDS);
		lComboBoxDestinatariCC.setFetchDelay(500);
		lComboBoxDestinatariCC.setIcons(filtroDestinatariCCItem);
		lComboBoxDestinatariCC.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoMail != null && tipoMail.equals("PEO");
			}
		});
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
		
		// Bottone lookup per aprire la RUBRICA EMAIL
		lookupRubricaEmailButtonDestinatariCC = new ImgButtonItem("lookupRubricaEmailButtonDestinatariCC", "lookup/rubricaemail.png",
				I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailButtonDestinatariCC.setStartRow(false);
		lookupRubricaEmailButtonDestinatariCC.setEndRow(true);
		lookupRubricaEmailButtonDestinatariCC.setColSpan(1);
		lookupRubricaEmailButtonDestinatariCC.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioUDMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioUDMailMultiLookupRubricaEmailPopup("destinatariCC");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailButtonDestinatariCC.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoMail != null && tipoMail.equals("PEO");
			}
		});
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
//				return casellaIsPec != null && casellaIsPec.equals("false");
//			}
//		});
	}
	
	/**
	 *  Destinatario CCN
	 */
	private void createDestinatariCCN(CustomValidator lCustomValidator) {
		
		GWTRestDataSource proposteDestinatariCCNDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatariCCN = new ComboBoxItem("destinatariCCN", I18NUtil.getMessages().invioudmail_detail_lTextItemDestinatariCCN_title());
		lComboBoxDestinatariCCN.setColSpan(8);
		lComboBoxDestinatariCCN.setStartRow(false);
		lComboBoxDestinatariCCN.setEndRow(false);
		lComboBoxDestinatariCCN.setShowPickerIcon(false);
		lComboBoxDestinatariCCN.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatariCCN.setValueField("indirizzoEmail");
		lComboBoxDestinatariCCN.setDisplayField("indirizzoEmail");
		lComboBoxDestinatariCCN.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatariCCN.setValidators(lCustomValidator);
		lComboBoxDestinatariCCN.setAutoFetchData(false);
		lComboBoxDestinatariCCN.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatariCCN.setAddUnknownValues(true);
		lComboBoxDestinatariCCN.setValidateOnChange(false);
		lComboBoxDestinatariCCN.setOptionDataSource(proposteDestinatariCCNDS);
		lComboBoxDestinatariCCN.setFetchDelay(500);
		lComboBoxDestinatariCCN.setIcons(filtroDestinatariCCNItem);
		lComboBoxDestinatariCCN.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		lComboBoxDestinatariCCN.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return casellaIsPec != null && casellaIsPec.equals("false");
			}
		});
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
		
		// Bottone lookup per aprire la RUBRICA EMAIL
		lookupRubricaEmailButtonDestinatariCCN = new ImgButtonItem("lookupRubricaEmailButtonDestinatariCCN", "lookup/rubricaemail.png",
				I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailButtonDestinatariCCN.setStartRow(false);
		lookupRubricaEmailButtonDestinatariCCN.setEndRow(true);
		lookupRubricaEmailButtonDestinatariCCN.setColSpan(1);
		lookupRubricaEmailButtonDestinatariCCN.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioUDMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioUDMailMultiLookupRubricaEmailPopup("destinatariCCN");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailButtonDestinatariCCN.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return casellaIsPec != null && casellaIsPec.equals("false");
			}
		});
	}

	// mittente
	private void createMittente() {
		
		idCasellaMittenteItem = new HiddenItem("idCasellaMittente");
		
		lHiddenItemInvioMailFromAtti = new HiddenItem("invioMailFromAtti");
		
		lSelectItemMittente = new SelectItem("mittente", I18NUtil.getMessages().invioudmail_detail_lSelectItemMittente_title());
		lSelectItemMittente.setColSpan(8);
		lSelectItemMittente.setDisplayField("value");
		lSelectItemMittente.setValueField("key");
		lSelectItemMittente.setRequired(true);
		lSelectItemMittente.setAllowEmptyValue(false);
		lSelectItemMittente.setWidth(COMPONENT_WIDTH);
		lSelectItemMittente.setAddUnknownValues(false);
		lSelectItemMittente.setRejectInvalidValueOnChange(true);		
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		accounts.addParam("finalita", "INVIO");
		if (tipoMail != null && tipoMail.equals("PEC")) {
			accounts.addParam("tipoMail", tipoMail);
		}
		lSelectItemMittente.setOptionDataSource(accounts);
		lSelectItemMittente.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				if(invioMailFromAtti != null && invioMailFromAtti) {
					if (AurigaLayout.getParametroDBAsBoolean("HIDE_CONF_LETTURA_MAIL")) {
						lCheckboxConfermaLettura.setVisible(false);
						lComboBoxDestinatariCCN.setVisible(true);
						lookupRubricaEmailButtonDestinatariCCN.setVisible(true);
					} else {
						lHiddenItemTipoMail.setValue("PEO");
						lCheckboxConfermaLettura.setVisible(true);
						lComboBoxDestinatariCCN.setVisible(true);
						lookupRubricaEmailButtonDestinatariCCN.setVisible(true);
						// Per default viene settata la checkbox a false
						lHiddenItemConfermaLetturaShow.setValue(false);
						lCheckboxConfermaLettura.setValue(false);
					}
					markForRedraw();
				} else {
					String tipoAccount = "PEO";
					String mittenteCorrente = (String) (lValuesManager != null && !"".equals(lValuesManager.getValue("mittente")) ?
							lValuesManager.getValue("mittente") : null);
					if(mittenteCorrente != null) {
						if (event.getData().getLength() > 0) {
							for(int i=0; i < event.getData().getLength(); i++) {
								Record accountRecord = event.getData().get(i);
								if(accountRecord.getAttribute("key").equals(mittenteCorrente)) {
									if(accountRecord.getAttribute("value").contains("PEC")) {
										tipoAccount = "PEC";
									}
									lSelectItemMittente.setValue(accountRecord.getAttribute("key"));
									break;
								} else {
									lSelectItemMittente.setValue("");
								}
							}
						}
					}
					if (AurigaLayout.getParametroDBAsBoolean("HIDE_CONF_LETTURA_MAIL")) {
						lCheckboxConfermaLettura.setVisible(false);
						//Nascondo la combo dei destinatari CCN nel caso di un mittente pec
						if (tipoAccount != null && tipoAccount.contains("PEC")) {
							lComboBoxDestinatariCCN.setVisible(false);
							lookupRubricaEmailButtonDestinatariCCN.setVisible(false);
						} else {
							lComboBoxDestinatariCCN.setVisible(true);
							lookupRubricaEmailButtonDestinatariCCN.setVisible(true);
						}
					} else {
						//Nascondo la combo dei destinatari CCN nel caso di un mittente pec
						if (tipoAccount != null && tipoAccount.contains("PEC")) {
							lHiddenItemTipoMail.setValue("PEC"); 
							lCheckboxConfermaLettura.setVisible(false);
							lComboBoxDestinatariCCN.setVisible(false);
							lookupRubricaEmailButtonDestinatariCCN.setVisible(false);
						} else {
							lHiddenItemTipoMail.setValue("PEO");
							lCheckboxConfermaLettura.setVisible(true);
							lComboBoxDestinatariCCN.setVisible(true);
							lookupRubricaEmailButtonDestinatariCCN.setVisible(true);
						}
						// Per default viene settata la checkbox a false
						lHiddenItemConfermaLetturaShow.setValue(false);
						lCheckboxConfermaLettura.setValue(false);
					}
					markForRedraw();
				}
			}
		});
		lSelectItemMittente.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				lCheckboxConfermaLettura.setValue(false); // Ogni volta che si cambia mittente nel form il flag di Conferma lettura si deve resettare.
				
				manageCheckBoxConfermaLettura();
				
				manageComboBoxDestinatariCCN();
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
					if (mittenteDefault != null && !"".equals(mittenteDefault)) {
						mittenteDefaultPrefItem = mittenteDefault;
					}
				}
			}
		});
		
		//Inserimento del pulsante da affiancare alla select di selezione del mittente
		salvaMittenteDefaultImgButton = new ImgButtonItem("salvaMittenteDefault", "buttons/saveIn.png", I18NUtil.getMessages().invioudmail_detail_salvaMittenteDefault_title());
		salvaMittenteDefaultImgButton.setShowTitle(false);
		salvaMittenteDefaultImgButton.setAlwaysEnabled(true);
		salvaMittenteDefaultImgButton.setWidth(16);
		salvaMittenteDefaultImgButton.setValueIconSize(16);
		salvaMittenteDefaultImgButton.setEndRow(true);
		salvaMittenteDefaultImgButton.setColSpan(1);
		salvaMittenteDefaultImgButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_salvaMittenteDefault_title());
		salvaMittenteDefaultImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				if (lSelectItemMittente.getValueAsString() != null && !"".equals(lSelectItemMittente.getValueAsString())) {
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
							Layout.addMessage(
									new MessageBean(lSelectItemMittente.getValueAsString() + " salvato come mittente di default", "", MessageType.INFO));
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
		salvaMittenteDefaultImgButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				boolean isVisible = true;
//				if(invioMailFromAtti != null && invioMailFromAtti) {
//					isVisible = false;
//				}
//				return isVisible;
				return invioMailFromAtti == null || (invioMailFromAtti != null && !invioMailFromAtti);
			}
		});
	}

	private void createHiddenItem() {
				
		lHiddenItemSegnaturaPresente = new HiddenItem("segnaturaPresente");

		lHiddenItemIdUD = new HiddenItem("idUD");
		lHiddenItemFlgTipoProv = new HiddenItem("flgTipoProv");

		lHiddenItemTipoMail = new HiddenItem("tipoMail");

		lHiddenItemIdMailPartenza = new HiddenItem("idMailPartenza");
		
		lHiddenItemConfermaLetturaShow = new HiddenItem("confermaLetturaShow");
		
		/**
		 * FILTRI DESTINATARI
		 */
		recordFiltriDestinatariItem = new HiddenItem("recordFiltriDestinatari");
		recordFiltriDestinatariCCItem = new HiddenItem("recordFiltriDestinatariCC");
		recordFiltriDestinatariCCNItem = new HiddenItem("recordFiltriDestinatariCCN");
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
	

	protected void downloadSegnatura(final String display) {
		Record lRecordMail = getRecord();
		if (lRecordMail != null) {
			GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaInvioUDMailDatasource");
			lGWTRestDataSource.executecustom("downloadSegnatura", lRecordMail, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecord = response.getData()[0];
						String uri = lRecord.getAttribute("value");
						_manageIconClick(display, uri, false);
					}
				}
			});
		}
	}

	protected void _manageIconClick(String display, String uri, boolean remoteUri) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri + "");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	public void caricaMail(Record lRecord) {
		clearErrors(true);
		lValuesManager.editRecord(lRecord);
		lInvioUDDestinatariItem.setAggiuntaMode();
		boolean invioMailFromAtti = lRecord.getAttributeAsBoolean("invioMailFromAtti") != null ? lRecord.getAttributeAsBoolean("invioMailFromAtti") : false;		
		if(invioMailFromAtti) {
			lSelectItemMittente.setCanEdit(false);
			editRecord(lRecord);
			markForRedraw();
		} else {
			lSelectItemMittente.setCanEdit(true);
			editRecord(lRecord);
			setMittenteIniziale(lRecord);
			markForRedraw();
		}
	}
	
	private void setMittenteIniziale(Record lRecord) {

		String mittente = lRecord != null && lRecord.getAttributeAsString("mittente") != null && !"".equals(lRecord.getAttributeAsString("mittente"))
				? lRecord.getAttributeAsString("mittente") : "";
		/**
		 * Metodo richiamato in più punti da AurigaWeb Se il mittente è valorizzato verifico che sia presente nella select dei mittenti, se si lo valorizzo per
		 * non avere il caso in cui si abbia un mittente visibile a video ma non presente in lista
		 */
		if (mittente != null && !"".equals(mittente)) {
			boolean trovato = false;
			for (int i = 0; i < lSelectItemMittente.getClientPickListData().length; i++) {
				Record recordItem = lSelectItemMittente.getClientPickListData()[i];
				if (recordItem.getAttributeAsString("key").equals(mittente)) {
					trovato = true;
					break;
				}
			}
			//Se è stato trovato un mittente lo seleziono
			if (trovato) {
				lSelectItemMittente.setValue(mittente);
				markForRedraw();
			}
			checkIfCasellaIsPec();
			markForRedraw();
		} else {
			/*
			 * Il mittente non è valorizzato quindi procedo alla verifica del mittente originale
			 */
			setMittenteDefault(null, lRecord);
		}
	}
	
	/**
	 * Se il mittente iniziale è un mittente accreditato, lo si preimposta come mittente di default, altrimenti si verifica la
	 * preference se sia accreditata o meno.
	 */
	private void setMittenteDefault(final String mittenteIniziale, Record lRecord) {

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
					boolean trovatoDefault = false;
					if (mittenteDefaultPrefItem != null && !"".equals(mittenteDefaultPrefItem)) {
						for (int i = 0; i < lSelectItemMittente.getClientPickListData().length; i++) {
							Record recordItem = lSelectItemMittente.getClientPickListData()[i];
							if (recordItem.getAttributeAsString("key").equals(mittenteDefaultPrefItem)) {
								trovatoDefault = true;
								break;
							}
						}
						if (trovatoDefault) {
							lSelectItemMittente.setValue(mittenteDefaultPrefItem);
							// Refresh del form per eseguire l'analisi della mail di default (PEC o PEO)
							markForRedraw();
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().postaElettronica_nuovomessaggio_mittente_default_warning(), "", MessageType.WARNING));
							lSelectItemMittente.setValue("");
						}
					} else {
						lSelectItemMittente.setValue("");
					}
				}
				checkIfCasellaIsPec();
			}
		});
	}
	/**
	 * Metodo che controlla se la casella mail selezionata all'interno della select (o impostata all'apertura del form)
	 * è una PEC o meno.
	 * Viene valorizzato il valore dell'HiddenItem in base al fatto che sia una PEC o meno.
	 */
	private void checkIfCasellaIsPec() {
		if ((lSelectItemMittente.getDisplayValue() != null && !"".equals(lSelectItemMittente.getDisplayValue())
				&& lSelectItemMittente.getDisplayValue().contains("PEC"))){
			casellaIsPec = "true";
		} else {
			casellaIsPec = "false";
		}
		manageCheckBoxConfermaLettura();
		manageComboBoxDestinatariCCN();
	}
	
	public String getApplicationName() {
		String applicationName = null;
		try {
			Dictionary dictionary = Dictionary.getDictionary("params");
			applicationName = dictionary != null && dictionary.get("applicationName") != null ? dictionary.get("applicationName") : "";
		} catch (Exception e) {
			applicationName = "";
		}
		return applicationName;
	}

	public Record getRecord() {
		if (validate()) {
			Record lRecord = new Record(lValuesManager.rememberValues());
			return lRecord;
		} else
			return null;
	}

	boolean valid = false;
	Map<String, String> lMapErroriNascosti = null;

	public boolean validate() {
		lValuesManager.clearErrors(true);
		lValuesManager.addHiddenValidationErrorsHandler(new HiddenValidationErrorsHandler() {

			@Override
			public void onHiddenValidationErrors(HiddenValidationErrorsEvent event) {
				lMapErroriNascosti = event.getErrors();
			}
		});
		valid = true;
		lValuesManager.validate();
		Map<String, String> lMapErrori = lValuesManager.getErrors();
		if (lMapErrori != null) {
			for (String lString : lMapErrori.keySet()) {
				if (lMapErroriNascosti == null || !lMapErroriNascosti.containsKey(lString)) {
					valid = false;
				}
			}
		}
		for (DynamicForm form : lValuesManager.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item instanceof ReplicableItem) {
					ReplicableItem lReplicableItem = (ReplicableItem) item;
					if (lReplicableItem.isVisible())
						valid = lReplicableItem.validate() && valid;
				}
			}
		}
		return valid;
	}

	public void setlHiddenItemIdMailPartenza(HiddenItem lHiddenItemIdMailPartenza) {
		this.lHiddenItemIdMailPartenza = lHiddenItemIdMailPartenza;
	}

	public HiddenItem getlHiddenItemIdMailPartenza() {
		return lHiddenItemIdMailPartenza;
	}

	// Appende a str la stringa strToAppend, preceduta dal carattere ; se questo non è presente alla fine di str
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

	public class InvioUDMailMultiLookupRubricaEmailPopup extends LookupRubricaEmailPopup {

		private String fieldName;
		private HashMap<String, Integer> indirizzoRefCount = new HashMap<String, Integer>();

		public InvioUDMailMultiLookupRubricaEmailPopup(String pFieldName) {
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
			}else{
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
			//Nascondo la text area per l'html
			lTextAreaItemBody.hide();
			
			//Visualizzo la text area
			lRichTextItemBody.show();		
			markForRedraw();
		}
	}
	
	
	public void verifyChangeStyle() {
		if ((lValuesManager.getValue("textHtml") != null) && (lValuesManager.getValue("textHtml").equals("text"))) {
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
	
	/**
	 * Metodo che aggiorna il contenuto del richTextItem
	 * @param body nuovo corpo da inserire
	 */
	public void refreshBody(String body) {
		
		lRichTextItemBody.setValue(body);
		//Aggiorno la grafica
		markForRedraw();
	}
	
	public String getBody(){
		return lRichTextItemBody.getValue().toString();
	}
	
	public ValuesManager getMapValues(){
		return lValuesManager;
	}
	
	@Override
	public void editNewRecord() {
		super.editNewRecord();
	}

	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
	}
	
	public void refreshForm(Map initialValues){
		
		//Per l'aggiornamento dei valori 
		editNewRecord(initialValues);
		
		//Per controllare se visualizzare o meno la checkbox di conferma lettura
		manageCheckBoxConfermaLettura();
		
		manageComboBoxDestinatariCCN();
		
		filtroPresenteFromModello(initialValues);
	}
	
	/**
	 * Metodo per la gestione della visualizzazione o meno della checkbox relativa alla conferma di lettura
	 */
	private void manageCheckBoxConfermaLettura() {
		
		if (AurigaLayout.getParametroDBAsBoolean("HIDE_CONF_LETTURA_MAIL")) {
			lCheckboxConfermaLettura.setVisible(false);
		} else {
			if (lSelectItemMittente.getDisplayValue() != null && !"".equals(lSelectItemMittente.getDisplayValue())
					&& lSelectItemMittente.getDisplayValue().contains("PEC")) {
				lHiddenItemTipoMail.setValue("PEC"); 
				lHiddenItemConfermaLetturaShow.setValue(false);
				lCheckboxConfermaLettura.setVisible(false);
			} else {
				lHiddenItemTipoMail.setValue("PEO");
				lHiddenItemConfermaLetturaShow.setValue(true);
				lCheckboxConfermaLettura.setVisible(true);
			}
		}
		markForRedraw();
	}
	
	/**
	 * Metodo per la gestione della visualizzazione o meno della combo box destinarati in CCN
	 */
	private void manageComboBoxDestinatariCCN() {
		
		if (lSelectItemMittente.getDisplayValue() != null && !"".equals(lSelectItemMittente.getDisplayValue())
				&& lSelectItemMittente.getDisplayValue().contains("PEC")) {
			lComboBoxDestinatariCCN.hide();
			lookupRubricaEmailButtonDestinatariCCN.hide();
		} else {
			lComboBoxDestinatariCCN.show();
			lookupRubricaEmailButtonDestinatariCCN.show();
		}
		markForRedraw();
	}
	
	public String getStyleText(){
		return (String) style.getValue();
	}
	
	public void selezionaDestinatarioPrimario(Record record) {

		lComboBoxDestinatari.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		
		markForRedraw();
	}
	
	public void selezionaDestinatarioSecondario(Record record,Boolean isCC) {
		if(isCC){	
			lComboBoxDestinatariCC.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		}else{
			lComboBoxDestinatariCCN.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		}
		markForRedraw();
	}

	protected String buildIconHtml(String src, String value) {
		return "<div align=\"left\"><img src=\"images/" + src + "\" height=\"10\" width=\"10\" alt=\"\" />&nbsp;&nbsp;" + value + "</div>";
	}
	
	private String getConfiguredPrefKeyPrefix() {
		return Layout.getConfiguredPrefKeyPrefix();
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
	
	@Override
	protected void onDestroy() {
		if (lValuesManager != null) {
			try {
				lValuesManager.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
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
			}
		}
		return verifyPrimari || verifyCC || verifyCCN;
	}
}