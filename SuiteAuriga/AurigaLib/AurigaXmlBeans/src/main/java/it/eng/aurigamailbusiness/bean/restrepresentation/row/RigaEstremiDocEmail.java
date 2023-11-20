/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class RigaEstremiDocEmail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NumeroColonna(numero = "1")
	private String idUD;
	@NumeroColonna(numero = "2")
	private String estremiProt;
	
	public String getIdUD() {
		return idUD;
	}
	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}
	public String getEstremiProt() {
		return estremiProt;
	}
	public void setEstremiProt(String estremiProt) {
		this.estremiProt = estremiProt;
	}	
	
}