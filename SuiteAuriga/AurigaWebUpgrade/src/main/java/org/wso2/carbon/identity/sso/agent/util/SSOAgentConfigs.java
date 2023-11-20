/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.util;

import java.io.IOException;
import java.io.FileNotFoundException;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import java.io.FileInputStream;
import java.util.Properties;
import javax.servlet.FilterConfig;
import java.security.KeyStore;
import java.io.InputStream;
import java.util.logging.Logger;

public class SSOAgentConfigs
{
    private static Logger LOGGER;
    private static Boolean samlSSOLoginEnabled;
    private static Boolean openidLoginEnabled;
    private static Boolean saml2GrantEnabled;
    private static Boolean SPIDSSOLoginEnabled;
    private static String sessionBeanName;
    private static String loginUrl;
    private static String samlSSOUrl;
    private static String openIdUrl;
    private static String saml2GrantUrl;
    private static String issuerId;
    private static String consumerUrl;
    private static String idPUrl;
    private static String attributeConsumingServiceIndex;
    private static Boolean isSLOEnabled;
    private static String logoutUrl;
    private static Boolean isResponseSigned;
    private static Boolean isAssertionSigned;
    private static Boolean isAssertionEncrypted;
    private static Boolean isRequestSigned;
    private static Boolean isForceAuthn;
    private static String ssoAgentCredentialImplClass;
    private static InputStream keyStoreStream;
    private static String keyStorePassword;
    private static KeyStore keyStore;
    private static String idPCertAlias;
    private static String privateKeyAlias;
    private static String privateKeyPassword;
    private static String tokenEndpoint;
    private static String clientId;
    private static String clientSecret;
    private static String spidLevel;
    private static String openIdProviderUrl;
    private static String returnTo;
    private static String claimedIdParameterName;
    private static String attributesRequestorImplClass;
    private static String requestQueryParameters;
    private static String addExtension;
    private static String samlAuthRequestNameIdPolicySPNameQualifier;
    private static String samlAuthRequestNameIdPolicyFormat;
    private static String samlAuthRequestSetForceAuthn;
    private static String samlAuthRequestSetRequestedAuthnContext;
    private static String samlAuthRequestRequestedAuthnContextClassRef;
    private static String samlAuthRequestRequestedAuthnContextComparison;
    
    public static void initConfig(final FilterConfig fConfigs) throws SSOAgentException {
        final Properties properties = new Properties();
        try {
            if (fConfigs.getInitParameter("SSOAgentPropertiesFilePath") != null && !fConfigs.getInitParameter("SSOAgentPropertiesFilePath").equals("")) {
                properties.load(new FileInputStream(fConfigs.getInitParameter("SSOAgentPropertiesFilePath")));
                initConfig(properties);
            }
            else {
                SSOAgentConfigs.LOGGER.warning("'SSOAgentPropertiesFilePath' not configured");
            }
        }
        catch (FileNotFoundException e) {
            throw new SSOAgentException("Agent properties file not found");
        }
        catch (IOException e2) {
            throw new SSOAgentException("Error occurred while reading Agent properties file");
        }
    }
    
    public static void initConfig(final String propertiesFilePath) throws SSOAgentException {
        final Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFilePath));
            initConfig(properties);
        }
        catch (FileNotFoundException e) {
            throw new SSOAgentException("Agent properties file not found at " + propertiesFilePath);
        }
        catch (IOException e2) {
            throw new SSOAgentException("Error reading Agent properties file at " + propertiesFilePath);
        }
    }
    
    public static void initConfig(final Properties properties) throws SSOAgentException {
        if (properties.getProperty("EnableSPIDSSOLogin") != null) {
            SSOAgentConfigs.SPIDSSOLoginEnabled = Boolean.parseBoolean(properties.getProperty("EnableSPIDSSOLogin"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'EnableSPIDSSOLogin' not configured. Defaulting to 'false'");
            SSOAgentConfigs.SPIDSSOLoginEnabled = false;
        }
        if (properties.getProperty("EnableSAMLSSOLogin") != null) {
            SSOAgentConfigs.samlSSOLoginEnabled = Boolean.parseBoolean(properties.getProperty("EnableSAMLSSOLogin"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'EnableSAMLSSOLogin' not configured. Defaulting to 'true'");
            SSOAgentConfigs.samlSSOLoginEnabled = true;
        }
        if (properties.getProperty("EnableOpenIDLogin") != null) {
            SSOAgentConfigs.openidLoginEnabled = Boolean.parseBoolean(properties.getProperty("EnableOpenIDLogin"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'EnableOpenIDLogin' not configured. Defaulting to 'true'");
            SSOAgentConfigs.openidLoginEnabled = true;
        }
        if (properties.getProperty("EnableSAML2Grant") != null) {
            SSOAgentConfigs.saml2GrantEnabled = Boolean.parseBoolean(properties.getProperty("EnableSAML2Grant"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'EnableSAML2Grant' not configured. Defaulting to 'true'");
            SSOAgentConfigs.saml2GrantEnabled = true;
        }
        if (properties.getProperty("SSOAgentSessionBeanName") != null) {
            SSOAgentConfigs.sessionBeanName = properties.getProperty("SSOAgentSessionBeanName");
        }
        else {
            SSOAgentConfigs.LOGGER.info("'SSOAgentSessionBeanName' not configured. Defaulting to 'SSOAgentSessionBean'");
            SSOAgentConfigs.sessionBeanName = "SSOAgentSessionBean";
        }
        SSOAgentConfigs.spidLevel = properties.getProperty("SpidLevel");
        SSOAgentConfigs.loginUrl = properties.getProperty("LoginUrl");
        SSOAgentConfigs.samlSSOUrl = properties.getProperty("SAMLSSOUrl");
        SSOAgentConfigs.saml2GrantUrl = properties.getProperty("SAML2GrantUrl");
        SSOAgentConfigs.openIdUrl = properties.getProperty("OpenIDUrl");
        SSOAgentConfigs.issuerId = properties.getProperty("SAML.IssuerID");
        SSOAgentConfigs.consumerUrl = properties.getProperty("SAML.ConsumerUrl");
        SSOAgentConfigs.idPUrl = properties.getProperty("SAML.IdPUrl");
        SSOAgentConfigs.attributeConsumingServiceIndex = properties.getProperty("SAML.AttributeConsumingServiceIndex");
        if (properties.getProperty("SAML.EnableSLO") != null) {
            SSOAgentConfigs.isSLOEnabled = Boolean.parseBoolean(properties.getProperty("SAML.EnableSLO"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'SAML.EnableSLO' not configured. Defaulting to 'false'");
            SSOAgentConfigs.isSLOEnabled = false;
        }
        SSOAgentConfigs.logoutUrl = properties.getProperty("SAML.LogoutUrl");
        if (properties.getProperty("SAML.EnableResponseSigning") != null) {
            SSOAgentConfigs.isResponseSigned = Boolean.parseBoolean(properties.getProperty("SAML.EnableResponseSigning"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'SAML.EnableResponseSigning' not configured. Defaulting to 'false'");
            SSOAgentConfigs.isResponseSigned = false;
        }
        if (properties.getProperty("SAML.EnableAssertionSigning") != null) {
            SSOAgentConfigs.isAssertionSigned = Boolean.parseBoolean(properties.getProperty("SAML.EnableAssertionSigning"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'SAML.EnableAssertionSigning' not configured. Defaulting to 'true'");
            SSOAgentConfigs.isAssertionSigned = true;
        }
        if (properties.getProperty("SAML.EnableAssertionEncryption") != null) {
            SSOAgentConfigs.isAssertionEncrypted = Boolean.parseBoolean(properties.getProperty("SAML.EnableAssertionEncryption"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'SAML.EnableAssertionEncryption' not configured. Defaulting to 'false'");
            SSOAgentConfigs.isAssertionEncrypted = false;
        }
        if (properties.getProperty("SAML.EnableRequestSigning") != null) {
            SSOAgentConfigs.isRequestSigned = Boolean.parseBoolean(properties.getProperty("SAML.EnableRequestSigning"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'SAML.EnableRequestSigning' not configured. Defaulting to 'false'");
            SSOAgentConfigs.isRequestSigned = false;
        }
        if (properties.getProperty("SAML.EnableForceAuthentication") != null) {
            SSOAgentConfigs.isForceAuthn = Boolean.parseBoolean(properties.getProperty("SAML.EnableForceAuthentication"));
        }
        else {
            SSOAgentConfigs.LOGGER.info("'SAML.EnableForceAuthentication' not configured. Defaulting to 'false'");
            SSOAgentConfigs.isForceAuthn = false;
        }
        SSOAgentConfigs.ssoAgentCredentialImplClass = properties.getProperty("SAML.SSOAgentCredentialImplClass");
        if (properties.getProperty("KeyStore") != null) {
            try {
                SSOAgentConfigs.keyStoreStream = new FileInputStream(properties.getProperty("KeyStore"));
            }
            catch (FileNotFoundException e) {
                throw new SSOAgentException("Cannot find file " + properties.getProperty("KeyStore"));
            }
        }
        SSOAgentConfigs.keyStorePassword = properties.getProperty("SAML.KeyStorePassword");
        SSOAgentConfigs.idPCertAlias = properties.getProperty("SAML.IdPCertAlias");
        SSOAgentConfigs.privateKeyAlias = properties.getProperty("SAML.PrivateKeyAlias");
        SSOAgentConfigs.privateKeyPassword = properties.getProperty("SAML.PrivateKeyPassword");
        SSOAgentConfigs.tokenEndpoint = properties.getProperty("SAML.OAuth2TokenEndpoint");
        SSOAgentConfigs.clientId = properties.getProperty("SAML.OAuth2ClientID");
        SSOAgentConfigs.clientSecret = properties.getProperty("SAML.OAuth2ClientSecret");
        SSOAgentConfigs.openIdProviderUrl = properties.getProperty("OpenID.OpenIdProviderUrl");
        SSOAgentConfigs.returnTo = properties.getProperty("OpenID.ReturnToUrl");
        SSOAgentConfigs.claimedIdParameterName = properties.getProperty("OpenID.ClaimedIDParameterName");
        SSOAgentConfigs.attributesRequestorImplClass = properties.getProperty("OpenID.AttributesRequestorImplClass");
        SSOAgentConfigs.requestQueryParameters = properties.getProperty("SAML.Request.Query.Param");
        SSOAgentConfigs.addExtension = properties.getProperty("SAML.Request.Add.Extension");
        if (properties.getProperty("SAML.authRequest.nameIdPolicy.spNameQualifier") != null) {
        	SSOAgentConfigs.samlAuthRequestNameIdPolicySPNameQualifier = properties.getProperty("SAML.authRequest.nameIdPolicy.spNameQualifier");
        } else {
        	SSOAgentConfigs.samlAuthRequestNameIdPolicySPNameQualifier = "";
        }
        if (properties.getProperty("SAML.authRequest.nameIdPolicy.format") != null) {
        	SSOAgentConfigs.samlAuthRequestNameIdPolicyFormat = properties.getProperty("SAML.authRequest.nameIdPolicy.format");
        } else {
        	SSOAgentConfigs.samlAuthRequestNameIdPolicyFormat = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
        }  
        if (properties.getProperty("SAML.authRequest.setForceAuthn") != null) {
        	SSOAgentConfigs.samlAuthRequestSetForceAuthn = properties.getProperty("SAML.authRequest.setForceAuthn");
        } else {
        	SSOAgentConfigs.samlAuthRequestSetForceAuthn = "true";
        }
        if (properties.getProperty("SAML.authRequest.setRequestedAuthnContext") != null) {
        	SSOAgentConfigs.samlAuthRequestSetRequestedAuthnContext = properties.getProperty("SAML.authRequest.setRequestedAuthnContext");
        } else {
        	SSOAgentConfigs.samlAuthRequestSetRequestedAuthnContext = "true";
        }
        if (properties.getProperty("SAML.authRequest.requestedAuthnContext.classRef") != null) {
        	SSOAgentConfigs.samlAuthRequestRequestedAuthnContextClassRef = properties.getProperty("SAML.authRequest.requestedAuthnContext.classRef");
        } else {
        	SSOAgentConfigs.samlAuthRequestRequestedAuthnContextClassRef = "";
        }    
        if (properties.getProperty("SAML.authRequest.requestedAuthnContext.comparison") != null) {
        	SSOAgentConfigs.samlAuthRequestRequestedAuthnContextComparison = properties.getProperty("SAML.authRequest.requestedAuthnContext.comparison");
        } else {
        	SSOAgentConfigs.samlAuthRequestRequestedAuthnContextComparison = "";
        }    
        
    }
    
    public static void initCheck() throws SSOAgentException {
        if (isSPIDSSOLoginEnabled() && isSAMLSSOLoginEnabled()) {
            throw new SSOAgentException("Authentication protocols Spid and Saml are mutually exclusive. 'EnableSPIDSSOLogin' and 'EnableSAMLSSOLogin' are both activated");
        }
        if ((isSPIDSSOLoginEnabled() || isSPIDSSOLoginEnabled()) && getSpidLevel() == null) {
            throw new SSOAgentException("'SPIDLevel' not configured");
        }
        if ((isSPIDSSOLoginEnabled() || isSPIDSSOLoginEnabled()) && getSpidLevel() != null) {
            final String level = getSpidLevel();
            if (!level.equals("1") & !level.equals("2") & !level.equals("3")) {
                throw new SSOAgentException("'SPIDLevel' must be between 1 and 3");
            }
        }
        if ((isSAMLSSOLoginEnabled() || isOpenIDLoginEnabled()) && getLoginUrl() == null) {
            throw new SSOAgentException("'LoginUrl' not configured");
        }
        if (isSAMLSSOLoginEnabled() && isSAML2GrantEnabled() && getSAML2GrantUrl() == null) {
            throw new SSOAgentException("'SAML2GrantUrl' not configured");
        }
        if (isSAMLSSOLoginEnabled() && getSAMLSSOUrl() == null) {
            throw new SSOAgentException("'SAMLSSOUrl' not configured");
        }
        if (isSAMLSSOLoginEnabled() && getIssuerId() == null) {
            throw new SSOAgentException("'SAML.IssuerId' not configured");
        }
        if (isSAMLSSOLoginEnabled() && getConsumerUrl() == null) {
            throw new SSOAgentException("'SAML.ConsumerUrl' not configured");
        }
        if (isSAMLSSOLoginEnabled() && getIdPUrl() == null) {
            throw new SSOAgentException("'SAML.IdPUrl' not configured");
        }
        if (isSAMLSSOLoginEnabled() && getAttributeConsumingServiceIndex() == null) {
            SSOAgentConfigs.LOGGER.info("'SAML.AttributeConsumingServiceIndex' not configured. No attributes of the Subject will be requested");
        }
        if (isSAMLSSOLoginEnabled() && isSLOEnabled() && getLogoutUrl() == null) {
            throw new SSOAgentException("Single Logout enabled, but SAML.LogoutUrl not configured");
        }
        if (isSAMLSSOLoginEnabled() && (isResponseSigned() || isAssertionSigned() || isAssertionEncripted() || isRequestSigned()) && getSSOAgentCredentialImplClass() == null) {
            SSOAgentConfigs.LOGGER.info("SAML.SSOAgentCredentialImplClass not configured. Defaulting to 'org.wso2.carbon.identity.sso.agent.saml.SSOAgentKeyStoreCredential'");
            setSSOAgentCredentialImplClass("org.wso2.carbon.identity.sso.agent.saml.SSOAgentKeyStoreCredential");
        }
        if (isSAMLSSOLoginEnabled() && (isResponseSigned() || isAssertionSigned() || isAssertionEncripted() || isRequestSigned()) && getSSOAgentCredentialImplClass() != null && getKeyStoreStream() == null) {
            throw new SSOAgentException("KeyStore not configured");
        }
        if (isSAMLSSOLoginEnabled() && (isResponseSigned() || isAssertionSigned() || isAssertionEncripted() || isRequestSigned()) && getSSOAgentCredentialImplClass() != null && getKeyStoreStream() != null && getKeyStorePassword() == null) {
            SSOAgentConfigs.LOGGER.info("KeyStorePassword not configured. Defaulting to 'wso2carbon'");
            setKeyStorePassword("wso2carbon");
        }
        if (isSAMLSSOLoginEnabled() && (isResponseSigned() || isAssertionSigned()) && getSSOAgentCredentialImplClass() != null && getIdPCertAlias() == null) {
            SSOAgentConfigs.LOGGER.info("'SAML.IdPCertAlias' not configured. Defaulting to 'wso2carbon'");
        }
        if (isSAMLSSOLoginEnabled() && (isRequestSigned() || isAssertionEncripted()) && getSSOAgentCredentialImplClass() != null && getPrivateKeyAlias() == null) {
            SSOAgentConfigs.LOGGER.info("SAML.PrivateKeyAlias not configured. Defaulting to 'wso2carbon'");
            setPrivateKeyAlias("wso2carbon");
        }
        if (isSAMLSSOLoginEnabled() && (isRequestSigned() || isAssertionEncripted()) && getSSOAgentCredentialImplClass() != null && getPrivateKeyPassword() == null) {
            SSOAgentConfigs.LOGGER.info("SAML.PrivateKeyPassword not configured. Defaulting to 'wso2carbon'");
            setPrivateKeyPassword("wso2carbon");
        }
        if (!isSAMLSSOLoginEnabled() && isSAML2GrantEnabled()) {
            SSOAgentConfigs.LOGGER.info("SAMLSSOLogin disabled. Therefore disabling SAML2Grant as well");
            setSAML2GrantEnabled(false);
        }
        if (isSAMLSSOLoginEnabled() && isSAML2GrantEnabled() && getTokenEndpoint() == null) {
            SSOAgentConfigs.LOGGER.info("SAML.OAuth2TokenEndpoint not configured. Defaulting to 'https://localhost:9443/oauth2/token'");
            setTokenEndpoint("https://localhost:9443/oauth2/token");
        }
        if (isSAMLSSOLoginEnabled() && isSAML2GrantEnabled() && getTokenEndpoint() != null && getOAuth2ClientId() == null) {
            SSOAgentConfigs.LOGGER.info("SAML.OAuth2ClientID not configured");
            throw new SSOAgentException("SAML.OAuth2ClientId not configured");
        }
        if (isSAMLSSOLoginEnabled() && isSAML2GrantEnabled() && getTokenEndpoint() != null && getOAuth2ClientSecret() == null) {
            throw new SSOAgentException("SAML.OAuth2ClientSecret not configured");
        }
        if (isOpenIDLoginEnabled() && getOpenIdUrl() == null) {
            throw new SSOAgentException("'OpenIDUrl' not configured");
        }
        if (isOpenIDLoginEnabled() && getOpenIdProviderUrl() == null) {
            throw new SSOAgentException("'OpenID.OpenIdProviderUrl' not configured");
        }
        if (isOpenIDLoginEnabled() && getReturnTo() == null) {
            throw new SSOAgentException("OpenID.ReturnToUrl not configured");
        }
        if (isOpenIDLoginEnabled() && getClaimedIdParameterName() == null) {
            SSOAgentConfigs.LOGGER.info("OpenID.ClaimIDParameterName not configured. Defaulting to 'claimed_id'");
            setClaimedIdParameterName("claimed_id");
        }
        if (isOpenIDLoginEnabled() && getAttributesRequestorImplClass() == null) {
            SSOAgentConfigs.LOGGER.info("OpenID.AttributesRequestorImplClass not configured. No attributes of the subject will be fetched");
        }
    }
    
    public static boolean isSPIDSSOLoginEnabled() {
        return SSOAgentConfigs.SPIDSSOLoginEnabled;
    }
    
    public static boolean isSAMLSSOLoginEnabled() {
        return SSOAgentConfigs.samlSSOLoginEnabled;
    }
    
    public static boolean isOpenIDLoginEnabled() {
        return SSOAgentConfigs.openidLoginEnabled;
    }
    
    public static boolean isSAML2GrantEnabled() {
        return SSOAgentConfigs.saml2GrantEnabled;
    }
    
    public static String getSessionBeanName() {
        return SSOAgentConfigs.sessionBeanName;
    }
    
    public static String getLoginUrl() {
        return SSOAgentConfigs.loginUrl;
    }
    
    public static String getSAMLSSOUrl() {
        return SSOAgentConfigs.samlSSOUrl;
    }
    
    public static String getOpenIdUrl() {
        return SSOAgentConfigs.openIdUrl;
    }
    
    public static String getSAML2GrantUrl() {
        return SSOAgentConfigs.saml2GrantUrl;
    }
    
    public static String getIssuerId() {
        return SSOAgentConfigs.issuerId;
    }
    
    public static String getConsumerUrl() {
        return SSOAgentConfigs.consumerUrl;
    }
    
    public static String getIdPUrl() {
        return SSOAgentConfigs.idPUrl;
    }
    
    public static String getAttributeConsumingServiceIndex() {
        return SSOAgentConfigs.attributeConsumingServiceIndex;
    }
    
    public static boolean isSLOEnabled() {
        return SSOAgentConfigs.isSLOEnabled;
    }
    
    public static String getLogoutUrl() {
        return SSOAgentConfigs.logoutUrl;
    }
    
    public static boolean isResponseSigned() {
        return SSOAgentConfigs.isResponseSigned;
    }
    
    public static boolean isAssertionSigned() {
        return SSOAgentConfigs.isAssertionSigned;
    }
    
    public static boolean isAssertionEncripted() {
        return SSOAgentConfigs.isAssertionEncrypted;
    }
    
    public static boolean isRequestSigned() {
        return SSOAgentConfigs.isRequestSigned;
    }
    
    public static boolean isForceAuthn() {
        return SSOAgentConfigs.isForceAuthn;
    }
    
    public static String getSSOAgentCredentialImplClass() {
        return SSOAgentConfigs.ssoAgentCredentialImplClass;
    }
    
    private static InputStream getKeyStoreStream() {
        return SSOAgentConfigs.keyStoreStream;
    }
    
    private static String getKeyStorePassword() {
        return SSOAgentConfigs.keyStorePassword;
    }
    
    public static KeyStore getKeyStore() throws SSOAgentException {
        if (SSOAgentConfigs.keyStore == null) {
            setKeyStore(readKeyStore(getKeyStoreStream(), getKeyStorePassword()));
        }
        return SSOAgentConfigs.keyStore;
    }
    
    public static String getIdPCertAlias() {
        return SSOAgentConfigs.idPCertAlias;
    }
    
    public static String getPrivateKeyAlias() {
        return SSOAgentConfigs.privateKeyAlias;
    }
    
    public static String getPrivateKeyPassword() {
        return SSOAgentConfigs.privateKeyPassword;
    }
    
    public static String getTokenEndpoint() {
        return SSOAgentConfigs.tokenEndpoint;
    }
    
    public static String getOAuth2ClientId() {
        return SSOAgentConfigs.clientId;
    }
    
    public static String getOAuth2ClientSecret() {
        return SSOAgentConfigs.clientSecret;
    }
    
    public static String getOpenIdProviderUrl() {
        return SSOAgentConfigs.openIdProviderUrl;
    }
    
    public static String getReturnTo() {
        return SSOAgentConfigs.returnTo;
    }
    
    public static String getClaimedIdParameterName() {
        return SSOAgentConfigs.claimedIdParameterName;
    }
    
    public static String getAttributesRequestorImplClass() {
        return SSOAgentConfigs.attributesRequestorImplClass;
    }
    
    public static void setSAMLSSOLoginEnabled(final Boolean samlSSOLoginEnabled) {
        SSOAgentConfigs.samlSSOLoginEnabled = samlSSOLoginEnabled;
    }
    
    public static void setOpenidLoginEnabled(final Boolean openidLoginEnabled) {
        SSOAgentConfigs.openidLoginEnabled = openidLoginEnabled;
    }
    
    public static void setSAML2GrantEnabled(final Boolean saml2GrantEnabled) {
        SSOAgentConfigs.saml2GrantEnabled = saml2GrantEnabled;
    }
    
    public static void setSessionBeanName(final String sessionBeanName) {
        SSOAgentConfigs.sessionBeanName = sessionBeanName;
    }
    
    public static void setLoginUrl(final String loginUrl) {
        SSOAgentConfigs.loginUrl = loginUrl;
    }
    
    public static void setSAMLSSOUrl(final String samlSSOUrl) {
        SSOAgentConfigs.samlSSOUrl = samlSSOUrl;
    }
    
    public static void setOpenIdUrl(final String openIdUrl) {
        SSOAgentConfigs.openIdUrl = openIdUrl;
    }
    
    public static void setSAML2GrantUrl(final String saml2GrantUrl) {
        SSOAgentConfigs.saml2GrantUrl = saml2GrantUrl;
    }
    
    public static void setIssuerId(final String issuerId) {
        SSOAgentConfigs.issuerId = issuerId;
    }
    
    public static void setConsumerUrl(final String consumerUrl) {
        SSOAgentConfigs.consumerUrl = consumerUrl;
    }
    
    public static void setIdPUrl(final String idPUrl) {
        SSOAgentConfigs.idPUrl = idPUrl;
    }
    
    public static void setAttributeConsumingServiceIndex(final String attributeConsumingServiceIndex) {
        SSOAgentConfigs.attributeConsumingServiceIndex = attributeConsumingServiceIndex;
    }
    
    public static void setSLOEnabled(final Boolean SLOEnabled) {
        SSOAgentConfigs.isSLOEnabled = SLOEnabled;
    }
    
    public static void setLogoutUrl(final String logoutUrl) {
        SSOAgentConfigs.logoutUrl = logoutUrl;
    }
    
    public static void setResponseSigned(final Boolean responseSigned) {
        SSOAgentConfigs.isResponseSigned = responseSigned;
    }
    
    public static void setAssertionSigned(final Boolean assertionSigned) {
        SSOAgentConfigs.isAssertionSigned = assertionSigned;
    }
    
    public static void setAssertionEncrypted(final Boolean assertionEncrypted) {
        SSOAgentConfigs.isAssertionEncrypted = assertionEncrypted;
    }
    
    public static void setRequestSigned(final Boolean requestSigned) {
        SSOAgentConfigs.isRequestSigned = requestSigned;
    }
    
    public static void setForceAuthn(final Boolean forceAuthn) {
        SSOAgentConfigs.isForceAuthn = forceAuthn;
    }
    
    public static void setSSOAgentCredentialImplClass(final String ssoAgentCredentialImplClass) {
        SSOAgentConfigs.ssoAgentCredentialImplClass = ssoAgentCredentialImplClass;
    }
    
    public static void setKeyStoreStream(final String keyStore) throws SSOAgentException {
        try {
            SSOAgentConfigs.keyStoreStream = new FileInputStream(keyStore);
        }
        catch (FileNotFoundException e) {
            throw new SSOAgentException("Cannot find file " + keyStore);
        }
    }
    
    public static void setKeyStoreStream(final InputStream keyStoreStream) {
        if (SSOAgentConfigs.keyStoreStream == null) {
            SSOAgentConfigs.keyStoreStream = keyStoreStream;
        }
    }
    
    public static void setKeyStore(final KeyStore keyStore) {
        SSOAgentConfigs.keyStore = keyStore;
    }
    
    public static void setKeyStorePassword(final String keyStorePassword) {
        SSOAgentConfigs.keyStorePassword = keyStorePassword;
    }
    
    public static void setIdPCertAlias(final String idPCertAlias) {
        SSOAgentConfigs.idPCertAlias = idPCertAlias;
    }
    
    public static void setPrivateKeyAlias(final String privateKeyAlias) {
        SSOAgentConfigs.privateKeyAlias = privateKeyAlias;
    }
    
    public static void setPrivateKeyPassword(final String privateKeyPassword) {
        SSOAgentConfigs.privateKeyPassword = privateKeyPassword;
    }
    
    public static void setTokenEndpoint(final String tokenEndpoint) {
        SSOAgentConfigs.tokenEndpoint = tokenEndpoint;
    }
    
    public static void setOAuth2ClientSecret(final String clientId) {
        SSOAgentConfigs.clientId = clientId;
    }
    
    public static void setOAuth2ClientId(final String clientSecret) {
        SSOAgentConfigs.clientSecret = clientSecret;
    }
    
    public static void setOpenIdProviderUrl(final String openIdProviderUrl) {
        SSOAgentConfigs.openIdProviderUrl = openIdProviderUrl;
    }
    
    public static void setReturnTo(final String returnTo) {
        SSOAgentConfigs.returnTo = returnTo;
    }
    
    public static void setClaimedIdParameterName(final String claimedIdParameterName) {
        SSOAgentConfigs.claimedIdParameterName = claimedIdParameterName;
    }
    
    public static void setAttributesRequestorImplClass(final String attributesRequestorImplClass) {
        SSOAgentConfigs.attributesRequestorImplClass = attributesRequestorImplClass;
    }
    
    public static String getRequestQueryParameters() {
        return SSOAgentConfigs.requestQueryParameters;
    }
    
    public static String getAddExtension() {
        return SSOAgentConfigs.addExtension;
    }
    
    public static void setAddExtension(final String addExtension) {
        SSOAgentConfigs.addExtension = addExtension;
    }
    
    public static String getSpidLevel() {
        return SSOAgentConfigs.spidLevel;
    }
        
	public static String getSamlAuthRequestNameIdPolicySPNameQualifier() {
		return samlAuthRequestNameIdPolicySPNameQualifier;
	}
	
	public static void setSamlAuthRequestNameIdPolicySPNameQualifier(String samlAuthRequestNameIdPolicySPNameQualifier) {
		SSOAgentConfigs.samlAuthRequestNameIdPolicySPNameQualifier = samlAuthRequestNameIdPolicySPNameQualifier;
	}

	public static String getSamlAuthRequestNameIdPolicyFormat() {
		return samlAuthRequestNameIdPolicyFormat;
	}

	public static void setSamlAuthRequestNameIdPolicyFormat(String samlAuthRequestNameIdPolicyFormat) {
		SSOAgentConfigs.samlAuthRequestNameIdPolicyFormat = samlAuthRequestNameIdPolicyFormat;
	}
	
	public static String getSamlAuthRequestSetForceAuthn() {
		return samlAuthRequestSetForceAuthn;
	}
	
	public static void setSamlAuthRequestSetForceAuthn(String samlAuthRequestSetForceAuthn) {
		SSOAgentConfigs.samlAuthRequestSetForceAuthn = samlAuthRequestSetForceAuthn;
	}

	public static String getSamlAuthRequestSetRequestedAuthnContext() {
		return samlAuthRequestSetRequestedAuthnContext;
	}

	public static void setSamlAuthRequestSetRequestedAuthnContext(String samlAuthRequestSetRequestedAuthnContext) {
		SSOAgentConfigs.samlAuthRequestSetRequestedAuthnContext = samlAuthRequestSetRequestedAuthnContext;
	}

	public static String getSamlAuthRequestRequestedAuthnContextClassRef() {
		return samlAuthRequestRequestedAuthnContextClassRef;
	}
	
	public static void setSamlAuthRequestRequestedAuthnContextClassRef(String samlAuthRequestRequestedAuthnContextClassRef) {
		SSOAgentConfigs.samlAuthRequestRequestedAuthnContextClassRef = samlAuthRequestRequestedAuthnContextClassRef;
	}
	
	public static String getSamlAuthRequestRequestedAuthnContextComparison() {
		return samlAuthRequestRequestedAuthnContextComparison;
	}
	
	public static void setSamlAuthRequestRequestedAuthnContextComparison(String samlAuthRequestRequestedAuthnContextComparison) {
		SSOAgentConfigs.samlAuthRequestRequestedAuthnContextComparison = samlAuthRequestRequestedAuthnContextComparison;
	}

	private static KeyStore readKeyStore(final InputStream is, final String storePassword) throws SSOAgentException {
        if (storePassword == null) {
            throw new SSOAgentException("KeyStore password can not be null");
        }
        try {
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, storePassword.toCharArray());
            return keyStore;
        }
        catch (Exception e) {
            throw new SSOAgentException("Error while loading key store file", e);
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException ignored) {
                    throw new SSOAgentException("Error while closing input stream of key store");
                }
            }
        }
    }
    
    static {
        SSOAgentConfigs.LOGGER = Logger.getLogger("InfoLogging");
    }
}
