/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-mar-2013 16.48.19 by Hibernate Tools 3.4.0.CR1

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

/**
 * TMimetypeFmtDig generated by hbm2java
 */
@Entity
@Table(name = "T_MIMETYPE_FMT_DIG")
public class TMimetypeFmtDig implements java.io.Serializable {

	private static final long serialVersionUID = 8461939540103502191L;

	private TMimetypeFmtDigId id;

	private TAnagFormatiDig TAnagFormatiDig;

	private String rifReader;

	private Date tsIns;

	private String idUteIns;

	private Date tsUltimoAgg;

	private String idUteUltimoAgg;

	private Boolean flgAnn;

	public TMimetypeFmtDig() {
	}

	public TMimetypeFmtDig(TMimetypeFmtDigId id, TAnagFormatiDig TAnagFormatiDig, Date tsIns, Date tsUltimoAgg, Boolean flgAnn) {
		this.id = id;
		this.TAnagFormatiDig = TAnagFormatiDig;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.flgAnn = flgAnn;
	}

	public TMimetypeFmtDig(TMimetypeFmtDigId id, TAnagFormatiDig TAnagFormatiDig, String rifReader, Date tsIns, String idUteIns, Date tsUltimoAgg,
			String idUteUltimoAgg, Boolean flgAnn) {
		this.id = id;
		this.TAnagFormatiDig = TAnagFormatiDig;
		this.rifReader = rifReader;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
		this.flgAnn = flgAnn;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "idDigFormat", column = @Column(name = "ID_DIG_FORMAT", nullable = false, length = 64)),
			@AttributeOverride(name = "mimetype", column = @Column(name = "MIMETYPE", nullable = false, length = 100)) })
	public TMimetypeFmtDigId getId() {
		return this.id;
	}

	public void setId(TMimetypeFmtDigId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIG_FORMAT", nullable = false, insertable = false, updatable = false)
	public TAnagFormatiDig getTAnagFormatiDig() {
		return this.TAnagFormatiDig;
	}

	public void setTAnagFormatiDig(TAnagFormatiDig TAnagFormatiDig) {
		this.TAnagFormatiDig = TAnagFormatiDig;
	}

	@Column(name = "RIF_READER", length = 30)
	public String getRifReader() {
		return this.rifReader;
	}

	public void setRifReader(String rifReader) {
		this.rifReader = rifReader;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "ID_UTE_INS", length = 64)
	public String getIdUteIns() {
		return this.idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	@Column(name = "TS_ULTIMO_AGG", nullable = false)
	public Date getTsUltimoAgg() {
		return this.tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	@Column(name = "ID_UTE_ULTIMO_AGG", length = 64)
	public String getIdUteUltimoAgg() {
		return this.idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	@Column(name = "FLG_ANN", nullable = false, precision = 1, scale = 0)
	public Boolean getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(Boolean flgAnn) {
		this.flgAnn = flgAnn;
	}

}