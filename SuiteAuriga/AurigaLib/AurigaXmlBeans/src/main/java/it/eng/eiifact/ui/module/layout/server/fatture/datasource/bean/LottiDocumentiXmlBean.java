/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class LottiDocumentiXmlBean implements Serializable {

	private static final long serialVersionUID = -483078932590126871L;

	@XmlVariabile(nome = "#NomeLotto", tipo = TipoVariabile.SEMPLICE)
	private String nomeLotto;

	@XmlVariabile(nome = "#DescrizioneLotto", tipo = TipoVariabile.SEMPLICE)
	private String descrizioneLotto;

	@XmlVariabile(nome = "#CodStato", tipo = TipoVariabile.SEMPLICE)
	private String codStato;

	@XmlVariabile(nome = "#TsLastUpdStato", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date tsLastUpdStato;

	@XmlVariabile(nome = "#TsStartJob", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date tsStartJob;

	@XmlVariabile(nome = "#TsEndJob", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date tsEndJob;

	@XmlVariabile(nome = "#Note", tipo = TipoVariabile.SEMPLICE)
	private String note;

	@XmlVariabile(nome = "#NrRecTotale", tipo = TipoVariabile.SEMPLICE)
	private String nrRecTotale;

	@XmlVariabile(nome = "#NrRecCaricati", tipo = TipoVariabile.SEMPLICE)
	private String nrRecCaricati;

	@XmlVariabile(nome = "#NrRecErrore", tipo = TipoVariabile.SEMPLICE)
	private String nrRecErrore;

	@XmlVariabile(nome = "#NrRecWarning", tipo = TipoVariabile.SEMPLICE)
	private String nrRecWarning;

	@XmlVariabile(nome = "#NrRecDoppi", tipo = TipoVariabile.SEMPLICE)
	private String nrRecDoppi;

	@XmlVariabile(nome = "#NrRecConsultabili", tipo = TipoVariabile.SEMPLICE)
	private String nrRecConsultabili;

	@XmlVariabile(nome = "#NrFileInIndice", tipo = TipoVariabile.SEMPLICE)
	private String nrFileInIndice;

	@XmlVariabile(nome = "#NrFileRicevuti", tipo = TipoVariabile.SEMPLICE)
	private String nrFileRicevuti;

	@XmlVariabile(nome = "#NrFileAssenti", tipo = TipoVariabile.SEMPLICE)
	private String nrFileAssenti;

	@XmlVariabile(nome = "#NrFileNonDescritti", tipo = TipoVariabile.SEMPLICE)
	private String nrFileNonDescritti;

	@XmlVariabile(nome = "#ProvCiLotto", tipo = TipoVariabile.SEMPLICE)
	private String provCiLotto;

	@XmlVariabile(nome = "#IdUd", tipo = TipoVariabile.SEMPLICE)
	private String idUd;

	@XmlVariabile(nome = "#IdEmailNotitifica", tipo = TipoVariabile.SEMPLICE)
	private String idEmailNotitifica;

	@XmlVariabile(nome = "#FlgHidden", tipo = TipoVariabile.SEMPLICE)
	private String flgHidden;

	@XmlVariabile(nome = "#FlgAnnullato", tipo = TipoVariabile.SEMPLICE)
	private String flgAnnullato;

	@XmlVariabile(nome = "#FlgLocked", tipo = TipoVariabile.SEMPLICE)
	private String flgLocked;

	@XmlVariabile(nome = "#WorkflowState", tipo = TipoVariabile.SEMPLICE)
	private String workflowState;

	
	public String getNomeLotto() {
		return nomeLotto;
	}

	public void setNomeLotto(String nomeLotto) {
		this.nomeLotto = nomeLotto;
	}

	public String getDescrizioneLotto() {
		return descrizioneLotto;
	}

	public void setDescrizioneLotto(String descrizioneLotto) {
		this.descrizioneLotto = descrizioneLotto;
	}

	public String getCodStato() {
		return codStato;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	public Date getTsLastUpdStato() {
		return tsLastUpdStato;
	}

	public void setTsLastUpdStato(Date tsLastUpdStato) {
		this.tsLastUpdStato = tsLastUpdStato;
	}

	public Date getTsStartJob() {
		return tsStartJob;
	}

	public void setTsStartJob(Date tsStartJob) {
		this.tsStartJob = tsStartJob;
	}

	public Date getTsEndJob() {
		return tsEndJob;
	}

	public void setTsEndJob(Date tsEndJob) {
		this.tsEndJob = tsEndJob;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNrRecTotale() {
		return nrRecTotale;
	}

	public void setNrRecTotale(String nrDocTotale) {
		this.nrRecTotale = nrDocTotale;
	}

	public String getNrRecCaricati() {
		return nrRecCaricati;
	}

	public void setNrRecCaricati(String nrDocCaricati) {
		this.nrRecCaricati = nrDocCaricati;
	}

	public String getNrRecErrore() {
		return nrRecErrore;
	}

	public void setNrRecErrore(String nrDocErrore) {
		this.nrRecErrore = nrDocErrore;
	}

	public String getNrRecWarning() {
		return nrRecWarning;
	}

	public void setNrRecWarning(String nrDocWarning) {
		this.nrRecWarning = nrDocWarning;
	}

	public String getNrRecDoppi() {
		return nrRecDoppi;
	}

	public void setNrRecDoppi(String nrDocDoppi) {
		this.nrRecDoppi = nrDocDoppi;
	}

	public String getNrRecConsultabili() {
		return nrRecConsultabili;
	}

	public void setNrRecConsultabili(String nrRecConsultabili) {
		this.nrRecConsultabili = nrRecConsultabili;
	}

	public String getNrFileInIndice() {
		return nrFileInIndice;
	}

	public void setNrFileInIndice(String nrFileInIndice) {
		this.nrFileInIndice = nrFileInIndice;
	}

	public String getNrFileRicevuti() {
		return nrFileRicevuti;
	}

	public void setNrFileRicevuti(String nrFileRicevuti) {
		this.nrFileRicevuti = nrFileRicevuti;
	}

	public String getNrFileAssenti() {
		return nrFileAssenti;
	}

	public void setNrFileAssenti(String nrFileAssenti) {
		this.nrFileAssenti = nrFileAssenti;
	}

	public String getNrFileNonDescritti() {
		return nrFileNonDescritti;
	}

	public void setNrFileNonDescritti(String nrFileNonDescritti) {
		this.nrFileNonDescritti = nrFileNonDescritti;
	}

	public String getProvCiLotto() {
		return provCiLotto;
	}

	public void setProvCiLotto(String provCiLotto) {
		this.provCiLotto = provCiLotto;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getIdEmailNotitifica() {
		return idEmailNotitifica;
	}

	public void setIdEmailNotitifica(String idEmailNotitifica) {
		this.idEmailNotitifica = idEmailNotitifica;
	}

	public String getFlgHidden() {
		return flgHidden;
	}

	public void setFlgHidden(String flgHidden) {
		this.flgHidden = flgHidden;
	}

	public String getFlgAnnullato() {
		return flgAnnullato;
	}

	public void setFlgAnnullato(String flgAnnullato) {
		this.flgAnnullato = flgAnnullato;
	}

	public String getFlgLocked() {
		return flgLocked;
	}

	public void setFlgLocked(String flgLocked) {
		this.flgLocked = flgLocked;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWorkflowState() {
		return workflowState;
	}

	public void setWorkflowState(String workflowState) {
		this.workflowState = workflowState;
	}

}
