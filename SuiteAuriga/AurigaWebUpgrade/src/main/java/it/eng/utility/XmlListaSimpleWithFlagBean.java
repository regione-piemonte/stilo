/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class XmlListaSimpleWithFlagBean {

	@NumeroColonna(numero = "1")
	private String key;
	
	@NumeroColonna(numero = "2")
	private String value;
	
	@NumeroColonna(numero = "3")
	private Boolean flag;

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

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}