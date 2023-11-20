/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.checkout;


import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSCheckOutBean implements Serializable {

	private String xml;
	private String idDoc;
	private String nroProgrVer;
	private File extracted;
	
	
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
	public File getExtracted() {
		return extracted;
	}
	public void setExtracted(File extracted) {
		this.extracted = extracted;
	}
	

	


	

	

	}
