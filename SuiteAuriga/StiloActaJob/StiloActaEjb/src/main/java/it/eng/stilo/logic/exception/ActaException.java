/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * This class represents an exception occurred when a generic object is not available.
 */
@SuppressWarnings("serial")
public class ActaException extends Exception {

    /**
     * Default empty constructor.
     */
    public ActaException() {
        super();
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message   The message to be used for the exception.
     * @param throwable The originating throwable.
     */
    public ActaException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message The message to be used for the exception.
     */
    public ActaException(final String message) {
        super(message);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param throwable The originating throwable.
     */
    public ActaException(final Throwable throwable) {
        super(throwable);
    }

}
