/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.config.SessionFile;

import java.util.List;

public class ActivitiSessionFileConfigurator {

	private List<SessionFile> sessions;

	public void setSessions(List<SessionFile> sessions) {
		this.sessions = sessions;
	}

	public List<SessionFile> getSessions() {
		return sessions;
	}
}
