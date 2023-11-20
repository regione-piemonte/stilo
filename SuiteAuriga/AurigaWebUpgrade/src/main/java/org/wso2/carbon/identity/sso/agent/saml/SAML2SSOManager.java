/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.saml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.common.impl.ExtensionsBuilder;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.LogoutResponse;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.SessionIndex;
import org.opensaml.saml2.core.impl.AuthnContextClassRefBuilder;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.LogoutRequestBuilder;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.saml2.core.impl.NameIDPolicyBuilder;
import org.opensaml.saml2.core.impl.RequestedAuthnContextBuilder;
import org.opensaml.saml2.core.impl.SessionIndexBuilder;
import org.opensaml.saml2.encryption.Decrypter;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.EncryptedKey;
import org.opensaml.xml.encryption.EncryptedKeyResolver;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.validation.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.wso2.carbon.identity.sso.agent.bean.SSOAgentSessionBean;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SAML2SSOManager {
	
	private static final Logger logger = Logger.getLogger(SAML2SSOManager.class);
	
    private String authReqRandomId;
    private String relayState;
    private X509Credential credential;
    
    public SAML2SSOManager() throws SSOAgentException {
        this.authReqRandomId = Integer.toHexString(new Double(Math.random()).intValue());
        this.relayState = null;
        this.credential = null;
        try {
//        	Velocity.setProperty("runtime.log", "velocityyyyy.log");
            DefaultBootstrap.bootstrap();
            synchronized (this) {
                if (this.credential == null) {
                    synchronized (this) {
                        final SSOAgentCredential credential = (SSOAgentCredential)Class.forName(SSOAgentConfigs.getSSOAgentCredentialImplClass()).newInstance();
                        credential.init();
                        this.credential = (X509Credential)new X509CredentialImpl(credential);
                    }
                }
            }
        }
        catch (ConfigurationException e) {
            throw new SSOAgentException("Error while bootstrapping OpenSAML library", (Throwable)e);
        }
        catch (ClassNotFoundException e2) {
            throw new SSOAgentException("Error while instantiating SSOAgentCredentialImplClass: " + SSOAgentConfigs.getSSOAgentCredentialImplClass(), e2);
        }
        catch (InstantiationException e3) {
            throw new SSOAgentException("Error while instantiating SSOAgentCredentialImplClass: " + SSOAgentConfigs.getSSOAgentCredentialImplClass(), e3);
        }
        catch (IllegalAccessException e4) {
            throw new SSOAgentException("Error while instantiating SSOAgentCredentialImplClass: " + SSOAgentConfigs.getSSOAgentCredentialImplClass(), e4);
        }
    }
    
    public String buildRequest(final HttpServletRequest request, final boolean isLogout, final boolean isPassive) throws SSOAgentException {
        RequestAbstractType requestMessage;
        if (!isLogout) {
            requestMessage = (RequestAbstractType)this.buildAuthnRequest(isPassive);
        }
        else {
            final SSOAgentSessionBean sessionBean = (SSOAgentSessionBean)request.getSession().getAttribute(SSOAgentConfigs.getSessionBeanName());
            if (sessionBean == null) {
                throw new SSOAgentException("SLO Request can not be built. SSO Session is null");
            }
            requestMessage = (RequestAbstractType)this.buildLogoutRequest(sessionBean.getSAMLSSOSessionBean().getSubjectId(), sessionBean.getSAMLSSOSessionBean().getIdPSessionIndex());
        }
        String idpUrl = null;
        final String encodedRequestMessage = this.encodeRequestMessage(requestMessage);
        logger.debug("encodedRequestMessage: " + encodedRequestMessage);
        final StringBuilder httpQueryString = new StringBuilder("SAMLRequest=" + encodedRequestMessage);
        if (this.relayState != null && !this.relayState.isEmpty()) {
            try {
                httpQueryString.append("&RelayState=" + URLEncoder.encode(this.relayState, "UTF-8").trim());
            }
            catch (UnsupportedEncodingException e) {
                throw new SSOAgentException("Error occurred while url encoding RelayState", e);
            }
        }
        if (SSOAgentConfigs.getRequestQueryParameters() != null && !SSOAgentConfigs.getRequestQueryParameters().isEmpty()) {
            httpQueryString.append(SSOAgentConfigs.getRequestQueryParameters());
        }
        if (SSOAgentConfigs.isRequestSigned()) {
            SSOAgentUtils.addDeflateSignatureToHTTPQueryString(httpQueryString, this.credential);
        }
        if (SSOAgentConfigs.getIdPUrl().indexOf("?") > -1) {
            idpUrl = SSOAgentConfigs.getIdPUrl().concat("&").concat(httpQueryString.toString());
        }
        else {
            idpUrl = SSOAgentConfigs.getIdPUrl().concat("?").concat(httpQueryString.toString());
        }
        return idpUrl;
    }
    
    public void processResponse(final HttpServletRequest request) throws SSOAgentException {
        final String samlRequest = request.getParameter("SAMLResponse");
        if (samlRequest != null) {
            final String decodedResponse = new String(Base64.decode(samlRequest));
            final XMLObject samlObject = this.unmarshall(decodedResponse);
            if (samlObject instanceof LogoutResponse) {
                this.doSLO(request);
            }
            else {
                this.processSSOResponse(request);
            }
            return;
        }
        throw new SSOAgentException("Invalid SAML Response. SAML Response can not be null.");
    }
    
    public void doSLO(final HttpServletRequest request) throws SSOAgentException {
        XMLObject samlObject = null;
        if (request.getParameter("SAMLRequest") != null) {
            samlObject = this.unmarshall(new String(Base64.decode(request.getParameter("SAMLRequest"))));
        }
        if (samlObject == null) {
            samlObject = this.unmarshall(new String(Base64.decode(request.getParameter("SAMLResponse"))));
        }
        if (samlObject instanceof LogoutRequest) {
            final LogoutRequest logoutRequest = (LogoutRequest)samlObject;
            final String sessionIndex = logoutRequest.getSessionIndexes().get(0).getSessionIndex();
            SSOAgentSessionManager.invalidateSession(sessionIndex);
        }
        else {
            if (!(samlObject instanceof LogoutResponse)) {
                throw new SSOAgentException("Invalid Single Logout SAML Request");
            }
            if (request.getSession(false) != null) {
                request.getSession().invalidate();
            }
        }
    }
    
    private void processSSOResponse(final HttpServletRequest request) throws SSOAgentException {
        final SSOAgentSessionBean sessionBean = new SSOAgentSessionBean();
        sessionBean.setSAMLSSOSessionBean(sessionBean.new SAMLSSOSessionBean());
        request.getSession().setAttribute(SSOAgentConfigs.getSessionBeanName(), (Object)sessionBean);
        final String samlResponseString = new String(Base64.decode(request.getParameter("SAMLResponse")));
        logger.debug("samlResponseString:\n" + samlResponseString);
        final Response samlResponse = (Response)this.unmarshall(samlResponseString);
        sessionBean.getSAMLSSOSessionBean().setSAMLResponseString(samlResponseString);
        sessionBean.getSAMLSSOSessionBean().setSAMLResponse(samlResponse);
        Assertion assertion = null;
        if (SSOAgentConfigs.isAssertionEncripted()) {
            final List<EncryptedAssertion> encryptedAssertions = (List<EncryptedAssertion>)samlResponse.getEncryptedAssertions();
            EncryptedAssertion encryptedAssertion = null;
            if (encryptedAssertions != null && encryptedAssertions.size() > 0) {
                encryptedAssertion = encryptedAssertions.get(0);
                try {
                    assertion = getDecryptedAssertion(encryptedAssertion, this.credential);
                }
                catch (Exception e) {
                    throw new SSOAgentException("Unable to decrypt the SAML Assertion");
                }
            }
        }
        else {
            final List<Assertion> assertions = (List<Assertion>)samlResponse.getAssertions();
            if (assertions != null && assertions.size() > 0) {
                assertion = assertions.get(0);
            }
        }
        if (assertion == null) {
            if (samlResponse.getStatus() != null && samlResponse.getStatus().getStatusCode() != null && samlResponse.getStatus().getStatusCode().getValue().equals("urn:oasis:names:tc:SAML:2.0:status:Responder") && samlResponse.getStatus().getStatusCode().getStatusCode() != null && samlResponse.getStatus().getStatusCode().getStatusCode().getValue().equals("urn:oasis:names:tc:SAML:2.0:status:NoPassive")) {
                request.getSession().removeAttribute(SSOAgentConfigs.getSessionBeanName());
                logger.debug("Setto isLogin per la sessione " + request.getSession().getId());
                request.getSession().setAttribute("isLogin", (Object)"true");
                return;
            }
            logger.error("samlResponse.getStatus(): " + samlResponse.getStatus());
            if (samlResponse.getStatus() != null) {
            	logger.error("samlResponse.getStatus().getStatusCode(): " + samlResponse.getStatus().getStatusCode());
            	if (samlResponse.getStatus().getStatusCode() != null) {
            		logger.error("samlResponse.getStatus().getStatusCode().getValue(): " + samlResponse.getStatus().getStatusCode().getValue());
            		if (samlResponse.getStatus().getStatusCode().getStatusCode() != null) {
            			logger.error("samlResponse.getStatus().getStatusCode().getStatusCode(): " + samlResponse.getStatus().getStatusCode().getStatusCode().getValue());
            		}
            	}
            }
            logger.error("samlResponse.getStatus().getStatusCode().getValue(): " + samlResponse.getStatus());
            throw new SSOAgentException("SAML Assertion not found in the Response");
        }
        else {
            sessionBean.getSAMLSSOSessionBean().setSAMLAssertion(assertion);
            String subject = null;
            if (assertion.getSubject() != null && assertion.getSubject().getNameID() != null) {
                subject = assertion.getSubject().getNameID().getValue();
            }
            if (subject == null) {
                throw new SSOAgentException("SAML Response does not contain the name of the subject");
            }
            sessionBean.getSAMLSSOSessionBean().setSubjectId(subject);
            request.getSession().setAttribute(SSOAgentConfigs.getSessionBeanName(), (Object)sessionBean);
            this.validateAudienceRestriction(assertion);
            this.validateSignature(samlResponse, assertion);
            sessionBean.getSAMLSSOSessionBean().setSAMLAssertionString(marshall((XMLObject)assertion));
            ((SSOAgentSessionBean)request.getSession().getAttribute(SSOAgentConfigs.getSessionBeanName())).getSAMLSSOSessionBean().setSAMLSSOAttributes(this.getAssertionStatements(assertion));
            if (SSOAgentConfigs.isSLOEnabled()) {
                final String sessionId = assertion.getAuthnStatements().get(0).getSessionIndex();
                if (sessionId == null) {
                    throw new SSOAgentException("Single Logout is enabled but IdP Session ID not found in SAML Assertion");
                }
                ((SSOAgentSessionBean)request.getSession().getAttribute(SSOAgentConfigs.getSessionBeanName())).getSAMLSSOSessionBean().setIdPSessionIndex(sessionId);
                SSOAgentSessionManager.addAuthenticatedSession(sessionId, request.getSession());
            }
        }
    }
    
    private LogoutRequest buildLogoutRequest(final String user, final String sessionIdx) throws SSOAgentException {
        final LogoutRequest logoutReq = new LogoutRequestBuilder().buildObject();
        logoutReq.setID(SSOAgentUtils.createID());
        logoutReq.setDestination(SSOAgentConfigs.getIdPUrl());
        final DateTime issueInstant = new DateTime();
        logoutReq.setIssueInstant(issueInstant);
        logoutReq.setNotOnOrAfter(new DateTime(issueInstant.getMillis() + 300000L));
        final IssuerBuilder issuerBuilder = new IssuerBuilder();
        final Issuer issuer = issuerBuilder.buildObject();
        issuer.setValue(SSOAgentConfigs.getIssuerId());
        logoutReq.setIssuer(issuer);
        final NameID nameId = new NameIDBuilder().buildObject();
        nameId.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
        nameId.setValue(user);
        logoutReq.setNameID(nameId);
        final SessionIndex sessionIndex = new SessionIndexBuilder().buildObject();
        sessionIndex.setSessionIndex(sessionIdx);
        logoutReq.getSessionIndexes().add(sessionIndex);
        logoutReq.setReason("Single Logout");
        return logoutReq;
    }
    
    private AuthnRequest buildAuthnRequest(final boolean isPassive) throws SSOAgentException {
        final IssuerBuilder issuerBuilder = new IssuerBuilder();
        final Issuer issuer = issuerBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "samlp");
        issuer.setValue(SSOAgentConfigs.getIssuerId());
        final NameIDPolicyBuilder nameIdPolicyBuilder = new NameIDPolicyBuilder();
        final NameIDPolicy nameIdPolicy = nameIdPolicyBuilder.buildObject();
        nameIdPolicy.setFormat(SSOAgentConfigs.getSamlAuthRequestNameIdPolicyFormat());
        nameIdPolicy.setSPNameQualifier(SSOAgentConfigs.getSamlAuthRequestNameIdPolicySPNameQualifier());
        nameIdPolicy.setAllowCreate(Boolean.valueOf(true));
        final AuthnContextClassRefBuilder authnContextClassRefBuilder = new AuthnContextClassRefBuilder();
        final AuthnContextClassRef authnContextClassRef = authnContextClassRefBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextClassRef", "saml");
        authnContextClassRef.setAuthnContextClassRef(SSOAgentConfigs.getSamlAuthRequestRequestedAuthnContextClassRef());
        final RequestedAuthnContextBuilder requestedAuthnContextBuilder = new RequestedAuthnContextBuilder();
        final RequestedAuthnContext requestedAuthnContext = requestedAuthnContextBuilder.buildObject();
        String comparison = SSOAgentConfigs.getSamlAuthRequestRequestedAuthnContextComparison();
        if (StringUtils.isNotBlank(comparison)) {
        	if ("BETTER".equalsIgnoreCase(comparison)) {
        		requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.BETTER);
        	} else if ("EXACT".equalsIgnoreCase(comparison)) {
        		requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
        	} else if ("MAXIMUM".equalsIgnoreCase(comparison)) {
        		requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.MAXIMUM);
        	} else if ("MINIMUM".equalsIgnoreCase(comparison)) {
        		requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.MINIMUM);
        	}
        }
        requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
        requestedAuthnContext.getAuthnContextClassRefs().add(authnContextClassRef);
        if (SSOAgentConfigs.isSPIDSSOLoginEnabled()) {
            String spidClass = null;
            final AuthnContextClassRefBuilder authnContextClassRefBuilderSpidSSO = new AuthnContextClassRefBuilder();
            final AuthnContextClassRef authnContextClassRefSpidSSO = authnContextClassRefBuilderSpidSSO.buildObject();
            final String levelSpid = SSOAgentConfigs.getSpidLevel();
            if (levelSpid.equals("1")) {
                spidClass = "urn:oasis:names:tc:SAML:2.0:ac:classes:SpidL1";
            }
            else if (levelSpid.equals("2")) {
                spidClass = "urn:oasis:names:tc:SAML:2.0:ac:classes:SpidL2";
            }
            else if (levelSpid.equals("3")) {
                spidClass = "urn:oasis:names:tc:SAML:2.0:ac:classes:SpidL3";
            }
            authnContextClassRefSpidSSO.setAuthnContextClassRef(spidClass);
            requestedAuthnContext.getAuthnContextClassRefs().add(authnContextClassRefSpidSSO);
        }
        final DateTime issueInstant = new DateTime();
        final AuthnRequestBuilder authRequestBuilder = new AuthnRequestBuilder();
        final AuthnRequest authRequest = authRequestBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequest", "samlp");
        if (!isPassive) {
            authRequest.setForceAuthn(Boolean.valueOf(SSOAgentConfigs.isForceAuthn()));
        }
        else {
            authRequest.setForceAuthn(Boolean.valueOf(false));
        }
        authRequest.setIsPassive(Boolean.valueOf(isPassive));
        authRequest.setIssueInstant(issueInstant);
        authRequest.setProtocolBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        authRequest.setAssertionConsumerServiceURL(SSOAgentConfigs.getConsumerUrl());
        authRequest.setIssuer(issuer);
        authRequest.setNameIDPolicy(nameIdPolicy);
        String setRequestedAuthnContext = SSOAgentConfigs.getSamlAuthRequestSetRequestedAuthnContext();
        if (StringUtils.isBlank(setRequestedAuthnContext) || "true".equalsIgnoreCase(setRequestedAuthnContext)) {
        	authRequest.setRequestedAuthnContext(requestedAuthnContext);
        }
        authRequest.setID(SSOAgentUtils.createID());
        authRequest.setVersion(SAMLVersion.VERSION_20);
        authRequest.setDestination(SSOAgentConfigs.getIdPUrl());
        String setForceAuthn = SSOAgentConfigs.getSamlAuthRequestSetForceAuthn();
        if (StringUtils.isBlank(setForceAuthn) || "true".equalsIgnoreCase(setForceAuthn)) {
        	authRequest.setForceAuthn(true);
        } else {
        	authRequest.setForceAuthn(false);
        }
        if ("true".equalsIgnoreCase(SSOAgentConfigs.getAddExtension())) {
            final String xmlString = "<saml2p:Extensions xmlns:saml2p=\"urn:oasis:names:tc:SAML:2.0:protocol\"><Test>TestContent</Test></saml2p:Extensions>";
            Element element = null;
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                final DocumentBuilder builder = factory.newDocumentBuilder();
                final Document document = builder.parse(new InputSource(new StringReader(xmlString)));
                element = document.getDocumentElement();
            }
            catch (Exception ex) {}
            if (element != null) {
                final ExtensionsBuilder extBuilder = new ExtensionsBuilder();
                final Extensions extensions = extBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "Extensions", "saml2p");
                extensions.setDOM(element);
                authRequest.setExtensions(extensions);
            }
        }
        if (SSOAgentConfigs.getAttributeConsumingServiceIndex() != null && SSOAgentConfigs.getAttributeConsumingServiceIndex().trim().length() > 0) {
            authRequest.setAttributeConsumingServiceIndex(Integer.valueOf(Integer.parseInt(SSOAgentConfigs.getAttributeConsumingServiceIndex())));
        }
        return authRequest;
    }
    
    private String encodeRequestMessage(final RequestAbstractType requestMessage) throws SSOAgentException {
        final Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller((XMLObject)requestMessage);
        Element authDOM = null;
        try {
            authDOM = marshaller.marshall((XMLObject)requestMessage);
            final Deflater deflater = new Deflater(8, true);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
            final StringWriter rspWrt = new StringWriter();
            XMLHelper.writeNode((Node)authDOM, (Writer)rspWrt);
            deflaterOutputStream.write(rspWrt.toString().getBytes());
            deflaterOutputStream.close();
            final String encodedRequestMessage = Base64.encodeBytes(byteArrayOutputStream.toByteArray(), 8);
            return URLEncoder.encode(encodedRequestMessage, "UTF-8").trim();
        }
        catch (MarshallingException e) {
            throw new SSOAgentException("Error occurred while encoding SAML request", (Throwable)e);
        }
        catch (UnsupportedEncodingException e2) {
            throw new SSOAgentException("Error occurred while encoding SAML request", e2);
        }
        catch (IOException e3) {
            throw new SSOAgentException("Error occurred while encoding SAML request", e3);
        }
    }
    
    private XMLObject unmarshall(final String samlString) throws SSOAgentException {
        final String decodedString = this.decodeHTMLCharacters(samlString);
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        try {
            final DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            final ByteArrayInputStream is = new ByteArrayInputStream(decodedString.getBytes());
            final Document document = docBuilder.parse(is);
            final Element element = document.getDocumentElement();
            final UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
            final Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(element);
            return unmarshaller.unmarshall(element);
        }
        catch (ParserConfigurationException e) {
            throw new SSOAgentException("Error in unmarshalling SAML Request from the encoded String", e);
        }
        catch (UnmarshallingException e2) {
            throw new SSOAgentException("Error in unmarshalling SAML Request from the encoded String", (Throwable)e2);
        }
        catch (SAXException e3) {
            throw new SSOAgentException("Error in unmarshalling SAML Request from the encoded String", e3);
        }
        catch (IOException e4) {
            throw new SSOAgentException("Error in unmarshalling SAML Request from the encoded String", e4);
        }
    }
    
    private String decodeHTMLCharacters(final String encodedStr) {
        return encodedStr.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"").replaceAll("&apos;", "'");
    }
    
    private Map<String, String> getAssertionStatements(final Assertion assertion) {
        final Map<String, String> results = new HashMap<String, String>();
        if (assertion != null) {
            final List<AttributeStatement> attributeStatementList = (List<AttributeStatement>)assertion.getAttributeStatements();
            if (attributeStatementList != null) {
                for (final AttributeStatement statement : attributeStatementList) {
                    final List<Attribute> attributesList = (List<Attribute>)statement.getAttributes();
                    for (final Attribute attribute : attributesList) {
                        String attributeValue = attribute.getAttributeValues().get(0).getDOM().getTextContent();
                        for (int i = 1; i < attribute.getAttributeValues().size(); ++i) {
                            final Element value = attribute.getAttributeValues().get(i).getDOM();
                            attributeValue = attributeValue + "," + value.getTextContent();
                        }
                        results.put(attribute.getName(), attributeValue);
                    }
                }
            }
        }
        return results;
    }
    
    private void validateAudienceRestriction(final Assertion assertion) throws SSOAgentException {
        if (assertion != null) {
            final Conditions conditions = assertion.getConditions();
            if (conditions == null) {
                throw new SSOAgentException("SAML Response doesn't contain Conditions");
            }
            final List<AudienceRestriction> audienceRestrictions = (List<AudienceRestriction>)conditions.getAudienceRestrictions();
            if (audienceRestrictions == null || audienceRestrictions.isEmpty()) {
                throw new SSOAgentException("SAML Response doesn't contain AudienceRestrictions");
            }
            boolean audienceFound = false;
            for (final AudienceRestriction audienceRestriction : audienceRestrictions) {
                if (audienceRestriction.getAudiences() != null && audienceRestriction.getAudiences().size() > 0) {
                    for (final Audience audience : audienceRestriction.getAudiences()) {
                        if (SSOAgentConfigs.getIssuerId().equals(audience.getAudienceURI())) {
                            audienceFound = true;
                            break;
                        }
                    }
                }
                if (audienceFound) {
                    break;
                }
            }
            if (!audienceFound) {
                throw new SSOAgentException("SAML Assertion Audience Restriction validation failed");
            }
        }
    }
    
    private void validateSignature(final Response response, final Assertion assertion) throws SSOAgentException {
        if (SSOAgentConfigs.isResponseSigned()) {
            if (response.getSignature() == null) {
                throw new SSOAgentException("SAMLResponse signing is enabled, but signature element not found in SAML Response element.");
            }
            try {
                final SignatureValidator validator = new SignatureValidator((Credential)this.credential);
                validator.validate(response.getSignature());
            }
            catch (ValidationException e) {
                throw new SSOAgentException("Signature validation failed for SAML Response");
            }
        }
        if (SSOAgentConfigs.isAssertionSigned()) {
            if (assertion.getSignature() == null) {
                throw new SSOAgentException("SAMLAssertion signing is enabled, but signature element not found in SAML Assertion element.");
            }
            try {
                final SignatureValidator validator = new SignatureValidator((Credential)this.credential);
                validator.validate(assertion.getSignature());
            }
            catch (ValidationException e) {
                throw new SSOAgentException("Signature validation failed for SAML Assertion");
            }
        }
    }
    
    public static String marshall(final XMLObject xmlObject) throws SSOAgentException {
        try {
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
            final MarshallerFactory marshallerFactory = org.opensaml.xml.Configuration.getMarshallerFactory();
            final Marshaller marshaller = marshallerFactory.getMarshaller(xmlObject);
            final Element element = marshaller.marshall(xmlObject);
            final ByteArrayOutputStream byteArrayOutputStrm = new ByteArrayOutputStream();
            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();
            final LSOutput output = impl.createLSOutput();
            output.setByteStream(byteArrayOutputStrm);
            writer.write(element, output);
            return byteArrayOutputStrm.toString();
        }
        catch (ClassNotFoundException e) {
            throw new SSOAgentException("Error in marshalling SAML Assertion", e);
        }
        catch (InstantiationException e2) {
            throw new SSOAgentException("Error in marshalling SAML Assertion", e2);
        }
        catch (MarshallingException e3) {
            throw new SSOAgentException("Error in marshalling SAML Assertion", (Throwable)e3);
        }
        catch (IllegalAccessException e4) {
            throw new SSOAgentException("Error in marshalling SAML Assertion", e4);
        }
    }
    
    public static Assertion getDecryptedAssertion(final EncryptedAssertion encryptedAssertion, final X509Credential credential) throws Exception {
        final KeyInfoCredentialResolver keyResolver = (KeyInfoCredentialResolver)new StaticKeyInfoCredentialResolver((Credential)credential);
        final EncryptedKey key = encryptedAssertion.getEncryptedData().getKeyInfo().getEncryptedKeys().get(0);
        Decrypter decrypter = new Decrypter((KeyInfoCredentialResolver)null, keyResolver, (EncryptedKeyResolver)null);
        final SecretKey dkey = (SecretKey)decrypter.decryptKey(key, encryptedAssertion.getEncryptedData().getEncryptionMethod().getAlgorithm());
        final Credential shared = (Credential)SecurityHelper.getSimpleCredential(dkey);
        decrypter = new Decrypter((KeyInfoCredentialResolver)new StaticKeyInfoCredentialResolver(shared), (KeyInfoCredentialResolver)null, (EncryptedKeyResolver)null);
        decrypter.setRootInNewDocument(true);
        return decrypter.decrypt(encryptedAssertion);
    }
}
