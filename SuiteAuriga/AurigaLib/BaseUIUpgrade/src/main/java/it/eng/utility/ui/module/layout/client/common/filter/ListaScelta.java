/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.types.FieldType;

public class ListaScelta extends CustomDataSourceField {
	
	public ListaScelta() {
		super(FieldType.TEXT);	
	}

	public ListaScelta(String name) {
		super(name, FieldType.TEXT);
	}

	public ListaScelta(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public ListaScelta(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public ListaScelta(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "lista_scelta");		
//		setValidOperators(new OperatorId[] { OperatorId.EQUALS });        	          		
		setValueMap(new HashMap());		
	    
	}

}