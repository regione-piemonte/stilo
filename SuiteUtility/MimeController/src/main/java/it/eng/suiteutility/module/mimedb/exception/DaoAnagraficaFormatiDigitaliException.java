/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class DaoAnagraficaFormatiDigitaliException extends Exception implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8507466212623790678L;
	protected String errMessage;
    protected Exception errException;

	public DaoAnagraficaFormatiDigitaliException(String message, Exception e)	
	{
		super(message+" "+e.getMessage(), e);
		this.errMessage = message;
   	    this.errException = e;
	}
	
	public DaoAnagraficaFormatiDigitaliException(String message)	
	{
		super(message);
		this.errMessage = message;
	}

}