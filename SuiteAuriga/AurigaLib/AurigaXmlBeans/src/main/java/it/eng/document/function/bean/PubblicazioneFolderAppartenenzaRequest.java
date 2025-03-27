/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PubblicazioneFolderAppartenenzaRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codiceFascicolo;
	private String idFolder;
	private String oggetto;
	
	public String getCodiceFascicolo() {
		return codiceFascicolo;
	}
	
	public void setCodiceFascicolo(String codiceFascicolo) {
		this.codiceFascicolo = codiceFascicolo;
	}
	
	public String getIdFolder() {
		return idFolder;
	}
	
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	@Override
	public String toString() {
		return "PubblicazioneFolderAppartenenzaRequest [codiceFascicolo=" + codiceFascicolo + ", idFolder=" + idFolder
				+ ", oggetto=" + oggetto + "]";
	}
	
}
