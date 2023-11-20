/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;

public abstract class CustomItem extends CanvasItem {

	protected CustomItem _instance; 
	protected DynamicForm _form;
	protected Map<String, String> property = new HashMap<String, String>();

	public CustomItem(JavaScriptObject jsObj){
		super(jsObj);
		_instance = this;
	}

	public CustomItem(){
		_instance = this;
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				onInitCustomItem(item);
			}
		});			
//		setValidators(new AllOrNothingValidator());
	}
	
	public CustomItem(final Map<String, String> property){
		_instance = this;
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			public void onInit(FormItem item) {
				onInitCustomItem(item, property);
			}
		});			
//		setValidators(new AllOrNothingValidator());
	}
	
	public void onInitCustomItem(FormItem item) {
		CustomItem lCustomItem = buildObject(item.getJsObj());
		lCustomItem.disegna(item.getValue());
	}
	
	public void onInitCustomItem(FormItem item, Map<String, String> property) {
		CustomItem lCustomItem = buildObject(item.getJsObj());
		lCustomItem.property = property;
		lCustomItem.disegna(item.getValue());
	}

	/**
	 * !!!! ATTENZIONE!!!!
	 * @return o CustomItemFormField[] o ExtendedCustomItemFormField[]
	 * 
	 */
	public abstract FormItem[] getFormFields();
	
	/**
	 * Ha il compito di restituire l'instanza della classe, poichè
	 * la superclasse è astratta. Tipicamente
	 * 
	 * ItemExtendsFilterCanvasItem lItem = new ItemExtendsFilterCanvasItem(jsObj);
	 * return lFilterCanvasItem;
	 * @param jsObj
	 * @return
	 */
	public abstract CustomItem buildObject(JavaScriptObject jsObj);

	public static CustomItem getOrCreateRef(JavaScriptObject jsObj) {
		if(jsObj == null) return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if(obj != null) {
			obj.setJsObj(jsObj);
			return (CustomItem) obj;
		} else {
			return null;
		}
	}

	@Override
	public Boolean validate() {
		return _form.validate();
	}
	
	public void onChangeFilter(String clauseOperator) {
		// Di default non fa nulla, va sovrascritto nelle classi derivate se necessario
	}

	protected DynamicForm getFormToDraw(){
		DynamicForm lFormToDraw = new DynamicForm();
		lFormToDraw.setNumCols(10);
		lFormToDraw.setColWidths(1,1,1,1,1,1,1,1,1,1);
		lFormToDraw.setStopOnError(true);
		lFormToDraw.setMargin(-2);
		lFormToDraw.setOverflow(Overflow.VISIBLE);
		lFormToDraw.setWidth(10);
//		lFormToDraw.setCellBorder(1);
		return lFormToDraw;
	}

	protected void disegna(Object value) {
		_form = getFormToDraw();
		_form.setFields(getFormFields());
		Record lRecord = setDefaultValues(value);
		if (value != null && value instanceof JavaScriptObject) {		
			editRecord(new Record((JavaScriptObject) value));
		}
		_instance.redraw();
		_instance.storeValue(new Record(_form.getValues()));
		setCanvas(_form);
		setShouldSaveValue(true);	
		if (value == null){
			if (lRecord != null){
				editRecord(lRecord);
				_instance.redraw(); 
				_instance.storeValue(new Record(_form.getValues()));
				storeDefaultValues(lRecord);
			}
		}
	}
	
	protected void editNewRecord() {
		_form.editNewRecord();
	}
	
	protected void editRecord(Record record) {
		_form.editRecord(record);
	}

	protected void storeDefaultValues(Record lRecord) {
	}

	protected Record setDefaultValues(Object value) {
		return null;
	}

}
