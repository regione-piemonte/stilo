package it.eng.dm.sira.service.procedimento;

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
