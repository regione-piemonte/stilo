/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;

public class AnnoRicercaEsatta extends CustomDataSourceField {
	
	public AnnoRicercaEsatta() {
		super(FieldType.TEXT);	
	}

	public AnnoRicercaEsatta(String name) {
		super(name, FieldType.TEXT);
	}

	public AnnoRicercaEsatta(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public AnnoRicercaEsatta(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public AnnoRicercaEsatta(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {		
		setAttribute("customType", "anno_ricerca_esatta");		
		
		AnnoItem editorType = new AnnoItem(); 
		setEditorType(editorType);
	}
}
