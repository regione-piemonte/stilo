/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.DataConOraItem;

public class DateConOraFilterItem extends CustomDataSourceField {
	
	public DateConOraFilterItem(){
		super(FieldType.CUSTOM);		
	}
	
	public DateConOraFilterItem(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public DateConOraFilterItem(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public DateConOraFilterItem(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public DateConOraFilterItem(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		setAttribute("customType", "data_con_ora");		
		DataConOraItem editorType = new DataConOraItem(); 
		setEditorType(editorType);
	}

}
