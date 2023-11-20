/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.util;

public class SSOAgentConstants
{
    public static final String HTTP_POST_PARAM_SAML2_AUTH_REQ = "SAMLRequest";
    public static final String HTTP_POST_PARAM_SAML2_RESP = "SAMLResponse";
    public static final String IDP_SESSION = "IdPSession";
    public static final String SPID_AUTH_CONTEXT_CLASS_LEVEL_1 = "urn:oasis:names:tc:SAML:2.0:ac:classes:SpidL1";
    public static final String SPID_AUTH_CONTEXT_CLASS_LEVEL_2 = "urn:oasis:names:tc:SAML:2.0:ac:classes:SpidL2";
    public static final String SPID_AUTH_CONTEXT_CLASS_LEVEL_3 = "urn:oasis:names:tc:SAML:2.0:ac:classes:SpidL3";
    public static final String OPENID_MODE = "openid.mode";
    
    public class StatusCodes
    {
        public static final String IDENTITY_PROVIDER_ERROR = "urn:oasis:names:tc:SAML:2.0:status:Responder";
        public static final String NO_PASSIVE = "urn:oasis:names:tc:SAML:2.0:status:NoPassive";
    }
}
