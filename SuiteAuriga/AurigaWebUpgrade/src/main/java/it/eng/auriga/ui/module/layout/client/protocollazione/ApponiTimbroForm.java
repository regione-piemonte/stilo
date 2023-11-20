/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class ApponiTimbroForm extends DynamicForm {
	
	private ApponiTimbroForm instance;
	
	private SelectItem posizioneTimbro;
	private SelectItem rotazioneTimbro;
	private SelectItem tipoPagina; 
	private NumericItem paginaDa;
	private NumericItem paginaA;	
	
	public ApponiTimbroForm() {
		this(null);
	}
	
	public ApponiTimbroForm(Record record) {
		
		instance = this;

		setKeepInParentRect(true);
		setNumCols(8);
		setColWidths(10, 10, 10, 10, 10, 10, "*", "*");
		setCellPadding(5);
		setWrapItemTitles(false);
		
		initPosizioneTimbroItem();
		initRotazioneTimbroItem();
		initTipoPaginaItem(record); 	
		initPaginaDaItem();
		initPaginaAItem();	

		/*Devo aggiungere la scelta dell intervallo solo se sto scegliendo le impostazioni di timbratura per un singolo file*/
		if(!record.getAttributeAsBoolean("scaricoZip")) {
			setFields(
					posizioneTimbro,
					rotazioneTimbro,
					tipoPagina, 	
					paginaDa,
					paginaA
				);
		}else {
			setFields(
					posizioneTimbro,
					rotazioneTimbro,
					tipoPagina
				);
		}
		loadDefault(record);
	}
	
	private void initPosizioneTimbroItem() {
		posizioneTimbro = new SelectItem("posizioneTimbro", "Posizione");
		posizioneTimbro.setWidth(150);
		posizioneTimbro.setStartRow(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("altoDx", "in alto a destra");
		lLinkedHashMap.put("altoSn", "in alto a sinistra");
		lLinkedHashMap.put("bassoDx", "in basso a destra");
		lLinkedHashMap.put("bassoSn", "in basso a sinistra");
		posizioneTimbro.setValueMap(lLinkedHashMap);		
	}
	
	private void initRotazioneTimbroItem() {
		rotazioneTimbro = new SelectItem("rotazioneTimbro", "Rotazione");
		rotazioneTimbro.setWidth(90);
		rotazioneTimbro.setStartRow(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("orizzontale", "orizzontale");
		lLinkedHashMap.put("verticale", "verticale");
		rotazioneTimbro.setValueMap(lLinkedHashMap);		
	}
	
	private void initTipoPaginaItem(Record record) {
		tipoPagina = new SelectItem("tipoPaginaTimbro", "Pagine da timbrare");
		tipoPagina.setWidth(150);
		tipoPagina.setStartRow(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("prima", "prima");
		lLinkedHashMap.put("ultima", "ultima");
		lLinkedHashMap.put("tutte", "tutte");
		if(!record.getAttributeAsBoolean("scaricoZip")) {
			lLinkedHashMap.put("intervallo", "intervallo");
		}
		tipoPagina.setValueMap(lLinkedHashMap);		
		tipoPagina.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {

				instance.markForRedraw();
			}
		});
	}
	
	private void initPaginaDaItem() {
		paginaDa = new NumericItem("paginaDa", "da", false);
		paginaDa.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return "intervallo".equals(tipoPagina.getValue());
			}
		});
		paginaDa.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {

				return "intervallo".equals(tipoPagina.getValue());
			}
		}));
	}
	
	private void initPaginaAItem() {
		paginaA = new NumericItem("paginaA", "a", false);
		paginaA.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return "intervallo".equals(tipoPagina.getValue());
			}
		});
		paginaA.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {

				return "intervallo".equals(tipoPagina.getValue());
			}
		}));
	}	
	
	public Record getRecord(){
		if (validate()){
			return new Record(getValues());			
		} 
		return null;
	}
	
	private void loadDefault(Record input) {
		Record record = new Record();
		record.setAttribute("rotazioneTimbro", input.getAttribute("rotazioneTimbroPref"));
		record.setAttribute("posizioneTimbro", input.getAttribute("posizioneTimbroPref"));
		record.setAttribute("tipoPaginaTimbro", input.getAttribute("tipoPaginaPref"));
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadOpzioniTimbroDocDefault");
		lGwtRestService.call(record, new ServiceCallback<Record>() {
			@Override
			public void execute(Record record) {
				editRecord(record);
			}
		});
	}

}