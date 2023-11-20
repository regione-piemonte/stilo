/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class InvalidEncryptionKeyException extends Exception {

	private static final long serialVersionUID = -4532202906096679697L;

	public InvalidEncryptionKeyException(String message) {
		super(message);
	}

	public InvalidEncryptionKeyException(String message, Throwable throwable) {
		super(message, throwable);
	}

}