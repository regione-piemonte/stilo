/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.ScadenzaItem;

public class ScadenzaFilterItem extends CustomDataSourceField {
	
	public ScadenzaFilterItem(){
		super(FieldType.CUSTOM);		
	}
	
	public ScadenzaFilterItem(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public ScadenzaFilterItem(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public ScadenzaFilterItem(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public ScadenzaFilterItem(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		setAttribute("customType", "scadenza");		
		ScadenzaItem editorType = new ScadenzaItem(); 
		setEditorType(editorType);
	}

}