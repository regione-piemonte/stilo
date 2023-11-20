/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;

import it.eng.utility.ui.module.core.server.bean.Criterion;

public class AttributiProcCreator {

	public enum AttributiName{
		NRO_PRATICA("#NRO_PRATICA", "numeroPratica"), ANNO_PRATICA("#ANNO_PRATICA", "annoPratica");
		private String dbValue;
		private String guiValueFilter;
		private AttributiName(String dbValue, String guiValueFilter){
			this.dbValue = dbValue;
			this.guiValueFilter = guiValueFilter;
		}
		public String toString(){
			return this.dbValue;
		}
		public String getGuiValueFilter() {
			return guiValueFilter;
		}
	}

	public enum AttributiOperatori{
		UGUALE("uguale", "equals"), GREATER_THAN("maggiore", "greaterThan"), LESS_THAN("minore", "lessThan"),
		GREATER_OR_EQUAL("maggiore o uguale", "greaterOrEqual"), LESS_OR_EQUAL("minore o uguale", "lessOrEqual"),
		TRA("tra", "betweenInclusive"),
		NON_VALORIZZATO("non valorizzato"),
		VALORIZZATO("valorizzato"),
		SPUNTATO("spuntato"),
		NON_SPUNTATO("non spuntato"),
		SIMILE_CASE_SENSITIVE("simile a (case-sensitive)"),
		SIMILE_CASE_INSENSITIVE("simile a (case-insensitive)");
		private String dbValue;
		private String guiValue;
		private AttributiOperatori(String dbValue){
			this.dbValue = dbValue;
		}
		private AttributiOperatori(String dbValue, String guiValue){
			this.dbValue = dbValue;
			this.guiValue = guiValue;
		}
		public String toString(){
			return this.dbValue;
		}
		public static AttributiOperatori getFromGui(String guiValue){
			for (AttributiOperatori lAttributiOperatori : values()){
				if (StringUtils.isNotEmpty(lAttributiOperatori.getGuiValue())
						&& lAttributiOperatori.getGuiValue().equals(guiValue))
					return lAttributiOperatori;
			}
			return null;
		}
		public String getGuiValue() {
			return guiValue;
		}
	}

	public static AttributiProcBean buildNumber(AttributiName name, Criterion criterion){
		if (criterion == null) return null;
		AttributiProcBean lAttributiProcBean = new AttributiProcBean();
		lAttributiProcBean.setNome(name.toString());
		NumberCriterion lNumberCriterion = getNumber(criterion);
		lAttributiProcBean.setOperatoreLogico(AttributiOperatori.getFromGui(criterion.getOperator()));
		if (lNumberCriterion.getStart()!=null)
			lAttributiProcBean.setConfrontoDa(lNumberCriterion.getStart()+"");
		if (lNumberCriterion.getEnd()!=null)
			lAttributiProcBean.setConfrontoA(lNumberCriterion.getEnd()+"");
		
		// Se l'operatore e' "TRA" controllo se i valori del range
		if( criterion.getOperator().equalsIgnoreCase(AttributiOperatori.TRA.getGuiValue()) ){
			
			// Se il range e' vuoto, il filtro lo cancello perche' NON DEVE essere passato alla stored
			if(lAttributiProcBean.getConfrontoA() == null && lAttributiProcBean.getConfrontoDa() == null){
				lAttributiProcBean = null;
			}
			else{
				// Se e' valorizzato solo il range MIN
				if(lAttributiProcBean.getConfrontoDa() != null && lAttributiProcBean.getConfrontoA() == null){
					// cambio l'operatore in MAGGIORE o UGUALE  DI (>=)
					lAttributiProcBean.setOperatoreLogico(AttributiOperatori.getFromGui(AttributiOperatori.GREATER_OR_EQUAL.getGuiValue()));
				}
				// Se e' valorizzato solo il range MAX 
				if(lAttributiProcBean.getConfrontoDa() == null && lAttributiProcBean.getConfrontoA() != null){
					// cambio l'operatyore in MINORE O UGUALE DI ( <=) e sposto il valore nel range max
					lAttributiProcBean.setConfrontoDa(lAttributiProcBean.getConfrontoA());
					lAttributiProcBean.setConfrontoA(null);
					lAttributiProcBean.setOperatoreLogico(AttributiOperatori.getFromGui(AttributiOperatori.LESS_OR_EQUAL.getGuiValue()));
				}
			}			
		}
		return lAttributiProcBean;
	}

	public static AttributiProcBean build(AttributiName name, AttributiOperatori operatore, Integer start, Integer end){
		AttributiProcBean lAttributiProcBean = new AttributiProcBean();
		lAttributiProcBean.setNome(name.toString());
		lAttributiProcBean.setOperatoreLogico(operatore);
		if (start != null){
			lAttributiProcBean.setConfrontoDa(start+"");
		}
		if (end != null){
			lAttributiProcBean.setConfrontoA(end+"");
		}
		return lAttributiProcBean;
	}

	public static NumberCriterion getNumber(Criterion criterion){

		NumberCriterion lNumberCriterion = new NumberCriterion();
		if(criterion.getValue() != null) {
			lNumberCriterion.setStart(Integer.valueOf((String)criterion.getValue()));
		} else if(criterion.getStart() != null || criterion.getEnd() != null) {
//			if (criterion.getOperator().equals("betweenInclusive")) {
				if (criterion.getStart() != null) {
					lNumberCriterion.setStart(Integer.valueOf((String)criterion.getStart()));	
				}
				if (criterion.getEnd() != null) {
					lNumberCriterion.setEnd(Integer.valueOf((String)criterion.getEnd()));
				}
//			}			
		}
		return lNumberCriterion;
	} 



}
