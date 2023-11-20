/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.shared.bean.ParametriDBBean;
import it.eng.utility.filter.FilterBase;
import it.eng.utility.ui.module.core.server.service.AbstractRootService;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;


public class EncryptionDisabledFilter extends FilterBase {
	
	private static final Logger LOGGER = Logger.getLogger(EncryptionDisabledFilter.class);
	
    private boolean filterEnabled;
    private String targets;
    private String[] targetArray;
    
    
    public EncryptionDisabledFilter() {
		this.filterEnabled = true;
		this.targets = null;
		this.targetArray = null;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
	    if (!filterEnabled) {
	    	chain.doFilter(request, response);
	    	return;
	    }
		
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		
    	final HttpServletRequest httpReq = (HttpServletRequest) request;
        final HttpSession session = httpReq.getSession(false);
        
		if (session == null) {
			chain.doFilter(request, response);
			return;
		}
		
		final LoginBean loginBean = UserUtil.getLoginInfo(session);
		LOGGER.debug("loginBean: "+String.valueOf(loginBean));
		if (loginBean != null) {
			chain.doFilter(request, response);
			return;
		}
        
//        if (LOGGER.isDebugEnabled()) {
//            final Enumeration<?> names = httpReq.getHeaderNames();
//            while (names.hasMoreElements()) {
//            	final String name = names.nextElement().toString();
//            	LOGGER.debug("Header: "+name + " -> " + String.valueOf(httpReq.getHeader(name)));
//            }
//        }	
//        final String contentType = httpReq.getHeader("content-type");
    	
//    	if ( StringUtils.contains(contentType, "application/json") ) {
    		final String referer = httpReq.getHeader("referer");

    		for (String target : targetArray) {
//    			LOGGER.debug("target: "+target);
    	    	if ( StringUtils.containsIgnoreCase(referer, target) ) {
    	    		final ParametriDBBean dbParam = ParametriDBUtil.getParametriDB(session);
    				final String NAME_FLAG_CIFRATURA = AbstractRootService.NAME_FLAG_CIFRATURA;
    				boolean flagCifratura = false;
    	    		if (dbParam != null) {
    		    		LOGGER.debug("Parametri DB: "+String.valueOf(dbParam.getParametriDB()));
    		    		flagCifratura = ParametriDBUtil.getParametroDBAsBoolean(session, NAME_FLAG_CIFRATURA);
    	    		} 
    				LOGGER.info("Messo in sessione "+session.getId()+": "+NAME_FLAG_CIFRATURA+" con valore "+flagCifratura);
    				session.setAttribute(NAME_FLAG_CIFRATURA, Boolean.valueOf(flagCifratura));
    				break;
    	    	}//if
			}//for
//	    }

		chain.doFilter(request, response);	
	}
	
	public boolean isFilterEnabled() {
		return filterEnabled;
	}
	public void setFilterEnabled(boolean filterEnabled) {
		this.filterEnabled = filterEnabled;
	}
	
	public String getTargets() {
		return targets;
	}
	public void setTargets(String targets) {
		this.targets = targets;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		targetArray = StringUtils.splitPreserveAllTokens(targets, ",");
		if (filterEnabled && targetArray == null) {
			throw new ServletException("Il parametro iniziale 'targets' deve essere valorizzato.");
		}
	}
	
}
