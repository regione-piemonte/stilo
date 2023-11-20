/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class InseritoInOdGDiscussioneSedutaItem extends CustomItem {
	
	public InseritoInOdGDiscussioneSedutaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public InseritoInOdGDiscussioneSedutaItem(){
		super();
	}
		
	public InseritoInOdGDiscussioneSedutaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new InseritoInOdGDiscussioneSedutaItem(jsObj);
		return lCustomItem;
	}
		
	@Override
	public CustomItemFormField[] getFormFields() {
		
		return new CustomItemFormField[]{};
	}
		
}