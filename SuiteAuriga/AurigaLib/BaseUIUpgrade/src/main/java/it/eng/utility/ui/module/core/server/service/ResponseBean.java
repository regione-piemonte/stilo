/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.message.MessageBean;

import java.util.List;

public class ResponseBean {

	private Integer status;
    private Integer startRow;
    private Integer endRow;
    private Integer totalRows;
    private Integer rowsForPage;
    private List<?> data;
    private List<MessageBean> messages;
    private boolean overflowData;
    private String overflowSortField;
	private boolean overflowSortDesc;
	   
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public List<MessageBean> getMessages() {
		return messages;
	}
	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}
	public boolean isOverflowData() {
		return overflowData;
	}
	public void setOverflowData(boolean overflowData) {
		this.overflowData = overflowData;
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