/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

/**
 * Classe che rappresenta un errore di funzionamento dello storage
 * 
 */
public class StorageException extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;

	public StorageException() {
		super();
	}

	public StorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(String message) {
		super(message);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}

}
