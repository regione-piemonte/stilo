/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.saml;

import java.util.Hashtable;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class SSOAgentSessionManager
{
    private static Map<String, HttpSession> ssoSessions;
    
    public static void invalidateSession(final String sessionIndex) {
        final HttpSession session = SSOAgentSessionManager.ssoSessions.remove(sessionIndex);
        if (session != null) {
            session.removeAttribute(SSOAgentConfigs.getSessionBeanName());
        }
    }
    
    public static void addAuthenticatedSession(final String idPSessionId, final HttpSession session) {
        SSOAgentSessionManager.ssoSessions.put(idPSessionId, session);
    }
    
    static Map<String, HttpSession> getSsoSessions() {
        return SSOAgentSessionManager.ssoSessions;
    }
    
    static {
        SSOAgentSessionManager.ssoSessions = new Hashtable<String, HttpSession>();
    }
}
