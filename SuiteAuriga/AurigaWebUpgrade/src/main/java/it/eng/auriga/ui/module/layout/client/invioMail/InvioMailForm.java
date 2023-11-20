/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
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
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
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
import it.eng.auriga.ui.module.layout.client.postaElettronica.VisualizzaCorpoHTMLMail;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
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
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class InvioMailForm extends DynamicForm {

	private static final int COMPONENT_WIDTH = 800;

	private InvioMailForm _this = this;

	// Contiene l'id della mail predecessore (utilizzato per inoltri e risposte singole)
	private HiddenItem lHiddenItemIdEmail;
	// Contiene la lista degli id delle mail predecessore (utilizzato per nuovi invii come copia)
	private HiddenItem lHiddenItemListaIdEmailPredecessore;
	// Contiene l'id della mail di partenza a cui è associata la registrazione da associare alla mail
	private HiddenItem lHiddenItemIdEmailUD;

	private HiddenItem casellaIsPecItem;
	private HiddenItem destinatariHidden;
	private HiddenItem tipoRelCopia;
	private HiddenItem salvaInviati;
	private HiddenItem uriMailItem;
	private HiddenItem statoLavorazioneMailPartenza;
	private HiddenItem idEmailPrincipaleItem;

	private SelectItem lSelectItemMittente;
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
	private ImgButtonItem viewerCorpoMailImgButton;
	private ImgButtonItem editCorpoMailImgButton;
	private SpacerItem spacer;
	private SpacerItem lSpacerItemCorpoMail;
	protected RichTextItem corpoMailContenutiItem;
	private SpacerItem spacerStyleItem;
	private RadioGroupItem style;
	private SpacerItem lSpacerItemBody;
	private TextAreaItem lTextAreaItemBody;
	private RichTextItem lRichTextHtmlItemBody;
	private SpacerItem lSpacerItemflgConfermaLettura;
	private CheckboxItem flgConfermaLettura;
	private CheckboxItem flgInvioSeparato;

	private GWTRestDataSource accounts;

	private Boolean isMassivo = false;
	private Boolean editing;
	private String tipoRelazione;
	private String mittenteDefaultPrefItem;
	
	private String starterMail = "";
	
	public InvioMailForm(final String tipoRel) {

		this.tipoRelazione = tipoRel;

		setWrapItemTitles(false);
		setNumCols(20);
		setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, "*", "*");
		
		createHiddenItem();

		editing = true;

		createMittente();
		
		// Creazione filtro destinatari
		creaFiltroDestinatariPrimari();
		
		createDestinatariPrimari();
		
		// Creazione filtro destinatari
		creaFiltroDestinatariCC();
		
		createDestinatariCC();
		
		// Creazione filtro destinatari
		creaFiltroDestinatariCCN();
		
		createDestinaratiCCN();
	
		createOggetto();

		createButtonBody();

		createCorpoMailContenuti();

		createAttachment();

		createUploadButton();

		// Opzioni Testo
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		styleMap.put("text", I18NUtil.getMessages().invionotificainteropform_styleMap_TEXT_value());
		styleMap.put("html", I18NUtil.getMessages().invionotificainteropform_styleMap_HTML_value());

		createRadioBody(styleMap);

		createConfermaLettura();
		
		createInvioSeparato();
	
		setFields(
			lHiddenItemListaIdEmailPredecessore, lHiddenItemIdEmailUD,destinatariHidden,idEmailPrincipaleItem,
			lHiddenItemIdEmail, statoLavorazioneMailPartenza, casellaIsPecItem, uriMailItem, tipoRelCopia, salvaInviati,
			recordFiltriDestinatariItem,recordFiltriDestinatariCCItem,recordFiltriDestinatariCCNItem,
			lSelectItemMittente, salvaMittenteDefaultImgButton, 
			lComboBoxDestinatari, lookupRubricaEmailDestinatariImgButton,
			lComboBoxDestinatariCC, lookupRubricaEmailDestinatariCCImgButton,
			lComboBoxDestinatariCCN, lookupRubricaEmailDestinatariCCNImgButton,
			lTextItemOggetto, 
			lAttachmentItem, spacer, uploadButton,
			spacerStyleItem, style, flgConfermaLettura, flgInvioSeparato, 
			lSpacerItemCorpoMail, corpoMailContenutiItem, 
			lSpacerItemBody, lRichTextHtmlItemBody, lTextAreaItemBody, editCorpoMailImgButton, viewerCorpoMailImgButton
		);
	}

	private void createInvioSeparato() {
		
		flgInvioSeparato = new CheckboxItem("flgInvioSeparato", I18NUtil.getMessages().inviomailform_flgInvioSeparato_title());
		flgInvioSeparato.setWrapTitle(true);
		flgInvioSeparato.setWidth("*");
		flgInvioSeparato.setEndRow(true);
	}

	private void createConfermaLettura() {
		//Spacer utilizzato per inserire o meno lo spazio in base alla presenza del chheckbox di conferma lettura
		lSpacerItemflgConfermaLettura = new SpacerItem();
		lSpacerItemflgConfermaLettura.setStartRow(true);
		lSpacerItemflgConfermaLettura.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isFlgConfermaLetturaToShow();
			}
		});

		flgConfermaLettura = new CheckboxItem("confermaLettura", I18NUtil.getMessages().email_categoria_PEO_RIC_CONF_value());
		flgConfermaLettura.setWrapTitle(false);
		flgConfermaLettura.setWidth(100);
		flgConfermaLettura.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return isFlgConfermaLetturaToShow();
			}
		});
	}

	private void createRadioBody(LinkedHashMap<String, String> styleMap) {
		
		spacerStyleItem = new SpacerItem();
		spacerStyleItem.setStartRow(true);
		spacerStyleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !editing;
			}
		});
		
		style = new RadioGroupItem("textHtml");
		style.setDefaultValue("html");
		style.setTitle(I18NUtil.getMessages().invionotificainteropform_styleItem_title());
		style.setValueMap(styleMap);
		style.setVertical(false);
		style.setWrap(false);
		style.setWidth(250);
		style.setStartRow(true);
		style.setColSpan(1);
		style.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				manageChangeTextHtml();
			}
		});

		lSpacerItemBody = new SpacerItem();
		lSpacerItemBody.setStartRow(true);
		lSpacerItemBody.setColSpan(1);
		lSpacerItemBody.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return editing;
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
		lTextAreaItemBody.setIconVAlign(VerticalAlignment.CENTER);
		lTextAreaItemBody.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return editing && style.getValue() != null && "text".equals(style.getValue());
			}
		});

		// Casella testo HTML
		lRichTextHtmlItemBody = new RichTextItem("bodyHtml");
		lRichTextHtmlItemBody.setDefaultValue("");
		lRichTextHtmlItemBody.setVisible(true);
		lRichTextHtmlItemBody.setShowTitle(false);
		// lRichTextHtmlItemBody.setCellStyle(it.eng.utility.Styles.textItem); // lo commento perchè sennò il RichTextItem compare spostato a dx, così facendo però mi mette il bordino 
		lRichTextHtmlItemBody.setHeight(600);
		lRichTextHtmlItemBody.setWidth(COMPONENT_WIDTH);
		lRichTextHtmlItemBody.setColSpan(8);
		lRichTextHtmlItemBody.setStartRow(false);
		lRichTextHtmlItemBody.setEndRow(false);
		lRichTextHtmlItemBody.setIconVAlign(VerticalAlignment.CENTER);
		lRichTextHtmlItemBody.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");
		lRichTextHtmlItemBody.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return editing && style.getValue() != null && "html".equals(style.getValue());
			}
		});
	}

	private void createUploadButton() {
		
		spacer = new SpacerItem();
		spacer.setWidth(20);
		spacer.setStartRow(true);
		spacer.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				if ((lAttachmentItem != null) && (lAttachmentItem.getTotalMembers() > 0)) {
					return true;
				} else {
					return false;
				}
			}
		});
		
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
		uploadButton.setEndRow(true);
		uploadButton.setShowTitle(true);
		uploadButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				if ((lAttachmentItem != null) && (lAttachmentItem.getTotalMembers() > 0)) {
					uploadButton.setShowTitle(false);
				} else {
					uploadButton.setShowTitle(true);
				}
				return true;
			}
		});
	}

	private void createAttachment() {
		
		lAttachmentItem = new AttachmentReplicableItem(true, 250) {

			public void downloadFile(final ServiceCallback<Record> lDsCallback) {

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource");
				Record lRecord = new Record();
				lRecord.setAttribute("uri", getValues().get("uriMail"));
				lRecord.setAttribute("idEmail", getValues().get("idEmailPrincipale"));
				lGwtRestDataSource.executecustom("retrieveAttach", lRecord, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							RecordList listaAllegati = response.getData()[0].getAttributeAsRecordList("listaAllegati");
							Record lRecordNuovoInvio = new Record(getValues());
							if (listaAllegati != null) {
								RecordList attachList = new RecordList();
								for (int i = 0; i < listaAllegati.getLength(); i++) {
									Record attach = new Record();
									attach.setAttribute("fileNameAttach", listaAllegati.get(i).getAttribute("nomeFile"));
									attach.setAttribute("infoFileAttach", listaAllegati.get(i).getAttributeAsRecord("infoFile"));
									attach.setAttribute("uriAttach", listaAllegati.get(i).getAttribute("uri"));
									attachList.add(attach);
								}
								lRecordNuovoInvio.setAttribute("attach", attachList);
								realEditRecord(lRecordNuovoInvio);
							}
							lDsCallback.execute(response.getData()[0]);
						}
					}
				});
			}

			@Override
			public boolean showStampaFileButton() {
				Record record = new Record(getValuesManager().getValues());
				Boolean abilitaStampaFile = record != null && record.getAttributeAsString("abilitaStampaFile") != null
						&& "true".equals(record.getAttributeAsString("abilitaStampaFile")) ? true : false;
				return abilitaStampaFile;
			}
		};
		lAttachmentItem.setName("attach");
		lAttachmentItem.setTitle(I18NUtil.getMessages().invionotificainteropform_allegatiItem_title());
		lAttachmentItem.setShowNewButton(false);
		lAttachmentItem.setWidth(COMPONENT_WIDTH);
		lAttachmentItem.setColSpan(8);
		lAttachmentItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				if ((lAttachmentItem != null) && (lAttachmentItem.getTotalMembers() > 0)) {
					if (uploadButton != null) {
						uploadButton.setShowTitle(false);
					}
					return true;
				} else {
					if (uploadButton != null) {
						uploadButton.setShowTitle(true);
					}
					return false;
				}
			}
		});
	}

	private void createCorpoMailContenuti() {
		
		lSpacerItemCorpoMail = new SpacerItem();
		lSpacerItemCorpoMail.setStartRow(true);
		lSpacerItemCorpoMail.setColSpan(1);
		lSpacerItemCorpoMail.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return !editing;
			}
		});
		
		corpoMailContenutiItem = new RichTextItem();
		corpoMailContenutiItem.setTitle(I18NUtil.getMessages().posta_elettronica_corpo_mail_contenuti_item());
		corpoMailContenutiItem.setName("");
		corpoMailContenutiItem.setHeight(200);
		corpoMailContenutiItem.setWidth(COMPONENT_WIDTH);
		corpoMailContenutiItem.setStartRow(false);
		corpoMailContenutiItem.setEndRow(false);
		corpoMailContenutiItem.setShowTitle(false);
		corpoMailContenutiItem.setColSpan(8);
		corpoMailContenutiItem.setCellStyle(it.eng.utility.Styles.textItem);
		corpoMailContenutiItem.setIconVAlign(VerticalAlignment.CENTER);
		corpoMailContenutiItem.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");
		corpoMailContenutiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (!editing) {
					if ("text".equals(style.getValueAsString())) {
						corpoMailContenutiItem.setValue(lTextAreaItemBody.getValueAsString());
						corpoMailContenutiItem.setName("bodyText");
					} else {
						corpoMailContenutiItem.setValue((String) lRichTextHtmlItemBody.getValue());
						corpoMailContenutiItem.setName("bodyHtml");
					}
				}
				return !editing;
			}
		});

	}

	private void createButtonBody() {
		/**
		 * TEXT AREA READ ONLY
		 */
		viewerCorpoMailImgButton = new ImgButtonItem("viewerCorpo", "buttons/view.png", I18NUtil.getMessages().posta_elettronica_viewer_contenuti_item1());
		viewerCorpoMailImgButton.setShowTitle(false);
		viewerCorpoMailImgButton.setAlwaysEnabled(true);
		viewerCorpoMailImgButton.setStartRow(false);
		viewerCorpoMailImgButton.setWidth(10);
		viewerCorpoMailImgButton.setColSpan(1);
		viewerCorpoMailImgButton.setVAlign(VerticalAlignment.TOP);
		viewerCorpoMailImgButton.setPrompt(I18NUtil.getMessages().posta_elettronica_viewer_contenuti_item1());
		viewerCorpoMailImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				managePreviewViewerBody();
			}
		});
		
		editCorpoMailImgButton = new ImgButtonItem("editCorpo", "buttons/view_editor.png", "Edit corpo");
		editCorpoMailImgButton.setShowTitle(false);
		editCorpoMailImgButton.setAlwaysEnabled(true);
		editCorpoMailImgButton.setWidth(10);
		editCorpoMailImgButton.setValueIconSize(32);
		editCorpoMailImgButton.setColSpan(1);
		editCorpoMailImgButton.setStartRow(false);
		editCorpoMailImgButton.setPrompt("Edit corpo email");
		editCorpoMailImgButton.setVAlign(VerticalAlignment.TOP);
		editCorpoMailImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageEditViewerBody();
			}
		});
	}

	private void createOggetto() {
		// Oggetto
		lTextItemOggetto = new TextItem("oggetto", I18NUtil.getMessages().invionotificainteropform_oggettoItem_title());
		lTextItemOggetto.setTitleColSpan(1);
		lTextItemOggetto.setWidth(COMPONENT_WIDTH);
		lTextItemOggetto.setStartRow(true);
		lTextItemOggetto.setEndRow(true);
		lTextItemOggetto.setColSpan(10);
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
	}

	// Destinatari Primari
	private void createDestinatariPrimari() {
		
		GWTRestDataSource proposteDestinatariPrimariDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatari = new ComboBoxItem("destinatari", I18NUtil.getMessages().invionotificainteropform_destinatariItem_title());
		lComboBoxDestinatari.setRequired(true);
		lComboBoxDestinatari.setColSpan(8);
		lComboBoxDestinatari.setShowPickerIcon(false);
		lComboBoxDestinatari.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatari.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatari.setStartRow(true);
		lComboBoxDestinatari.setValueField("indirizzoEmail");
		lComboBoxDestinatari.setDisplayField("indirizzoEmail");
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
		lComboBoxDestinatari.setValidators(getValidatorEmail());

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

		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario
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
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup("destinatari");
				lookupRubricaEmailPopup.show();
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
	}
	
	private void createDestinatariCC() {
		
		GWTRestDataSource proposteDestinatariCCDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		// Destinatari CC
		lComboBoxDestinatariCC = new ComboBoxItem("destinatariCC", I18NUtil.getMessages().invionotificainteropform_destinatariCCItem_title());
		lComboBoxDestinatariCC.setColSpan(8);
		lComboBoxDestinatariCC.setShowPickerIcon(false);
		lComboBoxDestinatariCC.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatariCC.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatariCC.setStartRow(true);
		lComboBoxDestinatariCC.setValueField("indirizzoEmail");
		lComboBoxDestinatariCC.setDisplayField("indirizzoEmail");
		lComboBoxDestinatariCC.setAutoFetchData(false);
		lComboBoxDestinatariCC.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatariCC.setAddUnknownValues(true);
		lComboBoxDestinatariCC.setOptionDataSource(proposteDestinatariCCDS);
		lComboBoxDestinatariCC.setValidateOnChange(false);
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
		/*
		 * Ai destinatari CC dovrà essere passato un altro CustomValidator, uguale identico a quello dei destinatari ma non lo stesso, 
		 * altrimenti poi viene generata un'umbrellaException
		 */
		lComboBoxDestinatariCC.setValidators(getValidatorEmail());

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

		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario CC
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
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup("destinatariCC");
				lookupRubricaEmailPopup.show();
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
//				
//				boolean isVisibile = true;
//				if(lSelectItemMittente.getDisplayValue() != null && !"".equals(lSelectItemMittente.getDisplayValue())
//						&& lSelectItemMittente.getDisplayValue().contains("PEC")){
//							isVisibile = false;
//				}else{
//					if(getValuesManager() != null && getValuesManager().getValues() != null ){
//						Record lRecord = new Record(getValuesManager().getValues());
//						Boolean isBozza = lRecord != null && lRecord.getAttribute("id") != null && !"".equals(lRecord.getAttribute("id"))
//								&& lRecord.getAttribute("id").endsWith(".B");
//						if(isBozza){
//							if (casellaIsPecItem != null && casellaIsPecItem.getValue() != null 
//									&& !"".equals(casellaIsPecItem.getValue()) && "true".equals(casellaIsPecItem.getValue())) {
//								isVisibile = false;
//							}
//						}
//					}
//				}
//				return isVisibile;
//			}
//		});
	}
	
	private void createDestinaratiCCN() {
		
		GWTRestDataSource proposteDestinatariCCDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		// Destinatari CCN
		lComboBoxDestinatariCCN = new ComboBoxItem("destinatariCCN", I18NUtil.getMessages().invionotificainteropform_destinatariCCNItem_title());
		lComboBoxDestinatariCCN.setColSpan(8);
		lComboBoxDestinatariCCN.setShowPickerIcon(false);
		lComboBoxDestinatariCCN.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatariCCN.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatariCCN.setStartRow(true);
		lComboBoxDestinatariCCN.setValueField("indirizzoEmail");
		lComboBoxDestinatariCCN.setDisplayField("indirizzoEmail");
		lComboBoxDestinatariCCN.setAutoFetchData(false);
		lComboBoxDestinatariCCN.setAlwaysFetchMissingValues(true);
		lComboBoxDestinatariCCN.setAddUnknownValues(true);
		lComboBoxDestinatariCCN.setOptionDataSource(proposteDestinatariCCDS);
		lComboBoxDestinatariCCN.setValidateOnChange(false);
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
				
				boolean isVisibile = true;
				if(lSelectItemMittente.getDisplayValue() != null && !"".equals(lSelectItemMittente.getDisplayValue())
						&& lSelectItemMittente.getDisplayValue().contains("PEC")){
							isVisibile = false;
				}else{
					if(getValuesManager() != null && getValuesManager().getValues() != null ){
						Record lRecord = new Record(getValuesManager().getValues());
						Boolean isBozza = lRecord != null && lRecord.getAttribute("id") != null && !"".equals(lRecord.getAttribute("id"))
								&& lRecord.getAttribute("id").endsWith(".B");
						if(isBozza){
							if (casellaIsPecItem != null && casellaIsPecItem.getValue() != null 
									&& !"".equals(casellaIsPecItem.getValue()) && "true".equals(casellaIsPecItem.getValue())) {
								isVisibile = false;
							}
						}
					}
				}
				return isVisibile;
			}
		});				
		/*
		 * Ai destinatari CCN dovrà essere passato un altro CustomValidator, uguale identico a quello dei destinatari ma non lo stesso, 
		 * altrimenti poi viene generata un'umbrellaException
		 */
		lComboBoxDestinatariCCN.setValidators(getValidatorEmail());

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

		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario CC
		lookupRubricaEmailDestinatariCCNImgButton = new ImgButtonItem("lookupRubricaEmailDestinatariCCN", "lookup/rubricaemail.png", I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariCCNImgButton.setShowTitle(false);
		lookupRubricaEmailDestinatariCCNImgButton.setAlwaysEnabled(true);
		lookupRubricaEmailDestinatariCCNImgButton.setWidth(16);
		lookupRubricaEmailDestinatariCCNImgButton.setValueIconSize(32);
		lookupRubricaEmailDestinatariCCNImgButton.setStartRow(false);
		lookupRubricaEmailDestinatariCCNImgButton.setEndRow(false);
		lookupRubricaEmailDestinatariCCNImgButton.setColSpan(1);
		lookupRubricaEmailDestinatariCCNImgButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariCCNImgButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup("destinatariCCN");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailDestinatariCCNImgButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				boolean isVisibile = true;
				if(lSelectItemMittente.getDisplayValue() != null && !"".equals(lSelectItemMittente.getDisplayValue())
						&& lSelectItemMittente.getDisplayValue().contains("PEC")){
							isVisibile = false;
				}else{
					if(getValuesManager() != null && getValuesManager().getValues() != null ){
						Record lRecord = new Record(getValuesManager().getValues());
						Boolean isBozza = lRecord != null && lRecord.getAttribute("id") != null && !"".equals(lRecord.getAttribute("id"))
								&& lRecord.getAttribute("id").endsWith(".B");
						if(isBozza){
							if (casellaIsPecItem != null && casellaIsPecItem.getValue() != null 
									&& !"".equals(casellaIsPecItem.getValue()) && "true".equals(casellaIsPecItem.getValue())) {
								isVisibile = false;
							}
						}
					} 
				}
				return isVisibile;
			}
		});
	}

	private void createMittente() {
		
		accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		accounts.addParam("finalita", "INVIO");
		
		lSelectItemMittente = new SelectItem("mittente", I18NUtil.getMessages().invionotificainteropform_mittenteItem_title());
		lSelectItemMittente.setDisplayField("value");
		lSelectItemMittente.setValueField("key");
		lSelectItemMittente.setAttribute("obbligatorio", true);
		lSelectItemMittente.setOptionDataSource(accounts);
		lSelectItemMittente.setWidth(COMPONENT_WIDTH);
		lSelectItemMittente.setColSpan(8);
		lSelectItemMittente.setAddUnknownValues(false);
		lSelectItemMittente.setRejectInvalidValueOnChange(true);
		lSelectItemMittente.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				flgConfermaLettura.setValue(false);	// Ogni volta che si cambia mittente nel form il flag di Conferma lettura si deve resettare.
				checkIfCasellaIsPec();
				markForRedraw();
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
	}

	private void createHiddenItem() {
		
		//DEFINIZIONE DEGLI HIDDENITEM
		lHiddenItemIdEmail = new HiddenItem("idEmail");
		lHiddenItemListaIdEmailPredecessore = new HiddenItem("listaIdEmailPredecessore");
		lHiddenItemIdEmailUD = new HiddenItem("idEmailUD");
		// Campo nascosto per valorizzare salvaInviati. Data che la rispettiva checkbox
		// è stata tolta, mi serve un posto dove salvare il valore ricevuto
		salvaInviati = new HiddenItem("salvaInviati");

		uriMailItem = new HiddenItem("uriMail");
		
		statoLavorazioneMailPartenza = new HiddenItem("mailPredecessoreStatoLav");

		casellaIsPecItem = new HiddenItem("casellaIsPec");

		tipoRelCopia = new HiddenItem("tipoRelCopia");
		
		destinatariHidden = new HiddenItem("destinatariHidden");
		
		idEmailPrincipaleItem = new HiddenItem("idEmailPrincipale");
		
		/**
		 * FILTRI DESTINATARI
		 */
		recordFiltriDestinatariItem = new HiddenItem("recordFiltriDestinatari");
		recordFiltriDestinatariCCItem = new HiddenItem("recordFiltriDestinatariCC");
		recordFiltriDestinatariCCNItem = new HiddenItem("recordFiltriDestinatariCCN");
	}

	protected void realEditRecord(Record lRecord) {
		super.editRecord(lRecord);
		setCanEdit(true);
		markForRedraw();
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		editing = canEdit;
		for (FormItem item : getFields()) {
			if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
				item.setCanEdit(canEdit);
				item.redraw();
			}
			if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
				item.setCanEdit(canEdit);
				item.redraw();
			}
		}
		if (!editing) {
			lComboBoxDestinatari.setShowIcons(false);
			lComboBoxDestinatariCC.setShowIcons(false);
			lComboBoxDestinatariCCN.setShowIcons(false);
			lSpacerItemCorpoMail.setVisible(true);
			corpoMailContenutiItem.setVisible(true);
			lSpacerItemBody.setVisible(false);
			spacerStyleItem.setVisible(true);
			style.setVisible(false);
			lTextAreaItemBody.setVisible(false);
			lRichTextHtmlItemBody.setVisible(false);
			flgConfermaLettura.setVisible(false);
			editCorpoMailImgButton.setVisible(false);
			lookupRubricaEmailDestinatariImgButton.setVisible(false);
			salvaMittenteDefaultImgButton.setVisible(false);
		} else {
			lComboBoxDestinatari.setShowIcons(true);
			lComboBoxDestinatariCC.setShowIcons(true);
			lComboBoxDestinatariCCN.setShowIcons(true);
			lSpacerItemCorpoMail.setVisible(false);
			corpoMailContenutiItem.setVisible(false);
			lSpacerItemBody.setVisible(true);
			spacerStyleItem.setVisible(false);
			style.setVisible(true);
			lTextAreaItemBody.setVisible(true);
			lRichTextHtmlItemBody.setVisible(true);
			flgConfermaLettura.setVisible(true);
			editCorpoMailImgButton.setVisible(true);
			lookupRubricaEmailDestinatariImgButton.setVisible(true);		
			salvaMittenteDefaultImgButton.setVisible(true);
		}
	}

	private String getConfiguredPrefKeyPrefix() {
		return Layout.getConfiguredPrefKeyPrefix();
	}

	public void caricaMail(final Record lRecord) {

		clearErrors(true);

		final String inoltroMassivo = lRecord.getAttributeAsString("IM");
		final String mittenteInizialeCorrente = lRecord.getAttributeAsString("mittenteIniziale");
		lRecord.setAttribute("mittenteIniziale", "");
		editRecord(lRecord);

		isMassivo = lRecord.getAttributeAsBoolean("isMassivo");
		lAttachmentItem.setDetailRecord(lRecord);

		/**
		 * Provengo da un'azione massiva quindi non viene data la preimpostazione, ma viene verificato solo se esiste che il mittente di default sia una casella
		 * abilitata
		 */
		if (inoltroMassivo != null && !"".equals(inoltroMassivo)) {
			setPreferenceMittenteDefault();
		} else {
			setMittenteIniziale(lRecord, mittenteInizialeCorrente);
		}
	}

	private void setMittenteIniziale(Record lRecord, String mittenteInizialeCorrente) {

		String mittente = lRecord != null && lRecord.getAttributeAsString("mittente") != null && !"".equals(lRecord.getAttributeAsString("mittente"))
				? lRecord.getAttributeAsString("mittente") : "";
		String mittenteIniziale = mittenteInizialeCorrente != null && !"".equals(mittenteInizialeCorrente) ? mittenteInizialeCorrente : "";

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
		} else {
			/*
			 * Il mittente non è valorizzato quindi procedo alla verifica del mittente originale
			 */
			setMittenteDefault(mittenteIniziale, lRecord);
		}
	}

	public Record getRecord() {
		if (validate()) {
			Record lRecord = new Record(rememberValues());
			return lRecord;
		} else
			return null;
	}

	protected void manageChangeTextHtml() {
		if (style.getValue().equals("text")) {
			//Si sta cambiando dal RichTextItem al TextItem
			if(AurigaLayout.getParametroDBAsBoolean("CANCELLA_TAG_VUOTI_CORPO_MAIL")){
			
				try {	
					Record recordBody = new Record();
					recordBody.setAttribute("bodyHtml", lRichTextHtmlItemBody.getValue() != null ? ((String)lRichTextHtmlItemBody.getValue()) : "");
	
					// Chiamata al datasource per controllare il contenuto del corpo della mail
					GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
					corpoMailDataSource.performCustomOperation("checkIfBodyIsEmpty", recordBody, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
	
								String bodyHtml = record.getAttribute("bodyHtml");
								String bodyText = record.getAttribute("bodyText");	
								lTextAreaItemBody.setValue(bodyText);
								lRichTextHtmlItemBody.setValue(bodyHtml);
								markForRedraw();
							}
						}
					}, new DSRequest());
	
				} catch (Exception e) {	
					lTextAreaItemBody.setValue(lRichTextHtmlItemBody.getValue());
					markForRedraw();
				}
			} else {
				lTextAreaItemBody.setValue(lRichTextHtmlItemBody.getValue());
				markForRedraw();
			}
		} else {
			markForRedraw();
		}	
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

	public ReplicableItem getAttachmentReplicableItem() {
		return lAttachmentItem;
	}
	
	private void manageEditViewerBody(){
		
		final Record lRecord = new Record(getValuesManager().getValues());
		String titleEmail = lRecord.getAttribute("titoloGUIDettaglioEmail");
		String progressivo = lRecord.getAttribute("id") != null && !"".equals(lRecord.getAttribute("id")) ? lRecord.getAttribute("id") : "";
		String tipo = lRecord.getAttribute("tipo") != null && !"".equals(lRecord.getAttribute("tipo")) ? lRecord.getAttribute("tipo") : "";
		String sottoTipo = lRecord.getAttribute("sottoTipo") != null && !"".equals(lRecord.getAttribute("sottoTipo")) ? lRecord.getAttribute("sottoTipo") : "";
		String tsInvio = lRecord.getAttribute("tsInvio") != null && !"".equals(lRecord.getAttribute("tsInvio")) ? lRecord.getAttribute("tsInvio") : "";

		String titolo = "Editor testo email: ";
		if (titleEmail != null) {
			titolo += (titleEmail != null && !"".equals(titleEmail) && titleEmail.length() > 0) ? titleEmail + " - " : progressivo + " - " + tipo + " - " + sottoTipo + " del "
					+ tsInvio;
		}else{
			String idEmail = lRecord.getAttribute("idEmail") != null && !"".equals(lRecord.getAttribute("idEmail")) ?
				lRecord.getAttribute("idEmail") : "";
			String oggetto = lRecord.getAttribute("oggetto") != null && !"".equals(lRecord.getAttribute("oggetto")) ?
					lRecord.getAttribute("oggetto") : "";
			titolo += idEmail + " con oggetto: "+oggetto;
		}
		String body = "";
		if ("text".equals(style.getValueAsString())) {
			body = lTextAreaItemBody.getValueAsString();
		} else {
			body = (String) lRichTextHtmlItemBody.getValue();
		}
		
		final EditorEmailWindow editorEmailWindow = new EditorEmailWindow(titolo,body){
			
			@Override
			public void manageOnCloseClick() {
				String body = getCurrentBody();
				if ("text".equals(style.getValueAsString())) {
					lTextAreaItemBody.setValue(body);
				} else {
					lRichTextHtmlItemBody.setValue(body);
				}
				markForDestroy();
			};
		};
		editorEmailWindow.show();
	}

	private void managePreviewViewerBody() {
		final Record lRecord = new Record(getValuesManager().getValues());

		Boolean isBozza = lRecord != null && lRecord.getAttribute("id") != null && !"".equals(lRecord.getAttribute("id"))
				&& lRecord.getAttribute("id").endsWith(".B");

		if (isBozza || (editing != null && editing)) {
			String body = "";
			if ("text".equals(style.getValueAsString())) {
				body = lTextAreaItemBody.getValueAsString();
			} else {
				body = (String) lRichTextHtmlItemBody.getValue();
			}

			lRecord.setAttribute("isBozzaEditabile", "1");

			lRecord.setAttribute("inputHtml", body);
			
			VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
			visualizzaCorpoMail.show();
		} else {

			lRecord.setAttribute("completa", "false");

			String progressivo = lRecord.getAttribute("id");
			String idEmail = lRecord.getAttribute("idEmail");
			String uriFileEml = lRecord.getAttribute("uri");

			Record lRecord1 = new Record();
			lRecord1.setAttribute("progressivo", progressivo);
			lRecord1.setAttribute("idEmail", idEmail);
			lRecord1.setAttribute("uriFileEml", uriFileEml);

			String url = GWT.getHostPageBaseURL() + "springdispatcher/createpdf/getHtml";
			RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
			requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("mimetype=text/html&").append("record=" + encodeURL(JSON.encode(lRecord1.getJsObj(), new JSONEncoder())));
			try {
				requestBuilder.sendRequest(stringBuilder.toString(), new RequestCallback() {

					@Override
					public void onResponseReceived(Request request, Response response) {

						String u = URL.encode(response.getText());
						String html = GWT.getHostPageBaseURL() + "springdispatcher/stream?uri=" + u;

						lRecord.setAttribute("inputHtml", html);
						
						VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
						visualizzaCorpoMail.show();
					}

					@Override
					public void onError(Request request, Throwable exception) {

						lRecord.setAttribute("inputHtml", lRecord.getAttribute("body"));
						VisualizzaCorpoHTMLMail visualizzaCorpoMail = new VisualizzaCorpoHTMLMail(lRecord);
						visualizzaCorpoMail.show();
					}
				});
			} catch (Exception e) {
			}
		}
	}

	public static String encodeURL(String str) {
		if (str != null) {
			return URL.encode(str.replaceAll("&", "%26"));
		}
		return null;
	}

	/**
	 * Azione massiva: se la preference sul mittente è presente, si procede alla verifica se sia accreditato o meno.
	 */
	private void setPreferenceMittenteDefault() {

		lSelectItemMittente.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (lSelectItemMittente.getClientPickListData().length == 0) {
					lSelectItemMittente.setValue("");
					Layout.addMessage(new MessageBean("Non sei abilitato all’invio da nessuna casella", "", MessageType.WARNING));
				} else {
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
							//Controllo se la casella selezionata è una PEC impostando l'hiddenItem
							checkIfCasellaIsPec();
							// Refresh del form
							markForRedraw();
						}
					}
				}
			}
		});
	}

	/**
	 * Inoltro o Risposta puntuale: se il mittente iniziale è un mittente accreditato, lo si preimposta come mittente di default, altrimenti si verifica la
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
							//Controllo se la casella selezionata è una PEC impostando l'hiddenItem
							checkIfCasellaIsPec();							
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
							//Controllo se la casella selezionata è una PEC impostando l'hiddenItem
							checkIfCasellaIsPec();
							// Refresh del form per eseguire l'analisi della mail di default (PEC o PEO)
							markForRedraw();
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().postaElettronica_nuovomessaggio_mittente_default_warning(), "", MessageType.WARNING));
							lSelectItemMittente.setValue("");
						}
					}
					if (!trovato && !trovatoDefault) {
						if (getApplicationName().equalsIgnoreCase("EIIFact"))
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().postaElettronica_mittente_default_warning_digidoc(), "", MessageType.WARNING));
						else					
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().postaElettronica_mittente_default_warning(), "", MessageType.WARNING));

					}
				}
			}
		});
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		
		/*
		 * Inserito perchè, prendendo una mail in uscita, inserendo una posta non pEC, 
		 * lasciando la check deselezionata e salvando la mail 
		 * quando viene ricaricata automaticamente la check viene selezionata (ERRONEAMENTE)
		 * Se sistemato il giro di setup della checkbox e degli altri componenti allora 
		 * provare a toglire questa chiamata 
		 */
		checkFlgConfermaLettura(new Boolean(record.getAttribute("confermaLettura")));
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
		
		checkFlgConfermaLettura(initialValues.get("confermaLettura") != null ? (Boolean) initialValues.get("confermaLettura") : false);
	}
	
	/**
	 * Metodo che visualizza o meno il flag di conferma lettura
	 * @return
	 */
	private boolean isFlgConfermaLetturaToShow() {
		//Se il parametro da DB HIDE_CONF_LETTURA_MAIL è stato impostato a true allora non visualizzo la checkbox di conferma lettura
		if (AurigaLayout.getParametroDBAsBoolean("HIDE_CONF_LETTURA_MAIL")) {
			return false;
		} else {
			/*
			 * Se la casella di posta elettronica associata è una PEC allora non deve essere visualizzato il
			 * check di conferma lettura mentre, se la casella non è classificata come PEC, allora deve
			 * essere visualizzato il check di conferma lettura
			 */
			if (casellaIsPecItem.getValue() != null && !"".equals(casellaIsPecItem.getValue()) && "true".equals(casellaIsPecItem.getValue())) {
				return false;
			} else{
				return true;
			}
		}
	}
	
	/**
	 * Metodo che imposta il valore della checkbox relativa al flag di conferma lettura
	 * @param confermaLetturaFlag valore del flag di conferma lettura
	 */
	private void checkFlgConfermaLettura(Boolean confermaLetturaFlag) {
		if(confermaLetturaFlag != null){
			flgConfermaLettura.setValue(confermaLetturaFlag);
		}
	}
	
	/**
	 * Metodo che controlla se la casella mail selezionata all'interno della select (o impostata all'apertura del form)
	 * è una PEC o meno.
	 * Viene valorizzato il valore dell'HiddenItem in base al fatto che sia una PEC o meno.
	 */
	private void checkIfCasellaIsPec() {
		if ((lSelectItemMittente.getDisplayValue() != null && !"".equals(lSelectItemMittente.getDisplayValue())
				&& lSelectItemMittente.getDisplayValue().contains("PEC"))){
			casellaIsPecItem.setValue("true"); 
		} else {
			casellaIsPecItem.setValue("false");
		}
	}
	
	public String getStyleText(){
		return (String) style.getValue();
	}
	
	protected String buildIconHtml(String src, String value) {
		return "<div align=\"left\"><img src=\"images/" + src + "\" height=\"10\" width=\"10\" alt=\"\" />&nbsp;&nbsp;" + value + "</div>";
	}
	
	public void selezionaDestinatarioPrimario(Record record) {
		
		lComboBoxDestinatari.setValue(record.getAttribute("indirizzoEmail").replace(" ", ""));
		_this.markForRedraw();
	}
	
	public void selezionaDestinatarioSecondario(Record record,Boolean isCC) {
		if(isCC){	
			lComboBoxDestinatariCC.setValue(record.getAttribute("indirizzoEmail").replace(" ", ""));
		}else{
			lComboBoxDestinatariCCN.setValue(record.getAttribute("indirizzoEmail").replace(" ", ""));
		}
		_this.markForRedraw();
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