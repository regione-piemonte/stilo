/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SapVociPegRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String entrateUscite;
	private String esercizio;
	private String capitolo;
	private String articolo;
	private String numero;
	private String competenzaPluriennale;
	private String descrizioneCapitolo;
	private String pdcLivello1;
	private String missione;
	private String programma;
	private String cdr;
	private String descrizioneCdr;
	private String can;
	private String descrizioneCan;
	private String disponibile;
	private String foglia;
	private String ente;
	private String codiceUtente;
	private String password;
	
	public String getEntrateUscite() {
		return entrateUscite;
	}
	
	public void setEntrateUscite(String entrateUscite) {
		this.entrateUscite = entrateUscite;
	}
	
	public String getEsercizio() {
		return esercizio;
	}
	
	public void setEsercizio(String esercizio) {
		this.esercizio = esercizio;
	}
	
	public String getCapitolo() {
		return capitolo;
	}
	
	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}
	
	public String getArticolo() {
		return articolo;
	}
	
	public void setArticolo(String articolo) {
		this.articolo = articolo;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getCompetenzaPluriennale() {
		return competenzaPluriennale;
	}
	
	public void setCompetenzaPluriennale(String competenzaPluriennale) {
		this.competenzaPluriennale = competenzaPluriennale;
	}
	
	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}
	
	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}
	
	public String getPdcLivello1() {
		return pdcLivello1;
	}
	
	public void setPdcLivello1(String pdcLivello1) {
		this.pdcLivello1 = pdcLivello1;
	}
	
	public String getMissione() {
		return missione;
	}
	
	public void setMissione(String missione) {
		this.missione = missione;
	}
	
	public String getProgramma() {
		return programma;
	}
	
	public void setProgramma(String programma) {
		this.programma = programma;
	}
	
	public String getCdr() {
		return cdr;
	}
	
	public void setCdr(String cdr) {
		this.cdr = cdr;
	}
	
	public String getDescrizioneCdr() {
		return descrizioneCdr;
	}
	
	public void setDescrizioneCdr(String descrizioneCdr) {
		this.descrizioneCdr = descrizioneCdr;
	}
	
	public String getCan() {
		return can;
	}
	
	public void setCan(String can) {
		this.can = can;
	}
	
	public String getDescrizioneCan() {
		return descrizioneCan;
	}
	
	public void setDescrizioneCan(String descrizioneCan) {
		this.descrizioneCan = descrizioneCan;
	}
	
	public String getDisponibile() {
		return disponibile;
	}
	
	public void setDisponibile(String disponibile) {
		this.disponibile = disponibile;
	}
	
	public String getFoglia() {
		return foglia;
	}
	
	public void setFoglia(String foglia) {
		this.foglia = foglia;
	}
	
	public String getEnte() {
		return ente;
	}
	
	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	public String getCodiceUtente() {
		return codiceUtente;
	}
	
	public void setCodiceUtente(String codiceUtente) {
		this.codiceUtente = codiceUtente;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "SapVociPegRequest [entrateUscite=" + entrateUscite + ", esercizio=" + esercizio + ", capitolo="
				+ capitolo + ", articolo=" + articolo + ", numero=" + numero + ", competenzaPluriennale="
				+ competenzaPluriennale + ", descrizioneCapitolo=" + descrizioneCapitolo + ", pdcLivello1="
				+ pdcLivello1 + ", missione=" + missione + ", programma=" + programma + ", cdr=" + cdr
				+ ", descrizioneCdr=" + descrizioneCdr + ", can=" + can + ", descrizioneCan=" + descrizioneCan
				+ ", disponibile=" + disponibile + ", foglia=" + foglia + ", ente=" + ente + ", codiceUtente="
				+ codiceUtente + ", password=" + password + "]";
	}
	
}
