/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class Criterion implements Serializable{

	private Object start;
	private Object end;
	private String fieldName;
	private String operator;
	private Object value;
	private List<Criterion> criteria;
	
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
