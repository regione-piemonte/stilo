/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
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
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

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
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class EsibenteCanvas extends IndirizzoCanvas {

	private ExtendedTextItem codRapidoItem;
	private ExtendedTextItem cognomeItem;
	private ExtendedTextItem nomeItem;
	private ExtendedTextItem codFiscaleItem;
	private TextItem emailItem;
	private ImgButtonItem salvaInRubricaButton;
	private ImgButtonItem visualizzaInRubricaButton;
	private HiddenItem idSoggettoItem;
	private HiddenItem idUserSoggettoItem;
	private HiddenItem idScrivaniaSoggettoItem;
	private SelectItem tipoDocRiconoscimentoItem;
	private ExtendedTextItem estremiDocRiconoscimentoItem;
	private CheckboxItem flgAncheMittenteItem;

	public EsibenteCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void buildMainForm() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idSoggettoItem = new HiddenItem("idSoggetto");
		idUserSoggettoItem = new HiddenItem("idUserSoggetto");
		idScrivaniaSoggettoItem = new HiddenItem("idScrivaniaSoggetto");

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
							// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un esibente di una registrazione in entrata/uscita/interna
							mDynamicForm.setFieldErrors("codRapido",
									I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("esibente", "in entrata"));
						}
					});
				}
			}
		});

		// cognome
		cognomeItem = new ExtendedTextItem("cognome", I18NUtil.getMessages().protocollazione_detail_cognomeItem_title());
		cognomeItem.setWidth(125);
		cognomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((EsibentiItem) getItem()).validazioneItemAbilitata() && (((EsibentiItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()));
			}
		}));
		cognomeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				 if(((EsibentiItem) getItem()).validazioneItemAbilitata() && (((EsibentiItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()))) {
					 cognomeItem.setAttribute("obbligatorio", true);
					 cognomeItem.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().protocollazione_detail_cognomeItem_title()));
					 cognomeItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);						
				 } else {
					 cognomeItem.setAttribute("obbligatorio", false);
					 cognomeItem.setTitle(I18NUtil.getMessages().protocollazione_detail_cognomeItem_title());
					 cognomeItem.setTitleStyle(it.eng.utility.Styles.formTitle);
				 }
				return true;
			}			
		});
		cognomeItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((EsibentiItem) getItem()).manageOnChanged();
				if(cognomeItem.getValue() != null && !"".equals(cognomeItem.getValue()) &&
				   nomeItem.getValue() != null && !"".equals(nomeItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeItem.getValue());									
					lRecord.setAttribute("codTipoSoggetto", "PF");
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		cognomeItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((EsibentiItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// nome
		nomeItem = new ExtendedTextItem("nome", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeItem.setWidth(125);
		nomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((EsibentiItem) getItem()).validazioneItemAbilitata() && (((EsibentiItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()));
			}
		}));
		nomeItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				 if(((EsibentiItem) getItem()).validazioneItemAbilitata() && (((EsibentiItem) getItem()).isRequiredDenominazione(mDynamicForm.hasValue()))) {
					 nomeItem.setAttribute("obbligatorio", true);
					 nomeItem.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().protocollazione_detail_nomeItem_title()));
					 nomeItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				 } else {
					 nomeItem.setAttribute("obbligatorio", false);
					 nomeItem.setTitle(I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
					 nomeItem.setTitleStyle(it.eng.utility.Styles.formTitle);
				 }
				return true;
			}
			
		});
		nomeItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((EsibentiItem) getItem()).manageOnChanged();
				if(cognomeItem.getValue() != null && !"".equals(cognomeItem.getValue()) &&
				   nomeItem.getValue() != null && !"".equals(nomeItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeItem.getValue());									
					lRecord.setAttribute("codTipoSoggetto", "PF");
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		nomeItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((EsibentiItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cod.fiscale
		codFiscaleItem = new ExtendedTextItem("codFiscale", I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
		codFiscaleItem.setWidth(120);
		codFiscaleItem.setLength(16);
		codFiscaleItem.setCharacterCasing(CharacterCasing.UPPER);
		codFiscaleItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals(value) || AurigaLayout.getParametroDBAsBoolean("DISATTIVA_CTRL_CF_PIVA_EMDI")) {
					return true;
				}
				RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
				return regExp.test((String) value);
			}
		});
		codFiscaleItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((EsibentiItem) getItem()).manageOnChanged();
				if(codFiscaleItem.getValue() != null && !"".equals(codFiscaleItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("codfiscaleSoggetto", codFiscaleItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", "PF");
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		codFiscaleItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((EsibentiItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// bottone seleziona da rubrica
		ImgButtonItem lookupRubricaButton = new ImgButtonItem("lookupRubricaButton", "lookup/rubrica.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupRubricaButton_prompt());
		lookupRubricaButton.setColSpan(1);
		lookupRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((EsibentiItem) getItem()).setCercaInRubricaAfterChanged(false);				
				EsibenteLookupRubrica lookupRubricaPopup = new EsibenteLookupRubrica(null);
				lookupRubricaPopup.show();
			}
		});

		// bottone cerca da rubrica
		ImgButtonItem cercaInRubricaButton = new ImgButtonItem("cercaInRubricaButton", "lookup/rubricasearch.png", I18NUtil.getMessages()
				.protocollazione_detail_cercaInRubricaButton_prompt());
		cercaInRubricaButton.setColSpan(1);
		cercaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((EsibentiItem) getItem()).setCercaInRubricaAfterChanged(false);	
				cercaInRubrica();				
			}
		});

		// bottone salva in rubrica
		salvaInRubricaButton = new ImgButtonItem("salvaInRubricaEsibente", "buttons/saveIn.png", I18NUtil.getMessages()
				.protocollazione_detail_salvaInRubricaButton_prompt());
		salvaInRubricaButton.setColSpan(1);
		salvaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return Layout.isPrivilegioAttivo("GRS/S/UO;I") && !(idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue()));
			}
		});
		salvaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((EsibentiItem) getItem()).setCercaInRubricaAfterChanged(false);			
				String codRapido = codRapidoItem.getValueAsString();
				String cognome = cognomeItem.isVisible() ? cognomeItem.getValueAsString() : null;
				String nome = nomeItem.isVisible() ? nomeItem.getValueAsString() : null;
				String codiceFiscale = codFiscaleItem.isVisible() ? codFiscaleItem.getValueAsString() : null;
				Record recordEsibente = new Record();
				recordEsibente.setAttribute("tipo", "PF");
				recordEsibente.setAttribute("codRapido", codRapido);
				recordEsibente.setAttribute("denominazione", "");
				recordEsibente.setAttribute("cognome", cognome);
				recordEsibente.setAttribute("nome", nome);
				recordEsibente.setAttribute("codiceFiscale", codiceFiscale);
				Record recordIndirizzo = getFormValuesAsRecord();
				SalvaInRubricaPopup salvaInRubricaPopup = new SalvaInRubricaPopup("SEL_SOGG_EST", recordEsibente, recordIndirizzo) {

					@Override
					public void manageLookupBack(Record record) {
						setFormValuesFromRecordRubrica(record);
					}
				};
				salvaInRubricaPopup.show();
			}
		});
		
		//bottone visualizza nominativo in rubrica
		visualizzaInRubricaButton = new ImgButtonItem("visualizzaInRubricaEsibente", "buttons/detail.png", I18NUtil.getMessages()
				.protocollazione_detail_visualizzaInRubricaButton_prompt());
		visualizzaInRubricaButton.setAlwaysEnabled(true);
		visualizzaInRubricaButton.setColSpan(1);
		visualizzaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue());
			}
		});

		visualizzaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				((EsibentiItem) getItem()).setCercaInRubricaAfterChanged(false);			
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
		
		emailItem = new TextItem("email", "e-mail");
		//emailItem.setAttribute("obbligatorio", Boolean.toString(isEmailItemObbligatorio()));
		emailItem.setAttribute("obbligatorio", (isEmailItemObbligatorio()));
		emailItem.setWidth(240);
		emailItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isEmailItemToShow();
			}
		});
		emailItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(isEmailItemToShow()) {
					if(value == null || "".equals((String) value)) return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					return regExp.test((String) value);		
				}
				return true;
			}
		});
		
		final GWTRestDataSource tipoDocRiconoscimentoDS = new GWTRestDataSource("TipoDocRiconoscimentoDataSource", "key", FieldType.TEXT);
		
		tipoDocRiconoscimentoItem = new SelectItem("tipoDocRiconoscimento", "Doc. di riconoscimento");
		tipoDocRiconoscimentoItem.setValueField("key");
		tipoDocRiconoscimentoItem.setDisplayField("value");		
		tipoDocRiconoscimentoItem.setWidth(125);
		tipoDocRiconoscimentoItem.setAllowEmptyValue(false);
		tipoDocRiconoscimentoItem.setOptionDataSource(tipoDocRiconoscimentoDS);
		tipoDocRiconoscimentoItem.setAutoFetchData(false);
		tipoDocRiconoscimentoItem.setFetchMissingValues(true);
		tipoDocRiconoscimentoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				((EsibentiItem) getItem()).manageOnChanged();
			}
		});
		
		tipoDocRiconoscimentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isTipoDocRiconoscimentoToShow();
			}
		});

		estremiDocRiconoscimentoItem = new ExtendedTextItem("estremiDocRiconoscimento");
		estremiDocRiconoscimentoItem.setShowTitle(false);
		estremiDocRiconoscimentoItem.setWidth(125);
		estremiDocRiconoscimentoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((EsibentiItem) getItem()).manageOnChanged();
			}
		});
		
		estremiDocRiconoscimentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isEstremiDocRiconoscimentoToShow();
			}
		});

		flgAncheMittenteItem = new CheckboxItem("flgAncheMittente", "anche mittente");
		flgAncheMittenteItem.setStartRow(true);
		flgAncheMittenteItem.setValue(false);
		flgAncheMittenteItem.setColSpan(1);
		flgAncheMittenteItem.setWidth("*");
		// flgAncheMittenteItem.setLabelAsTitle(true);
		flgAncheMittenteItem.setShowTitle(true);
		flgAncheMittenteItem.setWrapTitle(false);
		flgAncheMittenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((EsibentiItem) getItem()).setFlgAncheMittente(event.getValue() != null && (Boolean) event.getValue());
				((EsibentiItem) getItem()).manageOnChanged();
			}
		});
		
		flgAncheMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((EsibentiItem)getItem()).getShowAncheMittente();
			}
		});

		mDynamicForm.setFields(idSoggettoItem, idUserSoggettoItem, idScrivaniaSoggettoItem, codRapidoItem, lookupRubricaButton, cognomeItem, nomeItem,
				codFiscaleItem, cercaInRubricaButton, salvaInRubricaButton, visualizzaInRubricaButton, emailItem, tipoDocRiconoscimentoItem, estremiDocRiconoscimentoItem, flgAncheMittenteItem);

		mDynamicForm.setNumCols(25);
		mDynamicForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");
	}
	
	@Override
	public void editRecord(Record record) {
		
		// Inizializzo la combo
		if (record.getAttribute("tipoDocRiconoscimento") != null && !"".equals(record.getAttributeAsString("tipoDocRiconoscimento"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("tipoDocRiconoscimento"), record.getAttribute("tipoDocRiconoscimento"));
			tipoDocRiconoscimentoItem.setValueMap(valueMap);
		}
		
		((EsibentiItem) getItem()).setFlgAncheMittente(record.getAttributeAsBoolean("flgAncheMittente"));
		
		GWTRestDataSource tipoDocRiconoscimentoDS = (GWTRestDataSource) tipoDocRiconoscimentoItem.getOptionDataSource();
		if (record.getAttribute("tipoDocRiconoscimento") != null && !"".equals(record.getAttributeAsString("tipoDocRiconoscimento"))) {
			tipoDocRiconoscimentoDS.addParam("tipoDocRiconoscimento", record.getAttributeAsString("tipoDocRiconoscimento"));
		} else {
			tipoDocRiconoscimentoDS.addParam("tipoDocRiconoscimento", null);
		}
		tipoDocRiconoscimentoItem.setOptionDataSource(tipoDocRiconoscimentoDS);
		
		super.editRecord(record);
	}
	
	public class EsibenteLookupRubrica extends LookupSoggettiPopup {

		public EsibenteLookupRubrica(Record record) {
			super(record, "PF", true);
		}

		@Override
		public String getFinalita() {
			return "SEL_PF";
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
			return new String[] { "PF" };
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
		((EsibentiItem) getItem()).manageOnChanged();
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
	
	protected void cercaInRubrica() {
		final Record lRecord = new Record();
		lRecord.setAttribute("cognomeSoggetto", cognomeItem.isVisible() ? cognomeItem.getValue() : null);
		lRecord.setAttribute("nomeSoggetto", nomeItem.isVisible() ? nomeItem.getValue() : null);
		lRecord.setAttribute("codfiscaleSoggetto", codFiscaleItem.isVisible() ? codFiscaleItem.getValue() : null);
		lRecord.setAttribute("codTipoSoggetto", "PF");
		cercaInRubrica(lRecord, true);		
	}
	
	protected void cercaInRubricaAfterChangedField(final Record record) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_CERCA_IN_RUBRICA")) {
			Timer t1 = new Timer() {
				public void run() {
					if(((EsibentiItem) getItem()).isCercaInRubricaAfterChanged()) {
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
					EsibenteLookupRubrica lookupRubricaPopup = new EsibenteLookupRubrica(record);
					lookupRubricaPopup.show();
				}
			}
		});
	}

	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		lGwtRestService.addParam("tipiAmmessi", "PF");		
		lGwtRestService.call(lRecord, callback);
	}

	public void setFormValuesFromRecordRubrica(Record record) {
		SC.echo(record.getJsObj());
		// Pulisco i dati del soggetto
		clearIdSoggetto();
		clearFormSoggettoValues();
		mDynamicForm.clearErrors(true);

		String tipoSoggetto = calcolaTipoSoggetto(record.getAttribute("tipo"));

		if (tipoSoggetto != null && "PF".equals(tipoSoggetto)) {
			mDynamicForm.setValue("codRapido", record.getAttribute("codiceRapido"));
			String idSoggetto = record.getAttribute("idSoggetto");
			mDynamicForm.setValue("idSoggetto", idSoggetto);
			mDynamicForm.setValue("idUserSoggetto", record.getAttribute("idUtente"));
			mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttribute("idScrivania"));
			mDynamicForm.setValue("cognome", record.getAttribute("cognome"));
			mDynamicForm.setValue("nome", record.getAttribute("nome"));
			mDynamicForm.setValue("codFiscale", record.getAttribute("codiceFiscale"));
			setIndirizzoCompletoFromRecordRubrica(record);
			mDynamicForm.markForRedraw();
			((EsibentiItem) getItem()).manageOnChanged();
		} else {
			// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un esibente di una registrazione <in entrata/ uscita / interna>
			mDynamicForm.setFieldErrors("codRapido",
					I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("esibente", "in entrata"));
		}
	}

	public abstract class CercaSoggettoServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError(boolean trovatiSoggMultipliInRicerca);

		@Override
		public void execute(Record object) {
			mDynamicForm.clearErrors(true);

			String tipoSoggetto = calcolaTipoSoggetto(object.getAttribute("tipologiaSoggetto"));

			if (tipoSoggetto != null && "PF".equals(tipoSoggetto)) {
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
				((EsibentiItem) getItem()).manageOnChanged();
				mDynamicForm.markForRedraw();
			} else {
				// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un esibente di una registrazione <in entrata/ uscita / interna>
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
		/*
		else if("LD".equals(tipo)){ tipoSoggetto = "LD"; }
		*/
		return tipoSoggetto;
	}

	public boolean showItemsIndirizzo() {
		return ((EsibentiItem) getItem()).showItemsIndirizzo();
	}

	@Override
	public boolean showOpenIndirizzo() {
		return false;
	}
	
	public boolean isTipoDocRiconoscimentoToShow() {
		return ((EsibentiItem) getItem()).isTipoDocRiconoscimentoToShow();
	}
	
	public boolean isEstremiDocRiconoscimentoToShow() {
		return ((EsibentiItem) getItem()).isEstremiDocRiconoscimentoToShow();
	}
	
	public boolean isEmailItemToShow() {
		return ((EsibentiItem) getItem()).isEmailItemToShow();
	}
	
	public boolean isEmailItemObbligatorio() {
		return ((EsibentiItem) getItem()).isEmailItemObbligatorio();
	}

}
