/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class XmlUtentiProfBean {
	
	// 1: Id. del utente in T_UTENTI_MOD_PEC (ID_FRUITORE_CASELLA)
	@NumeroColonna(numero = "1")
	private String idUtenteModPec;
	
	// 2: Id. dell'utente in DMT_USERS
	@NumeroColonna(numero = "2")
	private String idUser;
	
	// 3: Username dell'utente
	@NumeroColonna(numero = "3")
	private String username;
	
	// 4: (valori 1/0) Flag di profilo "smistatore" (gestione di tutte le mail ricevute)
	@NumeroColonna(numero = "4")
	private String flgSmistatore;
	
	// 5: (valori 1/0) Flag di profilo "mittente" (= abilitato all'invio di e-mail dalla casella)
	@NumeroColonna(numero = "5")
	private String flgMittente;
	
	// 6: (valori 1/0) Flag di profilo "amministratore"
	@NumeroColonna(numero = "6")
	private String flgAmministratore;
	
	// 7: Cognome e nome
	@NumeroColonna(numero = "7")
	private String cognomeNome;

	
	public String getIdUtenteModPec() {
		return idUtenteModPec;
	}

	public void setIdUtenteModPec(String idUtenteModPec) {
		this.idUtenteModPec = idUtenteModPec;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFlgSmistatore() {
		return flgSmistatore;
	}

	public void setFlgSmistatore(String flgSmistatore) {
		this.flgSmistatore = flgSmistatore;
	}

	public String getFlgMittente() {
		return flgMittente;
	}

	public void setFlgMittente(String flgMittente) {
		this.flgMittente = flgMittente;
	}

	public String getFlgAmministratore() {
		return flgAmministratore;
	}

	public void setFlgAmministratore(String flgAmministratore) {
		this.flgAmministratore = flgAmministratore;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}
	
}

