/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModDocumentoOutBean implements Serializable {

	private static final long serialVersionUID = -4295800492270691868L;

	private BigDecimal idUd;
	private BigDecimal idDoc;
	private String rowidDoc;
	private Map<String, String> fileInErrors;
	private BigDecimal numero;
	private Integer anno;
	private String sigla;
	private Date data;
	private Integer errorCode;
	private String errorContext;
	private String storeName;
	private String defaultMessage;
	private Boolean archiviazioneError = false;
	private Boolean invioConfermaAutomaticaError = false;	
	private Boolean salvataggioMetadatiError = false;		
	
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}
	public BigDecimal getIdDoc() {
		return idDoc;
	}
	public void setFileInErrors(Map<String, String> fileInErrors) {
		this.fileInErrors = fileInErrors;
	}
	public Map<String, String> getFileInErrors() {
		return fileInErrors;
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
	public void setArchiviazioneError(Boolean archiviazioneError) {
		this.archiviazioneError = archiviazioneError;
	}
	public Boolean getArchiviazioneError() {
		return archiviazioneError;
	}
	public Boolean getInvioConfermaAutomaticaError() {
		return invioConfermaAutomaticaError;
	}
	public void setInvioConfermaAutomaticaError(
			Boolean invioConfermaAutomaticaError) {
		this.invioConfermaAutomaticaError = invioConfermaAutomaticaError;
	}
	public Boolean getSalvataggioMetadatiError() {
		return salvataggioMetadatiError;
	}
	public void setSalvataggioMetadatiError(Boolean salvataggioMetadatiError) {
		this.salvataggioMetadatiError = salvataggioMetadatiError;
	}
	public String getRowidDoc() {
		return rowidDoc;
	}
	public void setRowidDoc(String rowidDoc) {
		this.rowidDoc = rowidDoc;
	}
	
}
