/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.security.GeneralSecurityException;

import javax.annotation.PostConstruct;
import javax.script.ScriptException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Throwables;

public abstract class AbstractRootService {
	
	public static final String NAME_FLAG_CIFRATURA = "FLAG_CIFRATURA";
	private static Logger mLogger = Logger.getLogger(AbstractRootService.class);
	
	@Autowired
	protected ServletContext context; 
	
	@Autowired(required=false)
	protected SJCLServer sjcl;
	
	@PostConstruct
	protected void init() {
		mLogger.debug("context: "+String.valueOf(context));
		if (sjcl == null) {
			mLogger.warn("Il bean 'sjcl' per la cifratura non è stato recuperato!");
			return;
		}
//        final InputStream in = context.getResourceAsStream(sjcl.getFileJS());
//        sjcl.setEngine(in);
	}
		
	
	protected String encrypt(String str, boolean flagEnabled) throws ScriptException {
		if (sjcl == null) return str;
		final String strEncrypted = sjcl.encrypt(str, flagEnabled);
        return strEncrypted;
	}
	
	protected String decryptIfNeeded(String str, boolean flagEnabled) throws ScriptException, GeneralSecurityException {
		if (sjcl == null) return str;
        final String strDecrypted = sjcl.decryptIfNeeded(str, flagEnabled);
        return strDecrypted;
	}
	
	protected boolean isEncryptionEnabled() {
		return sjcl == null ? false : sjcl.isEnabled();
	}
	
	protected boolean getFlagCifratura(HttpSession session) {
		boolean flagCifratura = isEncryptionEnabled();
		final Object flagCifraturaAttr =  session.getAttribute(NAME_FLAG_CIFRATURA);
	    if (flagCifraturaAttr != null) {		       
		   flagCifratura = ((Boolean)flagCifraturaAttr).booleanValue();
		   mLogger.debug("Recuperato da sessione "+session.getId()+" "+NAME_FLAG_CIFRATURA+" con valore: "+flagCifraturaAttr);
		}
		mLogger.debug("getFlagCifratura() restituisce: "+flagCifratura);
		return flagCifratura;
	}

//	protected boolean getFlagCifratura(HttpSession session) {
//		boolean flagCifratura = isEncryptionEnabled();
//		final LoginBean loginBean = UserUtil.getLoginInfo(session);
//	    if (loginBean != null) {
//		    mLogger.debug("Dominio: "+String.valueOf(loginBean.getDominio()));
//		    final boolean flagDominio = StringUtils.isNotBlank(loginBean.getDominio());
//		    if (flagDominio) {
//		       final Boolean flagCifraturaAttr = (Boolean) session.getAttribute(NAME_FLAG_CIFRATURA);
//		       flagCifratura = BooleanUtils.isTrue(flagCifraturaAttr);
//		       mLogger.debug("Recuperato da db flagCifratura: "+flagCifratura);
//		    }
//		}
//		mLogger.debug("getFlagCifratura() restituisce: "+flagCifratura);
//		return flagCifratura;
//	}
	
	protected String normalizeErrorMessage(String errorMessage) {
		return this.normalizeErrorMessage(errorMessage, false);
	}
	
	protected String normalizeErrorMessage(String errorMessage, boolean hideErrorDetails) {
		if (errorMessage != null) {
			// se ho il messaggio ripetuto due volte lo prendo una volta sola
			String message = errorMessage;
			int index = 0;
			while (true) {
				int pos = message.indexOf("_", index);
				if (pos != -1) {
					String message1 = message.substring(0, pos);
					String message2 = message.substring(pos + 1);
					if (message1.equals(message2)) {
						message = message1;
						break;
					}
				} else {
					break;
				}
				index = pos + 1;
			}
			return hideErrorDetails ? "Si e' verificato un errore" : message;
		} else {
			return "Errore generico";
		}
	}

	protected boolean isOracleError(Throwable e) {
		final Throwable rootCause = Throwables.getRootCause(e);
		final String rootCauseMsg = rootCause.getMessage();
		mLogger.info("rootCauseMsg: " + String.valueOf(rootCauseMsg));
		final boolean flagOracleError = StringUtils.contains(rootCauseMsg, "ORA-");
		mLogger.info("flagOracleError: " + flagOracleError);
		return flagOracleError;
	}


}
