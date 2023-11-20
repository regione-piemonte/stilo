/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.ScadenzaBean;

import java.util.Date;

public class ProcedimentiInIterFilterBean {

	private String idProcessType;
	private String svoltaFase;
	private String effettuatoTask;
	private String daIniziareFase;
	private String daCompletareFase;
	private String daEffettuareTask;
	private Integer numeroPratica;
	private Integer numeroPraticaStart;
	private Integer numeroPraticaEnd;
	private Integer annoPratica;
	private Integer annoPraticaStart;
	private Integer annoPraticaEnd;
	private String oggetto;
	private Date dataDiAvvioStart;
	private Date dataDiAvvioEnd;
	private String stato;
	private Boolean avviatiDaMe;
	private String inFase;
	private String eseguibileTask;
	private String denominazioneRichiedente;
	private String codFiscaleRichiedente;
	private String note;
	private ScadenzaBean scadenza;
	private Date dataPresentazioneStart;
	private Date dataPresentazioneEnd;
	private Integer annoAttoRifStart;
	private Integer annoAttoRifEnd;
	
	public String getIdProcessType() {
		return idProcessType;
	}
	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}
	public Integer getNumeroPratica() {
		return numeroPratica;
	}
	public void setNumeroPratica(Integer numeroPratica) {
		this.numeroPratica = numeroPratica;
	}
	public Integer getAnnoPratica() {
		return annoPratica;
	}
	public void setAnnoPratica(Integer annoPratica) {
		this.annoPratica = annoPratica;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public Date getDataDiAvvioStart() {
		return dataDiAvvioStart;
	}
	public void setDataDiAvvioStart(Date dataDiAvvioStart) {
		this.dataDiAvvioStart = dataDiAvvioStart;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Integer getNumeroPraticaStart() {
		return numeroPraticaStart;
	}
	public void setNumeroPraticaStart(Integer numeroPraticaStart) {
		this.numeroPraticaStart = numeroPraticaStart;
	}
	public Integer getNumeroPraticaEnd() {
		return numeroPraticaEnd;
	}
	public void setNumeroPraticaEnd(Integer numeroPraticaEnd) {
		this.numeroPraticaEnd = numeroPraticaEnd;
	}
	public Integer getAnnoPraticaStart() {
		return annoPraticaStart;
	}
	public void setAnnoPraticaStart(Integer annoPraticaStart) {
		this.annoPraticaStart = annoPraticaStart;
	}
	public Integer getAnnoPraticaEnd() {
		return annoPraticaEnd;
	}
	public void setAnnoPraticaEnd(Integer annoPraticaEnd) {
		this.annoPraticaEnd = annoPraticaEnd;
	}
	public Date getDataDiAvvioEnd() {
		return dataDiAvvioEnd;
	}
	public void setDataDiAvvioEnd(Date dataDiAvvioEnd) {
		this.dataDiAvvioEnd = dataDiAvvioEnd;
	}
	public String getSvoltaFase() {
		return svoltaFase;
	}
	public void setSvoltaFase(String svoltaFase) {
		this.svoltaFase = svoltaFase;
	}
	public String getEffettuatoTask() {
		return effettuatoTask;
	}
	public void setEffettuatoTask(String effettuatoTask) {
		this.effettuatoTask = effettuatoTask;
	}
	public String getDaIniziareFase() {
		return daIniziareFase;
	}
	public void setDaIniziareFase(String daIniziareFase) {
		this.daIniziareFase = daIniziareFase;
	}
	public String getDaCompletareFase() {
		return daCompletareFase;
	}
	public void setDaCompletareFase(String daCompletareFase) {
		this.daCompletareFase = daCompletareFase;
	}
	public String getDaEffettuareTask() {
		return daEffettuareTask;
	}
	public void setDaEffettuareTask(String daEffettuareTask) {
		this.daEffettuareTask = daEffettuareTask;
	}
	public Boolean getAvviatiDaMe() {
		return avviatiDaMe;
	}
	public void setAvviatiDaMe(Boolean avviatiDaMe) {
		this.avviatiDaMe = avviatiDaMe;
	}
	public String getInFase() {
		return inFase;
	}
	public void setInFase(String inFase) {
		this.inFase = inFase;
	}
	public String getEseguibileTask() {
		return eseguibileTask;
	}
	public void setEseguibileTask(String eseguibileTask) {
		this.eseguibileTask = eseguibileTask;
	}
	public String getDenominazioneRichiedente() {
		return denominazioneRichiedente;
	}
	public void setDenominazioneRichiedente(String denominazioneRichiedente) {
		this.denominazioneRichiedente = denominazioneRichiedente;
	}
	
	public ScadenzaBean getScadenza() {
		return scadenza;
	}
	public void setScadenza(ScadenzaBean scadenza) {
		this.scadenza = scadenza;
	}
	public Date getDataPresentazioneStart() {
		return dataPresentazioneStart;
	}
	public void setDataPresentazioneStart(Date dataPresentazioneStart) {
		this.dataPresentazioneStart = dataPresentazioneStart;
	}
	public Date getDataPresentazioneEnd() {
		return dataPresentazioneEnd;
	}
	public void setDataPresentazioneEnd(Date dataPresentazioneEnd) {
		this.dataPresentazioneEnd = dataPresentazioneEnd;
	}
	public Integer getAnnoAttoRifStart() {
		return annoAttoRifStart;
	}
	public void setAnnoAttoRifStart(Integer annoAttoRifStart) {
		this.annoAttoRifStart = annoAttoRifStart;
	}
	public Integer getAnnoAttoRifEnd() {
		return annoAttoRifEnd;
	}
	public void setAnnoAttoRifEnd(Integer annoAttoRifEnd) {
		this.annoAttoRifEnd = annoAttoRifEnd;
	}
	public String getCodFiscaleRichiedente() {
		return codFiscaleRichiedente;
	}
	public void setCodFiscaleRichiedente(String codFiscaleRichiedente) {
		this.codFiscaleRichiedente = codFiscaleRichiedente;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}