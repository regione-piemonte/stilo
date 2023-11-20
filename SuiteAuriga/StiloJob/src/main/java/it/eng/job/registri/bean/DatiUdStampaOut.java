/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;

public class DatiUdStampaOut implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@XmlVariabile(nome="ID_UD", tipo=TipoVariabile.SEMPLICE)
	private String idUd;
	
	@XmlVariabile(nome="ID_DOC", tipo=TipoVariabile.SEMPLICE)
	private String idDoc;
	
	@XmlVariabile(nome="DisplayFilename", tipo=TipoVariabile.SEMPLICE)
	private String displayFilename;
	
	
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
	public String getDisplayFilename() {
		return displayFilename;
	}
	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}
	
}
