/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

/**
 * 
 * @author DANCRIST
 *
 */

public class ContenutoFoglioImportatoBean {

	private String codApplicazione;
	private String codIstanzaApplicazione;
	private String denominazioneApplicazione;
	private String tipologiaContenuto;
	private Date   tsUpload;
	private String idUtenteUpload;
	private String denominazioneUtenteUpload;
	private String uri;
	private String stato;
	private Date   tsInizioElab;
	private Date   tsFineElab;
	private String nrTotaleRighe;
	private String nrRigheElabSuccesso;
	private String nrRigheInErrore;
	private String idFoglio;
	private String nomeFile;
	private String erroreElabFoglio;
	private String nrRiga;
	private Date   tsInsRiga;
	private String esitoElabRiga;
	private Date   tsUltimaElabRiga;
	private String codErroreElabRiga;
	private String msgErroreElabRiga;
	private String valoriRiga;
	
	private List<ParametroRigaFoglioXmlBean> parametriRiga;

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
	
	public List<ParametroRigaFoglioXmlBean> getParametriRiga() {
		return parametriRiga;
	}

	public void setParametriRiga(List<ParametroRigaFoglioXmlBean> parametriRiga) {
		this.parametriRiga = parametriRiga;
	}
	
}