/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

/**
 * 
 * @author denis.bragato
 *
 */
public class ClienteId implements java.io.Serializable {

	private static final long serialVersionUID = -2306906309867877818L;

	private BigDecimal idSpAoo;

	private String ciApplicazione;

	private String ciIstanzaApplicazione;


	public ClienteId() {
	}

	public ClienteId(BigDecimal idSpAoo, String ciApplicazione) {
		this.idSpAoo = idSpAoo;
		this.ciApplicazione = ciApplicazione;
	}

	public ClienteId(BigDecimal idSpAoo, String ciApplicazione, String ciIstanzaApplicazione) {
		this.idSpAoo = idSpAoo;
		this.ciApplicazione = ciApplicazione;
		this.ciIstanzaApplicazione = ciIstanzaApplicazione;
	}

	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	public String getCiApplicazione() {
		return this.ciApplicazione;
	}

	public void setCiApplicazione(String ciApplicazione) {
		this.ciApplicazione = ciApplicazione;
	}

	public String getCiIstanzaApplicazione() {
		return this.ciIstanzaApplicazione;
	}

	public void setCiIstanzaApplicazione(String ciIstanzaApplicazione) {
		this.ciIstanzaApplicazione = ciIstanzaApplicazione;
	}

	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ClienteId))
			return false;
		ClienteId castOther = (ClienteId) other;

		return ((this.getIdSpAoo() == castOther.getIdSpAoo()) || (this.getIdSpAoo() != null && castOther.getIdSpAoo() != null && this
				.getIdSpAoo().equals(castOther.getIdSpAoo())))
				&& ((this.getCiApplicazione() == castOther.getCiApplicazione()) || (this.getCiApplicazione() != null
						&& castOther.getCiApplicazione() != null && this.getCiApplicazione().equals(castOther.getCiApplicazione())))
				&& ((this.getCiIstanzaApplicazione() == castOther.getCiIstanzaApplicazione()) || (this.getCiIstanzaApplicazione() != null
						&& castOther.getCiIstanzaApplicazione() != null && this.getCiIstanzaApplicazione().equals(
						castOther.getCiIstanzaApplicazione())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdSpAoo() == null ? 0 : this.getIdSpAoo().hashCode());
		result = 37 * result + (getCiApplicazione() == null ? 0 : this.getCiApplicazione().hashCode());
		result = 37 * result + (getCiIstanzaApplicazione() == null ? 0 : this.getCiIstanzaApplicazione().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("ClienteId [idSpAoo=%s, ciApplicazione=%s, ciIstanzaApplicazione=%s]", idSpAoo, ciApplicazione,
				ciIstanzaApplicazione);
	}

	public String toShortString() {
		return String.format("[%s:%s:%s]", idSpAoo, ciApplicazione, ciIstanzaApplicazione);
	}

}
