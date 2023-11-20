/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FindElenchiAlbiResultBean implements Serializable {
	
	private java.lang.Integer nroTotRec;
	private java.lang.Integer nroRecInPagina;
	private java.lang.String result;
	private java.lang.String errContext;
	private java.lang.Integer errCode;
	private java.lang.String errMsg;
	
	public java.lang.Integer getNroTotRec() {
		return nroTotRec;
	}
	public void setNroTotRec(java.lang.Integer nroTotRec) {
		this.nroTotRec = nroTotRec;
	}
	public java.lang.Integer getNroRecInPagina() {
		return nroRecInPagina;
	}
	public void setNroRecInPagina(java.lang.Integer nroRecInPagina) {
		this.nroRecInPagina = nroRecInPagina;
	}
	public java.lang.String getResult() {
		return result;
	}
	public void setResult(java.lang.String result) {
		this.result = result;
	}
	public java.lang.String getErrContext() {
		return errContext;
	}
	public void setErrContext(java.lang.String errContext) {
		this.errContext = errContext;
	}
	public java.lang.Integer getErrCode() {
		return errCode;
	}
	public void setErrCode(java.lang.Integer errCode) {
		this.errCode = errCode;
	}
	public java.lang.String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(java.lang.String errMsg) {
		this.errMsg = errMsg;
	}
		
}
