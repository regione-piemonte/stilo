/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class IstruttoreProcCanvas extends ReplicableCanvas {

	private HiddenItem nomeUtenteIstruttoreProcItem; 
	private FilteredSelectItemWithDisplay utenteIstruttoreProcItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public IstruttoreProcCanvas(IstruttoreProcItem item) {
		super(item);
	}

	public IstruttoreProcCanvas(IstruttoreProcItem item, HashMap<String, String> parameters) {
		// TODO Auto-generated constructor stub
		super(item, parameters);		
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);	

		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		HashMap<String, String> params =  getParams();
		String listaIdProcessType = params.get("listaIdProcessType");

		SelectGWTRestDataSource utenteIstruttoreProcDS = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[]{"cognomeNome"/*, "username"*/}, true);
//		utenteIstruttoreProcDS.addParam("flgSoloTributi", "1");
		utenteIstruttoreProcDS.addParam("listaIdProcessType", listaIdProcessType);
		
		nomeUtenteIstruttoreProcItem = new HiddenItem("nomeUtenteIstruttoreProc");
		
		utenteIstruttoreProcItem = new FilteredSelectItemWithDisplay("idUtenteIstruttoreProc", utenteIstruttoreProcDS){
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);	
//				mDynamicForm.setValue("idUtenteIstruttoreProc", record.getAttributeAsString("idUtente"));
				mDynamicForm.setValue("nomeUtenteIstruttoreProc", record.getAttributeAsString("cognomeNome"));				
			}			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idUtenteIstruttoreProc", "");		
				mDynamicForm.setValue("nomeUtenteIstruttoreProc", "");		
			};	
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idUtenteIstruttoreProc", "");		
					mDynamicForm.setValue("nomeUtenteIstruttoreProc", "");	
				}
			}		
		};		
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//		
		ListGridField utentiUsernameField = new ListGridField("username", "Username");				
		utentiUsernameField.setWidth(80);		
		utenteIstruttoreProcItem.setPickListFields(utentiCognomeNomeField, utentiUsernameField);		
		utenteIstruttoreProcItem.setFilterLocally(true);
		utenteIstruttoreProcItem.setValueField("idUtente");  		
		utenteIstruttoreProcItem.setShowTitle(false);
		utenteIstruttoreProcItem.setOptionDataSource(utenteIstruttoreProcDS); 
		utenteIstruttoreProcItem.setWidth(300);
		utenteIstruttoreProcItem.setClearable(true);
		utenteIstruttoreProcItem.setShowIcons(true);
		utenteIstruttoreProcItem.setAutoFetchData(false);
		utenteIstruttoreProcItem.setAlwaysFetchMissingValues(true);
		utenteIstruttoreProcItem.setFetchMissingValues(true);
		if(AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utenteIstruttoreProcItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utenteIstruttoreProcItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
	
		
		mDynamicForm.setFields(utenteIstruttoreProcItem, nomeUtenteIstruttoreProcItem);
		

		addChild(mDynamicForm);

	}

	@Override
	public void editRecord(Record record) {
		
		mDynamicForm.clearErrors(true);
		if (record.getAttribute("tipoIstruttoreProc") != null) {
			if ("UT".equalsIgnoreCase(record.getAttribute("tipoIstruttoreProc"))) {
				if (record.getAttribute("idUtenteIstruttoreProc") != null && !"".equals(record.getAttributeAsString("idUtenteIstruttoreProc"))) {
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
					valueMap.put(record.getAttribute("idUtenteIstruttoreProc"), record.getAttribute("nomeUtenteIstruttoreProc"));
					utenteIstruttoreProcItem.setValueMap(valueMap);
				}
				
				GWTRestDataSource utentiDS = (GWTRestDataSource) utenteIstruttoreProcItem.getOptionDataSource();
				if (record.getAttribute("idUtenteIstruttoreProc") != null && !"".equals(record.getAttributeAsString("idUtenteIstruttoreProc"))) {
					utentiDS.addParam("idUtente", record.getAttributeAsString("idUtenteIstruttoreProc"));
				} else {
					utentiDS.addParam("idUtente", null);
				}
				utenteIstruttoreProcItem.setOptionDataSource(utentiDS);
			}
		}
		super.editRecord(record);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

}
