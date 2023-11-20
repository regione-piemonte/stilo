/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AttoRichiestaXmlBean {

	@NumeroColonna(numero = "1")
	private String estremiRichiesta;

	
	public String getEstremiRichiesta() {
		return estremiRichiesta;
	}

	
	public void setEstremiRichiesta(String estremiRichiesta) {
		this.estremiRichiesta = estremiRichiesta;
	}

}
