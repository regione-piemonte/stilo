/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.FillPdfService.TIPI_PDF;

public class FillPdfBean {

	private String idProcess;
	private String uriFile;
	private String nomeFile;
	private String uriFilePdf;
	private String nomeFilePdf;	
	private String mimetype;
	private Boolean firmato = false;
	private TIPI_PDF tipoPdf;
	
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public TIPI_PDF getTipoPdf() {
		return tipoPdf;
	}
	public void setTipoPdf(TIPI_PDF tipoPdf) {
		this.tipoPdf = tipoPdf;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getUriFilePdf() {
		return uriFilePdf;
	}
	public void setUriFilePdf(String uriFilePdf) {
		this.uriFilePdf = uriFilePdf;
	}
	public String getNomeFilePdf() {
		return nomeFilePdf;
	}
	public void setNomeFilePdf(String nomeFilePdf) {
		this.nomeFilePdf = nomeFilePdf;
	}
	public Boolean getFirmato() {
		return firmato;
	}
	public void setFirmato(Boolean firmato) {
		this.firmato = firmato;
	}
	
}
