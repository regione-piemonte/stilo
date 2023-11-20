/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import it.eng.gdpr.ParametriClientExtractor;
import it.eng.gdpr.tomcat.ClientDataThreadLocal;
import it.eng.utility.LogLoginTryCallHelper;

public class EnhancedLoginModuleImpl extends LoginModuleImpl {

	private static Logger logger = Logger.getLogger(EnhancedLoginModuleImpl.class);

	private Subject subject;
	private LogLoginTryCallHelper logLoginTryHelperCall = new LogLoginTryCallHelper();

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		super.initialize(subject, callbackHandler, sharedState, options);
	}

	@Override
	public boolean commit() throws LoginException {
		return super.commit();
	}

	@Override
	public boolean abort() throws LoginException {
		// logger.debug("Eseguo abort() sovrascritto.");
		final boolean result = super.abort();
		// logger.debug("Recupero informazioni sul client.");
		String parametriClient = null;
		try {
			// logger.debug(ClientDataThreadLocal.get());
			Map<String, String> clientData = ClientDataThreadLocal.get();
			if (clientData == null) {
				final String msg = "Classe 'AurigaValve' non specificata tramite tag Valve in server.xml.";
				// parametriClient = "{\"avvertimento\":\"" + msg + "\"}";
				logger.warn(msg);
				return result;
			} else if (clientData.isEmpty()) {
				final String msg = "Classe 'AurigaValve' specificata ma non abilitata tramite tag Valve in server.xml.";
				// parametriClient = "{\"avvertimento\":\"" + msg + "\"}";
				logger.warn(msg);
				return result;
			}
			parametriClient = ParametriClientExtractor.getJsonParametriClient(clientData);
		} catch (Exception e) {
			final String msg = "Il recupero dei parametri del client è fallito.";
			logger.error(msg, e);
			// throw new LoginException(msg);
			parametriClient = "{\"errore\":\"" + msg + "\"}";
		}
		logger.debug("Chiamo la stored '" + LogLoginTryCallHelper.FUNCTION_NAME + "'.");
		try {
			final String[] values = password.split("#SCHEMA#");
			final String schema = values.length == 1 ? values[0] : values[1];
			logLoginTryHelperCall.call(schema, null, username, true, null, null, null, parametriClient);
		} catch (Exception e) {
			final String msg = "La chiamata della function '" + LogLoginTryCallHelper.FUNCTION_NAME + "' è fallita.";
			logger.error(msg, e);
			// throw new LoginException(msg);
			return result;
		}
		return result;
	}

	@Override
	public boolean logout() throws LoginException {
		return super.logout();
	}

}// EnhancedLoginModuleImpl
