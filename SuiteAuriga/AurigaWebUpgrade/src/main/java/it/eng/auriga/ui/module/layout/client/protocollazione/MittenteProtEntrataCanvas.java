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
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
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
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class MittenteProtEntrataCanvas extends IndirizzoCanvas {

	private HiddenItem idMittenteItem;
	private HiddenItem idSoggettoItem; // id. in rubrica
	private HiddenItem idUoSoggettoItem;
	private HiddenItem idUserSoggettoItem;
	private HiddenItem idScrivaniaSoggettoItem;
	private HiddenItem idAssegnatarioItem;
	private HiddenItem flgSelXAssegnazioneItem;	
	private HiddenItem flgAssegnaAlMittenteXNuovaProtComeCopiaItem;
	private SelectItem tipoMittenteItem;
	private ExtendedTextItem codRapidoMittenteItem;
	private ExtendedTextItem denominazioneMittenteItem;
	private ExtendedTextItem cognomeMittenteItem;
	private ExtendedTextItem nomeMittenteItem;
	private ExtendedTextItem codfiscaleMittenteItem;
	private ImgButtonItem salvaInRubricaButton;
	private ImgButtonItem visualizzaInRubricaButton;
	private TextItem emailMittenteItem;
	private TextItem telMittenteItem;
	private HiddenItem descrAoomdgMittenteItem;
	private FilteredSelectItemWithDisplay aoomdgMittenteItem;
	private CheckboxItem flgAssegnaAlMittenteItem;
	private HiddenItem opzioniInvioHiddenItem;
	
	public boolean showFlgAssegnaAlMittente = false;

	public MittenteProtEntrataCanvas(ReplicableItem item) {
		super(item);		
		addDrawHandler(new DrawHandler() {
			
			@Override
			public void onDraw(DrawEvent event) {
				showHideIndirizzo();		
			}
		});		
	}

	@Override
	public void buildMainForm() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idMittenteItem = new HiddenItem("idMittente");

		idSoggettoItem = new HiddenItem("idSoggetto");
		idUoSoggettoItem = new HiddenItem("idUoSoggetto");
		idUserSoggettoItem = new HiddenItem("idUserSoggetto");
		idScrivaniaSoggettoItem = new HiddenItem("idScrivaniaSoggetto");
		idAssegnatarioItem = new HiddenItem("idAssegnatario");
		flgSelXAssegnazioneItem = new HiddenItem("flgSelXAssegnazione");
		flgAssegnaAlMittenteXNuovaProtComeCopiaItem = new HiddenItem("flgAssegnaAlMittenteXNuovaProtComeCopia");
		
		// tipo
		tipoMittenteItem = new SelectItem("tipoMittente","Tipo mittente");
		LinkedHashMap<String,String> styleMap = new LinkedHashMap<String, String>();
		if (allowOnlyPersonaFisica()) {
			tipoMittenteItem.setAllowEmptyValue(false);
			tipoMittenteItem.setDefaultValue("PF");
		} else {
			tipoMittenteItem.setAllowEmptyValue(!AurigaLayout.getIsAttivaAccessibilita());
		}
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			styleMap.put("", I18NUtil.getMessages().protocollazione_select_listmap_generic_value());
			tipoMittenteItem.setDefaultValue("");
		}
		styleMap.putAll(getTipoMittenteMap());
		// tipoMittenteItem.setRequired(true);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tipoMittenteItem.setTabIndex(null);	
			tipoMittenteItem.setShowTitle(true);		
		} else {
			tipoMittenteItem.setShowTitle(false);
		}
		tipoMittenteItem.setValueMap(styleMap);
		tipoMittenteItem.setWidth(150);
		tipoMittenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showItemSelTipoMittente();
			}
		});
		tipoMittenteItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				tipoMittenteItem.setAttribute("oldValue", event.getOldValue());
			}
		});
		tipoMittenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.clearErrors(true);
				String oldValue = tipoMittenteItem.getAttribute("oldValue") != null ? tipoMittenteItem.getAttribute("oldValue") : "";
				String value = event.getValue() != null ? (String) event.getValue() : "";
				// controllo se rispetto a prima sia cambiato il fatto che si veda o meno il campo denominazione
				boolean isChangedShowDenominazione = !(("".equals(oldValue) && toShowDenominazione(value)) || (toShowDenominazione(oldValue) && "".equals(value)) || (toShowDenominazione(oldValue) && toShowDenominazione(value)));
				// non faccio l'altro controllo perchè qui il soggetto è sempre esterno				
				if (isChangedShowDenominazione) {
					Record lRecord = mDynamicForm.getValuesAsRecord();
					lRecord.setAttribute("codRapidoMittente", "");
					lRecord.setAttribute("denominazioneMittente", "");
					lRecord.setAttribute("cognomeMittente", "");
					lRecord.setAttribute("nomeMittente", "");
					lRecord.setAttribute("codfiscaleMittente", "");
//					lRecord.setAttribute("flgAssegnaAlMittente", false); // non posso settare il check a false altrimenti non viene caricato successivamente  
						 												 // il valore di default, quindi utilizzo il clearValue()					
					lRecord.setAttribute("aoomdgMittente", "");
					lRecord.setAttribute("idMittente", "");		
					lRecord.setAttribute("idSoggetto", "");
					lRecord.setAttribute("idUoSoggetto", "");
					lRecord.setAttribute("idUserSoggetto", "");
					lRecord.setAttribute("idScrivaniaSoggetto", "");
					lRecord.setAttribute("flgSelXAssegnazione", "");					
					mDynamicForm.setValues(lRecord.toMap());
					mDynamicForm.clearValue("flgAssegnaAlMittente");					
					((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
					showHideFlgAssegnaAlMittente();
					((MittenteProtEntrataItem) getItem()).manageOnChanged();
				}
				showHideIndirizzo();
			}
		});
		
		// cod.rapido
		codRapidoMittenteItem = new ExtendedTextItem("codRapidoMittente", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoMittenteItem.setWidth(120);
		codRapidoMittenteItem.setColSpan(1);
		codRapidoMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("idMittente", "");
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");	
				mDynamicForm.clearValue("flgAssegnaAlMittente");
				((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
				if (event.getValue() != null && !"".equals(event.getValue())) {
					Record lRecord = new Record();
					lRecord.setAttribute("codRapidoSoggetto", event.getValue());
					cercaSoggetto(lRecord, new CercaSoggettoServiceCallback() {

						@Override
						public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
							// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in entrata/
							// uscita / interna>
							mDynamicForm.setFieldErrors("codRapidoMittente", I18NUtil.getMessages()
									.protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("mittente", "in entrata"));							
						}
					});
				}
			}
		});

		// denominazione
		denominazioneMittenteItem = new ExtendedTextItem("denominazioneMittente", I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());
		denominazioneMittenteItem.setWidth(250);
		denominazioneMittenteItem.setAttribute("obbligatorio", true);
		denominazioneMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return toShowDenominazione(tipoMittenteItem.getValueAsString());
			}
		});
		denominazioneMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return toShowDenominazione(tipoMittenteItem.getValueAsString()) && (((MittenteProtEntrataItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()));
			}
		}));
		denominazioneMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((MittenteProtEntrataItem) getItem()).manageOnChanged();
				if(denominazioneMittenteItem.getValue() != null && !"".equals(denominazioneMittenteItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("denominazioneSoggetto", denominazioneMittenteItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		denominazioneMittenteItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cognome
		cognomeMittenteItem = new ExtendedTextItem("cognomeMittente", I18NUtil.getMessages().protocollazione_detail_cognomeItem_title());
		cognomeMittenteItem.setWidth(125);
		cognomeMittenteItem.setAttribute("obbligatorio", true);
		cognomeMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isPersonaFisica(tipoMittenteItem.getValueAsString());
			}
		});
		cognomeMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isPersonaFisica(tipoMittenteItem.getValueAsString()) && (((MittenteProtEntrataItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()));
			}
		}));
		cognomeMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((MittenteProtEntrataItem) getItem()).manageOnChanged();
				if(cognomeMittenteItem.getValue() != null && !"".equals(cognomeMittenteItem.getValue()) &&
				   nomeMittenteItem.getValue() != null && !"".equals(nomeMittenteItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeMittenteItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeMittenteItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		cognomeMittenteItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// nome
		nomeMittenteItem = new ExtendedTextItem("nomeMittente", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeMittenteItem.setWidth(125);
		nomeMittenteItem.setAttribute("obbligatorio", true);
		nomeMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isPersonaFisica(tipoMittenteItem.getValueAsString());
			}
		});
		nomeMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isPersonaFisica(tipoMittenteItem.getValueAsString()) && (((MittenteProtEntrataItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()));
			}
		}));
		nomeMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((MittenteProtEntrataItem) getItem()).manageOnChanged();
				if(cognomeMittenteItem.getValue() != null && !"".equals(cognomeMittenteItem.getValue()) &&
				   nomeMittenteItem.getValue() != null && !"".equals(nomeMittenteItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeMittenteItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeMittenteItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		nomeMittenteItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cod.fiscale
		codfiscaleMittenteItem = new ExtendedTextItem("codfiscaleMittente", I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
		codfiscaleMittenteItem.setWidth(150);
		codfiscaleMittenteItem.setCharacterCasing(CharacterCasing.UPPER);
		codfiscaleMittenteItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals(value) || AurigaLayout.getParametroDBAsBoolean("DISATTIVA_CTRL_CF_PIVA_EMDI")) {
					return true;
				}
				if (isPersonaFisica(tipoMittenteItem.getValueAsString())) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);
				}
			}
		});		
		codfiscaleMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((MittenteProtEntrataItem) getItem()).manageOnChanged();
				if(codfiscaleMittenteItem.getValue() != null && !"".equals(codfiscaleMittenteItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("codfiscaleSoggetto", codfiscaleMittenteItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		codfiscaleMittenteItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});
		codfiscaleMittenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String tipoMittente = tipoMittenteItem.getValueAsString();
				if (isPersonaFisica(tipoMittente)) {
					codfiscaleMittenteItem.setLength(16);
					codfiscaleMittenteItem.setTitle(I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
				} else {
					codfiscaleMittenteItem.setLength(28);
					codfiscaleMittenteItem.setTitle(I18NUtil.getMessages().protocollazione_detail_codFiscalePIVAItem_title());
				}
				return toShowDenominazione(tipoMittente) || isPersonaFisica(tipoMittente);
			}
		});
		
		emailMittenteItem = new TextItem("emailMittente", "e-mail");		
		//emailMittenteItem.setAttribute("obbligatorio", Boolean.toString(isEmailMittenteItemObbligatorio()));		
		emailMittenteItem.setAttribute("obbligatorio", (isEmailMittenteItemObbligatorio()));		
		emailMittenteItem.setWidth(240);
		emailMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((MittenteProtEntrataItem)getItem()).getShowEmail();
			}
		});
		emailMittenteItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(((MittenteProtEntrataItem)getItem()).getShowEmail()) {
					if(value == null || "".equals((String) value)) return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					return regExp.test((String) value);		
				}
				return true;
			}
		});
		
		telMittenteItem = new TextItem("telMittente", "Tel.");
		telMittenteItem.setAttribute("obbligatorio", false);
		telMittenteItem.setWidth(160);
		telMittenteItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((MittenteProtEntrataItem)getItem()).getShowTelefono();
			}
		});
		// TODEL Tolta espressione regolare per validare il numero di telefono, ne serve una migliore
		// telMittenteItem.setValidators(new RegExpValidator("^[0-9]{5,12}"));

		descrAoomdgMittenteItem = new HiddenItem("descrAoomdgMittente");

		// soggetto AOO MDG
		SelectGWTRestDataSource lGwtRestDataSourceAooMdg = new SelectGWTRestDataSource("LoadComboSoggettiAooMdgDataSource", "idAooMdg", FieldType.TEXT,
				new String[] { "descrizioneAooMdg" }, true);

		aoomdgMittenteItem = new FilteredSelectItemWithDisplay("aoomdgMittente", lGwtRestDataSourceAooMdg) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				SelectGWTRestDataSource aoomdgMittenteDS = (SelectGWTRestDataSource) aoomdgMittenteItem.getOptionDataSource();
				aoomdgMittenteDS.addParam("descrizioneAooMdg", record.getAttributeAsString("descrizioneAooMdg"));
				aoomdgMittenteItem.setOptionDataSource(aoomdgMittenteDS);

				mDynamicForm.setValue("descrAoomdgMittente", record.getAttributeAsString("descrizioneAooMdg"));
				mDynamicForm.setValue("idSoggetto", record.getAttributeAsString("idAooMdg"));
				mDynamicForm.setValue("codRapidoMittente", record.getAttributeAsString("codiceRapidoAooMdg"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				SelectGWTRestDataSource aoomdgMittenteDS = (SelectGWTRestDataSource) aoomdgMittenteItem.getOptionDataSource();
				aoomdgMittenteDS.addParam("descrizioneAooMdg", null);
				aoomdgMittenteItem.setOptionDataSource(aoomdgMittenteDS);

				mDynamicForm.setValue("aoomdgMittente", "");
				mDynamicForm.setValue("descrAoomdgMittente", "");
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("codRapidoMittente", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					SelectGWTRestDataSource aoomdgMittenteDS = (SelectGWTRestDataSource) aoomdgMittenteItem.getOptionDataSource();
					aoomdgMittenteDS.addParam("descrizioneAooMdg", null);
					aoomdgMittenteItem.setOptionDataSource(aoomdgMittenteDS);

					mDynamicForm.setValue("descrAoomdgMittente", "");
					mDynamicForm.setValue("idSoggetto", "");
					mDynamicForm.setValue("codRapidoMittente", "");
				}
			}
		};
		aoomdgMittenteItem.setAutoFetchData(false);
		aoomdgMittenteItem.setFetchMissingValues(true);

		ListGridField codiceRapidoAooMdgField = new ListGridField("codiceRapidoAooMdg", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoAooMdgField.setWidth(80);

		ListGridField descrizioneAooMdgField = new ListGridField("descrizioneAooMdg", I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());
		// descrizioneAooMdgField.setWidth(200);

		ListGridField descrizioneEstesaAooMdgField = new ListGridField("descrizioneEstesaAooMdg", I18NUtil.getMessages()
				.protocollazione_detail_denominazioneEstesaItem_title());
		descrizioneEstesaAooMdgField.setHidden(true);
		// descrizioneEstesaAooMdgField.setWidth("*");

		aoomdgMittenteItem.setPickListFields(codiceRapidoAooMdgField, descrizioneAooMdgField, descrizioneEstesaAooMdgField);
		aoomdgMittenteItem.setEmptyPickListMessage(I18NUtil.getMessages().protocollazione_detail_aoomdgMittDestItem_noSearchOrEmptyMessage());
		// aoomdgMittenteItem.setFilterLocally(true);
		aoomdgMittenteItem.setValueField("idAooMdg");
		aoomdgMittenteItem.setOptionDataSource(lGwtRestDataSourceAooMdg);
		aoomdgMittenteItem.setShowTitle(false);
		aoomdgMittenteItem.setWidth(450);
		aoomdgMittenteItem.setClearable(true);
		aoomdgMittenteItem.setShowIcons(true);
		aoomdgMittenteItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesaAooMdg") : null;
			}
		});
		aoomdgMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return tipoMittente != null && "AOOI".equals(tipoMittente);
			}
		});
		aoomdgMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return tipoMittente != null && "AOOI".equals(tipoMittente);
			}
		}));
		aoomdgMittenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((MittenteProtEntrataItem) getItem()).manageOnChanged();
			}
		});

		// bottone seleziona da rubrica
		ImgButtonItem lookupRubricaButton = new ImgButtonItem("lookupRubricaButton", "lookup/rubrica.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupRubricaButton_prompt());
		lookupRubricaButton.setColSpan(1);
		lookupRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return tipoMittente == null || "".equals(tipoMittente) || isPersonaFisica(tipoMittente) || isPersonaGiuridica(tipoMittente);
			}
		});
		lookupRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(false);			
				MittenteLookupRubrica lookupRubricaPopup = new MittenteLookupRubrica(null, tipoMittenteItem.getValueAsString());
				lookupRubricaPopup.show();
			}
		});

		// bottone cerca da rubrica
		ImgButtonItem cercaInRubricaButton = new ImgButtonItem("cercaInRubricaButton", "lookup/rubricasearch.png", I18NUtil.getMessages()
				.protocollazione_detail_cercaInRubricaButton_prompt());
		cercaInRubricaButton.setColSpan(1);
		cercaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return tipoMittente == null || "".equals(tipoMittente) || isPersonaFisica(tipoMittente)
						|| (isPersonaGiuridica(tipoMittente) && !"AOOI".equalsIgnoreCase(tipoMittente));
			}
		});
		cercaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(false);				
				cercaInRubrica();
			}
		});

		// bottone salva in rubrica
		salvaInRubricaButton = new ImgButtonItem("salvainrubricaMittente", "buttons/saveIn.png", I18NUtil.getMessages()
				.protocollazione_detail_salvaInRubricaButton_prompt());
		salvaInRubricaButton.setColSpan(1);
		salvaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return Layout.isPrivilegioAttivo("GRS/S/UO;I")
						&& (tipoMittente == null || "".equals(tipoMittente) || ((isPersonaFisica(tipoMittente) || isPersonaGiuridica(tipoMittente))
								&& !"AOOI".equalsIgnoreCase(tipoMittente) && !"PA".equalsIgnoreCase(tipoMittente))) 
						&& !(idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue()));
			}
		});
		salvaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(false);			
				String tipo = tipoMittenteItem.getValueAsString();
				String codRapido = codRapidoMittenteItem.getValueAsString();
				String denominazione = denominazioneMittenteItem.isVisible() ? denominazioneMittenteItem.getValueAsString() : null;
				String cognome = cognomeMittenteItem.isVisible() ? cognomeMittenteItem.getValueAsString() : null;
				String nome = nomeMittenteItem.isVisible() ? nomeMittenteItem.getValueAsString() : null;
				String codiceFiscale = codfiscaleMittenteItem.isVisible() ? codfiscaleMittenteItem.getValueAsString() : null;
				Record recordMittente = new Record();
				recordMittente.setAttribute("tipo", tipo);
				recordMittente.setAttribute("codRapido", codRapido);
				recordMittente.setAttribute("denominazione", denominazione);
				recordMittente.setAttribute("cognome", cognome);
				recordMittente.setAttribute("nome", nome);
				recordMittente.setAttribute("codiceFiscale", codiceFiscale);
				Record recordIndirizzo = getFormValuesAsRecord();
				SalvaInRubricaPopup salvaInRubricaPopup = new SalvaInRubricaPopup("SEL_SOGG_EST", recordMittente, recordIndirizzo) {

					@Override
					public void manageLookupBack(Record record) {
						setFormValuesFromRecordRubrica(record);
					}
				};
				salvaInRubricaPopup.show();
			}
		});
		
		//bottone visualizza nominativo in rubrica
		visualizzaInRubricaButton = new ImgButtonItem("visualizzaInRubricaMittente", "buttons/detail.png", I18NUtil.getMessages()
				.protocollazione_detail_visualizzaInRubricaButton_prompt());
		visualizzaInRubricaButton.setAlwaysEnabled(true);
		visualizzaInRubricaButton.setColSpan(1);
		visualizzaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue())
						&& (tipoMittente == null || "".equals(tipoMittente) || isPersonaFisica(tipoMittente) || isPersonaGiuridica(tipoMittente));
			}
		});
		
		visualizzaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(false);			
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
							String nomeDettaglio = !mDynamicForm.getValueAsString("denominazioneMittente").equals("") ? 
									mDynamicForm.getValueAsString("denominazioneMittente") : mDynamicForm.getValueAsString("cognomeMittente") + " " + mDynamicForm.getValueAsString("nomeMittente");
							Layout.addModalWindow("dettaglio_soggetto", "Dettaglio " + nomeDettaglio, "buttons/detail.png", detail);
						}
					}
				});
			}
		});

		// check assegna
		flgAssegnaAlMittenteItem = new CheckboxItem("flgAssegnaAlMittente", I18NUtil.getMessages().protocollazione_detail_flgAssegnaItem_title());
		flgAssegnaAlMittenteItem.setRequired(false);
		flgAssegnaAlMittenteItem.setColSpan(1);
		flgAssegnaAlMittenteItem.setWidth(70);
		flgAssegnaAlMittenteItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtItem) getItem()).manageChangeFlgAssegnaAlMittente(event);				
			}
		});
		flgAssegnaAlMittenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
				mDynamicForm.redraw();
			}
		});
		flgAssegnaAlMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {					
				item.setTitle(I18NUtil.getMessages().protocollazione_detail_flgAssegnaItem_title());
				item.setCanEdit(getEditing());
				//TODO invece di guardare idAssegnatario posso guardare tutti i record di listaAssegnazioniSalvate
				String idAssegnatario = mDynamicForm.getValueAsString("idAssegnatario") != null ? mDynamicForm.getValueAsString("idAssegnatario") : null;
				if(idAssegnatario != null && !"".equals(idAssegnatario)) {
					String idUoSoggetto = mDynamicForm.getValueAsString("idUoSoggetto") != null ? mDynamicForm.getValueAsString("idUoSoggetto") : "";
					String idScrivaniaSoggetto = mDynamicForm.getValueAsString("idScrivaniaSoggetto") != null ? mDynamicForm.getValueAsString("idScrivaniaSoggetto") : "";
					String idUserSoggetto = mDynamicForm.getValueAsString("idUserSoggetto") != null ? mDynamicForm.getValueAsString("idUserSoggetto") : "";
					if((idAssegnatario.startsWith("UO") && idUoSoggetto.equals(idAssegnatario.substring(2))) ||
					   (idAssegnatario.startsWith("SV") && idScrivaniaSoggetto.equals(idAssegnatario.substring(2))) ||
					   (idAssegnatario.startsWith("UT") && idUserSoggetto.equals(idAssegnatario.substring(2)))) {
						boolean flgAssegnaAlMittente = mDynamicForm.getValue("flgAssegnaAlMittente") != null ? (Boolean) mDynamicForm.getValue("flgAssegnaAlMittente") : false;
						if(!flgAssegnaAlMittente) {
							mDynamicForm.setValue("flgAssegnaAlMittente", true);
							((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
						}
						item.setTitle(I18NUtil.getMessages().protocollazione_detail_flgAssegnataItem_title());
						item.setCanEdit(false);
						return true;
					}
				}
				//TODO per decommentare questa parte devo fare in modo che flgSelXAssegnazione venga caricato nel dettaglio
//				boolean flgSelXAssegnazione = mDynamicForm.getValueAsString("flgSelXAssegnazione") != null && "1".equals(mDynamicForm.getValueAsString("flgSelXAssegnazione"));				
//				if(/*!fromLoadDett && */!flgSelXAssegnazione) {
//					mDynamicForm.setValue("flgAssegnaAlMittente", false);
//					((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
//					item.setCanEdit(false);
//				}
				return showFlgAssegnaAlMittente;
			}
		});

		opzioniInvioHiddenItem = new HiddenItem("opzioniInvio");
		
		// BOTTONE : opzioni invio sull'assegnazione
		ImgButtonItem opzioniInvioAssegnazioneButton = new ImgButtonItem("opzioniInvioAssegnazioneButton", "buttons/altriDati.png", I18NUtil.getMessages()
				.protocollazione_detail_opzioniInvioAssegnazioneButton_prompt());
//		opzioniInvioAssegnazioneButton.setAlwaysEnabled(true);
		opzioniInvioAssegnazioneButton.setColSpan(1);
		opzioniInvioAssegnazioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(false);		
				String tipoMittente = new Record(mDynamicForm.getValues()).getAttribute("tipoMittente");
				boolean flgUo = tipoMittente != null && "UOI".equals(tipoMittente);
				Record recordOpzioniInvio = new Record(mDynamicForm.getValues()).getAttributeAsRecord("opzioniInvio");
				OpzioniInvioAssegnazionePopup opzioniInvioAssegnazionePopup = new OpzioniInvioAssegnazionePopup(flgUo, recordOpzioniInvio, getEditing()) {

					@Override
					public String getIdUd() {
						return ((MittenteProtItem) getItem()).getIdUdProtocollo();
					}
					
					@Override
					public String getFlgUdFolder() {
						return "U";
					}
					
					@Override
					public void onClickOkButton(Record record) {
						mDynamicForm.setValue("opzioniInvio", record);	
					}
				};
				opzioniInvioAssegnazionePopup.show();
			}
		});
		opzioniInvioAssegnazioneButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAssegnaAlMittente && flgAssegnaAlMittenteItem.getValueAsBoolean();
			}
		});

		mDynamicForm.setFields(
			idMittenteItem,
			idSoggettoItem,
			idUoSoggettoItem,
			idUserSoggettoItem,
			idScrivaniaSoggettoItem,
			idAssegnatarioItem,
			flgSelXAssegnazioneItem,
			flgAssegnaAlMittenteXNuovaProtComeCopiaItem,
			tipoMittenteItem,
			codRapidoMittenteItem,
			lookupRubricaButton,
			denominazioneMittenteItem,
			cognomeMittenteItem,
			nomeMittenteItem,
			codfiscaleMittenteItem,
			emailMittenteItem,
			telMittenteItem,
			descrAoomdgMittenteItem,
			aoomdgMittenteItem,
			cercaInRubricaButton,
			salvaInRubricaButton,
			visualizzaInRubricaButton,
			flgAssegnaAlMittenteItem,
			opzioniInvioHiddenItem,
			opzioniInvioAssegnazioneButton
		);

		if (AurigaLayout.getIsAttivaAccessibilita()) {
			mDynamicForm.setTabIndex(-1);
			mDynamicForm.setCanFocus(false);		
		}
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50",
				"100");

	}

	public void showHideIndirizzo() {
		// String tipo = mDynamicForm.getValueAsString("tipoMittente");
		// setVisibleIndirizzo(tipo != null && "PF".equals(tipo));
		try {			
			String mezzoTrasmissione = ((MittenteProtEntrataItem) getItem()).getMezzoTrasmissione();					
			if (mezzoTrasmissione != null && ("PEC".equals(mezzoTrasmissione) || "PEO".equals(mezzoTrasmissione))) {
				setVisibleIndirizzo(false);
			} else {
				setVisibleIndirizzo(true);
			}			
		} catch(Exception e) {}	
	}

	public boolean isValorizzatoSoggPerAssegnazione() {
		String idUoSoggetto = mDynamicForm.getValueAsString("idUoSoggetto");
		if (idUoSoggetto == null)
			idUoSoggetto = "";
		// String idUserSoggetto = mDynamicForm.getValueAsString("idUserSoggetto");
		// if (idUserSoggetto == null)
		// idUserSoggetto = "";
		String idScrivaniaSoggetto = mDynamicForm.getValueAsString("idScrivaniaSoggetto");
		if (idScrivaniaSoggetto == null)
			idScrivaniaSoggetto = "";
		String supportoOriginale = ((MittenteProtItem) getItem()).getSupportoOriginaleProt();
		if(supportoOriginale != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "cartaceo".equals(supportoOriginale)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "digitale".equals(supportoOriginale)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "misto".equals(supportoOriginale))) {
				return !idUoSoggetto.equalsIgnoreCase("");
			}
		}
		return (!idUoSoggetto.equalsIgnoreCase("") /* || !idUserSoggetto.equalsIgnoreCase("") */|| !idScrivaniaSoggetto.equalsIgnoreCase(""));
	}
	
	public void showHideFlgAssegnaAlMittente() {
		// Visualizzo solo se uno dei 3 campi idUoSoggetto, idUserSoggetto, idScrivaniaSoggetto e' valorizzato
		boolean fromLoadDett =  mDynamicForm.getValue("fromLoadDett") != null ? (Boolean) mDynamicForm.getValue("fromLoadDett") : false; 
		if (((MittenteProtItem) getItem()).getShowFlgAssegnaAlMittente() && isValorizzatoSoggPerAssegnazione()) {		
			if(!fromLoadDett && mDynamicForm.getValue("flgAssegnaAlMittente") == null) {				
				if(mDynamicForm.getValue("flgAssegnaAlMittenteXNuovaProtComeCopia") != null) {
					boolean flgAssegnaAlMittenteXNuovaProtComeCopia = (Boolean) mDynamicForm.getValue("flgAssegnaAlMittenteXNuovaProtComeCopia");
					mDynamicForm.setValue("flgAssegnaAlMittente", flgAssegnaAlMittenteXNuovaProtComeCopia);
					((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
				} else {
					boolean flgSelXAssegnazione = mDynamicForm.getValueAsString("flgSelXAssegnazione") != null && "1".equals(mDynamicForm.getValueAsString("flgSelXAssegnazione"));
					if(((MittenteProtItem) getItem()).isAttivoAssegnatarioUnicoProt() && ((MittenteProtItem) getItem()).getNroAssegnazioniProt() > 0) {							
						flgSelXAssegnazione = false;
					}
					if(((MittenteProtItem) getItem()).isProtInModalitaWizard()) {
						if(((MittenteProtItem) getItem()).isSupportoOriginaleProtValorizzato() && ((MittenteProtItem) getItem()).isAttivoAssegnatarioUnicoCartaceoProt() && ((MittenteProtItem) getItem()).getNroAssegnazioniProt() > 0) {							
							flgSelXAssegnazione = false;
						}
					}
					// se il soggetto è selezionabile per l'assegnazione allora setto il check al valore di default
					if(flgSelXAssegnazione) {
						mDynamicForm.setValue("flgAssegnaAlMittente", ((MittenteProtItem) getItem()).getFlgAssegnaAlMittenteDefault());
						((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
					} 
				}						
			}
			showFlgAssegnaAlMittente = true;
		} else {
//			mDynamicForm.setValue("flgAssegnaAlMittente", false); // non posso settare il check a false altrimenti non viene caricato successivamente  
			  													  // il valore di default, quindi utilizzo il clearValue()
			mDynamicForm.clearValue("flgAssegnaAlMittente");
			((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
			showFlgAssegnaAlMittente = false;
		}
		mDynamicForm.markForRedraw();
	}
	
	public class MittenteLookupRubrica extends LookupSoggettiPopup {

		public MittenteLookupRubrica(Record record, String tipoMittente) {
			super(record, tipoMittente, true);
		}
		
		@Override
		public String getFinalita() {
			if (getItem() instanceof MittenteProtEntrataItem) {
				return ((MittenteProtEntrataItem) getItem()).getFinalitaLookup();
			}else {
				return "SEL_SOGG_EST";
			}
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
			return AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST") ? new String[] { "AOOI", "PA", "PF", "PG" } : new String[] { "PA", "PF", "PG" };
		}

	}

	protected void clearFormSoggettoValues() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapidoMittente", "");
		lRecord.setAttribute("denominazioneMittente", "");
		lRecord.setAttribute("cognomeMittente", "");
		lRecord.setAttribute("nomeMittente", "");
		lRecord.setAttribute("codfiscaleMittente", "");
		mDynamicForm.setValues(lRecord.toMap());
		((MittenteProtEntrataItem) getItem()).manageOnChanged();
	}

	protected void clearIdSoggetto() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapidoMittente", "");
		lRecord.setAttribute("idMittente", "");		
		lRecord.setAttribute("idSoggetto", "");
		lRecord.setAttribute("idUoSoggetto", "");
		lRecord.setAttribute("idUserSoggetto", "");
		lRecord.setAttribute("idScrivaniaSoggetto", "");
		lRecord.setAttribute("flgSelXAssegnazione", "");		
		mDynamicForm.setValues(lRecord.toMap());
		mDynamicForm.clearValue("flgAssegnaAlMittente");
		((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
	}

	protected boolean toShowDenominazione(String tipoSoggetto) {
		return tipoSoggetto == null || "".equals(tipoSoggetto) || (isPersonaGiuridica(tipoSoggetto) && !"AOOI".equalsIgnoreCase(tipoSoggetto));
	}

	protected boolean isPersonaGiuridica(String tipoSoggetto) {
		return ((MittenteProtItem) getItem()).isPersonaGiuridica(tipoSoggetto);
	}

	protected boolean isPersonaFisica(String tipoSoggetto) {
		return ((MittenteProtItem) getItem()).isPersonaFisica(tipoSoggetto);
	}

	protected void cercaInRubrica() {
		final Record lRecord = new Record();
		lRecord.setAttribute("denominazioneSoggetto", denominazioneMittenteItem.isVisible() ? denominazioneMittenteItem.getValue() : null);
		lRecord.setAttribute("cognomeSoggetto", cognomeMittenteItem.isVisible() ? cognomeMittenteItem.getValue() : null);
		lRecord.setAttribute("nomeSoggetto", nomeMittenteItem.isVisible() ? nomeMittenteItem.getValue() : null);
		lRecord.setAttribute("codfiscaleSoggetto", codfiscaleMittenteItem.isVisible() ? codfiscaleMittenteItem.getValue() : null);
		lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());		
		cercaInRubrica(lRecord, true);		
	}
	
	protected void cercaInRubricaAfterChangedField(final Record record) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_CERCA_IN_RUBRICA")) {	
			Timer t1 = new Timer() {
				public void run() {
					if(((MittenteProtEntrataItem) getItem()).isCercaInRubricaAfterChanged()) {
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
					MittenteLookupRubrica lookupRubricaPopup = new MittenteLookupRubrica(record, tipoMittenteItem.getValueAsString());
					lookupRubricaPopup.show();
				}
			}
		});	
	}
	
	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		lGwtRestService.addParam("tipiAmmessi", AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST") ? "AOOI,PA,PF,PG" : "PA,PF,PG");
		if(((MittenteProtItem) getItem()).isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {				
			lGwtRestService.addParam("finalita", ((MittenteProtItem) getItem()).isAttivaRestrAssCartaceoProt() ? "MITT_DEST_CARTACEO" : "MITT_DEST_NO_CARTACEO");
		} else {
			lGwtRestService.addParam("finalita", "MITT_DEST");
		}
		lGwtRestService.addParam("idUd", ((MittenteProtItem) getItem()).getIdUdProtocollo());
		lGwtRestService.call(lRecord, callback);
	}

	protected void cercaSoggettoAOOMDG() {
		final String codRapidoMittente = codRapidoMittenteItem.getValueAsString();
		if(codRapidoMittente != null && !"".equals(codRapidoMittente)) {
			mDynamicForm.setValue("idSoggetto", (String) null);
			mDynamicForm.setValue("aoomdgMittente", (String) null);
			mDynamicForm.clearErrors(true);
			SelectGWTRestDataSource aoomdgMittenteDS = (SelectGWTRestDataSource) aoomdgMittenteItem.getOptionDataSource();
			aoomdgMittenteDS.addParam("codiceRapidoAooMdg", codRapidoMittente);
			aoomdgMittenteDS.addParam("descrizioneAooMdg", null);
			aoomdgMittenteItem.setOptionDataSource(aoomdgMittenteDS);
			aoomdgMittenteDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String codiceRapidoAooMdg = data.get(i).getAttribute("codiceRapidoAooMdg");
							if (codRapidoMittente.equalsIgnoreCase(codiceRapidoAooMdg)) {
								SelectGWTRestDataSource aoomdgMittenteDS = (SelectGWTRestDataSource) aoomdgMittenteItem.getOptionDataSource();
								aoomdgMittenteDS.addParam("descrizioneAooMdg", data.get(i).getAttributeAsString("descrizioneAooMdg"));
								aoomdgMittenteItem.setOptionDataSource(aoomdgMittenteDS);
								mDynamicForm.setValue("idSoggetto", data.get(i).getAttribute("idAooMdg"));
								mDynamicForm.setValue("aoomdgMittente", data.get(i).getAttribute("idAooMdg"));
								mDynamicForm.setValue("descrAoomdgMittente", data.get(i).getAttributeAsString("descrizioneAooMdg"));
								((MittenteProtEntrataItem) getItem()).manageOnChanged();
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setFieldErrors("codRapidoMittente",
								I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("mittente", "in entrata"));
					}
				}
			});
		}
	}

	// public String calcolaTipoSoggetto(String flgPersonaFisica, String codTipoSoggetto) {
	// String tipoSoggetto = null;
	// if(flgPersonaFisica==null) flgPersonaFisica = "";
	// if(codTipoSoggetto==null) codTipoSoggetto = "";
	// if (codTipoSoggetto.equalsIgnoreCase("PA")){
	// tipoSoggetto = "PA";
	// }
	// else if (codTipoSoggetto.equalsIgnoreCase("AOOE")){
	// tipoSoggetto = "PA";
	// }
	// else if (codTipoSoggetto.equalsIgnoreCase("AOOI")){
	// tipoSoggetto = "AOOI";
	// }
	// else if(!codTipoSoggetto.equalsIgnoreCase("UP") && !codTipoSoggetto.equalsIgnoreCase("UOI")) {
	// if (flgPersonaFisica.equalsIgnoreCase("0")){
	// tipoSoggetto = "PG";
	// }
	// else if (flgPersonaFisica.equalsIgnoreCase("1")){
	// tipoSoggetto = "PF";
	// }
	// }
	// return tipoSoggetto;
	// }

	public String calcolaTipoSoggetto(String tipo) {
		String tipoSoggetto = null;
		if ("#APA".equals(tipo)) {
			tipoSoggetto = "PA";
		} else if ("#IAMM".equals(tipo)) {
			tipoSoggetto = "AOOI";
		} else if ("#AF".equals(tipo)) {
			tipoSoggetto = "PF";
		} else if ("#AG".equals(tipo)) {
			tipoSoggetto = "PG";
		}
		return tipoSoggetto;
	}

	public void setFormValuesFromRecordRubrica(Record record) {
		SC.echo(record.getJsObj());
		// Pulisco i dati del soggetto
		clearIdSoggetto();
		clearFormSoggettoValues();
		mDynamicForm.clearErrors(true);
		// String flgPersFisica = record.getAttribute("flgPersFisica") != null ? record.getAttribute("flgPersFisica") : "";
		// String codTipoSoggetto = record.getAttribute("codTipoSoggInt");
		// if(codTipoSoggetto == null || "".equals(codTipoSoggetto)) {
		// if("1".equals(flgPersFisica)) {
		// if(record.getAttributeAsBoolean("flgUnitaDiPersonale")) {
		// codTipoSoggetto = "UP";
		// }
		// } else {
		// codTipoSoggetto = record.getAttribute("tipologia");
		// if(codTipoSoggetto != null && !"".equals(codTipoSoggetto)) {
		// String[] tokens = new StringTokenizer(codTipoSoggetto, ";").getTokens();
		// if(tokens.length == 2) {
		// codTipoSoggetto = tokens[1];
		// }
		// }
		// }
		// }
		//
		// String tipoMittente = calcolaTipoSoggetto(record.getAttribute("flgPersFisica"), codTipoSoggetto);
		String tipoMittente = calcolaTipoSoggetto(record.getAttribute("tipo"));
		if (tipoMittente != null) {
			mDynamicForm.setValue("tipoMittente", tipoMittente);
			mDynamicForm.setValue("codRapidoMittente", record.getAttribute("codiceRapido"));
			mDynamicForm.setValue("idMittente", record.getAttribute("idSoggetto"));
			mDynamicForm.setValue("idSoggetto", record.getAttribute("idSoggetto"));
			mDynamicForm.setValue("idUoSoggetto", record.getAttribute("idUo"));
			mDynamicForm.setValue("idUserSoggetto", record.getAttribute("idUtente"));
			mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttribute("idScrivania"));
			mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));
			mDynamicForm.clearValue("flgAssegnaAlMittente");
			((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
			if ("AOOI".equalsIgnoreCase(tipoMittente)) {
				mDynamicForm.setValue("aoomdgMittente", record.getAttribute("idSoggetto"));
				mDynamicForm.setValue("descrAoomdgMittente", record.getAttribute("denominazione"));
				manageLoadSelectAoomdgMittenteInEditRecord(mDynamicForm.getValuesAsRecord());				
			} else if (isPersonaGiuridica(tipoMittente)) {
				mDynamicForm.setValue("denominazioneMittente", record.getAttribute("denominazione"));
			} else if (isPersonaFisica(tipoMittente)) {
				mDynamicForm.setValue("cognomeMittente", record.getAttribute("cognome"));
				mDynamicForm.setValue("nomeMittente", record.getAttribute("nome"));
				mDynamicForm.setValue("codfiscaleMittente", record.getAttribute("codiceFiscale"));
			}
			setIndirizzoCompletoFromRecordRubrica(record);
			showHideIndirizzo();
			showHideFlgAssegnaAlMittente();
			((MittenteProtEntrataItem) getItem()).manageOnChanged();
			mDynamicForm.markForRedraw();
		} else {
			// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in entrata/ uscita /
			// interna>
			mDynamicForm.setFieldErrors("codRapidoMittente",
					I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("mittente", "in entrata"));
		}
	}

	@Override
	public void editRecord(Record record) {
		manageLoadSelectAoomdgMittenteInEditRecord(record);			
		showFlgAssegnaAlMittente = false;
		super.editRecord(record);
		showHideIndirizzo();
		showHideFlgAssegnaAlMittente();
		if(isChangedRecord(record)) {
			((MittenteProtEntrataItem) getItem()).manageOnChanged();
		}
	}
	
	public void manageLoadSelectAoomdgMittenteInEditRecord(Record record) {
		if ("AOOI".equalsIgnoreCase(record.getAttribute("tipoMittente"))) {
			if (record.getAttribute("aoomdgMittente") != null && !"".equals(record.getAttributeAsString("aoomdgMittente")) &&
				record.getAttribute("descrAoomdgMittente") != null && !"".equals(record.getAttributeAsString("descrAoomdgMittente"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("aoomdgMittente"), record.getAttribute("descrAoomdgMittente"));
				aoomdgMittenteItem.setValueMap(valueMap);
			}

			SelectGWTRestDataSource aoomdgMittenteDS = (SelectGWTRestDataSource) aoomdgMittenteItem.getOptionDataSource();
			if (record.getAttribute("aoomdgMittente") != null && !"".equals(record.getAttributeAsString("aoomdgMittente"))) {
				aoomdgMittenteDS.addParam("idAooMdg", record.getAttributeAsString("aoomdgMittente"));
				aoomdgMittenteDS.addParam("descrizioneAooMdg", record.getAttributeAsString("descrAoomdgMittente"));
			} else {
				aoomdgMittenteDS.addParam("idAooMdg", null);
				aoomdgMittenteDS.addParam("descrizioneAooMdg", null);
			}
			aoomdgMittenteItem.setOptionDataSource(aoomdgMittenteDS);
		}
	}

	public abstract class CercaSoggettoServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError(boolean trovatiSoggMultipliInRicerca);

		@Override
		public void execute(Record object) {
			mDynamicForm.clearErrors(true);
			// String tipoMittente = calcolaTipoSoggetto(object.getAttribute("tipoSoggetto"), object.getAttribute("codTipoSoggetto"));
			String tipoMittente = calcolaTipoSoggetto(object.getAttribute("tipologiaSoggetto"));
			if (tipoMittente != null) {
				mDynamicForm.setValue("tipoMittente", tipoMittente);
				// Pulisco i dati del soggetto
				mDynamicForm.setValue("idMittente", "");				
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");
				mDynamicForm.clearValue("flgAssegnaAlMittente");
				((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
				mDynamicForm.setValue("denominazioneMittente", "");
				mDynamicForm.setValue("cognomeMittente", "");
				mDynamicForm.setValue("nomeMittente", "");
				mDynamicForm.setValue("codfiscaleMittente", "");
				if (object.getAttribute("codRapidoSoggetto") != null && !object.getAttribute("codRapidoSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codRapidoMittente", object.getAttribute("codRapidoSoggetto"));
				}
				if (object.getAttribute("denominazioneSoggetto") != null && !object.getAttribute("denominazioneSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("denominazioneMittente", object.getAttribute("denominazioneSoggetto"));
				}
				if (object.getAttribute("cognomeSoggetto") != null && !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("cognomeMittente", object.getAttribute("cognomeSoggetto"));
				}
				if (object.getAttribute("nomeSoggetto") != null && !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("nomeMittente", object.getAttribute("nomeSoggetto"));
				}
				if (object.getAttribute("codfiscaleSoggetto") != null && !object.getAttribute("codfiscaleSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codfiscaleMittente", object.getAttribute("codfiscaleSoggetto"));
				}
				if (object.getAttribute("idSoggetto") != null && !object.getAttribute("idSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idMittente", object.getAttribute("idSoggetto"));
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
				if (object.getAttribute("flgSelXAssegnazione") != null && !object.getAttribute("flgSelXAssegnazione").equalsIgnoreCase("")) {
					mDynamicForm.setValue("flgSelXAssegnazione", object.getAttribute("flgSelXAssegnazione"));
				}				
				// Se il codice soggetto restituito e' AOOI (=AOO MDG) inizializzo la select
				if (mDynamicForm.getValueAsString("tipoMittente").equalsIgnoreCase("AOOI")) {
					cercaSoggettoAOOMDG();
				}
				if (showItemsIndirizzo()) {
					setIndirizzoAfterFindSoggettoService(object);
				}				
				showHideIndirizzo();
				showHideFlgAssegnaAlMittente();
				((MittenteProtEntrataItem) getItem()).manageOnChanged();
				mDynamicForm.markForRedraw();
			} else {
				// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in entrata/ uscita /
				// interna>
				executeOnError(object.getAttribute("trovatiSoggMultipliInRicerca") != null && object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca"));
			}
		}
	}
	
	public boolean isMittenteValorizzato() {
		String denominazione = denominazioneMittenteItem.isVisible() ? denominazioneMittenteItem.getValueAsString() : null;
		String cognome = cognomeMittenteItem.isVisible() ? cognomeMittenteItem.getValueAsString() : null;
		String nome = nomeMittenteItem.isVisible() ? nomeMittenteItem.getValueAsString() : null;
		return ((denominazione != null && !"".equals(denominazione)) || ((cognome != null && !"".equals(cognome)) && (nome != null && !"".equals(nome))));
	}

	public boolean isChangedAndValid() {
		return isChanged() && isMittenteValorizzato();
	}
	
	public LinkedHashMap<String, String> getTipoMittenteMap() {
		if (!allowOnlyPersonaFisica()) {
			LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
			if (AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST")) {
				String decodificaAOOI = AurigaLayout.getParametroDB("LABEL_AOOI_IN_MITT_DEST");
				if (decodificaAOOI != null && !"".equals(decodificaAOOI)) {
					styleMap.put("AOOI", decodificaAOOI);
				} else {
					styleMap.put("AOOI", I18NUtil.getMessages().protocollazione_select_listmap_AOOI_value());
				}
			}
			styleMap.put("PA", I18NUtil.getMessages().protocollazione_select_listmap_PA_value());
			styleMap.put("PF", I18NUtil.getMessages().protocollazione_select_listmap_PF_value());
			styleMap.put("PG", I18NUtil.getMessages().protocollazione_select_listmap_PG_value());
			return styleMap;
		}else {
			LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
			styleMap.put("PF", I18NUtil.getMessages().protocollazione_select_listmap_PF_value());
			return styleMap;
		}
	}
	
	public boolean isEmailMittenteItemObbligatorio() {
		return ((MittenteProtEntrataItem) getItem()).isEmailMittenteItemObbligatorio();
	}
	
	public boolean showItemsIndirizzo() {
		return ((MittenteProtEntrataItem) getItem()).getShowItemsIndirizzo();
	}
	
	public boolean showItemSelTipoMittente() {
		return ((MittenteProtEntrataItem) getItem()).getShowItemSelTipoMittente();
	}
	
	public boolean allowOnlyPersonaFisica() {
		return ((MittenteProtEntrataItem) getItem()).getAllowOnlyPersonaFisica();
	}
	
	@Override
	public boolean showOpenIndirizzo() {
		return ((MittenteProtEntrataItem) getItem()).showOpenIndirizzo();
	}
	
	public void resetDefaultValueFlgAssegnaAlMittente() {
		boolean fromLoadDett =  mDynamicForm.getValue("fromLoadDett") != null ? (Boolean) mDynamicForm.getValue("fromLoadDett") : false; 
		if(!fromLoadDett) {
			mDynamicForm.clearValue("flgAssegnaAlMittente");
			((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
		}
	}

	@Override
	public void redraw() {
		super.redraw();
		showHideIndirizzo();
		showHideFlgAssegnaAlMittente();
	}
	
}