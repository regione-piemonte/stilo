/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttRepertori generated by hbm2java
 */
@Entity
@Table(name = "PTT_REPERTORI")
public class PttRepertori implements java.io.Serializable {

	private PttRepertoriId id;
	private String desRepertorio;
	private Short anno;
	private Integer progrAnnoPrec;
	private Integer progr;
	private Date dtInizioVld;
	private Date dtFineVld;
	private String flgNoVis;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private String flgModDocRep;
	private String flgDelDocRep;
	private String flgProvvisorio;
	private String flgParticolare;
	private String mantieniProgr;
	private String flgStampaRegProt;
	private Set<PttDocumenti> pttDocumentis = new HashSet<PttDocumenti>(0);
	private Set<PttRepAssociati> pttRepAssociatis = new HashSet<PttRepAssociati>(
			0);

	public PttRepertori() {
	}

	public PttRepertori(PttRepertoriId id, String desRepertorio,
			Date dtInizioVld) {
		this.id = id;
		this.desRepertorio = desRepertorio;
		this.dtInizioVld = dtInizioVld;
	}

	public PttRepertori(PttRepertoriId id, String desRepertorio, Short anno,
			Integer progrAnnoPrec, Integer progr, Date dtInizioVld,
			Date dtFineVld, String flgNoVis, Date dtIns, Integer uteIns,
			Date dtUltMod, Integer uteUltMod, String flgModDocRep,
			String flgDelDocRep, String flgProvvisorio, String flgParticolare,
			String mantieniProgr, String flgStampaRegProt,
			Set<PttDocumenti> pttDocumentis,
			Set<PttRepAssociati> pttRepAssociatis) {
		this.id = id;
		this.desRepertorio = desRepertorio;
		this.anno = anno;
		this.progrAnnoPrec = progrAnnoPrec;
		this.progr = progr;
		this.dtInizioVld = dtInizioVld;
		this.dtFineVld = dtFineVld;
		this.flgNoVis = flgNoVis;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.flgModDocRep = flgModDocRep;
		this.flgDelDocRep = flgDelDocRep;
		this.flgProvvisorio = flgProvvisorio;
		this.flgParticolare = flgParticolare;
		this.mantieniProgr = mantieniProgr;
		this.flgStampaRegProt = flgStampaRegProt;
		this.pttDocumentis = pttDocumentis;
		this.pttRepAssociatis = pttRepAssociatis;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codEnte", column = @Column(name = "COD_ENTE", nullable = false, length = 3)),
			@AttributeOverride(name = "tpRepertorio", column = @Column(name = "TP_REPERTORIO", nullable = false, length = 5)) })
	public PttRepertoriId getId() {
		return this.id;
	}

	public void setId(PttRepertoriId id) {
		this.id = id;
	}

	@Column(name = "DES_REPERTORIO", nullable = false, length = 150)
	public String getDesRepertorio() {
		return this.desRepertorio;
	}

	public void setDesRepertorio(String desRepertorio) {
		this.desRepertorio = desRepertorio;
	}

	@Column(name = "ANNO", precision = 4, scale = 0)
	public Short getAnno() {
		return this.anno;
	}

	public void setAnno(Short anno) {
		this.anno = anno;
	}

	@Column(name = "PROGR_ANNO_PREC", precision = 7, scale = 0)
	public Integer getProgrAnnoPrec() {
		return this.progrAnnoPrec;
	}

	public void setProgrAnnoPrec(Integer progrAnnoPrec) {
		this.progrAnnoPrec = progrAnnoPrec;
	}

	@Column(name = "PROGR", precision = 7, scale = 0)
	public Integer getProgr() {
		return this.progr;
	}

	public void setProgr(Integer progr) {
		this.progr = progr;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INIZIO_VLD", nullable = false, length = 7)
	public Date getDtInizioVld() {
		return this.dtInizioVld;
	}

	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FINE_VLD", length = 7)
	public Date getDtFineVld() {
		return this.dtFineVld;
	}

	public void setDtFineVld(Date dtFineVld) {
		this.dtFineVld = dtFineVld;
	}

	@Column(name = "FLG_NO_VIS", length = 1)
	public String getFlgNoVis() {
		return this.flgNoVis;
	}

	public void setFlgNoVis(String flgNoVis) {
		this.flgNoVis = flgNoVis;
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

	@Column(name = "FLG_MOD_DOC_REP", length = 1)
	public String getFlgModDocRep() {
		return this.flgModDocRep;
	}

	public void setFlgModDocRep(String flgModDocRep) {
		this.flgModDocRep = flgModDocRep;
	}

	@Column(name = "FLG_DEL_DOC_REP", length = 1)
	public String getFlgDelDocRep() {
		return this.flgDelDocRep;
	}

	public void setFlgDelDocRep(String flgDelDocRep) {
		this.flgDelDocRep = flgDelDocRep;
	}

	@Column(name = "FLG_PROVVISORIO", length = 1)
	public String getFlgProvvisorio() {
		return this.flgProvvisorio;
	}

	public void setFlgProvvisorio(String flgProvvisorio) {
		this.flgProvvisorio = flgProvvisorio;
	}

	@Column(name = "FLG_PARTICOLARE", length = 1)
	public String getFlgParticolare() {
		return this.flgParticolare;
	}

	public void setFlgParticolare(String flgParticolare) {
		this.flgParticolare = flgParticolare;
	}

	@Column(name = "MANTIENI_PROGR", length = 1)
	public String getMantieniProgr() {
		return this.mantieniProgr;
	}

	public void setMantieniProgr(String mantieniProgr) {
		this.mantieniProgr = mantieniProgr;
	}

	@Column(name = "FLG_STAMPA_REG_PROT", length = 1)
	public String getFlgStampaRegProt() {
		return this.flgStampaRegProt;
	}

	public void setFlgStampaRegProt(String flgStampaRegProt) {
		this.flgStampaRegProt = flgStampaRegProt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttRepertori")
	public Set<PttDocumenti> getPttDocumentis() {
		return this.pttDocumentis;
	}

	public void setPttDocumentis(Set<PttDocumenti> pttDocumentis) {
		this.pttDocumentis = pttDocumentis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttRepertori")
	public Set<PttRepAssociati> getPttRepAssociatis() {
		return this.pttRepAssociatis;
	}

	public void setPttRepAssociatis(Set<PttRepAssociati> pttRepAssociatis) {
		this.pttRepAssociatis = pttRepAssociatis;
	}

}
