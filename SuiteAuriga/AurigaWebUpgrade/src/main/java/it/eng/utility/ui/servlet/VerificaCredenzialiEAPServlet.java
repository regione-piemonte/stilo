/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gwt.thirdparty.json.JSONObject;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.authentication.AuthType;
import it.eng.utility.authentication.Authentication;
import it.eng.utility.authentication.DBAuth;
import it.eng.utility.authentication.LdapAuth;

@Controller
@RequestMapping("/verificaLoginEAP")
public class VerificaCredenzialiEAPServlet {

	private static Logger mLogger = Logger.getLogger(VerificaCredenzialiEAPServlet.class);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get(HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception {
		String resultHtml = "<hml><h1>Hello verifica login</h1><html>";
		mLogger.debug(resultHtml);
		servletresponse.setHeader("Content-Type", "text/html;charset=ISO-8859-1");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		return new ResponseEntity<String>(resultHtml, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> verifica(@RequestBody String strJsonBody, HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse, ModelMap model) throws Exception {
		mLogger.debug("VerificaCredenzialiEAPServlet -> verifica");
//		mLogger.debug("Stringa json ricevuta nel body BASE64: " + strJsonBody);
		strJsonBody = new String(Base64.decodeBase64(strJsonBody));
//		mLogger.debug("Stringa json ricevuta nel body dopo conversione da BASE64: " + strJsonBody);
		JSONObject jsonBody = new JSONObject(strJsonBody);
		//Recupero l'utente 
		String username = jsonBody.getString("username");
		//Recupero la password con la concatenazione dello schema 
		String passwordAndSchema = jsonBody.getString("password");
		// Recupero il flag e forzare l'autenticazione db
		String strForceDBAuth = jsonBody.getString("forceDBAuth");
		boolean forceDBAuth = strForceDBAuth != null ? Boolean.getBoolean(strForceDBAuth) : false;				
		// Verifico le credenziali
//		mLogger.debug("Chiamo il servizio di logn con username: " + username + " passwordAndSchema: " + passwordAndSchema + " forceDBAuth: " + forceDBAuth);
		boolean login = makeLogin(username, passwordAndSchema, forceDBAuth);
		mLogger.debug("login vale: " + login);
		// Preparo la response
		servletresponse.setHeader("Content-Type", "text/html;charset=ISO-8859-1");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		if (login) {
			return new ResponseEntity<String>("verificaCredenzialiOK",  HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("verificaCredenzialiKO",  HttpStatus.OK);
		}
	}

	private boolean makeLogin(String username, String password, boolean forceDBAuth) {
		Authentication auth = (Authentication) SpringAppContext.getContext().getBean("authentication");
		boolean esito = false;
		if (auth.getAuthType().equals(AuthType.DB) || forceDBAuth == true) {
			DBAuth db = new DBAuth();
			esito = db.authenticate(username, password);
		} else if (auth.getAuthType().equals(AuthType.LDAP)) {
			String[] passwords = password.split("#SCHEMA#");
			String pwd = password;
			if (passwords != null && passwords.length > 0)
				pwd = passwords[0];
			LdapAuth ldap = (LdapAuth) SpringAppContext.getContext().getBean("ldapAuth");
			esito = ldap.authenticate(username, pwd);
		}
		return esito;
	}
	
}
