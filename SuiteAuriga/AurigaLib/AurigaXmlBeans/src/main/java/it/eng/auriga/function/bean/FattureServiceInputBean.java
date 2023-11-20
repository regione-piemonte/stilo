/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FattureServiceInputBean implements Serializable{

	private String idUd;
	private String idDoc;
	private String fileName;
	private String mimeType;	
	private boolean isFirmato;
	private String uriFileFattura;

	public String getIdUd() {
		return idUd;
	}

	public String getIdDoc() {
		return idDoc;
	}

	public String getFileName() {
		return fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public boolean isFirmato() {
		return isFirmato;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setFirmato(boolean isFirmato) {
		this.isFirmato = isFirmato;
	}

	public String getUriFileFattura() {
		return uriFileFattura;
	}

	public void setUriFileFattura(String uriFileFattura) {
		this.uriFileFattura = uriFileFattura;
	}

}
