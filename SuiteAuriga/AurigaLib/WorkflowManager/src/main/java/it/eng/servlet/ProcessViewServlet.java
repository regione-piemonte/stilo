/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ProcessViewServlet extends HttpServlet{
	
	private static Logger mLogger = Logger.getLogger(ProcessViewServlet.class);
	@Override
	protected void doGet(HttpServletRequest pHttpServletRequest, HttpServletResponse pHttpServletResponse)
			throws ServletException, IOException {
		mLogger.debug("Start doGet");
		String lStrIdProcess = pHttpServletRequest.getParameter("idProcess");
		mLogger.debug("Richiesto processo con id " + lStrIdProcess);
//		ExternalLoginHandler lExternalLoginHandler = (ExternalLoginHandler)SpringAppContext.getApplicationContext().getBean(ExternalLoginHandler.class);
		pHttpServletRequest.getSession().setAttribute("LOGIN_EXT_USERNAME", pHttpServletRequest.getParameter("usernameExt"));
		pHttpServletRequest.getSession().setAttribute("LOGIN_EXT_PASSWORD", pHttpServletRequest.getParameter("passwordExt"));
		pHttpServletResponse.sendRedirect("#myProcess/" + lStrIdProcess);
	}

}
