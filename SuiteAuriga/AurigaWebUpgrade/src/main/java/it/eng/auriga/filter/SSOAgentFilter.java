/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.wso2.carbon.identity.sso.agent.bean.SSOAgentSessionBean;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import org.wso2.carbon.identity.sso.agent.oauth2.SAML2GrantAccessTokenRequestor;
import org.wso2.carbon.identity.sso.agent.openid.OpenIDManager;
import org.wso2.carbon.identity.sso.agent.saml.SAML2SSOManager;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;

public class SSOAgentFilter implements Filter {
	
	private static final Logger logSSO = Logger.getLogger(SSOAgentFilter.class);
    private SAML2SSOManager samlSSOManager;
    private OpenIDManager openIdManager;
    
    public void init(FilterConfig fConfig) throws ServletException {
        try {
        	logSSO.debug("Inizializzo SSOAgentFilter");
            SSOAgentConfigs.initConfig(fConfig);
            SSOAgentConfigs.initCheck();
            this.samlSSOManager = new SAML2SSOManager();
            this.openIdManager = new OpenIDManager();
//            fConfig.getServletContext().addListener("org.wso2.carbon.identity.sso.agent.saml.SSOAgentHttpSessionListener");
        } catch (SSOAgentException e) {
        	logSSO.error(e.getMessage(), e);
            throw e;
        }
    }
    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
        	logSSO.debug("Sono dentro");
            HttpServletRequest request = (HttpServletRequest)req;
            HttpServletResponse response = (HttpServletResponse)res;
            String toReturnURL = request.getRequestURI().toString();
            logSSO.debug("Controllo accesso URL: " + toReturnURL);
            logSSO.debug("SessionID=" + request.getSession().getId());
            if (!toReturnURL.equals(request.getContextPath() + "/samlsso") && !toReturnURL.equals(request.getContextPath() + "/logout") && !toReturnURL.equals(request.getContextPath() + "/acs.jsp") && !toReturnURL.equals(request.getContextPath() + "/loginSchemeSelection.jsp")) {
                HttpSession session = request.getSession();
                session.setAttribute("toReturnURL", (Object)toReturnURL);
                logSSO.debug("URL a cui ritornare a valle del processo di autenticazione: " + toReturnURL);
            }
            String samlRequest = request.getParameter("SAMLRequest");
            logSSO.debug("samlRequest: ");
            logSSO.debug(samlRequest);
            String samlResponse = request.getParameter("SAMLResponse");
            logSSO.debug("samlResponse: ");
            logSSO.debug(samlResponse);
            String openid_mode = request.getParameter("openid.mode");
            String claimed_id = request.getParameter(SSOAgentConfigs.getClaimedIdParameterName());
            Label_0872: {
                if ((SSOAgentConfigs.isSAMLSSOLoginEnabled() || SSOAgentConfigs.isSPIDSSOLoginEnabled()) && samlRequest != null) {
                    this.samlSSOManager.doSLO(request);
                } else {
                    if ((SSOAgentConfigs.isSAMLSSOLoginEnabled() || SSOAgentConfigs.isSPIDSSOLoginEnabled()) && samlResponse != null) {
                        try {
                            this.samlSSOManager.processResponse(request);
                            break Label_0872;
                        } catch (SSOAgentException e) {
                            if (request.getSession(false) != null) {
                                request.getSession(false).removeAttribute(SSOAgentConfigs.getSessionBeanName());
                            }
                            logSSO.error(e.getMessage(), e);
                            res.setContentType("text/html");
							PrintWriter out = res.getWriter();
							out.print(IOUtils.toString(SSOAgentFilter.class.getResourceAsStream("utenteNonAccreditato.jsp")));
							return;
                        }
                    }
                    if (SSOAgentConfigs.isOpenIDLoginEnabled() && openid_mode != null && !openid_mode.equals("") && !openid_mode.equals("null")) {
                        try {
                            this.openIdManager.processOpenIDLoginResponse(request, response);
                            break Label_0872;
                        } catch (SSOAgentException e) {
                            if (request.getSession(false) != null) {
                                request.getSession(false).removeAttribute(SSOAgentConfigs.getSessionBeanName());
                            }
                            logSSO.error(e.getMessage(), e);
                            throw e;
                        }
                    }
                    if ((SSOAgentConfigs.isSAMLSSOLoginEnabled() || SSOAgentConfigs.isSPIDSSOLoginEnabled()) && SSOAgentConfigs.isSLOEnabled() && request.getRequestURI().contains("logout")) {
                        if (request.getSession(false) != null) {
                            response.sendRedirect(this.samlSSOManager.buildRequest(request, true, false));
                            return;
                        }
                    } else {
                        if ((SSOAgentConfigs.isSAMLSSOLoginEnabled() || SSOAgentConfigs.isSPIDSSOLoginEnabled()) && request.getRequestURI().endsWith(SSOAgentConfigs.getSAMLSSOUrl())) {
                            response.sendRedirect(this.samlSSOManager.buildRequest(request, false, false));
                            return;
                        }
                        if (SSOAgentConfigs.isOpenIDLoginEnabled() && request.getRequestURI().endsWith(SSOAgentConfigs.getOpenIdUrl()) && claimed_id != null && !claimed_id.equals("") && !claimed_id.equals("null")) {
                            response.sendRedirect(this.openIdManager.doOpenIDLogin(request, response));
                            return;
                        }
                        if ((SSOAgentConfigs.isSAMLSSOLoginEnabled() || SSOAgentConfigs.isSPIDSSOLoginEnabled() || SSOAgentConfigs.isOpenIDLoginEnabled()) && !request.getRequestURI().endsWith(SSOAgentConfigs.getLoginUrl()) && (request.getSession(false) == null || request.getSession(false).getAttribute(SSOAgentConfigs.getSessionBeanName()) == null)) {
                            response.sendRedirect(this.samlSSOManager.buildRequest(request, false, !SSOAgentConfigs.isForceAuthn()));
                            return;
                        }
                        if ((SSOAgentConfigs.isSAMLSSOLoginEnabled() || SSOAgentConfigs.isSPIDSSOLoginEnabled()) && SSOAgentConfigs.isSAML2GrantEnabled() && request.getRequestURI().endsWith(SSOAgentConfigs.getSAML2GrantUrl()) && request.getSession(false) != null && request.getSession(false).getAttribute(SSOAgentConfigs.getSessionBeanName()) != null && ((SSOAgentSessionBean)request.getSession().getAttribute(SSOAgentConfigs.getSessionBeanName())).getSAMLSSOSessionBean() != null && ((SSOAgentSessionBean)request.getSession(false).getAttribute(SSOAgentConfigs.getSessionBeanName())).getSAMLSSOSessionBean().getSAMLAssertion() != null) {
                            SAML2GrantAccessTokenRequestor.getAccessToken(request);
                        }
                    }
                }
            }
            chain.doFilter((ServletRequest)request, (ServletResponse)response);
        } catch (SSOAgentException e2) {
            logSSO.error(e2.getMessage(), e2);
            throw e2;
        }
    }
    
    public void destroy() {
    }

}