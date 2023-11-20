/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.utility.filter.FilterBase;
import it.eng.utility.ui.user.AurigaUserUtil;

public class ShibbolethAuthorizationFilter extends FilterBase {
	
	private static Logger mLogger = Logger.getLogger(ShibbolethAuthorizationFilter.class);
	
	private FilterConfig filterConfig;
	
	private String usernameHeaderName;
	
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	
	public String getUsernameHeaderName() {
		return usernameHeaderName;
	}
	
	public void setUsernameHeaderName(String usernameHeaderName) {
		this.usernameHeaderName = usernameHeaderName;
	}

	@Override
	public void doFilter(ServletRequest pServletRequest, ServletResponse pServletResponse,
			FilterChain pFilterChain) throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse) pServletResponse;
		HttpServletRequest httpRequest = (HttpServletRequest) pServletRequest;
		// Loggo tutti gli header
//		mLogger.error("********** HEADER DELLA REQUEST **********");
//		Enumeration<String> requestHeaders = httpRequest.getHeaderNames();
//		while (requestHeaders.hasMoreElements()) {
//			String headerName = requestHeaders.nextElement();
//			String headerContent = httpRequest.getHeader(headerName);
//			mLogger.error(headerName + ": " + headerContent);
//		}
//		mLogger.error("********** HEADER DELLA RESPONSE **********");
//		Collection<String> responseHeaders = httpResponse.getHeaderNames();
//		Iterator<String> responseHeadersIterator = responseHeaders.iterator();
//		while (responseHeadersIterator.hasNext()) {
//			responseHeadersIterator.next();
//			String headerName = (String) responseHeadersIterator.next();
//			String headerContent = httpResponse.getHeader(headerName);
//			mLogger.error(headerName + ": " + headerContent);
//		}
		// Controllo l'header della request per verificare l'autorizzazione
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(httpRequest.getSession());
		String shibUsername = getShibUsername(httpRequest);
		// boolean autorizzato = StringUtils.isNotBlank(shibUsername);
		boolean autorizzato = loginBean != null || StringUtils.isNotBlank(shibUsername);
//		mLogger.debug("loginBean: " + loginBean);
//		mLogger.debug("shibUsername: " + shibUsername);
//		mLogger.debug("Utente autorizzato: " + autorizzato);
//		mLogger.debug("httpRequest.getRequestURI(): " + httpRequest.getRequestURI());
	    if (!autorizzato && httpRequest.getRequestURI().contains("rest")) {
	    	// mLogger.error("Non autorizzato");
    		pServletResponse.setContentType("text/html");
			PrintWriter out = pServletResponse.getWriter();
			out.print(IOUtils.toString(ShibbolethAuthorizationFilter.class.getResourceAsStream("sessionExpired.jsp")));
			return;
	    } else {
	    	pFilterChain.doFilter(pServletRequest, pServletResponse);
	    }
		return;	    
	}

	@Override
	public void destroy() {
		
	}
	
	public String getShibUsername(HttpServletRequest request) {
		return StringUtils.isNotBlank(request.getHeader(getUsernameHeaderName())) ? request.getHeader(getUsernameHeaderName()).toUpperCase() : request.getHeader(getUsernameHeaderName());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		this.filterConfig = filterConfig;
	}

	@Override
	protected Logger getLogger() {
		return mLogger;
	}

}
