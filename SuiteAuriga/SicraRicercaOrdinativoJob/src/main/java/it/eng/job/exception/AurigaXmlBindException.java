/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AurigaXmlBindException extends Exception {

	private static final long serialVersionUID = 4573307735697957361L;

	public AurigaXmlBindException() {
	}

	public AurigaXmlBindException(String message) {
		super(message);
	}

	public AurigaXmlBindException(Throwable cause) {
		super(cause);
	}

	public AurigaXmlBindException(String message, Throwable cause) {
		super(message, cause);
	}

}
