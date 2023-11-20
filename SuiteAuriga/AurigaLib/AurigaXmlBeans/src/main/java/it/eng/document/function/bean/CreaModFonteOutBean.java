/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModFonteOutBean implements Serializable {

	private static final long serialVersionUID = -4295800492270691868L;

	private BigDecimal idFonte;
	private BigDecimal idUd;
	private BigDecimal idDoc;		
	private Map<String, String> fileInErrors;
	private BigDecimal numero;
	private Integer anno;
	private String sigla;
	private Date data;
	private Integer errorCode;
	private String errorContext;
	private String storeName;
	private String defaultMessage;		
	
	public BigDecimal getIdFonte() {
		return idFonte;
	}
	public void setIdFonte(BigDecimal idFonte) {
		this.idFonte = idFonte;
	}
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
	public Map<String, String> getFileInErrors() {
		return fileInErrors;
	}
	public void setFileInErrors(Map<String, String> fileInErrors) {
		this.fileInErrors = fileInErrors;
	}
	public BigDecimal getNumero() {
		return numero;
	}
	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorContext() {
		return errorContext;
	}
	public void setErrorContext(String errorContext) {
		this.errorContext = errorContext;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getDefaultMessage() {
		return defaultMessage;
	}
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
