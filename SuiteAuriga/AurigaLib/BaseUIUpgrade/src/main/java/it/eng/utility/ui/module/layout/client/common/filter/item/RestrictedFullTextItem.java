/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class RestrictedFullTextItem extends CustomItem {

	public RestrictedFullTextItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public RestrictedFullTextItem(){
		super();
	}
		
	public RestrictedFullTextItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new RestrictedFullTextItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {

		CustomItemFormField parole = new CustomItemFormField("parole", this);		
		TextItem paroleEditorType = new TextItem();
		paroleEditorType.setTextBoxStyle(it.eng.utility.Styles.luceneTextBox);
		paroleEditorType.setPrompt(I18NUtil.getMessages().filterFullTextItem_parole_prompt());
		paroleEditorType.setWidth(200);
		parole.setEditorType(paroleEditorType);		
		parole.setEndRow(false);		
		parole.setColSpan(1);		

		Boolean showSelectAttributi = property.get("showSelectAttributi") != null ? new Boolean(property.get("showSelectAttributi")) : false;
		Boolean showFlgRicorsiva = property.get("showFlgRicorsiva") != null ? new Boolean(property.get("showFlgRicorsiva")) : false;		

		CustomItemFormField attributi = null;
		if(showSelectAttributi) {
			attributi = new CustomItemFormField("attributi", I18NUtil.getMessages().filterFullTextItem_attributi_title(), this);
			SelectItem attributiEditorType = new SelectItem();
//			if (property.get("attributiDataSource") != null && !"".equals(property.get("attributiDataSource"))){
//				SelectGWTRestDataSource attributiDS = new SelectGWTRestDataSource(property.get("attributiDataSource"));
//				if(property.get("categoria") != null && !"".equals(property.get("categoria"))) {
//					attributiDS.addParam("categoria", property.get("categoria"));
//				}
//				attributiEditorType.setOptionDataSource(attributiDS);
//				attributiEditorType.setDisplayField("value");
//				attributiEditorType.setValueField("field");			
//				attributiEditorType.setAutoFetchData(true);		
//			} else {
//				attributiEditorType.setValueMap(new LinkedHashMap<String, String>());
//			}
			if (property.get("nomeEntita") != null && !"".equals(property.get("nomeEntita"))){
				attributiEditorType.setValueMap(Layout.getAttributiValueMap(property.get("nomeEntita")));				
			} else {
				attributiEditorType.setValueMap(new LinkedHashMap<String, String>());
			}
			attributiEditorType.setDisplayField("value");
			attributiEditorType.setValueField("key");
			attributiEditorType.setMultiple(true);
			attributiEditorType.setWidth(200);
			attributi.setEditorType(attributiEditorType);		
			attributi.setEndRow(false);
			attributi.setColSpan(1);	
		}
		
		CustomItemFormField flgRicorsiva = null;
		if(showFlgRicorsiva){
			flgRicorsiva = new CustomItemFormField("flgRicorsiva", I18NUtil.getMessages().filterFullTextItem_flgRicorsiva_title(), this);		
			CheckboxItem flgRicorsivaEditorType = new CheckboxItem();
			flgRicorsivaEditorType.setWidth("*");
			flgRicorsiva.setEditorType(flgRicorsivaEditorType);		
			flgRicorsiva.setEndRow(false);		
			flgRicorsiva.setColSpan(1);						
		} 

		if(showSelectAttributi && showFlgRicorsiva){
			return new CustomItemFormField[]{parole, attributi, flgRicorsiva};
		} else if(showSelectAttributi) {
			return new CustomItemFormField[]{parole, attributi};
		} else if(showFlgRicorsiva){
			return new CustomItemFormField[]{parole, flgRicorsiva};		
		} else {
			return new CustomItemFormField[]{parole};
		}
	}
	
	@Override
	protected Record setDefaultValues(Object value) {
		Boolean showFlgRicorsiva = property.get("showFlgRicorsiva") != null ? new Boolean(property.get("showFlgRicorsiva")) : false;
		if(showFlgRicorsiva){
			_form.setValue("flgRicorsiva", true);		
		}
		int size = Layout.getAttributiValueMap(property.get("nomeEntita")).keySet().size();
		_form.setValue("attributi", Layout.getAttributiValueMap(property.get("nomeEntita")).keySet().toArray(new String[size]));	
		return new Record(_form.getValues());
	}

}
