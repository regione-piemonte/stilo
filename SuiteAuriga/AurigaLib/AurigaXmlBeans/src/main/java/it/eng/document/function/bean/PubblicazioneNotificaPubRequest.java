/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PubblicazioneNotificaPubRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String annoPubblicazione;
	private String dataFinePubblicazione;
	private String dataInizioPubblicazione;
	private List<PubblicazioneElencoDocumentiRequest> elencoDocumentiPubblicazione;
	private String idDocPrimario;
	private String idUD;
	private String mittente;
	private String numeroPubblicazione;
	private String numeroRegistrazione;
	private String oggetto;
	private String tipoDocumento;
	private String stato;
	private boolean interruzioneTermini;
	
	public String getAnnoPubblicazione() {
		return annoPubblicazione;
	}
	
	public void setAnnoPubblicazione(String annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}
	
	public String getDataFinePubblicazione() {
		return dataFinePubblicazione;
	}
	
	public void setDataFinePubblicazione(String dataFinePubblicazione) {
		this.dataFinePubblicazione = dataFinePubblicazione;
	}
	
	public String getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}
	
	public void setDataInizioPubblicazione(String dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}
	
	public List<PubblicazioneElencoDocumentiRequest> getElencoDocumentiPubblicazione() {
		return elencoDocumentiPubblicazione;
	}
	
	public void setElencoDocumentiPubblicazione(List<PubblicazioneElencoDocumentiRequest> elencoDocumentiPubblicazione) {
		this.elencoDocumentiPubblicazione = elencoDocumentiPubblicazione;
	}
	
	public String getIdDocPrimario() {
		return idDocPrimario;
	}
	
	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	
	public String getIdUD() {
		return idUD;
	}
	
	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}
	
	public String getMittente() {
		return mittente;
	}
	
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	
	public String getNumeroPubblicazione() {
		return numeroPubblicazione;
	}
	
	public void setNumeroPubblicazione(String numeroPubblicazione) {
		this.numeroPubblicazione = numeroPubblicazione;
	}
	
	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}
	
	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public boolean isInterruzioneTermini() {
		return interruzioneTermini;
	}
	
	public void setInterruzioneTermini(boolean interruzioneTermini) {
		this.interruzioneTermini = interruzioneTermini;
	}
	
	@Override
	public String toString() {
		return "PubblicazioneNotificaPubRequest [annoPubblicazione=" + annoPubblicazione + ", dataFinePubblicazione="
				+ dataFinePubblicazione + ", dataInizioPubblicazione=" + dataInizioPubblicazione
				+ ", elencoDocumentiPubblicazione=" + elencoDocumentiPubblicazione + ", idDocPrimario=" + idDocPrimario
				+ ", idUD=" + idUD + ", mittente=" + mittente + ", numeroPubblicazione=" + numeroPubblicazione
				+ ", numeroRegistrazione=" + numeroRegistrazione + ", oggetto=" + oggetto + ", tipoDocumento="
				+ tipoDocumento + ", stato=" + stato + ", interruzioneTermini=" + interruzioneTermini + "]";
	}
	
}
