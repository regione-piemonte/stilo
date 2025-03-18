/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FOConversionException extends Exception {
	
	protected String error_message = "";
	
	/**
	 * display the error message
	 */
	public String getMessage()
	{
		return error_message;
	}
	
	public FOConversionException(){}
	
	/**set message at exception*/
	public FOConversionException( String message ) 
	{
		error_message = message;
	}
}
