/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.items.NumericItem;

public class Numero extends CustomDataSourceField {
	
	public Numero() {
		super(FieldType.TEXT);
	}

	public Numero(String name) {
		super(name, FieldType.TEXT);
	}

	public Numero(String name, String title) {
		super(name, FieldType.TEXT, title);
	}
	
	public Numero(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.TEXT, title, lMap);
		init();
	}

	public Numero(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public Numero(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {
				
		setAttribute("customType", "numero");		
		
		boolean hasFloat = property.get("hasFloat") != null && property.get("hasFloat").equalsIgnoreCase("true");
		Integer nroDecimali = property.get("nroDecimali") != null ? Integer.valueOf(property.get("nroDecimali")) : null;
		
		NumericItem editorType = new NumericItem(hasFloat, nroDecimali);   
 
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
