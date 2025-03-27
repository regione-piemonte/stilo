/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SapCapitoloRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String cdr;
	private String descrizioneCdr;
	private String entrateUscite;
	private String esercizio;
	
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
	
	public String getEntrateUscite() {
		return entrateUscite;
	}
	
	public void setEntrateUscite(String entrateUscite) {
		this.entrateUscite = entrateUscite;
	}
	
	public String getEsercizio() {
		return esercizio;
	}
	
	public void setEsercizio(String esercizio) {
		this.esercizio = esercizio;
	}

	@Override
	public String toString() {
		return "SapCapitoloRequest [cdr=" + cdr + ", descrizioneCdr=" + descrizioneCdr + ", entrateUscite="
				+ entrateUscite + ", esercizio=" + esercizio + "]";
	}
	
}
