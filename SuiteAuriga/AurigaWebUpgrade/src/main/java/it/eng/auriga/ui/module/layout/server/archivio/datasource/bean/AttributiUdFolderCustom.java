/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AttributiUdFolderCustom {

	@NumeroColonna(numero = "1")
	private String id;

	@NumeroColonna(numero = "3")
	private String tipoRelazione;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipoRelazione() {
		return tipoRelazione;
	}
	public void setTipoRelazione(String tipoRelazione) {
		this.tipoRelazione = tipoRelazione;
	}
	
}
