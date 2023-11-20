/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

public class CustomItemFormField extends FormItem {

	private CustomItem _owner;
	
	public CustomItemFormField(String name, CustomItem lFilterCanvasItem){
		this(name, null, lFilterCanvasItem);
	}
	
	public CustomItemFormField(String name, String title, CustomItem lFilterCanvasItem){
		super(name);
		if(title != null && !"".equals(title)) {
			setTitle(title);
		} else {
			setShowTitle(false);			
		}
		setWrapTitle(false);
		_owner = lFilterCanvasItem;
		addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				manageChanged(event);
			}
		});
	}
	
	public CustomItemFormField(FormItem lFormItem){
		super(lFormItem.getJsObj());
	}
	
	// ottavio
	public CustomItemFormField(FormItem lFormItem, String title, CustomItem lFilterCanvasItem){
		super(lFormItem.getJsObj());
		if(title != null && !"".equals(title)) {
			setTitle(title);
		} else {
			setShowTitle(false);			
		}
		_owner = lFilterCanvasItem;		
		addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				manageChanged(event);
			}
		});
	}
	
	protected void manageChanged(ChangedEvent event) {
//		event.getForm().clearErrors(true);
//		_owner.setAttribute("valido", event.getForm().validate());
//		event.getForm().focusInItem(event.getItem());
		Object value = event.getValue();
		value = manageValue(value);
		updateInternalValue(event.getItem().getName(), event.getValue());
	}

	/**
	 * Funzione che ha il compito di restituire il valore modificato.
	 * 
	 * Se ne fa l'override se si ha bisogno di modificare il valore prima di restituirlo
	 * al canvas per il salvataggio
	 * 
	 * @param value
	 * @return
	 */
	protected Object manageValue(Object value) {
		return value;
	}

	public void updateInternalValue(String name, Object value){
		DynamicForm lform1 = (DynamicForm)_owner.getCanvas();
		CanvasItem lCanvasItem = lform1.getCanvasItem();
		Record lRecord;
		if (lCanvasItem.getValue()==null){
			lRecord = new Record();
		} else lRecord = new Record((JavaScriptObject) lCanvasItem.getValue());
		lRecord.setAttribute(name, value);
		lCanvasItem.storeValue(lRecord);
	}
	
}
