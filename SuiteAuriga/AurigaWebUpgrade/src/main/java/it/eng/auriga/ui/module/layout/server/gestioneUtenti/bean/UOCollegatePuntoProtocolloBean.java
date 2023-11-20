/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class UOCollegatePuntoProtocolloBean {

	@NumeroColonna(numero="1")
	private String idUO;
		
	@NumeroColonna(numero="2")
	private String livelliUO;
	
	@NumeroColonna(numero="3")
	private String denominazioneUO;
	
	@NumeroColonna(numero="4")
	private boolean flgAbilitazione;
	
	// FIXME verificare le colonne
	// @NumeroColonna(numero="5")
	private boolean flgAbilitaEreditarieta;
	
	// @NumeroColonna(numero="6")
	private boolean flgAbilitazioneEreditata;
	
	// @NumeroColonna(numero="7")
	private String idUoAbilitazioneEreditata;

	public String getIdUO() {
		return idUO;
	}

	public void setIdUO(String idUO) {
		this.idUO = idUO;
	}

	public String getLivelliUO() {
		return livelliUO;
	}

	public void setLivelliUO(String livelliUO) {
		this.livelliUO = livelliUO;
	}

	public String getDenominazioneUO() {
		return denominazioneUO;
	}

	public void setDenominazioneUO(String denominazioneUO) {
		this.denominazioneUO = denominazioneUO;
	}

	public boolean isFlgAbilitazione() {
		return flgAbilitazione;
	}

	public void setFlgAbilitazione(boolean flgAbilitazione) {
		this.flgAbilitazione = flgAbilitazione;
	}
	
	public boolean isFlgAbilitaEreditarieta() {
		return flgAbilitaEreditarieta;
	}
	
	public void setFlgAbilitaEreditarieta(boolean flgAbilitaEreditarieta) {
		this.flgAbilitaEreditarieta = flgAbilitaEreditarieta;
	}
	
	public boolean isFlgAbilitazioneEreditata() {
		return flgAbilitazioneEreditata;
	}
	
	public void setFlgAbilitazioneEreditata(boolean flgAbilitazioneEreditata) {
		this.flgAbilitazioneEreditata = flgAbilitazioneEreditata;
	}
	
	public String getIdUoAbilitazioneEreditata() {
		return idUoAbilitazioneEreditata;
	}

	public void setIdUoAbilitazioneEreditata(String idUoAbilitazioneEreditata) {
		this.idUoAbilitazioneEreditata = idUoAbilitazioneEreditata;
	}
	
	

	
}
