/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class SoggettiDetail extends CustomDetail {

	protected SoggettiDetail _instance;

	// DynamicForm
	protected DynamicForm soggettiForm;
	protected DynamicForm estremiSuIpaForm;
	protected DynamicForm nascitaIstituzioneForm;
	protected DynamicForm cessazioneForm;
	protected DynamicForm indirizziForm;
	protected DynamicForm altreDenominazioniForm;
	protected AssegnazioneUoForms assegnaAdUoForms;
	protected DynamicForm contattiForm;
	
	// DetailSection
	protected DetailSection soggettiSection;
	protected DetailSection assegnaAdUOSection;
	protected DetailSection estremiSuIpaSection;
	protected DetailSection nascitaIstituzioneSection;
	protected DetailSection cessazioneSection;
	protected DetailSection indirizziSection;
	protected DetailSection altreDenominazioniSection;
	protected DetailSection contattiSection;
	
	
	// HiddenItem
	protected HiddenItem idSoggettoItem;
	protected HiddenItem flgDiSistemaItem;
	protected HiddenItem flgValidoItem;
	protected HiddenItem flgIgnoreWarningItem;
	protected HiddenItem flgInOrganigrammaItem;
	protected HiddenItem nomeComuneNascitaIstituzioneItem;
	
	// SelectItem
	protected SelectItem tipoItem;
	protected SelectItem sottotipoItem;
	
	// TextItem
	protected TextItem codiceRapidoItem;
	protected TextItem cognomeItem;
	protected TextItem nomeItem;
	protected TextItem titoloItem;
	protected TextItem denominazioneItem;
	protected TextItem codiceFiscaleItem;
	protected TextItem partitaIvaItem;
	protected SelectItem sessoItem;
	protected SelectItem condizioneGiuridicaItem;
	protected SelectItem causaleCessazioneItem;
	
	// FilteredSelectItem
	protected FilteredSelectItem cittadinanzaItem;
	protected FilteredSelectItem statoNascitaIstituzioneItem;
	protected FilteredSelectItem comuneNascitaIstituzioneItem;
	
	// TextItem
	protected TextItem codiceAmmInIpaItem;
	protected TextItem codiceAooInIpaItem;
	protected TextItem codiceUoInIpaItem;
	protected TextItem provNascitaIstituzioneItem;
	protected TextItem cittaNascitaIstituzioneItem;

	// DateItem
	protected DateItem dataNascitaIstituzioneItem;
	protected DateItem dataCessazioneItem;
	
	// ReplicableItem
	protected IndirizziSoggettoItem indirizziItem;
	protected ContattiSoggettoItem contattiItem;
	protected AltreDenominazioniSoggettoItem altreDenominazioniItem;

	protected boolean editing;

	public SoggettiDetail(String nomeEntita) {

		super(nomeEntita);

		_instance = this;

		final GWTRestDataSource sottotipoSoggettoDS = new GWTRestDataSource("SottotipoSoggettoDataSource", "key", FieldType.TEXT);
		final GWTRestDataSource cittadinanzaSoggettoDS = new GWTRestDataSource("StatoDataSource", "codIstatStato", FieldType.TEXT, true);
		cittadinanzaSoggettoDS.addParam("flgSoloVld", "1");
		final GWTRestDataSource condizioneGiuridicaSoggettoDS = new GWTRestDataSource("CondGiuridicaSoggettoDataSource", "key", FieldType.TEXT);
		final GWTRestDataSource comuneNascitaIstituzioneDS = new GWTRestDataSource("ComuneDataSource", "codIstatComune", FieldType.TEXT, true);
		final GWTRestDataSource statoNascitaIstituzioneDS = new GWTRestDataSource("StatoDataSource", "codIstatStato", FieldType.TEXT, true);
		final GWTRestDataSource causaleCessazioneDS = new GWTRestDataSource("CausaleCessazioneSoggettoDataSource", "key", FieldType.TEXT);

		soggettiForm = new DynamicForm();
		soggettiForm.setValuesManager(vm);
		soggettiForm.setWidth("100%");
		soggettiForm.setHeight("5");
		soggettiForm.setPadding(5);
		soggettiForm.setNumCols(12);
		soggettiForm.setWrapItemTitles(false);

		idSoggettoItem = new HiddenItem("idSoggetto");

		flgDiSistemaItem = new HiddenItem("flgDiSistema");
		flgValidoItem = new HiddenItem("flgValido");

		flgIgnoreWarningItem = new HiddenItem("flgIgnoreWarning");
		flgIgnoreWarningItem.setDefaultValue(0);

		flgInOrganigrammaItem = new HiddenItem("flgInOrganigramma");

		tipoItem = new SelectItem("tipo", I18NUtil.getMessages().soggetti_detail_tipoItem_title()) {

			@Override
			public void setValue(String value) {
				
				super.setValue(value);
				if (value.equals("UP") || value.equals("#AF")) {
					nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().soggetti_detail_nascitaSection_title());
					indirizziItem.validateTipo("1");
					contattiItem.validateTipo("1");
				} else {
					nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().soggetti_detail_istituzioneSection_title());
					indirizziItem.validateTipo(null);
					contattiItem.validateTipo(null);
				}
				if (value.equals("#APA") || value.equals("#IAMM") || value.equals("UO;UOI")) {
					estremiSuIpaSection.show();
				} else {
					estremiSuIpaSection.hide();
				}
				_instance.redraw();
			}
		};
		
		tipoItem.setRequired(true);
		tipoItem.setWidth(200);
		tipoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				String tipo = soggettiForm.getValue("tipo") != null ? soggettiForm.getValueAsString("tipo") : "";
				if (tipo.equals("UP") || tipo.equals("#AF")) {
					nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().soggetti_detail_nascitaSection_title());
					indirizziItem.validateTipo("1");
					contattiItem.validateTipo("1");
				} else {
					nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().soggetti_detail_istituzioneSection_title());
					indirizziItem.validateTipo(null);
					contattiItem.validateTipo(null);
				}
				if (tipo.equals("#APA") || tipo.equals("#IAMM") || tipo.equals("UO;UOI")) {
					estremiSuIpaSection.show();
				} else {
					estremiSuIpaSection.hide();
				}
				_instance.redraw();
			}
		});

		sottotipoItem = new SelectItem("sottotipo", I18NUtil.getMessages().soggetti_detail_sottotipoItem_title()) {

			@Override
			public void setValue(String value) {
				
				super.setValue(value);
				if (!"".equals(value) && (value.startsWith("PA") || value.startsWith("AOO") || value.startsWith("UO"))) {
					estremiSuIpaSection.show();
				} else {
					estremiSuIpaSection.hide();
				}
			}
		};
		sottotipoItem.setValueField("key");
		sottotipoItem.setDisplayField("value");
		sottotipoItem.setOptionDataSource(sottotipoSoggettoDS);
		sottotipoItem.setWidth(200);
		sottotipoItem.setClearable(true);
		sottotipoItem.setCachePickListResults(false);
		sottotipoItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {

				if (tipoItem.getValue() != null && !"".equals((String) tipoItem.getValue())) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("tipo", OperatorId.EQUALS, (String) tipoItem.getValue());
					return new AdvancedCriteria(OperatorId.AND, criterias);
				}
				return null;
			}
		});
		sottotipoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String tipo = soggettiForm.getValue("tipo") != null ? soggettiForm.getValueAsString("tipo") : "";
				return tipo != null && (tipo.equals("#APA") || tipo.equals("#IAMM") || tipo.equals("#AG"));
			}
		});

		cognomeItem = new TextItem("cognome", I18NUtil.getMessages().soggetti_detail_cognomeItem_title());
		cognomeItem.setWidth(200);
		cognomeItem.setStartRow(true);
		cognomeItem.setAttribute("obbligatorio", true);
		cognomeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});
		cognomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return isPersonaFisica();
			}
		}));

		nomeItem = new TextItem("nome", I18NUtil.getMessages().soggetti_detail_nomeItem_title());
		nomeItem.setWidth(200);
		nomeItem.setAttribute("obbligatorio", true);
		nomeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});
		nomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return isPersonaFisica();
			}
		}));

		titoloItem = new TextItem("titolo", I18NUtil.getMessages().soggetti_detail_titoloItem_title());
		titoloItem.setWidth(200);
		titoloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});
		titoloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});

		denominazioneItem = new TextItem("denominazione", I18NUtil.getMessages().soggetti_detail_denominazioneItem_title());
		denominazioneItem.setWidth(200);
		denominazioneItem.setStartRow(true);
		denominazioneItem.setAttribute("obbligatorio", true);
		denominazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !isPersonaFisica();
			}
		});
		denominazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return !isPersonaFisica();
			}
		}));

		codiceRapidoItem = new TextItem("codiceRapido", I18NUtil.getMessages().soggetti_detail_codiceRapidoItem_title());
		codiceRapidoItem.setWidth(120);

		codiceFiscaleItem = new TextItem("codiceFiscale", I18NUtil.getMessages().soggetti_detail_codiceFiscaleItem_title());
		codiceFiscaleItem.setWidth(200);
		codiceFiscaleItem.setStartRow(true);
		codiceFiscaleItem.setLength(16);
		codiceFiscaleItem.setWrapTitle(false);
		codiceFiscaleItem.setCharacterCasing(CharacterCasing.UPPER);
		codiceFiscaleItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				if (value == null || "".equals((String) value))
					return true;
				RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
				return regExp.test((String) value);
			}
		});

		partitaIvaItem = new TextItem("partitaIva", I18NUtil.getMessages().soggetti_detail_partitaIvaItem_title());
		partitaIvaItem.setWidth(200);
		partitaIvaItem.setLength(11);
		partitaIvaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !isPersonaFisica();
			}
		});
		partitaIvaItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				if (value == null || "".equals((String) value))
					return true;
				if (isPersonaFisica()) {
					return true;
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.partitaIvaRegExp());
					return regExp.test((String) value);
				}
			}
		});

		sessoItem = new SelectItem("sesso", I18NUtil.getMessages().soggetti_detail_sessoItem_title());
		sessoItem.setWidth(100);
		sessoItem.setAllowEmptyValue(true);
		LinkedHashMap<String, String> sessoValueMap = new LinkedHashMap<String, String>();
		sessoValueMap.put("M", I18NUtil.getMessages().soggetti_sesso_M_value());
		sessoValueMap.put("F", I18NUtil.getMessages().soggetti_sesso_F_value());
		sessoItem.setValueMap(sessoValueMap);
		sessoItem.setDefaultValue((String) null);
		sessoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});

		cittadinanzaItem = new FilteredSelectItem("cittadinanza", I18NUtil.getMessages().soggetti_detail_cittadinanzaItem_title());
		ListGridField codIstatStatoCittadinanzaField = new ListGridField("codIstatStato", "Cod.");
		codIstatStatoCittadinanzaField.setHidden(true);
		ListGridField nomeStatoCittadinanzaField = new ListGridField("nomeStato", "Stato");
		cittadinanzaItem.setPickListFields(codIstatStatoCittadinanzaField, nomeStatoCittadinanzaField);
		cittadinanzaItem.setValueField("codIstatStato");
		cittadinanzaItem.setDisplayField("nomeStato");
		cittadinanzaItem.setOptionDataSource(cittadinanzaSoggettoDS);
		cittadinanzaItem.setWidth(198);
		cittadinanzaItem.setClearable(true);
		cittadinanzaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isPersonaFisica();
			}
		});

		condizioneGiuridicaItem = new SelectItem("condizioneGiuridica", I18NUtil.getMessages().soggetti_detail_condizioneGiuridicaItem_title());
		condizioneGiuridicaItem.setValueField("key");
		condizioneGiuridicaItem.setDisplayField("value");
		condizioneGiuridicaItem.setOptionDataSource(condizioneGiuridicaSoggettoDS);
		condizioneGiuridicaItem.setWidth(200);
		condizioneGiuridicaItem.setClearable(true);
		condizioneGiuridicaItem.setCachePickListResults(false);
		condizioneGiuridicaItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				if (idSoggettoItem.getValue() != null && !"".equals((String) idSoggettoItem.getValue())) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("idSoggetto", OperatorId.EQUALS, (String) idSoggettoItem.getValue());
					return new AdvancedCriteria(OperatorId.AND, criterias);
				}
				return null;
			}
		});
		condizioneGiuridicaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return !isPersonaFisica();
			}
		});

		soggettiForm.setItems(idSoggettoItem, flgDiSistemaItem,
				flgValidoItem,
				flgIgnoreWarningItem,
				flgInOrganigrammaItem,
				tipoItem, sottotipoItem, cognomeItem, nomeItem, titoloItem, denominazioneItem, codiceRapidoItem, codiceFiscaleItem, partitaIvaItem, sessoItem,
				cittadinanzaItem, condizioneGiuridicaItem);

		// Verifico se è abiliato il partizionamento della rubrica
		if (SoggettiLayout.isPartizionamentoRubricaAbilitato()) {
			assegnaAdUoForms = new AssegnazioneUoForms("", vm) {

				public String getFinalitaForLookupOrganigramma() {
					return "ALTRO";
				}

				public String getFinalitaForComboOrganigramma() {
					return "ALTRO";
				}

				public boolean isPartizionamentoRubricaAbilitato() {
					return SoggettiLayout.isPartizionamentoRubricaAbilitato();
				}

				public boolean isAbilInserireModificareSoggInQualsiasiUo() {
					return SoggettiLayout.isAbilInserireModificareSoggInQualsiasiUo();
				}
			};			
			assegnaAdUOSection = new DetailSection(I18NUtil.getMessages().soggetti_detail_assegnaAdUoSection_title(), true, true, true,	assegnaAdUoForms.getForms());
			assegnaAdUOSection.setVisible(assegnaAdUoForms.isOrganigrammaFormVisible());			
		}

		estremiSuIpaForm = new DynamicForm();
		estremiSuIpaForm.setValuesManager(vm);
		estremiSuIpaForm.setWidth("100%");
		estremiSuIpaForm.setHeight("5");
		estremiSuIpaForm.setPadding(5);
		estremiSuIpaForm.setNumCols(12);
		estremiSuIpaForm.setWrapItemTitles(false);

		codiceAmmInIpaItem = new TextItem("codiceAmmInIpa", I18NUtil.getMessages().soggetti_detail_codiceAmmInIpaItem_title());
		codiceAmmInIpaItem.setWidth(100);

		codiceAooInIpaItem = new TextItem("codiceAooInIpa", I18NUtil.getMessages().soggetti_detail_codiceAooInIpaItem_title());
		codiceAooInIpaItem.setWidth(100);
		codiceAooInIpaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String tipo = soggettiForm.getValue("tipo") != null ? soggettiForm.getValueAsString("tipo") : "";
				return !tipo.equals("#APA");
			}
		});

		codiceUoInIpaItem = new TextItem("codiceUoInIpa", I18NUtil.getMessages().soggetti_detail_codiceUoInIpaItem_title());
		codiceUoInIpaItem.setWidth(120);
		codiceUoInIpaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String tipo = soggettiForm.getValue("tipo") != null ? soggettiForm.getValueAsString("tipo") : "";
				return !tipo.equals("UO;UOI");
			}
		});

		estremiSuIpaForm.setItems(codiceAmmInIpaItem, codiceAooInIpaItem, codiceUoInIpaItem);

		nascitaIstituzioneForm = new DynamicForm();
		nascitaIstituzioneForm.setValuesManager(vm);
		nascitaIstituzioneForm.setWidth("100%");
		nascitaIstituzioneForm.setHeight("5");
		nascitaIstituzioneForm.setPadding(5);
		nascitaIstituzioneForm.setNumCols(12);
		nascitaIstituzioneForm.setWrapItemTitles(false);

		dataNascitaIstituzioneItem = new DateItem("dataNascitaIstituzione", I18NUtil.getMessages().soggetti_detail_dataNascitaIstituzioneItem_title());

		statoNascitaIstituzioneItem = new FilteredSelectItem("statoNascitaIstituzione", I18NUtil.getMessages()
				.soggetti_detail_statoNascitaIstituzioneItem_title()) {

			@Override
			protected void clearSelect() {
				super.clearSelect();
				comuneNascitaIstituzioneItem.setValue("");
				nomeComuneNascitaIstituzioneItem.setValue("");
				provNascitaIstituzioneItem.setValue("");
				cittaNascitaIstituzioneItem.setValue("");
				nascitaIstituzioneForm.redraw();
			};
		};
		ListGridField codIstatStatoNascitaIstituzioneField = new ListGridField("codIstatStato", "Cod. Istat");
		codIstatStatoNascitaIstituzioneField.setHidden(true);
		ListGridField nomeStatoNascitaIstituzioneField = new ListGridField("nomeStato", "Stato");
		statoNascitaIstituzioneItem.setPickListFields(codIstatStatoNascitaIstituzioneField, nomeStatoNascitaIstituzioneField);
		statoNascitaIstituzioneItem.setValueField("codIstatStato");
		statoNascitaIstituzioneItem.setDisplayField("nomeStato");
		statoNascitaIstituzioneItem.setOptionDataSource(statoNascitaIstituzioneDS);
		statoNascitaIstituzioneItem.setWidth(220);
		statoNascitaIstituzioneItem.setClearable(true);
		statoNascitaIstituzioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				nascitaIstituzioneForm.redraw();
			}
		});

		nomeComuneNascitaIstituzioneItem = new HiddenItem("nomeComuneNascitaIstituzione");

		comuneNascitaIstituzioneItem = new FilteredSelectItem("comuneNascitaIstituzione", I18NUtil.getMessages()
				.soggetti_detail_comuneNascitaIstituzioneItem_title()) {

			@Override
			public void onOptionClick(Record record) {
				
				nomeComuneNascitaIstituzioneItem.setValue(record.getAttribute("nomeComune"));

				GWTRestDataSource comuneNascitaIstituzioneDS = (GWTRestDataSource) comuneNascitaIstituzioneItem.getOptionDataSource();
				comuneNascitaIstituzioneDS.addParam("nomeComune", record.getAttribute("nomeComune"));
				comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);

				provNascitaIstituzioneItem.setValue(record.getAttribute("targaProvincia"));
			}

			@Override
			public void setValue(String value) {
				
				super.setValue(value);
				if (value == null || "".equals(value)) {
					nomeComuneNascitaIstituzioneItem.setValue("");

					GWTRestDataSource comuneNascitaIstituzioneDS = (GWTRestDataSource) comuneNascitaIstituzioneItem.getOptionDataSource();
					comuneNascitaIstituzioneDS.addParam("nomeComune", null);
					comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);

					provNascitaIstituzioneItem.setValue("");
				}
			}
		};
		ListGridField codIstatComuneNascitaIstituzioneField = new ListGridField("codIstatComune", "Cod. Istat");
		codIstatComuneNascitaIstituzioneField.setHidden(true);
		ListGridField nomeComuneNascitaIstituzioneField = new ListGridField("nomeComune", "Comune");
		ListGridField targaProvinciaNascitaIstituzioneField = new ListGridField("targaProvincia", "Prov.");
		targaProvinciaNascitaIstituzioneField.setWidth(50);
		comuneNascitaIstituzioneItem.setPickListFields(codIstatComuneNascitaIstituzioneField, nomeComuneNascitaIstituzioneField,
				targaProvinciaNascitaIstituzioneField);
		comuneNascitaIstituzioneItem.setEmptyPickListMessage(I18NUtil.getMessages().soggetti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage());
		comuneNascitaIstituzioneItem.setValueField("codIstatComune");
		comuneNascitaIstituzioneItem.setDisplayField("nomeComune");
		comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);
		comuneNascitaIstituzioneItem.setWidth(220);
		comuneNascitaIstituzioneItem.setClearable(true);
		comuneNascitaIstituzioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String stato = ((statoNascitaIstituzioneItem.getValue() != null) ? (String) statoNascitaIstituzioneItem.getValue() : "");
				return !"".equals(stato) && "200".equals(stato);
			}
		});

		provNascitaIstituzioneItem = new TextItem("provNascitaIstituzione", I18NUtil.getMessages().soggetti_detail_provNascitaIstituzioneItem_title());
		provNascitaIstituzioneItem.setWidth(50);
		provNascitaIstituzioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String stato = ((statoNascitaIstituzioneItem.getValue() != null) ? (String) statoNascitaIstituzioneItem.getValue() : "");
				return !"".equals(stato) && "200".equals(stato);
			}
		});

		cittaNascitaIstituzioneItem = new TextItem("cittaNascitaIstituzione", I18NUtil.getMessages().soggetti_detail_cittaNascitaIstituzioneItem_title());
		cittaNascitaIstituzioneItem.setWidth(250);
		cittaNascitaIstituzioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String stato = ((statoNascitaIstituzioneItem.getValue() != null) ? (String) statoNascitaIstituzioneItem.getValue() : "");
				return !"".equals(stato) && !"200".equals(stato);
			}
		});

		nascitaIstituzioneForm.setItems(dataNascitaIstituzioneItem, statoNascitaIstituzioneItem, nomeComuneNascitaIstituzioneItem,
				comuneNascitaIstituzioneItem, provNascitaIstituzioneItem, cittaNascitaIstituzioneItem);

		cessazioneForm = new DynamicForm();
		cessazioneForm.setValuesManager(vm);
		cessazioneForm.setWidth("100%");
		cessazioneForm.setHeight("5");
		cessazioneForm.setPadding(5);
		cessazioneForm.setNumCols(12);
		cessazioneForm.setWrapItemTitles(false);

		dataCessazioneItem = new DateItem("dataCessazione", I18NUtil.getMessages().soggetti_detail_dataCessazioneItem_title());

		causaleCessazioneItem = new SelectItem("causaleCessazione", I18NUtil.getMessages().soggetti_detail_causaleCessazioneItem_title());
		causaleCessazioneItem.setValueField("key");
		causaleCessazioneItem.setDisplayField("value");
		causaleCessazioneItem.setOptionDataSource(causaleCessazioneDS);
		causaleCessazioneItem.setWidth(220);
		causaleCessazioneItem.setClearable(true);
		causaleCessazioneItem.setCachePickListResults(false);
		causaleCessazioneItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				if (idSoggettoItem.getValue() != null && !"".equals((String) idSoggettoItem.getValue())) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("idSoggetto", OperatorId.EQUALS, (String) idSoggettoItem.getValue());
					return new AdvancedCriteria(OperatorId.AND, criterias);
				}
				return null;
			}
		});

		cessazioneForm.setItems(dataCessazioneItem, causaleCessazioneItem);

		indirizziForm = new DynamicForm();
		indirizziForm.setValuesManager(vm);
		indirizziForm.setWidth("100%");
		indirizziForm.setHeight("5");
		indirizziForm.setPadding(5);
		indirizziForm.setWrapItemTitles(false);

		indirizziItem = new IndirizziSoggettoItem() {

			@Override
			public boolean isRubricaSoggetti() {
				return true;
			}

			@Override
			public String getFlgPersonaFisica() {
				return _instance.isPersonaFisica() ? "1" : "";
			}
		};
		indirizziItem.setName("listaIndirizzi");
		indirizziItem.setShowTitle(false);

		indirizziForm.setFields(indirizziItem);

		contattiForm = new DynamicForm();
		contattiForm.setValuesManager(vm);
		contattiForm.setWidth("100%");
		contattiForm.setHeight("5");
		contattiForm.setPadding(5);
		contattiForm.setWrapItemTitles(false);

		contattiItem = new ContattiSoggettoItem() {

			@Override
			public boolean isNewMode() {
				return (getLayout() == null || (getLayout().getMode() != null && "new".equals(getLayout().getMode())));
			}		
			
			@Override
			public String getFlgPersonaFisica() {
				return _instance.isPersonaFisica() ? "1" : "";
			}
		};
		contattiItem.setName("listaContatti");
		contattiItem.setShowTitle(false);

		contattiForm.setFields(contattiItem);

		altreDenominazioniForm = new DynamicForm();
		altreDenominazioniForm.setValuesManager(vm);
		altreDenominazioniForm.setWidth("100%");
		altreDenominazioniForm.setHeight("5");
		altreDenominazioniForm.setPadding(5);
		altreDenominazioniForm.setWrapItemTitles(false);

		altreDenominazioniItem = new AltreDenominazioniSoggettoItem();
		altreDenominazioniItem.setName("listaAltreDenominazioni");
		altreDenominazioniItem.setShowTitle(false);

		altreDenominazioniForm.setFields(altreDenominazioniItem);

		soggettiSection = new DetailSection(I18NUtil.getMessages().soggetti_detail_soggettiSection_title(), true, true, false, soggettiForm);
		estremiSuIpaSection = new DetailSection(I18NUtil.getMessages().soggetti_detail_estremiSuIpaSection_title(), true, true, false, estremiSuIpaForm);
		nascitaIstituzioneSection = new DetailSection(I18NUtil.getMessages().soggetti_detail_istituzioneSection_title(), true, true, false,
				nascitaIstituzioneForm);
		cessazioneSection = new DetailSection(I18NUtil.getMessages().soggetti_detail_cessazioneSection_title(), true, false, false, cessazioneForm);
		indirizziSection = new DetailSection(I18NUtil.getMessages().soggetti_detail_indirizziSection_title(), true, true, false, indirizziForm) {
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
		};
		contattiSection = new DetailSection(I18NUtil.getMessages().soggetti_detail_contattiSection_title(), true, true, false, contattiForm) {
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
		};
		altreDenominazioniSection = new DetailSection(I18NUtil.getMessages().soggetti_detail_altreDenominazioniSection_title(), true, false, false,
				altreDenominazioniForm);

		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		if (assegnaAdUOSection != null) {
			// Inserisco la section solamente se è stata creata
			// (la creo solamente se il partizionamento della rubrica è abilitato)
			lVLayout.addMember(assegnaAdUOSection);
		}

		lVLayout.addMember(soggettiSection);

		lVLayout.addMember(estremiSuIpaSection);
		lVLayout.addMember(nascitaIstituzioneSection);
		lVLayout.addMember(cessazioneSection);
		lVLayout.addMember(indirizziSection);
		lVLayout.addMember(contattiSection);
		lVLayout.addMember(altreDenominazioniSection);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}

	public boolean isPersonaFisica() {
		String tipo = tipoItem.getValueAsString() != null ? tipoItem.getValueAsString() : "";
		return tipo.equals("UP") || tipo.equals("#AF");
	}

	public boolean isPersonaFisica(Record record) {
		String tipo = (record != null && record.getAttribute("tipo") != null) ? record.getAttributeAsString("tipo") : "";
		return tipo.equals("UP") || tipo.equals("#AF");
	}
	
	@Override
	public void editNewRecord() {

		GWTRestDataSource sottotipoSoggettoDS = (GWTRestDataSource) sottotipoItem.getOptionDataSource();
		sottotipoSoggettoDS.addParam("idSoggetto", null);
		sottotipoSoggettoDS.addParam("tipo", null);
		sottotipoItem.setOptionDataSource(sottotipoSoggettoDS);

		GWTRestDataSource cittadinanzaDS = (GWTRestDataSource) cittadinanzaItem.getOptionDataSource();
		cittadinanzaDS.addParam("codIstatStato", null);
		cittadinanzaItem.setOptionDataSource(cittadinanzaDS);

		GWTRestDataSource condizioneGiuridicaSoggettoDS = (GWTRestDataSource) condizioneGiuridicaItem.getOptionDataSource();
		condizioneGiuridicaSoggettoDS.addParam("idSoggetto", null);
		condizioneGiuridicaItem.setOptionDataSource(condizioneGiuridicaSoggettoDS);

		GWTRestDataSource comuneNascitaIstituzioneDS = (GWTRestDataSource) comuneNascitaIstituzioneItem.getOptionDataSource();
		comuneNascitaIstituzioneDS.addParam("codIstatComune", null);
		comuneNascitaIstituzioneDS.addParam("nomeComune", null);
		comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);

		GWTRestDataSource statoNascitaIstituzioneDS = (GWTRestDataSource) statoNascitaIstituzioneItem.getOptionDataSource();
		statoNascitaIstituzioneDS.addParam("codIstatStato", null);
		statoNascitaIstituzioneItem.setOptionDataSource(statoNascitaIstituzioneDS);

		GWTRestDataSource causaleCessazioneDS = (GWTRestDataSource) causaleCessazioneItem.getOptionDataSource();
		causaleCessazioneDS.addParam("idSoggetto", null);
		causaleCessazioneItem.setOptionDataSource(causaleCessazioneDS);

		estremiSuIpaSection.hide();
		
		super.editNewRecord();
	}

	@Override
	public void editRecord(Record record) {
		
		GWTRestDataSource sottotipoSoggettoDS = (GWTRestDataSource) sottotipoItem.getOptionDataSource();
		if (record.getAttribute("idSoggetto") != null && !"".equals(record.getAttributeAsString("idSoggetto"))) {
			sottotipoSoggettoDS.addParam("idSoggetto", record.getAttributeAsString("idSoggetto"));
		} else {
			sottotipoSoggettoDS.addParam("idSoggetto", null);
		}
		if (record.getAttribute("tipo") != null && !"".equals(record.getAttributeAsString("tipo"))) {
			sottotipoSoggettoDS.addParam("tipo", record.getAttributeAsString("tipo"));
		} else {
			sottotipoSoggettoDS.addParam("tipo", null);
		}
		sottotipoItem.setOptionDataSource(sottotipoSoggettoDS);

		GWTRestDataSource cittadinanzaDS = (GWTRestDataSource) cittadinanzaItem.getOptionDataSource();
		if (record.getAttribute("cittadinanza") != null && !"".equals(record.getAttributeAsString("cittadinanza"))) {
			cittadinanzaDS.addParam("codIstatStato", record.getAttributeAsString("cittadinanza"));
		} else {
			cittadinanzaDS.addParam("codIstatStato", null);
		}
		cittadinanzaItem.setOptionDataSource(cittadinanzaDS);

		GWTRestDataSource condizioneGiuridicaSoggettoDS = (GWTRestDataSource) condizioneGiuridicaItem.getOptionDataSource();
		if (record.getAttribute("idSoggetto") != null && !"".equals(record.getAttributeAsString("idSoggetto"))) {
			condizioneGiuridicaSoggettoDS.addParam("idSoggetto", record.getAttributeAsString("idSoggetto"));
		} else {
			condizioneGiuridicaSoggettoDS.addParam("idSoggetto", null);
		}
		condizioneGiuridicaItem.setOptionDataSource(condizioneGiuridicaSoggettoDS);

		GWTRestDataSource comuneNascitaIstituzioneDS = (GWTRestDataSource) comuneNascitaIstituzioneItem.getOptionDataSource();
		if (record.getAttribute("comuneNascitaIstituzione") != null && !"".equals(record.getAttributeAsString("comuneNascitaIstituzione"))) {
			comuneNascitaIstituzioneDS.addParam("codIstatComune", record.getAttributeAsString("comuneNascitaIstituzione"));
			comuneNascitaIstituzioneDS.addParam("nomeComune", record.getAttributeAsString("nomeComuneNascitaIstituzione"));
		} else {
			comuneNascitaIstituzioneDS.addParam("codIstatComune", null);
			comuneNascitaIstituzioneDS.addParam("nomeComune", null);
		}
		comuneNascitaIstituzioneItem.setOptionDataSource(comuneNascitaIstituzioneDS);

		GWTRestDataSource statoNascitaIstituzioneDS = (GWTRestDataSource) statoNascitaIstituzioneItem.getOptionDataSource();
		if (record.getAttribute("statoNascitaIstituzione") != null && !"".equals(record.getAttributeAsString("statoNascitaIstituzione"))) {
			statoNascitaIstituzioneDS.addParam("codIstatStato", record.getAttributeAsString("statoNascitaIstituzione"));
		} else {
			statoNascitaIstituzioneDS.addParam("codIstatStato", null);
		}
		statoNascitaIstituzioneItem.setOptionDataSource(statoNascitaIstituzioneDS);

		GWTRestDataSource causaleCessazioneDS = (GWTRestDataSource) causaleCessazioneItem.getOptionDataSource();
		if (record.getAttribute("idSoggetto") != null && !"".equals(record.getAttributeAsString("idSoggetto"))) {
			causaleCessazioneDS.addParam("idSoggetto", record.getAttributeAsString("idSoggetto"));
		} else {
			causaleCessazioneDS.addParam("idSoggetto", null);
		}
		causaleCessazioneItem.setOptionDataSource(causaleCessazioneDS);

		if (isPersonaFisica(record)) {
			nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().soggetti_detail_nascitaSection_title());
			estremiSuIpaSection.hide();
		} else {
			nascitaIstituzioneSection.setTitle(I18NUtil.getMessages().soggetti_detail_istituzioneSection_title());
			String tipo = (record != null && record.getAttribute("tipo") != null) ? record.getAttributeAsString("tipo") : "";
			if (!"".equals(tipo) && (tipo.equals("#APA") || tipo.equals("#IAMM") || tipo.equals("UO;UOI"))) {
				estremiSuIpaSection.show();
			} else {
				estremiSuIpaSection.hide();
			}
		}

		tipoItem.setAddUnknownValues(true);
		super.editRecord(record);

		markForRedraw();
	}

	protected void setTipoSoggettoValueMap(Record record) {
		tipoItem.setAddUnknownValues(!editing);
		String idDominio = AurigaLayout.getIdDominio();
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		if (idDominio == null || "".equals(idDominio)) {
			styleMap.put("#APA", I18NUtil.getMessages().soggetti_tipo_APA_value());
			styleMap.put("#IAMM", I18NUtil.getMessages().soggetti_tipo_IAMM_value());
		} else if ( record != null && record.getAttribute("tipo") != null) {
			if ("#APA".equals(record.getAttributeAsString("tipo"))) {
				styleMap.put("#APA", I18NUtil.getMessages().soggetti_tipo_APA_value());
			} else if ("#IAMM".equals(record.getAttributeAsString("tipo"))) {
				styleMap.put("#IAMM", I18NUtil.getMessages().soggetti_tipo_IAMM_value());
			}
		}
		if (!editing || !AurigaLayout.getParametroDBAsBoolean("INIBITO_INS_UO_IN_RUBRICA")) {
			styleMap.put("UO;UOI", I18NUtil.getMessages().soggetti_tipo_UOUOI_value());
		}
		styleMap.put("UP", I18NUtil.getMessages().soggetti_tipo_UP_value());
		styleMap.put("#AF", I18NUtil.getMessages().soggetti_tipo_AF_value());
		styleMap.put("#AG", I18NUtil.getMessages().soggetti_tipo_AG_value());
		
		
		boolean showPA = false;
		if ( record != null && record.getAttribute("tipo") != null) {			
			if ("#APA;PA".equals(record.getAttributeAsString("tipo")) ||
				"#APA;AOO;AOOE".equals(record.getAttributeAsString("tipo")) ||
				"#APA;UO;UOE".equals(record.getAttributeAsString("tipo"))	
					) {
				showPA = true;
			}
		}
		
		// se si ha il privilegio GRS/SPA;M o GRS/SPA;I o GRS/SPA;I  e GRS/SPA;FC aggiungo le voci :  
		// - Altra PA                                 
		// - AOO di altra PA            
		// - Struttura di altra PA      
		if (SoggettiLayout.isAbilToModPA() || SoggettiLayout.isAbilInserirePA() || SoggettiLayout.isAbilToDelPA()){
			showPA = true;
		}
			
		if (showPA){			
			styleMap.put("#APA;PA", I18NUtil.getMessages().soggetti_tipo_APA_PA_value());
			styleMap.put("#APA;AOO;AOOE", I18NUtil.getMessages().soggetti_tipo_APA_AOO_value());
			styleMap.put("#APA;UO;UOE", I18NUtil.getMessages().soggetti_tipo_APA_UO_value());
		}
		
		
		tipoItem.setValueMap(styleMap);

		if (soggettiForm != null) {
			soggettiForm.markForRedraw();
		}
	}

	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(canEdit);
		codiceAmmInIpaItem.setCanEdit(false);
		codiceAooInIpaItem.setCanEdit(false);
		codiceUoInIpaItem.setCanEdit(false);
		provNascitaIstituzioneItem.setCanEdit(false);
		setTipoSoggettoValueMap(vm.getValuesAsRecord());
		
		// Se il soggetto e' PA o AOO di altra PA o Struttura di altra PA verifico il privilegio.		
		if (isSoggettoPA()){
			// se NON si ha il privilegio GRS/SPA;M 			
			if (!SoggettiLayout.isAbilToModPA()){
						
				// se si ha uno dei privilegi GRS/SPA/E;I o  GRS/SPA/E;FC
				if (SoggettiLayout.isAbilToModPAE()){
					// allora BLOCCO TUTTO tranne i CONTATTI
					setCanEdit(false, soggettiForm);
					setCanEdit(false, estremiSuIpaForm);
					setCanEdit(false, nascitaIstituzioneForm);
					setCanEdit(false, cessazioneForm);
					setCanEdit(false, indirizziForm);
					setCanEdit(false, altreDenominazioniForm);
					if (soggettiSection != null) {
						assegnaAdUoForms.viewMode();						
					}
					setCanEdit(true, contattiForm);
				}
				else{
					// allora BLOCCO TUTTO
					setCanEdit(false, soggettiForm);
					setCanEdit(false, estremiSuIpaForm);
					setCanEdit(false, nascitaIstituzioneForm);
					setCanEdit(false, cessazioneForm);
					setCanEdit(false, indirizziForm);
					setCanEdit(false, altreDenominazioniForm);
					if (soggettiSection != null) {
						assegnaAdUoForms.viewMode();						
					}
					setCanEdit(false, contattiForm);
					
				}
			}
		}
	}

	@Override
	public void viewMode() {
		super.viewMode();
		if (SoggettiLayout.isPartizionamentoRubricaAbilitato()) {
			// Il partizionamento della rubrica è attivo
			Record record = new Record();
			record.setAttribute("tipo", "UO");
			record.setAttribute("idUoSvUt", vm.getValues().get("idUoAssociata"));
			record.setAttribute("codRapidoUo", vm.getValues().get("numeroLivelli"));
			record.setAttribute("flgVisibileDaSottoUo", vm.getValues().get("flgVisibileDaSottoUo"));
			record.setAttribute("flgModificabileDaSottoUo", vm.getValues().get("flgModificabileDaSottoUo"));
			// Verifico che sia presente il form del'assegnazione del soggetto alla UO
			if (assegnaAdUOSection != null) {
				// Setto i valori del form per l'assegnazione della UO al soggetto
				assegnaAdUoForms.viewMode();
				assegnaAdUoForms.setFormValuesFromRecord(record);
			}
		}
	}

	@Override
	public void editMode() {
		super.editMode();
		if (SoggettiLayout.isPartizionamentoRubricaAbilitato()) {
			// Il partizionamento della rubrica è attivo
			Record record = new Record();
			record.setAttribute("tipo", "UO");
			record.setAttribute("idUoSvUt", vm.getValues().get("idUoAssociata"));
			record.setAttribute("codRapidoUo", vm.getValues().get("numeroLivelli"));
			record.setAttribute("flgVisibileDaSottoUo", vm.getValues().get("flgVisibileDaSottoUo"));
			record.setAttribute("flgModificabileDaSottoUo", vm.getValues().get("flgModificabileDaSottoUo"));
			if (assegnaAdUOSection != null) {
				assegnaAdUoForms.editMode();
				assegnaAdUoForms.setFormValuesFromRecord(record);
			}
		}
	}

	@Override
	public void newMode() {
		super.newMode();
		if (SoggettiLayout.isPartizionamentoRubricaAbilitato()) {
			assegnaAdUoForms.newMode();
		}
	}

	public boolean isSoggettoPA() {
		String tipo = soggettiForm.getValue("tipo") != null ? soggettiForm.getValueAsString("tipo") : "";
		return (tipo.startsWith("#APA"));
	}
}
