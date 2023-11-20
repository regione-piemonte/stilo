/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import it.eng.utility.filter.FilterBase;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.UserUtil;


public class CSRFValidationFilter extends FilterBase {

	public static final String NAME_ATTRIBUTE_CSRF_TOKEN = "Csrf-Token";
	public static final String NAME_COOKIE_CSRF_TOKEN = "Csrf-Token";
	public static final String NAME_HEADER_CSRF_TOKEN = "X-"+NAME_COOKIE_CSRF_TOKEN;
    private static final Logger LOGGER = Logger.getLogger(CSRFValidationFilter.class);

    private URL targetOrigin;
    private boolean filterEnabled;
    
  
    public CSRFValidationFilter() {
    	this.targetOrigin = null;
		this.filterEnabled = true;
	}


	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	    if (!filterEnabled) {
	    	chain.doFilter(request, response);
	    	return;
	    }
		
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		
    	final HttpServletRequest httpReq = (HttpServletRequest) request;
        final HttpServletResponse httpResp = (HttpServletResponse) response;
        final HttpSession session = httpReq.getSession(false);
        String accessDeniedReason;
        
		if (session == null) {
			chain.doFilter(request, response);
			return;
		}
		
		final LoginBean loginBean = UserUtil.getLoginInfo(session);
		if (loginBean == null) {
			chain.doFilter(request, response);
			return;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("loginBean: "+String.valueOf(loginBean));
		}
 
		//Try to get the source from the "Origin" header
        String reqHeaderValueSrc = httpReq.getHeader("Origin"); //scheme://host:port
        LOGGER.debug("reqHeaderValueOrigin: "+String.valueOf(reqHeaderValueSrc));
        if ( isBlank(reqHeaderValueSrc) ) {
            //If empty then fallback on "Referer" header
            reqHeaderValueSrc = httpReq.getHeader("Referer");
            LOGGER.debug("reqHeaderValueReferer: "+String.valueOf(reqHeaderValueSrc));
            //If this one is empty too then we trace the event and we block the request (recommendation of the article)...
            if ( isBlank(reqHeaderValueSrc) ) {
                accessDeniedReason = "CSRFValidationFilter: ORIGIN and REFERER request headers are both absent/empty so we block the request !";
                LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                LOGGER.warn(accessDeniedReason);
                LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//                httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
                chain.doFilter(request, response);
                return;
            }
        }

        //Compare the source against the expected target origin
        LOGGER.debug("targetOrigin: "+String.valueOf(targetOrigin));
        if (targetOrigin != null) {
	        URL sourceURL = new URL(reqHeaderValueSrc); 
	        LOGGER.debug("sourceURL: "+String.valueOf(sourceURL));
	        if (!targetOrigin.getProtocol().equals(sourceURL.getProtocol()) 
	        		|| !targetOrigin.getHost().equals(sourceURL.getHost()) 
	        		|| targetOrigin.getPort() != sourceURL.getPort()) {
	            //One the part do not match so we trace the event and we block the request
	            accessDeniedReason = String.format("CSRFValidationFilter: Protocol/Host/Port do not fully matches so we block the request! (%s != %s) ", targetOrigin, sourceURL);
	            LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	            LOGGER.warn(accessDeniedReason);
	            LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//	            httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
	            chain.doFilter(request, response);
	            return;
	        }
        }
        
        final String sessionCsrfToken = (String) session.getAttribute(NAME_ATTRIBUTE_CSRF_TOKEN);
        LOGGER.debug("sessionCsrfToken: "+String.valueOf(sessionCsrfToken));
        
        final String cookieTokenName = determineCookieName(httpReq);
        LOGGER.debug("cookieTokenName: "+String.valueOf(cookieTokenName));
        Cookie reqCookieToken = null;
        final Cookie[] cookiesReq = httpReq.getCookies();
        if (cookiesReq != null) {
            for (Cookie c : cookiesReq) {
               final boolean flag = c.getName().equals(cookieTokenName);
               if (flag) {
            	   reqCookieToken = c;
            	   break;
               }
			}            
        }
        LOGGER.debug("reqCookieToken: " + String.valueOf(reqCookieToken));
        if (reqCookieToken == null || isBlank(reqCookieToken.getValue())) {
        	// reqCookieToken è nullo oppure è NON nullo ma con un valore NON significativo.
        	LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            LOGGER.warn("CSRFValidationFilter: CSRF cookie absent or value is null/empty so we provide one and return an HTTP NO_CONTENT response !");
            LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            
            //Set response state to "204 No Content" in order to allow the requester to clearly identify an initial response providing the initial CSRF token
//            httpResp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            chain.doFilter(request, response);

        } else {
        	// reqCookieToken è non nullo ed ha un valore significativo.
        	
            //If the cookie is present then we pass to validation phase
            //Get token from the custom HTTP header (part under control of the requester)
            String reqHeaderValueToken = httpReq.getHeader(NAME_HEADER_CSRF_TOKEN);
            LOGGER.info("reqHeaderValueToken: " + String.valueOf(reqHeaderValueToken));
            
            //If empty then we trace the event and we block the request
            if ( isBlank(reqHeaderValueToken) ) {
            	
                accessDeniedReason = "CSRFValidationFilter: Token provided via HTTP Header is absent/empty so we block the request !";
                LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                LOGGER.warn(accessDeniedReason);
                LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//                httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
                chain.doFilter(request, response);
                
//            } else if (!reqHeaderValueToken.equals(reqCookieToken.getValue())) {
//            	
//                //Verify that token from header and one from cookie are the same
//                //Here is not the case so we trace the event and we block the request
//                accessDeniedReason = "CSRFValidationFilter: Token provided via HTTP Header and via Cookie are not equals so we block the request !";
//                LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//                LOGGER.warn(accessDeniedReason);
//                LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//                httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
                
            } else if (!reqHeaderValueToken.equals(sessionCsrfToken)) {

                    accessDeniedReason = "CSRFValidationFilter: Token provided via HTTP Header is not valid so we block the request !";
                    LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    LOGGER.warn(accessDeniedReason);
                    LOGGER.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//                    httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedReason);
                    chain.doFilter(request, response);
            } else {
//            	LOGGER.debug("|||||||||||||||||||||||| RICHIESTA ACCETTATA! |||||||||||||||||||||||||||||||||||||||||||||||");
                //Verify that token from header and one from cookie matches
                //Here is the case so we let the request reach the target component (ServiceServlet, jsp...) 
                chain.doFilter(request, response);
            }
        }//else
        
    }//doFilter

    public URL getTargetOrigin() {
		return targetOrigin;
	}
	public void setTargetOrigin(URL targetOrigin) {
		this.targetOrigin = targetOrigin;
	}
	
	public boolean isFilterEnabled() {
		return filterEnabled;
	}
	public void setFilterEnabled(boolean filterEnabled) {
		this.filterEnabled = filterEnabled;
	}
	
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
    private String determineCookieName(HttpServletRequest httpRequest) {
    	final boolean flagOnlyName = false;
    	String backendServiceName = flagOnlyName ? "" : "-" + httpRequest.getContextPath().replaceAll("/", "-");
        return NAME_COOKIE_CSRF_TOKEN + backendServiceName;
    }

}
