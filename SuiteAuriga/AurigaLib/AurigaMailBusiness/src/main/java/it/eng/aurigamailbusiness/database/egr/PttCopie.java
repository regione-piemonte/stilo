/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * PttCopie generated by hbm2java
 */
@Entity
@Table(name = "PTT_COPIE", uniqueConstraints = {
		@UniqueConstraint(columnNames = "VISIONATA_DA"),
		@UniqueConstraint(columnNames = "LISTA_SETTORI") })
public class PttCopie implements java.io.Serializable {

	private PttCopieId id;
	private PttDocumenti pttDocumenti;
	private PttIndici pttIndici;
	private CvtUtenti cvtUtenti;
	private CvtUo cvtUoByUoAnnCopia;
	private CvtUo cvtUoByUoPrimoAss;
	private CvtUo cvtUoByPostInCarico;
	private CvtUo cvtUoByUoSped;
	private PttTpDoc pttTpDoc;
	private CvtUo cvtUoByUoInCarico;
	private String flgAnn;
	private String flgAcc;
	private String flgCpf;
	private Date dtClass;
	private Integer idFascicolo;
	private Integer numSottofasc;
	private Date dtScarto;
	private String noteScarto;
	private String note;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private Integer settoreInCarico;
	private Serializable listaSettori;
	private Integer servizioInCarico;
	private Integer uocInCarico;
	private Integer uosInCarico;
	private Integer settoreSped;
	private Integer servizioSped;
	private Integer uocSped;
	private Integer uosSped;
	private Date dtSpedEffLast;
	private String flgAnnCopia;
	private String motiviAnnCopia;
	private Date dtAnnCopia;
	private Serializable visionataDa;
	private Short idTpFis;
	private Date dtRaccomandata;
	private String nroRaccomandata;
	private BigDecimal costoSped;
	private String flgNovisStampe;
	private Boolean flgOrig;
	private Set<PttDocInFirma> pttDocInFirmas = new HashSet<PttDocInFirma>(0);
	private Set<PttCopieRichSpost> pttCopieRichSposts = new HashSet<PttCopieRichSpost>(
			0);

	public PttCopie() {
	}

	public PttCopie(PttCopieId id, PttDocumenti pttDocumenti,
			CvtUo cvtUoByUoInCarico) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
		this.cvtUoByUoInCarico = cvtUoByUoInCarico;
	}

	public PttCopie(PttCopieId id, PttDocumenti pttDocumenti,
			PttIndici pttIndici, CvtUtenti cvtUtenti, CvtUo cvtUoByUoAnnCopia,
			CvtUo cvtUoByUoPrimoAss, CvtUo cvtUoByPostInCarico,
			CvtUo cvtUoByUoSped, PttTpDoc pttTpDoc, CvtUo cvtUoByUoInCarico,
			String flgAnn, String flgAcc, String flgCpf, Date dtClass,
			Integer idFascicolo, Integer numSottofasc, Date dtScarto,
			String noteScarto, String note, Date dtIns, Integer uteIns,
			Date dtUltMod, Integer uteUltMod, Integer settoreInCarico,
			Serializable listaSettori, Integer servizioInCarico,
			Integer uocInCarico, Integer uosInCarico, Integer settoreSped,
			Integer servizioSped, Integer uocSped, Integer uosSped,
			Date dtSpedEffLast, String flgAnnCopia, String motiviAnnCopia,
			Date dtAnnCopia, Serializable visionataDa, Short idTpFis,
			Date dtRaccomandata, String nroRaccomandata, BigDecimal costoSped,
			String flgNovisStampe, Boolean flgOrig,
			Set<PttDocInFirma> pttDocInFirmas,
			Set<PttCopieRichSpost> pttCopieRichSposts) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
		this.pttIndici = pttIndici;
		this.cvtUtenti = cvtUtenti;
		this.cvtUoByUoAnnCopia = cvtUoByUoAnnCopia;
		this.cvtUoByUoPrimoAss = cvtUoByUoPrimoAss;
		this.cvtUoByPostInCarico = cvtUoByPostInCarico;
		this.cvtUoByUoSped = cvtUoByUoSped;
		this.pttTpDoc = pttTpDoc;
		this.cvtUoByUoInCarico = cvtUoByUoInCarico;
		this.flgAnn = flgAnn;
		this.flgAcc = flgAcc;
		this.flgCpf = flgCpf;
		this.dtClass = dtClass;
		this.idFascicolo = idFascicolo;
		this.numSottofasc = numSottofasc;
		this.dtScarto = dtScarto;
		this.noteScarto = noteScarto;
		this.note = note;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.settoreInCarico = settoreInCarico;
		this.listaSettori = listaSettori;
		this.servizioInCarico = servizioInCarico;
		this.uocInCarico = uocInCarico;
		this.uosInCarico = uosInCarico;
		this.settoreSped = settoreSped;
		this.servizioSped = servizioSped;
		this.uocSped = uocSped;
		this.uosSped = uosSped;
		this.dtSpedEffLast = dtSpedEffLast;
		this.flgAnnCopia = flgAnnCopia;
		this.motiviAnnCopia = motiviAnnCopia;
		this.dtAnnCopia = dtAnnCopia;
		this.visionataDa = visionataDa;
		this.idTpFis = idTpFis;
		this.dtRaccomandata = dtRaccomandata;
		this.nroRaccomandata = nroRaccomandata;
		this.costoSped = costoSped;
		this.flgNovisStampe = flgNovisStampe;
		this.flgOrig = flgOrig;
		this.pttDocInFirmas = pttDocInFirmas;
		this.pttCopieRichSposts = pttCopieRichSposts;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "numCopia", column = @Column(name = "NUM_COPIA", nullable = false, precision = 4, scale = 0)) })
	public PttCopieId getId() {
		return this.id;
	}

	public void setId(PttCopieId id) {
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
	@JoinColumn(name = "ID_INDICE")
	public PttIndici getPttIndici() {
		return this.pttIndici;
	}

	public void setPttIndici(PttIndici pttIndici) {
		this.pttIndici = pttIndici;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UTE_ANN_COPIA")
	public CvtUtenti getCvtUtenti() {
		return this.cvtUtenti;
	}

	public void setCvtUtenti(CvtUtenti cvtUtenti) {
		this.cvtUtenti = cvtUtenti;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UO_ANN_COPIA")
	public CvtUo getCvtUoByUoAnnCopia() {
		return this.cvtUoByUoAnnCopia;
	}

	public void setCvtUoByUoAnnCopia(CvtUo cvtUoByUoAnnCopia) {
		this.cvtUoByUoAnnCopia = cvtUoByUoAnnCopia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UO_PRIMO_ASS")
	public CvtUo getCvtUoByUoPrimoAss() {
		return this.cvtUoByUoPrimoAss;
	}

	public void setCvtUoByUoPrimoAss(CvtUo cvtUoByUoPrimoAss) {
		this.cvtUoByUoPrimoAss = cvtUoByUoPrimoAss;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POST_IN_CARICO")
	public CvtUo getCvtUoByPostInCarico() {
		return this.cvtUoByPostInCarico;
	}

	public void setCvtUoByPostInCarico(CvtUo cvtUoByPostInCarico) {
		this.cvtUoByPostInCarico = cvtUoByPostInCarico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UO_SPED")
	public CvtUo getCvtUoByUoSped() {
		return this.cvtUoByUoSped;
	}

	public void setCvtUoByUoSped(CvtUo cvtUoByUoSped) {
		this.cvtUoByUoSped = cvtUoByUoSped;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TP_DOC")
	public PttTpDoc getPttTpDoc() {
		return this.pttTpDoc;
	}

	public void setPttTpDoc(PttTpDoc pttTpDoc) {
		this.pttTpDoc = pttTpDoc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UO_IN_CARICO", nullable = false)
	public CvtUo getCvtUoByUoInCarico() {
		return this.cvtUoByUoInCarico;
	}

	public void setCvtUoByUoInCarico(CvtUo cvtUoByUoInCarico) {
		this.cvtUoByUoInCarico = cvtUoByUoInCarico;
	}

	@Column(name = "FLG_ANN", length = 1)
	public String getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(String flgAnn) {
		this.flgAnn = flgAnn;
	}

	@Column(name = "FLG_ACC", length = 1)
	public String getFlgAcc() {
		return this.flgAcc;
	}

	public void setFlgAcc(String flgAcc) {
		this.flgAcc = flgAcc;
	}

	@Column(name = "FLG_CPF", length = 1)
	public String getFlgCpf() {
		return this.flgCpf;
	}

	public void setFlgCpf(String flgCpf) {
		this.flgCpf = flgCpf;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_CLASS", length = 7)
	public Date getDtClass() {
		return this.dtClass;
	}

	public void setDtClass(Date dtClass) {
		this.dtClass = dtClass;
	}

	@Column(name = "ID_FASCICOLO", precision = 8, scale = 0)
	public Integer getIdFascicolo() {
		return this.idFascicolo;
	}

	public void setIdFascicolo(Integer idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Column(name = "NUM_SOTTOFASC", precision = 5, scale = 0)
	public Integer getNumSottofasc() {
		return this.numSottofasc;
	}

	public void setNumSottofasc(Integer numSottofasc) {
		this.numSottofasc = numSottofasc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_SCARTO", length = 7)
	public Date getDtScarto() {
		return this.dtScarto;
	}

	public void setDtScarto(Date dtScarto) {
		this.dtScarto = dtScarto;
	}

	@Column(name = "NOTE_SCARTO", length = 250)
	public String getNoteScarto() {
		return this.noteScarto;
	}

	public void setNoteScarto(String noteScarto) {
		this.noteScarto = noteScarto;
	}

	@Column(name = "NOTE", length = 250)
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

	@Column(name = "SETTORE_IN_CARICO", precision = 5, scale = 0)
	public Integer getSettoreInCarico() {
		return this.settoreInCarico;
	}

	public void setSettoreInCarico(Integer settoreInCarico) {
		this.settoreInCarico = settoreInCarico;
	}

	@Column(name = "LISTA_SETTORI", unique = true)
	public Serializable getListaSettori() {
		return this.listaSettori;
	}

	public void setListaSettori(Serializable listaSettori) {
		this.listaSettori = listaSettori;
	}

	@Column(name = "SERVIZIO_IN_CARICO", precision = 5, scale = 0)
	public Integer getServizioInCarico() {
		return this.servizioInCarico;
	}

	public void setServizioInCarico(Integer servizioInCarico) {
		this.servizioInCarico = servizioInCarico;
	}

	@Column(name = "UOC_IN_CARICO", precision = 5, scale = 0)
	public Integer getUocInCarico() {
		return this.uocInCarico;
	}

	public void setUocInCarico(Integer uocInCarico) {
		this.uocInCarico = uocInCarico;
	}

	@Column(name = "UOS_IN_CARICO", precision = 5, scale = 0)
	public Integer getUosInCarico() {
		return this.uosInCarico;
	}

	public void setUosInCarico(Integer uosInCarico) {
		this.uosInCarico = uosInCarico;
	}

	@Column(name = "SETTORE_SPED", precision = 5, scale = 0)
	public Integer getSettoreSped() {
		return this.settoreSped;
	}

	public void setSettoreSped(Integer settoreSped) {
		this.settoreSped = settoreSped;
	}

	@Column(name = "SERVIZIO_SPED", precision = 5, scale = 0)
	public Integer getServizioSped() {
		return this.servizioSped;
	}

	public void setServizioSped(Integer servizioSped) {
		this.servizioSped = servizioSped;
	}

	@Column(name = "UOC_SPED", precision = 5, scale = 0)
	public Integer getUocSped() {
		return this.uocSped;
	}

	public void setUocSped(Integer uocSped) {
		this.uocSped = uocSped;
	}

	@Column(name = "UOS_SPED", precision = 5, scale = 0)
	public Integer getUosSped() {
		return this.uosSped;
	}

	public void setUosSped(Integer uosSped) {
		this.uosSped = uosSped;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_SPED_EFF_LAST", length = 7)
	public Date getDtSpedEffLast() {
		return this.dtSpedEffLast;
	}

	public void setDtSpedEffLast(Date dtSpedEffLast) {
		this.dtSpedEffLast = dtSpedEffLast;
	}

	@Column(name = "FLG_ANN_COPIA", length = 1)
	public String getFlgAnnCopia() {
		return this.flgAnnCopia;
	}

	public void setFlgAnnCopia(String flgAnnCopia) {
		this.flgAnnCopia = flgAnnCopia;
	}

	@Column(name = "MOTIVI_ANN_COPIA", length = 500)
	public String getMotiviAnnCopia() {
		return this.motiviAnnCopia;
	}

	public void setMotiviAnnCopia(String motiviAnnCopia) {
		this.motiviAnnCopia = motiviAnnCopia;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ANN_COPIA", length = 7)
	public Date getDtAnnCopia() {
		return this.dtAnnCopia;
	}

	public void setDtAnnCopia(Date dtAnnCopia) {
		this.dtAnnCopia = dtAnnCopia;
	}

	@Column(name = "VISIONATA_DA", unique = true)
	public Serializable getVisionataDa() {
		return this.visionataDa;
	}

	public void setVisionataDa(Serializable visionataDa) {
		this.visionataDa = visionataDa;
	}

	@Column(name = "ID_TP_FIS", precision = 4, scale = 0)
	public Short getIdTpFis() {
		return this.idTpFis;
	}

	public void setIdTpFis(Short idTpFis) {
		this.idTpFis = idTpFis;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_RACCOMANDATA", length = 7)
	public Date getDtRaccomandata() {
		return this.dtRaccomandata;
	}

	public void setDtRaccomandata(Date dtRaccomandata) {
		this.dtRaccomandata = dtRaccomandata;
	}

	@Column(name = "NRO_RACCOMANDATA", length = 30)
	public String getNroRaccomandata() {
		return this.nroRaccomandata;
	}

	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}

	@Column(name = "COSTO_SPED", precision = 11)
	public BigDecimal getCostoSped() {
		return this.costoSped;
	}

	public void setCostoSped(BigDecimal costoSped) {
		this.costoSped = costoSped;
	}

	@Column(name = "FLG_NOVIS_STAMPE", length = 1)
	public String getFlgNovisStampe() {
		return this.flgNovisStampe;
	}

	public void setFlgNovisStampe(String flgNovisStampe) {
		this.flgNovisStampe = flgNovisStampe;
	}

	@Column(name = "FLG_ORIG", precision = 1, scale = 0)
	public Boolean getFlgOrig() {
		return this.flgOrig;
	}

	public void setFlgOrig(Boolean flgOrig) {
		this.flgOrig = flgOrig;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttCopie")
	public Set<PttDocInFirma> getPttDocInFirmas() {
		return this.pttDocInFirmas;
	}

	public void setPttDocInFirmas(Set<PttDocInFirma> pttDocInFirmas) {
		this.pttDocInFirmas = pttDocInFirmas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttCopie")
	public Set<PttCopieRichSpost> getPttCopieRichSposts() {
		return this.pttCopieRichSposts;
	}

	public void setPttCopieRichSposts(Set<PttCopieRichSpost> pttCopieRichSposts) {
		this.pttCopieRichSposts = pttCopieRichSposts;
	}

}