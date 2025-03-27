/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.aurigamailbusiness.exception;

public class NoAdressesException extends Exception {
	
	
	private static final long serialVersionUID = -3819507686368416858L;

	protected String errMessage;

	protected Exception errException;

	public NoAdressesException(String message, Exception e) {
		super(message + " " + e.getMessage(), e);
		this.errMessage = message;
		this.errException = e;
	}

	public NoAdressesException(String message) {
		super(message);
		this.errMessage = message;
	}

}
