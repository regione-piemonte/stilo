/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GestioneAllegatiPraticaOutBean implements Serializable {

	private Map<String, String> fileInErrors;
	private Integer errorCode;
	private String errorContext;
	private String storeName;
	private String defaultMessage;
	private boolean inError;
	
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	public Map<String, String> getFileInErrors() {
		return fileInErrors;
	}
	public void setFileInErrors(Map<String, String> fileInErrors) {
		this.fileInErrors = fileInErrors;
	}
	
}
