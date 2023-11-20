/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData.Tipo;
import it.eng.document.TipoData;
import java.util.Date;

public class MonitoraggioOperazioniBatchBean {
	
	@NumeroColonna(numero = "1")
	private String idRichiesta;

	@NumeroColonna(numero = "2")
	private String tipoOperazione;

	@NumeroColonna(numero = "3")
	private String tipoOggettiDaProcessare;

	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA)
	private Date dataRichiesta;

	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA)
	private Date dataSchedulazione;

	@NumeroColonna(numero = "6")
	private String motivoRichiesta;

	@NumeroColonna(numero = "7")
	private String utenteRichiestaSottomissione;

	@NumeroColonna(numero = "8")
	private String statoRichiesta;

	@NumeroColonna(numero = "9")
	private String livelloPrioritaRichiesta;

	@NumeroColonna(numero = "10")
	private String estremiOperazioneDerivanteRichiesta;

	@NumeroColonna(numero = "11")
	private String dettagliOperazioneRichiesta;

	@NumeroColonna(numero = "12")
	@TipoData(tipo = Tipo.DATA)
	private Date inizioPrimaElaborazione;

	@NumeroColonna(numero = "13")
	@TipoData(tipo = Tipo.DATA)
	private Date inizioUltimaElaborazione;

	@NumeroColonna(numero = "14")
	@TipoData(tipo = Tipo.DATA)
	private Date fineUltimaElaborazione;

	@NumeroColonna(numero = "15")
	@TipoData(tipo = Tipo.DATA)
	private Date dataCompletataElaborazione;

	@NumeroColonna(numero = "16")
	private Integer numeroElaborazioni;

	@NumeroColonna(numero = "17")
	private String note;

	@NumeroColonna(numero = "18")
	private String codTipoNuovoAssegnatario;

	@NumeroColonna(numero = "19")
	private String tipoEventoScatenante;

	@NumeroColonna(numero = "20")
	private String eventoScatenanteSuTipoOggetto;

	@NumeroColonna(numero = "21")
	private String estremiOggettoSuOperazione;

	@NumeroColonna(numero = "22")
	private String flgDettagli;

	@NumeroColonna(numero = "23")
	private String numeroOggettiDaElaborare;

	@NumeroColonna(numero = "24")
	private String numeroOggettiElaboratiConSuccesso;

	@NumeroColonna(numero = "25")
	private String numeroOggettiElaboratiConErrore;

	@NumeroColonna(numero = "26")
	private String rowIdRecord;

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public String getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public String getTipoOggettiDaProcessare() {
		return tipoOggettiDaProcessare;
	}

	public void setTipoOggettiDaProcessare(String tipoOggettiDaProcessare) {
		this.tipoOggettiDaProcessare = tipoOggettiDaProcessare;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public Date getDataSchedulazione() {
		return dataSchedulazione;
	}

	public void setDataSchedulazione(Date dataSchedulazione) {
		this.dataSchedulazione = dataSchedulazione;
	}

	public String getMotivoRichiesta() {
		return motivoRichiesta;
	}

	public void setMotivoRichiesta(String motivoRichiesta) {
		this.motivoRichiesta = motivoRichiesta;
	}

	public String getUtenteRichiestaSottomissione() {
		return utenteRichiestaSottomissione;
	}

	public void setUtenteRichiestaSottomissione(String utenteRichiestaSottomissione) {
		this.utenteRichiestaSottomissione = utenteRichiestaSottomissione;
	}

	public String getStatoRichiesta() {
		return statoRichiesta;
	}

	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}

	public String getLivelloPrioritaRichiesta() {
		return livelloPrioritaRichiesta;
	}

	public void setLivelloPrioritaRichiesta(String livelloPrioritaRichiesta) {
		this.livelloPrioritaRichiesta = livelloPrioritaRichiesta;
	}

	public String getEstremiOperazioneDerivanteRichiesta() {
		return estremiOperazioneDerivanteRichiesta;
	}

	public void setEstremiOperazioneDerivanteRichiesta(String estremiOperazioneDerivanteRichiesta) {
		this.estremiOperazioneDerivanteRichiesta = estremiOperazioneDerivanteRichiesta;
	}

	public String getDettagliOperazioneRichiesta() {
		return dettagliOperazioneRichiesta;
	}

	public void setDettagliOperazioneRichiesta(String dettagliOperazioneRichiesta) {
		this.dettagliOperazioneRichiesta = dettagliOperazioneRichiesta;
	}

	public Date getInizioPrimaElaborazione() {
		return inizioPrimaElaborazione;
	}

	public void setInizioPrimaElaborazione(Date inizioPrimaElaborazione) {
		this.inizioPrimaElaborazione = inizioPrimaElaborazione;
	}

	public Date getInizioUltimaElaborazione() {
		return inizioUltimaElaborazione;
	}

	public void setInizioUltimaElaborazione(Date inizioUltimaElaborazione) {
		this.inizioUltimaElaborazione = inizioUltimaElaborazione;
	}

	public Date getFineUltimaElaborazione() {
		return fineUltimaElaborazione;
	}

	public void setFineUltimaElaborazione(Date fineUltimaElaborazione) {
		this.fineUltimaElaborazione = fineUltimaElaborazione;
	}

	public Date getDataCompletataElaborazione() {
		return dataCompletataElaborazione;
	}

	public void setDataCompletataElaborazione(Date dataCompletataElaborazione) {
		this.dataCompletataElaborazione = dataCompletataElaborazione;
	}

	public Integer getNumeroElaborazioni() {
		return numeroElaborazioni;
	}

	public void setNumeroElaborazioni(Integer numeroElaborazioni) {
		this.numeroElaborazioni = numeroElaborazioni;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTipoEventoScatenante() {
		return tipoEventoScatenante;
	}

	public void setTipoEventoScatenante(String tipoEventoScatenante) {
		this.tipoEventoScatenante = tipoEventoScatenante;
	}

	public String getEventoScatenanteSuTipoOggetto() {
		return eventoScatenanteSuTipoOggetto;
	}

	public void setEventoScatenanteSuTipoOggetto(String eventoScatenanteSuTipoOggetto) {
		this.eventoScatenanteSuTipoOggetto = eventoScatenanteSuTipoOggetto;
	}

	public String getEstremiOggettoSuOperazione() {
		return estremiOggettoSuOperazione;
	}

	public void setEstremiOggettoSuOperazione(String estremiOggettoSuOperazione) {
		this.estremiOggettoSuOperazione = estremiOggettoSuOperazione;
	}

	public String getFlgDettagli() {
		return flgDettagli;
	}

	public void setFlgDettagli(String flgDettagli) {
		this.flgDettagli = flgDettagli;
	}

	public String getNumeroOggettiDaElaborare() {
		return numeroOggettiDaElaborare;
	}

	public void setNumeroOggettiDaElaborare(String numeroOggettiDaElaborare) {
		this.numeroOggettiDaElaborare = numeroOggettiDaElaborare;
	}

	public String getNumeroOggettiElaboratiConSuccesso() {
		return numeroOggettiElaboratiConSuccesso;
	}

	public void setNumeroOggettiElaboratiConSuccesso(String numeroOggettiElaboratiConSuccesso) {
		this.numeroOggettiElaboratiConSuccesso = numeroOggettiElaboratiConSuccesso;
	}

	public String getNumeroOggettiElaboratiConErrore() {
		return numeroOggettiElaboratiConErrore;
	}

	public void setNumeroOggettiElaboratiConErrore(String numeroOggettiElaboratiConErrore) {
		this.numeroOggettiElaboratiConErrore = numeroOggettiElaboratiConErrore;
	}

	public String getCodTipoNuovoAssegnatario() {
		return codTipoNuovoAssegnatario;
	}

	public void setCodTipoNuovoAssegnatario(String codTipoNuovoAssegnatario) {
		this.codTipoNuovoAssegnatario = codTipoNuovoAssegnatario;
	}

	public String getRowIdRecord() {
		return rowIdRecord;
	}

	public void setRowIdRecord(String rowIdRecord) {
		this.rowIdRecord = rowIdRecord;
	}
}