/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SalvaInRubricaPopup;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SoggettiDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.IndirizzoCanvas;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class FirmatariCanvas extends IndirizzoCanvas {

	private ExtendedTextItem codRapidoItem;
	private ExtendedTextItem denominazioneItem;
	private ExtendedTextItem cognomeItem;
	private ExtendedTextItem nomeItem;
	private ExtendedTextItem codFiscaleItem;
	private ImgButtonItem salvaInRubricaButton;
	private ImgButtonItem visualizzaInRubricaButton;
	private TextItem emailItem;
	private TextItem telItem;
	private SelectItem tipoSoggettoItem;
	private HiddenItem idSoggettoItem;
	private HiddenItem idUoSoggettoItem;
	private HiddenItem idUserSoggettoItem;
	private HiddenItem idScrivaniaSoggettoItem;
	private HiddenItem descrAoomdgSoggettoItem;
	private FilteredSelectItemWithDisplay aoomdgSoggettoItem;

	public FirmatariCanvas(ReplicableItem item) {
		super(item);		
	}

	@Override
	public void buildMainForm() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idSoggettoItem = new HiddenItem("idSoggetto");
		idUoSoggettoItem = new HiddenItem("idUoSoggetto");
		idUserSoggettoItem = new HiddenItem("idUserSoggetto");
		idScrivaniaSoggettoItem = new HiddenItem("idScrivaniaSoggetto");

		// tipo
		tipoSoggettoItem = new SelectItem("tipoSoggetto");
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
//		if (AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST")) {
//			String decodificaAOOI = AurigaLayout.getParametroDB("LABEL_AOOI_IN_MITT_DEST");
//			if (decodificaAOOI != null && !"".equals(decodificaAOOI)) {
//				styleMap.put("AOOI", decodificaAOOI);
//			} else {
//				styleMap.put("AOOI", I18NUtil.getMessages().protocollazione_select_listmap_AOOI_value());
//			}
//		}
//		styleMap.put("PA", I18NUtil.getMessages().protocollazione_select_listmap_PA_value());
		styleMap.put("PF", I18NUtil.getMessages().protocollazione_select_listmap_PF_value());
//		styleMap.put("PG", I18NUtil.getMessages().protocollazione_select_listmap_PG_value());
		tipoSoggettoItem.setAllowEmptyValue(true);
		// tipoSoggettoItem.setRequired(true);
		tipoSoggettoItem.setShowTitle(false);
		tipoSoggettoItem.setValueMap(styleMap);
		tipoSoggettoItem.setWidth(150);
		tipoSoggettoItem.setDefaultValue("PF");
		tipoSoggettoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return false;
			}
		});
		tipoSoggettoItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				tipoSoggettoItem.setAttribute("oldValue", event.getOldValue());
			}
		});
		tipoSoggettoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.clearErrors(true);
				String oldValue = tipoSoggettoItem.getAttribute("oldValue") != null ? tipoSoggettoItem.getAttribute("oldValue") : "";
				String value = event.getValue() != null ? (String) event.getValue() : "";
				if (!("".equals(oldValue) && toShowDenominazione(value)) && !(toShowDenominazione(oldValue) && "".equals(value))
						&& !(toShowDenominazione(oldValue) && toShowDenominazione(value))) {
					Record lRecord = mDynamicForm.getValuesAsRecord();
					lRecord.setAttribute("codRapido", "");
					lRecord.setAttribute("denominazione", "");
					lRecord.setAttribute("cognome", "");
					lRecord.setAttribute("nome", "");
					lRecord.setAttribute("aoomdgSoggetto", "");
					lRecord.setAttribute("idSoggetto", "");
					lRecord.setAttribute("idUoSoggetto", "");
					lRecord.setAttribute("idUserSoggetto", "");
					lRecord.setAttribute("idScrivaniaSoggetto", "");
					mDynamicForm.setValues(lRecord.toMap());
					// mezzoTrasmissioneItem.refreshFilteredSelectIndirizzoDestinatario(null);
					((FirmatariItem) getItem()).manageOnChanged();
				}
			}
		});
		
		// cod.rapido
		codRapidoItem = new ExtendedTextItem("codRapido", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);
		codRapidoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				if (event.getValue() != null && !"".equals(event.getValue())) {
					String tipoSoggetto = tipoSoggettoItem.getValueAsString();
					Record lRecord = new Record();
					lRecord.setAttribute("codRapidoSoggetto", event.getValue());
					cercaSoggetto(lRecord, new CercaSoggettoServiceCallback() {

						@Override
						public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
							// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un interessato di una registrazione <in entrata/ uscita /
							// interna>
							mDynamicForm.setFieldErrors("codRapido",
									I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("interessato", "in uscita"));
						}
					});
				}
			}
		});

		// denominazione
		denominazioneItem = new ExtendedTextItem("denominazione", I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());
		denominazioneItem.setWidth(200);
		denominazioneItem.setAttribute("obbligatorio", true);
		denominazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return toShowDenominazione(tipoSoggettoItem.getValueAsString());
			}
		});
		denominazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return toShowDenominazione(tipoSoggettoItem.getValueAsString()) && (((FirmatariItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()));
			}
		}));
		denominazioneItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((FirmatariItem) getItem()).manageOnChanged();
				if(denominazioneItem.getValue() != null && !"".equals(denominazioneItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("denominazioneSoggetto", denominazioneItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoSoggettoItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		denominazioneItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((FirmatariItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cognome
		cognomeItem = new ExtendedTextItem("cognome", I18NUtil.getMessages().protocollazione_detail_cognomeItem_title());
		cognomeItem.setWidth(150);
		cognomeItem.setAttribute("obbligatorio", true);
		cognomeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isPersonaFisica(tipoSoggettoItem.getValueAsString());
			}
		});
		cognomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isPersonaFisica(tipoSoggettoItem.getValueAsString()) && (((FirmatariItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()));
			}
		}));
		cognomeItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((FirmatariItem) getItem()).manageOnChanged();
				if(cognomeItem.getValue() != null && !"".equals(cognomeItem.getValue()) &&
				   nomeItem.getValue() != null && !"".equals(nomeItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeItem.getValue());									
					lRecord.setAttribute("codTipoSoggetto", tipoSoggettoItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		cognomeItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((FirmatariItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// nome
		nomeItem = new ExtendedTextItem("nome", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeItem.setWidth(150);
		nomeItem.setAttribute("obbligatorio", true);
		nomeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isPersonaFisica(tipoSoggettoItem.getValueAsString()) && (((FirmatariItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()));
			}
		});
		nomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isPersonaFisica(tipoSoggettoItem.getValueAsString());
			}
		}));
		nomeItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((FirmatariItem) getItem()).manageOnChanged();
				if(cognomeItem.getValue() != null && !"".equals(cognomeItem.getValue()) &&
				   nomeItem.getValue() != null && !"".equals(nomeItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeItem.getValue());									
					lRecord.setAttribute("codTipoSoggetto", tipoSoggettoItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		nomeItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((FirmatariItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cod.fiscale
		codFiscaleItem = new ExtendedTextItem("codFiscale", I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
		codFiscaleItem.setWidth(120);
		codFiscaleItem.setCharacterCasing(CharacterCasing.UPPER);
		codFiscaleItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals(value) || AurigaLayout.getParametroDBAsBoolean("DISATTIVA_CTRL_CF_PIVA_EMDI")) {
					return true;
				}
				if (isPersonaFisica(tipoSoggettoItem.getValueAsString())) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);
				}
			}
		});		
		codFiscaleItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((FirmatariItem) getItem()).manageOnChanged();
				if(codFiscaleItem.getValue() != null && !"".equals(codFiscaleItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("codfiscaleSoggetto", codFiscaleItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoSoggettoItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		codFiscaleItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((FirmatariItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});
		codFiscaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				if (isPersonaFisica(tipoSoggetto)) {
					codFiscaleItem.setLength(16);
					codFiscaleItem.setTitle(I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
				} else {
					codFiscaleItem.setLength(28);
					codFiscaleItem.setTitle(I18NUtil.getMessages().protocollazione_detail_codFiscalePIVAItem_title());
				}
				return toShowDenominazione(tipoSoggetto) || isPersonaFisica(tipoSoggetto);
			}
		});
		
		emailItem = new TextItem("email", "e-mail");
		emailItem.setAttribute("obbligatorio", false);
		emailItem.setWidth(240);
		emailItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((FirmatariItem)getItem()).getShowEmail();
			}
		});
		emailItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(((FirmatariItem)getItem()).getShowEmail()) {
					if(value == null || "".equals((String) value)) return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					return regExp.test((String) value);		
				}
				return true;
			}
		});
		
		telItem = new TextItem("tel", "Tel.");
		telItem.setAttribute("obbligatorio", false);
		telItem.setWidth(160);
		telItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((FirmatariItem)getItem()).getShowTelefono();
			}
		});			
		// TODEL Tolta espressione regolare per validare il numero di telefono, ne serve una migliore
		// telItem.setValidators(new RegExpValidator("^[0-9]{5,12}"));
		
		descrAoomdgSoggettoItem = new HiddenItem("descrAoomdgSoggetto");

		// soggetto AOO MDG
		SelectGWTRestDataSource lGwtRestDataSourceAooMdg = new SelectGWTRestDataSource("LoadComboSoggettiAooMdgDataSource", "idAooMdg", FieldType.TEXT,
				new String[] { "descrizioneAooMdg" }, true);

		aoomdgSoggettoItem = new FilteredSelectItemWithDisplay("aoomdgSoggetto", lGwtRestDataSourceAooMdg) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				SelectGWTRestDataSource aoomdgSoggettoDS = (SelectGWTRestDataSource) aoomdgSoggettoItem.getOptionDataSource();
				aoomdgSoggettoDS.addParam("descrizioneAooMdg", record.getAttributeAsString("descrizioneAooMdg"));
				aoomdgSoggettoItem.setOptionDataSource(aoomdgSoggettoDS);

				mDynamicForm.setValue("descrAoomdgSoggetto", record.getAttributeAsString("descrizioneAooMdg"));

				mDynamicForm.setValue("idSoggetto", record.getAttributeAsString("idAooMdg"));
				mDynamicForm.setValue("codRapido", record.getAttributeAsString("codiceRapidoAooMdg"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				SelectGWTRestDataSource aoomdgSoggettoDS = (SelectGWTRestDataSource) aoomdgSoggettoItem.getOptionDataSource();
				aoomdgSoggettoDS.addParam("descrizioneAooMdg", null);
				aoomdgSoggettoItem.setOptionDataSource(aoomdgSoggettoDS);

				mDynamicForm.setValue("aoomdgSoggetto", "");
				mDynamicForm.setValue("descrAoomdgSoggetto", "");

				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("codRapido", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					SelectGWTRestDataSource aoomdgSoggettoDS = (SelectGWTRestDataSource) aoomdgSoggettoItem.getOptionDataSource();
					aoomdgSoggettoDS.addParam("descrizioneAooMdg", null);
					aoomdgSoggettoItem.setOptionDataSource(aoomdgSoggettoDS);

					mDynamicForm.setValue("descrAoomdgSoggetto", "");

					mDynamicForm.setValue("idSoggetto", "");
					mDynamicForm.setValue("codRapido", "");
				}
			}
		};
		aoomdgSoggettoItem.setAutoFetchData(false);
		aoomdgSoggettoItem.setFetchMissingValues(true);

		ListGridField codiceRapidoAooMdgField = new ListGridField("codiceRapidoAooMdg", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoAooMdgField.setWidth(70);

		ListGridField descrizioneAooMdgField = new ListGridField("descrizioneAooMdg", I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());
		// descrizioneAooMdgField.setWidth(200);

		ListGridField descrizioneEstesaAooMdgField = new ListGridField("descrizioneEstesaAooMdg", I18NUtil.getMessages()
				.protocollazione_detail_denominazioneEstesaItem_title());
		descrizioneEstesaAooMdgField.setHidden(true);
		// descrizioneEstesaAooMdgField.setWidth("*");

		aoomdgSoggettoItem.setPickListFields(codiceRapidoAooMdgField, descrizioneAooMdgField, descrizioneEstesaAooMdgField);
		aoomdgSoggettoItem.setEmptyPickListMessage(I18NUtil.getMessages().protocollazione_detail_aoomdgMittDestItem_noSearchOrEmptyMessage());
		// aoomdgSoggettoItem.setFilterLocally(true);
		aoomdgSoggettoItem.setValueField("idAooMdg");
		aoomdgSoggettoItem.setOptionDataSource(lGwtRestDataSourceAooMdg);
		aoomdgSoggettoItem.setShowTitle(false);
		aoomdgSoggettoItem.setWidth(450);
		aoomdgSoggettoItem.setClearable(true);
		aoomdgSoggettoItem.setShowIcons(true);
		aoomdgSoggettoItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesaAooMdg") : null;
			}
		});
		aoomdgSoggettoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				return tipoSoggetto != null && "AOOI".equals(tipoSoggetto);
			}
		});
		aoomdgSoggettoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				return tipoSoggetto != null && "AOOI".equals(tipoSoggetto);
			}
		}));
		aoomdgSoggettoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((FirmatariItem) getItem()).manageOnChanged();
			}
		});

		// bottone seleziona da rubrica
		ImgButtonItem lookupRubricaButton = new ImgButtonItem("lookupRubricaButton", "lookup/rubrica.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupRubricaButton_prompt());
		lookupRubricaButton.setColSpan(1);
		lookupRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				return tipoSoggetto == null || "".equals(tipoSoggetto) || isPersonaFisica(tipoSoggetto) || isPersonaGiuridica(tipoSoggetto);
			}
		});
		lookupRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((FirmatariItem) getItem()).setCercaInRubricaAfterChanged(false);			
				FirmatariLookupSoggettiPopup lookupRubricaPopup = new FirmatariLookupSoggettiPopup(null, tipoSoggettoItem.getValueAsString());
				lookupRubricaPopup.show();
			}
		});

		// bottone cerca in rubrica
		ImgButtonItem cercaInRubricaButton = new ImgButtonItem("cercaInRubricaButton", "lookup/rubricasearch.png", I18NUtil.getMessages()
				.protocollazione_detail_cercaInRubricaButton_prompt());
		cercaInRubricaButton.setColSpan(1);
		cercaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				return tipoSoggetto == null || "".equals(tipoSoggetto) || isPersonaFisica(tipoSoggetto)
						|| (isPersonaGiuridica(tipoSoggetto) && !"AOOI".equalsIgnoreCase(tipoSoggetto));
			}
		});
		cercaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((FirmatariItem) getItem()).setCercaInRubricaAfterChanged(false);		
				cercaInRubrica();				
			}
		});

		// bottone salva in rubrica
		salvaInRubricaButton = new ImgButtonItem("salvaInRubricaButton", "buttons/saveIn.png", I18NUtil.getMessages()
				.protocollazione_detail_salvaInRubricaButton_prompt());
		salvaInRubricaButton.setColSpan(1);
		salvaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				return Layout.isPrivilegioAttivo("GRS/S/UO;I")
						&& (tipoSoggetto == null || "".equals(tipoSoggetto) || ((isPersonaFisica(tipoSoggetto) || isPersonaGiuridica(tipoSoggetto))
								&& !"AOOI".equalsIgnoreCase(tipoSoggetto) && !"PA".equalsIgnoreCase(tipoSoggetto)))
						&& !(idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue()));
			}
		});
		salvaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((FirmatariItem) getItem()).setCercaInRubricaAfterChanged(false);			
				String tipo = tipoSoggettoItem.getValueAsString();
				String codRapido = codRapidoItem.getValueAsString();
				String denominazione = denominazioneItem.isVisible() ? denominazioneItem.getValueAsString() : null;
				String cognome = cognomeItem.isVisible() ? cognomeItem.getValueAsString() : null;
				String nome = nomeItem.isVisible() ? nomeItem.getValueAsString() : null;
				String codiceFiscale = codFiscaleItem.isVisible() ? codFiscaleItem.getValueAsString() : null;
				Record recordFirmatari = new Record();
				recordFirmatari.setAttribute("tipo", tipo);
				recordFirmatari.setAttribute("codRapido", codRapido);
				recordFirmatari.setAttribute("denominazione", denominazione);
				recordFirmatari.setAttribute("cognome", cognome);
				recordFirmatari.setAttribute("nome", nome);
				recordFirmatari.setAttribute("codiceFiscale", codiceFiscale);
				Record recordIndirizzo = getFormValuesAsRecord();
				SalvaInRubricaPopup salvaInRubricaPopup = new SalvaInRubricaPopup(null, recordFirmatari, recordIndirizzo) {

					@Override
					public void manageLookupBack(Record record) {
						setFormValuesFromRecordRubrica(record);
					}
				};
				salvaInRubricaPopup.show();
			}
		});

		//bottone visualizza nominativo in rubrica
		visualizzaInRubricaButton = new ImgButtonItem("visualizzaInRubricaFirmatari", "buttons/detail.png", I18NUtil.getMessages()
				.protocollazione_detail_visualizzaInRubricaButton_prompt());
		visualizzaInRubricaButton.setAlwaysEnabled(true);
		visualizzaInRubricaButton.setColSpan(1);
		visualizzaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				return ((isPersonaFisica(tipoSoggetto) || isPersonaGiuridica(tipoSoggetto))
						&& !"AOOI".equalsIgnoreCase(tipoSoggetto) && !"PA".equalsIgnoreCase(tipoSoggetto)) 
						&& idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue());
			}
		});

		visualizzaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				((FirmatariItem) getItem()).setCercaInRubricaAfterChanged(false);			
				String idSoggetto = mDynamicForm.getValueAsString("idSoggetto");

				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idSoggetto", idSoggetto);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AnagraficaSoggettiDataSource",
						"idSoggetto", FieldType.TEXT);
				lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record detailRecord = response.getData()[0];
							SoggettiDetail detail = new SoggettiDetail("anagrafiche_soggetti.detail");
							detail.editRecord(detailRecord);
							detail.viewMode();
							String nomeDettaglio = mDynamicForm.getValueAsString("cognome") + " " + mDynamicForm.getValueAsString("nome");
							Layout.addModalWindow("dettaglio_soggetto", "Dettaglio " + nomeDettaglio, "buttons/detail.png", detail);
						}
					}
				});
			}
		});		
		
		mDynamicForm.setFields(idSoggettoItem, idUoSoggettoItem, idUserSoggettoItem, idScrivaniaSoggettoItem, tipoSoggettoItem, codRapidoItem,
				lookupRubricaButton, denominazioneItem, cognomeItem, nomeItem, codFiscaleItem, emailItem, telItem, descrAoomdgSoggettoItem, aoomdgSoggettoItem,
				cercaInRubricaButton, salvaInRubricaButton, visualizzaInRubricaButton);

		mDynamicForm.setNumCols(30);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50",
				"100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

	}

	public class FirmatariLookupSoggettiPopup extends LookupSoggettiPopup {

		public FirmatariLookupSoggettiPopup(Record record, String tipoSoggetto) {
			super(record, tipoSoggetto, true);
		}
		
		@Override
		public String getFinalita() {
			return "SEL_SOGG_EST";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordRubrica(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		@Override
		public String[] getTipiAmmessi() {
//			return AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST") ? new String[] { "AOOI", "PA", "PF", "PG" } : new String[] { "PA", "PF", "PG" };
			return new String[] { "PF" };
		}

	}

	protected void clearFormSoggettoValues() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapido", "");
		lRecord.setAttribute("denominazione", "");
		lRecord.setAttribute("cognome", "");
		lRecord.setAttribute("nome", "");
		lRecord.setAttribute("codFiscale", "");
		mDynamicForm.setValues(lRecord.toMap());
		((FirmatariItem) getItem()).manageOnChanged();
	}

	protected void clearIdSoggetto() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("idSoggetto", "");
		lRecord.setAttribute("idUoSoggetto", "");
		lRecord.setAttribute("idUserSoggetto", "");
		lRecord.setAttribute("idScrivaniaSoggetto", "");
		lRecord.setAttribute("codRapido", "");
		mDynamicForm.setValues(lRecord.toMap());
		// mezzoTrasmissioneItem.refreshFilteredSelectIndirizzoDestinatario(null);
	}

	protected boolean toShowDenominazione(String tipoSoggetto) {
		return tipoSoggetto == null || "".equals(tipoSoggetto) || (isPersonaGiuridica(tipoSoggetto) && !"AOOI".equalsIgnoreCase(tipoSoggetto));
	}

	protected boolean isPersonaGiuridica(String tipoSoggetto) {
		return ((FirmatariItem) getItem()).isPersonaGiuridica(tipoSoggetto);
	}

	protected boolean isPersonaFisica(String tipoSoggetto) {
		return ((FirmatariItem) getItem()).isPersonaFisica(tipoSoggetto);
	}
	
	protected void cercaInRubrica() {
		final Record lRecord = new Record();
		lRecord.setAttribute("denominazioneSoggetto", denominazioneItem.isVisible() ? denominazioneItem.getValue() : null);
		lRecord.setAttribute("cognomeSoggetto", cognomeItem.isVisible() ? cognomeItem.getValue() : null);
		lRecord.setAttribute("nomeSoggetto", nomeItem.isVisible() ? nomeItem.getValue() : null);
		lRecord.setAttribute("codfiscaleSoggetto", codFiscaleItem.isVisible() ? codFiscaleItem.getValue() : null);
		lRecord.setAttribute("codTipoSoggetto", tipoSoggettoItem.getValue());
		cercaInRubrica(lRecord, true);
	}
	
	protected void cercaInRubricaAfterChangedField(final Record record) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_CERCA_IN_RUBRICA")) {
			Timer t1 = new Timer() {
				public void run() {
					if(((FirmatariItem) getItem()).isCercaInRubricaAfterChanged()) {
						cercaInRubrica(record, false);
					}
				}
			};
			String delay = AurigaLayout.getParametroDB("CERCA_IN_RUBRICA_DELAY");
			t1.schedule((delay != null && !"".equals(delay)) ? Integer.parseInt(delay) : 1000);		
		}
	}
	
	protected void cercaInRubrica(final Record record, final boolean showLookupWithNoResults) {
		cercaSoggetto(record, new CercaSoggettoServiceCallback() {

			@Override
			public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
				if(showLookupWithNoResults || trovatiSoggMultipliInRicerca) {
					FirmatariLookupSoggettiPopup lookupRubricaPopup = new FirmatariLookupSoggettiPopup(record, tipoSoggettoItem.getValueAsString());
					lookupRubricaPopup.show();
				}
			}
		});
	}

	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
//		lGwtRestService.addParam("tipiAmmessi", AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST") ? "AOOI,PA,PF,PG" : "PA,PF,PG");
		lGwtRestService.addParam("tipiAmmessi", "PF");
		lGwtRestService.call(lRecord, callback);
	}

	protected void cercaSoggettoAOOMDG() {
		final String codRapidoSoggetto = codRapidoItem.getValueAsString();
		if(codRapidoSoggetto != null && !"".equals(codRapidoSoggetto)) {
			mDynamicForm.setValue("idSoggetto", (String) null);
			mDynamicForm.setValue("aoomdgSoggetto", (String) null);
			mDynamicForm.clearErrors(true);
			SelectGWTRestDataSource aoomdgSoggettoDS = (SelectGWTRestDataSource) aoomdgSoggettoItem.getOptionDataSource();
			aoomdgSoggettoDS.addParam("codiceRapidoAooMdg", codRapidoSoggetto);
			aoomdgSoggettoDS.addParam("descrizioneAooMdg", null);
			aoomdgSoggettoItem.setOptionDataSource(aoomdgSoggettoDS);
			aoomdgSoggettoDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String codiceRapidoAooMdg = data.get(i).getAttribute("codiceRapidoAooMdg");
							if (codRapidoSoggetto.equalsIgnoreCase(codiceRapidoAooMdg)) {
								SelectGWTRestDataSource aoomdgSoggettoDS = (SelectGWTRestDataSource) aoomdgSoggettoItem.getOptionDataSource();
								aoomdgSoggettoDS.addParam("descrizioneAooMdg", data.get(i).getAttributeAsString("descrizioneAooMdg"));
								aoomdgSoggettoItem.setOptionDataSource(aoomdgSoggettoDS);
								mDynamicForm.setValue("idSoggetto", data.get(i).getAttribute("idAooMdg"));
								mDynamicForm.setValue("aoomdgSoggetto", data.get(i).getAttribute("idAooMdg"));
								mDynamicForm.setValue("descrAoomdgSoggetto", data.get(i).getAttributeAsString("descrizioneAooMdg"));
								((FirmatariItem) getItem()).manageOnChanged();
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setFieldErrors("codRapido",
								I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("interessato", "in uscita"));
					}
				}
			});
		}
	}

	public String calcolaTipoSoggetto(String tipo) {
		String tipoSoggetto = null;
		if ("UO;UOI".equals(tipo)) {
			tipoSoggetto = "UOI";
		} else if ("UP".equals(tipo)) {
			tipoSoggetto = "UP";
		} else if ("#APA".equals(tipo)) {
			tipoSoggetto = "PA";
		} else if ("#IAMM".equals(tipo)) {
			tipoSoggetto = "AOOI";
		} else if ("#AF".equals(tipo)) {
			tipoSoggetto = "PF";
		} else if ("#AG".equals(tipo)) {
			tipoSoggetto = "PG";
		} /*
		 * else if("LD".equals(tipo)){ tipoSoggetto = "LD"; }
		 */
		return tipoSoggetto;
	}

	public void setFormValuesFromRecordRubrica(Record record) {
		SC.echo(record.getJsObj());
		// Pulisco i dati del soggetto
		clearIdSoggetto();
		clearFormSoggettoValues();
		mDynamicForm.clearErrors(true);
		String tipoSoggetto = calcolaTipoSoggetto(record.getAttribute("tipo"));
		if (tipoSoggetto != null) {
			mDynamicForm.setValue("tipoSoggetto", tipoSoggetto);
			mDynamicForm.setValue("codRapido", record.getAttribute("codiceRapido"));
			mDynamicForm.setValue("idSoggetto", record.getAttribute("idSoggetto"));
			mDynamicForm.setValue("idUoSoggetto", record.getAttribute("idUo"));
			mDynamicForm.setValue("idUserSoggetto", record.getAttribute("idUtente"));
			mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttribute("idScrivania"));
			if ("AOOI".equalsIgnoreCase(tipoSoggetto)) {
				mDynamicForm.setValue("aoomdgSoggetto", record.getAttribute("idSoggetto"));
				mDynamicForm.setValue("descrAoomdgSoggetto", record.getAttribute("denominazione"));
				manageLoadSelectAoomdgSoggettoInEditRecord(mDynamicForm.getValuesAsRecord());
			} else if (isPersonaGiuridica(tipoSoggetto)) {
				mDynamicForm.setValue("denominazione", record.getAttribute("denominazione"));
			} else if (isPersonaFisica(tipoSoggetto)) {
				mDynamicForm.setValue("cognome", record.getAttribute("cognome"));
				mDynamicForm.setValue("nome", record.getAttribute("nome"));
				mDynamicForm.setValue("codFiscale", record.getAttribute("codiceFiscale"));
			}
			setIndirizzoCompletoFromRecordRubrica(record);
			// mezzoTrasmissioneItem.refreshFilteredSelectIndirizzoDestinatario(idSoggetto);
			((FirmatariItem) getItem()).manageOnChanged();
		} else {
			// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un interessato di una registrazione <in entrata/ uscita / interna>
			mDynamicForm.setFieldErrors("codRapido",
					I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("interessato", "in uscita"));
		}
	}

	@Override
	public void editRecord(Record record) {
		manageLoadSelectAoomdgSoggettoInEditRecord(record);
		String idSoggetto = null;
		if (record.getAttribute("idSoggetto") != null && !record.getAttribute("idSoggetto").equalsIgnoreCase("")) {
			idSoggetto = (String) record.getAttribute("idSoggetto");
		}
		// mezzoTrasmissioneItem.refreshFilteredSelectIndirizzoDestinatario(idSoggetto);
		super.editRecord(record);
		if(isChangedRecord(record)) {			
			((FirmatariItem) getItem()).manageOnChanged();
		}
	}
	
	public void manageLoadSelectAoomdgSoggettoInEditRecord(Record record) {
		if ("AOOI".equalsIgnoreCase(record.getAttribute("tipoSoggetto"))) {
			if (record.getAttribute("aoomdgSoggetto") != null && !"".equals(record.getAttributeAsString("aoomdgSoggetto")) &&
				record.getAttribute("descrAoomdgSoggetto") != null && !"".equals(record.getAttributeAsString("descrAoomdgSoggetto")) ) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("aoomdgSoggetto"), record.getAttribute("descrAoomdgSoggetto"));
				aoomdgSoggettoItem.setValueMap(valueMap);
			}

			SelectGWTRestDataSource aoomdgSoggettoDS = (SelectGWTRestDataSource) aoomdgSoggettoItem.getOptionDataSource();
			if (record.getAttribute("aoomdgSoggetto") != null && !"".equals(record.getAttributeAsString("aoomdgSoggetto"))) {
				aoomdgSoggettoDS.addParam("idAooMdg", record.getAttributeAsString("aoomdgSoggetto"));
				aoomdgSoggettoDS.addParam("descrizioneAooMdg", record.getAttributeAsString("descrAoomdgSoggetto"));
			} else {
				aoomdgSoggettoDS.addParam("idAooMdg", null);
				aoomdgSoggettoDS.addParam("descrizioneAooMdg", null);
			}
			aoomdgSoggettoItem.setOptionDataSource(aoomdgSoggettoDS);
		}
	}

	public abstract class CercaSoggettoServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError(boolean trovatiSoggMultipliInRicerca);

		@Override
		public void execute(Record object) {
			mDynamicForm.clearErrors(true);
			String tipoSoggetto = calcolaTipoSoggetto(object.getAttribute("tipologiaSoggetto"));
			if (tipoSoggetto != null) {
				mDynamicForm.setValue("tipoSoggetto", tipoSoggetto);
				// Pulisco i dati del soggetto
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("denominazione", "");
				mDynamicForm.setValue("cognome", "");
				mDynamicForm.setValue("nome", "");
				mDynamicForm.setValue("codFiscale", "");
				if (object.getAttribute("codRapidoSoggetto") != null && !object.getAttribute("codRapidoSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codRapido", object.getAttribute("codRapidoSoggetto"));
				}
				if (object.getAttribute("denominazioneSoggetto") != null && !object.getAttribute("denominazioneSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("denominazione", object.getAttribute("denominazioneSoggetto"));
				}
				if (object.getAttribute("cognomeSoggetto") != null && !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("cognome", object.getAttribute("cognomeSoggetto"));
				}
				if (object.getAttribute("nomeSoggetto") != null && !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("nome", object.getAttribute("nomeSoggetto"));
				}
				if (object.getAttribute("codfiscaleSoggetto") != null && !object.getAttribute("codfiscaleSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codFiscale", object.getAttribute("codfiscaleSoggetto"));
				}
				if (object.getAttribute("idSoggetto") != null && !object.getAttribute("idSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idSoggetto", object.getAttribute("idSoggetto"));
				}
				if (object.getAttribute("idUoSoggetto") != null && !object.getAttribute("idUoSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idUoSoggetto", object.getAttribute("idUoSoggetto"));
				}
				if (object.getAttribute("idUserSoggetto") != null && !object.getAttribute("idUserSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idUserSoggetto", object.getAttribute("idUserSoggetto"));
				}
				if (object.getAttribute("idScrivaniaSoggetto") != null && !object.getAttribute("idScrivaniaSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idScrivaniaSoggetto", object.getAttribute("idScrivaniaSoggetto"));
				}
				// Se il codice soggetto restituito e' AOOI (=AOO MDG) inizializzo la select
				if (mDynamicForm.getValueAsString("tipoSoggetto").equalsIgnoreCase("AOOI")) {
					cercaSoggettoAOOMDG();
				}
				String idSoggetto = null;
				if (object.getAttribute("idSoggetto") != null && !object.getAttribute("idSoggetto").equalsIgnoreCase("")) {
					idSoggetto = (String) object.getAttribute("idSoggetto");
				}
				if (showItemsIndirizzo()) {
					setIndirizzoAfterFindSoggettoService(object);
				}				
				// mezzoTrasmissioneItem.refreshFilteredSelectIndirizzoDestinatario(idSoggetto);
				((FirmatariItem) getItem()).manageOnChanged();
				mDynamicForm.markForRedraw();
			} else {
				// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un interessato di una registrazione <in entrata/ uscita / interna>
				executeOnError(object.getAttribute("trovatiSoggMultipliInRicerca") != null && object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca"));
			}
		}
	}

	public boolean isChangedAndValid() {
		String denominazione = denominazioneItem.isVisible() ? denominazioneItem.getValueAsString() : null;
		String cognome = cognomeItem.isVisible() ? cognomeItem.getValueAsString() : null;
		String nome = nomeItem.isVisible() ? nomeItem.getValueAsString() : null;
		return isChanged()
				&& ((denominazione != null && !"".equals(denominazione)) || ((cognome != null && !"".equals(cognome)) && (nome != null && !"".equals(nome))));
	}

	public void canEditMezzoTrasmissione() {
		// mezzoTrasmissioneItem.setCanEditMezzoTrasmissioneMode();
	}
	
	public boolean showItemsIndirizzo() {
		return true;
	}

	@Override
	public boolean showOpenIndirizzo() {
		return true;
	}

}
