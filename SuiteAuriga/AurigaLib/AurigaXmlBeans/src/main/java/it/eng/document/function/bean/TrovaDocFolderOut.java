/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrovaDocFolderOut implements Serializable {

	private String  resultOut;
	private Integer nroTotRecOut;
	private Integer nroRecInPaginaOut;
	private String  percorsoRicercaXMLOut;
	private String  dettagliCercaInFolderOut;
    private Integer errorCode;
	private String  errorContext;
	private String  defaultMessage;
	private boolean inError;
	
	public String getResultOut() {
		return resultOut;
	}
	public void setResultOut(String resultOut) {
		this.resultOut = resultOut;
	}
	public Integer getNroTotRecOut() {
		return nroTotRecOut;
	}
	public void setNroTotRecOut(Integer nroTotRecOut) {
		this.nroTotRecOut = nroTotRecOut;
	}
	public Integer getNroRecInPaginaOut() {
		return nroRecInPaginaOut;
	}
	public void setNroRecInPaginaOut(Integer nroRecInPaginaOut) {
		this.nroRecInPaginaOut = nroRecInPaginaOut;
	}
	public String getPercorsoRicercaXMLOut() {
		return percorsoRicercaXMLOut;
	}
	public void setPercorsoRicercaXMLOut(String percorsoRicercaXMLOut) {
		this.percorsoRicercaXMLOut = percorsoRicercaXMLOut;
	}
	public String getDettagliCercaInFolderOut() {
		return dettagliCercaInFolderOut;
	}
	public void setDettagliCercaInFolderOut(String dettagliCercaInFolderOut) {
		this.dettagliCercaInFolderOut = dettagliCercaInFolderOut;
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
	public String getDefaultMessage() {
		return defaultMessage;
	}
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	public boolean isInError() {
		return inError;
	}
	public void setInError(boolean inError) {
		this.inError = inError;
	}
}
