/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the T_BUILT_SIP database table.
 * 
 */
@Embeddable
public class TBuiltSipPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_BUILT_SIP")
	private String idBuiltSip;

    public TBuiltSipPK() {
    }
	public String getIdBuiltSip() {
		return this.idBuiltSip;
	}
	public void setIdBuiltSip(String idBuiltSip) {
		this.idBuiltSip = idBuiltSip;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TBuiltSipPK)) {
			return false;
		}
		TBuiltSipPK castOther = (TBuiltSipPK) other;
		return this.idBuiltSip.equals(castOther.idBuiltSip);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idBuiltSip.hashCode();

		return hash;
	}
}