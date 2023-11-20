/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalvaFascicoloOut implements Serializable{

	private BigDecimal idFolderOut;
	private Integer errorCode;
	private String errorContext;
	private String defaultMessage;
	private boolean inError;
	private Map<String, String> fileInErrors;	

	public BigDecimal getIdFolderOut() {
		return idFolderOut;
	}

	public void setIdFolderOut(BigDecimal idFolderOut) {
		this.idFolderOut = idFolderOut;
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
