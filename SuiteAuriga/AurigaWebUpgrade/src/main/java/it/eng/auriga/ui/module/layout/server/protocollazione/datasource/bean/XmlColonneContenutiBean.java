/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.io.Serializable;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ErroreRigaExcelBean;

public class XmlColonneContenutiBean implements Serializable{
	
	private static final long serialVersionUID = 308124932501049140L;
	
	private it.eng.jaxb.variabili.Lista dettagliColonne;
	private it.eng.jaxb.variabili.Lista xmlContenuti;
	private String message;
	private String numRigheDestinatari;
	private boolean successful;
	private List<ErroreRigaExcelBean> listaExcelDatiInError;
	private String mimetype;
	private String uri;
	
	public it.eng.jaxb.variabili.Lista getDettagliColonne() {
		return dettagliColonne;
	}
	public void setDettagliColonne(it.eng.jaxb.variabili.Lista dettagliColonne) {
		this.dettagliColonne = dettagliColonne;
	}
	public it.eng.jaxb.variabili.Lista getXmlContenuti() {
		return xmlContenuti;
	}
	public void setXmlContenuti(it.eng.jaxb.variabili.Lista xmlContenuti) {
		this.xmlContenuti = xmlContenuti;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNumRigheDestinatari() {
		return numRigheDestinatari;
	}
	public void setNumRigheDestinatari(String numRigheDestinatari) {
		this.numRigheDestinatari = numRigheDestinatari;
	}
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public List<ErroreRigaExcelBean> getListaExcelDatiInError() {
		return listaExcelDatiInError;
	}
	public void setListaExcelDatiInError(List<ErroreRigaExcelBean> listaExcelDatiInError) {
		this.listaExcelDatiInError = listaExcelDatiInError;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

}
