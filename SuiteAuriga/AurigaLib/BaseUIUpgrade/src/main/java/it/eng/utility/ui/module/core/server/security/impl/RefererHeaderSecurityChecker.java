/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.InetAddress;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import it.eng.utility.ui.module.core.server.security.ISecurityChecker;
import it.eng.utility.ui.module.core.server.service.RequestBean;

public class RefererHeaderSecurityChecker implements ISecurityChecker {

	static Logger mLogger = Logger.getLogger(RefererHeaderSecurityChecker.class);

	@Override
	public boolean check(RequestBean request, HttpServletRequest servletrequest, HttpServletResponse servletresponse) {

		try {
			// Host locale
			// String localAddress = InetAddress.getLocalHost().getHostName();
			// URL referer header
			String referer = servletrequest.getHeader("referer");
			// Host referer header
			String refererHostAddress = InetAddress.getByName(new URL(referer).getHost()).getHostAddress();
			InetAddress refererInetAddress = InetAddress.getByName(refererHostAddress);
			
			mLogger.debug("Referer-Header: " + refererInetAddress + " (isLoopbackAddress: "  + refererInetAddress.isLoopbackAddress() + ", isAnyLocalAddress: " + refererInetAddress.isAnyLocalAddress() + " ,isLinkLocalAddress: " +  refererInetAddress.isLinkLocalAddress() + " ,isSiteLocalAddress: " + refererInetAddress.isSiteLocalAddress());
			
			if (refererInetAddress.isLoopbackAddress() || refererInetAddress.isAnyLocalAddress() || refererInetAddress.isLinkLocalAddress() || refererInetAddress.isSiteLocalAddress()) {
				return true;
			} else {
				mLogger.error("Referer-Header " + refererInetAddress + " non valido!");
				return false;
			}
			
		} catch (Exception e) {
			mLogger.debug(e);
			return false;
		}
	}

}
