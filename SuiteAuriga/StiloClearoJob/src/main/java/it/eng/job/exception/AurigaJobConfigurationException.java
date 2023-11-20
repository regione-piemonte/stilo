/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AurigaJobConfigurationException extends Exception {

	private static final long serialVersionUID = -44239572869401081L;

	public AurigaJobConfigurationException() {
	}

	public AurigaJobConfigurationException(String message) {
		super(message);
	}

	public AurigaJobConfigurationException(Throwable cause) {
		super(cause);
	}

	public AurigaJobConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
