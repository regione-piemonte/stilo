/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class BodyEmailMatchListBean implements Serializable {

	private static final long serialVersionUID = -1910052049875680898L;

	@NumeroColonna(numero = "1")
	private String paroleInTesto;

	public String getParoleInTesto() {
		return paroleInTesto;
	}

	public void setParoleInTesto(String paroleInTesto) {
		this.paroleInTesto = paroleInTesto;
	}
	
}
