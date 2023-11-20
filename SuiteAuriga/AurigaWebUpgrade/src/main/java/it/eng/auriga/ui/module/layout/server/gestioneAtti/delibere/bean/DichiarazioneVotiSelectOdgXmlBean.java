/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class DichiarazioneVotiSelectOdgXmlBean {

	@NumeroColonna(numero = "1")
	private String idUser;

	@NumeroColonna(numero = "2")
	private String denominazione;

	@NumeroColonna(numero = "3")
	private String decodificaDelegato;


	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDecodificaDelegato() {
		return decodificaDelegato;
	}

	public void setDecodificaDelegato(String decodificaDelegato) {
		this.decodificaDelegato = decodificaDelegato;
	}

}