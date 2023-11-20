/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DestinatariNotificaPECXmlBean {
	
	@NumeroColonna(numero = "1")
	private String descrizione;

	@NumeroColonna(numero = "2")
	private String indirizzoPEC;

	@NumeroColonna(numero = "3")
	private String nota;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIndirizzoPEC() {
		return indirizzoPEC;
	}

	public void setIndirizzoPEC(String indirizzoPEC) {
		this.indirizzoPEC = indirizzoPEC;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
}
