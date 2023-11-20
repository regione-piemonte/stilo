/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class GWTCacheControlFilter implements Filter {
	
	private static Logger mLogger = Logger.getLogger(GWTCacheControlFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain pFilterChain)
			throws IOException, ServletException {

		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String requestURI = httpRequest.getRequestURI();

			if (requestURI.contains(".nocache.")) {
				Date now = new Date();
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.setDateHeader("Date", now.getTime());
				// one day old
				httpResponse.setDateHeader("Expires", now.getTime() - 86400000L);
				httpResponse.setHeader("Pragma", "no-cache");
				httpResponse.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
			}

			pFilterChain.doFilter(request, response);
		} catch (Exception e) {
			mLogger.warn(e);
		}
	}

	@Override
	public void destroy() {
		

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		

	}

}
