/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.authentication.module;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.script.ScriptException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginapplicazioneBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLogindaportletBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;
import it.eng.client.DmpkLoginLoginapplicazione;
import it.eng.client.DmpkLoginLogindaportlet;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.authentication.AuthType;
import it.eng.utility.authentication.Authentication;
import it.eng.utility.authentication.DBAuth;
import it.eng.utility.authentication.LdapAuth;
import it.eng.utility.ui.module.core.server.service.SJCLServer;

public class LoginModuleImpl implements LoginModule {

	private static Logger mLogger = Logger.getLogger(LoginModuleImpl.class);

	// private Locale locale = AurigaUserUtil.getLocale();

	protected String username;
	protected String password;
	private boolean verification;
	Callback[] calls = new Callback[2];
	private SJCLServer sjcl;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {

		try {
			sjcl = (SJCLServer) SpringAppContext.getContext().getBean("sjcl");
			// mLogger.debug("sjcl: "+String.valueOf(sjcl));
		} catch (BeansException e) {
			mLogger.warn("Recupero del bean 'sjcl' per la cifratura non riuscito.", e);
		}

		calls[0] = new NameCallback("Username");
		calls[1] = new PasswordCallback("Password", false);
		try {
			callbackHandler.handle(calls);
		} catch (IOException e) {
			mLogger.warn(e);
		} catch (UnsupportedCallbackException e) {
			mLogger.warn(e);
		}
		NameCallback lNameCallback = (NameCallback) calls[0];
		PasswordCallback lPasswordCallback = (PasswordCallback) calls[1];

		final String localUsername = lNameCallback.getName();
		final String localPassword = new String(lPasswordCallback.getPassword());

		username = localUsername; // sjcl.decrypt(localUsername);
		password = localPassword;
		if (sjcl != null) {
			try {
				password = sjcl.decrypt(localPassword, true);
			} catch (ScriptException e) {
				mLogger.error("Decifratura della password non riuscita.", e);
				password = localPassword;
			}
		}
	}

	@Override
	public boolean login() throws LoginException {

		mLogger.debug("Start Login");

		// se arrivo da una applicazione esterna tramite portlet
		
		if (username != null && username.contains("#COD_APPL#") && username.contains("#COD_APPL_IST#")) {
			
			String userid = username.substring(0, username.indexOf("#COD_APPL#"));
			String codApplicazione = username.substring(username.indexOf("#COD_APPL#") + 10, username.indexOf("#COD_APPL_IST#"));
			String codIstanzaAppl = username.substring(username.indexOf("#COD_APPL_IST#") + 14);
			
			String schema = null;
			String applicationKey = null;
			if(password != null) {
				String[] values = password.split("#SCHEMA#");
				if (values.length == 1) {
					// Ho solo lo schema
					schema = values[0];
				} else {
					applicationKey = values[0].length() > 0 ? values[0] : null;
					schema = values[1];
				}
			}
			
			DmpkLoginLogindaportletBean input = new DmpkLoginLogindaportletBean();
			input.setCodapplicazionein(codApplicazione);
			input.setCodistanzaapplin(codIstanzaAppl);
			input.setUseridin(userid);
			input.setPasswordin(applicationKey);
			
			AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
			// Inserisco la lingua di default
			lAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
			lAurigaLoginBean.setSchema(schema);
			
			DmpkLoginLogindaportlet loginDaPortlet = new DmpkLoginLogindaportlet();
			
			StoreResultBean<DmpkLoginLogindaportletBean> result;
			Locale locale = new Locale("it", "IT");
			mLogger.debug("DMPK_LOGIN.LoginDaPortlet");
			mLogger.debug("schema: " + schema);
			mLogger.debug("codApplicazione: " + codApplicazione);
			mLogger.debug("codIstanzaAppl: " + codIstanzaAppl);
			mLogger.debug("userid: " + userid);
			mLogger.debug("password: " + applicationKey);
			try {
				result = loginDaPortlet.execute(locale, lAurigaLoginBean, input);
			} catch (Exception e) {
				mLogger.warn(e);
				return false;
			}
			if (result.isInError()) {
				mLogger.error(result.getDefaultMessage(), new Throwable(
						"Messaggio: " + result.getDefaultMessage() + " errorContext: " + result.getErrorContext() + "errorCode: " + result.getErrorCode()));
				return false;
			}
			return true;
		}
		
		if (username != null && username.startsWith("USERID_APPL#")) {
			
			String useridappl = username.substring(12);
			String[] values = password.split("#SCHEMA#");
			String schema = null;
			String realPassword = null;
			if (values.length == 1) {
				// Ho solo lo schema
				schema = values[0];
			} else {
				realPassword = values[0].length() > 0 ? values[0] : null;
				schema = values[1];
			}

			DmpkLoginLoginapplicazioneBean input = new DmpkLoginLoginapplicazioneBean();
			input.setUseridapplicazionein(useridappl);
			input.setPasswordin(realPassword);

			AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
			// Inserisco la lingua di default
			lAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
			lAurigaLoginBean.setSchema(schema);
			
			DmpkLoginLoginapplicazione loginApplicazione = new DmpkLoginLoginapplicazione();
			StoreResultBean<DmpkLoginLoginapplicazioneBean> result;
			Locale locale = new Locale("it", "IT");
			mLogger.debug("DMPK_LOGIN.LoginApplicazione");
			mLogger.debug("schema: " + schema);
			mLogger.debug("useridApplicazione: " + username);
			mLogger.debug("password: " + realPassword);
			try {
				result = loginApplicazione.execute(locale, lAurigaLoginBean, input);
			} catch (Exception e) {
				mLogger.warn(e);
				return false;
			}
			if (result.isInError()) {
				mLogger.error(result.getDefaultMessage(), new Throwable(
						"Messaggio: " + result.getDefaultMessage() + " errorContext: " + result.getErrorContext() + "errorCode: " + result.getErrorCode()));
				return false;
			}
			return true;
		}

		Authentication auth = (Authentication) SpringAppContext.getContext().getBean("authentication");
		boolean esito = false;
		if (auth.getAuthType().equals(AuthType.DB)) {
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

	@Override
	public boolean commit() throws LoginException {
		mLogger.debug("Login ok");
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		mLogger.debug("Login error");
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		return false;
	}

}
