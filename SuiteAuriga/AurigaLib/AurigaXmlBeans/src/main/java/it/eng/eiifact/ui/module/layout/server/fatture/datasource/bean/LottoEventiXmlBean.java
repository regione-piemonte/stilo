/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class LottoEventiXmlBean implements Serializable {

	private static final long serialVersionUID = -483078932590126871L;

	@XmlVariabile(nome = "#IdJob", tipo = TipoVariabile.SEMPLICE)
	private String idJob;

	@XmlVariabile(nome = "#IdLotto", tipo = TipoVariabile.SEMPLICE)
	private String idLotto;

	@XmlVariabile(nome = "#CodEvento", tipo = TipoVariabile.SEMPLICE)
	private String codEvento;

	@XmlVariabile(nome = "#CodEsitoEvento", tipo = TipoVariabile.SEMPLICE)
	private String codEsitoEvento;

	@XmlVariabile(nome = "#DescEsitoEvento", tipo = TipoVariabile.SEMPLICE)
	private String descEsitoEvento;

	@XmlVariabile(nome = "#TsEvento", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date tsEvento;

	@XmlVariabile(nome = "#Note", tipo = TipoVariabile.SEMPLICE)
	private String note;

	@XmlVariabile(nome = "#FlgHidden", tipo = TipoVariabile.SEMPLICE)
	private String flgHidden;

	@XmlVariabile(nome = "#FlgAnnullato", tipo = TipoVariabile.SEMPLICE)
	private String flgAnnullato;

	@XmlVariabile(nome = "#FlgLocked", tipo = TipoVariabile.SEMPLICE)
	private String flgLocked;

	public String getIdJob() {
		return idJob;
	}

	public void setIdJob(String idJob) {
		this.idJob = idJob;
	}

	public String getIdLotto() {
		return idLotto;
	}

	public void setIdLotto(String idLotto) {
		this.idLotto = idLotto;
	}

	public String getCodEvento() {
		return codEvento;
	}

	public void setCodEvento(String codEvento) {
		this.codEvento = codEvento;
	}

	public String getCodEsitoEvento() {
		return codEsitoEvento;
	}

	public void setCodEsitoEvento(String codEsitoEvento) {
		this.codEsitoEvento = codEsitoEvento;
	}

	public String getDescEsitoEvento() {
		return descEsitoEvento;
	}

	public void setDescEsitoEvento(String descEsitoEvento) {
		this.descEsitoEvento = descEsitoEvento;
	}

	public Date getTsEvento() {
		return tsEvento;
	}

	public void setTsEvento(Date tsEvento) {
		this.tsEvento = tsEvento;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
}
