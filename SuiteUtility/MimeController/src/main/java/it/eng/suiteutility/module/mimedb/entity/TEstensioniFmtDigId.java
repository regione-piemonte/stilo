package it.eng.suiteutility.module.mimedb.entity;

// Generated 29-gen-2013 12.50.59 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TEstensioniFmtDigId generated by hbm2java
 */
@Embeddable
public class TEstensioniFmtDigId implements java.io.Serializable {

	private String idDigFormat;
	private String estensione;

	public TEstensioniFmtDigId() {
	}

	public TEstensioniFmtDigId(String idDigFormat, String estensione) {
		this.idDigFormat = idDigFormat;
		this.estensione = estensione;
	}

	@Column(name = "ID_DIG_FORMAT", nullable = false, length = 64)
	public String getIdDigFormat() {
		return this.idDigFormat;
	}

	public void setIdDigFormat(String idDigFormat) {
		this.idDigFormat = idDigFormat;
	}

	@Column(name = "ESTENSIONE", nullable = false, length = 5)
	public String getEstensione() {
		return this.estensione;
	}

	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TEstensioniFmtDigId))
			return false;
		TEstensioniFmtDigId castOther = (TEstensioniFmtDigId) other;

		return ((this.getIdDigFormat() == castOther.getIdDigFormat()) || (this
				.getIdDigFormat() != null && castOther.getIdDigFormat() != null && this
				.getIdDigFormat().equals(castOther.getIdDigFormat())))
				&& ((this.getEstensione() == castOther.getEstensione()) || (this
						.getEstensione() != null
						&& castOther.getEstensione() != null && this
						.getEstensione().equals(castOther.getEstensione())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdDigFormat() == null ? 0 : this.getIdDigFormat()
						.hashCode());
		result = 37
				* result
				+ (getEstensione() == null ? 0 : this.getEstensione()
						.hashCode());
		return result;
	}

}
