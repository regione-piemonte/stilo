/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.menu.ShowInMenuFunction;

import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Classe costruita a partire dalla {@link ShowInMenuFunction}
 * I controlli vengono fatti direttamente sull'attributo del nodo della 
 * scrivania selezionato
 * @author Rametta
 *
 */
public class ShowInMenuFunctionFromScrivania extends ShowInMenuFunction{

	protected ScrivaniaLayout layout;
	protected String name; 
	protected String operator1; 	
	protected String[] values1; 
	protected String operator2; 	
	protected String[] values2; 
	protected String operator3; 	
	protected String[] values3; 
	
	public ShowInMenuFunctionFromScrivania(ScrivaniaLayout pLayout, ListGridField[] pListGridField, CustomList pList, String pName, String pOperator, String[] valori) {		
		this(pLayout, pListGridField, pList, pName, pOperator, valori, null, null, null, null);
	}

	public ShowInMenuFunctionFromScrivania(ScrivaniaLayout pLayout, ListGridField[] pListGridField, CustomList pList, String pName, String pOperator1, String[] valori1, String pOperator2, String[] valori2) {		
		this(pLayout, pListGridField, pList, pName, pOperator1, valori1, pOperator2, valori2, null, null);
	}

	public ShowInMenuFunctionFromScrivania(ScrivaniaLayout pLayout, ListGridField[] pListGridField, CustomList pList, String pName, String pOperator1, String[] valori1, String pOperator2, String[] valori2, String pOperator3, String[] valori3) {
		super(pListGridField, pList);	
		layout = pLayout;
		name = pName;
		operator1 = pOperator1 != null ? pOperator1 : "equals";
		values1 = valori1;
		operator2 = pOperator2 != null ? pOperator2 : "equals";
		values2 = valori2;
		operator3 = pOperator3 != null ? pOperator3 : "equals";
		values3 = valori3;
	}
	
	@Override
	public boolean mustBeShown() {
		String value = null;
		boolean cond1 = true;
		boolean cond2 = true;
		boolean cond3 = true;
		
		if("idNode".equals(name)) {
			value = layout != null ? layout.getIdNode() : null;
		} else if("idFolder".equals(name)) {
			value = layout != null ? layout.getIdFolder() : null;
		} else if("tipoNodo".equals(name)) {
			value = layout != null ? layout.getTipoNodo() : null;
		}
		if(value != null) {						
			if(operator1 != null && values1 !=null) {
				cond1= false;
				for (String val1 : values1){		
					if(operator1.equals("equals")  && value.equals(val1)){
						cond1= true;
					}
					if(operator1.equals("notEquals")  && !value.equals(val1)){
						cond1= true;
					}
					if(operator1.equals("startsWith")  && value.startsWith(val1)){
						cond1= true;
					}
					if(operator1.equals("notStartsWith")  && !value.startsWith(val1)){
						cond1= true;
					}
					if(operator1.equals("contains")  && value.contains(val1)){
						cond1= true;
					}
					if(operator1.equals("notContains")  && !value.contains(val1)){
						cond1= true;
					}
				}
			}
			
			if(operator2 != null && values2 !=null) {
				cond2= false;
				for (String val2 : values2){		
					if(operator2.equals("equals")  && value.equals(val2)){
						cond2= true;
					}
					if(operator2.equals("notEquals")  && !value.equals(val2)){
						cond1= true;
					}
					if(operator2.equals("startsWith")  && value.startsWith(val2)){
						cond2= true;
					}
					if(operator2.equals("notStartsWith")  && !value.startsWith(val2)){
						cond2= true;
					}
					if(operator2.equals("contains")  && value.contains(val2)){
						cond2= true;
					}
					if(operator2.equals("notContains")  && !value.contains(val2)){
						cond2= true;
					}					
				}				
			}

			if(operator3 != null && values3 !=null) {
				cond3= false;
				for (String val3 : values3){		
					if(operator3.equals("equals")  && value.equals(val3)){
						cond3= true;
					}
					if(operator3.equals("notEquals")  && !value.equals(val3)){
						cond3= true;
					}
					if(operator3.equals("startsWith")  && value.startsWith(val3)){
						cond3= true;
					}
					if(operator3.equals("notStartsWith")  && !value.startsWith(val3)){
						cond3= true;
					}
					if(operator3.equals("contains")  && value.contains(val3)){
						cond3= true;
					}
					if(operator3.equals("notContains")  && !value.contains(val3)){
						cond3= true;
					}					
				}	
			}
			if (cond1 && cond2 && cond3){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
}