package it.eng.client.applet.operation;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class PasswordHandler implements CallbackHandler {

	private char[] pin;
	
	public PasswordHandler(char[] pin) {
		this.pin = pin;
	}
	
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		//Provo ad effettuare il login
		for(Callback callback:callbacks){
			if(callback instanceof PasswordCallback){
				((PasswordCallback)callback).setPassword(this.pin);
			}
		}
	}	
}