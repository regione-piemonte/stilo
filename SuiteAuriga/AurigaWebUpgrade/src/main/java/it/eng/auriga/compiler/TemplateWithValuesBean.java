/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

public class TemplateWithValuesBean {
	protected File fileGenerato;
	// Il file generato nel formato odt originale
	protected File fileOdtGenerato;
	
	protected String errorMessage;
	protected boolean isInError = false;
	
	public boolean isInError() {
		return isInError;
	}

	public void setInError(boolean isInError) {
		this.isInError = isInError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public File getFileGenerato() {
		return fileGenerato;
	}
	
	public void setFileGenerato(File fileGenerato) {
		this.fileGenerato = fileGenerato;
	}

	public File getFileOdtGenerato() {
		return fileOdtGenerato;
	}

	public void setFileOdtGenerato(File fileOdtGenerato) {
		this.fileOdtGenerato = fileOdtGenerato;
	}
	
}
