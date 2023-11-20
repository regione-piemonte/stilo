/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

public class CASAuthenticationFilter extends AbstractCasFilter {
	
	/**
     * The URL to the CAS Server login.
     */
    private String casServerLoginUrl = "";
    
    static String casServerUrlPrefix = "";
    
    static String logout = "";

    /**
     * Whether to send the renew request or not.
     */
    private boolean renew = false;

    /**
     * Whether to send the gateway request or not.
     */
    private boolean gateway = false;
    
    private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
            log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
            log.trace("Loaded renew parameter: " + this.renew);
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
            log.trace("Loaded gateway parameter: " + this.gateway);

            final String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);

            if (gatewayStorageClass != null) {
                try {
                    this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
                } catch (final Exception e) {
                    log.error(e,e);
                    throw new ServletException(e);
                }
            }      	
        }
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
       
    	InputStream is = null;
    	try {
	    	final HttpServletRequest request = (HttpServletRequest) servletRequest;
	        final HttpServletResponse response = (HttpServletResponse) servletResponse;
	        final HttpSession session = request.getSession(false);
	        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;
	
	        if (assertion != null) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	        if(casServerLoginUrl!=null && casServerLoginUrl.startsWith("file:")) {
	      	  setCasServerLoginUrl(getClientIp(request, this.casServerLoginUrl));
	        }
	
	        CASAuthenticationFilter.casServerUrlPrefix = casServerLoginUrl.replace("/login", "");
	        Properties prop = new Properties();
	        String webappPath = request.getRealPath(File.separator + "WEB-INF" + File.separator + "casServer.properties");
	        is = new FileInputStream(webappPath);
	    	prop.load(is);
	    	String logout = prop.getProperty("logoutRedirectUrl");
	        CASAuthenticationFilter.logout = casServerLoginUrl.replace("/login", logout);
	
	        GenericConfigBean genericConfigBean = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
	        if (genericConfigBean != null) {
	        	genericConfigBean.setLogoutRedirectUrl(CASAuthenticationFilter.logout);
	    		}
	//        final String casServerLoginUrl = getClientIp(request);
	        final String serviceUrl = constructServiceUrl(request, response);
	        final String ticket = CommonUtils.safeGetParameter(request,getArtifactParameterName());
	        final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);
	
	        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	
	        final String modifiedServiceUrl;
	
	        log.debug("no ticket and no assertion found");
	        if (this.gateway) {
	            log.debug("setting gateway attribute in session");
	            modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
	        } else {
	            modifiedServiceUrl = serviceUrl;
	        }
	
	        if (log.isDebugEnabled()) {
	            log.debug("Constructed service url: " + modifiedServiceUrl);
	        }
	
	        final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);
	
	        if (log.isDebugEnabled()) {
	            log.debug("redirecting to \"" + urlToRedirectTo + "\"");
	        }
	
	        response.sendRedirect(urlToRedirectTo);
    	} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
		}
    }

    public final void setRenew(final boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(final boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerLoginUrl(final String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }
    
    public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
    	this.gatewayStorage = gatewayStorage;
    }
   
    private static String getClientIp(HttpServletRequest request, String nomeFile) throws IOException {
    	
    	InputStream is = null;
    	try {
	    	Properties prop = new Properties();
	    	String webappBase = request.getRealPath(File.separator + "WEB-INF" + File.separator + nomeFile.replaceAll("file:", ""));
	    	is = new FileInputStream(webappBase);
	    	
	    	prop.load(is);
	    	String remoteAddr = prop.getProperty(request.getServerName());        	
	   
	        return remoteAddr;
    	} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
		}
    }

	public static String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}
}