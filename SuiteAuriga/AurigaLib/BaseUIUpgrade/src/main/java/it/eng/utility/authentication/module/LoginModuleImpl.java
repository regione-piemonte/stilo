/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.log4j.Logger;

public class LoginModuleImpl  implements LoginModule {

	private String username;
	private String password;
	private boolean verification;
	Callback[] calls=new Callback[2];
	
	private static Logger mLogger = Logger.getLogger(LoginModuleImpl.class);
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		mLogger.debug("initialize() LoginModuleImpl - BaseUI");
		calls[0]=new NameCallback("name");
		calls[1]=new PasswordCallback("Password",false);
		try {
			callbackHandler.handle(calls);
		} catch (IOException e) {
			mLogger.error(e);
		} catch (UnsupportedCallbackException e) {
			mLogger.error(e);
		}
		NameCallback lNameCallback = (NameCallback) calls[0];
		username = lNameCallback.getName();

		PasswordCallback lPasswordCallback = (PasswordCallback) calls[1];
		password = new String(lPasswordCallback.getPassword());
		
	}

	@Override
	public boolean login() throws LoginException {
		mLogger.debug("login() LoginModuleImpl - BaseUI");
		if (username.toUpperCase().equals("ADMIN")){
			verification = true;
			return true;
		}
		else throw new FailedLoginException();
	}

	@Override
	public boolean commit() throws LoginException {
		mLogger.debug("commit() LoginModuleImpl - BaseUI");
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		mLogger.debug("abort() LoginModuleImpl - BaseUI");
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		mLogger.debug("logout() LoginModuleImpl - BaseUI");
		return false;
	}

}
