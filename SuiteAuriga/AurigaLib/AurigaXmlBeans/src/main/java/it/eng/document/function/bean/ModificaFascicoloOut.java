/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModificaFascicoloOut implements Serializable {

	private String uriPerAggiornamentoContainer;
	private Integer errorCode;
	private String errorContext;
	private String defaultMessage;
	private boolean inError;
	private Map<String, String> fileInErrors;	

	public void setUriPerAggiornamentoContainer(
			String uriPerAggiornamentoContainer) {
		this.uriPerAggiornamentoContainer = uriPerAggiornamentoContainer;
	}

	public String getUriPerAggiornamentoContainer() {
		return uriPerAggiornamentoContainer;
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

	public Map<String, String> getFileInErrors() {
		return fileInErrors;
	}

	public void setFileInErrors(Map<String, String> fileInErrors) {
		this.fileInErrors = fileInErrors;
	}
}
