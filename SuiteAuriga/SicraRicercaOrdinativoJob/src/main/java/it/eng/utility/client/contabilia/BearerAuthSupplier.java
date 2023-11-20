/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URI;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;
import org.apache.log4j.Logger;

import it.eng.utility.tokenretry.TokenRetry;

public final class BearerAuthSupplier implements HttpAuthSupplier {
	
	private String apiTokenRetry;
	private String consumerKey;
	private String consumerSecret;
	
	private static final Logger logger = Logger.getLogger(BearerAuthSupplier.class);
	
	@Override
	public boolean requiresRequestCaching() {
		return false;
	}
	
	@Override
	public String getAuthorization(AuthorizationPolicy authPolicy, URI url, Message message, String fullHeader) {
		return getTokenAuth();
	}
	
	public String getApiTokenRetry() {
		return apiTokenRetry;
	}
	
	public void setApiTokenRetry(String apiTokenRetry) {
		this.apiTokenRetry = apiTokenRetry;
	}
	
	public String getConsumerKey() {
		return consumerKey;
	}
	
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	
	public String getConsumerSecret() {
		return consumerSecret;
	}
	
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
	
	private String getTokenAuth() {
		String token = "";
		
		try {
			// acquisizione token tramite libreria token-retry: se sceduto viene aggiornato
			TokenRetry oauthHelper = new TokenRetry(apiTokenRetry, consumerKey, consumerSecret);
			
			token = "Bearer " + oauthHelper.getToken();
			
			logger.info("Token per autenticazione ai servizi WS di contabilia: " + token);
		} catch (Exception e) {
			logger.error("Errore generazione token: " + e.getMessage());
		}
		
		return token;
	}
	
}
