/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.SegnaturaFascicoloItem;

public class SegnaturaFascicolo extends CustomDataSourceField {
	
	public SegnaturaFascicolo(){
		super(FieldType.CUSTOM);
	}
	
	public SegnaturaFascicolo(String name) {
		super(name, FieldType.CUSTOM);
	}

	public SegnaturaFascicolo(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public SegnaturaFascicolo(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public SegnaturaFascicolo(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "segnatura_fascicolo");		

		SegnaturaFascicoloItem editorType = new SegnaturaFascicoloItem(); 
		setEditorType(editorType);

//		setValidOperators(OperatorId.EQUALS);

	}

}
