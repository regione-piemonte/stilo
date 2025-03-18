/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class HsmClientSignatureException extends Exception {

	public HsmClientSignatureException() {
	}

	public HsmClientSignatureException(String message) {
	    super(message);
	}
	public HsmClientSignatureException(String message, Throwable cause) {
	   super(message, cause);
	}
	
}
