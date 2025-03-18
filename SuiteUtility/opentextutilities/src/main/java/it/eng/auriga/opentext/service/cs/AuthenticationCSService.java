/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.MalformedURLException;

import it.eng.auriga.opentext.exception.AuthenticationException;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.bean.OTAuthenticationInfo;

public interface AuthenticationCSService {

	public OTAuthenticationInfo executeAuthentication() throws MalformedURLException, AuthenticationException;

	public OTAuthenticationInfo executeAuthentication(String username, String password)
			throws MalformedURLException, AuthenticationException;

	OTAuthenticationInfo executeImpersonation(String usernameToImpersonate, String adminUsername, String adminPassword)
			throws ContentServerException, MalformedURLException;

}
