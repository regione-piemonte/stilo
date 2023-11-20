/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

public class ListaSceltaOrganigramma extends CustomDataSourceField {
	
	public ListaSceltaOrganigramma() {
		super(FieldType.CUSTOM);	
	}

	public ListaSceltaOrganigramma(String name) {
		super(name, FieldType.CUSTOM);
	}

	public ListaSceltaOrganigramma(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public ListaSceltaOrganigramma(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
		init();
	}

	public ListaSceltaOrganigramma(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public ListaSceltaOrganigramma(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "lista_scelta_organigramma");
		
		ListaSceltaOrganigrammaItem editorType = new ListaSceltaOrganigrammaItem(property);
		setEditorType(editorType);
	    
	}

}