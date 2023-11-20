/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class OperazioniAurigaLottiDocException extends Exception {

	private static final long serialVersionUID = -8734670594651089980L;

	public OperazioniAurigaLottiDocException() {
	}

	public OperazioniAurigaLottiDocException(String message) {
		super(message);
	}

	public OperazioniAurigaLottiDocException(Throwable cause) {
		super(cause);
	}

	public OperazioniAurigaLottiDocException(String message, Throwable cause) {
		super(message, cause);
	}

}
