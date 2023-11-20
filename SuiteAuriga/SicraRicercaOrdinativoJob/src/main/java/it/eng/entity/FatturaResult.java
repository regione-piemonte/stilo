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
	name = "FatturaResult", 
	entities = { 
		@EntityResult(
			entityClass = FatturaResult.class, 
			fields = {
				@FieldResult(name = "idSpAoo",              column = "ID_SP_AOO"), 
				@FieldResult(name = "codApplOwner",         column = "COD_APPL_OWNER"), 
				@FieldResult(name = "codIstApplOwner",      column = "COD_IST_APPL_OWNER"), 
				@FieldResult(name = "idUd",                 column = "ID_UD"), 
				@FieldResult(name = "idDoc",                column = "ID_DOC"),
				@FieldResult(name = "desOgg",               column = "DES_OGG"),
				@FieldResult(name = "uri",                  column = "URI"),
				@FieldResult(name = "displayFilename",      column = "DISPLAY_FILENAME"),
				@FieldResult(name = "codStatoDett",         column = "COD_STATO_DETT"),
				@FieldResult(name = "tsLastUpdStato",       column = "TS_LAST_UPD_STATO")
				//@FieldResult(name = "flgAttivaPassiva",     column = "FLG_ATTIVA_PASSIVA"), 
				//@FieldResult(name = "progrTrasmSdi",        column = "PROGR_TRASM_SDI"), 
				//@FieldResult(name = "idTrasmittente",       column = "ID_TRASMITTENTE"), 
				//@FieldResult(name = "flgInvioInConserv",    column = "FLG_INVIO_IN_CONSERV"),
				//@FieldResult(name = "tsInvioInConserv",     column = "TS_INVIO_IN_CONSERV"), 
				//@FieldResult(name = "errMsgInvioInConserv", column = "ERR_MSG_INVIO_IN_CONSERV"), 
				//@FieldResult(name = "statoConservazione",   column = "STATO_CONSERVAZIONE") 
			}
		)
	}
)
public class FatturaResult implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	private BigDecimal idSpAoo;
	private String codApplOwner;
	private String codIstApplOwner;
	private BigDecimal idUd;
	@Id
	private BigDecimal idDoc;
	private String desOgg;
	private String uri;
	private String displayFilename;
	private String codStatoDett;
	private Date tsLastUpdStato;
//	private String flgAttivaPassiva;
//	private String progrTrasmSdi;
//	private String idTrasmittente;
//	private String flgInvioInConserv;
//	private Date tsInvioInConserv;
//	private String errMsgInvioInConserv;
//	private String statoConservazione;

	public FatturaResult() {
	}

	public FatturaResult(BigDecimal idSpAoo, String codApplOwner, String codIstApplOwner, BigDecimal idUd,
			BigDecimal idDoc, String desOgg, String uri, String displayFilename, String codStatoDett, Date tsLastUpdStato) {
		super();
		this.idSpAoo = idSpAoo;
		this.codApplOwner = codApplOwner;
		this.codIstApplOwner = codIstApplOwner;
		this.idUd = idUd;
		this.idDoc = idDoc;
		this.desOgg = desOgg;
		this.uri = uri;
		this.displayFilename = displayFilename;
		this.codStatoDett = codStatoDett;
		this.tsLastUpdStato = tsLastUpdStato;
	}


	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}
	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
	public String getCodApplOwner() {
		return codApplOwner;
	}
	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public String getCodIstApplOwner() {
		return codIstApplOwner;
	}
	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
	}

	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public BigDecimal getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public String getDesOgg() {
		return desOgg;
	}
	public void setDesOgg(String desOgg) {
		this.desOgg = desOgg;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getDisplayFilename() {
		return displayFilename;
	}
	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getCodStatoDett() {
		return codStatoDett;
	}
	public void setCodStatoDett(String codStatoDett) {
		this.codStatoDett = codStatoDett;
	}
	
	public Date getTsLastUpdStato() {
		return tsLastUpdStato;
	}
	public void setTsLastUpdStato(Date tsLastUpdStato) {
		this.tsLastUpdStato = tsLastUpdStato;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codApplOwner == null) ? 0 : codApplOwner.hashCode());
		result = prime * result + ((codIstApplOwner == null) ? 0 : codIstApplOwner.hashCode());
		result = prime * result + ((codStatoDett == null) ? 0 : codStatoDett.hashCode());
		result = prime * result + ((desOgg == null) ? 0 : desOgg.hashCode());
		result = prime * result + ((displayFilename == null) ? 0 : displayFilename.hashCode());
		result = prime * result + ((idDoc == null) ? 0 : idDoc.hashCode());
		result = prime * result + ((idSpAoo == null) ? 0 : idSpAoo.hashCode());
		result = prime * result + ((idUd == null) ? 0 : idUd.hashCode());
		result = prime * result + ((tsLastUpdStato == null) ? 0 : tsLastUpdStato.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		FatturaResult other = (FatturaResult) obj;
		if (codApplOwner == null) {
			if (other.codApplOwner != null)
				return false;
		} else if (!codApplOwner.equals(other.codApplOwner))
			return false;
		if (codIstApplOwner == null) {
			if (other.codIstApplOwner != null)
				return false;
		} else if (!codIstApplOwner.equals(other.codIstApplOwner))
			return false;
		if (codStatoDett == null) {
			if (other.codStatoDett != null)
				return false;
		} else if (!codStatoDett.equals(other.codStatoDett))
			return false;
		if (desOgg == null) {
			if (other.desOgg != null)
				return false;
		} else if (!desOgg.equals(other.desOgg))
			return false;
		if (displayFilename == null) {
			if (other.displayFilename != null)
				return false;
		} else if (!displayFilename.equals(other.displayFilename))
			return false;
		if (idDoc == null) {
			if (other.idDoc != null)
				return false;
		} else if (!idDoc.equals(other.idDoc))
			return false;
		if (idSpAoo == null) {
			if (other.idSpAoo != null)
				return false;
		} else if (!idSpAoo.equals(other.idSpAoo))
			return false;
		if (idUd == null) {
			if (other.idUd != null)
				return false;
		} else if (!idUd.equals(other.idUd))
			return false;
		if (tsLastUpdStato == null) {
			if (other.tsLastUpdStato != null)
				return false;
		} else if (!tsLastUpdStato.equals(other.tsLastUpdStato))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"FatturaResult [idSpAoo=%s, codApplOwner=%s, codIstApplOwner=%s, idUd=%s, idDoc=%s, desOgg=%s, uri=%s, displayFilename=%s, codStatoDett=%s, tsLastUpdStato=%s]",
				idSpAoo, codApplOwner, codIstApplOwner, idUd, idDoc, desOgg, uri, displayFilename, codStatoDett,
				tsLastUpdStato);
	}

	
}
