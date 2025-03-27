/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SapVociPegResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String entrateUscite;
	private String esercizio;
	private String capitolo;
	private String articolo;
	private String numero;
	private String descrizioneCapitolo;
	private String descrizioneCdr;
	private String descrizioneCan;
	private String competenzaPluriennale;
	private String titolo;
	private String funzione;
	private String servizio;
	private String intervento;
	private String pdcLivello1;
	private String pdcLivello2;
	private String pdcLivello3;
	private String pdcLivello4;
	private String pdcLivello5;
	private String pdcArmonizzatoAlf;
	private String missione;
	private String programma;
	private String cdr;
	private String can;
	private String direzioneCentrale;
	private float proposto;
	private float previsione;
	private float iniziale;
	private String approvato;
	private String totaleVariazioni;
	private float assestato;
	private float impegnatoAccertato;
	private float disponibile;
	private float mandatiReversaliEmessi;
	private float mandatiReversaliPagati;
	private String foglia;
	
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
	
	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}
	
	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}
	
	public String getDescrizioneCdr() {
		return descrizioneCdr;
	}
	
	public void setDescrizioneCdr(String descrizioneCdr) {
		this.descrizioneCdr = descrizioneCdr;
	}
	
	public String getDescrizioneCan() {
		return descrizioneCan;
	}
	
	public void setDescrizioneCan(String descrizioneCan) {
		this.descrizioneCan = descrizioneCan;
	}
	
	public String getCompetenzaPluriennale() {
		return competenzaPluriennale;
	}
	
	public void setCompetenzaPluriennale(String competenzaPluriennale) {
		this.competenzaPluriennale = competenzaPluriennale;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public String getFunzione() {
		return funzione;
	}
	
	public void setFunzione(String funzione) {
		this.funzione = funzione;
	}
	
	public String getServizio() {
		return servizio;
	}
	
	public void setServizio(String servizio) {
		this.servizio = servizio;
	}
	
	public String getIntervento() {
		return intervento;
	}
	
	public void setIntervento(String intervento) {
		this.intervento = intervento;
	}
	
	public String getPdcLivello1() {
		return pdcLivello1;
	}
	
	public void setPdcLivello1(String pdcLivello1) {
		this.pdcLivello1 = pdcLivello1;
	}
	
	public String getPdcLivello2() {
		return pdcLivello2;
	}
	
	public void setPdcLivello2(String pdcLivello2) {
		this.pdcLivello2 = pdcLivello2;
	}
	
	public String getPdcLivello3() {
		return pdcLivello3;
	}
	
	public void setPdcLivello3(String pdcLivello3) {
		this.pdcLivello3 = pdcLivello3;
	}
	
	public String getPdcLivello4() {
		return pdcLivello4;
	}
	
	public void setPdcLivello4(String pdcLivello4) {
		this.pdcLivello4 = pdcLivello4;
	}
	
	public String getPdcLivello5() {
		return pdcLivello5;
	}
	
	public void setPdcLivello5(String pdcLivello5) {
		this.pdcLivello5 = pdcLivello5;
	}
	
	public String getPdcArmonizzatoAlf() {
		return pdcArmonizzatoAlf;
	}
	
	public void setPdcArmonizzatoAlf(String pdcArmonizzatoAlf) {
		this.pdcArmonizzatoAlf = pdcArmonizzatoAlf;
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
	
	public String getCan() {
		return can;
	}
	
	public void setCan(String can) {
		this.can = can;
	}
	
	public String getDirezioneCentrale() {
		return direzioneCentrale;
	}
	
	public void setDirezioneCentrale(String direzioneCentrale) {
		this.direzioneCentrale = direzioneCentrale;
	}
	
	public float getProposto() {
		return proposto;
	}
	
	public void setProposto(float proposto) {
		this.proposto = proposto;
	}
	
	public float getPrevisione() {
		return previsione;
	}
	
	public void setPrevisione(float previsione) {
		this.previsione = previsione;
	}
	
	public float getIniziale() {
		return iniziale;
	}
	
	public void setIniziale(float iniziale) {
		this.iniziale = iniziale;
	}
	
	public String getApprovato() {
		return approvato;
	}
	
	public void setApprovato(String approvato) {
		this.approvato = approvato;
	}
	
	public String getTotaleVariazioni() {
		return totaleVariazioni;
	}
	
	public void setTotaleVariazioni(String totaleVariazioni) {
		this.totaleVariazioni = totaleVariazioni;
	}
	
	public float getAssestato() {
		return assestato;
	}
	
	public void setAssestato(float assestato) {
		this.assestato = assestato;
	}
	
	public float getImpegnatoAccertato() {
		return impegnatoAccertato;
	}
	
	public void setImpegnatoAccertato(float impegnatoAccertato) {
		this.impegnatoAccertato = impegnatoAccertato;
	}
	
	public float getDisponibile() {
		return disponibile;
	}
	
	public void setDisponibile(float disponibile) {
		this.disponibile = disponibile;
	}
	
	public float getMandatiReversaliEmessi() {
		return mandatiReversaliEmessi;
	}
	
	public void setMandatiReversaliEmessi(float mandatiReversaliEmessi) {
		this.mandatiReversaliEmessi = mandatiReversaliEmessi;
	}
	
	public float getMandatiReversaliPagati() {
		return mandatiReversaliPagati;
	}
	
	public void setMandatiReversaliPagati(float mandatiReversaliPagati) {
		this.mandatiReversaliPagati = mandatiReversaliPagati;
	}
	
	public String getFoglia() {
		return foglia;
	}
	
	public void setFoglia(String foglia) {
		this.foglia = foglia;
	}

	@Override
	public String toString() {
		return "SapVociPegResponse [entrateUscite=" + entrateUscite + ", esercizio=" + esercizio + ", capitolo="
				+ capitolo + ", articolo=" + articolo + ", numero=" + numero + ", descrizioneCapitolo="
				+ descrizioneCapitolo + ", descrizioneCdr=" + descrizioneCdr + ", descrizioneCan=" + descrizioneCan
				+ ", competenzaPluriennale=" + competenzaPluriennale + ", titolo=" + titolo + ", funzione=" + funzione
				+ ", servizio=" + servizio + ", intervento=" + intervento + ", pdcLivello1=" + pdcLivello1
				+ ", pdcLivello2=" + pdcLivello2 + ", pdcLivello3=" + pdcLivello3 + ", pdcLivello4=" + pdcLivello4
				+ ", pdcLivello5=" + pdcLivello5 + ", pdcArmonizzatoAlf=" + pdcArmonizzatoAlf + ", missione=" + missione
				+ ", programma=" + programma + ", cdr=" + cdr + ", can=" + can + ", direzioneCentrale="
				+ direzioneCentrale + ", proposto=" + proposto + ", previsione=" + previsione + ", iniziale=" + iniziale
				+ ", approvato=" + approvato + ", totaleVariazioni=" + totaleVariazioni + ", assestato=" + assestato
				+ ", impegnatoAccertato=" + impegnatoAccertato + ", disponibile=" + disponibile
				+ ", mandatiReversaliEmessi=" + mandatiReversaliEmessi + ", mandatiReversaliPagati="
				+ mandatiReversaliPagati + ", foglia=" + foglia + "]";
	}
	
}
