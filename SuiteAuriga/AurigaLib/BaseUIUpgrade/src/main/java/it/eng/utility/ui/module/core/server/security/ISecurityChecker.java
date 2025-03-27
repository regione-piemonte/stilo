/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.core.server.security;

import it.eng.utility.ui.module.core.server.service.RequestBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISecurityChecker {
	
	public boolean check(RequestBean request,
			HttpServletRequest servletrequest,
			HttpServletResponse servletresponse);

}
