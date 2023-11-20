/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.DataConOraEstesaItem;

public class DataConOraEstesa extends CustomDataSourceField {
	
	public DataConOraEstesa(){
		super(FieldType.CUSTOM);		
	}
	
	public DataConOraEstesa(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public DataConOraEstesa(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public DataConOraEstesa(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public DataConOraEstesa(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		setAttribute("customType", "data_con_ora_estesa");		
		DataConOraEstesaItem editorType = new DataConOraEstesaItem(); 
		setEditorType(editorType);
	}

}
