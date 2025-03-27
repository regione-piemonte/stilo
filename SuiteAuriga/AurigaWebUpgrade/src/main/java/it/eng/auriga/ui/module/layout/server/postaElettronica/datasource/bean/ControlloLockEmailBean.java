/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

import java.io.Serializable;

public class ControlloLockEmailBean implements Serializable{
	
	private boolean flagPresenzaLock;
	private boolean flagForzaLock;
	private String errorMessage;
	
	public boolean isFlagPresenzaLock() {
		return flagPresenzaLock;
	}
	public void setFlagPresenzaLock(boolean flagPresenzaLock) {
		this.flagPresenzaLock = flagPresenzaLock;
	}
	public boolean isFlagForzaLock() {
		return flagForzaLock;
	}
	public void setFlagForzaLock(boolean flagForzaLock) {
		this.flagForzaLock = flagForzaLock;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
