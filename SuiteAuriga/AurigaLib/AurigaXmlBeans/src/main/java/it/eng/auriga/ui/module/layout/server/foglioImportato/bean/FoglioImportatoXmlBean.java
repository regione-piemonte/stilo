/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author FABCAST
 *
 */

public class FoglioImportatoXmlBean {
	
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
	 * Errore di elaborazione dell'intero foglio(se errore complessivo che impedice di elaborare tutte le righe)
	 */
	@NumeroColonna(numero = "16")
	private String erroreElabFoglio;
	
	/**
	 * URI foglio excel rielaborato
	 */
	@NumeroColonna(numero = "17")
	private String uriExcelRielaborato;
	
	/**
	 * URI file prodotto come output dell'elaborazione dell'xls
	 */
	@NumeroColonna(numero = "18")
	private String uriFileProdotto;
	
	/**
	 * Nome del file del foglio
	 */
	@NumeroColonna(numero = "19")
	private String nomeFile;
	
	public String getCodApplicazione() {
		return codApplicazione;
	}
	
	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}

	public String getCodIstanzaApplicazione() {
		return codIstanzaApplicazione;
	}
	
	public void setCodIstanzaApplicazione(String codIstanzaApplicazione) {
		this.codIstanzaApplicazione = codIstanzaApplicazione;
	}

	public String getDenominazioneApplicazione() {
		return denominazioneApplicazione;
	}
	
	public void setDenominazioneApplicazione(String denominazioneApplicazione) {
		this.denominazioneApplicazione = denominazioneApplicazione;
	}
	
	public String getTipologiaContenuto() {
		return tipologiaContenuto;
	}
	
	public void setTipologiaContenuto(String tipologiaContenuto) {
		this.tipologiaContenuto = tipologiaContenuto;
	}
	
	public Date getTsUpload() {
		return tsUpload;
	}

	public void setTsUpload(Date tsUpload) {
		this.tsUpload = tsUpload;
	}

	public String getIdUtenteUpload() {
		return idUtenteUpload;
	}

	public void setIdUtenteUpload(String idUtenteUpload) {
		this.idUtenteUpload = idUtenteUpload;
	}

	public String getDenominazioneUtenteUpload() {
		return denominazioneUtenteUpload;
	}
	
	public void setDenominazioneUtenteUpload(String denominazioneUtenteUpload) {
		this.denominazioneUtenteUpload = denominazioneUtenteUpload;
	}

	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getTsInizioElab() {
		return tsInizioElab;
	}

	public void setTsInizioElab(Date tsInizioElab) {
		this.tsInizioElab = tsInizioElab;
	}
	
	public Date getTsFineElab() {
		return tsFineElab;
	}
	
	public void setTsFineElab(Date tsFineElab) {
		this.tsFineElab = tsFineElab;
	}
	
	public String getNrTotaleRighe() {
		return nrTotaleRighe;
	}

	public void setNrTotaleRighe(String nrTotaleRighe) {
		this.nrTotaleRighe = nrTotaleRighe;
	}
	
	public String getNrRigheElabSuccesso() {
		return nrRigheElabSuccesso;
	}
	
	public void setNrRigheElabSuccesso(String nrRigheElabSuccesso) {
		this.nrRigheElabSuccesso = nrRigheElabSuccesso;
	}

	public String getNrRigheInErrore() {
		return nrRigheInErrore;
	}
	
	public void setNrRigheInErrore(String nrRigheInErrore) {
		this.nrRigheInErrore = nrRigheInErrore;
	}
	
	public String getIdFoglio() {
		return idFoglio;
	}

	public void setIdFoglio(String idFoglio) {
		this.idFoglio = idFoglio;
	}
	
	public String getErroreElabFoglio() {
		return erroreElabFoglio;
	}

	public void setErroreElabFoglio(String erroreElabFoglio) {
		this.erroreElabFoglio = erroreElabFoglio;
	}
	
	public String getUriExcelRielaborato() {
		return uriExcelRielaborato;
	}

	public void setUriExcelRielaborato(String uriExcelRielaborato) {
		this.uriExcelRielaborato = uriExcelRielaborato;
	}

	public String getUriFileProdotto() {
		return uriFileProdotto;
	}
	
	public void setUriFileProdotto(String uriFileProdotto) {
		this.uriFileProdotto = uriFileProdotto;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
}