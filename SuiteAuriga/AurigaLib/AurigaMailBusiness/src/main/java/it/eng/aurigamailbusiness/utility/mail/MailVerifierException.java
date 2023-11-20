/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class MailVerifierException extends Exception {

	public MailVerifierException() {
		super();
	}

	public MailVerifierException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailVerifierException(String message) {
		super(message);
	}

	public MailVerifierException(Throwable cause) {
		super(cause);
	}
}
