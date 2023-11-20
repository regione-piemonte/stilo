/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

public class StringaRicercaEstesaCaseInsensitive1 extends CustomDataSourceField {
	
	public StringaRicercaEstesaCaseInsensitive1() {
		super(FieldType.TEXT);
	}

	public StringaRicercaEstesaCaseInsensitive1(String name) {
		super(name, FieldType.TEXT);
	}

	public StringaRicercaEstesaCaseInsensitive1(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public StringaRicercaEstesaCaseInsensitive1(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public StringaRicercaEstesaCaseInsensitive1(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "stringa_ricerca_estesa_case_insensitive_1");
		
//		setValidOperators(OperatorId.ICONTAINS, OperatorId.IEQUALS, OperatorId.ISTARTS_WITH, OperatorId.IENDS_WITH);        
        
	}

}
