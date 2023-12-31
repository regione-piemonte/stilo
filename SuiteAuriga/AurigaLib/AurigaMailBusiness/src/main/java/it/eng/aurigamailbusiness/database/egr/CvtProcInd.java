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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CvtProcInd generated by hbm2java
 */
@Entity
@Table(name = "CVT_PROC_IND")
public class CvtProcInd implements java.io.Serializable {

	private CvtProcIndId id;
	private CvtDecProc cvtDecProc;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;

	public CvtProcInd() {
	}

	public CvtProcInd(CvtProcIndId id, CvtDecProc cvtDecProc) {
		this.id = id;
		this.cvtDecProc = cvtDecProc;
	}

	public CvtProcInd(CvtProcIndId id, CvtDecProc cvtDecProc, Date dtIns,
			Integer uteIns, Date dtUltMod, Integer uteUltMod) {
		this.id = id;
		this.cvtDecProc = cvtDecProc;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idTpProc", column = @Column(name = "ID_TP_PROC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "idIndice", column = @Column(name = "ID_INDICE", nullable = false, precision = 8, scale = 0)) })
	public CvtProcIndId getId() {
		return this.id;
	}

	public void setId(CvtProcIndId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TP_PROC", nullable = false, insertable = false, updatable = false)
	public CvtDecProc getCvtDecProc() {
		return this.cvtDecProc;
	}

	public void setCvtDecProc(CvtDecProc cvtDecProc) {
		this.cvtDecProc = cvtDecProc;
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
