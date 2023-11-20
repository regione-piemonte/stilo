/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.saml;

import org.opensaml.xml.security.credential.UsageType;
import javax.crypto.SecretKey;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialContextSet;
import java.security.cert.X509CRL;
import java.util.Collection;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.PublicKey;
import org.opensaml.xml.security.x509.X509Credential;

public class X509CredentialImpl implements X509Credential
{
    private PublicKey publicKey;
    private X509Certificate entityCertificate;
    private PrivateKey privateKey;
    
    public X509CredentialImpl(final SSOAgentCredential credential) throws SSOAgentException {
        this.publicKey = null;
        this.entityCertificate = null;
        this.privateKey = null;
        this.publicKey = credential.getPublicKey();
        this.entityCertificate = credential.getEntityCertificate();
        this.privateKey = credential.getPrivateKey();
    }
    
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
    
    public X509Certificate getEntityCertificate() {
        return this.entityCertificate;
    }
    
    public Collection<X509CRL> getCRLs() {
        return null;
    }
    
    public Collection<X509Certificate> getEntityCertificateChain() {
        return null;
    }
    
    public CredentialContextSet getCredentalContextSet() {
        return null;
    }
    
    public Class<? extends Credential> getCredentialType() {
        return null;
    }
    
    public String getEntityId() {
        return null;
    }
    
    public Collection<String> getKeyNames() {
        return null;
    }
    
    public SecretKey getSecretKey() {
        return null;
    }
    
    public UsageType getUsageType() {
        return null;
    }
}
