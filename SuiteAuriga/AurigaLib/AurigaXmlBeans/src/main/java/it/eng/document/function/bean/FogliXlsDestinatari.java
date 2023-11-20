/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class FogliXlsDestinatari implements Serializable{
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 2972495212861582956L;

	@NumeroColonna(numero = "1")
	private String idFoglio;
	
	@NumeroColonna(numero = "2")
	private String nroDestinatario;
	
	@NumeroColonna(numero = "3")
	private String displayFileNameExcel;

	public String getIdFoglio() {
		return idFoglio;
	}

	public String getNroDestinatario() {
		return nroDestinatario;
	}

	public void setIdFoglio(String idFoglio) {
		this.idFoglio = idFoglio;
	}

	public void setNroDestinatario(String nroDestinatario) {
		this.nroDestinatario = nroDestinatario;
	}

	public String getDisplayFileNameExcel() {
		return displayFileNameExcel;
	}

	public void setDisplayFileNameExcel(String displayFileNameExcel) {
		this.displayFileNameExcel = displayFileNameExcel;
	}
	
}
