package it.eng.dm.sira.entity;

// Generated 21-nov-2014 16.03.15 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAreaTematicaProcedimento generated by hbm2java
 */
@Entity
@Table(name = "T_AREA_TEMATICA_PROCEDIMENTO")
public class TAreaTematicaProcedimento implements java.io.Serializable {

	private BigDecimal idArea;

	private String codice;

	private String descrizione;

	private String flgAnn;

	public TAreaTematicaProcedimento() {
	}

	public TAreaTematicaProcedimento(BigDecimal idArea, String codice, String flgAnn) {
		this.idArea = idArea;
		this.codice = codice;
		this.flgAnn = flgAnn;
	}

	public TAreaTematicaProcedimento(BigDecimal idArea, String codice, String descrizione, String flgAnn) {
		this.idArea = idArea;
		this.codice = codice;
		this.descrizione = descrizione;
		this.flgAnn = flgAnn;
	}

	@Id
	@Column(name = "ID_AREA", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdArea() {
		return this.idArea;
	}

	public void setIdArea(BigDecimal idArea) {
		this.idArea = idArea;
	}

	@Column(name = "CODICE", nullable = false, length = 40)
	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Column(name = "DESCRIZIONE", length = 200)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "FLG_ANN", nullable = false, length = 1)
	public String getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(String flgAnn) {
		this.flgAnn = flgAnn;
	}

}
