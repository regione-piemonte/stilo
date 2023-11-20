/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class StorageRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StorageRuntimeException() {
		super();
	}

	public StorageRuntimeException(String message) {
		super(message);
	}

	public StorageRuntimeException(Throwable cause) {
		super(cause);
	}

	public StorageRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
