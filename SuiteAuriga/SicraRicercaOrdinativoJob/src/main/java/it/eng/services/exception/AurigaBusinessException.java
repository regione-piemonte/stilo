/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AurigaBusinessException extends Exception {

	private static final long serialVersionUID = 694580717819445899L;

	public AurigaBusinessException() {
	}

	public AurigaBusinessException(String message) {
		super(message);
	}

	public AurigaBusinessException(Throwable cause) {
		super(cause);
	}

	public AurigaBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

}
