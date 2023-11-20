/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class MailAutoProtocolException extends Exception {
	
	public MailAutoProtocolException() {
		super();
	}

	public MailAutoProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailAutoProtocolException(String message) {
		super(message);
	}

	public MailAutoProtocolException(Throwable cause) {
		super(cause);
	}
}
