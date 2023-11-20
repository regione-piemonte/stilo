/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

public class StringaRicercaComplessa1 extends CustomDataSourceField {
	
	public StringaRicercaComplessa1() {
		super(FieldType.TEXT);
	}

	public StringaRicercaComplessa1(String name) {
		super(name, FieldType.TEXT);
	}

	public StringaRicercaComplessa1(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public StringaRicercaComplessa1(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public StringaRicercaComplessa1(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "stringa_ricerca_complessa_1");
		
//		setValidOperators ( 
//				OperatorId.ICONTAINS, 
//				OperatorId.INOT_CONTAINS, 
//				OperatorId.EQUALS,
//				OperatorId.NOT_EQUAL, 
//				OperatorId.IEQUALS, 
//				OperatorId.INOT_EQUAL, 
//				OperatorId.ISTARTS_WITH, 
//				OperatorId.IENDS_WITH,								
//				OperatorId.IS_NULL,
//				OperatorId.NOT_NULL
//		);        
	       
	}

}
