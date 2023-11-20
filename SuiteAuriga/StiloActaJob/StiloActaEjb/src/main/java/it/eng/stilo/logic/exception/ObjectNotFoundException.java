/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * This class represents an exception occurred when a generic object is not available.
 */
@SuppressWarnings("serial")
public class ObjectNotFoundException extends Exception {

    /**
     * Default empty constructor.
     */
    public ObjectNotFoundException() {
        super();
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message   The message to be used for the exception.
     * @param throwable The originating throwable.
     */
    public ObjectNotFoundException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message The message to be used for the exception.
     */
    public ObjectNotFoundException(final String message) {
        super(message);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param throwable The originating throwable.
     */
    public ObjectNotFoundException(final Throwable throwable) {
        super(throwable);
    }

}
