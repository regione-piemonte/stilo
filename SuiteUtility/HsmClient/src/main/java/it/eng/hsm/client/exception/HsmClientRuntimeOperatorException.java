package it.eng.hsm.client.exception;

import org.bouncycastle.operator.RuntimeOperatorException;

public class HsmClientRuntimeOperatorException extends RuntimeOperatorException {

	public HsmClientRuntimeOperatorException(String message) {
		super(message);
	}

	public HsmClientRuntimeOperatorException(String message, Throwable cause) {
		super(message, cause);
	}

}
