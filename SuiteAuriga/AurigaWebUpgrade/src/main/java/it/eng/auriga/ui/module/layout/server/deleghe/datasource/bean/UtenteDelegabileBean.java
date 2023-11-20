/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class UtenteDelegabileBean {

	@NumeroColonna(numero = "1")
	private String idUser;
	@NumeroColonna(numero = "3")
	private String username;
	@NumeroColonna(numero = "2")
	private String descrizione;
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizione() {
		return descrizione;
	}
}
