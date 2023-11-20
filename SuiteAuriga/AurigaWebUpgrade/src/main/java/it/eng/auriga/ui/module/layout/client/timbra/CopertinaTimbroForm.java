/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class CopertinaTimbroForm extends DynamicForm {
	
	private CopertinaTimbroForm instance;

	private SelectItem posizioneTimbroItem;
	private SelectItem rotazioneTimbroItem;
	private NumericItem numeroAllegatiItem;
	private HiddenItem testoIntestazione;	
	private HiddenItem testo;
	private HiddenItem idUd;
	private HiddenItem idDoc;
	private HiddenItem numeroAllegato;
	private HiddenItem tipoTimbroCopertina;
	private HiddenItem posizioneIntestazione;
	private HiddenItem infoFile;
	
	public CopertinaTimbroForm(CopertinaTimbroBean lCopertinaTimbroBean,Boolean isMultiplo) {
		
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
		initHiddenItems();
		initNumeroAllegatiItem();
		if(isMultiplo != null && isMultiplo){
			setFields(
					numeroAllegatiItem,
					rotazioneTimbroItem, 
					posizioneTimbroItem,
					idUd,
					idDoc,
					testoIntestazione,
					testo,
					numeroAllegato,
					tipoTimbroCopertina,
					posizioneIntestazione,
					infoFile
				);
		}else{
			setFields(
					posizioneTimbroItem,
					rotazioneTimbroItem, 		
					idUd,
					idDoc,
					testoIntestazione,
					testo,
					numeroAllegato,
					tipoTimbroCopertina,
					posizioneIntestazione,
					infoFile
				);
		}
	
		loadDefault(lCopertinaTimbroBean);
	}
	
	public Record getRecord(){
		if (validate()){
			return new Record(getValues());			
		} 
		return null;
	}
	
	/**
	 * METODO RELATIVO AL RECUPERO DELLE PREFERENCE DEI BARCODE
	 */
	private void loadDefault(CopertinaTimbroBean lCopertinaDaTimbrareBean) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadCopertinaDefaultDataSource");
		lGwtRestService.call(lCopertinaDaTimbrareBean, new ServiceCallback<Record>() {
			@Override
			public void execute(Record record) {
				editRecord(record);
			}
		});
	}
	
	private void initNumeroAllegatiItem(){
		numeroAllegatiItem = new NumericItem("numeroAllegati", I18NUtil.getMessages().protocollazione_copertinaTimbroNrAllegato());
		numeroAllegatiItem.setWidth(80);
		numeroAllegatiItem.setStartRow(true);
		numeroAllegatiItem.setRequired(true);
	}
	
	private void initPosizioneTimbroItem() {
		posizioneTimbroItem = new SelectItem("posizioneTimbro", "Posizione");
		posizioneTimbroItem.setWidth(150);
		posizioneTimbroItem.setStartRow(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("altoDx", "in alto a destra");
		lLinkedHashMap.put("altoSn", "in alto a sinistra");
		lLinkedHashMap.put("bassoDx", "in basso a destra");
		lLinkedHashMap.put("bassoSn", "in basso a sinistra");
		posizioneTimbroItem.setValueMap(lLinkedHashMap);		
	}
	
	private void initRotazioneTimbroItem() {
		rotazioneTimbroItem = new SelectItem("rotazioneTimbro", "Rotazione");
		rotazioneTimbroItem.setWidth(90);
		rotazioneTimbroItem.setStartRow(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("orizzontale", "orizzontale");
		lLinkedHashMap.put("verticale", "verticale");
		rotazioneTimbroItem.setValueMap(lLinkedHashMap);		
	}
	
	private void initHiddenItems() {

		idUd = new HiddenItem("idUd");
		idDoc = new HiddenItem("idDoc");
		tipoTimbroCopertina = new HiddenItem("tipoTimbroCopertina");
		numeroAllegato = new HiddenItem("numeroAllegato");
		posizioneIntestazione = new HiddenItem("posizioneIntestazione");
		testoIntestazione = new HiddenItem("testoIntestazione");
		testo = new HiddenItem("testo");
		infoFile = new HiddenItem("infoFile");
	}

}
