package it.eng.hsm.client.exception;

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
