/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.form.events.FilterChangedEvent;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.VStack;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

/**
 * 
 * @author DANCRIST
 *
 */

public class MonitoraggioPdVFilter extends ConfigurableFilter {
	
	public MonitoraggioPdVFilter(String lista) {
		
		super(lista);
		
		addFilterChangedHandler(new FilterChangedHandler() {

			@Override
			public void onFilterChanged(FilterChangedEvent event) {
				
				int codProcessoBancaProd = getClausePosition("codProcessoBancaProd");
				if (codProcessoBancaProd != -1) {
					if (!isGAE()) {								
						removeClause(getClause(codProcessoBancaProd));
					}
				}
			}
		});
	}
	
	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
	
		if (lMap.containsKey("codProcessoBancaProd") && !isGAE()) {		
			lMap.remove("codProcessoBancaProd");
		}

		return lMap;
	}
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		
		// Filtri per il GAE
		if(!isGAE()){
			selected = selected + "codProcessoBancaProd,";
		}

		for (Criterion lCriterion : lCriterions) {
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())) {
				selected += lCriterion.getFieldName() + ",";
			}
		}
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}

	private boolean isGAE() {
		return AurigaLayout.getParametroDB("PRODUCT_INST_NAME") != null && !"".equals(AurigaLayout.getParametroDB("PRODUCT_INST_NAME"))
				&& "GAE".equals(AurigaLayout.getParametroDB("PRODUCT_INST_NAME")) ? true : false;
	}
	
}
