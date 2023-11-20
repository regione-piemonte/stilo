/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBDettaglioAttoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idAtto;

    private long idEnte;

    private int numeroRegistro;

    private java.lang.String numeroRegistroGenerale;

    private java.lang.String numeroRegistroDiArea;

    private java.lang.String nomeArea;

    private java.lang.String oggetto;

    private java.lang.String dataPubblicazione;

    private java.lang.String dataScadenza;

    private java.lang.String dataAdozione;

    private java.lang.String ultimoGiornoDiPubblicazione;
    
    private AlboAVBDocumentoWSDTO alboAVBDocumentoWSDTO;
    
    private AlboAVBDocumentoWSDTO[] allegati;

	public AlboAVBDettaglioAttoDto() {
	}

	public AlboAVBDettaglioAttoDto(long idAtto, long idEnte, int numeroRegistro, String numeroRegistroGenerale,
			String numeroRegistroDiArea, String nomeArea, String oggetto, String dataPubblicazione, String dataScadenza,
			String dataAdozione, String ultimoGiornoDiPubblicazione, AlboAVBDocumentoWSDTO alboAVBDocumentoWSDTO,
			AlboAVBDocumentoWSDTO[] allegati) {
		this.idAtto = idAtto;
		this.idEnte = idEnte;
		this.numeroRegistro = numeroRegistro;
		this.numeroRegistroGenerale = numeroRegistroGenerale;
		this.numeroRegistroDiArea = numeroRegistroDiArea;
		this.nomeArea = nomeArea;
		this.oggetto = oggetto;
		this.dataPubblicazione = dataPubblicazione;
		this.dataScadenza = dataScadenza;
		this.dataAdozione = dataAdozione;
		this.ultimoGiornoDiPubblicazione = ultimoGiornoDiPubblicazione;
		this.alboAVBDocumentoWSDTO = alboAVBDocumentoWSDTO;
		this.allegati = allegati;
	}

	public long getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(long idAtto) {
		this.idAtto = idAtto;
	}

	public long getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(long idEnte) {
		this.idEnte = idEnte;
	}

	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
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

	public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public java.lang.String getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(java.lang.String dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public java.lang.String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(java.lang.String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public java.lang.String getDataAdozione() {
		return dataAdozione;
	}

	public void setDataAdozione(java.lang.String dataAdozione) {
		this.dataAdozione = dataAdozione;
	}

	public java.lang.String getUltimoGiornoDiPubblicazione() {
		return ultimoGiornoDiPubblicazione;
	}

	public void setUltimoGiornoDiPubblicazione(java.lang.String ultimoGiornoDiPubblicazione) {
		this.ultimoGiornoDiPubblicazione = ultimoGiornoDiPubblicazione;
	}

	public AlboAVBDocumentoWSDTO getAlboAVBDocumentoWSDTO() {
		return alboAVBDocumentoWSDTO;
	}

	public void setAlboAVBDocumentoWSDTO(AlboAVBDocumentoWSDTO alboAVBDocumentoWSDTO) {
		this.alboAVBDocumentoWSDTO = alboAVBDocumentoWSDTO;
	}

	public AlboAVBDocumentoWSDTO[] getAllegati() {
		return allegati;
	}

	public void setAllegati(AlboAVBDocumentoWSDTO[] allegati) {
		this.allegati = allegati;
	}   
}
