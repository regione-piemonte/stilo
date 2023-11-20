/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author mzanetti
 * Bean che restituisce i destinatari preferiti dell'utente
 */


public class DestinatarioPreferitoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tipo del destinatario (UO, UT, SV, G)
	 */
	@NumeroColonna(numero="1")
	private String tipoDestinatarioPreferito;
	
	/**
	 * Id dell'UO o utente preferito
	 */
	@NumeroColonna(numero="2")
	private String idDestinatarioPreferito;
	
	/**
	 * Descrizione della UO/utente
	 */
	@NumeroColonna(numero="3")
	private String descrizioneDestinatarioPreferito;
	
	public String getTipoDestinatarioPreferito() {
		return tipoDestinatarioPreferito;
	}

	public void setTipoDestinatarioPreferito(String tipoDestinatarioPreferito) {
		this.tipoDestinatarioPreferito = tipoDestinatarioPreferito;
	}

	public String getIdDestinatarioPreferito() {
		return idDestinatarioPreferito;
	}

	public void setIdDestinatarioPreferito(String idDestinatarioPreferito) {
		this.idDestinatarioPreferito = idDestinatarioPreferito;
	}
	
	public String getDescrizioneDestinatarioPreferito() {
		return descrizioneDestinatarioPreferito;
	}

	public void setDescrizioneDestinatarioPreferito(String descrizioneDestinatarioPreferito) {
		this.descrizioneDestinatarioPreferito = descrizioneDestinatarioPreferito;
	}
}