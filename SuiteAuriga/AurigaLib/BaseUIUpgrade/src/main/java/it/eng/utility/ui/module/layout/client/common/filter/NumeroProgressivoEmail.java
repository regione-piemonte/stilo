/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.NumeroProgressivoEmailItem;

public class NumeroProgressivoEmail extends CustomDataSourceField {
	
	public NumeroProgressivoEmail() {
		super(FieldType.CUSTOM);
	}

	public NumeroProgressivoEmail(String name) {
		super(name, FieldType.CUSTOM);
	}

	public NumeroProgressivoEmail(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public NumeroProgressivoEmail(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public NumeroProgressivoEmail(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public NumeroProgressivoEmail(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "numero_progressivo_email");		
		
		NumeroProgressivoEmailItem editorType = new NumeroProgressivoEmailItem(property);
		setEditorType(editorType);
        
	}

}
