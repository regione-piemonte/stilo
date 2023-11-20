/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridField;

public class DelegheVsUtentiCanvas extends ReplicableCanvas{

	private FilteredSelectItemWithDisplay utentiItem;	
	private DateItem dataVldDalItem;	
	private DateItem dataVldFinoAlItem;	
	private TextItem motivoItem;	

	private ReplicableCanvasForm mDynamicForm;
	
	@Override
	public void disegna() {		
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
				
		SelectGWTRestDataSource lGwtRestDataSourceUtenti = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT, new String[]{"cognomeNome"/*, "username"*/}, true);
		lGwtRestDataSourceUtenti.addParam("idUtenteToExclude", String.valueOf(Layout.getUtenteLoggato().getIdUser()));
		
		utentiItem = new FilteredSelectItemWithDisplay("idUtente", lGwtRestDataSourceUtenti){
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);	
				mDynamicForm.setValue("codiceRapido", record.getAttributeAsString("codice"));								
			}			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idUtente", "");
				mDynamicForm.setValue("codiceRapido", "");						
			};		
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idUtente", "");
					mDynamicForm.setValue("codiceRapido", "");	
				}
			}
		};		
		ListGridField utentiCodiceField = new ListGridField("codice", "Cod.");
		utentiCodiceField.setWidth(80);		
		ListGridField utentiCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//		
		ListGridField utentiUsernameField = new ListGridField("username", "Username");				
		utentiUsernameField.setWidth(170);		
		utentiItem.setPickListFields(utentiCodiceField, utentiCognomeNomeField, utentiUsernameField);
		utentiItem.setFilterLocally(true);
		utentiItem.setValueField("idUtente");  		
		utentiItem.setTitle("Verso");
		utentiItem.setOptionDataSource(lGwtRestDataSourceUtenti); 
		utentiItem.setWidth(380);
		// ATTENZIONE: il setPickListWidth() va sempre dopo il setWidth() altrimenti viene sovrascritto
		utentiItem.setPickListWidth(600);
		utentiItem.setRequired(true);
		utentiItem.setClearable(true);
		utentiItem.setShowIcons(true);
		utentiItem.setAutoFetchData(false);
		utentiItem.setAlwaysFetchMissingValues(true);
		utentiItem.setFetchMissingValues(true);
		if(AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utentiItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utentiItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});
		
		dataVldDalItem = new DateItem("dataVldDal", "dal");		
		dataVldDalItem.setColSpan(1);		
		
		dataVldFinoAlItem = new DateItem("dataVldFinoAl", "al");
		dataVldFinoAlItem.setColSpan(1);			
		
		motivoItem = new TextItem("motivo", "Motivo/i");
		motivoItem.setStartRow(true);		
		motivoItem.setWidth(680);
		motivoItem.setColSpan(5);		
						
		mDynamicForm.setFields(utentiItem, dataVldDalItem, dataVldFinoAlItem, motivoItem);
		
		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100");
		
		addChild(mDynamicForm);
	}	
	
	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		
		if (record.getAttribute("idUtente") != null && !"".equals(record.getAttributeAsString("idUtente"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idUtente"), record.getAttribute("descrizione"));
			utentiItem.setValueMap(valueMap);
		}
		
		GWTRestDataSource utentiDS = (GWTRestDataSource) utentiItem.getOptionDataSource();
		if (record.getAttribute("idUtente") != null && !"".equals(record.getAttributeAsString("idUtente"))) {
			utentiDS.addParam("idUtente", record.getAttributeAsString("idUtente"));
		} else {
			utentiDS.addParam("idUtente", null);
		}
		utentiItem.setOptionDataSource(utentiDS);
	}
		
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}

}
