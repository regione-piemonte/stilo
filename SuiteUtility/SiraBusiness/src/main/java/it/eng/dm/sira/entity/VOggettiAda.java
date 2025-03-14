package it.eng.dm.sira.entity;

// Generated 25-nov-2014 17.11.46 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VOggettiAda generated by hbm2java
 */
@Entity
@Table(name = "V_OGGETTI_ADA")
public class VOggettiAda implements java.io.Serializable {

	private BigDecimal codice;

	private String descrizione;

	private BigDecimal durata;

	private BigDecimal blocco;

	private BigDecimal attivo;

	private BigDecimal codTema;

	public VOggettiAda() {
	}

	@Id
	@Column(name = "CODICE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getCodice() {
		return this.codice;
	}

	public void setCodice(BigDecimal codice) {
		this.codice = codice;
	}

	@Column(name = "DESCRIZIONE", nullable = false)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "DURATA", nullable = false, precision = 22, scale = 0)
	public BigDecimal getDurata() {
		return this.durata;
	}

	public void setDurata(BigDecimal durata) {
		this.durata = durata;
	}

	@Column(name = "BLOCCO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getBlocco() {
		return this.blocco;
	}

	public void setBlocco(BigDecimal blocco) {
		this.blocco = blocco;
	}

	@Column(name = "ATTIVO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getAttivo() {
		return this.attivo;
	}

	public void setAttivo(BigDecimal attivo) {
		this.attivo = attivo;
	}

	@Column(name = "COD_TEMA", precision = 22, scale = 0)
	public BigDecimal getCodTema() {
		return this.codTema;
	}

	public void setCodTema(BigDecimal codTema) {
		this.codTema = codTema;
	}

}
