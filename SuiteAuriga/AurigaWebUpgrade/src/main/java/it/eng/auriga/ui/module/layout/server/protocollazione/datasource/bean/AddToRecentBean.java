/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

public class AddToRecentBean extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idUd;
	private String idDoc;
	private String  nroProgrVer;
	
	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	
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
