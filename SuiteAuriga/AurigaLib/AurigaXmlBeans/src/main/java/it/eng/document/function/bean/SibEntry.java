/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private String entrateUscite;
	private Integer esercizio;
	private Integer capitolo;
	private Integer articolo;
	private Integer numero;
	private Integer competenzaPluriennale;

	private String descrizioneCapitolo;
	private String descrizioneDC;
	private String descrizioneCDR;
	private String descrizioneCAN;
	private Integer titolo;
	private Integer funzione;
	private Integer servizio;
	private Integer intervento;
	private Integer PDCLivello1;
	private Integer PDCLivello2;
	private Integer PDCLivello3;
	private Integer PDCLivello4;
	private Integer PDCLivello5;
	private String PDCArmonizzatoAlf;
	private BigDecimal missione;
	private BigDecimal programma;
	private Integer CDR;
	private Integer CAN;
	private String direzioneCentrale;
	private BigDecimal proposto;
	private BigDecimal previsione;
	private BigDecimal iniziale;
	private BigDecimal approvato;
	private BigDecimal totaleVariazioni;
	private BigDecimal assestato;
	private BigDecimal impegnatoAccertato;
	private BigDecimal disponibile;
	private BigDecimal mandatiReversaliEmessi;
	private BigDecimal mandatiReversaliPagati;
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

	public String getDescrizioneDC() {
		return descrizioneDC;
	}

	public void setDescrizioneDC(String descrizioneDC) {
		this.descrizioneDC = descrizioneDC;
	}

	public String getDescrizioneCDR() {
		return descrizioneCDR;
	}

	public void setDescrizioneCDR(String descrizioneCDR) {
		this.descrizioneCDR = descrizioneCDR;
	}

	public String getDescrizioneCAN() {
		return descrizioneCAN;
	}

	public void setDescrizioneCAN(String descrizioneCAN) {
		this.descrizioneCAN = descrizioneCAN;
	}

	public Integer getCompetenzaPluriennale() {
		return competenzaPluriennale;
	}

	public void setCompetenzaPluriennale(Integer competenzaPluriennale) {
		this.competenzaPluriennale = competenzaPluriennale;
	}

	public Integer getTitolo() {
		return titolo;
	}

	public void setTitolo(Integer titolo) {
		this.titolo = titolo;
	}

	public Integer getFunzione() {
		return funzione;
	}

	public void setFunzione(Integer funzione) {
		this.funzione = funzione;
	}

	public Integer getServizio() {
		return servizio;
	}

	public void setServizio(Integer servizio) {
		this.servizio = servizio;
	}

	public Integer getIntervento() {
		return intervento;
	}

	public void setIntervento(Integer intervento) {
		this.intervento = intervento;
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

	public String getPDCArmonizzatoAlf() {
		return PDCArmonizzatoAlf;
	}

	public void setPDCArmonizzatoAlf(String pDCArmonizzatoAlf) {
		PDCArmonizzatoAlf = pDCArmonizzatoAlf;
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

	public Integer getCAN() {
		return CAN;
	}

	public void setCAN(Integer cAN) {
		CAN = cAN;
	}

	public String getDirezioneCentrale() {
		return direzioneCentrale;
	}

	public void setDirezioneCentrale(String direzioneCentrale) {
		this.direzioneCentrale = direzioneCentrale;
	}

	public BigDecimal getProposto() {
		return proposto;
	}

	public void setProposto(BigDecimal proposto) {
		this.proposto = proposto;
	}

	public BigDecimal getPrevisione() {
		return previsione;
	}

	public void setPrevisione(BigDecimal previsione) {
		this.previsione = previsione;
	}

	public BigDecimal getIniziale() {
		return iniziale;
	}

	public void setIniziale(BigDecimal iniziale) {
		this.iniziale = iniziale;
	}

	public BigDecimal getApprovato() {
		return approvato;
	}

	public void setApprovato(BigDecimal approvato) {
		this.approvato = approvato;
	}

	public BigDecimal getTotaleVariazioni() {
		return totaleVariazioni;
	}

	public void setTotaleVariazioni(BigDecimal totaleVariazioni) {
		this.totaleVariazioni = totaleVariazioni;
	}

	public BigDecimal getAssestato() {
		return assestato;
	}

	public void setAssestato(BigDecimal assestato) {
		this.assestato = assestato;
	}

	public BigDecimal getImpegnatoAccertato() {
		return impegnatoAccertato;
	}

	public void setImpegnatoAccertato(BigDecimal impegnatoAccertato) {
		this.impegnatoAccertato = impegnatoAccertato;
	}

	public BigDecimal getDisponibile() {
		return disponibile;
	}

	public void setDisponibile(BigDecimal disponibile) {
		this.disponibile = disponibile;
	}

	public BigDecimal getMandatiReversaliEmessi() {
		return mandatiReversaliEmessi;
	}

	public void setMandatiReversaliEmessi(BigDecimal mandatiReversaliEmessi) {
		this.mandatiReversaliEmessi = mandatiReversaliEmessi;
	}

	public BigDecimal getMandatiReversaliPagati() {
		return mandatiReversaliPagati;
	}

	public void setMandatiReversaliPagati(BigDecimal mandatiReversaliPagati) {
		this.mandatiReversaliPagati = mandatiReversaliPagati;
	}

	public String getFoglia() {
		return foglia;
	}

	public void setFoglia(String foglia) {
		this.foglia = foglia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entrateUscite == null) ? 0 : entrateUscite.hashCode());
		result = prime * result + ((esercizio == null) ? 0 : esercizio.hashCode());
		result = prime * result + ((capitolo == null) ? 0 : capitolo.hashCode());
		result = prime * result + ((articolo == null) ? 0 : articolo.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((competenzaPluriennale == null) ? 0 : competenzaPluriennale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SibEntry other = (SibEntry) obj;
		if (articolo == null) {
			if (other.articolo != null)
				return false;
		} else if (!articolo.equals(other.articolo))
			return false;
		if (capitolo == null) {
			if (other.capitolo != null)
				return false;
		} else if (!capitolo.equals(other.capitolo))
			return false;
		if (competenzaPluriennale == null) {
			if (other.competenzaPluriennale != null)
				return false;
		} else if (!competenzaPluriennale.equals(other.competenzaPluriennale))
			return false;
		if (entrateUscite == null) {
			if (other.entrateUscite != null)
				return false;
		} else if (!entrateUscite.equals(other.entrateUscite))
			return false;
		if (esercizio == null) {
			if (other.esercizio != null)
				return false;
		} else if (!esercizio.equals(other.esercizio))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

}