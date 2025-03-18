/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.csi.wso2.apiman.oauth2.helper.OauthHelper;

public class TokenRetry extends OauthHelper {
	
	public TokenRetry(String oauthURL, String consumerKey, String consumerSecret) {
		super(oauthURL, consumerKey, consumerSecret);
	}
	
}
