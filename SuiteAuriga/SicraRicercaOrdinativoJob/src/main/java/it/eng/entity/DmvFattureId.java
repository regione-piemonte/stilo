/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

//Generated 4-feb-2015 9.30.32 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DmvFattureId generated by hbm2java modificato da denis.bragato
 */
@Embeddable
public class DmvFattureId implements java.io.Serializable {

	private static final long serialVersionUID = -1054320077968735234L;

	private String codApplOwner;

	private String codIstApplOwner;

	private BigDecimal idDoc;

	public DmvFattureId() {
	}

	public DmvFattureId(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public DmvFattureId(String codApplOwner, String codIstApplOwner, BigDecimal idDoc) {
		this.codApplOwner = codApplOwner;
		this.codIstApplOwner = codIstApplOwner;
		this.idDoc = idDoc;
	}

	@Column(name = "COD_APPL_OWNER", length = 30)
	public String getCodApplOwner() {
		return this.codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	@Column(name = "COD_IST_APPL_OWNER", length = 30)
	public String getCodIstApplOwner() {
		return this.codIstApplOwner;
	}

	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DmvFattureId))
			return false;
		DmvFattureId castOther = (DmvFattureId) other;

		return ((this.getCodApplOwner() == castOther.getCodApplOwner()) || (this.getCodApplOwner() != null
				&& castOther.getCodApplOwner() != null && this.getCodApplOwner().equals(castOther.getCodApplOwner())))
				&& ((this.getCodIstApplOwner() == castOther.getCodIstApplOwner()) || (this.getCodIstApplOwner() != null
						&& castOther.getCodIstApplOwner() != null && this.getCodIstApplOwner().equals(castOther.getCodIstApplOwner())))
				&& ((this.getIdDoc() == castOther.getIdDoc()) || (this.getIdDoc() != null && castOther.getIdDoc() != null && this
						.getIdDoc().equals(castOther.getIdDoc())));
	}

	public int hashCode() {
		int result = 3;

		result = 37 * result + (getCodApplOwner() == null ? 0 : this.getCodApplOwner().hashCode());
		result = 37 * result + (getCodIstApplOwner() == null ? 0 : this.getCodIstApplOwner().hashCode());
		result = 37 * result + (getIdDoc() == null ? 0 : this.getIdDoc().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("DmvFattureId [codApplOwner=%s, codIstApplOwner=%s, idDoc=%s]", 
				codApplOwner, codIstApplOwner, idDoc);
	}

}
