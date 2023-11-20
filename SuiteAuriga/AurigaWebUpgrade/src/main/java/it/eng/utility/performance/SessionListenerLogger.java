/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.utility.ui.listener.SessionListenerBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class SessionListenerLogger {
	
	private static Logger mLogger = Logger.getLogger(SessionListenerLogger.class);
	
	public SessionListenerLogger() {
		
	}
	
	public void createdSession(SessionListenerBean bean){
		mLogger.debug("CREATED Session: " + bean.getIdSession()
		+ " createTime: " + bean.getCreationTime()
		+ " denominazione: " + bean.getDenominazione()
		+ " userid: " + bean.getUserid()
		+ " lastAccess: " + bean.getSessionLastAccessedTime()
		+ " with maxInactiveInterval: " + bean.getSessionMaxInactiveInterval());
	}
	
	public void destroySession(SessionListenerBean bean){
		mLogger.debug("DESTROY Session: " + bean.getIdSession()
		+ " denominazione: " + bean.getDenominazione()
		+ " userid: " + bean.getUserid()
        + " createTime: " + bean.getCreationTime()
        + " lastAccess: " + bean.getSessionLastAccessedTime()
        + " with maxInactiveInterval: " + bean.getSessionMaxInactiveInterval());
	}

}