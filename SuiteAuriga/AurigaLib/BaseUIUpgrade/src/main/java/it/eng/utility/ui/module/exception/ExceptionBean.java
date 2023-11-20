/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.annotation.JSONBean;

import com.smartgwt.client.data.Record;

@JSONBean
public class ExceptionBean {

	private int errorCode;
	private String errorMessage;
	private String htmlStackTrace;
	public ExceptionBean(Record record) {
		errorMessage = record.getAttribute("errorMessage");
		htmlStackTrace = record.getAttribute("htmlStackTrace");
		errorCode = record.getAttributeAsInt("errorCode");
	}
	public ExceptionBean() {
		// TODO Auto-generated constructor stub
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setHtmlStackTrace(String htmlStackTrace) {
		this.htmlStackTrace = htmlStackTrace;
	}
	public String getHtmlStackTrace() {
		return htmlStackTrace;
	}
}
