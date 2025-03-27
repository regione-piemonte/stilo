/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SapZstCapitoloResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String capitolo;
	private String descrizioneCapitolo;
	private String cdr;
	private String descrizioneCdr;
	
	public String getCapitolo() {
		return capitolo;
	}
	
	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}
	
	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}
	
	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}
	
	public String getCdr() {
		return cdr;
	}
	
	public void setCdr(String cdr) {
		this.cdr = cdr;
	}
	
	public String getDescrizioneCdr() {
		return descrizioneCdr;
	}
	
	public void setDescrizioneCdr(String descrizioneCdr) {
		this.descrizioneCdr = descrizioneCdr;
	}

	@Override
	public String toString() {
		return "SapZstCapitolo [capitolo=" + capitolo + ", descrizioneCapitolo=" + descrizioneCapitolo + ", cdr=" + cdr
				+ ", descrizioneCdr=" + descrizioneCdr + "]";
	}
	
}
