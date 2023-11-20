/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class FirmatarioXmlBean {
	
	@NumeroColonna(numero = "1")
	private String firmatario;

	public String getFirmatario() {
		return firmatario;
	}
	
	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}
	
}