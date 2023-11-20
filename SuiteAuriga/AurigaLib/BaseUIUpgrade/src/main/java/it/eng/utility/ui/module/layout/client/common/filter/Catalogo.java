/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;


public class Catalogo extends CustomDataSourceField {
	
	public Catalogo(){
		super(FieldType.CUSTOM);		
	}
	
	public Catalogo(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public Catalogo(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public Catalogo(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public Catalogo(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "catalogo_pratica");		

		CatalogoItem editorType = new CatalogoItem(); 
		setEditorType(editorType);

//		setValidOperators(OperatorId.EQUALS);

	}

}
