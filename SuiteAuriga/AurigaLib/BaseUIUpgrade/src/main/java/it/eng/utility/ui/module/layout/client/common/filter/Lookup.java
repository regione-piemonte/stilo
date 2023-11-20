/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.LookupItem;

public class Lookup extends CustomDataSourceField {
	
	public Lookup() {
		super(FieldType.CUSTOM);
	}

	public Lookup(String name) {
		super(name, FieldType.CUSTOM);
	}

	public Lookup(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public Lookup(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public Lookup(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public Lookup(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "lookup");		
		
		LookupItem editorType = new LookupItem(property);
		setEditorType(editorType);
        
	}

}
