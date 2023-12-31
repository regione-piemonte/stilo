/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Generated 15-giu-2018 12.03.29 by Hibernate Tools 3.6.0.Final

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DmtDocLottiElabMassive generated by hbm2java
 */
@Entity
@Table(name = "DMT_DOC_LOTTI_ELAB_MASSIVE")
public class DmtDocLottiElabMassive implements java.io.Serializable {

	private DmtDocLottiElabMassiveId id;
	private DmtLottiDocElabMassive dmtLottiDocElabMassive;
	private String idRichDoc;
	private String stato;
	private String xmlFragment;
	private Date tsValidazione;
	private Date tsInizioElaborazione;
	private Date tsFineElaborazione;
	private Date tsEsitoCompleto;
	private BigDecimal idUd;
	private String idEmail;
	private Date tsIns;
	private Date tsLastUpd;
	private Set<DmtErroriDocLottiElabMass> dmtErroriDocLottiElabMasses = new HashSet<DmtErroriDocLottiElabMass>(0);
	private Set<DmtOperDocLottiElabMass> dmtOperDocLottiElabMasses = new HashSet<DmtOperDocLottiElabMass>(0);

	public DmtDocLottiElabMassive() {
	}

	public DmtDocLottiElabMassive(DmtDocLottiElabMassiveId id, DmtLottiDocElabMassive dmtLottiDocElabMassive, String stato, String xmlFragment,
			Date tsValidazione, Date tsIns, Date tsLastUpd) {
		this.id = id;
		this.dmtLottiDocElabMassive = dmtLottiDocElabMassive;
		this.stato = stato;
		this.xmlFragment = xmlFragment;
		this.tsValidazione = tsValidazione;
		this.tsIns = tsIns;
		this.tsLastUpd = tsLastUpd;
	}

	public DmtDocLottiElabMassive(DmtDocLottiElabMassiveId id, DmtLottiDocElabMassive dmtLottiDocElabMassive, String idRichDoc, String stato,
			String xmlFragment, Date tsValidazione, Date tsInizioElaborazione, Date tsFineElaborazione, Date tsEsitoCompleto, BigDecimal idUd, String idEmail,
			Date tsIns, Date tsLastUpd, Set<DmtErroriDocLottiElabMass> dmtErroriDocLottiElabMasses, Set<DmtOperDocLottiElabMass> dmtOperDocLottiElabMasses) {
		this.id = id;
		this.dmtLottiDocElabMassive = dmtLottiDocElabMassive;
		this.idRichDoc = idRichDoc;
		this.stato = stato;
		this.xmlFragment = xmlFragment;
		this.tsValidazione = tsValidazione;
		this.tsInizioElaborazione = tsInizioElaborazione;
		this.tsFineElaborazione = tsFineElaborazione;
		this.tsEsitoCompleto = tsEsitoCompleto;
		this.idUd = idUd;
		this.idEmail = idEmail;
		this.tsIns = tsIns;
		this.tsLastUpd = tsLastUpd;
		this.dmtErroriDocLottiElabMasses = dmtErroriDocLottiElabMasses;
		this.dmtOperDocLottiElabMasses = dmtOperDocLottiElabMasses;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idJob", column = @Column(name = "ID_JOB", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "nroPosizDoc", column = @Column(name = "NRO_POSIZ_DOC", nullable = false, precision = 22, scale = 0)) })
	public DmtDocLottiElabMassiveId getId() {
		return this.id;
	}

	public void setId(DmtDocLottiElabMassiveId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_JOB", nullable = false, insertable = false, updatable = false)
	public DmtLottiDocElabMassive getDmtLottiDocElabMassive() {
		return this.dmtLottiDocElabMassive;
	}

	public void setDmtLottiDocElabMassive(DmtLottiDocElabMassive dmtLottiDocElabMassive) {
		this.dmtLottiDocElabMassive = dmtLottiDocElabMassive;
	}

	@Column(name = "ID_RICH_DOC", length = 50)
	public String getIdRichDoc() {
		return this.idRichDoc;
	}

	public void setIdRichDoc(String idRichDoc) {
		this.idRichDoc = idRichDoc;
	}

	@Column(name = "STATO", nullable = false, length = 50)
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name = "XML_FRAGMENT", nullable = false)
	public String getXmlFragment() {
		return this.xmlFragment;
	}

	public void setXmlFragment(String xmlFragment) {
		this.xmlFragment = xmlFragment;
	}

	@Column(name = "TS_VALIDAZIONE", nullable = false)
	public Date getTsValidazione() {
		return this.tsValidazione;
	}

	public void setTsValidazione(Date tsValidazione) {
		this.tsValidazione = tsValidazione;
	}

	@Column(name = "TS_INIZIO_ELABORAZIONE")
	public Date getTsInizioElaborazione() {
		return this.tsInizioElaborazione;
	}

	public void setTsInizioElaborazione(Date tsInizioElaborazione) {
		this.tsInizioElaborazione = tsInizioElaborazione;
	}

	@Column(name = "TS_FINE_ELABORAZIONE")
	public Date getTsFineElaborazione() {
		return this.tsFineElaborazione;
	}

	public void setTsFineElaborazione(Date tsFineElaborazione) {
		this.tsFineElaborazione = tsFineElaborazione;
	}

	@Column(name = "TS_ESITO_COMPLETO", length = 7)
	public Date getTsEsitoCompleto() {
		return this.tsEsitoCompleto;
	}

	public void setTsEsitoCompleto(Date tsEsitoCompleto) {
		this.tsEsitoCompleto = tsEsitoCompleto;
	}

	@Column(name = "ID_UD", precision = 22, scale = 0)
	public BigDecimal getIdUd() {
		return this.idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	@Column(name = "ID_EMAIL", length = 64)
	public String getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "TS_LAST_UPD", nullable = false)
	public Date getTsLastUpd() {
		return this.tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dmtDocLottiElabMassive")
	public Set<DmtErroriDocLottiElabMass> getDmtErroriDocLottiElabMasses() {
		return this.dmtErroriDocLottiElabMasses;
	}

	public void setDmtErroriDocLottiElabMasses(Set<DmtErroriDocLottiElabMass> dmtErroriDocLottiElabMasses) {
		this.dmtErroriDocLottiElabMasses = dmtErroriDocLottiElabMasses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dmtDocLottiElabMassive")
	public Set<DmtOperDocLottiElabMass> getDmtOperDocLottiElabMasses() {
		return this.dmtOperDocLottiElabMasses;
	}

	public void setDmtOperDocLottiElabMasses(Set<DmtOperDocLottiElabMass> dmtOperDocLottiElabMasses) {
		this.dmtOperDocLottiElabMasses = dmtOperDocLottiElabMasses;
	}

}
