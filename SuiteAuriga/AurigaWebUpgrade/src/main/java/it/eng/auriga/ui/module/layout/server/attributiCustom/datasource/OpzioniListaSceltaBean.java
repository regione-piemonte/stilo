/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class OpzioniListaSceltaBean {
	
	@NumeroColonna(numero = "1")
	private String opzione;

	public String getOpzione() {
		return opzione;
	}

	public void setOpzione(String opzione) {
		this.opzione = opzione;
	}

}
