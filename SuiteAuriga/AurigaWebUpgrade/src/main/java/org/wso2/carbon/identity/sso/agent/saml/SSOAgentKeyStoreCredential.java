/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.saml;

import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.KeyStoreException;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import java.security.cert.X509Certificate;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SSOAgentKeyStoreCredential implements SSOAgentCredential
{
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    private static X509Certificate entityCertificate;
    
    @Override
    public void init() throws SSOAgentException {
        readX509Credentials();
    }
    
    @Override
    public PublicKey getPublicKey() {
        return SSOAgentKeyStoreCredential.publicKey;
    }
    
    @Override
    public PrivateKey getPrivateKey() {
        return SSOAgentKeyStoreCredential.privateKey;
    }
    
    @Override
    public X509Certificate getEntityCertificate() {
        return SSOAgentKeyStoreCredential.entityCertificate;
    }
    
    private static void readX509Credentials() throws SSOAgentException {
        final String privateKeyAlias = SSOAgentConfigs.getPrivateKeyAlias();
        final String privateKeyPassword = SSOAgentConfigs.getPrivateKeyPassword();
        final String idpCertAlias = SSOAgentConfigs.getIdPCertAlias();
        final KeyStore keyStore = SSOAgentConfigs.getKeyStore();
        X509Certificate cert = null;
        PrivateKey privateKey = null;
        try {
            if (privateKeyAlias != null && SSOAgentConfigs.isRequestSigned()) {
                privateKey = (PrivateKey)keyStore.getKey(privateKeyAlias, privateKeyPassword.toCharArray());
                if (privateKey == null) {
                    throw new SSOAgentException("RequestSigning is enabled, but cannot find private key with the alias " + privateKeyAlias + " in the key store");
                }
            }
            cert = (X509Certificate)keyStore.getCertificate(idpCertAlias);
            if (cert == null) {
                throw new SSOAgentException("Cannot find IDP certificate with the alias " + idpCertAlias + " in the trust store");
            }
        }
        catch (KeyStoreException e) {
            throw new SSOAgentException("Error when reading keystore", e);
        }
        catch (UnrecoverableKeyException e2) {
            throw new SSOAgentException("Error when reading keystore", e2);
        }
        catch (NoSuchAlgorithmException e3) {
            throw new SSOAgentException("Error when reading keystore", e3);
        }
        SSOAgentKeyStoreCredential.publicKey = cert.getPublicKey();
        SSOAgentKeyStoreCredential.privateKey = privateKey;
        SSOAgentKeyStoreCredential.entityCertificate = cert;
    }
    
    static {
        SSOAgentKeyStoreCredential.publicKey = null;
        SSOAgentKeyStoreCredential.privateKey = null;
        SSOAgentKeyStoreCredential.entityCertificate = null;
    }
}
