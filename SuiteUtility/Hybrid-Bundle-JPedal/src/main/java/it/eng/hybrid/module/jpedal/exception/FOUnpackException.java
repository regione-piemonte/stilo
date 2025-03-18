/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
