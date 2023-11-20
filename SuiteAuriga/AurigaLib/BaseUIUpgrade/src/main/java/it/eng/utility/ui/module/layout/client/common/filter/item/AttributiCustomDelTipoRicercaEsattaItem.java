/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class AttributiCustomDelTipoRicercaEsattaItem extends CustomItem {

	public AttributiCustomDelTipoRicercaEsattaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public AttributiCustomDelTipoRicercaEsattaItem(){
		super();
	}
		
	public AttributiCustomDelTipoRicercaEsattaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new AttributiCustomDelTipoRicercaEsattaItem(jsObj);
		return lCustomItem;
	}
		
	@Override
	public CustomItemFormField[] getFormFields() {
		
		return new CustomItemFormField[]{};
	}

}
