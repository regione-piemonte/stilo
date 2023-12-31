/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttImpostazioniPostaz generated by hbm2java
 */
@Entity
@Table(name = "PTT_IMPOSTAZIONI_POSTAZ")
public class PttImpostazioniPostaz implements java.io.Serializable {

	private PttImpostazioniPostazId id;
	private CvtUo cvtUo;
	private PttPossibiliImpostazioni pttPossibiliImpostazioni;
	private String valDettaglio;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;

	public PttImpostazioniPostaz() {
	}

	public PttImpostazioniPostaz(PttImpostazioniPostazId id, CvtUo cvtUo,
			PttPossibiliImpostazioni pttPossibiliImpostazioni) {
		this.id = id;
		this.cvtUo = cvtUo;
		this.pttPossibiliImpostazioni = pttPossibiliImpostazioni;
	}

	public PttImpostazioniPostaz(PttImpostazioniPostazId id, CvtUo cvtUo,
			PttPossibiliImpostazioni pttPossibiliImpostazioni,
			String valDettaglio, Date dtIns, Integer uteIns, Date dtUltMod,
			Integer uteUltMod) {
		this.id = id;
		this.cvtUo = cvtUo;
		this.pttPossibiliImpostazioni = pttPossibiliImpostazioni;
		this.valDettaglio = valDettaglio;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idUo", column = @Column(name = "ID_UO", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "target", column = @Column(name = "TARGET", nullable = false, length = 100)) })
	public PttImpostazioniPostazId getId() {
		return this.id;
	}

	public void setId(PttImpostazioniPostazId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UO", nullable = false, insertable = false, updatable = false)
	public CvtUo getCvtUo() {
		return this.cvtUo;
	}

	public void setCvtUo(CvtUo cvtUo) {
		this.cvtUo = cvtUo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "TARGET", referencedColumnName = "TARGET", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "COD_VALORE", referencedColumnName = "COD_VALORE", nullable = false, insertable = false, updatable = false) })
	public PttPossibiliImpostazioni getPttPossibiliImpostazioni() {
		return this.pttPossibiliImpostazioni;
	}

	public void setPttPossibiliImpostazioni(
			PttPossibiliImpostazioni pttPossibiliImpostazioni) {
		this.pttPossibiliImpostazioni = pttPossibiliImpostazioni;
	}

	@Column(name = "VAL_DETTAGLIO", length = 100)
	public String getValDettaglio() {
		return this.valDettaglio;
	}

	public void setValDettaglio(String valDettaglio) {
		this.valDettaglio = valDettaglio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS", length = 7)
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	@Column(name = "UTE_INS", precision = 6, scale = 0)
	public Integer getUteIns() {
		return this.uteIns;
	}

	public void setUteIns(Integer uteIns) {
		this.uteIns = uteIns;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ULT_MOD", length = 7)
	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	@Column(name = "UTE_ULT_MOD", precision = 6, scale = 0)
	public Integer getUteUltMod() {
		return this.uteUltMod;
	}

	public void setUteUltMod(Integer uteUltMod) {
		this.uteUltMod = uteUltMod;
	}

}
