/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibInputGetVociPegVLiv implements Serializable {

	private static final long serialVersionUID = 1L;

	private String entrateUscite;
	private Integer esercizio;
	private Integer PDCLivello1;
	private Integer PDCLivello2;
	private Integer PDCLivello3;
	private Integer PDCLivello4;
	private Integer PDCLivello5;

	public String getEntrateUscite() {
		return entrateUscite;
	}

	public void setEntrateUscite(String entrateUscite) {
		this.entrateUscite = entrateUscite;
	}

	public Integer getEsercizio() {
		return esercizio;
	}

	public void setEsercizio(Integer esercizio) {
		this.esercizio = esercizio;
	}

	public Integer getPDCLivello1() {
		return PDCLivello1;
	}

	public void setPDCLivello1(Integer pDCLivello1) {
		PDCLivello1 = pDCLivello1;
	}

	public Integer getPDCLivello2() {
		return PDCLivello2;
	}

	public void setPDCLivello2(Integer pDCLivello2) {
		PDCLivello2 = pDCLivello2;
	}

	public Integer getPDCLivello3() {
		return PDCLivello3;
	}

	public void setPDCLivello3(Integer pDCLivello3) {
		PDCLivello3 = pDCLivello3;
	}

	public Integer getPDCLivello4() {
		return PDCLivello4;
	}

	public void setPDCLivello4(Integer pDCLivello4) {
		PDCLivello4 = pDCLivello4;
	}

	public Integer getPDCLivello5() {
		return PDCLivello5;
	}

	public void setPDCLivello5(Integer pDCLivello5) {
		PDCLivello5 = pDCLivello5;
	}

}