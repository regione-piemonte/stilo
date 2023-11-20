/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
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
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;

public class GestioneUtentiFilter extends ConfigurableFilter{
	
	private Boolean showFilterEscludiUtentiNoDominio;	
	
	public GestioneUtentiFilter(String lista, Map<String, String> extraparam) {
		super(lista, extraparam);
		
		updateShowFilter(extraparam);
		
		addFilterChangedHandler(new FilterChangedHandler() {
			
			@Override
			public void onFilterChanged(FilterChangedEvent event) {
				
				// Filtri relativi al protocollo
				int flgAccreditatiInDomIo = getClausePosition("flgAccreditatiInDomIo");				
				if(flgAccreditatiInDomIo != -1) {
					if (!showFilterEscludiUtentiNoDominio){						
						removeClause(getClause(flgAccreditatiInDomIo));	
					}
				}	
			}
		});
	}

	public void updateShowFilter(Map<String, String> extraparam) {
		setExtraParam(extraparam);			
		showFilterEscludiUtentiNoDominio = getExtraParam().get("showFilterEscludiUtentiNoDominio") != null ? Boolean.valueOf(getExtraParam().get("showFilterEscludiUtentiNoDominio")) : true;		
	}
	
	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
		
		if(!showFilterEscludiUtentiNoDominio) {
			lMap.remove("flgAccreditatiInDomIo");					
		}		
		return lMap;
	}
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		
		if (!showFilterEscludiUtentiNoDominio){
			selected = selected  + "flgAccreditatiInDomIo,";						
		}
		for (Criterion lCriterion : lCriterions){
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())){
				selected += lCriterion.getFieldName() + ",";
			}
		}	
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
	}
}
