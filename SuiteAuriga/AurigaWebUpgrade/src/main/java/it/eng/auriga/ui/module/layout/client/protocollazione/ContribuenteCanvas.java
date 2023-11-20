/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
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
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SalvaInRubricaPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ContribuenteCanvas extends IndirizzoCanvas {

	private ExtendedTextItem codRapidoItem;
	private ExtendedTextItem denominazioneItem;
	private ExtendedTextItem cognomeItem;
	private ExtendedTextItem nomeItem;
	private ExtendedTextItem codFiscaleItem;
	private HiddenItem idSoggettoItem;
	private HiddenItem idUserSoggettoItem;
	private HiddenItem idScrivaniaSoggettoItem;
	private ExtendedTextItem estremiDocRiconoscimentoItem;
	private CheckboxItem flgAncheMittenteItem;
	private TextItem emailContribuenteItem;
	private TextItem telContribuenteItem;	
	private ExtendedTextItem codiceACSItem;
	private SelectItem tipoSoggettoItem;

	public ContribuenteCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void buildMainForm() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idSoggettoItem = new HiddenItem("idSoggetto");
		idUserSoggettoItem = new HiddenItem("idUserSoggetto");
		idScrivaniaSoggettoItem = new HiddenItem("idScrivaniaSoggetto");

		// tipo
		tipoSoggettoItem = new SelectItem("tipoSoggetto");
		tipoSoggettoItem.setVisible(false);

		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		styleMap.put("PF", I18NUtil.getMessages().contribuente_combo_tipo_AF_value());

		if (isPersonaGiuridicaAbil()) {
			styleMap.put("PG", I18NUtil.getMessages().contribuente_combo_tipo_AG_value());
			tipoSoggettoItem.setVisible(true); // Mostro la combo solo se contiene piu' di 1 valore.
		}

		tipoSoggettoItem.setDefaultValue((String) "PF");

		tipoSoggettoItem.setAllowEmptyValue(false);
		tipoSoggettoItem.setShowTitle(false);
		tipoSoggettoItem.setValueMap(styleMap);
		tipoSoggettoItem.setWidth(150);
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
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				if (event.getValue() != null && !"".equals(event.getValue())) {
					Record lRecord = new Record();
					lRecord.setAttribute("codRapidoSoggetto", event.getValue());
					cercaSoggetto(lRecord, new CercaSoggettoServiceCallback() {

						@Override
						public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
							mDynamicForm.setFieldErrors("codRapido", I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("contribuente", "di istruttoria"));
						}
					});
				}
			}
		});
		codRapidoItem.setVisible(false);

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
				return toShowDenominazione(tipoSoggettoItem.getValueAsString());
			}
		}));
		denominazioneItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((ContribuenteItem) getItem()).manageOnChanged();
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
				((ContribuenteItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cognome
		cognomeItem = new ExtendedTextItem("cognome", I18NUtil.getMessages().protocollazione_detail_cognomeItem_title());
		cognomeItem.setWidth(150);
		cognomeItem.setRequired(true);
		cognomeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isPersonaFisica(tipoSoggettoItem.getValueAsString());
			}
		});

		cognomeItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((ContribuenteItem) getItem()).manageOnChanged();
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
				((ContribuenteItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// nome
		nomeItem = new ExtendedTextItem("nome", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeItem.setWidth(150);
		nomeItem.setRequired(true);
		nomeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isPersonaFisica(tipoSoggettoItem.getValueAsString());
			}
		});

		nomeItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((ContribuenteItem) getItem()).manageOnChanged();
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
				((ContribuenteItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cod.fiscale
		codFiscaleItem = new ExtendedTextItem("codFiscale", I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
		codFiscaleItem.setWidth(150);
		codFiscaleItem.setCharacterCasing(CharacterCasing.UPPER);
		codFiscaleItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals((String) value)) {
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
				((ContribuenteItem) getItem()).manageOnChanged();
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
				((ContribuenteItem) getItem()).setCercaInRubricaAfterChanged(true);
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
				return true;
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
				((ContribuenteItem) getItem()).setCercaInRubricaAfterChanged(false);		
				ContribuenteLookupRubrica lookupRubricaPopup = new ContribuenteLookupRubrica(null, tipoSoggettoItem.getValueAsString());
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
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				return tipoSoggetto == null || "".equals(tipoSoggetto) || isPersonaFisica(tipoSoggetto)
						|| (isPersonaGiuridica(tipoSoggetto) && !"AOOI".equalsIgnoreCase(tipoSoggetto));
			}
		});

		cercaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((ContribuenteItem) getItem()).setCercaInRubricaAfterChanged(false);			
				cercaInRubrica();				
			}
		});

		// bottone salva in rubrica
		ImgButtonItem salvaInRubricaButton = new ImgButtonItem("salvaInRubricaButton", "buttons/saveIn.png", I18NUtil.getMessages()
				.protocollazione_detail_salvaInRubricaButton_prompt());
		salvaInRubricaButton.setColSpan(1);
		salvaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoSoggetto = tipoSoggettoItem.getValueAsString();
				return Layout.isPrivilegioAttivo("GRS/S/UO;I")
						&& (tipoSoggetto == null || "".equals(tipoSoggetto) || ((isPersonaFisica(tipoSoggetto) || isPersonaGiuridica(tipoSoggetto))
								&& !"AOOI".equalsIgnoreCase(tipoSoggetto) && !"PA".equalsIgnoreCase(tipoSoggetto)));
			}
		});

		salvaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((ContribuenteItem) getItem()).setCercaInRubricaAfterChanged(false);		
				String tipo = tipoSoggettoItem.getValueAsString();
				String codRapido = codRapidoItem.getValueAsString();
				String denominazione = denominazioneItem.isVisible() ? denominazioneItem.getValueAsString() : null;
				String cognome = cognomeItem.isVisible() ? cognomeItem.getValueAsString() : null;
				String nome = nomeItem.isVisible() ? nomeItem.getValueAsString() : null;
				String codiceFiscale = codFiscaleItem.isVisible() ? codFiscaleItem.getValueAsString() : null;
				Record recordMittente = new Record();
				recordMittente.setAttribute("tipo", tipo);
				recordMittente.setAttribute("codRapido", codRapido);
				recordMittente.setAttribute("denominazione", denominazione);
				recordMittente.setAttribute("cognome", cognome);
				recordMittente.setAttribute("nome", nome);
				recordMittente.setAttribute("codiceFiscale", codiceFiscale);
				Record recordIndirizzo = getFormValuesAsRecord();
				SalvaInRubricaPopup salvaInRubricaPopup = new SalvaInRubricaPopup(null, recordMittente, recordIndirizzo) {

					@Override
					public void manageLookupBack(Record record) {
						setFormValuesFromRecordRubrica(record);
					}
				};
				salvaInRubricaPopup.show();
			}
		});

		estremiDocRiconoscimentoItem = new ExtendedTextItem("estremiDocRiconoscimento", "Doc. di riconoscimento");
		estremiDocRiconoscimentoItem.setWidth(150);
		estremiDocRiconoscimentoItem.setRequired(false);
		estremiDocRiconoscimentoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return false;
			}
		});
		estremiDocRiconoscimentoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((ContribuenteItem) getItem()).manageOnChanged();
			}
		});

		flgAncheMittenteItem = new CheckboxItem("flgAncheMittente", "anche mittente");
		flgAncheMittenteItem.setValue(false);
		flgAncheMittenteItem.setColSpan(1);
		flgAncheMittenteItem.setWidth("*");
		flgAncheMittenteItem.setShowTitle(true);
		flgAncheMittenteItem.setWrapTitle(false);
		flgAncheMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return false;
			}
		});

		flgAncheMittenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((ContribuenteItem) getItem()).setFlgAncheMittente(event.getValue() != null && (Boolean) event.getValue());
				((ContribuenteItem) getItem()).manageOnChanged();
			}
		});

		// codice ACS
		codiceACSItem = new ExtendedTextItem("codiceACS", I18NUtil.getMessages().protocollazione_detail_codiceACSItem_title());
		codiceACSItem.setShowTitle(true);
		codiceACSItem.setWidth(100);
		codiceACSItem.setKeyPressFilter("[0-9]");
		codiceACSItem.setLength(12);
		codiceACSItem.addChangedBlurHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				codiceACSItemOnchange(event.getValue());
			}
		});
		
		// email
		emailContribuenteItem = new TextItem("emailContribuente", I18NUtil.getMessages().protocollazione_detail_emailContribuenteItem_title());
		emailContribuenteItem.setAttribute("obbligatorio", false);
		emailContribuenteItem.setWidth(150);
		emailContribuenteItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(value == null || "".equals((String) value)) return true;
				RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
				return regExp.test((String) value);						
			}
		});

		// telefono
		telContribuenteItem = new TextItem("telContribuente", I18NUtil.getMessages().protocollazione_detail_telContribuenteItem_title());
		telContribuenteItem.setAttribute("obbligatorio", false);
		telContribuenteItem.setWidth(120);						

		mDynamicForm.setFields(idSoggettoItem, idUserSoggettoItem, idScrivaniaSoggettoItem, tipoSoggettoItem, codRapidoItem, lookupRubricaButton,
				denominazioneItem, cognomeItem, nomeItem, codFiscaleItem, cercaInRubricaButton, salvaInRubricaButton, estremiDocRiconoscimentoItem,
				flgAncheMittenteItem, codiceACSItem, emailContribuenteItem, telContribuenteItem);
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50",
				"100");
	}

	public class ContribuenteLookupRubrica extends LookupSoggettiPopup {

		public ContribuenteLookupRubrica(Record record, String tipoSoggetto) {
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
			return isPersonaGiuridicaAbil() ? new String[] { "PF", "PG" } : new String[] { "PF" };
		}
	}

	protected void clearFormSoggettoValues() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapido", "");
		lRecord.setAttribute("cognome", "");
		lRecord.setAttribute("nome", "");
		lRecord.setAttribute("codFiscale", "");
		mDynamicForm.setValues(lRecord.toMap());
		((ContribuenteItem) getItem()).manageOnChanged();
	}

	protected void clearIdSoggetto() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapido", "");
		lRecord.setAttribute("idSoggetto", "");
		lRecord.setAttribute("idUserSoggetto", "");
		lRecord.setAttribute("idScrivaniaSoggetto", "");
		mDynamicForm.setValues(lRecord.toMap());
	}

	protected boolean toShowDenominazione(String tipoSoggetto) {
		return tipoSoggetto == null || "".equals(tipoSoggetto) || (isPersonaGiuridica(tipoSoggetto) && !"AOOI".equalsIgnoreCase(tipoSoggetto));
	}

	protected boolean isPersonaGiuridica(String tipoSoggetto) {
		return ((ContribuenteItem) getItem()).isPersonaGiuridica(tipoSoggetto);
	}

	protected boolean isPersonaFisica(String tipoSoggetto) {
		return ((ContribuenteItem) getItem()).isPersonaFisica(tipoSoggetto);
	}

	protected boolean isPersonaGiuridicaAbil() {
		return ((ContribuenteItem) getItem()).isPersonaGiuridicaAbil();
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
					if(((ContribuenteItem) getItem()).isCercaInRubricaAfterChanged()) {
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
					ContribuenteLookupRubrica lookupRubricaPopup = new ContribuenteLookupRubrica(record, tipoSoggettoItem.getValueAsString());
					lookupRubricaPopup.show();
				}
			}
		});
	}

	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		lGwtRestService.addParam("tipiAmmessi", isPersonaGiuridicaAbil() ? "PF,PG" : "PF");
		lGwtRestService.call(lRecord, callback);
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
			String idSoggetto = record.getAttribute("idSoggetto");
			mDynamicForm.setValue("idSoggetto", idSoggetto);
			mDynamicForm.setValue("idUserSoggetto", record.getAttribute("idUtente"));
			mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttribute("idScrivania"));
			if (isPersonaGiuridica(tipoSoggetto)) {
				mDynamicForm.setValue("denominazione", record.getAttribute("denominazione"));
			} else if (isPersonaFisica(tipoSoggetto)) {
				mDynamicForm.setValue("cognome", record.getAttribute("cognome"));
				mDynamicForm.setValue("nome", record.getAttribute("nome"));
				mDynamicForm.setValue("codFiscale", record.getAttribute("codiceFiscale"));
			}

			setIndirizzoCompletoFromRecordRubrica(record);
			mDynamicForm.markForRedraw();
			((ContribuenteItem) getItem()).manageOnChanged();
		} else {
			// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un contribuente di una registrazione <di istruttoria>
			mDynamicForm.setFieldErrors("codRapido", I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("contribuente", "di istruttoria"));
		}
	}

	public abstract class CercaSoggettoServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError(boolean trovatiSoggMultipliInRicerca);

		@Override
		public void execute(Record object) {
			mDynamicForm.clearErrors(true);
			String tipoSoggetto = calcolaTipoSoggetto(object.getAttribute("tipologiaSoggetto"));
			if (isPersonaFisica(tipoSoggetto)) {
				// Pulisco i dati del soggetto
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("cognome", "");
				mDynamicForm.setValue("nome", "");
				mDynamicForm.setValue("codFiscale", "");
				if (object.getAttribute("codRapidoSoggetto") != null && !object.getAttribute("codRapidoSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codRapido", object.getAttribute("codRapidoSoggetto"));
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
				if (object.getAttribute("idUserSoggetto") != null && !object.getAttribute("idUserSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idUserSoggetto", object.getAttribute("idUserSoggetto"));
				}
				if (object.getAttribute("idScrivaniaSoggetto") != null && !object.getAttribute("idScrivaniaSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idScrivaniaSoggetto", object.getAttribute("idScrivaniaSoggetto"));
				}
				if (showItemsIndirizzo()) {
					setIndirizzoAfterFindSoggettoService(object);
				}				
				((ContribuenteItem) getItem()).manageOnChanged();
			} else {
				// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un contribuente di una registrazione <di istruttoria>
				executeOnError(object.getAttribute("trovatiSoggMultipliInRicerca") != null && object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca"));
			}
		}
	}

	public boolean isChangedAndValid() {
		String cognome = cognomeItem.isVisible() ? cognomeItem.getValueAsString() : null;
		String nome = nomeItem.isVisible() ? nomeItem.getValueAsString() : null;
		return isChanged() && (((cognome != null && !"".equals(cognome)) && (nome != null && !"".equals(nome))));
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
		}
		return tipoSoggetto;
	}

	public boolean showItemsIndirizzo() {
		return true;
	}

	@Override
	public boolean showOpenIndirizzo() {
		return true;
	}
	

	protected void codiceACSItemOnchange(Object value) {
//		System.out.println("inizio codiceACSItemOnchange("+value+")");
		if (isPersonaGiuridica(tipoSoggettoItem.getValueAsString())) {
			return;
		}
		if (value == null) {
			Record record = new Record();
			record.setAttribute("flagEmpty", true);
//			settaFormItemsDaRecord(record);
			return;
		}
		GWTRestService<Record, Record> gwtRestService = new GWTRestService<Record, Record>("FindDatiPrincipaliContribuenteDataSource");
		Record recordIn = new Record();
		recordIn.setAttribute("codiceACSOR", value);
//		System.out.println("CHIAMATA AL SERVER: "+value);
		gwtRestService.call(recordIn, new ServiceCallback<Record>() {
			@Override
			public void execute(Record recordOut) {
				String codiceACSOR = recordOut.getAttribute("codiceACSOR");
//				System.out.println("codiceACSOR restituito: "+codiceACSOR);
				if (!isBlank(codiceACSOR)) {
				    settaFormItemsDaRecord(recordOut);
				}
			}//execute
		});
//		System.out.println("fine codiceACSItemOnchange("+value+")");
	}//codiceACSItemOnchange


	private void settaFormItemsDaRecord(Record record) {
//		System.out.println("\ninizio settaFormItemsDaRecord()");
		final String attrCognome = "cognome";
		final String attrNome= "nome";
		final String attrCf = "cf";
		final String attrPiva = "piva";
		final String attrCodiceToponimo = "codiceToponimo";
		final String attrDescrizioneToponimo = "descrizioneToponimo";
		final String attrFrazione = "frazione";
		final String attrCivico = "civico";
		final String attrBarrato = "barrato";
		final String attrCap = "cap";
		final String attrCodiceIstatComune = "codiceIstatComune";
		final String attrDescrizioneComune= "descrizioneComune";
		final String attrCodiceIstatStato = "codiceIstatStato";
		final String attrDescrizioneStato = "descrizioneStato";
		final String attrTipoToponimo = "tipoToponimo";
		
		String cognome = null,nome = null,cf = null,piva = null,codiceToponimo = null,descrizioneToponimo = null,
			   frazione = null,civico = null,barrato = null,cap = null,codiceIstatComune = null,
		       descrizioneComune = null,codiceIstatStato = null,descrizioneStato = null,tipoToponimo = null;
		
		boolean flagFuoriComune = false; //è il valore di default
		
		String flagEmpty = record.getAttribute("flagEmpty");
		
		if (!Boolean.valueOf(flagEmpty)) {
//			System.out.println("leggo valori");
			cognome = emptyToNull(record.getAttribute(attrCognome)); 
			nome = emptyToNull(record.getAttribute(attrNome));
			cf = emptyToNull(record.getAttribute(attrCf));
			piva = emptyToNull(record.getAttribute(attrPiva));
			tipoToponimo = emptyToNull(record.getAttribute(attrTipoToponimo));
			codiceToponimo = emptyToNull(record.getAttribute(attrCodiceToponimo));
			descrizioneToponimo = emptyToNull(record.getAttribute(attrDescrizioneToponimo));
			frazione = emptyToNull(record.getAttribute(attrFrazione)); //"LAMBRATE"
			civico = emptyToNull(record.getAttribute(attrCivico));
			barrato = emptyToNull(record.getAttribute(attrBarrato));
			cap = emptyToNull(record.getAttribute(attrCap)); //"20134"
			codiceIstatComune = emptyToNull(record.getAttribute(attrCodiceIstatComune));
			descrizioneComune = emptyToNull(record.getAttribute(attrDescrizioneComune));
			codiceIstatStato = emptyToNull(record.getAttribute(attrCodiceIstatStato));
			descrizioneStato = emptyToNull(record.getAttribute(attrDescrizioneStato));
//			flagFuoriComune = true;
			
		} else {
			System.out.println("cancello valori");
		}
		
//		System.out.println("01 - "+attrCognome + ": " + cognome);
//		System.out.println("02 - "+attrNome + ": " + nome);
//		System.out.println("03 - "+attrCf + ": " + cf);
//		System.out.println("04 - "+attrPiva + ": " + piva);
//		System.out.println("05 - "+attrCodiceToponimo + ": " + codiceToponimo);
//		System.out.println("06 - "+attrDescrizioneToponimo + ": " + descrizioneToponimo);
//		System.out.println("07 - "+attrFrazione + ": " + frazione);
//		System.out.println("08 - "+attrCivico + ": " + civico);
//		System.out.println("09 - "+attrBarrato + ": " + barrato);
//		System.out.println("10 - "+attrCap + ": " + cap);
//		System.out.println("11 - "+attrCodiceIstatComune + ": " + codiceIstatComune);
//		System.out.println("12 - "+attrDescrizioneComune + ": " + descrizioneComune);
//		System.out.println("13 - "+attrCodiceIstatStato + ": " + codiceIstatStato);
//		System.out.println("14 - "+attrDescrizioneStato + ": " + descrizioneStato);
//		System.out.println("15 - "+attrTipoToponimo + ": " + tipoToponimo);
//		System.out.println("flagFuoriComune" + ": " + flagFuoriComune);

		//============================ Contribuente
		cognomeItem.setValue(cognome);
		nomeItem.setValue(nome);
		codFiscaleItem.setValue(cf);
		//============================= Indirizzo
		/*hidden*/nomeStatoItem.setValue(descrizioneStato);
		statoItem.setValue(codiceIstatStato);
		//---------------------------------------------
		flgFuoriComuneItem.setValue(flagFuoriComune);
		//----------------------------------------

		civicoItem.setValue(civico);
		appendiciItem.setValue(barrato);
		
		if (flagFuoriComune) {
			tipoToponimoItem.setValue(tipoToponimo);
			/*hidden*/codToponimoItem.setValue(codiceToponimo);
			toponimoItem.setValue(descrizioneToponimo);
			//---------------------------------------------------------
			if ( !isBlank(codiceIstatComune) ) {
				GWTRestDataSource comuneDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
				comuneDS.extraparam.put(comuneItem.getValueField(), codiceIstatComune);
				comuneDS.extraparam.put("nomeComune", descrizioneComune);
				
				comuneItem.setValue(codiceIstatComune);
				/*hidden*/nomeComuneItem.setValue(descrizioneComune);
		    } else {
		    	comuneItem.setValue((String)null);
		    	/*hidden*/nomeComuneItem.setValue((String)null);
		    }
	    } else {
	    	/*hidden*/codToponimoItem.setValue(codiceToponimo);
			indirizzoItem.setValue(descrizioneToponimo);
	    }
		//--------------------------------------------
		frazioneItem.setValue(frazione);
		if ( !isBlank(frazione) ) {
			Record recordFrazione = new Record();
			recordFrazione.setAttribute(frazioneItem.getValueField(), frazione);
			frazioneItem.onOptionClick(recordFrazione); 
		} else {
		   capItem.setValue(cap);
		}
//			    zonaItem.setValue(value);
//			    complementoIndirizzoItem.setValue(value);
		mIndirizzoDynamicForm1.redraw();
		mIndirizzoDynamicForm2.redraw();
//		System.out.println("fine settaFormItemsDaRecord()");
	}
	
	private String emptyToNull(String value) {
		if (value != null && value.trim().length()==0) {
			return null;
		}
		return value;
	}

}
