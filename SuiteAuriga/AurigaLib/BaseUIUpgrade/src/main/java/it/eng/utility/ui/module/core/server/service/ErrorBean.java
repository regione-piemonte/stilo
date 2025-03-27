/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.core.server.service;

public class ErrorBean {

	public ErrorBean() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ErrorBean(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}


	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	
}
