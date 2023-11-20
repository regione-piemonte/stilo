/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.widgets.form.fields.TextItem;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class StringaFullTextMistaItem extends CustomItem {
	
	protected CustomItemFormField parole;
	
	public StringaFullTextMistaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public StringaFullTextMistaItem(){
		super();
	}
		
	public StringaFullTextMistaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new StringaFullTextMistaItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {

		parole = new CustomItemFormField("parole", this);						
		TextItem paroleEditorType = new TextItem();								
		parole.setEditorType(paroleEditorType);		
//		parole.setShowIfCondition(new FormItemIfFunction() {			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				if(isWordsStartWithOperator()) {
//					item.setTextBoxStyle(it.eng.utility.Styles.luceneTextBox);
//					item.setPrompt(I18NUtil.getMessages().filterFullTextItem_parole_prompt());									
//				} else {					
//					item.setTextBoxStyle(it.eng.utility.Styles.textItem);
//					item.setPrompt(null);
//				}				
//				item.redraw();
//				return true;
//			}
//		});
		
		return new CustomItemFormField[]{parole};	
	}
	
	@Override
	protected void disegna(Object value) {
		super.disegna(value);
		redrawParoleTextBoxStyle(null);
	}
	
	@Override
	public void onChangeFilter(String clauseOperator) {
		super.onChangeFilter(clauseOperator);
		redrawParoleTextBoxStyle(clauseOperator);
	}
	
	private void redrawParoleTextBoxStyle(String operator) {
//		String operator = null;
//		try {
//			operator = _instance.getForm() != null ? (String) _instance.getForm().getField("operator").getValue() : null;
//		} catch(Throwable e) {
//			e.getMessage();
//		}
		if(_form.getItem("parole") != null) { 
			if(operator != null && "wordsStartWith".equals(operator)) {
				_form.getItem("parole").setTextBoxStyle(it.eng.utility.Styles.luceneTextBox);
				_form.getItem("parole").setPrompt(I18NUtil.getMessages().filterFullTextItem_parole_prompt());									
			} else {					
				_form.getItem("parole").setTextBoxStyle(it.eng.utility.Styles.textItem);
				_form.getItem("parole").setPrompt(null);
			}	
			_form.getItem("parole").updateState();
		}
	}
	
}
