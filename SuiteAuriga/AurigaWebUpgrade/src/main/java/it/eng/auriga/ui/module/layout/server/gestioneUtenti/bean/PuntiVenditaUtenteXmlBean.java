/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class PuntiVenditaUtenteXmlBean  {
	
	@NumeroColonna(numero="1")
	private String idPuntoVendita;

	public String getIdPuntoVendita() {
		return idPuntoVendita;
	}

	public void setIdPuntoVendita(String idPuntoVendita) {
		this.idPuntoVendita = idPuntoVendita;
	}


}