/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;

public class Anno extends CustomDataSourceField {
	
	public Anno() {
		super(FieldType.TEXT);	
	}

	public Anno(String name) {
		super(name, FieldType.TEXT);
	}

	public Anno(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public Anno(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public Anno(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {		
		setAttribute("customType", "anno");		
//		setValidOperators(new OperatorId[] { OperatorId.EQUALS });  
		
		AnnoItem editorType = new AnnoItem(); 
		setEditorType(editorType);
	}

}