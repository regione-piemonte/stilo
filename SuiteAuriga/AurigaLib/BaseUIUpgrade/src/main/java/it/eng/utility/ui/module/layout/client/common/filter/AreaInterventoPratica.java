/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */




import com.smartgwt.client.types.FieldType;


public class AreaInterventoPratica extends CustomDataSourceField {
	
	public AreaInterventoPratica(){
		super(FieldType.CUSTOM);		
	}
	
	public AreaInterventoPratica(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public AreaInterventoPratica(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public AreaInterventoPratica(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public AreaInterventoPratica(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "areaintervento_pratica");		

		AreaInterventoPraticaItem editorType = new AreaInterventoPraticaItem(); 
		setEditorType(editorType);

//		setValidOperators(OperatorId.EQUALS);

	}

}
