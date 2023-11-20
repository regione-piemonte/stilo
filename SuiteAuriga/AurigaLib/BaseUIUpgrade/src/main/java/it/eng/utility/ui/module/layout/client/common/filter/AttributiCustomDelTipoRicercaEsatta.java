/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.AttributiCustomDelTipoRicercaEsattaItem;

public class AttributiCustomDelTipoRicercaEsatta extends CustomDataSourceField {
	
	public AttributiCustomDelTipoRicercaEsatta() {
		super(FieldType.CUSTOM);
	}

	public AttributiCustomDelTipoRicercaEsatta(String name) {
		super(name, FieldType.CUSTOM);
	}

	public AttributiCustomDelTipoRicercaEsatta(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}
	
	public AttributiCustomDelTipoRicercaEsatta(String name, String title, Map<String, String> lMap) {
		super(name, FieldType.CUSTOM, title, lMap);
	}

	public AttributiCustomDelTipoRicercaEsatta(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public AttributiCustomDelTipoRicercaEsatta(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "attributi_custom_del_tipo_ricerca_esatta");		
		
		AttributiCustomDelTipoRicercaEsattaItem editorType = new AttributiCustomDelTipoRicercaEsattaItem(property);
		setEditorType(editorType);
        
	}

}
