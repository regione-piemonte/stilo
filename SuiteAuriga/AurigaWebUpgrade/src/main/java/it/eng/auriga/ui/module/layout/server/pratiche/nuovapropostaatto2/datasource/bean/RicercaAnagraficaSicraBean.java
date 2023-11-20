/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class RicercaAnagraficaSicraBean {
	
	private String codiceSoggetto;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String indirizzo;
	private String cap;
	private String localita;
	private String provincia;
	private String partitaIva;
	private String denominazione;
	
	public String getCodiceSoggetto() {
		return codiceSoggetto;
	}
	public String getNome() {
		return nome;
	}
	public String getCognome() {
		return cognome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setCodiceSoggetto(String codiceSoggetto) {
		this.codiceSoggetto = codiceSoggetto;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}
	public String getCap() {
		return cap;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
}
