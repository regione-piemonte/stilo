/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.FilterChangedEvent;
import com.smartgwt.client.widgets.form.events.FilterChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabile;
import it.eng.utility.ui.module.layout.client.common.filter.item.ScadenzaItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

/**
 * Renderizza il filtro che viene visualizzato sopra la lista e che permette di filtrarla
 * @author massimo malvestio
 *
 */
public class AttiPersonaliFilter extends ConfigurableFilter {

	private Boolean showFilterSmistamentoAtti;
	
	public AttiPersonaliFilter(String lista,  Map<String, String> extraparam) {
	
		super(lista, extraparam);
		
		updateShowFilter(extraparam);
		
		addFilterChangedHandler(new FilterChangedHandler() {
			
			@Override
			public void onFilterChanged(FilterChangedEvent event) {
				
				// Filtri relativi al protocollo
				int posAssegnatario = getClausePosition("assegnatario");				
				if(posAssegnatario != -1) {
					if (!showFilterSmistamentoAtti){						
						removeClause(getClause(posAssegnatario));	
					}
				}	
				
				
			}
		});
		
		setWidth(750);
	}
	
	public void updateShowFilter(Map<String, String> extraparam) {
		
		setExtraParam(extraparam);	
		
		showFilterSmistamentoAtti = getExtraParam().get("showFilterSmistamentoAtti") != null ? Boolean.valueOf(getExtraParam().get("showFilterSmistamentoAtti")) : false;
	
	}
	
	@Override
	public LinkedHashMap<String, String> getMappaFiltriToShow(LinkedHashMap<String, String> lMap) {
		
		if(!showFilterSmistamentoAtti) {
			lMap.remove("assegnatario");					
		}
		
		return lMap;
	}
	
	@Override
	protected Criteria createCriteria(FormItemFunctionContext itemContext) {

		SelectItem lSelectItem = (SelectItem) itemContext.getFormItem();
		AdvancedCriteria lAdvancedCriteria = getCriteria(true);
		Criterion[] lCriterions = lAdvancedCriteria.getCriteria();
		String selected = "";
		
		if (!showFilterSmistamentoAtti){
			selected = selected  + "assegnatario,";						
		}
		
		for (Criterion lCriterion : lCriterions){
			if (lCriterion.getFieldName() != null && !lCriterion.getFieldName().equals(lSelectItem.getValue())){
				selected += lCriterion.getFieldName() + ",";
			}
		}	
		
		return new AdvancedCriteria("escludi", OperatorId.EQUALS, selected + ";" + new Date().toString());
		
	}

	@Override
	public void createFilteredSelectItem(final FilterFieldBean filterFieldBean, DataSourceField dataSourceField) {
		
		SelectItemFiltrabile lSelectItem = new SelectItemFiltrabile(filter, filterFieldBean, dataSourceField);
		
		final List<String> filterFieldsClearList =  new ArrayList<String>();
		
		for (FilterFieldBean currentFieldFilterBean : Layout.getFilterConfig("atti_personali").getFields()){
			
			if (currentFieldFilterBean.getDependsFrom()!=null && currentFieldFilterBean.getDependsFrom().contains(filterFieldBean.getName())){
				filterFieldsClearList.add(currentFieldFilterBean.getName());
			}
		};
		
		if (filterFieldsClearList != null && !filterFieldsClearList.isEmpty()){
			
			lSelectItem.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					
					for (String lString : filterFieldsClearList){
						int position = getClausePosition(lString);
						if (position != -1){
							FormItem lClauseValueItem = (FormItem) getClauseValueItem(position);										
							SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
							lClauseValueSelectItem.clearValue();
						}
					}
					
					if (filterFieldBean.getName().equals("idProcessType")){
						int position = getClausePosition("scadenza");
						if (position != -1){
							FormItem lClauseValueItem = (FormItem) getClauseValueItem(position);
							ScadenzaItem lClauseValueSelectItem = (ScadenzaItem) lClauseValueItem;
							DynamicForm lDynamicForm = (DynamicForm) lClauseValueSelectItem.getCanvas();
							SelectItem lSelectItem = (SelectItem) lDynamicForm.getFields()[0];
							lSelectItem.clearValue();
						}
					}
					
				}
			});
		} 
		dataSourceField.setEditorType(lSelectItem);
		dataSourceField.setFilterEditorType(SelectItem.class);
		mappaSelects.put(filterFieldBean.getName(), lSelectItem);
	}
	
}
