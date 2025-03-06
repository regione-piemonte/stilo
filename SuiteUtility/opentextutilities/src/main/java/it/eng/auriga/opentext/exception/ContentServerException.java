package it.eng.auriga.opentext.exception;

public class ContentServerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
private String message;
	
	public ContentServerException() {
		// TODO Auto-generated constructor stub
	}
	
	public ContentServerException(String msg) {
		this.message=msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
