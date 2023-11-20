/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
	name = "DocumentiDaInviareResult", 
	entities = { 
		@EntityResult(
			entityClass = DocumentiDaInviareResult.class, 
			fields = {
				@FieldResult(name = "codApplOwner",         column = "COD_APPL_OWNER"), 
				@FieldResult(name = "codIstApplOwner",      column = "COD_IST_APPL_OWNER"), 
				@FieldResult(name = "idUd",                 column = "ID_UD"), 
				@FieldResult(name = "idDoc",                column = "ID_DOC"),
				@FieldResult(name = "nroDocumento",         column = "NRO_DOCUMENTO"),
				@FieldResult(name = "tsPubblicazione",      column = "TS_PUBBLICAZIONE"),
				@FieldResult(name = "idLotto",        		column = "ID_LOTTO"), 
				@FieldResult(name = "idDocLotto",       	column = "ID_DOC_LOTTO"), 
				@FieldResult(name = "idEvento",     		column = "ID_EVENTO"), 
				@FieldResult(name = "codEvento",    		column = "COD_EVENTO"),
				@FieldResult(name = "codEsitoEvento",    	column = "COD_ESITO_EVENTO"),
				@FieldResult(name = "descEsitoEvento",     	column = "DESC_ESITO_EVENTO"), 
				@FieldResult(name = "tsEvento",   			column = "TS_EVENTO"),
				@FieldResult(name = "note",     			column = "NOTE"), 
				@FieldResult(name = "idRegProtEmail", 		column = "ID_REG_PROT_EMAIL"), 
				@FieldResult(name = "tsLastUpd",   			column = "TS_LAST_UPD"),
				@FieldResult(name = "idUserLastUpd",   		column = "ID_USER_LAST_UPD")
			}
		)
	}
)
public class DocumentiDaInviareResult implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	private String codApplOwner;
	private String codIstApplOwner;
	private BigDecimal idUd;
	@Id
	private BigDecimal idDoc;
	private String nroDocumento;
	private Date tsPubblicazione;
	private String idLotto;
	private String idDocLotto;
	private BigDecimal idEvento;
	private String codEvento;
	private String codEsitoEvento;
	private String descEsitoEvento;
	private Date tsEvento;
	private String note;
	private String idRegProtEmail;
	private Date tsLastUpd;
	private BigDecimal idUserLastUpd;

	public DocumentiDaInviareResult() {
	}

	

	public DocumentiDaInviareResult(String codApplOwner, String codIstApplOwner, BigDecimal idUd, BigDecimal idDoc,
			String nroDocumento, Date tsPubblicazione, String idLotto, String idDocLotto, BigDecimal idEvento,
			String codEvento, String codEsitoEvento, String descEsitoEvento, Date tsEvento, String note,
			String idRegProtEmail, Date tsLastUpd, BigDecimal idUserLastUpd) {
		this.codApplOwner = codApplOwner;
		this.codIstApplOwner = codIstApplOwner;
		this.idUd = idUd;
		this.idDoc = idDoc;
		this.nroDocumento = nroDocumento;
		this.tsPubblicazione = tsPubblicazione;
		this.idLotto = idLotto;
		this.idDocLotto = idDocLotto;
		this.idEvento = idEvento;
		this.codEvento = codEvento;
		this.codEsitoEvento = codEsitoEvento;
		this.descEsitoEvento = descEsitoEvento;
		this.tsEvento = tsEvento;
		this.note = note;
		this.idRegProtEmail = idRegProtEmail;
		this.tsLastUpd = tsLastUpd;
		this.idUserLastUpd = idUserLastUpd;
	}



	public final String getCodApplOwner() {
		return codApplOwner;
	}



	public final void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}



	public final String getCodIstApplOwner() {
		return codIstApplOwner;
	}



	public final void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
	}



	public final BigDecimal getIdUd() {
		return idUd;
	}



	public final void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}



	public final BigDecimal getIdDoc() {
		return idDoc;
	}



	public final void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}



	public final String getNroDocumento() {
		return nroDocumento;
	}



	public final void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}



	public final Date getTsPubblicazione() {
		return tsPubblicazione;
	}



	public final void setTsPubblicazione(Date tsPubblicazione) {
		this.tsPubblicazione = tsPubblicazione;
	}



	public final String getIdLotto() {
		return idLotto;
	}



	public final void setIdLotto(String idLotto) {
		this.idLotto = idLotto;
	}



	public final String getIdDocLotto() {
		return idDocLotto;
	}



	public final void setIdDocLotto(String idDocLotto) {
		this.idDocLotto = idDocLotto;
	}



	public final BigDecimal getIdEvento() {
		return idEvento;
	}



	public final void setIdEvento(BigDecimal idEvento) {
		this.idEvento = idEvento;
	}



	public final String getCodEvento() {
		return codEvento;
	}



	public final void setCodEvento(String codEvento) {
		this.codEvento = codEvento;
	}



	public final String getCodEsitoEvento() {
		return codEsitoEvento;
	}



	public final void setCodEsitoEvento(String codEsitoEvento) {
		this.codEsitoEvento = codEsitoEvento;
	}



	public final String getDescEsitoEvento() {
		return descEsitoEvento;
	}



	public final void setDescEsitoEvento(String descEsitoEvento) {
		this.descEsitoEvento = descEsitoEvento;
	}



	public final Date getTsEvento() {
		return tsEvento;
	}



	public final void setTsEvento(Date tsEvento) {
		this.tsEvento = tsEvento;
	}



	public final String getNote() {
		return note;
	}



	public final void setNote(String note) {
		this.note = note;
	}



	public final String getIdRegProtEmail() {
		return idRegProtEmail;
	}



	public final void setIdRegProtEmail(String idRegProtEmail) {
		this.idRegProtEmail = idRegProtEmail;
	}



	public final Date getTsLastUpd() {
		return tsLastUpd;
	}



	public final void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}



	public final BigDecimal getIdUserLastUpd() {
		return idUserLastUpd;
	}



	public final void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codApplOwner == null) ? 0 : codApplOwner.hashCode());
		result = prime * result + ((codEsitoEvento == null) ? 0 : codEsitoEvento.hashCode());
		result = prime * result + ((codEvento == null) ? 0 : codEvento.hashCode());
		result = prime * result + ((codIstApplOwner == null) ? 0 : codIstApplOwner.hashCode());
		result = prime * result + ((descEsitoEvento == null) ? 0 : descEsitoEvento.hashCode());
		result = prime * result + ((idDoc == null) ? 0 : idDoc.hashCode());
		result = prime * result + ((idDocLotto == null) ? 0 : idDocLotto.hashCode());
		result = prime * result + ((idEvento == null) ? 0 : idEvento.hashCode());
		result = prime * result + ((idLotto == null) ? 0 : idLotto.hashCode());
		result = prime * result + ((idRegProtEmail == null) ? 0 : idRegProtEmail.hashCode());
		result = prime * result + ((idUd == null) ? 0 : idUd.hashCode());
		result = prime * result + ((idUserLastUpd == null) ? 0 : idUserLastUpd.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((nroDocumento == null) ? 0 : nroDocumento.hashCode());
		result = prime * result + ((tsEvento == null) ? 0 : tsEvento.hashCode());
		result = prime * result + ((tsLastUpd == null) ? 0 : tsLastUpd.hashCode());
		result = prime * result + ((tsPubblicazione == null) ? 0 : tsPubblicazione.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentiDaInviareResult other = (DocumentiDaInviareResult) obj;
		if (codApplOwner == null) {
			if (other.codApplOwner != null)
				return false;
		} else if (!codApplOwner.equals(other.codApplOwner))
			return false;
		if (codEsitoEvento == null) {
			if (other.codEsitoEvento != null)
				return false;
		} else if (!codEsitoEvento.equals(other.codEsitoEvento))
			return false;
		if (codEvento == null) {
			if (other.codEvento != null)
				return false;
		} else if (!codEvento.equals(other.codEvento))
			return false;
		if (codIstApplOwner == null) {
			if (other.codIstApplOwner != null)
				return false;
		} else if (!codIstApplOwner.equals(other.codIstApplOwner))
			return false;
		if (descEsitoEvento == null) {
			if (other.descEsitoEvento != null)
				return false;
		} else if (!descEsitoEvento.equals(other.descEsitoEvento))
			return false;
		if (idDoc == null) {
			if (other.idDoc != null)
				return false;
		} else if (!idDoc.equals(other.idDoc))
			return false;
		if (idDocLotto == null) {
			if (other.idDocLotto != null)
				return false;
		} else if (!idDocLotto.equals(other.idDocLotto))
			return false;
		if (idEvento == null) {
			if (other.idEvento != null)
				return false;
		} else if (!idEvento.equals(other.idEvento))
			return false;
		if (idLotto == null) {
			if (other.idLotto != null)
				return false;
		} else if (!idLotto.equals(other.idLotto))
			return false;
		if (idRegProtEmail == null) {
			if (other.idRegProtEmail != null)
				return false;
		} else if (!idRegProtEmail.equals(other.idRegProtEmail))
			return false;
		if (idUd == null) {
			if (other.idUd != null)
				return false;
		} else if (!idUd.equals(other.idUd))
			return false;
		if (idUserLastUpd == null) {
			if (other.idUserLastUpd != null)
				return false;
		} else if (!idUserLastUpd.equals(other.idUserLastUpd))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (nroDocumento == null) {
			if (other.nroDocumento != null)
				return false;
		} else if (!nroDocumento.equals(other.nroDocumento))
			return false;
		if (tsEvento == null) {
			if (other.tsEvento != null)
				return false;
		} else if (!tsEvento.equals(other.tsEvento))
			return false;
		if (tsLastUpd == null) {
			if (other.tsLastUpd != null)
				return false;
		} else if (!tsLastUpd.equals(other.tsLastUpd))
			return false;
		if (tsPubblicazione == null) {
			if (other.tsPubblicazione != null)
				return false;
		} else if (!tsPubblicazione.equals(other.tsPubblicazione))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return String.format(
				"DocumentiDaInviareResult [codApplOwner=%s, codIstApplOwner=%s, idUd=%s, idDoc=%s, nroDocumento=%s, tsPubblicazione=%s, idLotto=%s, idDocLotto=%s, idEvento=%s, codEvento=%s, codEsitoEvento=%s, descEsitoEvento=%s, tsEvento=%s, note=%s, idRegProtEmail=%s, tsLastUpd=%s, idUserLastUpd=%s]",
				codApplOwner, codIstApplOwner, idUd, idDoc, nroDocumento, tsPubblicazione, idLotto, idDocLotto,
				idEvento, codEvento, codEsitoEvento, descEsitoEvento, tsEvento, note, idRegProtEmail, tsLastUpd,
				idUserLastUpd);
	}



}
