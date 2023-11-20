/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Locale;

import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginVerificacredenzialiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.client.DmpkLoginVerificacredenziali;

public class DBAuth {
	
	private static Logger mLogger = Logger.getLogger(DBAuth.class);
	
	public boolean authenticate(String username, String password) {
		
		DmpkLoginVerificacredenziali lDmpkLoginVerificacredenziali = new DmpkLoginVerificacredenziali();
		DmpkLoginVerificacredenzialiBean lDmpkLoginVerificacredenzialiBean = new DmpkLoginVerificacredenzialiBean();
		lDmpkLoginVerificacredenzialiBean.setUsernamein(username);
		String[] values = password.split("#SCHEMA#");
		String schema;
		String realPassword = null;
		if (values.length==1){
			//Ho solo lo schema
			schema = values[0];
		} else {
			realPassword = values[0].length()>0?values[0]:null;//SHA1Encrypt.encrypt(values[0]):null;
			schema = values[1];
		}
		lDmpkLoginVerificacredenzialiBean.setPasswordin(realPassword);
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(schema);
		Locale locale = new Locale("it", "IT");
		StoreResultBean<DmpkLoginVerificacredenzialiBean> result;
		mLogger.debug("Schema vale " + schema);
		mLogger.debug("realPassword vale " + realPassword);
		mLogger.debug("username vale " + username);
		
		try {
			result = lDmpkLoginVerificacredenziali.execute(locale, lSchemaBean, lDmpkLoginVerificacredenzialiBean);
		} catch (Exception e) {
			mLogger.error(e);
			return false;
		}
		if (result.isInError()) {
			mLogger.error(result.getDefaultMessage(), new Throwable("Messaggio: " + 
					result.getDefaultMessage() + " errorContext: " + result.getErrorContext() 
					+ "errorCode: " + result.getErrorCode()));
			return false;
		} else return true;
	}
}