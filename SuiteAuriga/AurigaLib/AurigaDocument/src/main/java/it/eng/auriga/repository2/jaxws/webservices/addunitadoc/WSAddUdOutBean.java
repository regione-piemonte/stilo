/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.addunitadoc;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSAddUdOutBean implements Serializable {

	
	private String xmlRegOut;
	private String warnRegOut;
	private Integer errorCodeStore;

	
	public String getXmlRegOut() {
		return xmlRegOut;
	}
	public void setXmlRegOut(String xmlRegOut) {
		this.xmlRegOut = xmlRegOut;
	}
	public String getWarnRegOut() {
		return warnRegOut;
	}
	public void setWarnRegOut(String warnRegOut) {
		this.warnRegOut = warnRegOut;
	}
	public void setErrorCode(Integer errorCode) {
		// TODO Auto-generated method stub
		
	}
	public Integer getErrorCodeStore() {
		return errorCodeStore;
	}
	public void setErrorCodeStore(Integer errorCodeStore) {
		this.errorCodeStore = errorCodeStore;
	}
		
	}
