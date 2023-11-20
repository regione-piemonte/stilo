/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StoreResultBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5575486994279641333L;
	private T resultBean;
	private Integer errorCode;
	private String errorContext;
	private String storeName;
	private String defaultMessage;
	private boolean inError;

	public void setResultBean(T resultBean) {
		this.resultBean = resultBean;
	}

	public T getResultBean() {
		return resultBean;
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
	
	public void setInError(boolean inError) {
		this.inError = inError;
	}

	public boolean isInError() {
		return inError;
	}
	
}
