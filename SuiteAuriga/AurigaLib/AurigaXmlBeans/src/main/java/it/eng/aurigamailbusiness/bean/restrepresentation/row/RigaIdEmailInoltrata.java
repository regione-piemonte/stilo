/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class RigaIdEmailInoltrata {

	@NumeroColonna(numero = "1")
	private String idMailInoltrata;
	
	@NumeroColonna(numero = "2")
	private String flgMailStatoLavAperta;

	public String getIdMailInoltrata() {
		return idMailInoltrata;
	}

	public void setIdMailInoltrata(String idMailInoltrata) {
		this.idMailInoltrata = idMailInoltrata;
	}
	
	public String getFlgMailStatoLavAperta() {
		return flgMailStatoLavAperta;
	}

	public void setFlgMailStatoLavAperta(String flgMailStatoLavAperta) {
		this.flgMailStatoLavAperta = flgMailStatoLavAperta;
	}
	
	

}
