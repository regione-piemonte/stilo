/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSCheckInBean implements Serializable {

	private String idDoc;
	private String nroVersioneEstratta;
	private String flgVerificaFirmaFile;
	private String attributiVerXML;
	private String idUd;
	
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getNroVersioneEstratta() {
		return nroVersioneEstratta;
	}
	public void setNroVersioneEstratta(String nroVersioneEstratta) {
		this.nroVersioneEstratta = nroVersioneEstratta;
	}
	public String getFlgVerificaFirmaFile() {
		return flgVerificaFirmaFile;
	}
	public void setFlgVerificaFirmaFile(String flgVerificaFirmaFile) {
		this.flgVerificaFirmaFile = flgVerificaFirmaFile;
	}
	public String getAttributiVerXML() {
		return attributiVerXML;
	}
	public void setAttributiVerXML(String attributiVerXML) {
		this.attributiVerXML = attributiVerXML;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	
	

	}
