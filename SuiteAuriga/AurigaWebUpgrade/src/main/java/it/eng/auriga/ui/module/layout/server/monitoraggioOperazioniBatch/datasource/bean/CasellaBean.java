/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import java.io.Serializable;


public class CasellaBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String idCasella;
	
	@NumeroColonna(numero = "2")
	private String indirizzoCasella;

	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getIndirizzoCasella() {
		return indirizzoCasella;
	}

	public void setIndirizzoCasella(String indirizzoCasella) {
		this.indirizzoCasella = indirizzoCasella;
	}

	
}
