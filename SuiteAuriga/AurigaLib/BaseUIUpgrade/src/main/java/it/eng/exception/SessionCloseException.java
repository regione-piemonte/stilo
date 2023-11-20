/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.exception.manager.SessionCloseExceptionManager;
import it.eng.utility.ui.module.core.shared.annotation.ManagedException;

@ManagedException(gestore=SessionCloseExceptionManager.class)
public class SessionCloseException extends Exception {

	private static final long serialVersionUID = 1L;

	public SessionCloseException() {
		super();
	}

	public SessionCloseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SessionCloseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionCloseException(String message) {
		super(message);
	}

	public SessionCloseException(Throwable cause) {
		super(cause);
	}
}
