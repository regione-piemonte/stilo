/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttInformAnnId generated by hbm2java
 */
@Embeddable
public class PttInformAnnId implements java.io.Serializable {

	private int idDoc;
	private Date dtAnn;

	public PttInformAnnId() {
	}

	public PttInformAnnId(int idDoc, Date dtAnn) {
		this.idDoc = idDoc;
		this.dtAnn = dtAnn;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "DT_ANN", nullable = false, length = 7)
	public Date getDtAnn() {
		return this.dtAnn;
	}

	public void setDtAnn(Date dtAnn) {
		this.dtAnn = dtAnn;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttInformAnnId))
			return false;
		PttInformAnnId castOther = (PttInformAnnId) other;

		return (this.getIdDoc() == castOther.getIdDoc())
				&& ((this.getDtAnn() == castOther.getDtAnn()) || (this
						.getDtAnn() != null && castOther.getDtAnn() != null && this
						.getDtAnn().equals(castOther.getDtAnn())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdDoc();
		result = 37 * result
				+ (getDtAnn() == null ? 0 : this.getDtAnn().hashCode());
		return result;
	}

}