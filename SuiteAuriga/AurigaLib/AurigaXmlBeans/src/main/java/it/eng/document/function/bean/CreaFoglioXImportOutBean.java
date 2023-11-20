/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaFoglioXImportOutBean implements Serializable {


	private static final long serialVersionUID = -47146015794955127L;
	
	private String idFoglio;
	private Integer errorCode;
	private String errorContext;
	private String storeName;
	private String defaultMessage;
	
	public String getIdFoglio() {
		return idFoglio;
	}
	
	public void setIdFoglio(String idFoglio) {
		this.idFoglio = idFoglio;
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
	
	
}
