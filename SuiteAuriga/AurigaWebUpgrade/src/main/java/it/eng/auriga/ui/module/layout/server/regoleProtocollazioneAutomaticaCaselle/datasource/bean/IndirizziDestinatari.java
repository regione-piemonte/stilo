/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class IndirizziDestinatari {
	
	@NumeroColonna(numero = "1")
	private String indirizzo;
	@NumeroColonna(numero = "2")
	private String tipoIndirizzo;
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getTipoIndirizzo() {
		return tipoIndirizzo;
	}
	public void setTipoIndirizzo(String tipoIndirizzo) {
		this.tipoIndirizzo = tipoIndirizzo;
	}

}
