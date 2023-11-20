/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

/**
 * 
 * @author DANCRIST
 *
 */

public class EntitaAssociataModelloDoc extends CustomDataSourceField {
	
	public EntitaAssociataModelloDoc(){
		super(FieldType.CUSTOM);		
	}
	
	public EntitaAssociataModelloDoc(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public EntitaAssociataModelloDoc(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public EntitaAssociataModelloDoc(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public EntitaAssociataModelloDoc(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "entita_associata_modello_doc");		

		EntitaAssociataModelloDocItem editorType = new EntitaAssociataModelloDocItem(); 
		setEditorType(editorType);
	}

}