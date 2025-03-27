/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SapVociPegVLivResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String entrateUscite;
	private String esercizio;
	private String pdcLivello1;
	private String pdcLivello2;
	private String pdcLivello3;
	private String pdcLivello4;
	private String pdcLivello5;
	private String descrizione;
	
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
	
	public String getPdcLivello1() {
		return pdcLivello1;
	}
	
	public void setPdcLivello1(String pdcLivello1) {
		this.pdcLivello1 = pdcLivello1;
	}
	
	public String getPdcLivello2() {
		return pdcLivello2;
	}
	
	public void setPdcLivello2(String pdcLivello2) {
		this.pdcLivello2 = pdcLivello2;
	}
	
	public String getPdcLivello3() {
		return pdcLivello3;
	}
	
	public void setPdcLivello3(String pdcLivello3) {
		this.pdcLivello3 = pdcLivello3;
	}
	
	public String getPdcLivello4() {
		return pdcLivello4;
	}
	
	public void setPdcLivello4(String pdcLivello4) {
		this.pdcLivello4 = pdcLivello4;
	}
	
	public String getPdcLivello5() {
		return pdcLivello5;
	}
	
	public void setPdcLivello5(String pdcLivello5) {
		this.pdcLivello5 = pdcLivello5;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "SapVociPegVLivResponse [entrateUscite=" + entrateUscite + ", esercizio=" + esercizio + ", pdcLivello1="
				+ pdcLivello1 + ", pdcLivello2=" + pdcLivello2 + ", pdcLivello3=" + pdcLivello3 + ", pdcLivello4="
				+ pdcLivello4 + ", pdcLivello5=" + pdcLivello5 + ", descrizione=" + descrizione + "]";
	}
	
}
