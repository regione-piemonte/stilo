/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * This class represents an exception occurred at the database level when using
 * JPA or other ORM framework.
 * 
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception {

	/**
	 * Default empty constructor.
	 */
	public DatabaseException() {
		super();
	}

	/**
	 * Parametric constructor using fields.
	 * 
	 * @param message
	 *            The message to be used for the exception.
	 * @param throwable
	 *            The originating throwable.
	 */
	public DatabaseException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * Parametric constructor using fields.
	 * 
	 * @param message
	 *            The message to be used for the exception.
	 */
	public DatabaseException(final String message) {
		super(message);
	}

	/**
	 * Parametric constructor using fields.
	 * 
	 * @param throwable
	 *            The originating throwable.
	 */
	public DatabaseException(final Throwable throwable) {
		super(throwable);
	}

}
