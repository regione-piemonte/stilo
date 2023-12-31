/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * PttEmail generated by hbm2java
 */
@Entity
@Table(name = "PTT_EMAIL", uniqueConstraints = @UniqueConstraint(columnNames = {
		"MESSAGE_ID", "PEC_RIF" }))
public class PttEmail implements java.io.Serializable {

	private BigDecimal idEmail;
	private PttDocumenti pttDocumenti;
	private PttEmail pttEmail;
	private String mittente;
	private String oggetto;
	private Date dataInvio;
	private String tipoRicevuta;
	private String flgVuoleConferma;
	private String entrataUscita;
	private String codEnte;
	private BigDecimal stato;
	private BigDecimal idDocDm;
	private BigDecimal uoAssegnataria;
	private String listaIdUoAss;
	private Boolean flgProvPec;
	private Blob email;
	private String messageId;
	private Clob destinatari;
	private Clob destinatariCopia;
	private Clob corpo;
	private String statoAttivita;
	private Date dtAssegnazione;
	private Integer uoSmista;
	private Integer utenteSmista;
	private Clob listaSmistamenti;
	private Integer uoInvio;
	private Integer utenteInvio;
	private String pecRif;
	private Integer idUoPecRif;
	private Integer settoreAss;
	private Integer servizioAss;
	private Integer uocAss;
	private Integer uosAss;
	private Integer postazioneAss;
	private BigDecimal idDocDmEmail;
	private Date dtIns;
	private String errmsg;
	private Clob destinatariCcn;
	private Boolean flgSpam;
	private Date lockEmailDt;
	private BigDecimal lockEmailUte;
	private String idDocRegSuccessive;
	private Set<PttEmailAttach> pttEmailAttachs = new HashSet<PttEmailAttach>(0);
	private Set<PttDocInFirma> pttDocInFirmas = new HashSet<PttDocInFirma>(0);
	private PttEmailConferme pttEmailConferme;
	private Set<PttEmail> pttEmails = new HashSet<PttEmail>(0);
	private Set<PttEmailInvio> pttEmailInvios = new HashSet<PttEmailInvio>(0);
	private Set<PttEmailNotifiche> pttEmailNotifiches = new HashSet<PttEmailNotifiche>(
			0);

	public PttEmail() {
	}

	public PttEmail(BigDecimal idEmail, String tipoRicevuta, String codEnte) {
		this.idEmail = idEmail;
		this.tipoRicevuta = tipoRicevuta;
		this.codEnte = codEnte;
	}

	public PttEmail(BigDecimal idEmail, PttDocumenti pttDocumenti,
			PttEmail pttEmail, String mittente, String oggetto, Date dataInvio,
			String tipoRicevuta, String flgVuoleConferma, String entrataUscita,
			String codEnte, BigDecimal stato, BigDecimal idDocDm,
			BigDecimal uoAssegnataria, String listaIdUoAss, Boolean flgProvPec,
			Blob email, String messageId, Clob destinatari,
			Clob destinatariCopia, Clob corpo, String statoAttivita,
			Date dtAssegnazione, Integer uoSmista, Integer utenteSmista,
			Clob listaSmistamenti, Integer uoInvio, Integer utenteInvio,
			String pecRif, Integer idUoPecRif, Integer settoreAss,
			Integer servizioAss, Integer uocAss, Integer uosAss,
			Integer postazioneAss, BigDecimal idDocDmEmail, Date dtIns,
			String errmsg, Clob destinatariCcn, Boolean flgSpam,
			Date lockEmailDt, BigDecimal lockEmailUte,
			String idDocRegSuccessive, Set<PttEmailAttach> pttEmailAttachs,
			Set<PttDocInFirma> pttDocInFirmas,
			PttEmailConferme pttEmailConferme, Set<PttEmail> pttEmails,
			Set<PttEmailInvio> pttEmailInvios,
			Set<PttEmailNotifiche> pttEmailNotifiches) {
		this.idEmail = idEmail;
		this.pttDocumenti = pttDocumenti;
		this.pttEmail = pttEmail;
		this.mittente = mittente;
		this.oggetto = oggetto;
		this.dataInvio = dataInvio;
		this.tipoRicevuta = tipoRicevuta;
		this.flgVuoleConferma = flgVuoleConferma;
		this.entrataUscita = entrataUscita;
		this.codEnte = codEnte;
		this.stato = stato;
		this.idDocDm = idDocDm;
		this.uoAssegnataria = uoAssegnataria;
		this.listaIdUoAss = listaIdUoAss;
		this.flgProvPec = flgProvPec;
		this.email = email;
		this.messageId = messageId;
		this.destinatari = destinatari;
		this.destinatariCopia = destinatariCopia;
		this.corpo = corpo;
		this.statoAttivita = statoAttivita;
		this.dtAssegnazione = dtAssegnazione;
		this.uoSmista = uoSmista;
		this.utenteSmista = utenteSmista;
		this.listaSmistamenti = listaSmistamenti;
		this.uoInvio = uoInvio;
		this.utenteInvio = utenteInvio;
		this.pecRif = pecRif;
		this.idUoPecRif = idUoPecRif;
		this.settoreAss = settoreAss;
		this.servizioAss = servizioAss;
		this.uocAss = uocAss;
		this.uosAss = uosAss;
		this.postazioneAss = postazioneAss;
		this.idDocDmEmail = idDocDmEmail;
		this.dtIns = dtIns;
		this.errmsg = errmsg;
		this.destinatariCcn = destinatariCcn;
		this.flgSpam = flgSpam;
		this.lockEmailDt = lockEmailDt;
		this.lockEmailUte = lockEmailUte;
		this.idDocRegSuccessive = idDocRegSuccessive;
		this.pttEmailAttachs = pttEmailAttachs;
		this.pttDocInFirmas = pttDocInFirmas;
		this.pttEmailConferme = pttEmailConferme;
		this.pttEmails = pttEmails;
		this.pttEmailInvios = pttEmailInvios;
		this.pttEmailNotifiches = pttEmailNotifiches;
	}

	@Id
	@Column(name = "ID_EMAIL", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(BigDecimal idEmail) {
		this.idEmail = idEmail;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOC")
	public PttDocumenti getPttDocumenti() {
		return this.pttDocumenti;
	}

	public void setPttDocumenti(PttDocumenti pttDocumenti) {
		this.pttDocumenti = pttDocumenti;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMAIL_PADRE")
	public PttEmail getPttEmail() {
		return this.pttEmail;
	}

	public void setPttEmail(PttEmail pttEmail) {
		this.pttEmail = pttEmail;
	}

	@Column(name = "MITTENTE", length = 1000)
	public String getMittente() {
		return this.mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	@Column(name = "OGGETTO", length = 4000)
	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_INVIO", length = 7)
	public Date getDataInvio() {
		return this.dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	@Column(name = "TIPO_RICEVUTA", nullable = false, length = 1)
	public String getTipoRicevuta() {
		return this.tipoRicevuta;
	}

	public void setTipoRicevuta(String tipoRicevuta) {
		this.tipoRicevuta = tipoRicevuta;
	}

	@Column(name = "FLG_VUOLE_CONFERMA", length = 1)
	public String getFlgVuoleConferma() {
		return this.flgVuoleConferma;
	}

	public void setFlgVuoleConferma(String flgVuoleConferma) {
		this.flgVuoleConferma = flgVuoleConferma;
	}

	@Column(name = "ENTRATA_USCITA", length = 1)
	public String getEntrataUscita() {
		return this.entrataUscita;
	}

	public void setEntrataUscita(String entrataUscita) {
		this.entrataUscita = entrataUscita;
	}

	@Column(name = "COD_ENTE", nullable = false, length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@Column(name = "STATO", precision = 22, scale = 0)
	public BigDecimal getStato() {
		return this.stato;
	}

	public void setStato(BigDecimal stato) {
		this.stato = stato;
	}

	@Column(name = "ID_DOC_DM", precision = 22, scale = 0)
	public BigDecimal getIdDocDm() {
		return this.idDocDm;
	}

	public void setIdDocDm(BigDecimal idDocDm) {
		this.idDocDm = idDocDm;
	}

	@Column(name = "UO_ASSEGNATARIA", precision = 22, scale = 0)
	public BigDecimal getUoAssegnataria() {
		return this.uoAssegnataria;
	}

	public void setUoAssegnataria(BigDecimal uoAssegnataria) {
		this.uoAssegnataria = uoAssegnataria;
	}

	@Column(name = "LISTA_ID_UO_ASS", length = 1000)
	public String getListaIdUoAss() {
		return this.listaIdUoAss;
	}

	public void setListaIdUoAss(String listaIdUoAss) {
		this.listaIdUoAss = listaIdUoAss;
	}

	@Column(name = "FLG_PROV_PEC", precision = 1, scale = 0)
	public Boolean getFlgProvPec() {
		return this.flgProvPec;
	}

	public void setFlgProvPec(Boolean flgProvPec) {
		this.flgProvPec = flgProvPec;
	}

	@Column(name = "EMAIL")
	public Blob getEmail() {
		return this.email;
	}

	public void setEmail(Blob email) {
		this.email = email;
	}

	@Column(name = "MESSAGE_ID", length = 500)
	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Column(name = "DESTINATARI")
	public Clob getDestinatari() {
		return this.destinatari;
	}

	public void setDestinatari(Clob destinatari) {
		this.destinatari = destinatari;
	}

	@Column(name = "DESTINATARI_COPIA")
	public Clob getDestinatariCopia() {
		return this.destinatariCopia;
	}

	public void setDestinatariCopia(Clob destinatariCopia) {
		this.destinatariCopia = destinatariCopia;
	}

	@Column(name = "CORPO")
	public Clob getCorpo() {
		return this.corpo;
	}

	public void setCorpo(Clob corpo) {
		this.corpo = corpo;
	}

	@Column(name = "STATO_ATTIVITA", length = 1)
	public String getStatoAttivita() {
		return this.statoAttivita;
	}

	public void setStatoAttivita(String statoAttivita) {
		this.statoAttivita = statoAttivita;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ASSEGNAZIONE", length = 7)
	public Date getDtAssegnazione() {
		return this.dtAssegnazione;
	}

	public void setDtAssegnazione(Date dtAssegnazione) {
		this.dtAssegnazione = dtAssegnazione;
	}

	@Column(name = "UO_SMISTA", precision = 8, scale = 0)
	public Integer getUoSmista() {
		return this.uoSmista;
	}

	public void setUoSmista(Integer uoSmista) {
		this.uoSmista = uoSmista;
	}

	@Column(name = "UTENTE_SMISTA", precision = 6, scale = 0)
	public Integer getUtenteSmista() {
		return this.utenteSmista;
	}

	public void setUtenteSmista(Integer utenteSmista) {
		this.utenteSmista = utenteSmista;
	}

	@Column(name = "LISTA_SMISTAMENTI")
	public Clob getListaSmistamenti() {
		return this.listaSmistamenti;
	}

	public void setListaSmistamenti(Clob listaSmistamenti) {
		this.listaSmistamenti = listaSmistamenti;
	}

	@Column(name = "UO_INVIO", precision = 8, scale = 0)
	public Integer getUoInvio() {
		return this.uoInvio;
	}

	public void setUoInvio(Integer uoInvio) {
		this.uoInvio = uoInvio;
	}

	@Column(name = "UTENTE_INVIO", precision = 6, scale = 0)
	public Integer getUtenteInvio() {
		return this.utenteInvio;
	}

	public void setUtenteInvio(Integer utenteInvio) {
		this.utenteInvio = utenteInvio;
	}

	@Column(name = "PEC_RIF", length = 250)
	public String getPecRif() {
		return this.pecRif;
	}

	public void setPecRif(String pecRif) {
		this.pecRif = pecRif;
	}

	@Column(name = "ID_UO_PEC_RIF", precision = 8, scale = 0)
	public Integer getIdUoPecRif() {
		return this.idUoPecRif;
	}

	public void setIdUoPecRif(Integer idUoPecRif) {
		this.idUoPecRif = idUoPecRif;
	}

	@Column(name = "SETTORE_ASS", precision = 5, scale = 0)
	public Integer getSettoreAss() {
		return this.settoreAss;
	}

	public void setSettoreAss(Integer settoreAss) {
		this.settoreAss = settoreAss;
	}

	@Column(name = "SERVIZIO_ASS", precision = 5, scale = 0)
	public Integer getServizioAss() {
		return this.servizioAss;
	}

	public void setServizioAss(Integer servizioAss) {
		this.servizioAss = servizioAss;
	}

	@Column(name = "UOC_ASS", precision = 5, scale = 0)
	public Integer getUocAss() {
		return this.uocAss;
	}

	public void setUocAss(Integer uocAss) {
		this.uocAss = uocAss;
	}

	@Column(name = "UOS_ASS", precision = 5, scale = 0)
	public Integer getUosAss() {
		return this.uosAss;
	}

	public void setUosAss(Integer uosAss) {
		this.uosAss = uosAss;
	}

	@Column(name = "POSTAZIONE_ASS", precision = 5, scale = 0)
	public Integer getPostazioneAss() {
		return this.postazioneAss;
	}

	public void setPostazioneAss(Integer postazioneAss) {
		this.postazioneAss = postazioneAss;
	}

	@Column(name = "ID_DOC_DM_EMAIL", precision = 22, scale = 0)
	public BigDecimal getIdDocDmEmail() {
		return this.idDocDmEmail;
	}

	public void setIdDocDmEmail(BigDecimal idDocDmEmail) {
		this.idDocDmEmail = idDocDmEmail;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS", length = 7)
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	@Column(name = "ERRMSG", length = 4000)
	public String getErrmsg() {
		return this.errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	@Column(name = "DESTINATARI_CCN")
	public Clob getDestinatariCcn() {
		return this.destinatariCcn;
	}

	public void setDestinatariCcn(Clob destinatariCcn) {
		this.destinatariCcn = destinatariCcn;
	}

	@Column(name = "FLG_SPAM", precision = 1, scale = 0)
	public Boolean getFlgSpam() {
		return this.flgSpam;
	}

	public void setFlgSpam(Boolean flgSpam) {
		this.flgSpam = flgSpam;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LOCK_EMAIL_DT", length = 7)
	public Date getLockEmailDt() {
		return this.lockEmailDt;
	}

	public void setLockEmailDt(Date lockEmailDt) {
		this.lockEmailDt = lockEmailDt;
	}

	@Column(name = "LOCK_EMAIL_UTE", precision = 22, scale = 0)
	public BigDecimal getLockEmailUte() {
		return this.lockEmailUte;
	}

	public void setLockEmailUte(BigDecimal lockEmailUte) {
		this.lockEmailUte = lockEmailUte;
	}

	@Column(name = "ID_DOC_REG_SUCCESSIVE", length = 500)
	public String getIdDocRegSuccessive() {
		return this.idDocRegSuccessive;
	}

	public void setIdDocRegSuccessive(String idDocRegSuccessive) {
		this.idDocRegSuccessive = idDocRegSuccessive;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttEmail")
	public Set<PttEmailAttach> getPttEmailAttachs() {
		return this.pttEmailAttachs;
	}

	public void setPttEmailAttachs(Set<PttEmailAttach> pttEmailAttachs) {
		this.pttEmailAttachs = pttEmailAttachs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttEmail")
	public Set<PttDocInFirma> getPttDocInFirmas() {
		return this.pttDocInFirmas;
	}

	public void setPttDocInFirmas(Set<PttDocInFirma> pttDocInFirmas) {
		this.pttDocInFirmas = pttDocInFirmas;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pttEmail")
	public PttEmailConferme getPttEmailConferme() {
		return this.pttEmailConferme;
	}

	public void setPttEmailConferme(PttEmailConferme pttEmailConferme) {
		this.pttEmailConferme = pttEmailConferme;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttEmail")
	public Set<PttEmail> getPttEmails() {
		return this.pttEmails;
	}

	public void setPttEmails(Set<PttEmail> pttEmails) {
		this.pttEmails = pttEmails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttEmail")
	public Set<PttEmailInvio> getPttEmailInvios() {
		return this.pttEmailInvios;
	}

	public void setPttEmailInvios(Set<PttEmailInvio> pttEmailInvios) {
		this.pttEmailInvios = pttEmailInvios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pttEmail")
	public Set<PttEmailNotifiche> getPttEmailNotifiches() {
		return this.pttEmailNotifiches;
	}

	public void setPttEmailNotifiches(Set<PttEmailNotifiche> pttEmailNotifiches) {
		this.pttEmailNotifiches = pttEmailNotifiches;
	}

}
