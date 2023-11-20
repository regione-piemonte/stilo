/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.types.FieldType;

public class ListaSceltaEstesa extends CustomDataSourceField {
	
	public ListaSceltaEstesa() {
		super(FieldType.TEXT);	
	}

	public ListaSceltaEstesa(String name) {
		super(name, FieldType.TEXT);
	}

	public ListaSceltaEstesa(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public ListaSceltaEstesa(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public ListaSceltaEstesa(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "lista_scelta_estesa");					
//		setValidOperators(new OperatorId[] { OperatorId.EQUALS, OperatorId.NOT_EQUAL });   
	    setValueMap(new HashMap());
	    
	}	
	
}
