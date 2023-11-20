/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CancellaFascicoloOut implements Serializable{

	private String uriOut;
	private Integer errorCode;
	private String errorContext;
	private String defaultMessage;
	private boolean inError;
	public String getUriOut() {
		return uriOut;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public String getErrorContext() {
		return errorContext;
	}
	public String getDefaultMessage() {
		return defaultMessage;
	}
	public boolean isInError() {
		return inError;
	}
	public void setUriOut(String uriOut) {
		this.uriOut = uriOut;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public void setErrorContext(String errorContext) {
		this.errorContext = errorContext;
	}
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	public void setInError(boolean inError) {
		this.inError = inError;
	}

}
