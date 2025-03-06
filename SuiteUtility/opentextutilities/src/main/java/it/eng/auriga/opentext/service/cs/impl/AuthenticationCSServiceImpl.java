package it.eng.auriga.opentext.service.cs.impl;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.opentext.livelink.service.authentication.Authentication;
import com.opentext.livelink.service.authentication.Authentication_Service;
import com.sun.xml.ws.developer.WSBindingProvider;

import it.eng.auriga.opentext.exception.AuthenticationException;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.AuthenticationCSService;
import it.eng.auriga.opentext.service.cs.bean.OTAuthenticationInfo;

@Component
public class AuthenticationCSServiceImpl extends AbstractManageCSService implements AuthenticationCSService {

	private static Logger logger = Logger.getLogger(AuthenticationCSServiceImpl.class);

	private String endpoint;

	public AuthenticationCSServiceImpl(String endpoint) {
		super();
		this.endpoint = endpoint;

	}

	public AuthenticationCSServiceImpl() {
		super();
	}

	public OTAuthenticationInfo executeAuthentication() throws MalformedURLException, AuthenticationException {
		return executeAuthentication(null, null);
	}

	public OTAuthenticationInfo executeAuthentication(String username, String password)
			throws MalformedURLException, AuthenticationException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		if (username == null || "".equals(username) || password == null || "".equals(password)) {
			username = settingcs.getAuthenticationUser();
			password = settingcs.getAuthenticationPassword();
		}
		// Create the Authentication service client
		Authentication_Service authService = null;
		if (this.endpoint == null) {
			endpoint = settingcs.getAuthenticationWsdl();
		}

		authService = new Authentication_Service(new URL(endpoint));
		
		Authentication authClient = authService.getBasicHttpBindingAuthentication(endpoint);

		// Store the authentication token
		String authToken = null;
		OTAuthenticationInfo authInfo = null;
		// Call the AuthenticateUser() method to get an authentication token
		try {
			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Autenticazione utente...");

			logger.info("Autenticazione dell'utente " + username);
			authToken = authClient.authenticateUser(username, password);
			logger.info("authToken " + authToken);

			if (soapHeaderService == null)
				soapHeaderService = new SoapHeaderServiceImpl();

			soapHeaderService.setAuthSOAPHeader(authToken, (WSBindingProvider) authClient);

			// setto la data di espirazione del token

			authInfo = new OTAuthenticationInfo(authToken,
					authClient.getSessionExpirationDate().toGregorianCalendar().getTime());

			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " >> Autenticazione avvenuta con successo");

		} catch (Exception e) {
			logger.error("Errore durante l'autenticazione verso il documentale: " + e.getMessage(), e);
			throw new AuthenticationException();
		}
		return authInfo;

		// OTAuthenticationInfo authInfo = null;
		// authInfo = new
		// OTAuthenticationInfo("4564546546546465465464564564654645646456463",
		// new Date());

	}

	public OTAuthenticationInfo executeImpersonation(String usernameToImpersonate, String adminUsername,
			String adminPassword) throws ContentServerException, MalformedURLException {

		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		// Create the Authentication service client
		Authentication_Service authService = new Authentication_Service(new URL(settingcs.getAuthenticationWsdl()));
		if (this.endpoint == null) {
			endpoint = settingcs.getAuthenticationWsdl();
		}

		Authentication authClient = authService.getBasicHttpBindingAuthentication(endpoint);

		// Store the authentication token
		String authToken = null;
		OTAuthenticationInfo authInfo = null;
		// Call the AuthenticateUser() method to get an authentication token
		try {
			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Autenticazione utente..."
					+ usernameToImpersonate);

			authToken = authClient.authenticateUser(settingcs.getAuthenticationUser(),
					settingcs.getAuthenticationPassword());

			soapHeaderService.setAuthSOAPHeader(authToken, (WSBindingProvider) authClient);

			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Staccato token per utente "
					+ settingcs.getAuthenticationUser());
			authToken = authClient.impersonateUser(usernameToImpersonate);

			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> Staccato token per utente "
					+ usernameToImpersonate);

			authInfo = new OTAuthenticationInfo(authToken,
					authClient.getSessionExpirationDate().toGregorianCalendar().getTime());

			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()
					+ " >> Autenticazione avvenuta con successo");

		} catch (Exception e) {
			logger.error("Errore durante l'autenticazione verso il documentale: " + e.getMessage(), e);
			throw new ContentServerException(settingcs.getAuthException());
		}
		return authInfo;

		// OTAuthenticationInfo authInfo = null;
		// authInfo = new
		// OTAuthenticationInfo("4564546546546465465464564564654645646456463",
		// new Date());

	}

}