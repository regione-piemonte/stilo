/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.dao.exception;

/**
 * Classe specifica che indica credenziali non presenti
 * 
 * @author Mattia Zanetti
 *
 */

public class DmvConsumerEmptyCredentialException extends Exception {

	public DmvConsumerEmptyCredentialException() {
	}

	public DmvConsumerEmptyCredentialException(String message) {
		super(message);
	}

	public DmvConsumerEmptyCredentialException(Throwable cause) {
		super(cause);
	}

	public DmvConsumerEmptyCredentialException(String message, Throwable cause) {
		super(message, cause);
	}

}
