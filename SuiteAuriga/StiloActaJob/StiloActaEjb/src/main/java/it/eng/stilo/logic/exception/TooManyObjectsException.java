/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * This class represents an exception occurred when multiple instances of a generic object are available.
 */
@SuppressWarnings("serial")
public class TooManyObjectsException extends Exception {

    /**
     * Default empty constructor.
     */
    public TooManyObjectsException() {
        super();
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message   The message to be used for the exception.
     * @param throwable The originating throwable.
     */
    public TooManyObjectsException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message The message to be used for the exception.
     */
    public TooManyObjectsException(final String message) {
        super(message);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param throwable The originating throwable.
     */
    public TooManyObjectsException(final Throwable throwable) {
        super(throwable);
    }

}
