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
	name = "DocumentiDaSistemareResult", 
	entities = { 
		@EntityResult(
			entityClass = DocumentiDaSistemareResult.class, 
			fields = {
				@FieldResult(name = "idSpAoo",			column = "ID_SP_AOO"), 
				@FieldResult(name = "codApplOwner",		column = "COD_APPL_OWNER"), 
				@FieldResult(name = "codIstApplOwner",	column = "COD_IST_APPL_OWNER"), 
				@FieldResult(name = "idUd",				column = "ID_UD"), 
				@FieldResult(name = "idDoc",			column = "ID_DOC"),
				@FieldResult(name = "desOgg",			column = "DES_OGG"),
				@FieldResult(name = "idDocType",		column = "ID_DOC_TYPE"),
				@FieldResult(name = "nomeDocType",		column = "NOME_DOC_TYPE"),
				@FieldResult(name = "codTipoDoc",		column = "ID_DOC_TYPE"),
				@FieldResult(name = "nroProgr",			column = "NRO_PROGR"),
				@FieldResult(name = "displayFilename",	column = "DISPLAY_FILENAME"),
				@FieldResult(name = "mimetype",			column = "MIMETYPE"),
				@FieldResult(name = "rifInRepository",	column = "RIF_IN_REPOSITORY"),
			}
		)
	}
)
public class DocumentiDaSistemareResult implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	private BigDecimal idSpAoo;
	private String codApplOwner;
	private String codIstApplOwner;
	private BigDecimal idUd;
	@Id
	private BigDecimal idDoc;
	private String desOgg;
	private BigDecimal idDocType;
	private String nomeDocType;
	private BigDecimal nroProgr;
	private String displayFilename;
	private String mimetype;
	private String rifInRepository;

	public DocumentiDaSistemareResult() {
	}
	
	public DocumentiDaSistemareResult(
			BigDecimal idSpAoo, String codApplOwner, String codIstApplOwner, 
			BigDecimal idUd, BigDecimal idDoc, String desOgg,
			BigDecimal idDocType, String nomeDocType,
			BigDecimal nroProgr, String displayFilename, String mimetype, String rifInRepository) {
		this.idSpAoo = idSpAoo;
		this.codApplOwner = codApplOwner;
		this.codIstApplOwner = codIstApplOwner;
		this.idUd = idUd;
		this.idDoc = idDoc;
		this.desOgg = desOgg;
		this.idDocType = idDocType;
		this.nomeDocType = nomeDocType;
		this.nroProgr = nroProgr;
		this.displayFilename = displayFilename;
		this.mimetype = mimetype;
		this.rifInRepository = rifInRepository;
	}

	public final BigDecimal getIdSpAoo() {
		return idSpAoo;
	}
	public final void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
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

	public final String getDesOgg() {
		return desOgg;
	}
	public final void setDesOgg(String desOgg) {
		this.desOgg = desOgg;
	}

	public BigDecimal getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(BigDecimal idDocType) {
		this.idDocType = idDocType;
	}
	
	public String getNomeDocType() {
		return nomeDocType;
	}
	public void setNomeDocType(String nomeDocType) {
		this.nomeDocType = nomeDocType;
	}
	
	public final BigDecimal getNroProgr() {
		return nroProgr;
	}
	public final void setNroProgr(BigDecimal nroProgr) {
		this.nroProgr = nroProgr;
	}

	public final String getDisplayFilename() {
		return displayFilename;
	}
	public final void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public final String getMimetype() {
		return mimetype;
	}
	public final void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public final String getRifInRepository() {
		return rifInRepository;
	}
	public final void setRifInRepository(String rifInRepository) {
		this.rifInRepository = rifInRepository;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codApplOwner == null) ? 0 : codApplOwner.hashCode());
		result = prime * result + ((codIstApplOwner == null) ? 0 : codIstApplOwner.hashCode());
		result = prime * result + ((desOgg == null) ? 0 : desOgg.hashCode());
		result = prime * result + ((idDocType == null) ? 0 : idDocType.hashCode());
		result = prime * result + ((nomeDocType == null) ? 0 : nomeDocType.hashCode());
		result = prime * result + ((displayFilename == null) ? 0 : displayFilename.hashCode());
		result = prime * result + ((idDoc == null) ? 0 : idDoc.hashCode());
		result = prime * result + ((idSpAoo == null) ? 0 : idSpAoo.hashCode());
		result = prime * result + ((idUd == null) ? 0 : idUd.hashCode());
		result = prime * result + ((mimetype == null) ? 0 : mimetype.hashCode());
		result = prime * result + ((nroProgr == null) ? 0 : nroProgr.hashCode());
		result = prime * result + ((rifInRepository == null) ? 0 : rifInRepository.hashCode());
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
		DocumentiDaSistemareResult other = (DocumentiDaSistemareResult) obj;
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
		if (desOgg == null) {
			if (other.desOgg != null)
				return false;
		} else if (!desOgg.equals(other.desOgg))
			return false;
		if (idDocType == null) {
			if (other.idDocType != null)
				return false;
		} else if (!idDocType.equals(other.idDocType))
			return false;
		if (nomeDocType == null) {
			if (other.nomeDocType != null)
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
		if (mimetype == null) {
			if (other.mimetype != null)
				return false;
		} else if (!mimetype.equals(other.mimetype))
			return false;
		if (nroProgr == null) {
			if (other.nroProgr != null)
				return false;
		} else if (!nroProgr.equals(other.nroProgr))
			return false;
		if (rifInRepository == null) {
			if (other.rifInRepository != null)
				return false;
		} else if (!rifInRepository.equals(other.rifInRepository))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"DocumentiDaSistemareResult [idSpAoo=%s, codApplOwner=%s, codIstApplOwner=%s, idUd=%s, idDoc=%s, desOgg=%s, idDocType=%s, nomeDocType=%s, nroProgr=%s, displayFilename=%s, mimetype=%s, rifInRepository=%s]",
				idSpAoo, codApplOwner, codIstApplOwner, idUd, idDoc, desOgg, idDocType, nomeDocType, nroProgr,
				displayFilename, mimetype, rifInRepository);
	}

}
