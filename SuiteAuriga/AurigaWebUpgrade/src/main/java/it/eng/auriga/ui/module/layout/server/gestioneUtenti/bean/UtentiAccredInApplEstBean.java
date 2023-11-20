/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class UtentiAccredInApplEstBean {

    @NumeroColonna(numero="1")
    private String idUser;
	
	@NumeroColonna(numero="2")
	private String desUser;
	
	@NumeroColonna(numero="3")
	private String username;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getDesUser() {
		return desUser;
	}

	public void setDesUser(String desUser) {
		this.desUser = desUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
		
}
