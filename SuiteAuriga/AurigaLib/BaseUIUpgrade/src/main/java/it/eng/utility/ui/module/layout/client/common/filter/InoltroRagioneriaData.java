/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.InoltroRagioneriaItem;

/**
 * 
 * @author dbe4235
 *
 */

public class InoltroRagioneriaData extends CustomDataSourceField {
	
	public InoltroRagioneriaData() {
		super(FieldType.CUSTOM);
	}

	public InoltroRagioneriaData(String name) {
		super(name, FieldType.CUSTOM);
	}

	public InoltroRagioneriaData(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public InoltroRagioneriaData(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public InoltroRagioneriaData(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public InoltroRagioneriaData(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {		
		setAttribute("customType", "inoltro_ragioneria_data");		
		
		InoltroRagioneriaItem editorType = new InoltroRagioneriaItem("LoadComboInoltroRagioneriaDataDataSource"); 		
		setEditorType(editorType);
	}

}