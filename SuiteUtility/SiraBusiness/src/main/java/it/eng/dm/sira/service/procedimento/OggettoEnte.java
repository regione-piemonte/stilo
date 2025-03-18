/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.dm.sira.entity.VMguOrganigramma;

public class OggettoEnte {
	
	private boolean foglia;

	private VMguOrganigramma ente;

	public VMguOrganigramma getEnte() {
		return ente;
	}

	public void setEnte(VMguOrganigramma ente) {
		this.ente = ente;
	}

	public boolean isFoglia() {
		return foglia;
	}

	public void setFoglia(boolean foglia) {
		this.foglia = foglia;
	}

}
