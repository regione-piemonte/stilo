/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DatiGSACanvas extends ReplicableCanvas {
	
	protected TextItem raggruppamentoItem;
	protected TextItem progressivoItem;
	protected RadioGroupItem flgContoDebitoCreditoItem;
	protected FilteredSelectItemWithDisplay codContoPrimaNotaItem;
	protected HiddenItem desContoPrimaNotaItem;
	protected ExtendedNumericItem importoItem;
	protected SelectItem flgDareAvereItem;
	protected DateItem dataCompetenzaDaItem;
	protected DateItem dataCompetenzaAItem;
	
	protected ImgButtonItem lookupArchivioButton;
	
	private ReplicableCanvasForm mDynamicForm;

	public DatiGSACanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		raggruppamentoItem = new TextItem("raggruppamento", "Raggruppamento dei conti in una PN");	
		raggruppamentoItem.setShowTitle(true);
		raggruppamentoItem.setWidth(50);	
		raggruppamentoItem.setCanEdit(false);
		raggruppamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((DatiGSAItem) getItem()).isFromDettaglioMovimentoGSA();
			}
		});
		
		progressivoItem = new TextItem("progressivo", "Progr.");	   
		progressivoItem.setShowTitle(true);
		progressivoItem.setWidth(50);	
		progressivoItem.setCanEdit(false);
				
		flgContoDebitoCreditoItem = new RadioGroupItem("flgContoDebitoCredito", "Conto di debito/credito");
		HashMap<String, String> flgContoDebitoCreditoValueMap = new HashMap<String, String>();
		flgContoDebitoCreditoValueMap.put("S", "Si");
		flgContoDebitoCreditoValueMap.put("N", "No");
		flgContoDebitoCreditoItem.setValueMap(flgContoDebitoCreditoValueMap);		
		flgContoDebitoCreditoItem.setDefaultValue("N");		
		flgContoDebitoCreditoItem.setVertical(false);
		flgContoDebitoCreditoItem.setWrap(false);
		flgContoDebitoCreditoItem.setShowDisabled(false);
		flgContoDebitoCreditoItem.setAttribute("obbligatorio", true);
		flgContoDebitoCreditoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return !((DatiGSAItem) getItem()).isFromDettaglioMovimentoGSA();
			}
		}));
		flgContoDebitoCreditoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !((DatiGSAItem) getItem()).isFromDettaglioMovimentoGSA();
			}
		});
		flgContoDebitoCreditoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				reloadSelectContoPrimaNota();
			}
		});
		
		SelectGWTRestDataSource contoPrimaNotaDS = new SelectGWTRestDataSource("LoadComboContoPrimaNotaGSADataSource", "key", FieldType.TEXT, new String[] { "key", "value" }, true);
	
		codContoPrimaNotaItem = new FilteredSelectItemWithDisplay("codContoPrimaNota", "Conto di prima nota", contoPrimaNotaDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid pickListProperties = super.builPickListProperties();	
//				pickListProperties.setShowFilterEditor(false); 
				pickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource contoPrimaNotaDS = (GWTRestDataSource) codContoPrimaNotaItem.getOptionDataSource();
						if(!((DatiGSAItem) getItem()).isFromDettaglioMovimentoGSA()) {
							contoPrimaNotaDS.addParam("flgContoDebitoCredito", flgContoDebitoCreditoItem.getValueAsString());
						}
						Record lRecordFiltriContoPrimaNota = ((DatiGSAItem) getItem()).getRecordFiltriContoPrimaNota();
						if(lRecordFiltriContoPrimaNota != null) {
							contoPrimaNotaDS.addParam("nomeTipoDoc", lRecordFiltriContoPrimaNota.getAttribute("nomeTipoDoc"));
							contoPrimaNotaDS.addParam("codiceBP", lRecordFiltriContoPrimaNota.getAttribute("codiceBP"));
							contoPrimaNotaDS.addParam("codiceCapitolo", lRecordFiltriContoPrimaNota.getAttribute("codiceCapitolo"));
							contoPrimaNotaDS.addParam("codiceConto", lRecordFiltriContoPrimaNota.getAttribute("codiceConto"));
							contoPrimaNotaDS.addParam("descConto", lRecordFiltriContoPrimaNota.getAttribute("descConto"));
							contoPrimaNotaDS.addParam("entrataUscita", lRecordFiltriContoPrimaNota.getAttribute("entrataUscita"));
						}
						codContoPrimaNotaItem.setOptionDataSource(contoPrimaNotaDS);
						codContoPrimaNotaItem.invalidateDisplayValueCache();
					}
				});
				return pickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desContoPrimaNota", record.getAttribute("value"));
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("codContoPrimaNota", "");
				mDynamicForm.setValue("desContoPrimaNota", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("codContoPrimaNota", "");
					mDynamicForm.setValue("desContoPrimaNota", "");
				}
            }
		}; 
		codContoPrimaNotaItem.setWidth(250);
		codContoPrimaNotaItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o si è verificato un errore durante la chiamata ai servizi di AMCO");
		codContoPrimaNotaItem.setPickListWidth(350);
		codContoPrimaNotaItem.setValueField("key");
//		codContoPrimaNotaItem.setDisplayField("value");
		ListGridField codiceField = new ListGridField("key", "Cod.");
		codiceField.setWidth(100);
		ListGridField descrizioneField = new ListGridField("value", "Descrizione");
		descrizioneField.setWidth("*");
		codContoPrimaNotaItem.setPickListFields(codiceField, descrizioneField);	
		codContoPrimaNotaItem.setAutoFetchData(false);
		codContoPrimaNotaItem.setFetchMissingValues(true);
		codContoPrimaNotaItem.setAlwaysFetchMissingValues(true);
		codContoPrimaNotaItem.setCachePickListResults(false);
		codContoPrimaNotaItem.setRequired(true);
		
		desContoPrimaNotaItem = new HiddenItem("desContoPrimaNota");	   
		
		importoItem = new ExtendedNumericItem("importo", "Importo (&euro;)");
		importoItem.setKeyPressFilter("[-0-9.,]");
		importoItem.setWidth(100);
		importoItem.setColSpan(1);		
		importoItem.setDefaultValue(((DatiGSAItem) getItem()).getImportoDefaultValue());		
		importoItem.setAttribute("obbligatorio", true);
		importoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				mDynamicForm.setValue("importo", NumberFormatUtility.getFormattedValue(mDynamicForm.getValueAsString("importo")));
				return true;
			}
		});
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^-?([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
//		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
//			
//			@Override
//			protected boolean condition(Object value) {
//				String pattern = "#,##0.00";
//				double importo = 0;
//				if(value != null && !"".equals(value)) {
//					importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
//				}
//				return importo > 0;
//			}
//		};
//		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		importoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return true;
			}
		}), importoPrecisionValidator/*, importoMaggioreDiZeroValidator*/);
		importoItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				mDynamicForm.setValue("importo", NumberFormatUtility.getFormattedValue((String) event.getValue()));
			}
		});
		
		flgDareAvereItem = new SelectItem("flgDareAvere", "Dare/Avere"); 
		flgDareAvereItem.setShowTitle(false);
		flgDareAvereItem.setWidth(80);
		LinkedHashMap<String, String> flgDareAvereValueMap = new LinkedHashMap<String, String>();
		flgDareAvereValueMap.put("D", "Dare"); 
		flgDareAvereValueMap.put("A", "Avere");
		flgDareAvereItem.setValueMap(flgDareAvereValueMap);
		flgDareAvereItem.setRequired(true);
		
		CustomValidator lPeriodoCompetenzaValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				Date dataCompetenzaDa = dataCompetenzaDaItem.getValueAsDate();
		    	Date dataCompetenzaA = dataCompetenzaAItem.getValueAsDate();
		    	if(dataCompetenzaDa != null && dataCompetenzaA != null) {
		    		Integer differenceDays = CalendarUtil.getDaysBetween(dataCompetenzaDa, dataCompetenzaA);
		    		return (differenceDays >= 0);
		    	}		    	
		    	return true;
			}
		};
		lPeriodoCompetenzaValidator.setErrorMessage("La data di fine competenza non può essere antecedente a quella di inizio competenza");
		
		dataCompetenzaDaItem = new DateItem("dataCompetenzaDa", "Competenza dal");	   
		dataCompetenzaDaItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataCompetenzaDaItem.setValidators(lPeriodoCompetenzaValidator);
		
		dataCompetenzaAItem = new DateItem("dataCompetenzaA", "al");	   
		dataCompetenzaAItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataCompetenzaAItem.setValidators(lPeriodoCompetenzaValidator);
		
		mDynamicForm.setFields(raggruppamentoItem, progressivoItem, flgContoDebitoCreditoItem, codContoPrimaNotaItem, desContoPrimaNotaItem, importoItem, flgDareAvereItem, dataCompetenzaDaItem, dataCompetenzaAItem);
					
		addChild(mDynamicForm);		
	}
	
	public void reloadSelectContoPrimaNota() {
//		codContoPrimaNotaItem.fetchData();
//		mDynamicForm.setValue("codContoPrimaNota", "");
//		mDynamicForm.setValue("desContoPrimaNota", "");
		final String value =  mDynamicForm.getValueAsString("codContoPrimaNota");
		codContoPrimaNotaItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				RecordList data = response.getDataAsRecordList();
				if(value != null && !"".equals(value)) {
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							if (value.equals(data.get(i).getAttribute("key"))) {
								trovato = true;
							}
						}
					}
					if(!trovato) {
						mDynamicForm.setValue("codContoPrimaNota", "");
						mDynamicForm.setValue("desContoPrimaNota", "");
					}
				}
			}
		});
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);		
		raggruppamentoItem.setCanEdit(false);
		progressivoItem.setCanEdit(false);
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);	
		if(codContoPrimaNotaItem != null) {
			if (record.getAttribute("codContoPrimaNota") != null && !"".equals(record.getAttributeAsString("codContoPrimaNota")) &&
				record.getAttribute("desContoPrimaNota") != null && !"".equals(record.getAttributeAsString("desContoPrimaNota"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("codContoPrimaNota"), record.getAttribute("codContoPrimaNota") + " - " + record.getAttribute("desContoPrimaNota"));
				codContoPrimaNotaItem.setValueMap(valueMap);
			}
		}
	}
		
}
