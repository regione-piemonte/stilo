/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.StringaFullTextMistaItem;

public class StringaFullTextMista extends CustomDataSourceField {
	
	public StringaFullTextMista() {
		super(FieldType.CUSTOM);
	}

	public StringaFullTextMista(String name) {
		super(name, FieldType.CUSTOM);
	}

	public StringaFullTextMista(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public StringaFullTextMista(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public StringaFullTextMista(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public StringaFullTextMista(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
				
		setAttribute("customType", "stringa_full_text_mista");
		
		StringaFullTextMistaItem editorType = new StringaFullTextMistaItem();
		editorType.setWidth(200);
		
		setEditorType(editorType);
		
	}
	
}
