/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.extractone;


import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSExtractOneBean implements Serializable {

	private String xml;
	private String idDoc;
	private String idUd;	
	private String nroProgrVer;
	private List<File> extracted;
	private String fileName;
	private String mimeType;
	private boolean firmato;
	private boolean timbrato;
		
	public String getIdDoc() {
		return idDoc;
	}
	public String getNroProgrVer() {
		return nroProgrVer;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public void setNroProgrVer(String nroProgrVer) {
		this.nroProgrVer = nroProgrVer;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public List<File> getExtracted() {
		return extracted;
	}
	public void setExtracted(List<File> extracted) {
		this.extracted = extracted;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public boolean isFirmato() {
		return firmato;
	}
	public void setFirmato(boolean firmato) {
		this.firmato = firmato;
	}
	public boolean isTimbrato() {
		return timbrato;
	}
	public void setTimbrato(boolean timbrato) {
		this.timbrato = timbrato;
	}
	

	

	}
