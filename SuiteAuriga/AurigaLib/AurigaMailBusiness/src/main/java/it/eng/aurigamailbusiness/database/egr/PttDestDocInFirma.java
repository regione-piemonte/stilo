/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

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
 * PttDestDocInFirma generated by hbm2java
 */
@Entity
@Table(name = "PTT_DEST_DOC_IN_FIRMA")
public class PttDestDocInFirma implements java.io.Serializable {

	private PttDestDocInFirmaId id;
	private PttDocInFirma pttDocInFirma;
	private CvtUo cvtUo;
	private CvtAnagPd cvtAnagPd;

	public PttDestDocInFirma() {
	}

	public PttDestDocInFirma(PttDestDocInFirmaId id, PttDocInFirma pttDocInFirma) {
		this.id = id;
		this.pttDocInFirma = pttDocInFirma;
	}

	public PttDestDocInFirma(PttDestDocInFirmaId id,
			PttDocInFirma pttDocInFirma, CvtUo cvtUo, CvtAnagPd cvtAnagPd) {
		this.id = id;
		this.pttDocInFirma = pttDocInFirma;
		this.cvtUo = cvtUo;
		this.cvtAnagPd = cvtAnagPd;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDocDaFirmare", column = @Column(name = "ID_DOC_DA_FIRMARE", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "nroOrdine", column = @Column(name = "NRO_ORDINE", nullable = false, precision = 3, scale = 0)) })
	public PttDestDocInFirmaId getId() {
		return this.id;
	}

	public void setId(PttDestDocInFirmaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOC_DA_FIRMARE", nullable = false, insertable = false, updatable = false)
	public PttDocInFirma getPttDocInFirma() {
		return this.pttDocInFirma;
	}

	public void setPttDocInFirma(PttDocInFirma pttDocInFirma) {
		this.pttDocInFirma = pttDocInFirma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UO")
	public CvtUo getCvtUo() {
		return this.cvtUo;
	}

	public void setCvtUo(CvtUo cvtUo) {
		this.cvtUo = cvtUo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ANAG")
	public CvtAnagPd getCvtAnagPd() {
		return this.cvtAnagPd;
	}

	public void setCvtAnagPd(CvtAnagPd cvtAnagPd) {
		this.cvtAnagPd = cvtAnagPd;
	}

}
