/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.openid;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;
import java.util.Map;
import org.openid4java.discovery.Identifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.association.AssociationException;
import org.openid4java.message.ax.FetchResponse;
import java.util.HashMap;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.AuthRequest;
import org.openid4java.discovery.DiscoveryInformation;
import java.util.List;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.message.MessageException;
import org.openid4java.discovery.yadis.YadisException;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ax.FetchRequest;
import org.wso2.carbon.identity.sso.agent.bean.SSOAgentSessionBean;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.openid4java.discovery.Discovery;
import org.openid4java.server.RealmVerifierFactory;
import org.openid4java.discovery.yadis.YadisResolver;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.openid4java.util.HttpFetcherFactory;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import org.openid4java.consumer.ConsumerManager;

public class OpenIDManager
{
    private static ConsumerManager consumerManager;
    AttributesRequestor attributesRequestor;
    
    public OpenIDManager() throws SSOAgentException {
        this.attributesRequestor = null;
        OpenIDManager.consumerManager = this.getConsumerManagerInstance();
    }
    
    private ConsumerManager getConsumerManagerInstance() throws SSOAgentException {
        final HttpFetcherFactory httpFetcherFactory = new HttpFetcherFactory(this.loadSSLContext(), (X509HostnameVerifier)null);
        return new ConsumerManager(new RealmVerifierFactory(new YadisResolver(httpFetcherFactory)), new Discovery(), httpFetcherFactory);
    }
    
    public String doOpenIDLogin(final HttpServletRequest request, final HttpServletResponse response) throws SSOAgentException {
        final String claimed_id = request.getParameter(SSOAgentConfigs.getClaimedIdParameterName());
        try {
            final List discoveries = OpenIDManager.consumerManager.discover(claimed_id);
            final DiscoveryInformation discovered = OpenIDManager.consumerManager.associate(discoveries);
            final SSOAgentSessionBean ssoAgentSessionBean2;
            final SSOAgentSessionBean ssoAgentSessionBean;
            final SSOAgentSessionBean sessionBean = ssoAgentSessionBean = (ssoAgentSessionBean2 = new SSOAgentSessionBean());
            ssoAgentSessionBean.getClass();
            ssoAgentSessionBean2.setOpenIDSessionBean(ssoAgentSessionBean.new OpenIDSessionBean());
            sessionBean.getOpenIDSessionBean().setDiscoveryInformation(discovered);
            request.getSession().setAttribute(SSOAgentConfigs.getSessionBeanName(), (Object)sessionBean);
            OpenIDManager.consumerManager.setImmediateAuth(true);
            final AuthRequest authReq = OpenIDManager.consumerManager.authenticate(discovered, SSOAgentConfigs.getReturnTo());
            if (SSOAgentConfigs.getAttributesRequestorImplClass() != null) {
                if (this.attributesRequestor == null) {
                    synchronized (this) {
                        if (this.attributesRequestor == null) {
                            (this.attributesRequestor = (AttributesRequestor)Class.forName(SSOAgentConfigs.getAttributesRequestorImplClass()).newInstance()).init();
                        }
                    }
                }
                final String[] requestedAttributes = this.attributesRequestor.getRequestedAttributes(claimed_id);
                final FetchRequest fetchRequest = FetchRequest.createFetchRequest();
                for (final String requestedAttribute : requestedAttributes) {
                    fetchRequest.addAttribute(requestedAttribute, this.attributesRequestor.getTypeURI(claimed_id, requestedAttribute), this.attributesRequestor.isRequired(claimed_id, requestedAttribute), this.attributesRequestor.getCount(claimed_id, requestedAttribute));
                }
                authReq.addExtension((MessageExtension)fetchRequest);
            }
            return authReq.getDestinationUrl(true);
        }
        catch (YadisException e) {
            if (e.getErrorCode() == 1796) {
                throw new SSOAgentException(e.getMessage(), (Throwable)e);
            }
            throw new SSOAgentException("Error while creating FetchRequest", (Throwable)e);
        }
        catch (MessageException e2) {
            throw new SSOAgentException("Error while creating FetchRequest", (Throwable)e2);
        }
        catch (DiscoveryException e3) {
            throw new SSOAgentException("Error while doing OpenID Discovery", (Throwable)e3);
        }
        catch (ConsumerException e4) {
            throw new SSOAgentException("Error while doing OpenID Authentication", (Throwable)e4);
        }
        catch (ClassNotFoundException e5) {
            throw new SSOAgentException("Error while instantiating AttributeRequestorImplClass: " + SSOAgentConfigs.getAttributesRequestorImplClass(), e5);
        }
        catch (InstantiationException e6) {
            throw new SSOAgentException("Error while instantiating AttributeRequestorImplClass: " + SSOAgentConfigs.getAttributesRequestorImplClass(), e6);
        }
        catch (IllegalAccessException e7) {
            throw new SSOAgentException("Error while instantiating AttributeRequestorImplClass: " + SSOAgentConfigs.getAttributesRequestorImplClass(), e7);
        }
    }
    
    public void processOpenIDLoginResponse(final HttpServletRequest request, final HttpServletResponse response) throws SSOAgentException {
        try {
            final ParameterList authResponseParams = new ParameterList(request.getParameterMap());
            final SSOAgentSessionBean ssoAgentSessionBean = (SSOAgentSessionBean)request.getSession(false).getAttribute(SSOAgentConfigs.getSessionBeanName());
            if (ssoAgentSessionBean == null) {
                throw new SSOAgentException("Error while verifying OpenID response. Cannot find valid session for user");
            }
            final DiscoveryInformation discovered = ssoAgentSessionBean.getOpenIDSessionBean().getDiscoveryInformation();
            final VerificationResult verificationResult = OpenIDManager.consumerManager.verify(SSOAgentConfigs.getReturnTo(), authResponseParams, discovered);
            final Identifier verified = verificationResult.getVerifiedId();
            if (verified == null) {
                throw new SSOAgentException("OpenID verification failed");
            }
            final AuthSuccess authSuccess = (AuthSuccess)verificationResult.getAuthResponse();
            ssoAgentSessionBean.getOpenIDSessionBean().setClaimedId(authSuccess.getIdentity());
            if (authSuccess.hasExtension("http://openid.net/srv/ax/1.0")) {
                final Map<String, List<String>> attributesMap = new HashMap<String, List<String>>();
                final String[] attrArray = this.attributesRequestor.getRequestedAttributes(authSuccess.getIdentity());
                final FetchResponse fetchResp = (FetchResponse)authSuccess.getExtension("http://openid.net/srv/ax/1.0");
                for (final String attr : attrArray) {
                    final List attributeValues = fetchResp.getAttributeValuesByTypeUri(this.attributesRequestor.getTypeURI(authSuccess.getIdentity(), attr));
                    if (attributeValues.get(0) instanceof String && ((String) attributeValues.get(0)).split(",").length > 1) {
                        final String[] arr$2;
                        final String[] splitString = arr$2 = ((String) attributeValues.get(0)).split(",");
                        for (final String part : arr$2) {
                            attributeValues.add(part);
                        }
                    }
                    if (attributeValues.get(0) != null) {
                        attributesMap.put(attr, attributeValues);
                    }
                }
                ssoAgentSessionBean.getOpenIDSessionBean().setOpenIdAttributes(attributesMap);
            }
        }
        catch (AssociationException e) {
            throw new SSOAgentException("Error while verifying OpenID response", (Throwable)e);
        }
        catch (MessageException e2) {
            throw new SSOAgentException("Error while verifying OpenID response", (Throwable)e2);
        }
        catch (DiscoveryException e3) {
            throw new SSOAgentException("Error while verifying OpenID response", (Throwable)e3);
        }
    }
    
    private SSLContext loadSSLContext() throws SSOAgentException {
        KeyStore trustStore = null;
        try {
            trustStore = SSOAgentConfigs.getKeyStore();
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            return sslContext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new SSOAgentException("Error when reading keystore", e);
        }
        catch (KeyManagementException e2) {
            throw new SSOAgentException("Error when reading keystore", e2);
        }
        catch (KeyStoreException e3) {
            throw new SSOAgentException("Error when reading keystore", e3);
        }
    }
    
    static {
        OpenIDManager.consumerManager = null;
    }
}
