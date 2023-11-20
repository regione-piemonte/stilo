/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.document.NumeroColonna;


public class GenericXmlBean {

	@NumeroColonna(numero = "1")
	private String generic;

	public String getGeneric() {
		return generic;
	}

	public void setGeneric(String generic) {
		this.generic = generic;
	}

	

}