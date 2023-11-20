/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class ContenutoFoglioImportatoXmlBean {
	
	/**
	 * Cod. applicazione dell'applicazione/società che ha caricato il foglio
	 */
	@NumeroColonna(numero = "1")
	private String codApplicazione;

	/**
	 * Cod. istanza applicazione dell'applicazione/società che ha caricato il foglio
	 */
	@NumeroColonna(numero = "2")
	private String codIstanzaApplicazione;
	
	/**
	 * Denominazione dell'applicazione/società che ha caricato il foglio
	 */
	@NumeroColonna(numero = "3")
	private String denominazioneApplicazione;
	
	/**
	 * Tipo di contenuto del foglio
	 */
	@NumeroColonna(numero = "4")
	private String tipologiaContenuto;
	
	/**
	 * Data e ora di upload del foglio (nel formato dato dal parametro FMT_STD_TIMESTAMP)
	 */
	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA)
	private Date tsUpload;
	
	/**
	 * Id. utente che ha effettuato l'upload del foglio
	 */
	@NumeroColonna(numero = "6")
	private String idUtenteUpload;
	
	/**
	 * Cognome e nome dell'utente che ha effettuato l'upload del foglio
	 */
	@NumeroColonna(numero = "7")
	private String denominazioneUtenteUpload;
	
	/**
	 * URI del foglio
	 */
	@NumeroColonna(numero = "8")
	private String uri;
	
	/**
	 * Stato del foglio: da elaborare, in elaborazione, elaborato senza errori, elaborato con errori
	 */
	@NumeroColonna(numero = "9")
	private String stato;
	
	/**
	 * Data e ora di inizio elaborazione del foglio (nel formato dato dal parametro FMT_STD_TIMESTAMP)
	 */
	@NumeroColonna(numero = "10")
	@TipoData(tipo = Tipo.DATA)
	private Date tsInizioElab;
	
	/**
	 * Data e ora di fine elaborazione del foglio (nel formato dato dal parametro FMT_STD_TIMESTAMP)
	 */
	@NumeroColonna(numero = "11")
	@TipoData(tipo = Tipo.DATA)
	private Date tsFineElab;
	
	/**
	 * Nro complessivo di righe del foglio
	 */
	@NumeroColonna(numero = "12")
	private String nrTotaleRighe;
	
	/**
	 * Nro di righe del foglio elaborate con successo
	 */
	@NumeroColonna(numero = "13")
	private String nrRigheElabSuccesso;
	
	/**
	 * Nro di righe del foglio in errore
	 */
	@NumeroColonna(numero = "14")
	private String nrRigheInErrore;
	
	/**
	 * Id. del foglio
	 */
	@NumeroColonna(numero = "15")
	private String idFoglio;
	
	/**
	 * Nome del file del foglio 
	 */
	@NumeroColonna(numero = "16")
	private String nomeFile;
	
	/**
	 * Errore di elaborazione dell'intero foglio(se errore complessivo che impedice di elaborare tutte le righe)
	 */
	@NumeroColonna(numero = "17")
	private String erroreElabFoglio;
	
	/**
	 * Nro riga (nel foglio)
	 */
	@NumeroColonna(numero = "18")
	private String nrRiga;
	
	/**
	 * Data e ora di inserimento della riga (nel formato del paramaetro FMT_STD_TIMESTAMP)
	 */
	@NumeroColonna(numero = "19")
	@TipoData(tipo = Tipo.DATA)
	private Date tsInsRiga;
	
	/**
	 * Esito elaborazione riga: OK o KO
	 */
	@NumeroColonna(numero = "20")
	private String esitoElabRiga;
	
	/**
	 * Data e ora di ultima elaborazione riga(nel formato del paramaetro FMT_STD_TIMESTAMP)
	 */
	@NumeroColonna(numero = "21")
	@TipoData(tipo = Tipo.DATA)
	private Date tsUltimaElabRiga;
	
	/**
	 * Cod. errore elaborazione riga
	 */
	@NumeroColonna(numero = "22")
	private String codErroreElabRiga;
	
	/**
	 * Messaggio errore riga
	 */
	@NumeroColonna(numero = "23")
	private String msgErroreElabRiga;
	
	/**
	 * I dati della riga separati da separatore prefissato
	 */
	@NumeroColonna(numero = "24")
	private String valoriRiga;

	public String getCodApplicazione() {
		return codApplicazione;
	}

	public String getCodIstanzaApplicazione() {
		return codIstanzaApplicazione;
	}

	public String getDenominazioneApplicazione() {
		return denominazioneApplicazione;
	}

	public String getTipologiaContenuto() {
		return tipologiaContenuto;
	}

	public Date getTsUpload() {
		return tsUpload;
	}

	public String getIdUtenteUpload() {
		return idUtenteUpload;
	}

	public String getDenominazioneUtenteUpload() {
		return denominazioneUtenteUpload;
	}

	public String getUri() {
		return uri;
	}

	public String getStato() {
		return stato;
	}

	public Date getTsInizioElab() {
		return tsInizioElab;
	}

	public Date getTsFineElab() {
		return tsFineElab;
	}

	public String getNrTotaleRighe() {
		return nrTotaleRighe;
	}

	public String getNrRigheElabSuccesso() {
		return nrRigheElabSuccesso;
	}

	public String getNrRigheInErrore() {
		return nrRigheInErrore;
	}

	public String getIdFoglio() {
		return idFoglio;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public String getErroreElabFoglio() {
		return erroreElabFoglio;
	}

	public String getNrRiga() {
		return nrRiga;
	}

	public Date getTsInsRiga() {
		return tsInsRiga;
	}

	public String getEsitoElabRiga() {
		return esitoElabRiga;
	}

	public Date getTsUltimaElabRiga() {
		return tsUltimaElabRiga;
	}

	public String getCodErroreElabRiga() {
		return codErroreElabRiga;
	}

	public String getMsgErroreElabRiga() {
		return msgErroreElabRiga;
	}

	public String getValoriRiga() {
		return valoriRiga;
	}

	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}

	public void setCodIstanzaApplicazione(String codIstanzaApplicazione) {
		this.codIstanzaApplicazione = codIstanzaApplicazione;
	}

	public void setDenominazioneApplicazione(String denominazioneApplicazione) {
		this.denominazioneApplicazione = denominazioneApplicazione;
	}

	public void setTipologiaContenuto(String tipologiaContenuto) {
		this.tipologiaContenuto = tipologiaContenuto;
	}

	public void setTsUpload(Date tsUpload) {
		this.tsUpload = tsUpload;
	}

	public void setIdUtenteUpload(String idUtenteUpload) {
		this.idUtenteUpload = idUtenteUpload;
	}

	public void setDenominazioneUtenteUpload(String denominazioneUtenteUpload) {
		this.denominazioneUtenteUpload = denominazioneUtenteUpload;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public void setTsInizioElab(Date tsInizioElab) {
		this.tsInizioElab = tsInizioElab;
	}

	public void setTsFineElab(Date tsFineElab) {
		this.tsFineElab = tsFineElab;
	}

	public void setNrTotaleRighe(String nrTotaleRighe) {
		this.nrTotaleRighe = nrTotaleRighe;
	}

	public void setNrRigheElabSuccesso(String nrRigheElabSuccesso) {
		this.nrRigheElabSuccesso = nrRigheElabSuccesso;
	}

	public void setNrRigheInErrore(String nrRigheInErrore) {
		this.nrRigheInErrore = nrRigheInErrore;
	}

	public void setIdFoglio(String idFoglio) {
		this.idFoglio = idFoglio;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public void setErroreElabFoglio(String erroreElabFoglio) {
		this.erroreElabFoglio = erroreElabFoglio;
	}

	public void setNrRiga(String nrRiga) {
		this.nrRiga = nrRiga;
	}

	public void setTsInsRiga(Date tsInsRiga) {
		this.tsInsRiga = tsInsRiga;
	}

	public void setEsitoElabRiga(String esitoElabRiga) {
		this.esitoElabRiga = esitoElabRiga;
	}

	public void setTsUltimaElabRiga(Date tsUltimaElabRiga) {
		this.tsUltimaElabRiga = tsUltimaElabRiga;
	}

	public void setCodErroreElabRiga(String codErroreElabRiga) {
		this.codErroreElabRiga = codErroreElabRiga;
	}

	public void setMsgErroreElabRiga(String msgErroreElabRiga) {
		this.msgErroreElabRiga = msgErroreElabRiga;
	}

	public void setValoriRiga(String valoriRiga) {
		this.valoriRiga = valoriRiga;
	}
	
}