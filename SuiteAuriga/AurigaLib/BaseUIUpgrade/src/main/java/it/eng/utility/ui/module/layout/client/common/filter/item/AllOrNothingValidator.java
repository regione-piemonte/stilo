/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.utility.ui.module.core.client.i18n.I18NUtil;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class AllOrNothingValidator extends CustomValidator  {

	
	public AllOrNothingValidator(){
		setErrorMessage(I18NUtil.getMessages().requiredFieldError_message());
	}
	
	public AllOrNothingValidator(String[] lString){
		setErrorMessage(I18NUtil.getMessages().requiredFieldError_message());
		setAttribute("daVerificare", lString);
	}	
	
	public void setFieldNames(String[] lString){
		setAttribute("daVerificare", lString);
	}	
	
	@Override
	protected boolean condition(Object arg0) {
		String[] lString = this.getAttributeAsStringArray("daVerificare");
		if (arg0!=null) return true;
		DynamicForm lForm = formItem.getForm();
		FormItem[] items = lForm.getFields();
		boolean coerente = true;
		for (FormItem lFormItem : items){
			if (!lFormItem.getFieldName().equals(formItem.getName())){
				if (arg0!=null){
					if (lFormItem.getValue()==null) coerente = false;
				} else if (arg0==null){
					if (lString!=null){
						for (String valore : lString){
							if (valore.equals(lFormItem.getName())){
								if (lFormItem.getValue()!=null) coerente = false;
							}
						}
					} else {
						if (lFormItem.getValue()!=null) coerente = false;
					}
				}
			}
		}
//		if (!coerente) mFilter.setProperty("hasErrors", true);
		return coerente;
	}

}
