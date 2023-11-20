/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.items.NumericItem;

public class NumeroRicercaEsatta extends CustomDataSourceField {
	

	
	public NumeroRicercaEsatta() {
		super(FieldType.TEXT);
	}

	public NumeroRicercaEsatta(String name) {
		super(name, FieldType.TEXT);
	}

	public NumeroRicercaEsatta(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public NumeroRicercaEsatta(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public NumeroRicercaEsatta(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {
				
		setAttribute("customType", "numero_ricerca_esatta");		
		
		NumericItem editorType = new NumericItem();   
 
//	    setValidOperators(new OperatorId[] { 
//	    						OperatorId.BETWEEN_INCLUSIVE,
//	    						OperatorId.GREATER_THAN, 
//	    						OperatorId.LESS_THAN, 
//	    						OperatorId.GREATER_OR_EQUAL, 
//	    						OperatorId.LESS_OR_EQUAL,
//	    						OperatorId.EQUALS }
//	    );          
	    
	    setEditorType(editorType);        	          
        
	}

}
