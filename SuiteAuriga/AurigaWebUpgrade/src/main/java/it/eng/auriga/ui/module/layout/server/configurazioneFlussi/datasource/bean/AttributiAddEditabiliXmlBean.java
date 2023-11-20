/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AttributiAddEditabiliXmlBean {

	@NumeroColonna(numero = "1")
	private String nomeAttributo;

	public String getNomeAttributo() {
		return nomeAttributo;
	}

	public void setNomeAttributo(String nomeAttributo) {
		this.nomeAttributo = nomeAttributo;
	}
	
}
