/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class MailSaverException extends Exception {

	private static final long serialVersionUID = -9175417095248688455L;
	private EXCEPTION_TYPE tipoEccezione;

	public MailSaverException(EXCEPTION_TYPE tipoEccezione) {
		this.tipoEccezione = tipoEccezione;
	}

	public MailSaverException(EXCEPTION_TYPE tipoEccezione, String message) {
		super(message);
		this.tipoEccezione = tipoEccezione;
	}

	public MailSaverException(EXCEPTION_TYPE tipoEccezione, Throwable cause) {
		super(cause);
		this.tipoEccezione = tipoEccezione;
	}

	public MailSaverException(EXCEPTION_TYPE tipoEccezione, String message, Throwable cause) {
		super(message, cause);
		this.tipoEccezione = tipoEccezione;
	}

	public MailSaverException(EXCEPTION_TYPE tipoEccezione, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.tipoEccezione = tipoEccezione;
	}
	
	public boolean isSaveException() {
		return EXCEPTION_TYPE.SAVE == tipoEccezione;
	}
	
	public boolean isUpdateException() {
		return EXCEPTION_TYPE.UPDATE == tipoEccezione;
	}

	public static enum EXCEPTION_TYPE {
		SAVE, UPDATE;
	}
}
