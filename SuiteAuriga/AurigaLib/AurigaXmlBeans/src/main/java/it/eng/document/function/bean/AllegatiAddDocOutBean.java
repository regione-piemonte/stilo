/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class AllegatiAddDocOutBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String idDoc;
	
	@NumeroColonna(numero = "2")
	private String nroVersione;
	
	@NumeroColonna(numero = "3")
	private String uri;
	
	@NumeroColonna(numero = "4")
	private String displayFileName;
	
	@NumeroColonna(numero = "5")
	private String idDocType;
	
	@NumeroColonna(numero = "6")
	private String nomeDocType;
	
	@NumeroColonna(numero = "7")
	private String descrizione;
	
	@NumeroColonna(numero = "8")
	private String nroAllegato;

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getNroVersione() {
		return nroVersione;
	}

	public void setNroVersione(String nroVersione) {
		this.nroVersione = nroVersione;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDisplayFileName() {
		return displayFileName;
	}

	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getNomeDocType() {
		return nomeDocType;
	}

	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNroAllegato() {
		return nroAllegato;
	}

	public void setNroAllegato(String nroAllegato) {
		this.nroAllegato = nroAllegato;
	}
	
}
