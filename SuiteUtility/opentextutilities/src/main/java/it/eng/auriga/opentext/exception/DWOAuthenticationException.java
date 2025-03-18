/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.springframework.security.core.AuthenticationException;

public class DWOAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DWOAuthenticationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	
	public DWOAuthenticationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
