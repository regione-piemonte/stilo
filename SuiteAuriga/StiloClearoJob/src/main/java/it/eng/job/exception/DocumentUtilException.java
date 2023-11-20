/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DocumentUtilException extends Exception {

	private static final long serialVersionUID = 694580717819445899L;

	public DocumentUtilException() {
	}

	public DocumentUtilException(String message) {
		super(message);
	}

	public DocumentUtilException(Throwable cause) {
		super(cause);
	}

	public DocumentUtilException(String message, Throwable cause) {
		super(message, cause);
	}

}
