/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

public class ComboBox extends CustomDataSourceField {
	
	public ComboBox() {
		super(FieldType.TEXT);	
	}

	public ComboBox(String name) {
		super(name, FieldType.TEXT);
	}

	public ComboBox(String name, String title) {
		super(name, FieldType.TEXT, title);
	}
	
	public ComboBox(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.TEXT, title, lMap);
	}

	public ComboBox(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public ComboBox(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "combo_box");
		
		ComboBoxItemFiltrabile editorType = new ComboBoxItemFiltrabile();
		setEditorType(editorType);
	    
	}

}