/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.IClientCredential;
import com.microsoft.aad.msal4j.MsalException;

public class AccessToken {
	
	private static Logger logger = LogManager.getLogger(AccessToken.class);
    
	public AccessToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    public String OAuth2ClientCredentials(AccessTokenBean bn)
    {
    	logger.info("OAuth2ClientCredentials - INIZIO");
    	
    	logger.info("OAuth2ClientCredentials - scope: "+bn.getScope());
    	logger.info("OAuth2ClientCredentials - clientId: "+bn.getClientId());
    	logger.info("OAuth2ClientCredentials - authority: "+bn.getAuthority());
    	
    	Set<String> set = new HashSet<>(Arrays.asList(bn.getScope()
    		));
        IClientCredential credential = ClientCredentialFactory.createFromSecret(bn.getClientSecret());
        
    	ConfidentialClientApplication cca=null;
		try {
			cca = ConfidentialClientApplication
			        .builder(bn.getClientId(), credential)
			        .authority(bn.getAuthority())
			        .build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.info("MalformedURLException: "+e.getMessage());
		}catch (Exception ex) {
			logger.error("ex "+ex.getMessage());
		}
    	
    	IAuthenticationResult result=null;
        try {
        	ClientCredentialParameters parameters = ClientCredentialParameters.builder(set).build();

            // try to acquire token silently. This call will fail since the token cache does not
            // have a token for the application you are requesting an access token for
            result = cca.acquireToken(parameters).join();
        } catch (Exception ex) {
            if (ex.getCause() instanceof MsalException) {

                ClientCredentialParameters parameters =
                        ClientCredentialParameters
                                .builder(set)
                                .build();

                // Try to acquire a token. If successful, you should see
                // the token information printed out to console
                result = cca.acquireToken(parameters).join();
            } else {
                // Handle other exceptions accordingly
            	logger.error("ex "+ex.getMessage());
            }
        }
        logger.info("accessToken "+result.accessToken());
        logger.info("Scope: " + result.scopes());
        logger.info("Expires: " + result.expiresOnDate());
        
        logger.info("OAuth2ClientCredentials - FINE");
    	return result.accessToken();
    }
    
}
