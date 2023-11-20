/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

public class RichiesteAgibilitaFilter extends ConfigurableFilter {
	
	
	public RichiesteAgibilitaFilter(String lista, Map<String, String> extraparam) {
		super(lista, extraparam);
		
	}
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		
		for (Criterion lCriterion : lCriterions){
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())){
				selected += lCriterion.getFieldName() + ",";
			}
		}	
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}
}
