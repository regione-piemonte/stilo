/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.annotation.JSONBean;

import java.util.Map;

/**
 * Bean contenente la history di navigazione
 * @author michele
 *
 */
@JSONBean
public class HistoryBean {

	public HistoryBean() {
		
	}
	
	public HistoryBean(String title) {
		this.title = title;
	}
	
	
	private String title;
	private Map<String,String> parameter;
	private Integer row;
	private Integer column;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Map<String, String> getParameter() {
		return parameter;
	}
	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}
}