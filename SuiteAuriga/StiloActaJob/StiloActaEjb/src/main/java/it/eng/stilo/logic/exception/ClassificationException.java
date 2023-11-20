/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * This class represents an exception occurred when an operation regarding classification fails.
 */
@SuppressWarnings("serial")
public class ClassificationException extends Exception {

    /**
     * Default empty constructor.
     */
    public ClassificationException() {
        super();
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message   The message to be used for the exception.
     * @param throwable The originating throwable.
     */
    public ClassificationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param message The message to be used for the exception.
     */
    public ClassificationException(final String message) {
        super(message);
    }

    /**
     * Parametric constructor using fields.
     *
     * @param throwable The originating throwable.
     */
    public ClassificationException(final Throwable throwable) {
        super(throwable);
    }

}
