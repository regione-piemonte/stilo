/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.explorer.ExplorerApp;
import org.activiti.explorer.identity.LoggedInUser;
import org.activiti.explorer.identity.LoggedInUserImpl;
import org.activiti.explorer.ui.login.LoginHandler;
import org.apache.log4j.Logger;


public class ExternalLoginHandler implements LoginHandler {

	private static Logger mLogger = Logger.getLogger(ExternalLoginHandler.class);

	private transient IdentityService identityService;

	@Override
	public LoggedInUser authenticate(String arg0, String arg1) {
		mLogger.debug("Start authenticate username e password");
		return staticLogin(arg0, arg1);
	}

	@Override
	public LoggedInUser authenticate(HttpServletRequest pHttpServletRequest,
			HttpServletResponse pHttpServletResponse) {
		mLogger.debug("Start authenticate HttpServletRequest e HttpServletResponse");
		LoggedInUser lLoggedInUser = null;
		try {
			String lStrUsername = (String) pHttpServletRequest.getSession().getAttribute("LOGIN_EXT_USERNAME");
			String lStrPassword = (String) pHttpServletRequest.getSession().getAttribute("LOGIN_EXT_PASSWORD");

			if (lStrUsername == null && lStrPassword == null){
				mLogger.debug("Non è presente in session");
				String username = pHttpServletRequest.getParameter("usernameExt");
				String password = pHttpServletRequest.getParameter("passwordExt");
				if (username == null && password == null){
					mLogger.debug("Non è presente in request");
					return null;
				}
				lLoggedInUser = staticLogin(pHttpServletRequest.getParameter("usernameExt"), pHttpServletRequest.getParameter("passwordExt"));
				pHttpServletRequest.getSession().setAttribute("LOGIN_EXT", lLoggedInUser);
			} else {
				lLoggedInUser = staticLogin(lStrUsername, lStrPassword);
				pHttpServletRequest.getSession().setAttribute("LOGIN_EXT_USERNAME", null);
				pHttpServletRequest.getSession().setAttribute("LOGIN_EXT_PASSWORD", null);
			}
		} catch (Exception e){
			pHttpServletRequest.getSession().setAttribute("LOGIN_EXT_USERNAME", null);
			pHttpServletRequest.getSession().setAttribute("LOGIN_EXT_PASSWORD", null);
		}

		return lLoggedInUser;
	}

	protected LoggedInUser staticLogin(String username, String password) {

		User user = (User)this.identityService.createUserQuery().userId(username).singleResult();
		LoggedInUserImpl loggedInUser = new LoggedInUserImpl(user, password);
		List<Group> groups = this.identityService.createGroupQuery().groupMember(user.getId()).list();
		for (Group group : groups)
		{
			if ("security-role".equals(group.getType())) {
				loggedInUser.addSecurityRoleGroup(group);
				if ("user".equals(group.getId())) {
					loggedInUser.setUser(true);
				}
				if ("admin".equals(group.getId()))
					loggedInUser.setAdmin(true);
			}
			else if ((ExplorerApp.get().getAdminGroups() != null) && (ExplorerApp.get().getAdminGroups().contains(group.getId())))
			{
				loggedInUser.addSecurityRoleGroup(group);
				loggedInUser.setAdmin(true);
			} else if ((ExplorerApp.get().getUserGroups() != null) && (ExplorerApp.get().getUserGroups().contains(group.getId())))
			{
				loggedInUser.addSecurityRoleGroup(group);
				loggedInUser.setUser(true);
			} else {
				loggedInUser.addGroup(group);
			}
		}
		return loggedInUser;
	}

	@Override
	public void logout(LoggedInUser arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRequestEnd(HttpServletRequest arg0, HttpServletResponse arg1) {
		mLogger.debug("Start onRequestEnd");

	}

	@Override
	public void onRequestStart(HttpServletRequest arg0, HttpServletResponse arg1) {
		mLogger.debug("Start onRequestStart");

	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}


}
