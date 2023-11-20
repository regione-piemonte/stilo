/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.bean;

import com.google.gson.Gson;
import java.util.Iterator;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;
import java.util.List;
import java.util.Map;
import org.openid4java.discovery.DiscoveryInformation;
import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SSOAgentSessionBean implements Serializable
{
    private static final long serialVersionUID = 3847953960752846113L;
    private OpenIDSessionBean openIDSessionBean;
    private SAMLSSOSessionBean samlssoSessionBean;
    
    public SAMLSSOSessionBean getSAMLSSOSessionBean() {
        return this.samlssoSessionBean;
    }
    
    public void setSAMLSSOSessionBean(final SAMLSSOSessionBean samlssoSessionBean) {
        this.samlssoSessionBean = samlssoSessionBean;
    }
    
    public OpenIDSessionBean getOpenIDSessionBean() {
        return this.openIDSessionBean;
    }
    
    public void setOpenIDSessionBean(final OpenIDSessionBean openIDSessionBean) {
        this.openIDSessionBean = openIDSessionBean;
    }
    
    private void writeObject(final ObjectOutputStream out) throws IOException {
        System.out.println("writeObject");
        out.defaultWriteObject();
    }
    
    private Object writeReplace() throws ObjectStreamException {
        System.out.println("writeReplace");
        return this;
    }
    
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        System.out.println("readObject");
        in.registerValidation((ObjectInputValidation)this, 0);
        in.defaultReadObject();
    }
    
    public void validateObject() throws InvalidObjectException {
        System.out.println("validateObject");
    }
    
    private Object readResolve() throws ObjectStreamException {
        System.out.println("readResolve");
        return this;
    }
    
    public class OpenIDSessionBean
    {
        private DiscoveryInformation discoveryInformation;
        private String claimedId;
        private Map<String, List<String>> openIdAttributes;
        
        public DiscoveryInformation getDiscoveryInformation() {
            return this.discoveryInformation;
        }
        
        public void setDiscoveryInformation(final DiscoveryInformation discoveryInformation) {
            this.discoveryInformation = discoveryInformation;
        }
        
        public String getClaimedId() {
            return this.claimedId;
        }
        
        public void setClaimedId(final String claimedId) {
            this.claimedId = claimedId;
        }
        
        public Map<String, List<String>> getOpenIdAttributes() {
            return this.openIdAttributes;
        }
        
        public void setOpenIdAttributes(final Map<String, List<String>> openIdAttributes) {
            this.openIdAttributes = openIdAttributes;
        }
    }
    
    public class SAMLSSOSessionBean implements Serializable
    {
        private String subjectId;
        private Response samlResponse;
        private String samlResponseString;
        private Assertion samlAssertion;
        private String samlAssertionString;
        private AccessTokenResponseBean accessTokenResponseBean;
        private String idPSessionIndex;
        private Map<String, String> samlSSOAttributes;
        
        public String getSubjectId() {
            return this.subjectId;
        }
        
        public void setSubjectId(final String subjectId) {
            this.subjectId = subjectId;
        }
        
        public Map<String, String> getSAMLSSOAttributes() {
            return this.samlSSOAttributes;
        }
        
        public void setSAMLSSOAttributes(final Map<String, String> samlSSOAttributes) {
            this.samlSSOAttributes = samlSSOAttributes;
        }
        
        public String getIdPSessionIndex() {
            return this.idPSessionIndex;
        }
        
        public void setIdPSessionIndex(final String idPSessionIndex) {
            this.idPSessionIndex = idPSessionIndex;
        }
        
        public Response getSAMLResponse() {
            return this.samlResponse;
        }
        
        public void setSAMLResponse(final Response samlResponse) {
            this.samlResponse = samlResponse;
        }
        
        public String getSAMLResponseString() {
            return this.samlResponseString;
        }
        
        public void setSAMLResponseString(final String samlResponseString) {
            this.samlResponseString = samlResponseString;
        }
        
        public Assertion getSAMLAssertion() {
            return this.samlAssertion;
        }
        
        public void setSAMLAssertion(final Assertion samlAssertion) {
            this.samlAssertion = samlAssertion;
        }
        
        public String getSAMLAssertionString() {
            return this.samlAssertionString;
        }
        
        public void setSAMLAssertionString(final String samlAssertionString) {
            this.samlAssertionString = samlAssertionString;
        }
        
        public AccessTokenResponseBean getAccessTokenResponseBean() {
            return this.accessTokenResponseBean;
        }
        
        public void setAccessTokenResponseBean(final AccessTokenResponseBean accessTokenResponseBean) {
            this.accessTokenResponseBean = accessTokenResponseBean;
        }
        
        public String getAuthnContextClassRef() {
            String AuthnContextClassRef = null;
            try {
                final List<AuthnStatement> authnStatementsList = (List<AuthnStatement>)this.getSAMLAssertion().getAuthnStatements();
                if (authnStatementsList == null) {
                    return null;
                }
                for (final AuthnStatement thisStatement : authnStatementsList) {
                    if (null != thisStatement.getAuthnContext().getAuthnContextClassRef()) {
                        AuthnContextClassRef = thisStatement.getAuthnContext().getAuthnContextClassRef().getAuthnContextClassRef();
                    }
                }
            }
            catch (Exception e) {
                return null;
            }
            return AuthnContextClassRef;
        }
    }
    
    public static class AccessTokenResponseBean
    {
        private String access_token;
        private String refresh_token;
        private String token_type;
        private String expires_in;
        
        public String getAccess_token() {
            return this.access_token;
        }
        
        public void setAccess_token(final String access_token) {
            this.access_token = access_token;
        }
        
        public String getRefresh_token() {
            return this.refresh_token;
        }
        
        public void setRefresh_token(final String refresh_token) {
            this.refresh_token = refresh_token;
        }
        
        public String getToken_type() {
            return this.token_type;
        }
        
        public void setToken_type(final String token_type) {
            this.token_type = token_type;
        }
        
        public String getExpires_in() {
            return this.expires_in;
        }
        
        public void setExpires_in(final String expires_in) {
            this.expires_in = expires_in;
        }
        
        @Override
        public String toString() {
            final Gson gson = new Gson();
            return gson.toJson((Object)this);
        }
    }
}
