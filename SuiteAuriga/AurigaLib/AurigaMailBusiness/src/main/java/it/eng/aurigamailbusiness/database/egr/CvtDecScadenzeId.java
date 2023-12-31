/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CvtDecScadenzeId generated by hbm2java
 */
@Embeddable
public class CvtDecScadenzeId implements java.io.Serializable {

	private String codTpApplic;
	private String codTpScadenza;

	public CvtDecScadenzeId() {
	}

	public CvtDecScadenzeId(String codTpApplic, String codTpScadenza) {
		this.codTpApplic = codTpApplic;
		this.codTpScadenza = codTpScadenza;
	}

	@Column(name = "COD_TP_APPLIC", nullable = false, length = 1)
	public String getCodTpApplic() {
		return this.codTpApplic;
	}

	public void setCodTpApplic(String codTpApplic) {
		this.codTpApplic = codTpApplic;
	}

	@Column(name = "COD_TP_SCADENZA", nullable = false, length = 3)
	public String getCodTpScadenza() {
		return this.codTpScadenza;
	}

	public void setCodTpScadenza(String codTpScadenza) {
		this.codTpScadenza = codTpScadenza;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CvtDecScadenzeId))
			return false;
		CvtDecScadenzeId castOther = (CvtDecScadenzeId) other;

		return ((this.getCodTpApplic() == castOther.getCodTpApplic()) || (this
				.getCodTpApplic() != null && castOther.getCodTpApplic() != null && this
				.getCodTpApplic().equals(castOther.getCodTpApplic())))
				&& ((this.getCodTpScadenza() == castOther.getCodTpScadenza()) || (this
						.getCodTpScadenza() != null
						&& castOther.getCodTpScadenza() != null && this
						.getCodTpScadenza()
						.equals(castOther.getCodTpScadenza())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCodTpApplic() == null ? 0 : this.getCodTpApplic()
						.hashCode());
		result = 37
				* result
				+ (getCodTpScadenza() == null ? 0 : this.getCodTpScadenza()
						.hashCode());
		return result;
	}

}
