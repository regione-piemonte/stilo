/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DatiContabiliATERSIRDetail extends CustomDetail {
	
	private ListaDatiContabiliATERSIRItem gridItem;
	private Record gridRecord;
	private DynamicForm formMain;
	
	private ExtendedTextItem codiceCapitoloItem;
	private FilteredSelectItemWithDisplay descrizioneCapitoloItem;
	private TextItem codiceMissioneItem;
	private TextItem descrizioneMissioneItem;
	private TextItem codiceProgrammaItem;
	private TextItem descrizioneProgrammaItem;
	private TextItem codiceTitoloItem;
	private TextItem descrizioneTitoloItem;
	private TextItem codiceMacroAggregatoItem;
	private TextItem descrizioneMacroAggregatoItem;
	private TextItem denominazioneBeneficiarioItem;
	private TextItem cfPIvaBeneficiarioItem;
	private ExtendedNumericItem importoItem;	
	private NumericItem nrImpegnoItem;
	private DateItem dataImpegnoItem; 
	private TextAreaItem descrizioneImpegnoItem;		
	private AnnoItem annoCompetenzaItem;
	private AnnoItem annoRegistrazioneItem;
	private TextItem codiceCIGItem;
	private TextItem codiceCUPItem;
	private HiddenItem idItem;
	
	public DatiContabiliATERSIRDetail(final ListaDatiContabiliATERSIRItem gridItem, String nomeEntita, Record record, boolean canEdit) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		this.gridRecord = record;
		
		formMain = new DynamicForm();
		formMain.setWidth100();
		formMain.setNumCols(7);
		formMain.setColWidths(10, 5, 10, 5, 10, 5, 1);
		formMain.setValuesManager(vm);
		formMain.setWrapItemTitles(false);

		// Codice Capitolo
		codiceCapitoloItem = new ExtendedTextItem("codiceCapitolo", "Capitolo");
		codiceCapitoloItem.setWidth(120);
		codiceCapitoloItem.setTextAlign(Alignment.RIGHT);
		codiceCapitoloItem.setAttribute("obbligatorio", true);	
		codiceCapitoloItem.setStartRow(true);
		codiceCapitoloItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				formMain.setValue("descrizioneCapitolo", (String) null);
				
				formMain.setValue("codiceMissione",             (String) null);
				formMain.setValue("descrizioneMissione",        (String) null);
				
				formMain.setValue("codiceProgramma",            (String) null);
				formMain.setValue("descrizioneProgramma",       (String) null);
				
				formMain.setValue("codiceTitolo",               (String) null);
				formMain.setValue("descrizioneTitolo",          (String) null);
				
				formMain.setValue("codiceMacroAggregato",       (String) null);
				formMain.setValue("descrizioneMacroAggregato",  (String) null);
				
				formMain.clearErrors(true);
				final String value = codiceCapitoloItem.getValueAsString();
				 {
					GWTRestDataSource capitoloDS = (GWTRestDataSource) descrizioneCapitoloItem.getOptionDataSource();
					capitoloDS.addParam("codiceCapitolo", value);
					capitoloDS.addParam("descrizione", null);
					descrizioneCapitoloItem.setOptionDataSource(capitoloDS);
					
					if (value != null && !"".equals(value)) {
						descrizioneCapitoloItem.fetchData(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
								boolean trovato = false;
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										String codiceCapitolo = data.get(i).getAttribute("codiceCapitolo");
										if (value.equals(codiceCapitolo)) {
											
											SelectGWTRestDataSource capitoloDS = (SelectGWTRestDataSource) descrizioneCapitoloItem.getOptionDataSource();
											capitoloDS.addParam("descrizione", data.get(i).getAttribute("descrizioneCapitolo"));
											descrizioneCapitoloItem.setOptionDataSource(capitoloDS);
																						
											formMain.setValue("descrizioneCapitolo",        data.get(i).getAttribute("descrizioneCapitolo"));
											
											formMain.setValue("codiceMissione",             data.get(i).getAttribute("codiceMissione"));
											formMain.setValue("descrizioneMissione",        data.get(i).getAttribute("descrizioneMissione"));
											
											formMain.setValue("codiceProgramma",            data.get(i).getAttribute("codiceProgramma"));
											formMain.setValue("descrizioneProgramma",       data.get(i).getAttribute("descrizioneProgramma"));
											
											formMain.setValue("codiceTitolo",               data.get(i).getAttribute("codiceTitolo"));
											formMain.setValue("descrizioneTitolo",          data.get(i).getAttribute("descrizioneTitolo"));
											
											formMain.setValue("codiceMacroAggregato",       data.get(i).getAttribute("codiceMacroAggregato"));
											formMain.setValue("descrizioneMacroAggregato",  data.get(i).getAttribute("descrizioneMacroAggregato"));
											
											formMain.markForRedraw();
											
											formMain.clearErrors(true);
											trovato = true;
											break;
										}
									}
								}
								if (!trovato) {
									codiceCapitoloItem.validate();
									codiceCapitoloItem.blurItem();
								}
							}
						});
					} else {
						descrizioneCapitoloItem.fetchData();
					}
				}
			}
		});
		
		// Descrizione capitolo
		SelectGWTRestDataSource capitoloDS = new SelectGWTRestDataSource("LoadComboCapitoliDatiContabiliDataSource", "codiceCapitolo", FieldType.TEXT, new String[] { "descrizioneCapitolo" }, true);
		descrizioneCapitoloItem = new FilteredSelectItemWithDisplay("descrizioneCapitolo", capitoloDS) {
			
			@Override
			public void onOptionClick(Record record) {
				
				super.onOptionClick(record);
				
				SelectGWTRestDataSource capitoloDS = (SelectGWTRestDataSource) descrizioneCapitoloItem.getOptionDataSource();
				capitoloDS.addParam("descrizione", record.getAttributeAsString("descrizioneCapitolo"));
				descrizioneCapitoloItem.setOptionDataSource(capitoloDS);
				
				formMain.clearErrors(true);
				formMain.setValue("codiceCapitolo",             record.getAttribute("codiceCapitolo"));
				
				formMain.setValue("codiceMissione",             record.getAttribute("codiceMissione"));
				formMain.setValue("descrizioneMissione",        record.getAttribute("descrizioneMissione"));
				
				formMain.setValue("codiceProgramma",            record.getAttribute("codiceProgramma"));
				formMain.setValue("descrizioneProgramma",       record.getAttribute("descrizioneProgramma"));
				
				formMain.setValue("codiceTitolo",               record.getAttribute("codiceTitolo"));
				formMain.setValue("descrizioneTitolo",          record.getAttribute("descrizioneTitolo"));
				
				formMain.setValue("codiceMacroAggregato",       record.getAttribute("codiceMacroAggregato"));
				formMain.setValue("descrizioneMacroAggregato",  record.getAttribute("descrizioneMacroAggregato"));
				
				formMain.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					
					@Override
					public void execute() {
						descrizioneCapitoloItem.fetchData();
					}
				});
				
				formMain.markForRedraw();
			}					
			
			@Override
			protected void clearSelect() {
				
				super.clearSelect();
				
				SelectGWTRestDataSource capitoloDS = (SelectGWTRestDataSource) descrizioneCapitoloItem.getOptionDataSource();
				capitoloDS.addParam("descrizione", null);
				descrizioneCapitoloItem.setOptionDataSource(capitoloDS);
				
				formMain.setValue("codiceCapitolo", "");
				formMain.setValue("descrizioneCapitolo",  "");
				
				formMain.setValue("codiceMissione", "");
				formMain.setValue("descrizioneMissione",  "");
				
				formMain.setValue("codiceProgramma", "");
				formMain.setValue("descrizioneProgramma",  "");
				
				formMain.setValue("codiceTitolo", "");
				formMain.setValue("descrizioneTitolo",  "");
				
				formMain.setValue("codiceMacroAggregato", "");
				formMain.setValue("descrizioneMacroAggregato",  "");
				
				formMain.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					
					@Override
					public void execute() {
						descrizioneCapitoloItem.fetchData();
					}
				});
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					
					SelectGWTRestDataSource capitoloDS = (SelectGWTRestDataSource) descrizioneCapitoloItem.getOptionDataSource();
					capitoloDS.addParam("descrizione", null);
					descrizioneCapitoloItem.setOptionDataSource(capitoloDS);
					
					formMain.setValue("codiceCapitolo", "");
					formMain.setValue("descrizioneCapitolo",  "");
					
					formMain.setValue("codiceMissione", "");
					formMain.setValue("descrizioneMissione",  "");
					
					formMain.setValue("codiceProgramma", "");
					formMain.setValue("descrizioneProgramma",  "");
					
					formMain.setValue("codiceTitolo", "");
					formMain.setValue("descrizioneTitolo",  "");
					
					formMain.setValue("codiceMacroAggregato", "");
					formMain.setValue("descrizioneMacroAggregato",  "");
					
					formMain.clearErrors(true);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						
						@Override
						public void execute() {
							descrizioneCapitoloItem.fetchData();
						}
					});
				}
            }
		};
		
		descrizioneCapitoloItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		
		descrizioneCapitoloItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return true;
			}
		}));
		
		descrizioneCapitoloItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneCapitolo") : null;
			}
		});
		
		descrizioneCapitoloItem.setShowTitle(false);
		descrizioneCapitoloItem.setColSpan(5);
		descrizioneCapitoloItem.setClearable(true);
		descrizioneCapitoloItem.setShowIcons(true);
		descrizioneCapitoloItem.setAttribute("obbligatorio", true);
		descrizioneCapitoloItem.setWidth(370);
		
		descrizioneCapitoloItem.setFilterLocally(true);
		descrizioneCapitoloItem.setAutoFetchData(false);
		descrizioneCapitoloItem.setAlwaysFetchMissingValues(true);
		descrizioneCapitoloItem.setFetchMissingValues(true);
		descrizioneCapitoloItem.setValueField("descrizioneCapitolo");
		descrizioneCapitoloItem.setOptionDataSource(capitoloDS);
		descrizioneCapitoloItem.setStartRow(false);
		
		// Definizione delle colonne
		// CAPITOLO
		ListGridField codiceCapitoloField = new ListGridField("codiceCapitolo", I18NUtil.getMessages().dati_contabili_combo_capitoloField_title());
		codiceCapitoloField.setWidth(120);
		codiceCapitoloField.setShowHover(true);
		
		// DESCRIZIONE
		ListGridField descrizioneCapitoloField = new ListGridField("descrizioneCapitolo", I18NUtil.getMessages().dati_contabili_combo_descrizioneField_title());
		descrizioneCapitoloField.setWidth("*");
		
		List<ListGridField> capitoliPickListFields = new ArrayList<ListGridField>();
		capitoliPickListFields.add(codiceCapitoloField);
		capitoliPickListFields.add(descrizioneCapitoloField);
		
		// Aggiungo le colonne
		descrizioneCapitoloItem.setPickListFields(capitoliPickListFields.toArray(new ListGridField[capitoliPickListFields.size()]));
		
//		ListGrid pickListProperties = descrizioneCapitoloItem.getPickListProperties();		
//		pickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//			@Override
//			public void onFilterData(FetchDataEvent event) {
//				String codiceCapitolo = formMain.getValueAsString("codiceCapitolo");
//				GWTRestDataSource capitoloDS = (GWTRestDataSource) descrizioneCapitoloItem.getOptionDataSource();
//				capitoloDS.addParam("codiceCapitolo", codiceCapitolo);
//				descrizioneCapitoloItem.setOptionDataSource(capitoloDS);
//				descrizioneCapitoloItem.invalidateDisplayValueCache();
//			}
//		});
//		descrizioneCapitoloItem.setPickListProperties(pickListProperties);
		
		// Codice Missione
		codiceMissioneItem = new TextItem("codiceMissione", "Missione");
		codiceMissioneItem.setWidth(120);
		codiceMissioneItem.setTextAlign(Alignment.RIGHT);
		codiceMissioneItem.setStartRow(true);

		// Descrizione Missione
		descrizioneMissioneItem = new TextItem("descrizioneMissione");		
		descrizioneMissioneItem.setColSpan(5);
		descrizioneMissioneItem.setWidth(370);
		descrizioneMissioneItem.setShowTitle(false);
		descrizioneMissioneItem.setStartRow(false);

		// Codice Programma 
		codiceProgrammaItem = new TextItem("codiceProgramma", "Programma");
		codiceProgrammaItem.setWidth(120);
		codiceProgrammaItem.setTextAlign(Alignment.RIGHT);
		codiceProgrammaItem.setStartRow(true);

		// Descrizione Programma 
		descrizioneProgrammaItem = new TextItem("descrizioneProgramma");
		descrizioneProgrammaItem.setColSpan(5);
		descrizioneProgrammaItem.setWidth(370);
		descrizioneProgrammaItem.setShowTitle(false);
		descrizioneProgrammaItem.setStartRow(false);

		// Codice Titolo 
		codiceTitoloItem = new TextItem("codiceTitolo", "Titolo");
		codiceTitoloItem.setWidth(120);
		codiceTitoloItem.setTextAlign(Alignment.RIGHT);
		codiceTitoloItem.setStartRow(true);
		
		// Descrizione Titolo 
		descrizioneTitoloItem = new TextItem("descrizioneTitolo");
		descrizioneTitoloItem.setColSpan(5);
		descrizioneTitoloItem.setWidth(370);
		descrizioneTitoloItem.setShowTitle(false);
		descrizioneTitoloItem.setStartRow(false);
				
		// Codice Macro aggregato 
		codiceMacroAggregatoItem = new TextItem("codiceMacroAggregato", "Macro");
		codiceMacroAggregatoItem.setWidth(120);
		codiceMacroAggregatoItem.setTextAlign(Alignment.RIGHT);
		codiceMacroAggregatoItem.setStartRow(true);
		
		// Descrizione Macro aggregato 
		descrizioneMacroAggregatoItem = new TextItem("descrizioneMacroAggregato");
		descrizioneMacroAggregatoItem.setColSpan(5);
		descrizioneMacroAggregatoItem.setWidth(370);
		descrizioneMacroAggregatoItem.setShowTitle(false);
		descrizioneMacroAggregatoItem.setStartRow(false);
				
		// Beneficiario (Denominazione)
		denominazioneBeneficiarioItem = new TextItem("denominazioneBeneficiario", "Beneficiario");
		denominazioneBeneficiarioItem.setColSpan(4);
		denominazioneBeneficiarioItem.setWidth(280);
		denominazioneBeneficiarioItem.setStartRow(true);
		denominazioneBeneficiarioItem.setAttribute("obbligatorio", true);
		denominazioneBeneficiarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return true;
			}
		}));

		// Beneficiario (C.F/P.IVA)
		cfPIvaBeneficiarioItem = new TextItem("cfPIvaBeneficiario", "C.F/P.IVA");
		cfPIvaBeneficiarioItem.setWidth(150);
		cfPIvaBeneficiarioItem.setStartRow(false);
		
		// Importo (€)
		importoItem = new ExtendedNumericItem("importo", "Importo (&euro;)");
		importoItem.setColSpan(6);
		importoItem.setWidth(120);
		importoItem.setStartRow(true);
		importoItem.setAttribute("obbligatorio", true);
		importoItem.setStartRow(true);
		importoItem.setKeyPressFilter("[0-9.,]");		
		importoItem.addChangedBlurHandler(new ChangedHandler() {
				
			@Override
			public void onChanged(ChangedEvent event) {
				String valueStr = (String) event.getValue();
				if(valueStr != null && !"".equals(valueStr)) {
					if(importoItem.validate()) {
						importoItem.setValue(NumberFormatUtility.getFormattedValue(valueStr));
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
				
				String pattern = "#,##0.00";
				double importo = 0;
				if(value != null && !"".equals((String) value)) {
					importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
				}
				return importo > 0;
			}
		};
		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		importoItem.setValidators(importoPrecisionValidator, importoMaggioreDiZeroValidator);		
				
		// N° Impegno
		nrImpegnoItem = new NumericItem("nrImpegno", "N° Impegno");
		nrImpegnoItem.setWidth(120);
		nrImpegnoItem.setStartRow(true);
		// il campo "N° impegno" non deve essere mai obbligatorio
//		nrImpegnoItem.setAttribute("obbligatorio", true);
//		nrImpegnoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				return true;
//			}
//		}));
		
		// Data impegno
		dataImpegnoItem = new DateItem("dataImpegno", "Data impegno");
		dataImpegnoItem.setColSpan(4);
		dataImpegnoItem.setWidth(130);
		dataImpegnoItem.setStartRow(false);
		
		// Descrizione impegno 
		descrizioneImpegnoItem = new TextAreaItem("descrizioneImpegno", "Descrizione impegno");
		descrizioneImpegnoItem.setWidth(470);
		descrizioneImpegnoItem.setHeight(60);
		descrizioneImpegnoItem.setColSpan(6);
		descrizioneImpegnoItem.setStartRow(true);
		
		// Anno competenza
		annoCompetenzaItem = new AnnoItem("annoCompetenza", "Anno competenza");
		annoCompetenzaItem.setColSpan(6);
		annoCompetenzaItem.setWidth(120);
		annoCompetenzaItem.setStartRow(true);
		annoCompetenzaItem.setAttribute("obbligatorio", true);
		annoCompetenzaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return true;
			}
		}));
		
		// Anno di registrazione
		annoRegistrazioneItem = new AnnoItem("annoRegistrazione", "Anno di registrazione");
		annoRegistrazioneItem.setColSpan(6);
		annoRegistrazioneItem.setWidth(120);
		annoRegistrazioneItem.setStartRow(true);

		// CIG
		codiceCIGItem = new TextItem("codiceCIG", "CIG");
		codiceCIGItem.setColSpan(6);
		codiceCIGItem.setWidth(120);
		codiceCIGItem.setLength(10);
		codiceCIGItem.setTextAlign(Alignment.RIGHT);
		codiceCIGItem.setStartRow(true);
		codiceCIGItem.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		
		CustomValidator codiceCIGLengthValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (value != null && !"".equals((String) value)) {
					String valore = (String) value;
					return valore.length() == 10;
				}
				return true;
			}
		};
		
		codiceCIGLengthValidator.setErrorMessage("Il codice CIG, se indicato, deve essere di 10 caratteri");
		codiceCIGItem.setValidators(codiceCIGLengthValidator);	
		
		// CUP
		codiceCUPItem = new TextItem("codiceCUP", "CUP");
		codiceCUPItem.setColSpan(6);
		codiceCUPItem.setWidth(120);
		codiceCUPItem.setLength(15);
		codiceCUPItem.setTextAlign(Alignment.RIGHT);
		codiceCUPItem.setStartRow(true);
		codiceCUPItem.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		CustomValidator codiceCUPLengthValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				
				if (value != null && !"".equals((String) value)) {
					String valore = (String) value;
					return valore.length() == 15;
				}
			
				return true;
			}
		};
		codiceCUPLengthValidator.setErrorMessage("Il codice CUP, se indicato, deve essere di 15 caratteri");	
		codiceCUPItem.setValidators(codiceCUPLengthValidator);
		
		idItem = new HiddenItem("id");
		
		formMain.setFields(codiceCapitoloItem,
						   descrizioneCapitoloItem,
						   codiceMissioneItem, 
						   descrizioneMissioneItem,
						   codiceProgrammaItem,
						   descrizioneProgrammaItem,
						   codiceTitoloItem,
						   descrizioneTitoloItem,
						   codiceMacroAggregatoItem,
						   descrizioneMacroAggregatoItem,
						   denominazioneBeneficiarioItem,
						   cfPIvaBeneficiarioItem,
						   importoItem,
						   nrImpegnoItem,
						   dataImpegnoItem, 
						   descrizioneImpegnoItem,		
						   annoCompetenzaItem,
						   annoRegistrazioneItem,
						   codiceCIGItem,
						   codiceCUPItem,
						   idItem
		                  );
				
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(formMain);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
		
		if(record != null) {
			editRecord(record);
		} else {
			editNewRecord();
		}
		
		setCanEdit(canEdit);				
	}
	
	public void reloadCUPValueMap(String codiceCIG)  {	
		RecordList lCIGCUPRecordList = getCIGCUPRecordList();
		if(lCIGCUPRecordList != null && lCIGCUPRecordList.getLength() > 0) {
			List<String> listaCodCUP = new ArrayList<String>();
			if(codiceCIG != null && !"".equals(codiceCIG)) {
				for(int i=0; i < lCIGCUPRecordList.getLength(); i++) {
					if(lCIGCUPRecordList.get(i).getAttribute("codiceCIG") != null && codiceCIG.equals(lCIGCUPRecordList.get(i).getAttribute("codiceCIG"))) {
						listaCodCUP.add(lCIGCUPRecordList.get(i).getAttribute("codiceCUP"));
					}
				}
			}
			codiceCUPItem.setValueMap(listaCodCUP.toArray(new String[listaCodCUP.size()]));				
			if(codiceCUPItem.getValue() != null && !"".equals(codiceCUPItem.getValue()) && listaCodCUP != null && !listaCodCUP.contains(codiceCUPItem.getValue())) {
				formMain.clearValue("codiceCUP");
			}
			
		}
	}
	
	public RecordList getCIGCUPRecordList() {
		RecordList lCIGCUPRecordList = gridItem.getCIGCUPRecordList();		
		if(lCIGCUPRecordList != null) {
			if(gridRecord != null && gridRecord.getAttribute("codiceCIG") != null && !"".equals(gridRecord.getAttribute("codiceCIG")) &&
				gridRecord.getAttribute("codiceCUP") != null && !"".equals(gridRecord.getAttribute("codiceCUP"))) {				
				boolean trovato = false;				
				for(int i = 0; i < lCIGCUPRecordList.getLength(); i++) {
					if(lCIGCUPRecordList.get(i).getAttribute("codiceCIG") != null && gridRecord.getAttribute("codiceCIG").equals(lCIGCUPRecordList.get(i).getAttribute("codiceCIG")) &&
						lCIGCUPRecordList.get(i).getAttribute("codiceCUP") != null && gridRecord.getAttribute("codiceCUP").equals(lCIGCUPRecordList.get(i).getAttribute("codiceCUP"))) {
						trovato = true;
					}
				}
				if(!trovato) {
					RecordList lCIGCUPRecordListNew = new RecordList();		
					Record lRecord = new Record();
					lRecord.setAttribute("codiceCIG", gridRecord.getAttribute("codiceCIG"));
					lRecord.setAttribute("codiceCUP", gridRecord.getAttribute("codiceCUP"));
					lCIGCUPRecordListNew.add(lRecord);
					lCIGCUPRecordListNew.addList(lCIGCUPRecordList.toArray());
					return lCIGCUPRecordListNew;
				}								
			}
		}
		return lCIGCUPRecordList;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);	
		codiceMissioneItem.setCanEdit(false);
		descrizioneMissioneItem.setCanEdit(false);
		
		codiceProgrammaItem.setCanEdit(false);
		descrizioneProgrammaItem.setCanEdit(false);
		
		codiceTitoloItem.setCanEdit(false);
		descrizioneTitoloItem.setCanEdit(false);
		
		codiceMacroAggregatoItem.setCanEdit(false);
		descrizioneMacroAggregatoItem.setCanEdit(false);
	}
	
	@Override
	public void editNewRecord() {		
		reloadCUPValueMap(null);
		super.editNewRecord();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		reloadCUPValueMap((String) initialValues.get("codiceCIG"));
		super.editNewRecord(initialValues);
	}
	
	@Override
	public void editRecord(Record record) {		
		reloadCUPValueMap(record.getAttribute("codiceCIG"));
		super.editRecord(record);
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();		
		
		// Devo settare le date nel formato DD/MM/YYYY
		lRecordToSave.setAttribute("dataImpegno", dataImpegnoItem.getValueAsDate() != null ? DateUtil.format(dataImpegnoItem.getValueAsDate()) : null);
		return lRecordToSave;
	}
}