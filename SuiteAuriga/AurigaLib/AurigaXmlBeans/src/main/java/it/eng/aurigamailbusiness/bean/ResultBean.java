/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = -5575486994279641333L;

	private T resultBean;

	private String errorStackTrace;

	private String defaultMessage;

	private boolean inError;

	public void setResultBean(T resultBean) {
		this.resultBean = resultBean;
	}

	public T getResultBean() {
		return resultBean;
	}

	public String getErrorStackTrace() {
		return errorStackTrace;
	}

	public void setErrorStackTrace(String errorStackTrace) {
		this.errorStackTrace = errorStackTrace;
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
