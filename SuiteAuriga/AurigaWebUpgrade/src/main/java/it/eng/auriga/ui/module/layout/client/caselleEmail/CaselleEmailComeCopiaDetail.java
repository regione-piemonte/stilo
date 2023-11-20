/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;

public class CaselleEmailComeCopiaDetail extends CaselleEmailDetail {
	
	protected CaselleEmailComeCopiaDetail instance;
	
	protected DynamicForm creaComeCopiaForm;
	
	private SelectItem dominioItem;
	private HiddenItem idSpAooItem;
	private TextItem nuovoIndirizzoEmailItem;
//	private CheckboxItem flgAttivaScaricoItem;

	public CaselleEmailComeCopiaDetail(String nomeEntita, Record record) {
		// TODO Auto-generated constructor stub
		super(nomeEntita, record);
		
		instance = this;		
		
		creaComeCopiaForm = new DynamicForm();
		creaComeCopiaForm.setValuesManager(vm);
		creaComeCopiaForm.setWidth("100%");  
		creaComeCopiaForm.setHeight("5");  	
		creaComeCopiaForm.setPadding(5);
		creaComeCopiaForm.setNumCols(6);
		creaComeCopiaForm.setColWidths(500, 1, 1, 1, "*", "*");
		creaComeCopiaForm.setWrapItemTitles(true);
		
		final GWTRestDataSource dominiDS = new GWTRestDataSource("LoadComboEnteAOOCasellaDataSource", "key", FieldType.TEXT);
		// SELEZIONA TIPO ORGANIGRAMMA
		dominioItem = new SelectItem("dominio", I18NUtil.getMessages().caselleEmail_detail_dominioItem_title()) {
			@Override
			public void onOptionClick(Record record) {
				
				creaComeCopiaForm.setValue("idSpAoo", record.getAttribute("key"));	
			}
		};
		dominioItem.setValueField("key");
		dominioItem.setDisplayField("value");
		dominioItem.setOptionDataSource(dominiDS);
		dominioItem.setCachePickListResults(false);
		dominioItem.setStartRow(true);
		dominioItem.setClearable(false);
		dominioItem.setAttribute("obbligatorio", true);
		dominioItem.addDataArrivedHandler(new DataArrivedHandler() {			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				RecordList data = event.getData();
				if(data != null && data.getLength() == 1){
					Record record = data.get(0);
					creaComeCopiaForm.setValue("idSpAoo", record.getAttribute("key"));	
					dominioItem.hide();
				} else {
					dominioItem.show();
				}					
			};
		});
		
		idSpAooItem = new HiddenItem("idSpAoo");
		
		nuovoIndirizzoEmailItem = new TextItem("nuovoIndirizzoEmail", I18NUtil.getMessages().caselleEmail_detail_nuovoIndirizzoEmailItem_title());		
		nuovoIndirizzoEmailItem.setStartRow(true);
		nuovoIndirizzoEmailItem.setRequired(true);
		
//		SpacerItem spacerItem = new SpacerItem();
//		spacerItem.setStartRow(true);
//		spacerItem.setColSpan(1);
		
//		flgAttivaScaricoItem = new CheckboxItem("flgAttivaScarico", I18NUtil.getMessages().caselleEmail_detail_flgAttivaScarico_title());
//		flgAttivaScaricoItem.setValue(false);
//		flgAttivaScaricoItem.setColSpan(1);
//		flgAttivaScaricoItem.setWidth("*");	
		
		creaComeCopiaForm.setItems(dominioItem, idSpAooItem, nuovoIndirizzoEmailItem/*, spacerItem, flgAttivaScaricoItem*/);
		
		setMembers(hiddenForm, creaComeCopiaForm, parametriForm);		
	}
	
	@Override
	public void editRecord(Record record) {
		
		RecordList parametriCasella = record.getAttributeAsRecordList("parametriCasella");		
		if(parametriCasella != null) {
			RecordList nuoviParametriCasella = new RecordList();
			for(int i = 0; i < parametriCasella.getLength(); i++) {				
				Record param = new Record(parametriCasella.get(i).getJsObj());		
				if("mail.username".equals(param.getAttribute("nome")) || 
				   "mail.password".equals(param.getAttribute("nome")) || 
				   "mail.imap.user".equals(param.getAttribute("nome"))) {
					param.setAttribute("valore", (String) null); 
				}
				nuoviParametriCasella.add(param);
			}
			record.setAttribute("parametriCasella", nuoviParametriCasella);
		}				
		super.editRecord(record);
	}
	
	public Record getRecordToSave() {		
		Record record = super.getRecordToSave();
		record.setAttribute("idSpAoo", creaComeCopiaForm.getValue("idSpAoo"));
		record.setAttribute("nuovoIndirizzoEmail", creaComeCopiaForm.getValue("nuovoIndirizzoEmail"));
//		record.setAttribute("flgAttivaScarico", creaComeCopiaForm.getValue("flgAttivaScarico"));
		return record;
	}
	
	@Override
	public boolean customValidate() {
		boolean valid = true;
		if(dominioItem.isVisible() && (dominioItem.getValue() == null || "".equals(dominioItem))) {
			creaComeCopiaForm.setShowInlineErrors(true);
			Map<String, String> lMap = new HashMap<String, String>();
			lMap.put("domini", "Campo obbligatorio");
			creaComeCopiaForm.setErrors(lMap);
			valid = false;			
		}	
		return valid;		
	}
	
}

