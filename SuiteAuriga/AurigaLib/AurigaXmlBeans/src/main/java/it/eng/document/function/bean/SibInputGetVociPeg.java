/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibInputGetVociPeg implements Serializable {

	private static final long serialVersionUID = 1L;

	private String entrateUscite;
	private Integer esercizio;
	private Integer capitolo;
	private Integer articolo;
	private Integer numero;
	private String descrizioneCapitolo;
	private String descrizioneDirezioneCompetente;
	private Integer PDCLivello1;
	private BigDecimal missione;
	private BigDecimal programma;
	private Integer CDR;
	private String descrizioneCDR;
	private Integer CAN;
	private String descrizioneCAN;
	private BigDecimal disponibile;
	private Integer competenzaPluriennale;
	private String foglia;

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

	public Integer getCapitolo() {
		return capitolo;
	}

	public void setCapitolo(Integer capitolo) {
		this.capitolo = capitolo;
	}

	public Integer getArticolo() {
		return articolo;
	}

	public void setArticolo(Integer articolo) {
		this.articolo = articolo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}

	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}

	public String getDescrizioneDirezioneCompetente() {
		return descrizioneDirezioneCompetente;
	}

	public void setDescrizioneDirezioneCompetente(String descrizioneDirezioneCompetente) {
		this.descrizioneDirezioneCompetente = descrizioneDirezioneCompetente;
	}

	public Integer getPDCLivello1() {
		return PDCLivello1;
	}

	public void setPDCLivello1(Integer pDCLivello1) {
		PDCLivello1 = pDCLivello1;
	}

	public BigDecimal getMissione() {
		return missione;
	}

	public void setMissione(BigDecimal missione) {
		this.missione = missione;
	}

	public BigDecimal getProgramma() {
		return programma;
	}

	public void setProgramma(BigDecimal programma) {
		this.programma = programma;
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

	public Integer getCAN() {
		return CAN;
	}

	public void setCAN(Integer cAN) {
		CAN = cAN;
	}

	public String getDescrizioneCAN() {
		return descrizioneCAN;
	}

	public void setDescrizioneCAN(String descrizioneCAN) {
		this.descrizioneCAN = descrizioneCAN;
	}

	public BigDecimal getDisponibile() {
		return disponibile;
	}

	public void setDisponibile(BigDecimal disponibile) {
		this.disponibile = disponibile;
	}

	public Integer getCompetenzaPluriennale() {
		return competenzaPluriennale;
	}

	public void setCompetenzaPluriennale(Integer competenzaPluriennale) {
		this.competenzaPluriennale = competenzaPluriennale;
	}

	public String getFoglia() {
		return foglia;
	}

	public void setFoglia(String foglia) {
		this.foglia = foglia;
	}

}