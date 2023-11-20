/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
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
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupRubricaEmailPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.SceltaDestinatarioWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class InvioNotificaInteropForm extends DynamicForm {

	private static final int COMPONENT_WIDTH = 800;
	
	private InvioNotificaInteropForm _this = this;

	private ValuesManager lValuesManager;

	private SelectItem lSelectItemMittente;
	
	private FormItemIcon filtroDestinatariItem;
	private ComboBoxItem lComboBoxDestinatari;
	private ImgButtonItem lookupRubricaEmailButtonDestinatari;
	
	private FormItemIcon filtroDestinatariCCItem;
	private ComboBoxItem lComboBoxDestinatariCC;
	private ImgButtonItem lookupRubricaEmailButtonDestinatariCC;
	
	private TextItem lTextItemOggetto;
	private CheckboxItem lCheckboxItemSalvaInviati;

	private TextAreaItem lTextAreaItemBody;
	private TextAreaItem motivo;
	private RichTextItem lRichTextItemBody;

	private AttachmentItem lAttachmentItem;

	List<FormItem> items = new ArrayList<FormItem>();

	private CheckboxItem lCheckboxRilascia;

	public InvioNotificaInteropForm() {

		setWrapItemTitles(false);
		setNumCols(20);
		setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, "*", "*");
		
		SpacerItem lSpacerItem = new SpacerItem();
		lSpacerItem.setColSpan(1);
		lSpacerItem.setStartRow(true);

		//Mittenti
		buildMittenteItem();
		
		//Destinatari primari
		
		// Creazione filtro destinatari
		creaFiltroDestinatariPrimari();
		
		buildDestinatariItem(getValidatorEmail());

		// Creazione filtro destinatari
		creaFiltroDestinatariCC();
		
		// Bottone lookup per aprire la RUBRICA EMAIL
		//buildDestinatariLookupButton();
		
		// Destinatari CC
		buildDestinatariCCItem(getValidatorEmail());
		
		// Bottone lookup per aprire la RUBRICA EMAIL
		//buildDestinatariCCLookupButton();
		
		// Oggetto
		buildOggettoItem();
		
		//Motivo
		buildMotivoItem();
		
		// Attachment
		buildAttachmentItem();
		
		//items.add(lSpacerItem);

		// Casella testo TEXT
		//buildBodyTextItem();
		
		// Casella testo HTML
		buildBodyRichTextItem();
		
		items.add(lSpacerItem);
		// Salva mail
		buildSalvaInviatiItem();
		
		//items.add(lSpacerItem);
		//Invio separato
		//buildInvioSeparato();
		
		items.add(lSpacerItem);
		//Rilascia
		buildRilascia();

		lValuesManager = new ValuesManager();
		setValuesManager(lValuesManager);

		items.add(new HiddenItem("idEmail"));
		items.add(new HiddenItem("tipoNotifica"));
		items.add(new HiddenItem("categoriaPartenza"));
		items.add(new HiddenItem("textHtml"));
		items.add(new HiddenItem("recordFiltriDestinatari"));
		items.add(new HiddenItem("recordFiltriDestinatariCC"));

		setFields(items.toArray(new FormItem[items.size()]));

	}
	
	private void buildMittenteItem() {
		lSelectItemMittente = new SelectItem("mittente", I18NUtil.getMessages().invionotificainteropform_mittenteItem_title());
		lSelectItemMittente.setDisplayField("value");
		lSelectItemMittente.setValueField("key");
		lSelectItemMittente.setRequired(true);
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		accounts.addParam("finalita", "INVIO");
		lSelectItemMittente.setOptionDataSource(accounts);
		lSelectItemMittente.setEndRow(true);
		lSelectItemMittente.setWidth(COMPONENT_WIDTH);
		lSelectItemMittente.setColSpan(8);
		
		items.add(lSelectItemMittente);
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
	
	private void buildDestinatariItem(CustomValidator lCustomValidator) {
		
		GWTRestDataSource proposteDestinatariPrimariDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatari = new ComboBoxItem("destinatari", I18NUtil.getMessages().invionotificainteropform_destinatariItem_title());
		lComboBoxDestinatari.setRequired(true);
		lComboBoxDestinatari.setColSpan(8);
		lComboBoxDestinatari.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatari.setStartRow(true);
		lComboBoxDestinatari.setValidators(lCustomValidator);
		lComboBoxDestinatari.setValueField("indirizzoEmail");
		lComboBoxDestinatari.setDisplayField("indirizzoEmail");
		lComboBoxDestinatari.setShowPickerIcon(false);
		lComboBoxDestinatari.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatari.setRequired(true);
		lComboBoxDestinatari.setEndRow(false);
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

		items.add(lComboBoxDestinatari);
	}
	
//	private void buildDestinatariLookupButton() {
//		lookupRubricaEmailButtonDestinatari = new ImgButtonItem("lookupRubricaEmailButtonDestinatari", "lookup/rubricaemail.png", I18NUtil.getMessages()
//				.invioudmail_detail_lookupRubricaEmailItem_title());
//		lookupRubricaEmailButtonDestinatari.setEndRow(true);
//		lookupRubricaEmailButtonDestinatari.setColSpan(1);
//		lookupRubricaEmailButtonDestinatari.addIconClickHandler(new IconClickHandler() {
//
//			@Override
//			public void onIconClick(IconClickEvent event) {
//				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup("destinatari");
//				lookupRubricaEmailPopup.show();
//			}
//		});
//		items.add(lookupRubricaEmailButtonDestinatari);
//	}
	
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
	
	private void buildDestinatariCCItem(CustomValidator lCustomValidator) {
		
		GWTRestDataSource proposteDestinatariCCDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		lComboBoxDestinatariCC = new ComboBoxItem("destinatariCC", I18NUtil.getMessages().invionotificainteropform_destinatariCCItem_title());
		lComboBoxDestinatariCC.setColSpan(8);
		lComboBoxDestinatariCC.setWidth(COMPONENT_WIDTH + 2);
		lComboBoxDestinatariCC.setStartRow(true);
		lComboBoxDestinatariCC.setValidators(lCustomValidator);
		lComboBoxDestinatariCC.setValueField("indirizzoEmail");
		lComboBoxDestinatariCC.setDisplayField("indirizzoEmail");
		lComboBoxDestinatariCC.setShowPickerIcon(false);
		lComboBoxDestinatariCC.setTextBoxStyle(it.eng.utility.Styles.textItem);
		lComboBoxDestinatariCC.setEndRow(false);
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

		items.add(lComboBoxDestinatariCC);
	}

//	private void buildDestinatariCCLookupButton() {
//		lookupRubricaEmailButtonDestinatariCC = new ImgButtonItem("lookupRubricaEmailButtonDestinatariCC", "lookup/rubricaemail.png", I18NUtil.getMessages()
//				.invioudmail_detail_lookupRubricaEmailItem_title());
//		lookupRubricaEmailButtonDestinatariCC.setEndRow(true);
//		lookupRubricaEmailButtonDestinatariCC.addIconClickHandler(new IconClickHandler() {
//
//			@Override
//			public void onIconClick(IconClickEvent event) {
//				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup("destinatariCC");
//				lookupRubricaEmailPopup.show();
//			}
//		});
//
//		items.add(lookupRubricaEmailButtonDestinatariCC);
//	}

	private void buildSalvaInviatiItem() {
		lCheckboxItemSalvaInviati = new CheckboxItem("salvaInviati", I18NUtil.getMessages().invionotificainteropform_salvaInviatiItem_title());
		lCheckboxItemSalvaInviati.setWrapTitle(false);
		lCheckboxItemSalvaInviati.setColSpan(1);
		lCheckboxItemSalvaInviati.setWidth("*");
		lCheckboxItemSalvaInviati.setVisible(false);
		items.add(lCheckboxItemSalvaInviati);
	}
	
	private void buildRilascia() {
		lCheckboxRilascia = new CheckboxItem("flgRilascia", "Rilascia al termine dell’operazione");
		lCheckboxRilascia.setWrapTitle(false);
		lCheckboxRilascia.setColSpan(1);
		lCheckboxRilascia.setWidth("*");
		items.add(lCheckboxRilascia);
	}

	private void buildBodyRichTextItem() {
		lRichTextItemBody = new RichTextItem("bodyHtml");
		lRichTextItemBody.setDefaultValue("");
		lRichTextItemBody.setShowTitle(false);
		lRichTextItemBody.setCellStyle(it.eng.utility.Styles.textItem);
		lRichTextItemBody.setHeight(200);
		lRichTextItemBody.setWidth(COMPONENT_WIDTH);
		lRichTextItemBody.setColSpan(8);
		lRichTextItemBody.setStartRow(true);
		lRichTextItemBody.setControlGroups("fontControls", "formatControls", "styleControls", "colorControls", "insertControls");
		lRichTextItemBody.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return "html".equals(form.getValueAsString("textHtml"));
			}
		});
		items.add(lRichTextItemBody);
	}

	private void buildAttachmentItem() {
		lAttachmentItem = new AttachmentItem() {

			protected void manageIconClick(final String display, String uri) {
				Record lRecord = new Record(lValuesManager.rememberValues());
				new GWTRestService<Record, Record>("AurigaInvioNotificaInteropDatasource").executecustom("upedateXml", lRecord, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record lRecord = response.getData()[0];
							lRecord.setAttribute("displayFilename", display);
							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						}
					}
				});
			};
		};
		lAttachmentItem.setName("attach");
		lAttachmentItem.setTitle(I18NUtil.getMessages().invionotificainteropform_allegatiItem_title());
		lAttachmentItem.setWidth(1);
		lAttachmentItem.setStartRow(true);
		lAttachmentItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoNotifica = (String) form.getValue("tipoNotifica");
				if (tipoNotifica == null || tipoNotifica.trim().length() == 0) {
					return true;
				}
				String categoriaPartenza = (String) form.getValue("categoriaPartenza");

				if (categoriaPartenza == null || categoriaPartenza.trim().length() == 0) {
					return true;
				}

				if (tipoNotifica.equals("eccezione")) {
					return categoriaPartenza.equals("INTEROP_SEGN");
				} else {
					return true;
				}
			}
		});
		items.add(lAttachmentItem);
	}

	private void buildMotivoItem() {
		motivo = new TextAreaItem("motivo");
		motivo.setTitleColSpan(1);
		motivo.setDefaultValue("");
		motivo.setVisible(true);

		motivo.setShowTitle(true);
		motivo.setTitle("Motivo");
		motivo.setHeight(40);
		motivo.setWidth(COMPONENT_WIDTH);
		motivo.setColSpan(8);
		motivo.setStartRow(true);
		motivo.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoNotifica = (String) form.getValue("tipoNotifica");
				if (tipoNotifica == null || tipoNotifica.trim().length() == 0)
					return true;
				String categoriaPartenza = (String) form.getValue("categoriaPartenza");
				if (categoriaPartenza == null || categoriaPartenza.trim().length() == 0)
					return true;
				return tipoNotifica.equals("eccezione") && categoriaPartenza.equals("INTEROP_SEGN");
			}
		});
		items.add(motivo);
	}

	private void buildOggettoItem() {
		lTextItemOggetto = new TextItem("oggetto", I18NUtil.getMessages().invionotificainteropform_oggettoItem_title());
		lTextItemOggetto.setColSpan(8);
		lTextItemOggetto.setWidth(COMPONENT_WIDTH);
		lTextItemOggetto.setStartRow(true);
		items.add(lTextItemOggetto);
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
				String[] values = lString.split(";");
				boolean res = true;
				for (String val : values) {
					if (val == null || val.equals(""))
						res = res && true;
					else
						res = res && regExp.test(val.trim());
				}
				return res;
			}
		};
		lEmailRegExpValidator.setErrorMessage(I18NUtil.getMessages().invionotificainteropform_destinatariValidatorErrorMessage());
		return lEmailRegExpValidator;
	}

	public boolean isRequiredMotivo() {
		String tipoNotifica = (String) getValue("tipoNotifica");
		if (tipoNotifica == null || tipoNotifica.trim().length() == 0)
			return false;
		String categoriaPartenza = (String) getValue("categoriaPartenza");
		if (categoriaPartenza == null || categoriaPartenza.trim().length() == 0)
			return false;
		return tipoNotifica.equals("eccezione") && categoriaPartenza.equals("INTEROP_SEGN");
	}

	public void caricaMail(Record lRecord) {
		lValuesManager.editRecord(lRecord);
		motivo.setRequired(isRequiredMotivo());
	}

	public Record getRecord() {
		if (lValuesManager.validate()) {
			Record lRecord = new Record(lValuesManager.rememberValues());
			return lRecord;
		} else
			return null;
	}

	protected void manageChangeTextHtml(ChangedEvent event) {
		if (event.getValue().equals("text")) {
			lTextAreaItemBody.setValue(lRichTextItemBody.getValue());
			lRichTextItemBody.hide();
			lTextAreaItemBody.show();

		} else {
			lRichTextItemBody.setValue(lTextAreaItemBody.getValue());
			lTextAreaItemBody.hide();
			lRichTextItemBody.show();

		}
		markForRedraw();
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
	
	public void selezionaDestinatarioPrimario(Record record) {
		
		lComboBoxDestinatari.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		_this.markForRedraw();
	}
	
	public void selezionaDestinatarioSecondario(Record record,Boolean isCC) {
		if(isCC) {	
			lComboBoxDestinatariCC.setValue(record.getAttribute("indirizzoEmail").replace(" ", ";"));
		}
		_this.markForRedraw();
	}
	
	protected String buildIconHtml(String src, String value) {
		return "<div align=\"left\"><img src=\"images/" + src + "\" height=\"10\" width=\"10\" alt=\"\" />&nbsp;&nbsp;" + value + "</div>";
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

}