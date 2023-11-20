/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class SessionFileConfigurator {

	private List<SessionFile> sessions;

	public void setSessions(List<SessionFile> sessions) {
		this.sessions = sessions;
	}

	public List<SessionFile> getSessions() {
		return sessions;
	}
}
