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
 * PttNomdoc generated by hbm2java
 */
@Entity
@Table(name = "PTT_NOMDOC")
public class PttNomdoc implements java.io.Serializable {

	private PttNomdocId id;
	private PttDocumenti pttDocumenti;
	private CvtAnagPd cvtAnagPd;

	public PttNomdoc() {
	}

	public PttNomdoc(PttNomdocId id, PttDocumenti pttDocumenti) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
	}

	public PttNomdoc(PttNomdocId id, PttDocumenti pttDocumenti,
			CvtAnagPd cvtAnagPd) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
		this.cvtAnagPd = cvtAnagPd;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "idAnag", column = @Column(name = "ID_ANAG", precision = 9, scale = 0)),
			@AttributeOverride(name = "flgEsibDest", column = @Column(name = "FLG_ESIB_DEST", length = 1)),
			@AttributeOverride(name = "flgAltriNom", column = @Column(name = "FLG_ALTRI_NOM", length = 1)),
			@AttributeOverride(name = "idTopon", column = @Column(name = "ID_TOPON", precision = 9, scale = 0)),
			@AttributeOverride(name = "numCiv", column = @Column(name = "NUM_CIV", precision = 5, scale = 0)),
			@AttributeOverride(name = "espCiv", column = @Column(name = "ESP_CIV", length = 12)),
			@AttributeOverride(name = "desInd", column = @Column(name = "DES_IND", length = 60)),
			@AttributeOverride(name = "idCom", column = @Column(name = "ID_COM", precision = 6, scale = 0)),
			@AttributeOverride(name = "desCom", column = @Column(name = "DES_COM", length = 60)),
			@AttributeOverride(name = "cap", column = @Column(name = "CAP", precision = 5, scale = 0)),
			@AttributeOverride(name = "dtIns", column = @Column(name = "DT_INS", length = 7)),
			@AttributeOverride(name = "uteIns", column = @Column(name = "UTE_INS", precision = 6, scale = 0)),
			@AttributeOverride(name = "dtUltMod", column = @Column(name = "DT_ULT_MOD", length = 7)),
			@AttributeOverride(name = "uteUltMod", column = @Column(name = "UTE_ULT_MOD", precision = 6, scale = 0)),
			@AttributeOverride(name = "flgDestCopia", column = @Column(name = "FLG_DEST_COPIA", length = 1)),
			@AttributeOverride(name = "idTpFis", column = @Column(name = "ID_TP_FIS", precision = 4, scale = 0)),
			@AttributeOverride(name = "dtRaccomandata", column = @Column(name = "DT_RACCOMANDATA", length = 7)),
			@AttributeOverride(name = "nroRaccomandata", column = @Column(name = "NRO_RACCOMANDATA", length = 30)),
			@AttributeOverride(name = "costoSped", column = @Column(name = "COSTO_SPED", precision = 11)),
			@AttributeOverride(name = "flgNovisStampe", column = @Column(name = "FLG_NOVIS_STAMPE", length = 1)),
			@AttributeOverride(name = "desAnag", column = @Column(name = "DES_ANAG", length = 150)),
			@AttributeOverride(name = "dtLettera", column = @Column(name = "DT_LETTERA", length = 7)),
			@AttributeOverride(name = "nroLettera", column = @Column(name = "NRO_LETTERA", length = 250)),
			@AttributeOverride(name = "idRichPoste", column = @Column(name = "ID_RICH_POSTE", length = 64)),
			@AttributeOverride(name = "dtTelegramma", column = @Column(name = "DT_TELEGRAMMA", length = 7)) })
	public PttNomdocId getId() {
		return this.id;
	}

	public void setId(PttNomdocId id) {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ANAG", insertable = false, updatable = false)
	public CvtAnagPd getCvtAnagPd() {
		return this.cvtAnagPd;
	}

	public void setCvtAnagPd(CvtAnagPd cvtAnagPd) {
		this.cvtAnagPd = cvtAnagPd;
	}

}
