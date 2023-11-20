/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class SelezionaTipiAttoBean {
	
	@NumeroColonna(numero="1")
	private String idtipoAtto;
		
	@NumeroColonna(numero="2")
	private String denominazioneAtto;
	
	private boolean flgAbilitazione;

	public String getIdtipoAtto() {
		return idtipoAtto;
	}

	public void setIdtipoAtto(String idtipoAtto) {
		this.idtipoAtto = idtipoAtto;
	}

	public String getDenominazioneAtto() {
		return denominazioneAtto;
	}

	public void setDenominazioneAtto(String denominazioneAtto) {
		this.denominazioneAtto = denominazioneAtto;
	}

	public boolean isFlgAbilitazione() {
		return flgAbilitazione;
	}

	public void setFlgAbilitazione(boolean flgAbilitazione) {
		this.flgAbilitazione = flgAbilitazione;
	}
	
}
