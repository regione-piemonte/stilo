/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.saml;

import java.security.cert.X509Certificate;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;

public interface SSOAgentCredential
{
    void init() throws SSOAgentException;
    
    PublicKey getPublicKey() throws SSOAgentException;
    
    PrivateKey getPrivateKey() throws SSOAgentException;
    
    X509Certificate getEntityCertificate() throws SSOAgentException;
}
