/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 2-feb-2017 16.02.31 by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TCapFrazioniId generated by hbm2java
 */
@Embeddable
public class TCapFrazioniId implements java.io.Serializable {

	private static final long serialVersionUID = -7812911692194979368L;
	private String cap;
	private String frazione;
	private String nomeComune;

	public TCapFrazioniId() {
	}

	public TCapFrazioniId(String cap, String frazione, String nomeComune) {
		this.cap = cap;
		this.frazione = frazione;
		this.nomeComune = nomeComune;
	}

	@Column(name = "CAP", nullable = false, length = 5)
	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	@Column(name = "FRAZIONE", nullable = false, length = 50)
	public String getFrazione() {
		return this.frazione;
	}

	public void setFrazione(String frazione) {
		this.frazione = frazione;
	}

	@Column(name = "NOME_COMUNE", nullable = false, length = 50)
	public String getNomeComune() {
		return this.nomeComune;
	}

	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TCapFrazioniId))
			return false;
		TCapFrazioniId castOther = (TCapFrazioniId) other;

		return ((this.getCap() == castOther.getCap()) || (this.getCap() != null && castOther.getCap() != null && this.getCap().equals(castOther.getCap())))
				&& ((this.getFrazione() == castOther.getFrazione())
						|| (this.getFrazione() != null && castOther.getFrazione() != null && this.getFrazione().equals(castOther.getFrazione())))
				&& ((this.getNomeComune() == castOther.getNomeComune())
						|| (this.getNomeComune() != null && castOther.getNomeComune() != null && this.getNomeComune().equals(castOther.getNomeComune())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCap() == null ? 0 : this.getCap().hashCode());
		result = 37 * result + (getFrazione() == null ? 0 : this.getFrazione().hashCode());
		result = 37 * result + (getNomeComune() == null ? 0 : this.getNomeComune().hashCode());
		return result;
	}

}