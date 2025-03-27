/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean;

import it.eng.document.NumeroColonna;

public class CentroCostoBean {
	
	@NumeroColonna(numero = "1")
	private String denominazioneCdCCdR;

	public String getDenominazioneCdCCdR() {
		return denominazioneCdCCdR;
	}

	public void setDenominazioneCdCCdR(String denominazioneCdCCdR) {
		this.denominazioneCdCCdR = denominazioneCdCCdR;
	}
	
}
