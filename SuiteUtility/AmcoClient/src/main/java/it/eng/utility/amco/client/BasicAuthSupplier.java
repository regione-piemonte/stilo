package it.eng.utility.amco.client;

import java.net.URI;
import java.util.Base64;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;
import org.apache.log4j.Logger;

public class BasicAuthSupplier implements HttpAuthSupplier {
	
	private String username;
	private String password;
	
	private static final Logger logger = Logger.getLogger(BasicAuthSupplier.class);
	
	@Override
	public String getAuthorization(AuthorizationPolicy authPolicy, URI url, Message message, String fullHeader) {
		return generaTokenAuth();
	}

	@Override
	public boolean requiresRequestCaching() {
		return false;
	}
	
	private String generaTokenAuth() {
		String result = null;
		
		String auth = username + ":" + password;
		result = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
		
		logger.info("Token per basic auth: " + result);
		
		return result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
