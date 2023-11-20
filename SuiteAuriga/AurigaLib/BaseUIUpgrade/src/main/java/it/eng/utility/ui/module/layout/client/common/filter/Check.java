/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;

public class Check extends CustomDataSourceField {
	
	public Check() {
		super(FieldType.BOOLEAN);
	}

	public Check(String name) {
		super(name, FieldType.BOOLEAN);		
	}

	public Check(String name, String title) {
		super(name, FieldType.BOOLEAN, title);		
	}

	public Check(String name, String title, int length) {
		super(name, FieldType.BOOLEAN, title, length);		
	}

	public Check(String name, String title, int length, boolean required) {
		super(name, FieldType.BOOLEAN, title, length, required);		
	}
	
	protected void init() {
		
		setAttribute("customType", "check");		
		
		CheckboxItem lCheckboxItem = new CheckboxItem();
		lCheckboxItem.setShowTitle(false);
		lCheckboxItem.setLabelAsTitle(true);
		lCheckboxItem.setTitle("");
//		lCheckboxItem.setInitHandler(new FormItemInitHandler() {			
//			@Override
//			public void onInit(FormItem item) {
//				System.out.println("VALORE ### " + item.getValue());
//				CheckboxItem lItem = (CheckboxItem) item;
//				if (item.getValue()==null || item.getValue()==Boolean.FALSE || item.getValue()==Boolean.TRUE){
//					lItem.setDefaultValue(true);
//				} else {
//					lItem.setDefaultValue(false);
//				}
//			}
//		});
		
//		setValidOperators(new OperatorId[] { OperatorId.EQUALS });          
		
		setEditorType(lCheckboxItem);
	}

}
