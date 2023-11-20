/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.InseritoInOdGDiscussioneSedutaItem;

public class InseritoInOdGDiscussioneSeduta extends CustomDataSourceField {
	
	public InseritoInOdGDiscussioneSeduta() {
		super(FieldType.CUSTOM);
	}

	public InseritoInOdGDiscussioneSeduta(String name) {
		super(name, FieldType.CUSTOM);
	}

	public InseritoInOdGDiscussioneSeduta(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public InseritoInOdGDiscussioneSeduta(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public InseritoInOdGDiscussioneSeduta(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public InseritoInOdGDiscussioneSeduta(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "inserito_in_odg_discussione_seduta");		
		
		InseritoInOdGDiscussioneSedutaItem editorType = new InseritoInOdGDiscussioneSedutaItem();
		setEditorType(editorType);
        
	}

}
