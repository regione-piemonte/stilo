/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.Logger;

import it.eng.utility.tokenretry.TokenRetry;

public class BearerAuthSupplier {
	
	private static final Logger logger = Logger.getLogger(BearerAuthSupplier.class);
	
	private String apiTokenRetry;
	private String consumerKey;
	private String consumerSecret;
	
	public String getTokenAuth() {
		String token = "";
		
		try {
			// acquisizione token tramite libreria token-retry: se sceduto viene aggiornato
			TokenRetry oauthHelper = new TokenRetry(apiTokenRetry, consumerKey, consumerSecret);
			
			token = "Bearer " + oauthHelper.getToken();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return token;
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
	
}
