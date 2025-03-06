package test;
import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;


public class MyCallback2 implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,UnsupportedCallbackException {
		System.out.println("PASSO DI QUII 2");
		
		PasswordCallback pass = (PasswordCallback)callbacks[0];
		pass.setPassword("46303".toCharArray());
		
	}

}
