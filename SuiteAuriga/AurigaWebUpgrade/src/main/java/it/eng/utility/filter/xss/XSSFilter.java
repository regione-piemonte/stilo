/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import it.eng.utility.filter.FilterBase;

public class XSSFilter extends FilterBase {
	
	private static final Logger logger = Logger.getLogger(XSSFilter.class);
	
	private boolean filterEnabled = false;
	
	public boolean isFilterEnabled() {
		return filterEnabled;
	}
	public void setFilterEnabled(boolean filterEnabled) {
		this.filterEnabled = filterEnabled;
	}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	super.init(filterConfig);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	if (filterEnabled) {
    		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    	} else {
    		chain.doFilter(request, response);
    	}
    }
	@Override
	protected Logger getLogger() {
		return logger;
	}

}