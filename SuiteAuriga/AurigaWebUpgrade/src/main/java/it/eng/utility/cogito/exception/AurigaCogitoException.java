/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AurigaCogitoException extends Exception {

	private static final long serialVersionUID = -4532202906096679697L;

	public AurigaCogitoException(String message) {
		super(message);
	}

	public AurigaCogitoException(String message, Throwable throwable) {
		super(message, throwable);
	}

}