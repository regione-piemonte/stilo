/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class SmartCardAuthorizationException extends Exception{


	public SmartCardAuthorizationException() {
		super();
	}

	public SmartCardAuthorizationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SmartCardAuthorizationException(String arg0) {
		super(arg0);
	}

	public SmartCardAuthorizationException(Throwable arg0) {
		super(arg0);
	}
}
