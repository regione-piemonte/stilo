/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Generated 16-ott-2020 19.10.48 by Hibernate Tools 3.6.0.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DmtCodaProcWfToStartId generated by hbm2java
 */
@Embeddable
public class DmtCodaProcWfToStartId implements java.io.Serializable {

	private String tyObj;
	private BigDecimal idObj;

	public DmtCodaProcWfToStartId() {
	}

	public DmtCodaProcWfToStartId(String tyObj, BigDecimal idObj) {
		this.tyObj = tyObj;
		this.idObj = idObj;
	}

	@Column(name = "TY_OBJ", nullable = false, length = 1)
	public String getTyObj() {
		return this.tyObj;
	}

	public void setTyObj(String tyObj) {
		this.tyObj = tyObj;
	}

	@Column(name = "ID_OBJ", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdObj() {
		return this.idObj;
	}

	public void setIdObj(BigDecimal idObj) {
		this.idObj = idObj;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DmtCodaProcWfToStartId))
			return false;
		DmtCodaProcWfToStartId castOther = (DmtCodaProcWfToStartId) other;

		return ((this.getTyObj() == castOther.getTyObj()) || (this.getTyObj() != null && castOther.getTyObj() != null
				&& this.getTyObj().equals(castOther.getTyObj())))
				&& ((this.getIdObj() == castOther.getIdObj()) || (this.getIdObj() != null
						&& castOther.getIdObj() != null && this.getIdObj().equals(castOther.getIdObj())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTyObj() == null ? 0 : this.getTyObj().hashCode());
		result = 37 * result + (getIdObj() == null ? 0 : this.getIdObj().hashCode());
		return result;
	}

}