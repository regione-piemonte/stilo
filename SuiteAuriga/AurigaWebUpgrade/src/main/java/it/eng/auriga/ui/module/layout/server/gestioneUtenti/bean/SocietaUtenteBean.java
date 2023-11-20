/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class SocietaUtenteBean {

	@NumeroColonna(numero="1")
	private String idSocieta;
	
	@NumeroColonna(numero="2")
	private String denominazioneSocieta;

	public String getIdSocieta() {
		return idSocieta;
	}

	public String getDenominazioneSocieta() {
		return denominazioneSocieta;
	}

	public void setIdSocieta(String idSocieta) {
		this.idSocieta = idSocieta;
	}

	public void setDenominazioneSocieta(String denominazioneSocieta) {
		this.denominazioneSocieta = denominazioneSocieta;
	}
	
	
}
