/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoAttiDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String ente;

    private java.lang.String tipoAtto;

    private int anno;

    private int numeroRegistro;

    private java.lang.String numeroRegistroDiArea;

    private java.lang.String numeroRegistroGenerale;

    private java.lang.String nomeArea;

    private java.lang.String enteRichiedente;

    private java.lang.String oggetto;

    private java.lang.String note;

    private java.lang.String primoGiornoPubblicazione;

    private java.lang.String ultimoGiornoPubblicazione;

	public AlboAVBElencoAttiDto() {
	}

	public AlboAVBElencoAttiDto(String ente, String tipoAtto, int anno, int numeroRegistro, String numeroRegistroDiArea,
			String numeroRegistroGenerale, String nomeArea, String enteRichiedente, String oggetto, String note,
			String primoGiornoPubblicazione, String ultimoGiornoPubblicazione) {
		this.ente = ente;
		this.tipoAtto = tipoAtto;
		this.anno = anno;
		this.numeroRegistro = numeroRegistro;
		this.numeroRegistroDiArea = numeroRegistroDiArea;
		this.numeroRegistroGenerale = numeroRegistroGenerale;
		this.nomeArea = nomeArea;
		this.enteRichiedente = enteRichiedente;
		this.oggetto = oggetto;
		this.note = note;
		this.primoGiornoPubblicazione = primoGiornoPubblicazione;
		this.ultimoGiornoPubblicazione = ultimoGiornoPubblicazione;
	}

	public java.lang.String getEnte() {
		return ente;
	}

	public void setEnte(java.lang.String ente) {
		this.ente = ente;
	}

	public java.lang.String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(java.lang.String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public java.lang.String getNumeroRegistroDiArea() {
		return numeroRegistroDiArea;
	}

	public void setNumeroRegistroDiArea(java.lang.String numeroRegistroDiArea) {
		this.numeroRegistroDiArea = numeroRegistroDiArea;
	}

	public java.lang.String getNumeroRegistroGenerale() {
		return numeroRegistroGenerale;
	}

	public void setNumeroRegistroGenerale(java.lang.String numeroRegistroGenerale) {
		this.numeroRegistroGenerale = numeroRegistroGenerale;
	}

	public java.lang.String getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(java.lang.String nomeArea) {
		this.nomeArea = nomeArea;
	}

	public java.lang.String getEnteRichiedente() {
		return enteRichiedente;
	}

	public void setEnteRichiedente(java.lang.String enteRichiedente) {
		this.enteRichiedente = enteRichiedente;
	}

	public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getPrimoGiornoPubblicazione() {
		return primoGiornoPubblicazione;
	}

	public void setPrimoGiornoPubblicazione(java.lang.String primoGiornoPubblicazione) {
		this.primoGiornoPubblicazione = primoGiornoPubblicazione;
	}

	public java.lang.String getUltimoGiornoPubblicazione() {
		return ultimoGiornoPubblicazione;
	}

	public void setUltimoGiornoPubblicazione(java.lang.String ultimoGiornoPubblicazione) {
		this.ultimoGiornoPubblicazione = ultimoGiornoPubblicazione;
	}
}
