/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.document.NumeroColonna;

public class JobStampaRegistriJobConfBean {

	@NumeroColonna(numero="1")
	private BigDecimal idJob;
	
	@NumeroColonna(numero="2")
	private String header;
	
	@NumeroColonna(numero="3")
	private String footer;
	
	@NumeroColonna(numero="4")
	private Integer nroPagine;
	
	@NumeroColonna(numero="5")
	private String schemaDB;
	
	@NumeroColonna(numero="6")
	private Integer tipoDominio;
	
	@NumeroColonna(numero="7")
	private BigDecimal idDominio;
	
	@NumeroColonna(numero="8")
	private String username;
	
	@NumeroColonna(numero="9")
	private BigDecimal idDoc;
	
	@NumeroColonna(numero="10")
	private String nomeFileExport;
	
	@NumeroColonna(numero="11")
	private String tipoReport;
	
	@NumeroColonna(numero="12")
	private boolean flgAutomatico;
	
	@NumeroColonna(numero="13")
	private String nomeFileInvioConserv; 
	
	@NumeroColonna(numero="14")
	private boolean flgRegistroGenerato;
	
	@NumeroColonna(numero="15")
	private String uriRegistroGenerato;
	
	@NumeroColonna(numero="16")
	private String headerVar;
	
	@NumeroColonna(numero="17")
	private String footerVar;	
	
	@NumeroColonna(numero="18")	
	private Integer nroPagineVar;
	
	@NumeroColonna(numero="19")	
	private BigDecimal idParamXmlDatiInvioConserv;

	@NumeroColonna(numero="20")	
	private String nomeXmlDatiInvioConserv;

	public BigDecimal getIdJob() {
		return idJob;
	}

	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public Integer getNroPagine() {
		return nroPagine;
	}

	public void setNroPagine(Integer nroPagine) {
		this.nroPagine = nroPagine;
	}

	public String getSchemaDB() {
		return schemaDB;
	}

	public void setSchemaDB(String schemaDB) {
		this.schemaDB = schemaDB;
	}

	public Integer getTipoDominio() {
		return tipoDominio;
	}

	public void setTipoDominio(Integer tipoDominio) {
		this.tipoDominio = tipoDominio;
	}

	public BigDecimal getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(BigDecimal idDominio) {
		this.idDominio = idDominio;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public String getNomeFileExport() {
		return nomeFileExport;
	}

	public void setNomeFileExport(String nomeFileExport) {
		this.nomeFileExport = nomeFileExport;
	}

	public String getTipoReport() {
		return tipoReport;
	}

	public void setTipoReport(String tipoReport) {
		this.tipoReport = tipoReport;
	}

	public boolean isFlgAutomatico() {
		return flgAutomatico;
	}

	public void setFlgAutomatico(boolean flgAutomatico) {
		this.flgAutomatico = flgAutomatico;
	}

	public String getNomeFileInvioConserv() {
		return nomeFileInvioConserv;
	}

	public void setNomeFileInvioConserv(String nomeFileInvioConserv) {
		this.nomeFileInvioConserv = nomeFileInvioConserv;
	}

	public boolean isFlgRegistroGenerato() {
		return flgRegistroGenerato;
	}

	public void setFlgRegistroGenerato(boolean flgRegistroGenerato) {
		this.flgRegistroGenerato = flgRegistroGenerato;
	}

	public String getUriRegistroGenerato() {
		return uriRegistroGenerato;
	}

	public void setUriRegistroGenerato(String uriRegistroGenerato) {
		this.uriRegistroGenerato = uriRegistroGenerato;
	}

	public String getHeaderVar() {
		return headerVar;
	}

	public void setHeaderVar(String headerVar) {
		this.headerVar = headerVar;
	}

	public String getFooterVar() {
		return footerVar;
	}

	public void setFooterVar(String footerVar) {
		this.footerVar = footerVar;
	}

	public Integer getNroPagineVar() {
		return nroPagineVar;
	}

	public void setNroPagineVar(Integer nroPagineVar) {
		this.nroPagineVar = nroPagineVar;
	}

	public BigDecimal getIdParamXmlDatiInvioConserv() {
		return idParamXmlDatiInvioConserv;
	}

	public void setIdParamXmlDatiInvioConserv(BigDecimal idParamXmlDatiInvioConserv) {
		this.idParamXmlDatiInvioConserv = idParamXmlDatiInvioConserv;
	}

	public String getNomeXmlDatiInvioConserv() {
		return nomeXmlDatiInvioConserv;
	}

	public void setNomeXmlDatiInvioConserv(String nomeXmlDatiInvioConserv) {
		this.nomeXmlDatiInvioConserv = nomeXmlDatiInvioConserv;
	}
	
}
