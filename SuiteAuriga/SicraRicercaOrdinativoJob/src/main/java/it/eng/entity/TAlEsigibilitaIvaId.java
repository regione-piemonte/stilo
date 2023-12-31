/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 16-feb-2015 13.08.58 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TAlEsigibilitaIvaId generated by hbm2java
 */
@Embeddable
public class TAlEsigibilitaIvaId implements java.io.Serializable {

	private static final long serialVersionUID = -4013102684763258315L;

	private String idSpAoo;

	private String esigibilitaIva;

	private String natura;

	public TAlEsigibilitaIvaId() {
	}

	public TAlEsigibilitaIvaId(String idSpAoo, String esigibilitaIva, String natura) {
		this.idSpAoo = idSpAoo;
		this.esigibilitaIva = esigibilitaIva;
		this.natura = natura;
	}

	@Column(name = "ID_SP_AOO", nullable = false)
	public String getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Column(name = "ESIGIBILITA_IVA", nullable = false, length = 20)
	public String getEsigibilitaIva() {
		return this.esigibilitaIva;
	}

	public void setEsigibilitaIva(String esigibilitaIva) {
		this.esigibilitaIva = esigibilitaIva;
	}

	@Column(name = "NATURA", nullable = false)
	public String getNatura() {
		return this.natura;
	}

	public void setNatura(String natura) {
		this.natura = natura;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TAlEsigibilitaIvaId))
			return false;
		TAlEsigibilitaIvaId castOther = (TAlEsigibilitaIvaId) other;

		return ((this.getIdSpAoo() == castOther.getIdSpAoo()) || (this.getIdSpAoo() != null && castOther.getIdSpAoo() != null && this
				.getIdSpAoo().equals(castOther.getIdSpAoo())))
				&& ((this.getEsigibilitaIva() == castOther.getEsigibilitaIva()) || (this.getEsigibilitaIva() != null
						&& castOther.getEsigibilitaIva() != null && this.getEsigibilitaIva().equals(castOther.getEsigibilitaIva())))
				&& ((this.getNatura() == castOther.getNatura()) || (this.getNatura() != null && castOther.getNatura() != null && this
						.getNatura().equals(castOther.getNatura())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdSpAoo() == null ? 0 : this.getIdSpAoo().hashCode());
		result = 37 * result + (getEsigibilitaIva() == null ? 0 : this.getEsigibilitaIva().hashCode());
		result = 37 * result + (getNatura() == null ? 0 : this.getNatura().hashCode());
		return result;
	}

}
