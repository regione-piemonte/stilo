/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;

public class AdvancedCriteria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Object start;
	private Object end;
	private String fieldName;
	private String operator;
	private Object value;
	private List<Criterion> criteria;
		
	/**
	 * Ritorna il criteria in base al nome del Field
	 * @param fieldName
	 * @return
	 */
	public Criterion getCriterionByFieldName(String fieldName) {
		if(criteria!=null){
			for(Criterion criteriaone:criteria){
				if(criteriaone.getFieldName().equals(fieldName)){
					return criteriaone;
				}
			}
		}
		return null;
	}
	
	public <T> T buildBean(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		T object = clazz.newInstance();
		for(Criterion criteriaone:criteria){
			Class lClass = BeanUtilsBean2.getInstance().getPropertyUtils().getPropertyType(object, criteriaone.getFieldName());
			Object value = lClass.getConstructor(criteriaone.getValue().getClass()).newInstance(criteriaone.getValue());
			BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(object, criteriaone.getFieldName(), value);
		}
		return object;
	}
	
	/**
	 * Aggiunge un criteria alla lista
	 * @param criterion
	 */
	public void addCriterion(Criterion criterion){
		if(criteria==null){
			criteria = new ArrayList<Criterion>();
		}
		criteria.add(criterion);
	}	
	
	public Object getStart() {
		return start;
	}
	public void setStart(Object start) {
		this.start = start;
	}
	public Object getEnd() {
		return end;
	}
	public void setEnd(Object end) {
		this.end = end;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}	
	public List<Criterion> getCriteria() {
		return criteria;
	}
	public void setCriteria(List<Criterion> criteria) {
		this.criteria = criteria;
	}
	
	
}
