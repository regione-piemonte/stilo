/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.saml;

import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;
import org.wso2.carbon.identity.sso.agent.bean.SSOAgentSessionBean;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SSOAgentHttpSessionListener implements HttpSessionListener
{
    public void sessionCreated(final HttpSessionEvent httpSessionEvent) {
    }
    
    public void sessionDestroyed(final HttpSessionEvent httpSessionEvent) {
        final SSOAgentSessionBean sessionBean = (SSOAgentSessionBean)httpSessionEvent.getSession().getAttribute(SSOAgentConfigs.getSessionBeanName());
        if (sessionBean != null && sessionBean.getSAMLSSOSessionBean() != null && sessionBean.getSAMLSSOSessionBean().getIdPSessionIndex() != null) {
            SSOAgentSessionManager.getSsoSessions().remove(sessionBean.getSAMLSSOSessionBean().getIdPSessionIndex());
        }
    }
}
