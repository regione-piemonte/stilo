/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class HsmClientSignatureVerifyException extends Exception {

	public HsmClientSignatureVerifyException() {
	}

	public HsmClientSignatureVerifyException(String message) {
	    super(message);
	}
	public HsmClientSignatureVerifyException(String message, Throwable cause) {
	   super(message, cause);
	}	
}