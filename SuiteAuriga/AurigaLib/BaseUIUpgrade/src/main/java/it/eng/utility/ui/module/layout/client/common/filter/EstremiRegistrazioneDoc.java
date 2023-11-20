/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.EstremiRegistrazioneItem;

public class EstremiRegistrazioneDoc extends CustomDataSourceField {
	
	public EstremiRegistrazioneDoc(){
		super(FieldType.CUSTOM);		
	}
	
	public EstremiRegistrazioneDoc(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public EstremiRegistrazioneDoc(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public EstremiRegistrazioneDoc(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public EstremiRegistrazioneDoc(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "estremi_registrazione_doc");		

		EstremiRegistrazioneItem editorType = new EstremiRegistrazioneItem(); 
		setEditorType(editorType);

//		setValidOperators(OperatorId.EQUALS);

	}

}
