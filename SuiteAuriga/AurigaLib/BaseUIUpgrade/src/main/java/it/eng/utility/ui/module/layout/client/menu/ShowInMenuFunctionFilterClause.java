/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Classe costruita a partire dalla {@link ShowInMenuFunction}
 * I controlli vengono fatti direttamente sui criteria settati
 * sulla tabella
 * @author Rametta
 *
 */
public class ShowInMenuFunctionFilterClause extends ShowInMenuFunction{

	protected ConfigurableFilter filter;
	protected FilterValueRelation[] values;

	/**
	 * Permette di costruire una {@link ShowInMenuFunction} che per i field
	 * passati in ingresso, verifica che:
	 * - Tutti i filtri passati come {@link FilterValueRelation} --> name siano presenti
	 * - Per ogni filtro, sia settato uno dei valori previsti da {@link FilterValueRelation} --> values
	 * 
	 * @param pListGridField I Field per i quali eseguire la verifica
	 * @param pList La lista da cui ricavare i criteria settati
	 * @param valori i nomi dei filtri con i relativi valori ammessi
	 */
	public ShowInMenuFunctionFilterClause(ConfigurableFilter pFilter, ListGridField[] pListGridField,
			CustomList pList, FilterValueRelation[] valori) {
		super(pListGridField, pList);
		filter = pFilter;
		values = valori;
	}

	/* Data una lista di FilterValueRelation verifica in or i valori
	 */
	/* (non-Javadoc)
	 * @see it.eng.utility.ui.module.layout.client.menu.ShowInMenuFunction#mustBeShown()
	 */
	@Override
	public boolean mustBeShown() {
		Criteria lCriteria = list.getCriteria();
		if(lCriteria == null) 
			lCriteria = filter.getCriteria();		
		if (lCriteria!=null){
			AdvancedCriteria lAdvancedCriteria = lCriteria.asAdvancedCriteria();
			if(lAdvancedCriteria.getCriteria() != null) {
				boolean result = true;
				for(Criterion criterion : lAdvancedCriteria.getCriteria()) {
					result = result && getShownForTheCriteria(result, criterion);
				}
				return result;
			}
			return false;
		} else return false;
	}

	protected boolean getShownForTheCriteria(boolean result, Criterion criterion) {
		FilterValueRelation lValueRelation = findFilterValueRelation(criterion);
		if (lValueRelation == null) return true;
		else {
			String critValue = criterion.getValueAsString();
			if (isInValuesAdmitted(critValue, lValueRelation.getValues())){
				return true;
			} else return false;
		}
	}

	private boolean isInValuesAdmitted(String critValue, String[] valuesValid) {
		for (String lStrValue : valuesValid){			
			if (critValue == null || critValue.equals(lStrValue)){
				return true;
			}	
		}
		return false;
	}

	private FilterValueRelation findFilterValueRelation(Criterion criterion) {
		for (FilterValueRelation lValueRelation : values){
			if(criterion.getFieldName() != null && criterion.getFieldName().equals(lValueRelation.getName())) {
				return lValueRelation;
			}
		}
		return null;
	}

}
