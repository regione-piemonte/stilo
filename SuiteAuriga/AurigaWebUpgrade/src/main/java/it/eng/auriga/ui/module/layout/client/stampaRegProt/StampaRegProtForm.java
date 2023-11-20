/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;


public class StampaRegProtForm extends DynamicForm{

	private ValuesManager lValuesManager;
	
	private SelectItem registroItem;
	private RadioGroupItem tipoIntervalloItem;
	private AnnoItem annoRegItem;
	private NumericItem nroRegDaItem;
	private NumericItem nroRegAItem;
	private DateItem dataRegDaItem;
	private DateItem dataRegAItem;	
	private CheckboxItem schermaRiservatiItem;
	
	public StampaRegProtForm(final String nomeEntita){
		
		setWrapItemTitles(false);
		setNumCols(12);
		setColWidths("200", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "*");

		registroItem = new SelectItem("siglaRegistro", "Registro");
		registroItem.setRequired(true);
		registroItem.setColSpan(10);
		registroItem.setDefaultToFirstOption(true);
		registroItem.setAllowEmptyValue(false);
		
//		if(nomeEntita.equalsIgnoreCase("stampa_reg_prot")){
//		
//			LinkedHashMap<String, String> registroValueMap = new LinkedHashMap<String, String>();		
//			if(Layout.isPrivilegioAttivo("SR/PRT/PG")) {
//				registroValueMap.put("PG", "Protocollo Generale"); 
//			}
//			if(Layout.isPrivilegioAttivo("SR/PRT/PI")) {
//				registroValueMap.put("PI", "Protocollo Interno");
//			}
//			registroItem.setValueMap(registroValueMap);  
//		}
//		else{
		
		if (!nomeEntita.equalsIgnoreCase("stampa_reg_pubblicazioni")) {
			SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboStampaRegistro",
					new String[] { "codice", "descrizione" }, true);
			lGwtRestDataSource.addParam("tipoStampa", nomeEntita);
			lGwtRestDataSource.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					RecordList data = response.getDataAsRecordList();
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
					if (data != null && data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							if (data.get(i).getAttribute("idRegistro") != null
									&& !data.get(i).getAttribute("idRegistro").equals("")) {
								valueMap.put(data.get(i).getAttribute("idRegistro"),
										data.get(i).getAttribute("voceRegistro").equals(null) ? ""
												: data.get(i).getAttribute("voceRegistro"));
							}
						}
						registroItem.setDefaultValue(data.get(0).getAttribute("idRegistro"));
					}
					registroItem.setValueMap(valueMap);
				}

			});
			//		}
		} else {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put("PUBBL", "Pubblicazioni");
			registroItem.setDefaultValue("PUBBL");
			registroItem.setValueMap(valueMap);
		}
		
		registroItem.setStartRow(true);
		registroItem.setEndRow(true);
		
		LinkedHashMap<String, String> tipoIntervalloMap = new LinkedHashMap<String, String>();  
		tipoIntervalloMap.put("D", getLabelIntervalloD(nomeEntita));  
		tipoIntervalloMap.put("N", "di numeri di protocollo");  
				
		tipoIntervalloItem = new RadioGroupItem("tipoIntervallo");  
		tipoIntervalloItem.setDefaultValue("D");  
		tipoIntervalloItem.setTitle("Seleziona intervallo");
		tipoIntervalloItem.setValueMap(tipoIntervalloMap); 
		tipoIntervalloItem.setVertical(false);
		tipoIntervalloItem.setWrap(false);			
		tipoIntervalloItem.setColSpan(10);
		tipoIntervalloItem.setStartRow(true);		
		tipoIntervalloItem.setEndRow(true);		
		tipoIntervalloItem.addChangedHandler(new ChangedHandler() {  
			public void onChanged(ChangedEvent event) { 
				markForRedraw();
			}
		});
		
		annoRegItem = new AnnoItem("annoReg", "Anno");			
		annoRegItem.setStartRow(true);
		annoRegItem.setEndRow(false);		
		annoRegItem.setWidth(100);
		annoRegItem.setColSpan(1);
		annoRegItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return "N".equals(tipoIntervalloItem.getValue());
			}
		});
		annoRegItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return "N".equals(tipoIntervalloItem.getValue());
			}
		}));
		
		nroRegDaItem = new NumericItem("nroRegDa", getTitleNroRegDa(nomeEntita));
		nroRegDaItem.setStartRow(false);
		nroRegDaItem.setEndRow(false);
		nroRegDaItem.setColSpan(1);
		nroRegDaItem.setLength(7);		
		nroRegDaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return "N".equals(tipoIntervalloItem.getValue());
			}
		});
		nroRegDaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return "N".equals(tipoIntervalloItem.getValue());
			}
		}));
		
		nroRegAItem = new NumericItem("nroRegA", "al");
		nroRegAItem.setStartRow(false);
		nroRegAItem.setEndRow(true);
		nroRegAItem.setColSpan(1);
		nroRegAItem.setLength(7);
		nroRegAItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return "N".equals(tipoIntervalloItem.getValue());
			}
		});
		
		dataRegDaItem = new DateItem("dataRegDa", getTitleDataRegDa(nomeEntita));
		dataRegDaItem.setAttribute("obbligatorio", true);
		dataRegDaItem.setStartRow(true);
		dataRegDaItem.setEndRow(false);	
		dataRegDaItem.setColSpan(1);
		dataRegDaItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return "D".equals(tipoIntervalloItem.getValue());
			}
		});
		dataRegDaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return "D".equals(tipoIntervalloItem.getValue());
			}
		}));
		
		dataRegAItem = new DateItem("dataRegA", "al");
		dataRegAItem.setStartRow(false);
		dataRegAItem.setEndRow(true);	
		dataRegAItem.setColSpan(1);
		dataRegAItem.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return "D".equals(tipoIntervalloItem.getValue());
			}
		});
					
		schermaRiservatiItem = new CheckboxItem("schermaRiservati");
		schermaRiservatiItem.setTitle("scherma dati delle registrazioni riservate");
		schermaRiservatiItem.setWidth("*");
		schermaRiservatiItem.setColSpan(2);
		schermaRiservatiItem.setStartRow(true);
		schermaRiservatiItem.setEndRow(true);
		
		lValuesManager = new ValuesManager();
		setValuesManager(lValuesManager);

		if (!nomeEntita.equalsIgnoreCase("stampa_reg_pubblicazioni")) {
			setFields(registroItem, tipoIntervalloItem, annoRegItem, nroRegDaItem, nroRegAItem, dataRegDaItem,
					dataRegAItem, schermaRiservatiItem);
		} else {
			setFields(registroItem, tipoIntervalloItem, annoRegItem, nroRegDaItem, nroRegAItem, dataRegDaItem,
					dataRegAItem);
		}
		markForRedraw();
		
	}
	
	public Record getRecord(){
		if (lValuesManager.validate()){
			Record lRecord = new Record(lValuesManager.rememberValues());
			return lRecord;
		} else return null;
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
	
	protected String getTitleDataRegDa(String nomeEntita){
		return nomeEntita.equalsIgnoreCase("stampa_reg_pubblicazioni") ? "Data di pubblicazione dal" : "Data di registrazione dal";
	}
	
	protected String getTitleNroRegDa(String nomeEntita){
		return nomeEntita.equalsIgnoreCase("stampa_reg_pubblicazioni") ? "N.ro di pubblicazione dal" : "N.ro di registrazione dal";
	}
	
	protected String getLabelIntervalloD(String nomeEntita) {
		return nomeEntita.equalsIgnoreCase("stampa_reg_pubblicazioni") ? "di date di pubblicazione" : "di date di registrazione";
	}
}
