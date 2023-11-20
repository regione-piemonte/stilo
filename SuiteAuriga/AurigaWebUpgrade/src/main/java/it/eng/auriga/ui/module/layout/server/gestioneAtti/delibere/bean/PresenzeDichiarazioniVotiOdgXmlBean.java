/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class PresenzeDichiarazioniVotiOdgXmlBean {

	@NumeroColonna(numero = "1")
	private String idUser;

	@NumeroColonna(numero = "2")
	private String denominazione;
	
	@NumeroColonna(numero = "4")
	private String ruoloSeduta;
	
	@NumeroColonna(numero = "5")
	private String flgPresenza;

	@NumeroColonna(numero = "6")
	private String voto; // Valori possibili: astenuto, contrario, in aula.

	@NumeroColonna(numero = "7")
	private String idDelegato;

	@NumeroColonna(numero = "8")
	private String decodificaDelegato;
	
	@NumeroColonna(numero = "9")
	private String flgExtra;
	
	@NumeroColonna(numero = "10")
	private String flgVotoExtra;

	// Valori utilizzati solamente per creare la SezioneCache da passare al modello (non vengono utilizzati da store)
	private String presenzaCodificata;

	// Concateno id + decodifica delegato per gestione select nella grid
	private String delegato;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getRuoloSeduta() {
		return ruoloSeduta;
	}

	public void setRuoloSeduta(String ruoloSeduta) {
		this.ruoloSeduta = ruoloSeduta;
	}

	public String getFlgPresenza() {
		return flgPresenza;
	}

	public void setFlgPresenza(String flgPresenza) {
		this.flgPresenza = flgPresenza;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public String getIdDelegato() {
		return idDelegato;
	}

	public void setIdDelegato(String idDelegato) {
		this.idDelegato = idDelegato;
	}

	public String getDecodificaDelegato() {
		return decodificaDelegato;
	}

	public void setDecodificaDelegato(String decodificaDelegato) {
		this.decodificaDelegato = decodificaDelegato;
	}

	public String getFlgExtra() {
		return flgExtra;
	}

	public void setFlgExtra(String flgExtra) {
		this.flgExtra = flgExtra;
	}

	public String getFlgVotoExtra() {
		return flgVotoExtra;
	}

	public void setFlgVotoExtra(String flgVotoExtra) {
		this.flgVotoExtra = flgVotoExtra;
	}

	public String getPresenzaCodificata() {
		return presenzaCodificata;
	}

	public void setPresenzaCodificata(String presenzaCodificata) {
		this.presenzaCodificata = presenzaCodificata;
	}

	public String getDelegato() {
		return delegato;
	}

	public void setDelegato(String delegato) {
		this.delegato = delegato;
	}

}