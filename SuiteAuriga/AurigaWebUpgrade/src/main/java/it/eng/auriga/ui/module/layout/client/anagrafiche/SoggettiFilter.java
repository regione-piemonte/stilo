/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

import java.util.Date;
import java.util.LinkedHashMap;

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

public class SoggettiFilter extends ConfigurableFilter {

	public SoggettiFilter(String lista) {
		super(lista);

		addFilterChangedHandler(new FilterChangedHandler() {

			@Override
			public void onFilterChanged(FilterChangedEvent event) {
				// Filtri relativi a registrazioni annullate
				int posPorzRubrica = getClausePosition("porzioneRubrica");
				if (posPorzRubrica != -1) {
					if (!isAttivoPartRubricaXUo() || !isAbilToViewSullaRubricaDiFilter()) {
						removeClause(getClause(posPorzRubrica));
					}
				}
				
			}
		});
	}

	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
	
		if (lMap.containsKey("porzioneRubrica") && (!isAttivoPartRubricaXUo() || !isAbilToViewSullaRubricaDiFilter())) {
			lMap.remove("porzioneRubrica");
		}
		
		return lMap;
	}

	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";

		if (!isAttivoPartRubricaXUo() || !isAbilToViewSullaRubricaDiFilter()) {
			selected = selected + "porzioneRubrica,";
		}

		for (Criterion lCriterion : lCriterions){
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())){
				selected+=lCriterion.getFieldName()+",";
			}
		}		
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}

	public boolean isAttivoPartRubricaXUo() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PARTIZ_RUBRICA_X_UO");
	}
	
	public boolean isAbilToViewSullaRubricaDiFilter() {
		return Layout.isPrivilegioAttivo("GRS/S;M");
	}


}
