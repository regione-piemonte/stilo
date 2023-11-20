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
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.utility.ui.user.AurigaUserUtil;

public class LoginFilter implements Filter {
	
	private static Logger mLogger = Logger.getLogger(LoginFilter.class);

	@Override
	public void doFilter(ServletRequest pServletRequest, ServletResponse pServletResponse,
			FilterChain pFilterChain) throws IOException, ServletException {
		
		// INIZIO - DA METTERE SENZA JAAS
		/*
		HttpServletRequest httpRequest = (HttpServletRequest) pServletRequest;
	    if (httpRequest.getRequestURI().contains("rest")) {
	    	HttpSession httpSession = httpRequest.getSession();
	    	AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(httpSession);
			if (loginBean == null) {			
	    		pServletResponse.setContentType("text/html");
				PrintWriter out = pServletResponse.getWriter();
				out.print(IOUtils.toString(LoginFilter.class.getResourceAsStream("login.jsp")));
				return;
			} 
	    }
	    */
	    // FINE - DA METTERE SENZA JAAS
		pFilterChain.doFilter(pServletRequest, pServletResponse);
		return;	    
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
