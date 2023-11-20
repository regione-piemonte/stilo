/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DebugInvioBean {

	private boolean invia;
	private boolean abilitaInvioMittente = false;

	public boolean isAbilitaInvioMittente() {
		return abilitaInvioMittente;
	}

	public void setAbilitaInvioMittente(boolean abilitaInvioMittente) {
		this.abilitaInvioMittente = abilitaInvioMittente;
	}

	public boolean isInvia() {
		return invia;
	}

	public void setInvia(boolean invia) {
		this.invia = invia;
	}
}
