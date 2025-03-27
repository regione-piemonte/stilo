/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author dbe4235
 *
 */

public class XmlPostelBean {

	@NumeroColonna(numero = "1")
	private String key;
	@NumeroColonna(numero = "2")
	private String value;
	@NumeroColonna(numero = "3")
	private String indirizzo;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
}
