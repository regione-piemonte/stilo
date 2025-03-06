package it.eng.suiteutility.module.mimedb.exception;

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