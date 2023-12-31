/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 17-giu-2015 11.14.10 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DmtSezionaliId generated by hbm2java
 */
@Embeddable
public class DmtSezionaliId implements java.io.Serializable {

	private static final long serialVersionUID = -5993304593963157168L;

	private BigDecimal idSezionale;

	private BigDecimal idSpAoo;

	private String codSezionale;

	private BigDecimal anno;

	public DmtSezionaliId() {
	}

	public DmtSezionaliId(BigDecimal idSezionale, BigDecimal idSpAoo, String codSezionale, BigDecimal anno) {
		this.idSezionale = idSezionale;
		this.idSpAoo = idSpAoo;
		this.codSezionale = codSezionale;
		this.anno = anno;
	}

	@Column(name = "ID_SEZIONALE", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdSezionale() {
		return this.idSezionale;
	}

	public void setIdSezionale(BigDecimal idSezionale) {
		this.idSezionale = idSezionale;
	}

	@Column(name = "ID_SP_AOO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Column(name = "COD_SEZIONALE", nullable = false, length = 5)
	public String getCodSezionale() {
		return this.codSezionale;
	}

	public void setCodSezionale(String codSezionale) {
		this.codSezionale = codSezionale;
	}

	@Column(name = "ANNO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DmtSezionaliId))
			return false;
		DmtSezionaliId castOther = (DmtSezionaliId) other;

		return ((this.getIdSpAoo() == castOther.getIdSpAoo()) || (this.getIdSpAoo() != null && castOther.getIdSpAoo() != null && this
				.getIdSpAoo().equals(castOther.getIdSpAoo())))
				&& ((this.getIdSezionale() == castOther.getIdSezionale()) || (this.getIdSezionale() != null
						&& castOther.getIdSezionale() != null && this.getIdSezionale().equals(castOther.getIdSezionale())))
				&& ((this.getCodSezionale() == castOther.getCodSezionale()) || (this.getCodSezionale() != null
						&& castOther.getCodSezionale() != null && this.getCodSezionale().equals(castOther.getCodSezionale())))
				&& ((this.getAnno() == castOther.getAnno()) || (this.getAnno() != null && castOther.getAnno() != null && this.getAnno()
						.equals(castOther.getAnno())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdSpAoo() == null ? 0 : this.getIdSpAoo().hashCode());
		result = 37 * result + (getIdSezionale() == null ? 0 : this.getIdSezionale().hashCode());
		result = 37 * result + (getCodSezionale() == null ? 0 : this.getCodSezionale().hashCode());
		result = 37 * result + (getAnno() == null ? 0 : this.getAnno().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("DmtSezionaliId [idSezionale=%s, idSpAoo=%s, codSezionale=%s, anno=%s]", idSezionale, idSpAoo, codSezionale,
				anno);
	}

}
