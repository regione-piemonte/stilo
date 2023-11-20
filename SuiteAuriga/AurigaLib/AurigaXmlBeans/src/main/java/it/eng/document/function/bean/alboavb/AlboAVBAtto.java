/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBAtto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idAtto;

    private long idEnte;

    private int anno;

    private int numeroRegistro;

    private java.lang.String numeroRegistroGenerale;

    private java.lang.String numeroRegistroDiArea;

    private java.lang.String nomeArea;

    private long idTipoAtto;

    private java.lang.String oggetto;

    private java.lang.String descrizione;

    private java.lang.String note;

    private java.lang.String enteRichiestaPubblicazione;

    private java.lang.String dataPubblicazione;

    private java.lang.String dataScadenza;

    private java.lang.String dataAdozione;

    private int durataPubblicazioneAtto;

    private int offsetDurataPubblicazione;

    private long idDocumentFolder;

    private long idMainDocument;

    private java.lang.String rppNome;

    private java.lang.String rppCognome;

    private java.lang.String rppEmail;

    private java.lang.String rppTelefono;

    private java.lang.String rppIndirizzo;

	public AlboAVBAtto() {
		
	}

	public AlboAVBAtto(long idAtto, long idEnte, int anno, int numeroRegistro, String numeroRegistroGenerale,
			String numeroRegistroDiArea, String nomeArea, long idTipoAtto, String oggetto, String descrizione,
			String note, String enteRichiestaPubblicazione, String dataPubblicazione, String dataScadenza,
			String dataAdozione, int durataPubblicazioneAtto, int offsetDurataPubblicazione, long idDocumentFolder,
			long idMainDocument, String rppNome, String rppCognome, String rppEmail, String rppTelefono,
			String rppIndirizzo) {
		this.idAtto = idAtto;
		this.idEnte = idEnte;
		this.anno = anno;
		this.numeroRegistro = numeroRegistro;
		this.numeroRegistroGenerale = numeroRegistroGenerale;
		this.numeroRegistroDiArea = numeroRegistroDiArea;
		this.nomeArea = nomeArea;
		this.idTipoAtto = idTipoAtto;
		this.oggetto = oggetto;
		this.descrizione = descrizione;
		this.note = note;
		this.enteRichiestaPubblicazione = enteRichiestaPubblicazione;
		this.dataPubblicazione = dataPubblicazione;
		this.dataScadenza = dataScadenza;
		this.dataAdozione = dataAdozione;
		this.durataPubblicazioneAtto = durataPubblicazioneAtto;
		this.offsetDurataPubblicazione = offsetDurataPubblicazione;
		this.idDocumentFolder = idDocumentFolder;
		this.idMainDocument = idMainDocument;
		this.rppNome = rppNome;
		this.rppCognome = rppCognome;
		this.rppEmail = rppEmail;
		this.rppTelefono = rppTelefono;
		this.rppIndirizzo = rppIndirizzo;
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

	public long getIdTipoAtto() {
		return idTipoAtto;
	}

	public void setIdTipoAtto(long idTipoAtto) {
		this.idTipoAtto = idTipoAtto;
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

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
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

	public int getDurataPubblicazioneAtto() {
		return durataPubblicazioneAtto;
	}

	public void setDurataPubblicazioneAtto(int durataPubblicazioneAtto) {
		this.durataPubblicazioneAtto = durataPubblicazioneAtto;
	}

	public int getOffsetDurataPubblicazione() {
		return offsetDurataPubblicazione;
	}

	public void setOffsetDurataPubblicazione(int offsetDurataPubblicazione) {
		this.offsetDurataPubblicazione = offsetDurataPubblicazione;
	}

	public long getIdDocumentFolder() {
		return idDocumentFolder;
	}

	public void setIdDocumentFolder(long idDocumentFolder) {
		this.idDocumentFolder = idDocumentFolder;
	}

	public long getIdMainDocument() {
		return idMainDocument;
	}

	public void setIdMainDocument(long idMainDocument) {
		this.idMainDocument = idMainDocument;
	}

	public java.lang.String getRppNome() {
		return rppNome;
	}

	public void setRppNome(java.lang.String rppNome) {
		this.rppNome = rppNome;
	}

	public java.lang.String getRppCognome() {
		return rppCognome;
	}

	public void setRppCognome(java.lang.String rppCognome) {
		this.rppCognome = rppCognome;
	}

	public java.lang.String getRppEmail() {
		return rppEmail;
	}

	public void setRppEmail(java.lang.String rppEmail) {
		this.rppEmail = rppEmail;
	}

	public java.lang.String getRppTelefono() {
		return rppTelefono;
	}

	public void setRppTelefono(java.lang.String rppTelefono) {
		this.rppTelefono = rppTelefono;
	}

	public java.lang.String getRppIndirizzo() {
		return rppIndirizzo;
	}

	public void setRppIndirizzo(java.lang.String rppIndirizzo) {
		this.rppIndirizzo = rppIndirizzo;
	}	
}
