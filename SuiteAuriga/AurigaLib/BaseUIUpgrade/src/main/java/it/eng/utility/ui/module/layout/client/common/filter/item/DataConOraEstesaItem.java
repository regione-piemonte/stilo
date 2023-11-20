/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DataConOraEstesaItem extends CustomItem {

	public DataConOraEstesaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public DataConOraEstesaItem(){
		super();
	}
		
	public DataConOraEstesaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new DataConOraEstesaItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {
		
		CustomItemFormField data = new CustomItemFormField("data", this);
		DateItem lDateItem = new DateItem();
//		lDateItem.setRequired(true);
		data.setEditorType(lDateItem);
		data.setHidden(getOperator() != null && getOperator().equals(OperatorId.LAST_N_DAYS));

//		data.setRequired(true);
		
		CustomItemFormField oraMinuti = new CustomItemFormField("oraMinuti", this);
		TextItem lTextItemOraMinuti = new TextItem();
		lTextItemOraMinuti.setMask("##:##");
		lTextItemOraMinuti.setWidth(80);
		lTextItemOraMinuti.setLength(5);
		oraMinuti.setEditorType(lTextItemOraMinuti);
		oraMinuti.setHidden(getOperator() != null && getOperator().equals(OperatorId.LAST_N_DAYS));
		
		CustomItemFormField nGiorni = new CustomItemFormField("nGiorni", this);
		NumericItem lNumericItemNGiorni = new NumericItem(false);
		lNumericItemNGiorni.setWidth(80);
		lNumericItemNGiorni.setLength(5);
		nGiorni.setEditorType(lNumericItemNGiorni);
		nGiorni.setHidden(getOperator() != null && !getOperator().equals(OperatorId.LAST_N_DAYS));

		
		return new CustomItemFormField[]{data, oraMinuti, nGiorni};
	}
	
	public String getOraMinutiDefaultValue() {
		if (getName().equalsIgnoreCase("start")) {
			return "0000";
		} else if (getName().equalsIgnoreCase("end")) {
			return "2359";
		} if (getName().equalsIgnoreCase("value")) {
			String operator = (String) getForm().getValue("operator");
			if(operator != null) {
				if (operator.equals("lessThan")){
					return "2359";
				} else if (operator.equals("greaterThan")){
					return "0000";
				}			
			}
		}
		return null;
	}
	
	@Override
	protected void editRecord(Record record) {		
		_form.editRecord(record);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				if(_form.getValueAsString("oraMinuti") == null || _form.getValueAsString("oraMinuti").trim().equals("") || _form.getValueAsString("oraMinuti").length() != 4) {
					_form.setValue("oraMinuti", getOraMinutiDefaultValue());
				}
			}
		});
	}
	
	@Override
	public void onChangeFilter(String clauseOperator) {
		super.onChangeFilter(clauseOperator);
		setOraMinutiDefaultValue(clauseOperator);
	}
	
	@Override
	protected void disegna(Object value) {
		super.disegna(value);
		setOraMinutiDefaultValue(null);
	}

	private  void setOraMinutiDefaultValue(String operator) {
		String value = null;
		if(_form.getItem("oraMinuti") != null) { 
			if (getName().equalsIgnoreCase("start")) {
				value =  "0000";
			} else if (getName().equalsIgnoreCase("end")) {
				value = "2359";
			} else if (getName().equalsIgnoreCase("value")) {
				if(operator != null) {
					if (operator.equals("lessThan")){
						value = "2359";
					} else if (operator.equals("greaterThan")){
						value = "0000";
					}			
				}
			}
		}	
		if (value != null) {
			_form.getItem("oraMinuti").setDefaultValue(value);
		}
	}
	
	@Override
	protected Record setDefaultValues(Object value) {
		return null;
	}
	

}
