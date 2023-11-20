/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class TooManyAdressesException extends Exception {
	
	
	private static final long serialVersionUID = -3819507686368416858L;

	protected String errMessage;

	protected Exception errException;

	public TooManyAdressesException(String message, Exception e) {
		super(message + " " + e.getMessage(), e);
		this.errMessage = message;
		this.errException = e;
	}

	public TooManyAdressesException(String message) {
		super(message);
		this.errMessage = message;
	}

}
