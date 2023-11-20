/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.layout.client.common.filter.item.FullTextItem;

public class StringaFullText extends CustomDataSourceField {
	
	public StringaFullText() {
		super(FieldType.CUSTOM);
	}

	public StringaFullText(String name) {
		super(name, FieldType.CUSTOM);
	}

	public StringaFullText(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public StringaFullText(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public StringaFullText(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public StringaFullText(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "stringa_full_text");		
		
		FullTextItem editorType = new FullTextItem(property);
		setEditorType(editorType);
	}
	
	protected void setOperators() {
		
		String categoria = property.get("categoria") != null ? property.get("categoria") : "";		
		
		if (UserInterfaceFactory.getParametroDBAsBoolean("HIDE_FILTER_FULLTEXT_REP_DOC") && "REP_DOC".equals(categoria)) {
			setValidOperators(OperatorId.RECURSIVELY); 
		} else {
			super.setOperators();
		}       			
    }

}
