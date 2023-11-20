/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DataConOraItem extends CustomItem {

	public DataConOraItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public DataConOraItem(){
		super();
	}
		
	public DataConOraItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new DataConOraItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {
		
		CustomItemFormField data = new CustomItemFormField("data", this);
		DateItem lDateItem = new DateItem();
//		lDateItem.setRequired(true);
		data.setEditorType(lDateItem);
//		data.setRequired(true);
		
		CustomItemFormField oraMinuti = new CustomItemFormField("oraMinuti", this);
		TextItem lTextItemOraMinuti = new TextItem();
		lTextItemOraMinuti.setMask("##:##");
		lTextItemOraMinuti.setWidth(80);
		lTextItemOraMinuti.setLength(5);
		// lTextItemOraMinuti.setDefaultValue(getOraMinutiDefaultValue());	
//		CustomValidator lValidator = new CustomValidator() {
//
//			@Override
//			protected boolean condition(Object value) {
//				if (value == null) return true;
//				String val = (String)value;
//				if (val.trim().equals("")) return false;
//				try {
//					String ora = val.substring(0,2);
//					String minuti = val.substring(2,4);
//					if (ora.startsWith("0")) ora = ora.substring(1);
//					if (minuti.startsWith("0")) minuti = minuti.substring(1);
//					int oraInt = Integer.valueOf(ora);
//					int minutiInt = Integer.valueOf(minuti);
//					if (oraInt<=23 && oraInt >= 0 && minutiInt >=0 && minutiInt <= 59) return true;
//					else return false;
//				} catch (Exception e) {
//					return false;
//				}
//			}
//		};
//		lValidator.setErrorMessage("Inserire un orario valido");
//		lTextItemOraMinuti.setValidators(lValidator);
//		lValidator.setValidateOnChange(true);
		oraMinuti.setEditorType(lTextItemOraMinuti);
		
		return new CustomItemFormField[]{data, oraMinuti};
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
	
//	protected void disegna(Object value) {
//		_form = getFormToDraw();
//		_form.setFields(getFormFields());
//		setDefaultValues();
//		if (value != null) {		
//			_form.editRecord(new Record((JavaScriptObject) value));
//		}
//		_instance.storeValue(new Record(_form.getValues()));
//		setCanvas(_form);
//		setShouldSaveValue(true);	
//		
//		_form = getFormToDraw();
//		_form.setFields(getFormFields());
//		Record lRecord = setDefaultValues();
//		if (value != null) {		
//			_form.editRecord(new Record((JavaScriptObject) value));
//		}
//		_instance.storeValue(new Record(_form.getValues()));
//		setCanvas(_form);
//		setShouldSaveValue(true);	
//		storeDefaultValues(lRecord);
//	}
	
	@Override
	protected Record setDefaultValues(Object value) {
		return null;
//		CanvasItem lCanvasItemAnno = CanvasItem.getOrCreateRef(getJsObj());
//		System.out.println("CanvasItem " + lCanvasItemAnno.getJsObj());
//		DynamicForm lDynamicForm = (DynamicForm)lCanvasItemAnno.getCanvas();
//		System.out.println("DynamicForm " + lDynamicForm);
//		CanvasItem lCanvasItem = lDynamicForm.getCanvasItem();
//		if (_form.getCanvasItem()!=null){
//			if (value == null) {
//				if (lCanvasItem.getName().equalsIgnoreCase("start")){
//					Record lRecord = new Record((JavaScriptObject)_instance.getValue());
//					lRecord.setAttribute("oraMinuti", "0000");
//					_form.editRecord(lRecord);
//					_instance.storeValue(new Record(_form.getValues()));
//					return new Record(_form.getValues());
//				} else if (lCanvasItem.getName().equalsIgnoreCase("end")){
//					Record lRecord = new Record((JavaScriptObject)_instance.getValue());
//					lRecord.setAttribute("oraMinuti", "2359");
//					_form.editRecord(lRecord);
//					_instance.storeValue(new Record(_form.getValues()));
//					return new Record(_form.getValues());
//				} if (lCanvasItem.getName().equalsIgnoreCase("value")){
//					if (lCanvasItem.getForm().getFields().length>1){
//						String typeField = lCanvasItem.getForm().getFields()[1].getName();
//						String type = (String)lCanvasItem.getForm().getValue(typeField);
//						if (type.equals("lessThan")){
//							Record lRecord = new Record((JavaScriptObject)_instance.getValue());
//							lRecord.setAttribute("oraMinuti", "2359");
//							_form.editRecord(lRecord);
//							_instance.storeValue(new Record(_form.getValues()));
//							return new Record(_form.getValues());
//						} else {
//							Record lRecord = new Record((JavaScriptObject)_instance.getValue());
//							lRecord.setAttribute("oraMinuti", "0000");
//							_form.editRecord(lRecord);
//							_instance.storeValue(new Record(_form.getValues()));
//							return new Record(_form.getValues());
//						}
//					}
//					return null;
//				}
//			}
//		}
//		return null;
	}
	
	@Override
	protected void storeDefaultValues(Record lRecord) {
//		if (lRecord!=null){
//		System.out.println("Record " + lRecord.getJsObj());
//		CanvasItem lCanvasItemAnno = CanvasItem.getOrCreateRef(getJsObj());
//		System.out.println("CanvasItem " + lCanvasItemAnno.getJsObj());
//		DynamicForm lDynamicForm = (DynamicForm)lCanvasItemAnno.getCanvas();
//		System.out.println("DynamicForm " + lDynamicForm);
//		CanvasItem lCanvasItem = lDynamicForm.getCanvasItem();
//		System.out.println("lCanvasItem " + lCanvasItem);
//		lCanvasItem.storeValue(lRecord);
//		}
	}

}
