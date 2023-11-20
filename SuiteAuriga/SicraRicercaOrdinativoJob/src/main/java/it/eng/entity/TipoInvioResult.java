/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
	name = "TipoInvioResult", 
	entities = { 
		@EntityResult(
			entityClass = TipoInvioResult.class, 
			fields = {
				@FieldResult(name = "idUd", column = "ID_UD"), 
				@FieldResult(name = "tipoInvioDefault", column = "TIPO_INVIO_DEFAULT"), 
				@FieldResult(name = "invioDaApplicare", column = "INVIO_DA_APPLICARE"), 
				@FieldResult(name = "codStatoDett", column = "COD_STATO_DETT") 
			}
		)
	}
)
public class TipoInvioResult implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	private BigDecimal idUd;
	private String tipoInvioDefault;
	private String invioDaApplicare;
	private String codStatoDett;

	public TipoInvioResult() {
	}

	public TipoInvioResult(BigDecimal idUd, String tipoInvioDefault, String invioDaApplicare, String codStatoDett) {
		this.idUd = idUd;
		this.tipoInvioDefault = tipoInvioDefault;
		this.invioDaApplicare = invioDaApplicare;
		this.codStatoDett = codStatoDett;
	}

	@Id
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	
	public String getTipoInvioDefault() {
		return this.tipoInvioDefault;
	}
	public void setTipoInvioDefault(String tipoInvioDefault) {
		this.tipoInvioDefault = tipoInvioDefault;
	}

	public String getInvioDaApplicare() {
		return this.invioDaApplicare;
	}
	public void setInvioDaApplicare(String invioDaApplicare) {
		this.invioDaApplicare = invioDaApplicare;
	}
	
	public String getCodStatoDett() {
		return codStatoDett;
	}
	public void setCodStatoDett(String codStatoDett) {
		this.codStatoDett = codStatoDett;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TipoInvioResult))
			return false;
		TipoInvioResult castOther = (TipoInvioResult) other;

		return ((this.getIdUd() == castOther.getIdUd()) || (this.getIdUd() != null && castOther.getIdUd() != null && this.getIdUd().equals(
				castOther.getIdUd())))
				&& ((this.getTipoInvioDefault() == castOther.getTipoInvioDefault()) || (this.getTipoInvioDefault() != null
						&& castOther.getTipoInvioDefault() != null && this.getTipoInvioDefault().equals(castOther.getTipoInvioDefault())))
				&& ((this.getInvioDaApplicare() == castOther.getInvioDaApplicare()) || (this.getInvioDaApplicare() != null
						&& castOther.getInvioDaApplicare() != null && this.getInvioDaApplicare().equals(castOther.getInvioDaApplicare())))
				&& ((this.getCodStatoDett() == castOther.getCodStatoDett()) || (this.getCodStatoDett() != null
						&& castOther.getCodStatoDett() != null && this.getCodStatoDett().equals(castOther.getCodStatoDett())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdUd() == null ? 0 : this.getIdUd().hashCode());
		result = 37
				* result
				+ (getTipoInvioDefault() == null ? 0 : this.getTipoInvioDefault().hashCode());
		result = 37
				* result
				+ (getInvioDaApplicare() == null ? 0 : this.getInvioDaApplicare().hashCode());
		result = 37
				* result
				+ (getCodStatoDett() == null ? 0 : this.getCodStatoDett().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("TipoInvioResult [idUd=%s, tipoInvioDefault=%s, invioDaApplicare=%s, codStatoDett=%s]", idUd,
				tipoInvioDefault, invioDaApplicare, codStatoDett);
	}
}
