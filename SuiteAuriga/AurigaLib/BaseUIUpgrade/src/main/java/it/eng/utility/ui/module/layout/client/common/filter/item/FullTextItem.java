/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

@BeanFactory.Generate
public class FullTextItem extends CustomItem {
	
	public FullTextItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public FullTextItem(){
		super();
	}
	
	public FullTextItem(final Map<String, String> property){
		super(property);
	}
	
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new FullTextItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {		
		
		List<CustomItemFormField> listFormFields = new ArrayList<CustomItemFormField>();
	
		String categoria = property.get("categoria") != null ? property.get("categoria") : "";
		
		if (!UserInterfaceFactory.getParametroDBAsBoolean("HIDE_FILTER_FULLTEXT_REP_DOC") || !"REP_DOC".equals(categoria)) {
			
			CustomItemFormField parole = new CustomItemFormField("parole", this);		
			TextItem paroleEditorType = new TextItem();
			paroleEditorType.setTextBoxStyle(it.eng.utility.Styles.luceneTextBox);
			paroleEditorType.setPrompt(I18NUtil.getMessages().filterFullTextItem_parole_prompt());
			paroleEditorType.setWidth(200);
			parole.setEditorType(paroleEditorType);		
			parole.setEndRow(false);		
			parole.setColSpan(1);		
			listFormFields.add(parole);
			
			Boolean showSelectAttributi = property.get("showSelectAttributi") != null ? new Boolean(property.get("showSelectAttributi")) : false;
			if(showSelectAttributi) {
				CustomItemFormField attributi = new CustomItemFormField("attributi", I18NUtil.getMessages().filterFullTextItem_attributi_title(), this);
				SelectItem attributiEditorType = new SelectItem();
//				if (property.get("attributiDataSource") != null && !"".equals(property.get("attributiDataSource"))){
//					SelectGWTRestDataSource attributiDS = new SelectGWTRestDataSource(property.get("attributiDataSource"));
//					if(property.get("categoria") != null && !"".equals(property.get("categoria"))) {
//						attributiDS.addParam("categoria", property.get("categoria"));
//					}
//					attributiEditorType.setOptionDataSource(attributiDS);
//					attributiEditorType.setDisplayField("value");
//					attributiEditorType.setValueField("field");			
//					attributiEditorType.setAutoFetchData(true);		
//				} else {
//					attributiEditorType.setValueMap(new LinkedHashMap<String, String>());
//				}
				if (property.get("nomeEntita") != null && !"".equals(property.get("nomeEntita"))){
					attributiEditorType.setValueMap(Layout.getAttributiValueMap(property.get("nomeEntita")));				
				} else {
					attributiEditorType.setValueMap(new LinkedHashMap<String, String>());
				}
				attributiEditorType.setDisplayField("value");
				attributiEditorType.setValueField("key");
				attributiEditorType.setMultiple(true);
				attributiEditorType.setWidth(200);
				attributiEditorType.setHoverWidth(280);
				attributiEditorType.setItemHoverFormatter(new FormItemHoverFormatter() {			
					@Override
					public String getHoverHTML(FormItem item, DynamicForm form) {
						// TODO Auto-generated method stub
						Map values = _form.getValues();				
						ArrayList<String> attributi = (ArrayList<String>) values.get("attributi");;
						String ret = null;
						for(String key : attributi) {
							String value = Layout.getAttributiValueMap(property.get("nomeEntita")).get(key);
							ret = (ret == null) ? value : (ret + ", " + value);
						}
						return ret;
					}
				});
				attributi.setEditorType(attributiEditorType);		
				attributi.setEndRow(false);
				attributi.setColSpan(1);
				listFormFields.add(attributi);
			}
		}
			
		Boolean showFlgRicorsiva = property.get("showFlgRicorsiva") != null ? new Boolean(property.get("showFlgRicorsiva")) : false;		
		if(showFlgRicorsiva){
			CustomItemFormField flgRicorsiva = new CustomItemFormField("flgRicorsiva", I18NUtil.getMessages().filterFullTextItem_flgRicorsiva_title(), this);		
			if (UserInterfaceFactory.getParametroDBAsBoolean("HIDE_FILTER_FULLTEXT_REP_DOC") && "REP_DOC".equals(categoria)) {
				flgRicorsiva.setTitle(null);
				flgRicorsiva.setShowTitle(false);	
			}
			CheckboxItem flgRicorsivaEditorType = new CheckboxItem();
			flgRicorsivaEditorType.setWidth("*");
			flgRicorsivaEditorType.setShowTitle(false);
			flgRicorsiva.setEditorType(flgRicorsivaEditorType);			
			flgRicorsiva.setEndRow(false);		
			flgRicorsiva.setColSpan(1);	
			listFormFields.add(flgRicorsiva);
		}
			 
		return listFormFields.toArray(new CustomItemFormField[listFormFields.size()]);	
	}
	
	@Override
	protected Record setDefaultValues(Object value) {
		
		String categoria = property.get("categoria") != null ? property.get("categoria") : "";		
		
		if (!UserInterfaceFactory.getParametroDBAsBoolean("HIDE_FILTER_FULLTEXT_REP_DOC") || !"REP_DOC".equals(categoria)) {
			
			Boolean showSelectAttributi = property.get("showSelectAttributi") != null ? new Boolean(property.get("showSelectAttributi")) : false;
			if(showSelectAttributi){
				int size = Layout.getAttributiValueMap(property.get("nomeEntita")).keySet().size();
				_form.setValue("attributi", Layout.getAttributiValueMap(property.get("nomeEntita")).keySet().toArray(new String[size]));	
			}
		}
		
		Boolean showFlgRicorsiva = property.get("showFlgRicorsiva") != null ? new Boolean(property.get("showFlgRicorsiva")) : false;
		if(showFlgRicorsiva){
			_form.setValue("flgRicorsiva", true);		
		}
		
		return new Record(_form.getValues());
	}

}
