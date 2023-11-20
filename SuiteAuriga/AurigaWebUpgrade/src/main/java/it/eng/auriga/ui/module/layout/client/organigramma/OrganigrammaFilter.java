/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.events.FilterChangedEvent;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

/**
 * @author DANCRIST
 */

public class OrganigrammaFilter extends ConfigurableFilter {
	
	private String showFilterFlgObjType;
	
	public OrganigrammaFilter(String lista, Map<String, String> extraparam) {
		
		super(lista, extraparam);
		
		updateShowFilter(extraparam);
		
		addFilterChangedHandler(new FilterChangedHandler() {

			@Override
			public void onFilterChanged(FilterChangedEvent event) {
				
				int flgObjType = getClausePosition("flgObjType");
				if (flgObjType != -1) {
					if (showFilterFlgObjType.equalsIgnoreCase("N") //|| isLookup()
							) {
						removeClause(getClause(flgObjType));
					}
				}
			}
		});		
	}
	
	public boolean isLookup() {
		return getLayout() != null && ((OrganigrammaLayout) getLayout()).isLookup();
	}
	
	public OrganigrammaFilter(String lista) {
		super(lista);
		
	}
	
	public void updateShowFilter(Map<String, String> extraparam) {
		
		setExtraParam(extraparam);
			
		showFilterFlgObjType = getExtraParam().get("flgIncludiUtenti") != null && "1".equals(getExtraParam().get("flgIncludiUtenti")) ? "S" : "N";
	}
	
	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
		
		if (lMap.containsKey("flgObjType") && (showFilterFlgObjType.equalsIgnoreCase("N") //|| isLookup()
				)) {
			lMap.remove("flgObjType");
		}
		
		return lMap;
	}
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		
		if (showFilterFlgObjType.equalsIgnoreCase("N") //|| isLookup()
				) {
			selected = selected + "flgObjType,";
		}
		
		for (Criterion lCriterion : lCriterions) {
			if (!lCriterion.getFieldName().equals(lSelectItem.getValue())) {
				selected += lCriterion.getFieldName() + ",";
			}
		}
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}
}