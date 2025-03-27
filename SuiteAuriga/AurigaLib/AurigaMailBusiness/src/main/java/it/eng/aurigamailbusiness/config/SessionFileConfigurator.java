/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.aurigamailbusiness.config;

import java.util.List;

public class SessionFileConfigurator {

	private List<SessionFile> sessions;
	private SessionFile defaultSession;

	public void setSessions(List<SessionFile> sessions) {
		this.sessions = sessions;
	}

	public List<SessionFile> getSessions() {
		return sessions;
	}

	public SessionFile getDefaultSession() {
		return defaultSession;
	}

	public void setDefaultSession(SessionFile defaultSession) {
		this.defaultSession = defaultSession;
	}

}
