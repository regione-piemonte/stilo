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
	name = "DatiDocPerLottoResult", 
	entities = { 
		@EntityResult(
			entityClass = DatiDocPerLottoResult.class, 
			fields = {
				@FieldResult(name = "codApplOwner",		column = "COD_APPL_OWNER"), 
				@FieldResult(name = "codIstApplOwner",	column = "COD_IST_APPL_OWNER"), 
				@FieldResult(name = "idUd",				column = "ID_UD"), 
				@FieldResult(name = "idDoc",			column = "ID_DOC"),
				@FieldResult(name = "dtStesura",		column = "DT_STESURA"), 
				@FieldResult(name = "tsIns",			column = "TS_INS") 
			}
		)
	}
)
public class DatiDocPerLottoResult implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	private String codApplOwner;
	private String codIstApplOwner;
	private BigDecimal idUd;
	@Id
	private BigDecimal idDoc;
	private Date dtStesura;
	private Date tsIns;

	public DatiDocPerLottoResult() {
	}

	public DatiDocPerLottoResult(String codApplOwner, String codIstApplOwner,
			BigDecimal idUd, BigDecimal idDoc, 
			Date dtStesura, Date tsIns) {
		super();
		this.codApplOwner = codApplOwner;
		this.codIstApplOwner = codIstApplOwner;
		this.idUd = idUd;
		this.idDoc = idDoc;
		this.dtStesura = dtStesura;
		this.tsIns = tsIns;
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

	public Date getDtStesura() {
		return dtStesura;
	}
	public void setDtStesura(Date dtStesura) {
		this.dtStesura = dtStesura;
	}
	
	public Date getTsIns() {
		return tsIns;
	}
	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatiDocPerLottoResult other = (DatiDocPerLottoResult) obj;
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
		if (idDoc == null) {
			if (other.idDoc != null)
				return false;
		} else if (!idDoc.equals(other.idDoc))
			return false;
		if (idUd == null) {
			if (other.idUd != null)
				return false;
		} else if (!idUd.equals(other.idUd))
			return false;
		if (dtStesura == null) {
			if (other.dtStesura != null)
				return false;
		} else if (!dtStesura.equals(other.dtStesura))
			return false;
		if (tsIns == null) {
			if (other.tsIns != null)
				return false;
		} else if (!tsIns.equals(other.tsIns))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codApplOwner == null) ? 0 : codApplOwner.hashCode());
		result = prime * result
				+ ((codIstApplOwner == null) ? 0 : codIstApplOwner.hashCode());
		result = prime * result + ((idUd == null) ? 0 : idUd.hashCode());
		result = prime * result + ((idDoc == null) ? 0 : idDoc.hashCode());
		result = prime * result
				+ ((dtStesura == null) ? 0 : dtStesura.hashCode());
		result = prime * result
				+ ((tsIns == null) ? 0 : tsIns.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "DatiDocPerLottoResult [codApplOwner=" + codApplOwner + ", codIstApplOwner=" + codIstApplOwner
				+ ", idUd=" + idUd + ", idDoc=" + idDoc + ", dtStesura=" + dtStesura + ", tsIns=" + tsIns + "]";
	}
}
