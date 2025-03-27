/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.attiTrasparenza.datasource.bean;

import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class SimpleKeyValueTrasparenzaBean extends VisualBean {

	private String key;
	private String value;
	private Boolean flgBeneficiariObblig;
	
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public Boolean getFlgBeneficiariObblig() {
		return flgBeneficiariObblig;
	}
	public void setFlgBeneficiariObblig(Boolean flgBeneficiariObblig) {
		this.flgBeneficiariObblig = flgBeneficiariObblig;
	}
	
}
