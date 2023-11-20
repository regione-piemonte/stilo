/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class UtenteBean extends VisualBean {
	
	@NumeroColonna(numero = "1")
	private String idUtente;
	@NumeroColonna(numero = "2")
	private String cognomeNome;
	@NumeroColonna(numero = "3")
	private String codice;
	@NumeroColonna(numero = "4")
	private String username;
	
	private String cognome;
	private String nome;
	
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getCognomeNome() {
		return cognomeNome;
	}
	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
		
}
