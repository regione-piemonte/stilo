/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProsaMittenteDestinatario implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codiceMezzoSpedizione;
	private String denominazione;
	private String email;
	private String indirizzo;
	private Boolean invioPC;
	private String codiceMittenteDestinatario;
	private String citta;
	private String cognome;
	private String nome;
	private String tipo;

	public String getCodiceMezzoSpedizione() {
		return codiceMezzoSpedizione;
	}

	public void setCodiceMezzoSpedizione(String codiceMezzoSpedizione) {
		this.codiceMezzoSpedizione = codiceMezzoSpedizione;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Boolean getInvioPC() {
		return invioPC;
	}

	public void setInvioPC(Boolean invioPC) {
		this.invioPC = invioPC;
	}

	public String getCodiceMittenteDestinatario() {
		return codiceMittenteDestinatario;
	}

	public void setCodiceMittenteDestinatario(String codiceMittenteDestinatario) {
		this.codiceMittenteDestinatario = codiceMittenteDestinatario;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
