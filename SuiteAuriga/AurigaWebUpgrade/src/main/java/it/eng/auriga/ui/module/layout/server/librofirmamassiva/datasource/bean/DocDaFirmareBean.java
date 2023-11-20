/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

public class DocDaFirmareBean {

	private String idDoc;
	private BigDecimal nroVer;
	private String displayFilename;
	private String estremiNroProvvisorio;
	private String estremiNroAtto;
	private String tipoDocumento;
	private String uriFile;
	private String mimetype;
	private BigDecimal flgPdfizzabile;
	private BigDecimal flgFirmato;
	private Date tsInvioFirma;
	private String autoreInvioInFirma;
	private String idUd;
	private String idProcess;
	private String nomeTaskAutocompletamento;
	private String estremiNroProvvisorioXOrd;
	private String estremiNroAttoXOrd;
	private String activityName;
	private String bustaCrittograficaFattaDaAuriga;
	private String firmeNonValideBustaCrittografica;
	private String tipoBustaCrittografica;
	private BigDecimal nroAllegato; //0 se è file primario
	private BigDecimal flgAtto;

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public BigDecimal getNroVer() {
		return nroVer;
	}

	public void setNroVer(BigDecimal nroVer) {
		this.nroVer = nroVer;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getEstremiNroProvvisorio() {
		return estremiNroProvvisorio;
	}

	public void setEstremiNroProvvisorio(String estremiNroProvvisorio) {
		this.estremiNroProvvisorio = estremiNroProvvisorio;
	}

	public String getEstremiNroAtto() {
		return estremiNroAtto;
	}

	public void setEstremiNroAtto(String estremiNroAtto) {
		this.estremiNroAtto = estremiNroAtto;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public BigDecimal getFlgPdfizzabile() {
		return flgPdfizzabile;
	}

	public void setFlgPdfizzabile(BigDecimal flgPdfizzabile) {
		this.flgPdfizzabile = flgPdfizzabile;
	}

	public BigDecimal getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(BigDecimal flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public Date getTsInvioFirma() {
		return tsInvioFirma;
	}

	public void setTsInvioFirma(Date tsInvioFirma) {
		this.tsInvioFirma = tsInvioFirma;
	}

	public String getAutoreInvioInFirma() {
		return autoreInvioInFirma;
	}

	public void setAutoreInvioInFirma(String autoreInvioInFirma) {
		this.autoreInvioInFirma = autoreInvioInFirma;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

	public String getNomeTaskAutocompletamento() {
		return nomeTaskAutocompletamento;
	}

	public void setNomeTaskAutocompletamento(String nomeTaskAutocompletamento) {
		this.nomeTaskAutocompletamento = nomeTaskAutocompletamento;
	}

	public String getEstremiNroProvvisorioXOrd() {
		return estremiNroProvvisorioXOrd;
	}

	public void setEstremiNroProvvisorioXOrd(String estremiNroProvvisorioXOrd) {
		this.estremiNroProvvisorioXOrd = estremiNroProvvisorioXOrd;
	}

	public String getEstremiNroAttoXOrd() {
		return estremiNroAttoXOrd;
	}

	public void setEstremiNroAttoXOrd(String estremiNroAttoXOrd) {
		this.estremiNroAttoXOrd = estremiNroAttoXOrd;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getBustaCrittograficaFattaDaAuriga() {
		return bustaCrittograficaFattaDaAuriga;
	}

	public void setBustaCrittograficaFattaDaAuriga(String bustaCrittograficaFattaDaAuriga) {
		this.bustaCrittograficaFattaDaAuriga = bustaCrittograficaFattaDaAuriga;
	}

	public String getFirmeNonValideBustaCrittografica() {
		return firmeNonValideBustaCrittografica;
	}

	public void setFirmeNonValideBustaCrittografica(String firmeNonValideBustaCrittografica) {
		this.firmeNonValideBustaCrittografica = firmeNonValideBustaCrittografica;
	}

	public String getTipoBustaCrittografica() {
		return tipoBustaCrittografica;
	}

	public void setTipoBustaCrittografica(String tipoBustaCrittografica) {
		this.tipoBustaCrittografica = tipoBustaCrittografica;
	}

	public BigDecimal getNroAllegato() {
		return nroAllegato;
	}

	public void setNroAllegato(BigDecimal nroAllegato) {
		this.nroAllegato = nroAllegato;
	}
	
	public BigDecimal getFlgAtto() {
		return flgAtto;
	}

	public void setFlgAtto(BigDecimal flgAtto) {
		this.flgAtto = flgAtto;
	}

}
