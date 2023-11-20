/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class OggettoEmailMatchListBean implements Serializable {

	private static final long serialVersionUID = -8223963944017364803L;

	@NumeroColonna(numero = "1")
	private String paroleInOggetto;

	public String getParoleInOggetto() {
		return paroleInOggetto;
	}

	public void setParoleInOggetto(String paroleInOggetto) {
		this.paroleInOggetto = paroleInOggetto;
	}
	
	
}
