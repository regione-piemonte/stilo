/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class TimbraForm extends DynamicForm {
	
	private TimbraForm instance;

	private SelectItem posizioneTimbro;
	private SelectItem rotazioneTimbro;
	private SelectItem tipoPagina; 	
	private NumericItem paginaDa;
	private NumericItem paginaA;	
	private HiddenItem testoIntestazione;	
	private HiddenItem testo;
	private HiddenItem nomeFile;
	private HiddenItem uri;
	private HiddenItem remote;
	private HiddenItem mimetype;
	private HiddenItem idUd;
	private HiddenItem idDoc;
	private HiddenItem tipoTimbro;
	private HiddenItem skipScelteOpzioniCopertina;
	private HiddenItem finalita;
	boolean allPages=false;

	public TimbraForm(FileDaTimbrareBean lFileDaTimbrareBean) {
		
		instance = this;

		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(8);
		setColWidths(10, 10, 10, 10, 10, 10, "*", "*");
		setCellPadding(5);
		setWrapItemTitles(false);
		
		initPosizioneTimbroItem();
		initRotazioneTimbroItem();
		allPages = lFileDaTimbrareBean.getAttribute("finalita") != null && lFileDaTimbrareBean.getAttribute("finalita").equals("CONFORMITA_ORIG_CARTACEO");
		initTipoPaginaItem(allPages); 	
		initPaginaDaItem();
		initPaginaAItem();	
		
		initHiddenItems();

		setFields(
			posizioneTimbro,
			rotazioneTimbro,
			tipoPagina, 	
			paginaDa,
			paginaA,	
			testoIntestazione,
			testo,
			nomeFile, 
			uri, 
			remote, 
			mimetype,
			idUd,
			idDoc,
			tipoTimbro,
			skipScelteOpzioniCopertina,
			finalita
		);
		
		loadDefault(lFileDaTimbrareBean);

	}

	public Record getRecord(){
		if (validate()){
			return new Record(getValues());			
		} 
		return null;
	}

	private void loadDefault(FileDaTimbrareBean lFileDaTimbrareBean) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {
			@Override
			public void execute(Record record) {
				if(allPages) {
					record.setAttribute("tipoPagina", "tutte");
				}
				editRecord(record);
			}
		});
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
	
	private void initTipoPaginaItem(boolean allPages) {
		tipoPagina = new SelectItem("tipoPagina", "Pagine da timbrare");
		tipoPagina.setWidth(150);
		tipoPagina.setStartRow(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();

		if (allPages) {
			lLinkedHashMap.put("tutte", "tutte");
			tipoPagina.setValueMap(lLinkedHashMap);
		} else {
			lLinkedHashMap.put("prima", "prima");
			lLinkedHashMap.put("ultima", "ultima");
			lLinkedHashMap.put("tutte", "tutte");
			lLinkedHashMap.put("intervallo", "intervallo");
			tipoPagina.setValueMap(lLinkedHashMap);		
		}
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
	
	private void initHiddenItems() {
		testoIntestazione = new HiddenItem("testoIntestazione");
		testo = new HiddenItem("testo");
		nomeFile = new HiddenItem("nomeFile");
		uri = new HiddenItem("uri");
		remote = new HiddenItem("remote");
		mimetype = new HiddenItem("mimetype");
		idUd = new HiddenItem("idUd");
		idDoc = new HiddenItem("idDoc");
		tipoTimbro = new HiddenItem("tipoTimbro");
		skipScelteOpzioniCopertina = new HiddenItem("skipScelteOpzioniCopertina");
		finalita = new HiddenItem("finalita");
	}
	
}
