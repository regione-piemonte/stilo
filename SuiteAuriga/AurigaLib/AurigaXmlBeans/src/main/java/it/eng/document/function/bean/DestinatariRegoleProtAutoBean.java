/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class DestinatariRegoleProtAutoBean implements Serializable {

	private static final long serialVersionUID = 8762407704047589731L;

	@NumeroColonna(numero = "1")
	private String indirizzi;
	
	@NumeroColonna(numero = "2")
	private String tipo;

	public String getIndirizzi() {
		return indirizzi;
	}

	public void setIndirizzi(String indirizzi) {
		this.indirizzi = indirizzi;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
