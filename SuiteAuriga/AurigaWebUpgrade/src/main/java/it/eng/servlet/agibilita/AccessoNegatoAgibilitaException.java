/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.ServletException;

public class AccessoNegatoAgibilitaException extends ServletException {

	public AccessoNegatoAgibilitaException() {
		super();
	}

	public AccessoNegatoAgibilitaException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	public AccessoNegatoAgibilitaException(String message) {
		super(message);
	}

	public AccessoNegatoAgibilitaException(Throwable rootCause) {
		super(rootCause);
	}

}
