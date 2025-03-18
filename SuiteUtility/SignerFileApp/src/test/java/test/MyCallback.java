/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;


public class MyCallback implements CallbackHandler {


	public void handle(Callback[] callbacks) throws IOException,UnsupportedCallbackException {
		System.out.println("PASSO DI QUII");
		
		PasswordCallback pass = (PasswordCallback)callbacks[0];
		pass.setPassword("11433".toCharArray());
			
	}

}
