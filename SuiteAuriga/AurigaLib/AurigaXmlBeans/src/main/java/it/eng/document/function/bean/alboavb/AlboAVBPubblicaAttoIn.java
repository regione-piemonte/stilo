/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAttoIn implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idTipoAtto;

	private java.lang.String usernameResponsabilePubblicazione;

	private java.lang.String oggetto;

	private java.lang.String descrizione;

	private java.lang.String enteRichiestaPubblicazione;

	private java.lang.String dataPubblicazione;

	private java.lang.String dataAdozione;

	private int durataPubblicazioneAtto;

	private java.lang.Integer offsetDurataPubblicazione;

	private java.lang.String numeroRegistroGenerale;

	private java.lang.String numeroRegistroDiArea;

	private java.lang.String nomeArea;

	private java.lang.String note;

	public AlboAVBPubblicaAttoIn() {
	}

	public AlboAVBPubblicaAttoIn(long idTipoAtto, String usernameResponsabilePubblicazione, String oggetto,
			String descrizione, String enteRichiestaPubblicazione, String dataPubblicazione, String dataAdozione,
			int durataPubblicazioneAtto, Integer offsetDurataPubblicazione, String numeroRegistroGenerale,
			String numeroRegistroDiArea, String nomeArea, String note) {
		this.idTipoAtto = idTipoAtto;
		this.usernameResponsabilePubblicazione = usernameResponsabilePubblicazione;
		this.oggetto = oggetto;
		this.descrizione = descrizione;
		this.enteRichiestaPubblicazione = enteRichiestaPubblicazione;
		this.dataPubblicazione = dataPubblicazione;
		this.dataAdozione = dataAdozione;
		this.durataPubblicazioneAtto = durataPubblicazioneAtto;
		this.offsetDurataPubblicazione = offsetDurataPubblicazione;
		this.numeroRegistroGenerale = numeroRegistroGenerale;
		this.numeroRegistroDiArea = numeroRegistroDiArea;
		this.nomeArea = nomeArea;
		this.note = note;
	}

	public long getIdTipoAtto() {
		return idTipoAtto;
	}

	public void setIdTipoAtto(long idTipoAtto) {
		this.idTipoAtto = idTipoAtto;
	}

	public java.lang.String getUsernameResponsabilePubblicazione() {
		return usernameResponsabilePubblicazione;
	}

	public void setUsernameResponsabilePubblicazione(java.lang.String usernameResponsabilePubblicazione) {
		this.usernameResponsabilePubblicazione = usernameResponsabilePubblicazione;
	}

	public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public java.lang.String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

	public java.lang.String getEnteRichiestaPubblicazione() {
		return enteRichiestaPubblicazione;
	}

	public void setEnteRichiestaPubblicazione(java.lang.String enteRichiestaPubblicazione) {
		this.enteRichiestaPubblicazione = enteRichiestaPubblicazione;
	}

	public java.lang.String getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(java.lang.String dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public java.lang.String getDataAdozione() {
		return dataAdozione;
	}

	public void setDataAdozione(java.lang.String dataAdozione) {
		this.dataAdozione = dataAdozione;
	}

	public int getDurataPubblicazioneAtto() {
		return durataPubblicazioneAtto;
	}

	public void setDurataPubblicazioneAtto(int durataPubblicazioneAtto) {
		this.durataPubblicazioneAtto = durataPubblicazioneAtto;
	}

	public java.lang.Integer getOffsetDurataPubblicazione() {
		return offsetDurataPubblicazione;
	}

	public void setOffsetDurataPubblicazione(java.lang.Integer offsetDurataPubblicazione) {
		this.offsetDurataPubblicazione = offsetDurataPubblicazione;
	}

	public java.lang.String getNumeroRegistroGenerale() {
		return numeroRegistroGenerale;
	}

	public void setNumeroRegistroGenerale(java.lang.String numeroRegistroGenerale) {
		this.numeroRegistroGenerale = numeroRegistroGenerale;
	}

	public java.lang.String getNumeroRegistroDiArea() {
		return numeroRegistroDiArea;
	}

	public void setNumeroRegistroDiArea(java.lang.String numeroRegistroDiArea) {
		this.numeroRegistroDiArea = numeroRegistroDiArea;
	}

	public java.lang.String getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(java.lang.String nomeArea) {
		this.nomeArea = nomeArea;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}
}
