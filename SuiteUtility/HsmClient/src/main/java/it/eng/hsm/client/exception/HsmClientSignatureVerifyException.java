package it.eng.hsm.client.exception;

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