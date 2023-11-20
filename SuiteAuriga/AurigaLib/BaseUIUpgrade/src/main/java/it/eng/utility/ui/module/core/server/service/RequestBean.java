/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

public class RequestBean {

	private String dataSource;
	private String operationType;
	private String operationId;
	private Integer startRow;
	private Integer endRow;
	private String componentId;
	private HashMap<String,Object> data;
	private HashMap<String,Object> oldValues;
	private List<String> sortBy;
	
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public HashMap<String, Object> getData() {
		return data;
	}
	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
	public HashMap<String, Object> getOldValues() {
		return oldValues;
	}
	public void setOldValues(HashMap<String, Object> oldValues) {
		this.oldValues = oldValues;
	}
	public List<String> getSortBy() {
		return sortBy;
	}
	public void setSortBy(List<String> sortBy) {
		this.sortBy = sortBy;
	}
}