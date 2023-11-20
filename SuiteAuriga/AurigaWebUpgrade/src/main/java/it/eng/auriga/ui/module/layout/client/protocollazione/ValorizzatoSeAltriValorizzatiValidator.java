/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class ValorizzatoSeAltriValorizzatiValidator extends CustomValidator{
	
	private String[] fields;
	
	@Override
	protected boolean condition(Object value) {
		String name = getFormItem().getName();
		ArrayList<String> restanti = new ArrayList<String>(fields.length-1);
		for (String lString : fields){
			if (!lString.equals(name)){
				restanti.add(lString);
			}
		}
		if (isBlank(value)) {
			boolean valid = true;
			for (String lStrRest : restanti){
				Object lValueRest = getFormItem().getForm().getValue(lStrRest);
				valid = valid && isBlank(lValueRest);
			}
			return valid;
		} else return true;
	}
	
	private boolean isBlank(Object value) {
		return (value == null || ((value instanceof String) && ("".equals(value.toString().trim()))));		
	}
	
	public void setFields(String[] fields) {
		this.fields = fields;
	}
	
	public String[] getFields() {
		return fields;
	}

}
