/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FileManagerException extends Exception {

	private static final long serialVersionUID = -1585134358797356243L;

	public FileManagerException() {
	}

	public FileManagerException(String message) {
		super(message);
	}

	public FileManagerException(Throwable cause) {
		super(cause);
	}

	public FileManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
