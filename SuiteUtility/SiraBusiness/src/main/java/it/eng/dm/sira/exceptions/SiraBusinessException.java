package it.eng.dm.sira.exceptions;

public class SiraBusinessException extends Exception {

	private static final long serialVersionUID = -5143162984124483548L;

	protected String errMessage;

	protected Exception errException;

	public SiraBusinessException(String message, Exception e) {
		super(message + " " + e.getMessage(), e);
		this.errMessage = message;
		this.errException = e;
	}

	public SiraBusinessException(String message) {
		super(message);
		this.errMessage = message;
	}
}
