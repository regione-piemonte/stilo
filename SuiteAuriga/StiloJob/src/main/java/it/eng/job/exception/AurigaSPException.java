/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AurigaSPException extends Exception {

	private static final long serialVersionUID = 6525935552935407811L;

	public AurigaSPException() {
	}

	public AurigaSPException(String message) {
		super(message);
	}

	public AurigaSPException(Throwable cause) {
		super(cause);
	}

	public AurigaSPException(String message, Throwable cause) {
		super(message, cause);
	}

}
