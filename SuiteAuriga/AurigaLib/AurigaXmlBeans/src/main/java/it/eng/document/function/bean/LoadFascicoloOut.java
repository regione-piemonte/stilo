/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoadFascicoloOut implements Serializable{

	private XmlFascicoloOut xmlFascicoloOut;
	private Integer errorCode;
	private String errorContext;
	private String defaultMessage;
	private boolean inError;

	public void setXmlFascicoloOut(XmlFascicoloOut xmlFascicoloOut) {
		this.xmlFascicoloOut = xmlFascicoloOut;
	}

	public XmlFascicoloOut getXmlFascicoloOut() {
		return xmlFascicoloOut;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorContext() {
		return errorContext;
	}

	public void setErrorContext(String errorContext) {
		this.errorContext = errorContext;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public boolean isInError() {
		return inError;
	}

	public void setInError(boolean inError) {
		this.inError = inError;
	}

}
