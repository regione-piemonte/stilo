/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DatiContabiliAVBDetail extends CustomDetail {
	
	protected ListaDatiContabiliAVBItem gridItem;
	protected DynamicForm mDynamicForm;
	
	protected SelectItem missione;	
	protected HiddenItem desMissione;
	protected SelectItem programma;
	protected HiddenItem desProgramma;
	
	protected ExtendedTextItem filtroLivelliPdC;
	protected SelectItem livelliPdC;
	protected HiddenItem desLivelliPdC;
	
	protected TextItem annoBilancio;	
	protected TextItem capitolo;
	protected TextItem centroDiCosto;	
	protected TextItem nroImpAcc;	
	protected TextItem subImpAcc;			
	protected TextItem annoGestResidui;
	protected TextItem nroLiquidazione;
	protected TextItem annoLiquidazione;
	
	protected ExtendedNumericItem importo;	
	
	protected SpacerItem spacerPrenotazione;
	protected CheckboxItem isPrenotazione;
	protected SpacerItem spacerSoggettiVari;
	protected CheckboxItem isSoggettiVari;
	protected TextItem nominativoSogg;
	protected TextItem codFisPIVASogg; 
	protected TextItem sedeSogg;	
	
	protected FormItem codiceCIG;
	protected TextItem codiceCUP;
	
	protected TextItem iban;
	protected TextItem nroMandato;
	protected DateItem dataMandato;
	
//	protected HashMap<String, String> codCentroCostoValueMap;
	
	protected GWTRestDataSource missioneDS = new GWTRestDataSource("MissioneDatiContabiliDataSource", "key", FieldType.TEXT);
	protected GWTRestDataSource programmaDS = new GWTRestDataSource("ProgrammaDatiContabiliDataSource", "key", FieldType.TEXT);
	
	public DatiContabiliAVBDetail(String nomeEntita, ListaDatiContabiliAVBItem gridItem/*, final HashMap<String, String> codCentroCostoValueMap*/) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
//		this.codCentroCostoValueMap = codCentroCostoValueMap;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);		
		
		buildFormItems();
		
		List<FormItem> items = getFormItems();	
		mDynamicForm.setFields(items.toArray(new FormItem[items.size()]));
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
	
	protected void buildFormItems() {
		
		missione = new SelectItem("missione", "Missione") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desMissione", record.getAttribute("value"));				
				mDynamicForm.markForRedraw();			
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.clearValue("missione");
					mDynamicForm.clearValue("desMissione");
					mDynamicForm.markForRedraw();
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.clearValue("missione");
				mDynamicForm.clearValue("desMissione");
				mDynamicForm.markForRedraw();
			};
		};
		missione.setStartRow(true);
		missione.setWidth(300);
		missione.setColSpan(12);	
		missione.setAutoFetchData(false);
		missione.setAlwaysFetchMissingValues(true);
		missione.setFetchMissingValues(true);
		missione.setFetchDelay(500);
		missione.setOptionDataSource(missioneDS);
		missione.setValueField("key");
		missione.setDisplayField("value");		
		missione.setAllowEmptyValue(true);							
		missione.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isImpegno();
			}
		});	
		missione.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				reloadProgrammaItem();				
			}
		});		
		
		desMissione = new HiddenItem("desMissione");
		
		programma = new SelectItem("programma", "Programma") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desProgramma", record.getAttribute("value"));				
				mDynamicForm.markForRedraw();			
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.clearValue("programma");
					mDynamicForm.clearValue("desProgramma");
					mDynamicForm.markForRedraw();
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.clearValue("programma");
				mDynamicForm.clearValue("desProgramma");
				mDynamicForm.markForRedraw();
			};
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid programmaPickListProperties = super.builPickListProperties();	
				programmaPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						// il programma dipende dalla missione
						GWTRestDataSource programmaDS = (GWTRestDataSource) programma.getOptionDataSource();		
						programmaDS.addParam("missione", missione.getValueAsString());									
						programma.setOptionDataSource(programmaDS);
						programma.invalidateDisplayValueCache();
					}
				});
				return programmaPickListProperties;
			}
		};
		programma.setStartRow(true);
		programma.setWidth(300);
		programma.setColSpan(12);	
		programma.setAutoFetchData(false);
		programma.setAlwaysFetchMissingValues(true);
		programma.setFetchMissingValues(true);
		programma.setFetchDelay(500);
		programma.setOptionDataSource(programmaDS);
		programma.setValueField("key");
		programma.setDisplayField("value");	
		programma.setAllowEmptyValue(true);							
		programma.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno();
			}
		});
		
		desProgramma = new HiddenItem("desProgramma");

		filtroLivelliPdC = new ExtendedTextItem("filtroLivelliPdC", "Filtro per PdC");
		filtroLivelliPdC.setStartRow(true);
		filtroLivelliPdC.setWidth(100);
		filtroLivelliPdC.setColSpan(1);
		filtroLivelliPdC.addChangedBlurHandler(new ChangedHandler() {
	
			@Override
			public void onChanged(final ChangedEvent event) {
				reloadLivelliPdCItem();	
			}
		});	
		filtroLivelliPdC.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno() || isAccertamento();
			}
		});
	
		buildLivelliPdCItem();
		
		desLivelliPdC = new HiddenItem("desLivelliPdC");
		
		annoBilancio = new AnnoItem("annoBilancio", "Anno bilancio");
		annoBilancio.setStartRow(true);
		annoBilancio.setWidth(100);
		annoBilancio.setColSpan(1);
		annoBilancio.setAttribute("obbligatorio", true);
		annoBilancio.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isImpegno() || isAccertamento();
			}
		}));
		annoBilancio.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno() || isAccertamento();
			}
		});
		
		capitolo = new TextItem("capitolo", "Capitolo");
		capitolo.setStartRow(true);
		capitolo.setWidth(100);
		capitolo.setColSpan(1);
		capitolo.setAttribute("obbligatorio", true);
		capitolo.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isImpegno() || isAccertamento();
			}
		}));
		capitolo.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno() || isAccertamento();
			}
		});
		
		centroDiCosto = new TextItem("centroDiCosto", "CdC");
		centroDiCosto.setStartRow(true);
		centroDiCosto.setWidth(300);
		centroDiCosto.setColSpan(12);	
		centroDiCosto.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno() || isAccertamento();
			}
		});
		
		nroImpAcc = new TextItem("nroImpAcc", "N°");
		nroImpAcc.setStartRow(true);
		nroImpAcc.setWidth(100);
		nroImpAcc.setColSpan(1);
		nroImpAcc.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isImpegno()) {
					nroImpAcc.setTitle("N° impegno");
				} else if(isAccertamento()) {		
					nroImpAcc.setTitle("N° accertamento");
				}
				return isImpegno() || isAccertamento();
			}
		});
				
		subImpAcc = new TextItem("subImpAcc", "Sub");
		subImpAcc.setWidth(100);	
		subImpAcc.setColSpan(1);
		subImpAcc.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isImpegno() || isAccertamento();
			}
		});
				
		annoGestResidui = new AnnoItem("annoGestResidui", "Anno gestione residui");
		annoGestResidui.setStartRow(true);
		annoGestResidui.setWidth(100);
		annoGestResidui.setColSpan(1);
		annoGestResidui.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno() || isAccertamento();
			}
		});
		
		nroLiquidazione = new TextItem("nroLiquidazione", "N° liquidazione");
		nroLiquidazione.setStartRow(true);
		nroLiquidazione.setWidth(100);
		nroLiquidazione.setColSpan(1);
		nroLiquidazione.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isLiquidazione();
			}
		});
				
		annoLiquidazione = new AnnoItem("annoLiquidazione", "Anno liquidazione");
		annoLiquidazione.setWidth(100);
		annoLiquidazione.setColSpan(1);
		annoLiquidazione.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isLiquidazione();
			}
		});
		
		importo = new ExtendedNumericItem("importo", "Importo (&euro;)");
		importo.setStartRow(true);
		importo.setKeyPressFilter("[0-9.,]");		
		importo.addChangedBlurHandler(new ChangedHandler() {
				
			@Override
			public void onChanged(ChangedEvent event) {
				String valueStr = (String) event.getValue();
				if(valueStr != null && !"".equals(valueStr)) {
					if(importo.validate()) {
						importo.setValue(NumberFormatUtility.getFormattedValue(valueStr));
					}
				}
			}
		});		
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(isImpegno() || isAccertamento()) {
					String pattern = "#,##0.00";
					double importo = 0;
					if(value != null && !"".equals((String) value)) {
						importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
					}
					return importo > 0;
				}
				return true;
			}
		};
		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		importo.setValidators(importoPrecisionValidator, importoMaggioreDiZeroValidator);		
		importo.setWidth(160);		
		importo.setColSpan(8);
		importo.setAttribute("obbligatorio", true);
//		importo.setRequired(true);
		importo.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String valueStr = importo.getValueAsString();
				if(valueStr != null && !"".equals(valueStr)) {
					if(importo.validate()) {
						importo.setValue(NumberFormatUtility.getFormattedValue(valueStr));
					}
				}
				return isImpegno() || isAccertamento();
			}
		});
		
		spacerPrenotazione = new SpacerItem();
		spacerPrenotazione.setStartRow(true);
		spacerPrenotazione.setColSpan(1);
		spacerPrenotazione.setWidth(30);
		spacerPrenotazione.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return isImpegno();
			}
		});
		
		isPrenotazione = new CheckboxItem("isPrenotazione", "prenotazione");
		isPrenotazione.setDefaultValue(false);
		isPrenotazione.setWidth(100);
		isPrenotazione.setColSpan(1);
		isPrenotazione.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isImpegno();
			}
		});
		isPrenotazione.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.redraw();
			}
		});
		
		spacerSoggettiVari = new SpacerItem();
		spacerSoggettiVari.setStartRow(true);
		spacerSoggettiVari.setColSpan(1);
		spacerSoggettiVari.setWidth(30);
		spacerSoggettiVari.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return isImpegno();
			}
		});
		
		isSoggettiVari = new CheckboxItem("isSoggettiVari", "soggetti vari");
		isSoggettiVari.setDefaultValue(false);
		isSoggettiVari.setWidth(100);
		isSoggettiVari.setColSpan(1);
		isSoggettiVari.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno();
			}
		});
		isSoggettiVari.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.redraw();
			}
		});
		
		nominativoSogg = new TextItem("nominativoSogg", "Nominativo soggetto");
		nominativoSogg.setStartRow(true);
		nominativoSogg.setWidth(300);
		nominativoSogg.setColSpan(12);
		// solo per le liquidazioni il nominativo soggetto è obbligatorio
		nominativoSogg.setAttribute("obbligatorio", isLiquidazione());
		nominativoSogg.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isLiquidazione();
			}
		}));
		nominativoSogg.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (isImpegno() && !isSoggettiVari()) || isAccertamento() || isLiquidazione();
			}
		});
		
		codFisPIVASogg = new TextItem("codFisPIVASogg", "CF/P.IVA soggetto");
		codFisPIVASogg.setStartRow(true);
		codFisPIVASogg.setWidth(160);
		codFisPIVASogg.setColSpan(8);
		codFisPIVASogg.setLength(28);
		codFisPIVASogg.setCharacterCasing(CharacterCasing.UPPER);
		// solo per le liquidazioni il CF/P.IVA soggetto è obbligatorio
		codFisPIVASogg.setAttribute("obbligatorio", isLiquidazione());
		codFisPIVASogg.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isLiquidazione();
			}
		}), new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if((isImpegno() && !isSoggettiVari()) || isAccertamento() || isLiquidazione()) {
					if(value != null && !"".equals((String) value)) {						
						RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
						return regExp.test((String) value);
					}
				}
				return true;
			}
		});
		codFisPIVASogg.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (isImpegno() && !isSoggettiVari()) || isAccertamento() || isLiquidazione();
			}
		});
		
		sedeSogg = new TextItem("sedeSogg", "Sede soggetto");
		sedeSogg.setStartRow(true);
		sedeSogg.setWidth(300);
		sedeSogg.setColSpan(12);
		sedeSogg.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (isImpegno() && !isSoggettiVari()) || isAccertamento() || isLiquidazione();
			}
		});
				
		if(!gridItem.isEsclusoCIGProposta()) {
			String[] lCIGValueMap = gridItem.getCIGValueMap();
			if(lCIGValueMap != null && lCIGValueMap.length > 0) {					
				codiceCIG = new SelectItem("codiceCIG", "CIG");
				codiceCIG.setStartRow(true);
				codiceCIG.setWidth(160);
				codiceCIG.setColSpan(8);
				if(lCIGValueMap.length == 1) {
					((SelectItem) codiceCIG).setDefaultValue(lCIGValueMap[0]);
				}
				codiceCIG.setValueMap(lCIGValueMap);
				((SelectItem)codiceCIG).setAllowEmptyValue(true);		
			} else {				
				CustomValidator codiceCIGLengthValidator = new CustomValidator() {
	
					@Override
					protected boolean condition(Object value) {
						if(isImpegno() || isAccertamento()) {
							if (value != null && !"".equals((String) value)) {
								String valore = (String) value;
								return valore.length() == 10;
							}
						}
						return true;
					}
				};
				codiceCIGLengthValidator.setErrorMessage("Il codice CIG, se indicato, deve essere di 10 caratteri");				
				codiceCIG = new TextItem("codiceCIG", "CIG");
				codiceCIG.setStartRow(true);
				codiceCIG.setWidth(160);
				codiceCIG.setColSpan(8);
				((TextItem) codiceCIG).setLength(10);
				codiceCIG.setInputTransformer(new FormItemInputTransformer() {
					
					@Override
					public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
						return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
					}
				});
				codiceCIG.setValidators(codiceCIGLengthValidator);			
			}
			codiceCIG.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {				
					return isImpegno() || isAccertamento();
				}
			});
		}		
		
		CustomValidator codiceCUPLengthValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(isImpegno() || isAccertamento()) {
					if (value != null && !"".equals((String) value)) {
						String valore = (String) value;
						return valore.length() == 15;
					}
				}
				return true;
			}
		};
		codiceCUPLengthValidator.setErrorMessage("Il codice CUP, se indicato, deve essere di 15 caratteri");		
		codiceCUP = new TextItem("codiceCUP", "CUP");
		codiceCUP.setStartRow(true);
		codiceCUP.setWidth(160);
		codiceCUP.setColSpan(8);
		codiceCUP.setLength(15);
		codiceCUP.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		codiceCUP.setValidators(codiceCUPLengthValidator);
		codiceCUP.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno() || isAccertamento();
			}
		});
		
		iban = new TextItem("iban", "IBAN");
		iban.setStartRow(true);
		iban.setWidth(300);
		iban.setColSpan(12);	
		iban.setLength(27);
		iban.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isLiquidazione();
			}
		});
		
		nroMandato = new TextItem("nroMandato", "N° mandato");
		nroMandato.setStartRow(true);
		nroMandato.setWidth(100);
		nroMandato.setColSpan(1);
		nroMandato.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isLiquidazione();
			}
		});
		
		dataMandato = new DateItem("dataMandato", "Data mandato");
		dataMandato.setWidth(123);
		dataMandato.setColSpan(1);
//		dataMandato.setAttribute("obbligatorio", true);
//		dataMandato.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				return isLiquidazione();
//			}
//		}));
		dataMandato.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isLiquidazione();
			}
		});
	}
	
	public List<FormItem> getFormItems() {
		
		List<FormItem> items = new ArrayList<FormItem>();		
		items.add(missione);
		items.add(desMissione);
		items.add(programma);
		items.add(desProgramma);
		items.add(filtroLivelliPdC);
		items.add(livelliPdC);
		items.add(desLivelliPdC);		
		items.add(annoBilancio);
		items.add(capitolo);
		items.add(centroDiCosto);	
		items.add(nroImpAcc);
		items.add(subImpAcc);			
		items.add(annoGestResidui);
		items.add(nroLiquidazione);
		items.add(annoLiquidazione);		
		items.add(importo);			
		items.add(spacerPrenotazione);
		items.add(isPrenotazione);
		items.add(spacerSoggettiVari);
		items.add(isSoggettiVari);
		items.add(nominativoSogg);
		items.add(codFisPIVASogg); 
		items.add(sedeSogg);					
		if(!gridItem.isEsclusoCIGProposta()) {
			items.add(codiceCIG);
		}	
		items.add(codiceCUP);		
		items.add(iban);
		items.add(nroMandato);
		items.add(dataMandato);
		
		return items;
	}
	
	public void buildLivelliPdCItem() {
		
		if(livelliPdC != null) {
			if(livelliPdC.getOptionDataSource() != null) {
				livelliPdC.getOptionDataSource().destroy();
			}
			livelliPdC = null;
		}
		
		SelectGWTRestDataSource livelliPdCDS = new SelectGWTRestDataSource("LoadComboLivelliPdCDataSource", true, "idNode", FieldType.TEXT);				
		
		livelliPdC = new SelectItem("livelliPdC", "Piano dei Conti") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desLivelliPdC", record.getAttribute("nome"));				
				mDynamicForm.markForRedraw();			
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.clearValue("livelliPdC");
					mDynamicForm.clearValue("desLivelliPdC");
					mDynamicForm.markForRedraw();
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.clearValue("livelliPdC");
				mDynamicForm.clearValue("desLivelliPdC");
				mDynamicForm.markForRedraw();
			};
		};
		livelliPdC.setStartRow(true);
		livelliPdC.setWrapTitle(false);
		livelliPdC.setWidth(600);
		livelliPdC.setColSpan(16);	
		livelliPdC.setPickListWidth(750);			
		ListGridField nomeField = new ListGridField("nome");		
		ListGridField idNodeField = new ListGridField("idNode");
		idNodeField.setHidden(true);				
		/**
		 * Con la proprietà setDataSetType("tree"); nel setPickListFields va settato per primo un campo NON hidden
		 * perchè abbiamo riscontrato problemi di creazione del componente grafico
		 */
		livelliPdC.setPickListFields(nomeField, idNodeField);  
		livelliPdC.setDataSetType("tree"); 
		livelliPdC.setDisplayField("nome");
		livelliPdC.setValueField("idNode");              
		livelliPdC.setOptionDataSource(livelliPdCDS);	
		livelliPdC.setAutoFetchData(false);
		livelliPdC.setAlwaysFetchMissingValues(false);
		livelliPdC.setFetchMissingValues(false);
		livelliPdC.setCachePickListResults(true);
		livelliPdC.setFetchDelay(500);
		livelliPdC.setClearable(true);
	    
		final TreeGrid pickListPropertiesLivelliPdC = new TreeGrid();  
		pickListPropertiesLivelliPdC.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		pickListPropertiesLivelliPdC.setSelectionType(SelectionStyle.NONE);
		pickListPropertiesLivelliPdC.setShowSelectedStyle(false);
		pickListPropertiesLivelliPdC.addCellClickHandler(new CellClickHandler() {
			
			@Override
			public void onCellClick(CellClickEvent event) {
				livelliPdC.onOptionClick(event.getRecord());				
			}
		});		
		pickListPropertiesLivelliPdC.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {			
				GWTRestDataSource livelliPdCDS = (GWTRestDataSource) livelliPdC.getOptionDataSource();
				if(isImpegno()) {
					livelliPdCDS.addParam("flgEntrataUscita", "U");
				} else if(isAccertamento()) {
					livelliPdCDS.addParam("flgEntrataUscita", "E");
				}
				livelliPdCDS.addParam("codice", filtroLivelliPdC.getValueAsString());
				livelliPdC.setOptionDataSource(livelliPdCDS);
//				livelliPdC.invalidateDisplayValueCache(); // Questo non va messo altrimenti perdo la decodifica del valore selezionato
			}
		});				
		pickListPropertiesLivelliPdC.setShowHeader(false);
        pickListPropertiesLivelliPdC.setAutoFitFieldWidths(true); 
        pickListPropertiesLivelliPdC.setShowAllRecords(true);
        // con overflow a VISIBLE quando ci sono molti record si vede solo una porzione della pickList e non si può scrollare
        pickListPropertiesLivelliPdC.setBodyOverflow(Overflow.AUTO);
        pickListPropertiesLivelliPdC.setOverflow(Overflow.AUTO);
        pickListPropertiesLivelliPdC.setLeaveScrollbarGap(false);
        /*
         * Impedisce il ricaricamento generale dell'albero ad ogni esplosione dei nodi anche 
         * se nodi foglia
         */
        pickListPropertiesLivelliPdC.setLoadDataOnDemand(false);
        pickListPropertiesLivelliPdC.setNodeIcon("blank.png");
        pickListPropertiesLivelliPdC.setFolderIcon("blank.png");

		livelliPdC.setPickListProperties(pickListPropertiesLivelliPdC);
		livelliPdC.setAttribute("obbligatorio", true);
		livelliPdC.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isImpegno() || isAccertamento();
			}
		}));
		livelliPdC.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isImpegno() || isAccertamento();
			}
		});
	}
	
	public static LinkedHashMap<String, String> buildFlgEntrataUscitaValueMap() {
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", "Entrata");
		flgEntrataUscitaValueMap.put("U", "Uscita");		
		return flgEntrataUscitaValueMap;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);				
	}
	
	public boolean isImpegno() {
		return ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB() != null && ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.IMPEGNI);
	}
	
	public boolean isAccertamento() {
		return ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB() != null && ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.ACCERTAMENTI);
	}
	
	public boolean isLiquidazione() {
		return ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB() != null && ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.LIQUIDAZIONI);
	}
	
	public boolean isPrenotazione() {
		boolean ret = false;
		if(isImpegno()) {
			ret = isPrenotazione.getValueAsBoolean() != null && isPrenotazione.getValueAsBoolean();
		}
		return ret;
	}
	
	public boolean isSoggettiVari() {
		boolean ret = false;
		if(isImpegno()) {
			ret = isSoggettiVari.getValueAsBoolean() != null && isSoggettiVari.getValueAsBoolean();
		}
		return ret;
	}
	
	public void reloadProgrammaItem() {
		if(programma != null) {
			final String value = programma.getValueAsString() != null ? programma.getValueAsString() : "";
			mDynamicForm.setValue("programma", "");			
			mDynamicForm.setValue("desProgramma", "");			
			programma.fetchData(new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						RecordList data = response.getDataAsRecordList();
						boolean trovato = false;
						if (data.getLength() > 0) {
							if (!"".equals(value)) {
								for (int i = 0; i < data.getLength(); i++) {
									String key = data.get(i).getAttribute("key");
									if (value.equals(key)) {
										mDynamicForm.setValue("desProgramma", data.get(i).getAttribute("value"));																	
										trovato = true;
										break;
									}
								}
							}
						}
						if (!trovato) {
							mDynamicForm.setValue("programma", "");														
							mDynamicForm.setValue("desProgramma", "");							
						}
					}
				}
			});
		}
	}
	
	public void reloadLivelliPdCItem() {
		Record record = new Record(mDynamicForm.getValues());
		/*
		buildLivelliPdCItem(); 
		List<FormItem> items = getFormItems();	
		mDynamicForm.setFieldsOrig(items.toArray(new FormItem[items.size()]));				
 		*/
		buildFormItems(); // ricostruisco tutti gli item del form e non solo livelliPdCItem, altrimenti poi le select missione e programma non funzionano più
		List<FormItem> items = getFormItems();	
		mDynamicForm.setFields(items.toArray(new FormItem[items.size()])); // devo usare setFields e non setFieldsOrig, altrimenti non aggiunge * sulle label dei campi obbligatori
		manageLoadSelectInEditRecord(record, livelliPdC, "livelliPdC", new String[] {"desLivelliPdC"}, "idNode");		
//		if(livelliPdC != null) {
//			final String value = livelliPdC.getValueAsString() != null ? livelliPdC.getValueAsString() : "";
//			mDynamicForm.setValue("livelliPdC", "");			
//			mDynamicForm.setValue("desLivelliPdC", "");	
//			mDynamicForm.markForRedraw();			
//			livelliPdC.fetchData(new DSCallback() {
//	
//				@Override
//				public void execute(DSResponse response, Object rawData, DSRequest request) {
//					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
//						RecordList data = response.getDataAsRecordList();
//						if (data.getLength() > 0) {
//							if (data.getLength() == 1) {
//								String newValue = data.get(0).getAttribute(livelliPdC.getValueFieldName());
//								mDynamicForm.setValue("livelliPdC", newValue);							
//								mDynamicForm.setValue("desLivelliPdC", data.get(0).getAttribute("nome"));
//							} else if (!"".equals(value)) {
//								boolean trovato = false;
//								for (int i = 0; i < data.getLength(); i++) {
//									if (value.equals(data.get(i).getAttribute(livelliPdC.getValueFieldName()))) {				
//										mDynamicForm.setValue("desLivelliPdC", data.get(i).getAttribute("nome"));																	
//										trovato = true;
//										break;
//									} 
//								}
//								if (!trovato) {
//									mDynamicForm.setValue("livelliPdC", "");														
//									mDynamicForm.setValue("desLivelliPdC", "");
//								}
//							}
//						} else {
//							mDynamicForm.setValue("livelliPdC", ""); 
//							mDynamicForm.setValue("desLivelliPdC", "");
//						}							
//					}
//				}
//			});
//		}
	}
	
	@Override
	public void editRecord(Record record) {		
		manageLoadSelectInEditRecord(record, missione, "missione", new String[] {"desMissione"}, "key");
		manageLoadSelectInEditRecord(record, programma, "programma", new String[] {"desProgramma"}, "key");
		manageLoadSelectInEditRecord(record, livelliPdC, "livelliPdC", new String[] {"desLivelliPdC"}, "idNode");
		// flgPrenotazione nel dettaglio lo chiamo in un altro modo  perchè qui è un boolean mentre in lista una stringa con valori 1/0		
		record.setAttribute("isPrenotazione", record.getAttribute("flgPrenotazione") != null && "1".equals(record.getAttribute("flgPrenotazione")));
		// flgSoggettiVari nel dettaglio lo chiamo in un altro modo  perchè qui è un boolean mentre in lista una stringa con valori 1/0		
		record.setAttribute("isSoggettiVari", record.getAttribute("flgSoggettiVari") != null && "1".equals(record.getAttribute("flgSoggettiVari")));
		super.editRecord(record);
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();		
		lRecordToSave.setAttribute("filtroLivelliPdC", "");
		lRecordToSave.setAttribute("flgPrenotazione", isPrenotazione.getValueAsBoolean() != null && isPrenotazione.getValueAsBoolean() ? "1" : "0");
		lRecordToSave.setAttribute("flgSoggettiVari", isSoggettiVari.getValueAsBoolean() != null && isSoggettiVari.getValueAsBoolean() ? "1" : "0");
		if(isImpegno() && isSoggettiVari.getValueAsBoolean() != null && isSoggettiVari.getValueAsBoolean()) {
			lRecordToSave.setAttribute("nominativoSogg", "");
			lRecordToSave.setAttribute("codFisPIVASogg", "");
			lRecordToSave.setAttribute("sedeSogg", "");
		}
		// Devo settare le date nel formato DD/MM/YYYY
		lRecordToSave.setAttribute("dataMandato", dataMandato.getValueAsDate() != null ? DateUtil.format(dataMandato.getValueAsDate()) : null);
		return lRecordToSave;
	}
	
}