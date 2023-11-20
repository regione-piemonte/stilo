/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.extractmulti;

import java.io.File;
import java.io.Serializable;


public class ExtractBean implements Serializable {
	
	private String idDoc;
	private String nroProgrVer;
	
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
	

	}
