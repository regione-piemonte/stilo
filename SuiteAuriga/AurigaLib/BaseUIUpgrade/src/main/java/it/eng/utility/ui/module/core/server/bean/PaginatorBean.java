/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import it.eng.document.NumeroColonna;
import it.eng.xml.ReflectionUtility;

public class PaginatorBean<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<T> data = new ArrayList<T>();
	private Integer startRow;
	private Integer endRow;
	private Integer totalRows;
	private Integer rowsForPage;
	private boolean overflow = false;
	private String overflowSortField = null;
	private boolean overflowSortDesc = false;
	
	public PaginatorBean(List<T> data){
		if(data==null){
			data = new ArrayList<T>();
		}
		setData(data);
		setStartRow(0);
		setEndRow(data.size());
		setTotalRows(data.size());
	}
	
	public PaginatorBean(){
	}
	
	public void addRecord(T record){
		data.add(record);
	}
	
	public void setOverflowSortProperties(String colsOrderBy, String flgDescOrderBy, Object lBeanMappingXml) {
		if(colsOrderBy != null && !"".equals(colsOrderBy) && !colsOrderBy.contains(",")) {
			Field[] lFields = ReflectionUtility.getFields(lBeanMappingXml);
			for (Field lField : lFields) {
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				if (lNumeroColonna != null && lNumeroColonna.numero() != null && lNumeroColonna.numero().equals(colsOrderBy)) {
					setOverflowSortField(lField.getName());
				}
			}
			setOverflowSortDesc(flgDescOrderBy != null && "1".equals(flgDescOrderBy));
		}
	}
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
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
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public Integer getRowsForPage() {
		return rowsForPage;
	}
	public void setRowsForPage(Integer rowsForPage) {
		this.rowsForPage = rowsForPage;
	}
	public boolean getOverflow() {
		return overflow;
	}
	public void setOverflow(boolean overflow) {
		this.overflow = overflow;
	}
	public String getOverflowSortField() {
		return overflowSortField;
	}
	public void setOverflowSortField(String overflowSortField) {
		this.overflowSortField = overflowSortField;
	}
	public boolean getOverflowSortDesc() {
		return overflowSortDesc;
	}
	public void setOverflowSortDesc(boolean overflowSortDesc) {
		this.overflowSortDesc = overflowSortDesc;
	}
	
}