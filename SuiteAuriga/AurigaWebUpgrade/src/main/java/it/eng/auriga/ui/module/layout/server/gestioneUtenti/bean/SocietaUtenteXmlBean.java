/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class SocietaUtenteXmlBean {

	@NumeroColonna(numero="1")
	private String idSocieta;
	

	public String getIdSocieta() {
		return idSocieta;
	}


	public void setIdSocieta(String idSocieta) {
		this.idSocieta = idSocieta;
	}

	
	
}
