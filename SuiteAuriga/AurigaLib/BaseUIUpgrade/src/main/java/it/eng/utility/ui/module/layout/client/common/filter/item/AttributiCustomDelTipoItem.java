/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class AttributiCustomDelTipoItem extends CustomItem {

	public AttributiCustomDelTipoItem(JavaScriptObject jsObject){
		super(jsObject);
	}
		
	public AttributiCustomDelTipoItem(){
		super();
	}
	
	public AttributiCustomDelTipoItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new AttributiCustomDelTipoItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {
		
		return new CustomItemFormField[]{};
	}

}
