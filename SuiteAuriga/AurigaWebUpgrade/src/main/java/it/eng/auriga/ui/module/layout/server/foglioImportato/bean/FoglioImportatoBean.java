/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author FABCAST
 *
 */

public class FoglioImportatoBean {
	
	private String codApplicazione;
	private String codIstanzaApplicazione;
	private String denominazioneApplicazione;
	private String tipologiaContenuto;
	private Date tsUpload;
	private String idUtenteUpload;
	private String denominazioneUtenteUpload;
	private String uri;
	private String stato;
	private Date tsInizioElab;
	private Date tsFineElab;
	private String nrTotaleRighe;
	private String nrRigheElabSuccesso;
	private String nrRigheInErrore;
	private String idFoglio;
	private String erroreElabFoglio;
	private String uriExcelRielaborato;
	private String uriFileProdotto;
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
