/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class DaoException extends Exception {

	private static final long serialVersionUID = -1931626823735955575L;
	protected String errMessage;
	protected Exception errException;

	public DaoException(String message, Exception e)	
	{
		super(message+" "+e.getMessage(), e);
		this.errMessage = message;
		this.errException = e;
	}

	public DaoException(String message)	
	{
		super(message);
		this.errMessage = message;
	}
	
	public DaoException(Exception e) {
		super(e);
		this.errException = e;
	}

}
