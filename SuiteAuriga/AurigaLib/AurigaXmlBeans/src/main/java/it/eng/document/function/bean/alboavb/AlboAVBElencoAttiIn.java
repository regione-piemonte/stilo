/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoAttiIn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idEnte;

    private java.lang.String tipoAtto;

    private java.lang.String oggetto;

    private java.lang.String enteRichiestaPubblicazione;

    private java.lang.String dataPubblicazioneA;

    private java.lang.String dataPubblicazioneDa;

    private java.lang.String dataScadenzaDa;

    private java.lang.String dataScadenzaA;

    private java.lang.String dataAdozioneDa;

    private java.lang.String dataAdozioneA;

    private java.lang.String numeroRegistroGenerale;

    private java.lang.String numeroRegistroDiArea;

	public AlboAVBElencoAttiIn() {
	}

	public AlboAVBElencoAttiIn(long idEnte, String tipoAtto, String oggetto, String enteRichiestaPubblicazione,
			String dataPubblicazioneA, String dataPubblicazioneDa, String dataScadenzaDa, String dataScadenzaA,
			String dataAdozioneDa, String dataAdozioneA, String numeroRegistroGenerale, String numeroRegistroDiArea) {
		this.idEnte = idEnte;
		this.tipoAtto = tipoAtto;
		this.oggetto = oggetto;
		this.enteRichiestaPubblicazione = enteRichiestaPubblicazione;
		this.dataPubblicazioneA = dataPubblicazioneA;
		this.dataPubblicazioneDa = dataPubblicazioneDa;
		this.dataScadenzaDa = dataScadenzaDa;
		this.dataScadenzaA = dataScadenzaA;
		this.dataAdozioneDa = dataAdozioneDa;
		this.dataAdozioneA = dataAdozioneA;
		this.numeroRegistroGenerale = numeroRegistroGenerale;
		this.numeroRegistroDiArea = numeroRegistroDiArea;
	}

	public long getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(long idEnte) {
		this.idEnte = idEnte;
	}

	public java.lang.String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(java.lang.String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public java.lang.String getEnteRichiestaPubblicazione() {
		return enteRichiestaPubblicazione;
	}

	public void setEnteRichiestaPubblicazione(java.lang.String enteRichiestaPubblicazione) {
		this.enteRichiestaPubblicazione = enteRichiestaPubblicazione;
	}

	public java.lang.String getDataPubblicazioneA() {
		return dataPubblicazioneA;
	}

	public void setDataPubblicazioneA(java.lang.String dataPubblicazioneA) {
		this.dataPubblicazioneA = dataPubblicazioneA;
	}

	public java.lang.String getDataPubblicazioneDa() {
		return dataPubblicazioneDa;
	}

	public void setDataPubblicazioneDa(java.lang.String dataPubblicazioneDa) {
		this.dataPubblicazioneDa = dataPubblicazioneDa;
	}

	public java.lang.String getDataScadenzaDa() {
		return dataScadenzaDa;
	}

	public void setDataScadenzaDa(java.lang.String dataScadenzaDa) {
		this.dataScadenzaDa = dataScadenzaDa;
	}

	public java.lang.String getDataScadenzaA() {
		return dataScadenzaA;
	}

	public void setDataScadenzaA(java.lang.String dataScadenzaA) {
		this.dataScadenzaA = dataScadenzaA;
	}

	public java.lang.String getDataAdozioneDa() {
		return dataAdozioneDa;
	}

	public void setDataAdozioneDa(java.lang.String dataAdozioneDa) {
		this.dataAdozioneDa = dataAdozioneDa;
	}

	public java.lang.String getDataAdozioneA() {
		return dataAdozioneA;
	}

	public void setDataAdozioneA(java.lang.String dataAdozioneA) {
		this.dataAdozioneA = dataAdozioneA;
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
}
