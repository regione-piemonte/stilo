/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class FOException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8812188589187727324L;
	
 
	protected String errMessage;
	protected String argumets;
    protected Exception errException;

	public FOException(String message, Exception e)	
	{
		super(message+" "+e.getMessage(), e);
		this.errMessage = message;
   	    this.errException = e;
	}
	
	public FOException(String message, String arguments, Exception e)	
	{
		super(message+" "+e.getMessage(), e);
		this.errMessage = message;
		this.argumets = arguments;
		this.errException = e;
	}
	
	public FOException(String message)	
	{
		super(message);
		this.errMessage = message;
	}

	public String getArgumets() {
		return argumets;
	}

	public void setArgumets(String argumets) {
		this.argumets = argumets;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	
}
