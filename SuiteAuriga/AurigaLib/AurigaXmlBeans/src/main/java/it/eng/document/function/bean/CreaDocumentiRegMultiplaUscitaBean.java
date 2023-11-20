/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaDocumentiRegMultiplaUscitaBean implements Serializable {

	private static final long serialVersionUID = 9049337158961861179L;
	
	private List<CreaDocWithFileBean> listaDocRegMultiplaUscita;
	
	private String idJob;
	private Integer errorCode;
	private String errorContext;
	private String storeName;
	private String defaultMessage;
	
	public List<CreaDocWithFileBean> getListaDocRegMultiplaUscita() {
		return listaDocRegMultiplaUscita;
	}
	public void setListaDocRegMultiplaUscita(List<CreaDocWithFileBean> listaDocRegMultiplaUscita) {
		this.listaDocRegMultiplaUscita = listaDocRegMultiplaUscita;
	}
	public String getIdJob() {
		return idJob;
	}
	public void setIdJob(String idJob) {
		this.idJob = idJob;
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
	
}