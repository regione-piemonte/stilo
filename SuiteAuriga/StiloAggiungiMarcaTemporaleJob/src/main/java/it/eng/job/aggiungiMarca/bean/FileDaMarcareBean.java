/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

public class FileDaMarcareBean {

	private BigDecimal idUd;
	private BigDecimal idDoc;
	private Date tsFirma;
	private Integer numTry;
	private String tipoFile;
	private String nomeFile;
	private String uriFileFirmato;
	private String uriFileMarcato;
	private String tsMarca;
	
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	public BigDecimal getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}
	public String getUriFileFirmato() {
		return uriFileFirmato;
	}
	public void setUriFileFirmato(String uriFileFirmato) {
		this.uriFileFirmato = uriFileFirmato;
	}
	public String getUriFileMarcato() {
		return uriFileMarcato;
	}
	public void setUriFileMarcato(String uriFileMarcato) {
		this.uriFileMarcato = uriFileMarcato;
	}
	public String getTipoFile() {
		return tipoFile;
	}
	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
	}
	public String getTsMarca() {
		return tsMarca;
	}
	public void setTsMarca(String tsMarca) {
		this.tsMarca = tsMarca;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public Date getTsFirma() {
		return tsFirma;
	}
	public void setTsFirma(Date tsFirma) {
		this.tsFirma = tsFirma;
	}
	public Integer getNumTry() {
		return numTry;
	}
	public void setNumTry(Integer numTry) {
		this.numTry = numTry;
	}
	
	
	
}
