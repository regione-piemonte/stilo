/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
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
 * PttRichInvioPoste generated by hbm2java
 */
@Entity
@Table(name = "PTT_RICH_INVIO_POSTE")
public class PttRichInvioPoste implements java.io.Serializable {

	private PttRichInvioPosteId id;
	private PttDocumenti pttDocumenti;
	private String tipoServizio;
	private Date tsIns;
	private BigDecimal uteIns;

	public PttRichInvioPoste() {
	}

	public PttRichInvioPoste(PttRichInvioPosteId id, PttDocumenti pttDocumenti,
			String tipoServizio, Date tsIns) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
		this.tipoServizio = tipoServizio;
		this.tsIns = tsIns;
	}

	public PttRichInvioPoste(PttRichInvioPosteId id, PttDocumenti pttDocumenti,
			String tipoServizio, Date tsIns, BigDecimal uteIns) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
		this.tipoServizio = tipoServizio;
		this.tsIns = tsIns;
		this.uteIns = uteIns;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "idRichiesta", column = @Column(name = "ID_RICHIESTA", nullable = false, length = 64)) })
	public PttRichInvioPosteId getId() {
		return this.id;
	}

	public void setId(PttRichInvioPosteId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOC", nullable = false, insertable = false, updatable = false)
	public PttDocumenti getPttDocumenti() {
		return this.pttDocumenti;
	}

	public void setPttDocumenti(PttDocumenti pttDocumenti) {
		this.pttDocumenti = pttDocumenti;
	}

	@Column(name = "TIPO_SERVIZIO", nullable = false, length = 1)
	public String getTipoServizio() {
		return this.tipoServizio;
	}

	public void setTipoServizio(String tipoServizio) {
		this.tipoServizio = tipoServizio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TS_INS", nullable = false, length = 7)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "UTE_INS", precision = 22, scale = 0)
	public BigDecimal getUteIns() {
		return this.uteIns;
	}

	public void setUteIns(BigDecimal uteIns) {
		this.uteIns = uteIns;
	}

}