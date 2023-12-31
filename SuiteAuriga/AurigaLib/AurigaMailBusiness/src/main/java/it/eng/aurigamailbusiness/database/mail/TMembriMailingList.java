/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 2-feb-2017 16.02.31 by Hibernate Tools 3.5.0.Final

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
 * TMembriMailingList generated by hbm2java
 */
@Entity
@Table(name = "T_MEMBRI_MAILING_LIST")
public class TMembriMailingList implements java.io.Serializable {

	private static final long serialVersionUID = 5380216367227350601L;
	private TMembriMailingListId id;
	private TRubricaEmail TRubricaEmailByIdVoceRubricaMailingList;
	private TRubricaEmail TRubricaEmailByIdVoceRubricaMembro;
	private Date tsIns;
	private String idUteIns;
	private Date tsUltimoAgg;
	private String idUteUltimoAgg;

	public TMembriMailingList() {
	}

	public TMembriMailingList(TMembriMailingListId id, TRubricaEmail TRubricaEmailByIdVoceRubricaMailingList, TRubricaEmail TRubricaEmailByIdVoceRubricaMembro,
			Date tsIns, Date tsUltimoAgg) {
		this.id = id;
		this.TRubricaEmailByIdVoceRubricaMailingList = TRubricaEmailByIdVoceRubricaMailingList;
		this.TRubricaEmailByIdVoceRubricaMembro = TRubricaEmailByIdVoceRubricaMembro;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public TMembriMailingList(TMembriMailingListId id, TRubricaEmail TRubricaEmailByIdVoceRubricaMailingList, TRubricaEmail TRubricaEmailByIdVoceRubricaMembro,
			Date tsIns, String idUteIns, Date tsUltimoAgg, String idUteUltimoAgg) {
		this.id = id;
		this.TRubricaEmailByIdVoceRubricaMailingList = TRubricaEmailByIdVoceRubricaMailingList;
		this.TRubricaEmailByIdVoceRubricaMembro = TRubricaEmailByIdVoceRubricaMembro;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idVoceRubricaMailingList", column = @Column(name = "ID_VOCE_RUBRICA_MAILING_LIST", nullable = false, length = 64)),
			@AttributeOverride(name = "idVoceRubricaMembro", column = @Column(name = "ID_VOCE_RUBRICA_MEMBRO", nullable = false, length = 64)) })
	public TMembriMailingListId getId() {
		return this.id;
	}

	public void setId(TMembriMailingListId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VOCE_RUBRICA_MAILING_LIST", nullable = false, insertable = false, updatable = false)
	public TRubricaEmail getTRubricaEmailByIdVoceRubricaMailingList() {
		return this.TRubricaEmailByIdVoceRubricaMailingList;
	}

	public void setTRubricaEmailByIdVoceRubricaMailingList(TRubricaEmail TRubricaEmailByIdVoceRubricaMailingList) {
		this.TRubricaEmailByIdVoceRubricaMailingList = TRubricaEmailByIdVoceRubricaMailingList;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VOCE_RUBRICA_MEMBRO", nullable = false, insertable = false, updatable = false)
	public TRubricaEmail getTRubricaEmailByIdVoceRubricaMembro() {
		return this.TRubricaEmailByIdVoceRubricaMembro;
	}

	public void setTRubricaEmailByIdVoceRubricaMembro(TRubricaEmail TRubricaEmailByIdVoceRubricaMembro) {
		this.TRubricaEmailByIdVoceRubricaMembro = TRubricaEmailByIdVoceRubricaMembro;
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

}
