/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class DocumentiLottoXmlBean implements Serializable {

	private static final long serialVersionUID = -483078932590126871L;

	@XmlVariabile(nome = "#IdDoc", tipo = TipoVariabile.SEMPLICE)
	private String idDoc;

	@XmlVariabile(nome = "#NroDocumento", tipo = TipoVariabile.SEMPLICE)
	private String nroDocumento;

	@XmlVariabile(nome = "#TsDocumento", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date tsDocumento;

	@XmlVariabile(nome = "#TsPubblicazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date tsPubblicazione;

	@XmlVariabile(nome = "#CodStato", tipo = TipoVariabile.SEMPLICE)
	private String codStato;

	@XmlVariabile(nome = "#TsLastUpdStato", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date tsLastUpdStato;

	@XmlVariabile(nome = "#Note", tipo = TipoVariabile.SEMPLICE)
	private String note;

	@XmlVariabile(nome = "#IdUd", tipo = TipoVariabile.SEMPLICE)
	private String idUd;

	@XmlVariabile(nome = "#FlgHidden", tipo = TipoVariabile.SEMPLICE)
	private String flgHidden;

	@XmlVariabile(nome = "#FlgAnnullato", tipo = TipoVariabile.SEMPLICE)
	private String flgAnnullato;

	@XmlVariabile(nome = "#FlgLocked", tipo = TipoVariabile.SEMPLICE)
	private String flgLocked;

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public Date getTsDocumento() {
		return tsDocumento;
	}

	public void setTsDocumento(Date tsDocumento) {
		this.tsDocumento = tsDocumento;
	}

	public Date getTsPubblicazione() {
		return tsPubblicazione;
	}

	public void setTsPubblicazione(Date tsPubblicazione) {
		this.tsPubblicazione = tsPubblicazione;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
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

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

}
