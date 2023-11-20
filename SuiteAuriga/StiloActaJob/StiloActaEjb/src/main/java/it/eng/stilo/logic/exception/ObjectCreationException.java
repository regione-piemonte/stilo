/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * This class represents an exception occurred when a generic object cannot be created.
 */
@SuppressWarnings("serial")
public class ObjectCreationException extends Exception {

    /**
     * Default empty constructor.
     */
    public ObjectCreationException() {
        super();
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message   The message to be used for the exception.
     * @param throwable The originating throwable.
     */
    public ObjectCreationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message The message to be used for the exception.
     */
    public ObjectCreationException(final String message) {
        super(message);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param throwable The originating throwable.
     */
    public ObjectCreationException(final Throwable throwable) {
        super(throwable);
    }

}
