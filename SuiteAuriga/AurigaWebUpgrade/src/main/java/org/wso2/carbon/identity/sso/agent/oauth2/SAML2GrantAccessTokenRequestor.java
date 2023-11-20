/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.oauth2;

import java.io.InputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import com.google.gson.Gson;
import java.net.URLEncoder;
import org.opensaml.xml.util.Base64;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;
import org.wso2.carbon.identity.sso.agent.bean.SSOAgentSessionBean;
import javax.servlet.http.HttpServletRequest;

public class SAML2GrantAccessTokenRequestor
{
    public static void getAccessToken(final HttpServletRequest request) throws SSOAgentException {
        final String samlAssertionString = ((SSOAgentSessionBean)request.getSession().getAttribute(SSOAgentConfigs.getSessionBeanName())).getSAMLSSOSessionBean().getSAMLAssertionString();
        try {
            final String consumerKey = SSOAgentConfigs.getOAuth2ClientId();
            final String consumerSecret = SSOAgentConfigs.getOAuth2ClientSecret();
            final String tokenEndpoint = SSOAgentConfigs.getTokenEndpoint();
            final String accessTokenResponse = executePost(tokenEndpoint, "grant_type=urn:ietf:params:oauth:grant-type:saml2-bearer&assertion=" + URLEncoder.encode(Base64.encodeBytes(samlAssertionString.getBytes()).replaceAll("\n", "")), Base64.encodeBytes(new String(consumerKey + ":" + consumerSecret).getBytes()).replace("\n", ""));
            final Gson gson = new Gson();
            final SSOAgentSessionBean.AccessTokenResponseBean accessTokenResp = (SSOAgentSessionBean.AccessTokenResponseBean)gson.fromJson(accessTokenResponse, (Class)SSOAgentSessionBean.AccessTokenResponseBean.class);
            ((SSOAgentSessionBean)request.getSession().getAttribute(SSOAgentConfigs.getSessionBeanName())).getSAMLSSOSessionBean().setAccessTokenResponseBean(accessTokenResp);
        }
        catch (Exception e) {
            throw new SSOAgentException("Error while retrieving OAuth2 access token using SAML2 grant type", e);
        }
    }
    
    public static String executePost(final String targetURL, final String urlParameters, final String clientCredentials) throws Exception {
        HttpURLConnection connection = null;
        try {
            final URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic " + clientCredentials);
            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            final InputStream is = connection.getInputStream();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            final StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
