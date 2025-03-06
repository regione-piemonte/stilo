package it.eng.utility.tokenretry;

import it.csi.wso2.apiman.oauth2.helper.OauthHelper;

public class TokenRetry extends OauthHelper {
	
	public TokenRetry(String oauthURL, String consumerKey, String consumerSecret) {
		super(oauthURL, consumerKey, consumerSecret);
	}
	
}
