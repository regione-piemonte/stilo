/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.utility.filter.FilterBase;

public class RerAuthorizationFilter extends FilterBase {

	private static Logger mLogger = Logger.getLogger(RerAuthorizationFilter.class);

	private FilterConfig filterConfig;

	private String loginUrl;
	private String usernameHeaderName;
	private String matricolaHeaderName;

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}	
	
	public String getUsernameHeaderName() {
		return usernameHeaderName;
	}
	
	public void setUsernameHeaderName(String usernameHeaderName) {
		this.usernameHeaderName = usernameHeaderName;
	}

	public String getMatricolaHeaderName() {
		return matricolaHeaderName;
	}

	
	public void setMatricolaHeaderName(String matricolaHeaderName) {
		this.matricolaHeaderName = matricolaHeaderName;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest pServletRequest, ServletResponse pServletResponse, FilterChain pFilterChain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) pServletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) pServletResponse;
		// Controllo l'header della request per verificare l'autorizzazione
		String iamUsername = getIamUsername(httpRequest);
		String iamMatricola = getIamMatricola(httpRequest);
		
		boolean autorizzato = StringUtils.isNotBlank(iamUsername) || StringUtils.isNotBlank(iamMatricola);
		if (!autorizzato) {
			mLogger.debug("iamUsername non presente, eseguo la redirect alla login");
			httpResponse.sendRedirect(loginUrl);
		} else {
			pFilterChain.doFilter(pServletRequest, pServletResponse);
		}
		return;
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
	
	public String getIamUsername(HttpServletRequest request) {
		return StringUtils.isNotBlank(request.getHeader(getUsernameHeaderName())) ? request.getHeader(getUsernameHeaderName()).toUpperCase() :  request.getHeader(getUsernameHeaderName());
	}
	
	public String getIamMatricola(HttpServletRequest request) {
		return StringUtils.isNotBlank(request.getHeader(getMatricolaHeaderName())) ? request.getHeader(getMatricolaHeaderName()).toUpperCase() :  request.getHeader(getMatricolaHeaderName());
	}

}
