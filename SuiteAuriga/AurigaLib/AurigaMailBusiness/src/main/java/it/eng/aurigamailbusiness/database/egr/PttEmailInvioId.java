/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttEmailInvioId generated by hbm2java
 */
@Embeddable
public class PttEmailInvioId implements java.io.Serializable {

	private int idDoc;
	private int idAnag;
	private BigDecimal idEmail;

	public PttEmailInvioId() {
	}

	public PttEmailInvioId(int idDoc, int idAnag, BigDecimal idEmail) {
		this.idDoc = idDoc;
		this.idAnag = idAnag;
		this.idEmail = idEmail;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "ID_ANAG", nullable = false, precision = 9, scale = 0)
	public int getIdAnag() {
		return this.idAnag;
	}

	public void setIdAnag(int idAnag) {
		this.idAnag = idAnag;
	}

	@Column(name = "ID_EMAIL", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(BigDecimal idEmail) {
		this.idEmail = idEmail;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttEmailInvioId))
			return false;
		PttEmailInvioId castOther = (PttEmailInvioId) other;

		return (this.getIdDoc() == castOther.getIdDoc())
				&& (this.getIdAnag() == castOther.getIdAnag())
				&& ((this.getIdEmail() == castOther.getIdEmail()) || (this
						.getIdEmail() != null && castOther.getIdEmail() != null && this
						.getIdEmail().equals(castOther.getIdEmail())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdDoc();
		result = 37 * result + this.getIdAnag();
		result = 37 * result
				+ (getIdEmail() == null ? 0 : this.getIdEmail().hashCode());
		return result;
	}

}
