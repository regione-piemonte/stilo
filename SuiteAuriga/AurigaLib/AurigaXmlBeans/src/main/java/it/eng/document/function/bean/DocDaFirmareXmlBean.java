/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class DocDaFirmareXmlBean {

	private static final long serialVersionUID = 2218190035306283594L;

	@NumeroColonna(numero = "1")
	private BigDecimal idDoc;
	@NumeroColonna(numero = "2")
	private BigDecimal nroVer;
	@NumeroColonna(numero = "3")
	private String displayFilename;
	@NumeroColonna(numero = "4")
	private String estremiNroProvvisorio;
	@NumeroColonna(numero = "5")
	private String estremiNroAtto;
	@NumeroColonna(numero = "6")
	private String tipoDocumento;
	@NumeroColonna(numero = "7")
	private String uriFile;
	@NumeroColonna(numero = "8")
	private String mimetype;
	@NumeroColonna(numero = "9")
	private BigDecimal flgPdfizzabile;
	@NumeroColonna(numero = "10")
	private BigDecimal flgFirmato;
	@NumeroColonna(numero = "11")
	@TipoData(tipo = Tipo.DATA)
	private Date tsInvioFirma;
	@NumeroColonna(numero = "12")
	private String autoreInvioInFirma;
	@NumeroColonna(numero = "13")
	private BigDecimal idUd;
	@NumeroColonna(numero = "14")
	private BigDecimal idProcess;
	@NumeroColonna(numero = "15")
	private String nomeTaskAutocompletamento;
	@NumeroColonna(numero = "16")
	private String estremiNroProvvisorioXOrd;
	@NumeroColonna(numero = "17")
	private String estremiNroAttoXOrd;
	@NumeroColonna(numero = "18")
	private String activityName;
	@NumeroColonna(numero = "19")
	private String bustaCrittograficaFattaDaAuriga;
	@NumeroColonna(numero = "20")
	private String firmeNonValideBustaCrittografica;
	@NumeroColonna(numero = "21")
	private String tipoBustaCrittografica;	
	@NumeroColonna(numero = "22")
	private BigDecimal nroAllegato; //0 se è file primario
	@NumeroColonna(numero = "23")
	private BigDecimal flgAtto;

	public BigDecimal getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
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

	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public BigDecimal getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(BigDecimal idProcess) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
