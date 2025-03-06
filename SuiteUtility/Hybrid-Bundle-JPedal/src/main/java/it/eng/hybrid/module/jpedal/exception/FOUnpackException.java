package it.eng.hybrid.module.jpedal.exception;

public class FOUnpackException extends Exception {
	
	protected String error_message = "";
	
	/**
	 * display the error message
	 */
	public String getMessage()
	{
		return error_message;
	}
	
	public FOUnpackException(){}
	
	/**set message at exception*/
	public FOUnpackException( String message ) 
	{
		error_message = message;
	}
}
