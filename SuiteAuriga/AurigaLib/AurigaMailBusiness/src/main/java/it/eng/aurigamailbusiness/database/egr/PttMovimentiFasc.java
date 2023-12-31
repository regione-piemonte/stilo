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
 * PttMovimentiFasc generated by hbm2java
 */
@Entity
@Table(name = "PTT_MOVIMENTI_FASC")
public class PttMovimentiFasc implements java.io.Serializable {

	private PttMovimentiFascId id;
	private CvtUtenti cvtUtentiByUteAcc;
	private CvtUo cvtUoByUoSped;
	private CvtUo cvtUoByUoDest;
	private CvtUtenti cvtUtentiByUteSped;
	private Date dtAcc;
	private String flgAnn;
	private String flgAccEspl;
	private String flgEst;
	private String flgSpedIntFasc;
	private String flgTpAcc;
	private String note;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private String flgSmist;
	private String flgSmistAnn;

	public PttMovimentiFasc() {
	}

	public PttMovimentiFasc(PttMovimentiFascId id, CvtUo cvtUoByUoSped,
			CvtUo cvtUoByUoDest, CvtUtenti cvtUtentiByUteSped) {
		this.id = id;
		this.cvtUoByUoSped = cvtUoByUoSped;
		this.cvtUoByUoDest = cvtUoByUoDest;
		this.cvtUtentiByUteSped = cvtUtentiByUteSped;
	}

	public PttMovimentiFasc(PttMovimentiFascId id, CvtUtenti cvtUtentiByUteAcc,
			CvtUo cvtUoByUoSped, CvtUo cvtUoByUoDest,
			CvtUtenti cvtUtentiByUteSped, Date dtAcc, String flgAnn,
			String flgAccEspl, String flgEst, String flgSpedIntFasc,
			String flgTpAcc, String note, Date dtIns, Integer uteIns,
			Date dtUltMod, Integer uteUltMod, String flgSmist,
			String flgSmistAnn) {
		this.id = id;
		this.cvtUtentiByUteAcc = cvtUtentiByUteAcc;
		this.cvtUoByUoSped = cvtUoByUoSped;
		this.cvtUoByUoDest = cvtUoByUoDest;
		this.cvtUtentiByUteSped = cvtUtentiByUteSped;
		this.dtAcc = dtAcc;
		this.flgAnn = flgAnn;
		this.flgAccEspl = flgAccEspl;
		this.flgEst = flgEst;
		this.flgSpedIntFasc = flgSpedIntFasc;
		this.flgTpAcc = flgTpAcc;
		this.note = note;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.flgSmist = flgSmist;
		this.flgSmistAnn = flgSmistAnn;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idFascicolo", column = @Column(name = "ID_FASCICOLO", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "numSottofasc", column = @Column(name = "NUM_SOTTOFASC", nullable = false, precision = 5, scale = 0)),
			@AttributeOverride(name = "dtSped", column = @Column(name = "DT_SPED", nullable = false, length = 7)) })
	public PttMovimentiFascId getId() {
		return this.id;
	}

	public void setId(PttMovimentiFascId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UTE_ACC")
	public CvtUtenti getCvtUtentiByUteAcc() {
		return this.cvtUtentiByUteAcc;
	}

	public void setCvtUtentiByUteAcc(CvtUtenti cvtUtentiByUteAcc) {
		this.cvtUtentiByUteAcc = cvtUtentiByUteAcc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UO_SPED", nullable = false)
	public CvtUo getCvtUoByUoSped() {
		return this.cvtUoByUoSped;
	}

	public void setCvtUoByUoSped(CvtUo cvtUoByUoSped) {
		this.cvtUoByUoSped = cvtUoByUoSped;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UO_DEST", nullable = false)
	public CvtUo getCvtUoByUoDest() {
		return this.cvtUoByUoDest;
	}

	public void setCvtUoByUoDest(CvtUo cvtUoByUoDest) {
		this.cvtUoByUoDest = cvtUoByUoDest;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UTE_SPED", nullable = false)
	public CvtUtenti getCvtUtentiByUteSped() {
		return this.cvtUtentiByUteSped;
	}

	public void setCvtUtentiByUteSped(CvtUtenti cvtUtentiByUteSped) {
		this.cvtUtentiByUteSped = cvtUtentiByUteSped;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ACC", length = 7)
	public Date getDtAcc() {
		return this.dtAcc;
	}

	public void setDtAcc(Date dtAcc) {
		this.dtAcc = dtAcc;
	}

	@Column(name = "FLG_ANN", length = 1)
	public String getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(String flgAnn) {
		this.flgAnn = flgAnn;
	}

	@Column(name = "FLG_ACC_ESPL", length = 1)
	public String getFlgAccEspl() {
		return this.flgAccEspl;
	}

	public void setFlgAccEspl(String flgAccEspl) {
		this.flgAccEspl = flgAccEspl;
	}

	@Column(name = "FLG_EST", length = 1)
	public String getFlgEst() {
		return this.flgEst;
	}

	public void setFlgEst(String flgEst) {
		this.flgEst = flgEst;
	}

	@Column(name = "FLG_SPED_INT_FASC", length = 1)
	public String getFlgSpedIntFasc() {
		return this.flgSpedIntFasc;
	}

	public void setFlgSpedIntFasc(String flgSpedIntFasc) {
		this.flgSpedIntFasc = flgSpedIntFasc;
	}

	@Column(name = "FLG_TP_ACC", length = 1)
	public String getFlgTpAcc() {
		return this.flgTpAcc;
	}

	public void setFlgTpAcc(String flgTpAcc) {
		this.flgTpAcc = flgTpAcc;
	}

	@Column(name = "NOTE", length = 500)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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

	@Column(name = "FLG_SMIST", length = 1)
	public String getFlgSmist() {
		return this.flgSmist;
	}

	public void setFlgSmist(String flgSmist) {
		this.flgSmist = flgSmist;
	}

	@Column(name = "FLG_SMIST_ANN", length = 1)
	public String getFlgSmistAnn() {
		return this.flgSmistAnn;
	}

	public void setFlgSmistAnn(String flgSmistAnn) {
		this.flgSmistAnn = flgSmistAnn;
	}

}
