/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */

package it.eng.auriga.repository2.jaxws.webservices.extractzipfilesud;

import java.io.Serializable;

public class ExtractZipFileUDBean implements Serializable {

	private static final long serialVersionUID = 5254998015568413835L;
	
	private String idDoc;
	private String nroProgrVer;
	private String nomeFile;
	private String uri;

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getNroProgrVer() {
		return nroProgrVer;
	}

	public void setNroProgrVer(String nroProgrVer) {
		this.nroProgrVer = nroProgrVer;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
