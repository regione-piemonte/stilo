/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AurigaLoginException extends Exception {

	private static final long serialVersionUID = 7105191677662043840L;

	public AurigaLoginException() {
	}

	public AurigaLoginException(String message) {
		super(message);
	}

	public AurigaLoginException(Throwable cause) {
		super(cause);
	}

	public AurigaLoginException(String message, Throwable cause) {
		super(message, cause);
	}

}
