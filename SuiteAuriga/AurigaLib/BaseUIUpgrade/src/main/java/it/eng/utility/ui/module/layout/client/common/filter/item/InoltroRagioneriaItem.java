/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class InoltroRagioneriaItem extends CustomItem {
	
	public InoltroRagioneriaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public InoltroRagioneriaItem(){
		super();
	}
	
	public InoltroRagioneriaItem(String datasourceName){
		super(buildPropertyWithDatasourceName(datasourceName));
	}
	
	public static Map<String, String> buildPropertyWithDatasourceName(String datasourceName) {
		
		Map<String, String> property = new HashMap<String, String>();
		property.put("datasourceName", datasourceName);
		return property;
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new InoltroRagioneriaItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {
		
		return new CustomItemFormField[]{};
	}

}
