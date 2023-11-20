/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.exception.JDBCConnectionException;

public class AurigaDAOException extends Exception {

	private static final long serialVersionUID = 7105191677662043840L;
	
	private boolean connectionError = false;

	public AurigaDAOException() {
	}

	public AurigaDAOException(String message) {
		super(message);
	}

	public AurigaDAOException(Throwable cause) {
		super(cause);
		connectionError = (cause instanceof JDBCConnectionException);
	}

	public AurigaDAOException(String message, Throwable cause) {
		super(message, cause);
		connectionError = (cause instanceof JDBCConnectionException);
	}
	
	public boolean isConnectionError() {
		return connectionError;
	}

}
