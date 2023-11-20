/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

import java.util.Date;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.form.events.FilterChangedEvent;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.VStack;

public class OggettarioFilter extends ConfigurableFilter {
	
	public OggettarioFilter(String lista, Map<String, String> extraparam) {
		// TODO Auto-generated constructor stub
		super(lista, extraparam);	

		addFilterChangedHandler(new FilterChangedHandler() {			
			@Override
			public void onFilterChanged(FilterChangedEvent event) {
					
				int pos = getClausePosition("flgVersoRegistrazione");
				if(pos != -1) {
					String flgTipoProv = getExtraParam().get("flgTipoProv");
					if(flgTipoProv != null && !"".equals(flgTipoProv)) {						
						removeClause(getClause(pos));						
					} 			
				}				
			} 			
		});
	}	
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {
		
		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		String flgTipoProv = getExtraParam().get("flgTipoProv");
		if(flgTipoProv != null && !"".equals(flgTipoProv)) {						
			selected = "flgVersoRegistrazione,";						
		}
		for (Criterion lCriterion : lCriterions){
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())){
				selected+=lCriterion.getFieldName()+",";
			}
		}		
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}

}
