/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// // Generated 3-giu-2015 10.18.55 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DmtLottiDocumenti generated by hbm2java
 */
@Entity
@Table(name = "DMT_LOTTI_DOCUMENTI")
public class DmtLottiDocumenti implements java.io.Serializable {

	private static final long serialVersionUID = 5519033433460081362L;

	private String idLotto;

	private BigDecimal idSpAoo;

	private String codApplOwner;

	private String codIstApplOwner;

	private String nomeLotto;

	private String descrizione;

	private String codStato;

	private Date tsLastUpdStato;

	private Date tsStartJob;

	private Date tsEndJob;

	private String note;

	private BigDecimal nroRecTotale;

	private BigDecimal nroRecCaricati;

	private BigDecimal nroRecErrore;

	private BigDecimal nroRecWarning;

	private BigDecimal nroRecConsultabili;

	private BigDecimal nroRecDoppi;

	private BigDecimal nroFileInIndice;

	private BigDecimal nroFileRicevuti;

	private BigDecimal nroFileAssenti;

	private BigDecimal nroFileNonDescritti;

	private String provCiLotto;

	private BigDecimal idUd;

	private Boolean flgHidden;

	private Boolean flgLocked;

	private Boolean flgAnn;

	private Date tsIns;

	private BigDecimal idUserIns;

	private Date tsLastUpd;

	private BigDecimal idUserLastUpd;
	
	private String workflowState; 
	
	private String temporaryPath;

	private Set<DmtDocumentiLotto> dmtDocumentiLottos = new HashSet<DmtDocumentiLotto>(0);

	public DmtLottiDocumenti() {
	}

	public DmtLottiDocumenti(String idLotto, String nomeLotto) {
		this.idLotto = idLotto;
		this.nomeLotto = nomeLotto;
	}

	public DmtLottiDocumenti(String idLotto, BigDecimal idSpAoo, String nomeLotto) {
		this.idLotto = idLotto;
		this.idSpAoo = idSpAoo;
		this.nomeLotto = nomeLotto;
	}

	public DmtLottiDocumenti(String idLotto, BigDecimal idSpAoo, String codApplOwner, String codIstApplOwner, String nomeLotto,
			String descrizione, String codStato, Date tsLastUpdStato, Date tsStartJob, Date tsEndJob, String note, BigDecimal nroRecTotale,
			BigDecimal nroRecCaricati, BigDecimal nroRecErrore, BigDecimal nroRecWarning, BigDecimal nroRecConsultabili, BigDecimal nroRecDoppi,
			BigDecimal nroFileInIndice, BigDecimal nroFileRicevuti, BigDecimal nroFileAssenti, BigDecimal nroFileNonDescritti,
			String provCiLotto, BigDecimal idUd, Boolean flgHidden, Boolean flgLocked, Boolean flgAnn, Date tsIns, BigDecimal idUserIns,
			Date tsLastUpd, BigDecimal idUserLastUpd, Set<DmtDocumentiLotto> dmtDocumentiLottos) {
		this.idLotto = idLotto;
		this.idSpAoo = idSpAoo;
		this.codApplOwner = codApplOwner;
		this.codIstApplOwner = codIstApplOwner;
		this.nomeLotto = nomeLotto;
		this.descrizione = descrizione;
		this.codStato = codStato;
		this.tsLastUpdStato = tsLastUpdStato;
		this.tsStartJob = tsStartJob;
		this.tsEndJob = tsEndJob;
		this.note = note;
		this.nroRecTotale = nroRecTotale;
		this.nroRecCaricati = nroRecCaricati;
		this.nroRecErrore = nroRecErrore;
		this.nroRecWarning = nroRecWarning;
		this.nroRecConsultabili= nroRecConsultabili;
		this.nroRecDoppi = nroRecDoppi;
		this.nroFileInIndice = nroFileInIndice;
		this.nroFileRicevuti = nroFileRicevuti;
		this.nroFileAssenti = nroFileAssenti;
		this.nroFileNonDescritti = nroFileNonDescritti;
		this.provCiLotto = provCiLotto;
		this.idUd = idUd;
		this.flgHidden = flgHidden;
		this.flgLocked = flgLocked;
		this.flgAnn = flgAnn;
		this.tsIns = tsIns;
		this.idUserIns = idUserIns;
		this.tsLastUpd = tsLastUpd;
		this.idUserLastUpd = idUserLastUpd;
		this.dmtDocumentiLottos = dmtDocumentiLottos;
	}

	@Id
	@Column(name = "ID_LOTTO", unique = true, nullable = false, length = 64)
	public String getIdLotto() {
		return this.idLotto;
	}

	public void setIdLotto(String idLotto) {
		this.idLotto = idLotto;
	}

	@Column(name = "ID_SP_AOO", precision = 22, scale = 0)
	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Column(name = "COD_APPL_OWNER", length = 30)
	public String getCodApplOwner() {
		return this.codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	@Column(name = "COD_IST_APPL_OWNER", length = 30)
	public String getCodIstApplOwner() {
		return this.codIstApplOwner;
	}

	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
	}

	@Column(name = "NOME_LOTTO", nullable = false, length = 250)
	public String getNomeLotto() {
		return this.nomeLotto;
	}

	public void setNomeLotto(String nomeLotto) {
		this.nomeLotto = nomeLotto;
	}

	@Column(name = "DESCRIZIONE", length = 4000)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "COD_STATO", length = 10)
	public String getCodStato() {
		return this.codStato;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	@Column(name = "TS_LAST_UPD_STATO", length = 7)
	public Date getTsLastUpdStato() {
		return this.tsLastUpdStato;
	}

	public void setTsLastUpdStato(Date tsLastUpdStato) {
		this.tsLastUpdStato = tsLastUpdStato;
	}

	@Column(name = "TS_START_JOB", length = 7)
	public Date getTsStartJob() {
		return this.tsStartJob;
	}

	public void setTsStartJob(Date tsStartJob) {
		this.tsStartJob = tsStartJob;
	}

	@Column(name = "TS_END_JOB", length = 7)
	public Date getTsEndJob() {
		return this.tsEndJob;
	}

	public void setTsEndJob(Date tsEndJob) {
		this.tsEndJob = tsEndJob;
	}

	@Column(name = "NOTE", length = 1000)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "NRO_REC_TOTALE", precision = 22, scale = 0)
	public BigDecimal getNroRecTotale() {
		return this.nroRecTotale;
	}

	public void setNroRecTotale(BigDecimal nroRecTotale) {
		this.nroRecTotale = nroRecTotale;
	}

	@Column(name = "NRO_REC_CARICATI", precision = 22, scale = 0)
	public BigDecimal getNroRecCaricati() {
		return this.nroRecCaricati;
	}

	public void setNroRecCaricati(BigDecimal nroRecCaricati) {
		this.nroRecCaricati = nroRecCaricati;
	}

	@Column(name = "NRO_REC_ERRORE", precision = 22, scale = 0)
	public BigDecimal getNroRecErrore() {
		return this.nroRecErrore;
	}

	public void setNroRecErrore(BigDecimal nroRecErrore) {
		this.nroRecErrore = nroRecErrore;
	}

	@Column(name = "NRO_REC_WARNING", precision = 22, scale = 0)
	public BigDecimal getNroRecWarning() {
		return this.nroRecWarning;
	}

	public void setNroRecWarning(BigDecimal nroRecWarning) {
		this.nroRecWarning = nroRecWarning;
	}

	@Column(name = "NRO_REC_CONSULTABILI", precision = 22, scale = 0)
	public BigDecimal getNroRecConsultabili() {
		return this.nroRecConsultabili;
	}

	public void setNroRecConsultabili(BigDecimal nroRecConsultabili) {
		this.nroRecConsultabili = nroRecConsultabili;
	}

	@Column(name = "NRO_REC_DOPPI", precision = 22, scale = 0)
	public BigDecimal getNroRecDoppi() {
		return this.nroRecDoppi;
	}

	public void setNroRecDoppi(BigDecimal nroRecDoppi) {
		this.nroRecDoppi = nroRecDoppi;
	}

	@Column(name = "NRO_FILE_IN_INDICE", precision = 22, scale = 0)
	public BigDecimal getNroFileInIndice() {
		return this.nroFileInIndice;
	}

	public void setNroFileInIndice(BigDecimal nroFileInIndice) {
		this.nroFileInIndice = nroFileInIndice;
	}

	@Column(name = "NRO_FILE_RICEVUTI", precision = 22, scale = 0)
	public BigDecimal getNroFileRicevuti() {
		return this.nroFileRicevuti;
	}

	public void setNroFileRicevuti(BigDecimal nroFileRicevuti) {
		this.nroFileRicevuti = nroFileRicevuti;
	}

	@Column(name = "NRO_FILE_ASSENTI", precision = 22, scale = 0)
	public BigDecimal getNroFileAssenti() {
		return this.nroFileAssenti;
	}

	public void setNroFileAssenti(BigDecimal nroFileAssenti) {
		this.nroFileAssenti = nroFileAssenti;
	}

	@Column(name = "NRO_FILE_NON_DESCRITTI", precision = 22, scale = 0)
	public BigDecimal getNroFileNonDescritti() {
		return this.nroFileNonDescritti;
	}

	public void setNroFileNonDescritti(BigDecimal nroFileNonDescritti) {
		this.nroFileNonDescritti = nroFileNonDescritti;
	}

	@Column(name = "PROV_CI_LOTTO", length = 30)
	public String getProvCiLotto() {
		return this.provCiLotto;
	}

	public void setProvCiLotto(String provCiLotto) {
		this.provCiLotto = provCiLotto;
	}

	@Column(name = "ID_UD", precision = 22, scale = 0)
	public BigDecimal getIdUd() {
		return this.idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	@Column(name = "FLG_HIDDEN", precision = 1, scale = 0)
	public Boolean getFlgHidden() {
		return this.flgHidden;
	}

	public void setFlgHidden(Boolean flgHidden) {
		this.flgHidden = flgHidden;
	}

	@Column(name = "FLG_LOCKED", precision = 1, scale = 0)
	public Boolean getFlgLocked() {
		return this.flgLocked;
	}

	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
	}

	@Column(name = "FLG_ANN", precision = 1, scale = 0)
	public Boolean getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(Boolean flgAnn) {
		this.flgAnn = flgAnn;
	}

	@Column(name = "TS_INS", length = 7)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "ID_USER_INS", precision = 22, scale = 0)
	public BigDecimal getIdUserIns() {
		return this.idUserIns;
	}

	public void setIdUserIns(BigDecimal idUserIns) {
		this.idUserIns = idUserIns;
	}

	@Column(name = "TS_LAST_UPD", length = 7)
	public Date getTsLastUpd() {
		return this.tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	@Column(name = "ID_USER_LAST_UPD", precision = 22, scale = 0)
	public BigDecimal getIdUserLastUpd() {
		return this.idUserLastUpd;
	}

	public void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "dmtLottiDocumenti")
	public Set<DmtDocumentiLotto> getDmtDocumentiLottos() {
		return this.dmtDocumentiLottos;
	}

	public void setDmtDocumentiLottos(Set<DmtDocumentiLotto> dmtDocumentiLottos) {
		this.dmtDocumentiLottos = dmtDocumentiLottos;
	}
	
	@Column(name = "WORKFLOW_STATE", length = 8)
	public String getWorkflowState() {
		return workflowState;
	}

	public void setWorkflowState(String workflowState) {
		this.workflowState = workflowState;
	}
	
	@Column(name = "TEMPORARY_PATH")
	public String getTemporaryPath() {
		return temporaryPath;
	}

	public void setTemporaryPath(String temporaryPath) {
		this.temporaryPath = temporaryPath;
	}

	@Override
	public String toString() {
		return String
				.format("DmtLottiDocumenti [idLotto=%s, idSpAoo=%s, codApplOwner=%s, codIstApplOwner=%s, nomeLotto=%s, descrizione=%s, codStato=%s, tsLastUpdStato=%s, tsStartJob=%s, tsEndJob=%s, note=%s, nroRecTotale=%s, nroRecCaricati=%s, nroRecErrore=%s, nroRecWarning=%s, nroRecConsultabili=%s, nroRecDoppi=%s, nroFileInIndice=%s, nroFileRicevuti=%s, nroFileAssenti=%s, nroFileNonDescritti=%s, provCiLotto=%s, idUd=%s, flgHidden=%s, flgLocked=%s, flgAnn=%s, tsIns=%s, idUserIns=%s, tsLastUpd=%s, idUserLastUpd=%s, dmtDocumentiLottos=%s, workFlowState=%s]",
						idLotto, idSpAoo, codApplOwner, codIstApplOwner, nomeLotto, descrizione, codStato, tsLastUpdStato, tsStartJob,
						tsEndJob, note, nroRecTotale, nroRecCaricati, nroRecErrore, nroRecWarning, nroRecConsultabili, nroRecDoppi,
						nroFileInIndice, nroFileRicevuti, nroFileAssenti, nroFileNonDescritti, provCiLotto, idUd, flgHidden, flgLocked,
						flgAnn, tsIns, idUserIns, tsLastUpd, idUserLastUpd, dmtDocumentiLottos, workflowState);
	}

}
