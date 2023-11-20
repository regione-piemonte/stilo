/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.ClassificaFascicoloItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.FolderCustomItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtEntrataItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class RegoleProtocollazioneAutomaticaCaselleDetail extends CustomDetail {

	private DynamicForm regoleForm;
	private DynamicForm caratteristicheEmailRegolaForm;
	private DynamicForm parametriProtocollazioneForm;
	
	private DetailSection detailSectionCaratteristiche;
	private DetailSection detailSectionParametriProtocollazione;

	private HiddenItem idRegolaItem;
	private TextItem nomeRegolaItem;
	private TextAreaItem descrizioneRegolaItem;
	private DateItem dataAttivazioneItem;
	private DateItem dataCessazioneItem;
	private DateItem dataInizioSospensioneItem;
	private DateItem dataFineSospensioneItem;
	private SelectItem caselleItem;
	private RadioGroupItem flgTipoEmailItem;
	private RadioGroupItem flgTipoEmailRicezioneItem;
	private IndirizzoMittenteItem indirizziMittentiItem;
	private DatiSegnaturaRegoleRegAutoCaselleItem datiSegnaturaRegoleRegAutoCaselleItem;
	private StringaInOggettoMailItem paroleInOggettoMailItem;
	private StringaNelTestoMailItem paroleInTestoMailItem;
	private EmailDestinatariRegoleRegAutoCaselleItem emailDestinatariItem;
	private RadioGroupItem flgTipoRegistrazioneItem;
	private SelectItem repertorioItem;
	private UoDestinatariaItem uoRegistrazioneItem;
	private FilteredSelectItemWithDisplay utenteRegistrazioneItem;
//	private ExtendedTextItem mittenteRegistrazioneItem;
	private MittenteProtItem mittentiItem;
	private UoDestinatariaItem uoDestinatariaItem;
	private CheckboxItem flgRiservatezzaItem;
	private ClassificaFascicoloItem classificazioneFascicolazioneItem;
	private FolderCustomItem folderCustomItem;
	private RadioGroupItem flgTimbraturaItem;
	private CheckboxItem flgNotificaConfermaItem;
	private ExtendedTextItem indirizzoDestinatarioRispostaItem;
	private ExtendedTextItem oggettoRispostaItem;
	private CKEditorItem testoRispostaItem;
	
	public static final String _FLG_TIPO_EMAIL_INT = "interoperabile";
	public static final String _FLG_TIPO_EMAIL_PEC = "mittentePEC";
	public static final String _FLG_TIPO_EMAIL_PEO = "mittentePEO";
	public static final String _FLG_TIPO_EMAIL_TUTTI = "";
	
	public static final String _FLG_TIPO_EMAIL_RICEZIONE_PEC = "PEC";
	public static final String _FLG_TIPO_EMAIL_RICEZIONE_PEO = "PEO";
	public static final String _FLG_TIPO_EMAIL_RICEZIONE_TUTTI = "";

	public static final String _FLG_TIPO_REGISTRAZIONE_P = "PG";
	public static final String _FLG_TIPO_REGISTRAZIONE_R = "R";
	public static final String _FLG_TIPO_REGISTRAZIONE_PP = "PP";
	
	public static final String _FLG_TIMBRATURA_T = "tutti";
	public static final String _FLG_TIMBRATURA_N = "";
	public static final String _FLG_TIMBRATURA_P = "solo_primario";
	
	public RegoleProtocollazioneAutomaticaCaselleDetail(String nomeEntita) {
		super(nomeEntita);
		
		regoleForm = new DynamicForm();
		regoleForm.setValuesManager(vm);  			
		regoleForm.setWidth("100%"); 
		regoleForm.setPadding(5);
		regoleForm.setWrapItemTitles(false);
		
		regoleForm.setNumCols(10);
		regoleForm.setColWidths(120, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		idRegolaItem = new HiddenItem("idRegola");
		
		nomeRegolaItem = new TextItem("nomeRegola", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_nomeRegolaItem_title());
		nomeRegolaItem.setAttribute("obbligatorio", true);
		nomeRegolaItem.setWidth(481);
		
		descrizioneRegolaItem = new TextAreaItem("descrizioneRegola", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_descrizioneRegolaItem_title());
		descrizioneRegolaItem.setStartRow(true);
		descrizioneRegolaItem.setWidth(481);
		descrizioneRegolaItem.setHeight(40);
		
		dataAttivazioneItem = new DateItem("dtAttivazione", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_dataAttivazioneItem_title());
		dataAttivazioneItem.setAttribute("obbligatorio", true);
		dataAttivazioneItem.setStartRow(true);
		dataAttivazioneItem.setEndRow(false);	
		dataAttivazioneItem.setColSpan(1);
		
		dataCessazioneItem = new DateItem("dtCessazione", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_dataCessazioneItem_title());
		dataCessazioneItem.setStartRow(false);
		dataCessazioneItem.setEndRow(true);	
		dataCessazioneItem.setColSpan(1);
		
		dataInizioSospensioneItem = new DateItem("dtInizioSospensione", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_dataInizioSospensioneItem());
		dataInizioSospensioneItem.setStartRow(true);
		dataInizioSospensioneItem.setEndRow(false);	
		dataInizioSospensioneItem.setColSpan(1);
		
		dataFineSospensioneItem = new DateItem("dtFineSospensione", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_dataFineSospensioneItem_title());
		dataFineSospensioneItem.setStartRow(false);
		dataFineSospensioneItem.setEndRow(true);	
		dataFineSospensioneItem.setColSpan(1);
		
		regoleForm.setItems(
				idRegolaItem,
				nomeRegolaItem,
				descrizioneRegolaItem,
				dataAttivazioneItem,
				dataCessazioneItem,
				dataInizioSospensioneItem,
				dataFineSospensioneItem
			);
		
		createCaratteristicheEmailRegolaForm();
		
		createParametriProtocollazioneForm();
		
		detailSectionCaratteristiche = new DetailSection(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_detailSectionCaratteristiche_title(), true, true, false, caratteristicheEmailRegolaForm);

		detailSectionParametriProtocollazione = new DetailSection(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_detailSectionParametriProtocollazione_title(), true, true, false, parametriProtocollazioneForm);
		
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		
		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
				
		lVLayout.addMember(regoleForm);
		lVLayout.addMember(detailSectionCaratteristiche);
		lVLayout.addMember(detailSectionParametriProtocollazione);
			
		addMember(lVLayout);
		addMember(lVLayoutSpacer);		
	}
	
	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);
//		codAmmMittenteItem.setCanEdit(false);
//		codAooMittenteItem.setCanEdit(false);
//		codRegistroMittenteItem.setCanEdit(false);
		if(this.mode != null && ("new".equals(this.mode) || "edit".equals(this.mode))) {
//			codiceRegolaItem.hide();
		} else {
//			codiceRegolaItem.show();
		}
	}
	
	private void createCaratteristicheEmailRegolaForm() {
		caratteristicheEmailRegolaForm = new DynamicForm();
		caratteristicheEmailRegolaForm.setValuesManager(vm);
		caratteristicheEmailRegolaForm.setWidth("100%");
		caratteristicheEmailRegolaForm.setPadding(5);
		caratteristicheEmailRegolaForm.setWrapItemTitles(false);
		caratteristicheEmailRegolaForm.setNumCols(10);
		caratteristicheEmailRegolaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		GWTRestDataSource caselleDS = new GWTRestDataSource("CasellaRicezioneSelectDataSource", "key", FieldType.TEXT);
//		caselleDS.addParam("altriParamLoadCombo", "");
		 		
		caselleItem = new SelectItem("caselle", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_caselleItem_title());
		caselleItem.setWidth(200);
		caselleItem.setStartRow(true);
		caselleItem.setMultiple(true);
		caselleItem.setValueField("key");
		caselleItem.setDisplayField("value");
		caselleItem.setOptionDataSource(caselleDS);		
//		caselleItem.setAttribute("obbligatorio", true);
		
		flgTipoEmailRicezioneItem = new RadioGroupItem("flgTipoEmailRicezione", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgTipoEmailRicezioneItem_title());
		Map<String, String> flgTipoEmailRicezioneValueMap = new HashMap<String, String>();
		flgTipoEmailRicezioneValueMap.put(_FLG_TIPO_EMAIL_RICEZIONE_PEC, "pec");
		flgTipoEmailRicezioneValueMap.put(_FLG_TIPO_EMAIL_RICEZIONE_PEO, "peo");
		flgTipoEmailRicezioneValueMap.put(_FLG_TIPO_EMAIL_RICEZIONE_TUTTI, "tutti");
		flgTipoEmailRicezioneItem.setValueMap(flgTipoEmailRicezioneValueMap);
		flgTipoEmailRicezioneItem.setDefaultValue(_FLG_TIPO_EMAIL_RICEZIONE_TUTTI);
		flgTipoEmailRicezioneItem.setVertical(false);
		flgTipoEmailRicezioneItem.setWrap(false);
		flgTipoEmailRicezioneItem.setShowDisabled(false);
//		flgTipoEmailRicezioneItem.addChangeHandler(new ChangeHandler() {
//			
//			@Override
//			public void onChange(ChangeEvent event) {
//				String valueFfgTipoEmail = (String) caratteristicheEmailRegolaForm.getValue("flgTipoEmailRicezione");
//				String value = (String) event.getValue();
//					if (valueFfgTipoEmail.equals(value)) {										
//						caratteristicheEmailRegolaForm.setValue("flgTipoEmailRicezione", "");
//					} else {
//						caratteristicheEmailRegolaForm.setValue("flgTipoEmailRicezione", value);
//					}
//					caratteristicheEmailRegolaForm.redraw();
//			}
//		});
		
		indirizziMittentiItem = new IndirizzoMittenteItem();
		indirizziMittentiItem.setName("listaIndirizziMittenti");
		indirizziMittentiItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_indirizziMittentiItem_title());
		indirizziMittentiItem.setShowTitle(true);
		indirizziMittentiItem.setStartRow(true);
		
		flgTipoEmailItem = new RadioGroupItem("flgTipoEmailEntrata", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgTipoEmailItem_title());
		Map<String, String> flgTipoEmailValueMap = new HashMap<String, String>();
		flgTipoEmailValueMap.put(_FLG_TIPO_EMAIL_INT, "solo interoperabili");
		flgTipoEmailValueMap.put(_FLG_TIPO_EMAIL_PEC, "solo pec");
		flgTipoEmailValueMap.put(_FLG_TIPO_EMAIL_PEO, "solo peo");
		flgTipoEmailValueMap.put(_FLG_TIPO_EMAIL_TUTTI, "tutti");
		flgTipoEmailItem.setValueMap(flgTipoEmailValueMap);
		flgTipoEmailItem.setDefaultValue(_FLG_TIPO_EMAIL_TUTTI);
		flgTipoEmailItem.setStartRow(true);
		flgTipoEmailItem.setVertical(false);
		flgTipoEmailItem.setWrap(false);
		flgTipoEmailItem.setShowDisabled(false);
		
		datiSegnaturaRegoleRegAutoCaselleItem = new DatiSegnaturaRegoleRegAutoCaselleItem();
		datiSegnaturaRegoleRegAutoCaselleItem.setName("listaDatiSegnatura");
		datiSegnaturaRegoleRegAutoCaselleItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_datiSegnaturaRegoleRegAutoCaselleItem_title());
		datiSegnaturaRegoleRegAutoCaselleItem.setShowTitle(true);
		datiSegnaturaRegoleRegAutoCaselleItem.setStartRow(true);
		
		paroleInOggettoMailItem = new StringaInOggettoMailItem();
		paroleInOggettoMailItem.setName("listaParoleInOggettoMail");
		paroleInOggettoMailItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_paroleInOggettoMailItem_title());
		paroleInOggettoMailItem.setShowTitle(true);
		paroleInOggettoMailItem.setStartRow(true);
		
		paroleInTestoMailItem = new StringaNelTestoMailItem();
		paroleInTestoMailItem.setName("listaParoleInTestoMail");
		paroleInTestoMailItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_paroleInTestoMailItem_title());
		paroleInTestoMailItem.setShowTitle(true);
		paroleInTestoMailItem.setStartRow(true);
		
		emailDestinatariItem = new EmailDestinatariRegoleRegAutoCaselleItem();
		emailDestinatariItem.setName("listaEmailDestinatari");
		emailDestinatariItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_emailDestinatariItem_title());
		emailDestinatariItem.setShowTitle(true);
		emailDestinatariItem.setStartRow(true);
		
		caratteristicheEmailRegolaForm.setItems(
				caselleItem,
				flgTipoEmailRicezioneItem,
				indirizziMittentiItem,
				flgTipoEmailItem,
				datiSegnaturaRegoleRegAutoCaselleItem,
				paroleInOggettoMailItem,
				paroleInTestoMailItem,
				emailDestinatariItem
				);
	}
	
	private void createParametriProtocollazioneForm() {
		addDrawHandler(new DrawHandler() {

			@Override
			public void onDraw(DrawEvent event) {
				String valueAsString = getValueAsString("flgNotificaConferma");
				if (valueAsString != null) {
					indirizzoDestinatarioRispostaItem.setCanEdit(new Boolean(valueAsString));
					oggettoRispostaItem.setCanEdit(new Boolean(valueAsString));
					testoRispostaItem.setCanEdit(new Boolean(valueAsString));
				}
			}
		});

		parametriProtocollazioneForm = new DynamicForm();
		parametriProtocollazioneForm.setValuesManager(vm);
		parametriProtocollazioneForm.setWidth("100%");
		parametriProtocollazioneForm.setPadding(5);
		parametriProtocollazioneForm.setWrapItemTitles(false);
		parametriProtocollazioneForm.setNumCols(10);
		parametriProtocollazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		flgTipoRegistrazioneItem = new RadioGroupItem("flgTipoRegistrazione", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgTipoRegistrazioneItem_title());
		Map<String, String> flgTipoRegistrazioneValueMap = new HashMap<String, String>();
		flgTipoRegistrazioneValueMap.put(_FLG_TIPO_REGISTRAZIONE_P, "Protocollo Generale");
		flgTipoRegistrazioneValueMap.put(_FLG_TIPO_REGISTRAZIONE_R, "Repertorio");
		flgTipoRegistrazioneValueMap.put(_FLG_TIPO_REGISTRAZIONE_PP, "Protocollo Particolare");
		flgTipoRegistrazioneItem.setValueMap(flgTipoRegistrazioneValueMap);
		flgTipoRegistrazioneItem.setDefaultValue(_FLG_TIPO_REGISTRAZIONE_P);
		flgTipoRegistrazioneItem.setVertical(false);
		flgTipoRegistrazioneItem.setWrap(false);
		flgTipoRegistrazioneItem.setShowDisabled(false);
		flgTipoRegistrazioneItem.setAttribute("obbligatorio", true);
		flgTipoRegistrazioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if(getValueAsString("flgTipoRegistrazione").equals(_FLG_TIPO_REGISTRAZIONE_R)) {
					repertorioItem.clearValue();
					repertorioItem.show();
					repertorioItem.setAttribute("obbligatorio", true);
				} else if (getValueAsString("flgTipoRegistrazione").equals(_FLG_TIPO_REGISTRAZIONE_PP)) {
					repertorioItem.clearValue();
					repertorioItem.show();
					repertorioItem.setAttribute("obbligatorio", true);
				} else {
					repertorioItem.clearValue();
					repertorioItem.hide();
					repertorioItem.setAttribute("obbligatorio", false);
				}
			}
		});

		GWTRestDataSource gruppiRepertorioDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		//		gruppiRepertorioDS.addParam("", "");

		repertorioItem = new SelectItem("repertorio", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_repertorioItem_title());
		repertorioItem.setValueField("key");
		repertorioItem.setDisplayField("value");
		repertorioItem.setStartRow(true);
		repertorioItem.setOptionDataSource(gruppiRepertorioDS);
		repertorioItem.setWidth(200);
		repertorioItem.setClearable(true);
		repertorioItem.setCachePickListResults(false);
		repertorioItem.setAllowEmptyValue(true);
		repertorioItem.setAutoFetchData(false);
		repertorioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return getValueAsString("flgTipoRegistrazione").equals(_FLG_TIPO_REGISTRAZIONE_R) || getValueAsString("flgTipoRegistrazione").equals(_FLG_TIPO_REGISTRAZIONE_PP);
			}
		});

		uoRegistrazioneItem = new UoDestinatariaItem() {
			@Override
			public boolean showFlgAssegnaAlDestinatarioItem() {
				return false;
			}
		};
		uoRegistrazioneItem.setNotReplicable(true);
		uoRegistrazioneItem.setName("uoRegistrazione");
		uoRegistrazioneItem.setShowTitle(true);
		uoRegistrazioneItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_uoRegistrazioneItem_title());
		uoRegistrazioneItem.setStartRow(true);
		uoRegistrazioneItem.setAttribute("obbligatorio", true);
		uoRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});	

		SelectGWTRestDataSource utentiDS = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,	new String[] {"cognomeNome"/*, "username"*/}, true);

		utenteRegistrazioneItem = new FilteredSelectItemWithDisplay("idUtente", utentiDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				parametriProtocollazioneForm.setValue("codiceRapido", record.getAttributeAsString("codice"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				parametriProtocollazioneForm.setValue("idUtente", "");
				parametriProtocollazioneForm.setValue("codiceRapido", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					parametriProtocollazioneForm.setValue("idUtente", "");
					parametriProtocollazioneForm.setValue("codiceRapido", "");
				}
			}
		};
		utenteRegistrazioneItem.setAutoFetchData(false);
		utenteRegistrazioneItem.setAlwaysFetchMissingValues(true);
		utenteRegistrazioneItem.setFetchMissingValues(true);
		utenteRegistrazioneItem.setStartRow(true);

		ListGridField utentiCodiceField = new ListGridField("codice", "Cod.");
		utentiCodiceField.setWidth(90);
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//
		ListGridField utentiUsernameField = new ListGridField("username", "Username");
		utentiUsernameField.setWidth(90);
		utenteRegistrazioneItem.setPickListFields(utentiCodiceField, utentiCognomeNomeField, utentiUsernameField);
		utenteRegistrazioneItem.setFilterLocally(true);
		utenteRegistrazioneItem.setValueField("idUtente");
		utenteRegistrazioneItem.setOptionDataSource(utentiDS);
		utenteRegistrazioneItem.setShowTitle(true);
		utenteRegistrazioneItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_utenteRegistrazioneItem_title());
		utenteRegistrazioneItem.setWidth(450);
		utenteRegistrazioneItem.setRequired(true);
		utenteRegistrazioneItem.setClearable(true);
		utenteRegistrazioneItem.setShowIcons(true);		
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utenteRegistrazioneItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utenteRegistrazioneItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});

		//		mittenteRegistrazioneItem = new ExtendedTextItem("mittenteRegistrazione", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_mittenteRegistrazioneItem_title());
		//		mittenteRegistrazioneItem.setWidth(450);
		//		mittenteRegistrazioneItem.setStartRow(true);
		//		mittenteRegistrazioneItem.setColSpan(1);

		mittentiItem = new MittenteProtEntrataItem() {

			@Override
			public boolean getShowItemsIndirizzo() {
				return false;
			}

			@Override
			public boolean isAttivoAssegnatarioUnicoProt() {
				return true;
			}
		};
		mittentiItem.setStartRow(true);
		mittentiItem.setNotReplicable(true);
		mittentiItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_mittenteRegistrazioneItem_title());
		mittentiItem.setName("mittenteRegistrazione");

		uoDestinatariaItem = new UoDestinatariaItem();
		uoDestinatariaItem.setName("listaUoDestinatarie");
		uoDestinatariaItem.setShowTitle(true);
		uoDestinatariaItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_uoDestinatariaItem_title());
		uoDestinatariaItem.setStartRow(true);
		uoDestinatariaItem.setAttribute("obbligatorio", true);
		uoDestinatariaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});	

		//		uoAssegnatariaItem = new UoDestinatariaItem();
		//		uoAssegnatariaItem.setName("listaUoAssegnatarie");
		//		uoAssegnatariaItem.setShowTitle(true);
		//		uoAssegnatariaItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_uoAssegnatariaItem_title());
		//		uoAssegnatariaItem.setStartRow(true);
		//		uoAssegnatariaItem.setAttribute("obbligatorio", true);
		//		uoAssegnatariaItem.setShowIfCondition(new FormItemIfFunction() {
		//			
		//			@Override
		//			public boolean execute(FormItem item, Object value, DynamicForm form) {
		//				return true;
		//			}
		//		});	
		//		
		//		uoPerConoscenzaItem = new UoDestinatariaItem();
		//		uoPerConoscenzaItem.setName("listaUoPerConoscenza");
		//		uoPerConoscenzaItem.setShowTitle(true);
		//		uoPerConoscenzaItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_uoPerConoscenzaItem_title());
		//		uoPerConoscenzaItem.setStartRow(true);
		//		uoPerConoscenzaItem.setAttribute("obbligatorio", true);
		//		uoPerConoscenzaItem.setShowIfCondition(new FormItemIfFunction() {
		//			
		//			@Override
		//			public boolean execute(FormItem item, Object value, DynamicForm form) {
		//				return true;
		//			}
		//		});	

		flgRiservatezzaItem = new CheckboxItem("flgRiservatezza", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgRiservatezzaItem_title());
		flgRiservatezzaItem.setColSpan(1);
		flgRiservatezzaItem.setStartRow(true);
		flgRiservatezzaItem.setShowTitle(true);

		classificazioneFascicolazioneItem = new ClassificaFascicoloItem();
		classificazioneFascicolazioneItem.setName("classificazioneFascicolazione");
		classificazioneFascicolazioneItem.setStartRow(true);
		classificazioneFascicolazioneItem.setShowTitle(true);
		classificazioneFascicolazioneItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_classificazioneFascicolazioneItem_title());
		classificazioneFascicolazioneItem.setAttribute("obbligatorio", true);

		folderCustomItem = new FolderCustomItem();
		folderCustomItem.setName("listaFolderCustom");
		folderCustomItem.setStartRow(true);
		folderCustomItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_folderCustomItem_title());
		folderCustomItem.setShowTitle(true);

		flgTimbraturaItem = new RadioGroupItem("flgTimbratura", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgTimbraturaItem_title());
		Map<String, String> flgTimbraturaValueMap = new HashMap<String, String>();
		flgTimbraturaValueMap.put(_FLG_TIMBRATURA_T, "tutti");
		flgTimbraturaValueMap.put(_FLG_TIMBRATURA_P, "solo primario");
		flgTimbraturaValueMap.put(_FLG_TIMBRATURA_N, "nessuno");
		flgTimbraturaItem.setValueMap(flgTimbraturaValueMap);
		flgTimbraturaItem.setDefaultValue(_FLG_TIMBRATURA_N);
		flgTimbraturaItem.setVertical(false);
		flgTimbraturaItem.setWrap(false);
		flgTimbraturaItem.setStartRow(true);
		flgTimbraturaItem.setShowDisabled(false);

		flgNotificaConfermaItem = new CheckboxItem("flgNotificaConferma", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgNotificaConfermaItem_title());
		flgNotificaConfermaItem.setColSpan(1);
		flgNotificaConfermaItem.setShowTitle(true);
		flgNotificaConfermaItem.setStartRow(true);
		flgNotificaConfermaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				boolean checked = event.getValue() != null && (Boolean) event.getValue();
				indirizzoDestinatarioRispostaItem.clearValue();
				oggettoRispostaItem.clearValue();
				testoRispostaItem.clearValue();
				indirizzoDestinatarioRispostaItem.setCanEdit(checked);
				oggettoRispostaItem.setCanEdit(checked);
				testoRispostaItem.setCanEdit(checked);
			}
		});

		indirizzoDestinatarioRispostaItem = new ExtendedTextItem("indirizzoDestinatarioRisposta", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_indirizzoDestinatarioRispostaItem_title());
		indirizzoDestinatarioRispostaItem.setWidth(450);
		indirizzoDestinatarioRispostaItem.setStartRow(true);
		indirizzoDestinatarioRispostaItem.setColSpan(1);
		
		oggettoRispostaItem = new ExtendedTextItem("oggettoRisposta", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_oggettoRispostaItem_title());
		oggettoRispostaItem.setWidth(450);
		oggettoRispostaItem.setStartRow(true);
		oggettoRispostaItem.setColSpan(1);

		testoRispostaItem = new CKEditorItem("testoRisposta", -1, "restricted", 10, -1, "");
		testoRispostaItem.setShowTitle(true);
		testoRispostaItem.setTitle(I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_testoRispostaItem_title());
		testoRispostaItem.setColSpan(20);
		testoRispostaItem.setWidth("100%");

		parametriProtocollazioneForm.setItems(
				flgTipoRegistrazioneItem,
				repertorioItem,
				uoRegistrazioneItem,
				utenteRegistrazioneItem,
				//				mittenteRegistrazioneItem,
				mittentiItem,
				uoDestinatariaItem,
				//				uoAssegnatariaItem,
				//				uoPerConoscenzaItem,
				flgRiservatezzaItem,
				classificazioneFascicolazioneItem,
				//				folderCustomItem,
				flgTimbraturaItem,
				flgNotificaConfermaItem,
				indirizzoDestinatarioRispostaItem,
				oggettoRispostaItem,
				testoRispostaItem
				);
	}
	
	public String getValueAsString(String fieldName) {
		return vm.getValue(fieldName) != null ? (String) vm.getValue(fieldName) : "";
	}
	
	public void loadDettaglioAfterSave(String idRegola, final ServiceCallback<Record> callback) {
		loadDettaglio(idRegola, true, callback);
	}
	
	private void loadDettaglio(String idRegola, final Boolean afterSave, final ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("idRegola", idRegola);
		final GWTRestDataSource lRegoleProtocollazioneAutomaticaCaselleDatasource = new GWTRestDataSource("RegoleProtocollazioneAutomaticaCaselleDatasource");
		lRegoleProtocollazioneAutomaticaCaselleDatasource.getData(lRecord, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record dettaglio = response.getData()[0];
						if ((afterSave && dettaglio != null) || (dettaglio != null && dettaglio.getAttribute("abilModificabile") != null && dettaglio.getAttributeAsBoolean("abilModificabile"))) {
							if(callback != null) {
								callback.execute(response.getData()[0]);
							}	
						} else {
							Layout.addMessage(new MessageBean("Non sei abilitato", "", MessageType.ERROR));
						}
					} 
				}
			}
		});
	}
}
