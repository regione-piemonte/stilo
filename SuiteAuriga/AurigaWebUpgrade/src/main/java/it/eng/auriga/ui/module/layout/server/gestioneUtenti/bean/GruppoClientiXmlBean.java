/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;



public class GruppoClientiXmlBean {

	@NumeroColonna(numero="1")
	private String idGruppoClienti;

	public String getIdGruppoClienti() {
		return idGruppoClienti;
	}

	public void setIdGruppoClienti(String idGruppoClienti) {
		this.idGruppoClienti = idGruppoClienti;
	}

	
	
}
