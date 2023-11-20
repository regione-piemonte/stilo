/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibInputGetCapitolo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String entrateUscite;
	private Integer esercizio;
	private Integer CDR;
	private String descrizioneCDR;

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

	public Integer getCDR() {
		return CDR;
	}

	public void setCDR(Integer cDR) {
		CDR = cDR;
	}

	public String getDescrizioneCDR() {
		return descrizioneCDR;
	}

	public void setDescrizioneCDR(String descrizioneCDR) {
		this.descrizioneCDR = descrizioneCDR;
	}

}
