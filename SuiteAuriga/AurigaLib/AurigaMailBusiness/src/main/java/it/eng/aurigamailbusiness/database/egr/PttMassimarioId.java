/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttMassimarioId generated by hbm2java
 */
@Embeddable
public class PttMassimarioId implements java.io.Serializable {

	private int idIndice;
	private Integer idTpDoc;
	private String flgConservPerm;
	private Short anniMinConserv;

	public PttMassimarioId() {
	}

	public PttMassimarioId(int idIndice) {
		this.idIndice = idIndice;
	}

	public PttMassimarioId(int idIndice, Integer idTpDoc,
			String flgConservPerm, Short anniMinConserv) {
		this.idIndice = idIndice;
		this.idTpDoc = idTpDoc;
		this.flgConservPerm = flgConservPerm;
		this.anniMinConserv = anniMinConserv;
	}

	@Column(name = "ID_INDICE", nullable = false, precision = 8, scale = 0)
	public int getIdIndice() {
		return this.idIndice;
	}

	public void setIdIndice(int idIndice) {
		this.idIndice = idIndice;
	}

	@Column(name = "ID_TP_DOC", precision = 5, scale = 0)
	public Integer getIdTpDoc() {
		return this.idTpDoc;
	}

	public void setIdTpDoc(Integer idTpDoc) {
		this.idTpDoc = idTpDoc;
	}

	@Column(name = "FLG_CONSERV_PERM", length = 1)
	public String getFlgConservPerm() {
		return this.flgConservPerm;
	}

	public void setFlgConservPerm(String flgConservPerm) {
		this.flgConservPerm = flgConservPerm;
	}

	@Column(name = "ANNI_MIN_CONSERV", precision = 3, scale = 0)
	public Short getAnniMinConserv() {
		return this.anniMinConserv;
	}

	public void setAnniMinConserv(Short anniMinConserv) {
		this.anniMinConserv = anniMinConserv;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttMassimarioId))
			return false;
		PttMassimarioId castOther = (PttMassimarioId) other;

		return (this.getIdIndice() == castOther.getIdIndice())
				&& ((this.getIdTpDoc() == castOther.getIdTpDoc()) || (this
						.getIdTpDoc() != null && castOther.getIdTpDoc() != null && this
						.getIdTpDoc().equals(castOther.getIdTpDoc())))
				&& ((this.getFlgConservPerm() == castOther.getFlgConservPerm()) || (this
						.getFlgConservPerm() != null
						&& castOther.getFlgConservPerm() != null && this
						.getFlgConservPerm().equals(
								castOther.getFlgConservPerm())))
				&& ((this.getAnniMinConserv() == castOther.getAnniMinConserv()) || (this
						.getAnniMinConserv() != null
						&& castOther.getAnniMinConserv() != null && this
						.getAnniMinConserv().equals(
								castOther.getAnniMinConserv())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdIndice();
		result = 37 * result
				+ (getIdTpDoc() == null ? 0 : this.getIdTpDoc().hashCode());
		result = 37
				* result
				+ (getFlgConservPerm() == null ? 0 : this.getFlgConservPerm()
						.hashCode());
		result = 37
				* result
				+ (getAnniMinConserv() == null ? 0 : this.getAnniMinConserv()
						.hashCode());
		return result;
	}

}