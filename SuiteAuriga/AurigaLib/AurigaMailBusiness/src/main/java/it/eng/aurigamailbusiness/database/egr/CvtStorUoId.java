/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CvtStorUoId generated by hbm2java
 */
@Embeddable
public class CvtStorUoId implements java.io.Serializable {

	private int idUo;
	private Date dtInizioVld;

	public CvtStorUoId() {
	}

	public CvtStorUoId(int idUo, Date dtInizioVld) {
		this.idUo = idUo;
		this.dtInizioVld = dtInizioVld;
	}

	@Column(name = "ID_UO", nullable = false, precision = 8, scale = 0)
	public int getIdUo() {
		return this.idUo;
	}

	public void setIdUo(int idUo) {
		this.idUo = idUo;
	}

	@Column(name = "DT_INIZIO_VLD", nullable = false, length = 7)
	public Date getDtInizioVld() {
		return this.dtInizioVld;
	}

	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CvtStorUoId))
			return false;
		CvtStorUoId castOther = (CvtStorUoId) other;

		return (this.getIdUo() == castOther.getIdUo())
				&& ((this.getDtInizioVld() == castOther.getDtInizioVld()) || (this
						.getDtInizioVld() != null
						&& castOther.getDtInizioVld() != null && this
						.getDtInizioVld().equals(castOther.getDtInizioVld())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdUo();
		result = 37
				* result
				+ (getDtInizioVld() == null ? 0 : this.getDtInizioVld()
						.hashCode());
		return result;
	}

}