/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;


public class ImportoDoc implements Serializable {

	private static final long serialVersionUID = -4061169748243167703L;

	@XmlVariabile(nome="#value", tipo=TipoVariabile.SEMPLICE)
	private String value;

	@XmlVariabile(nome="#currency", tipo=TipoVariabile.SEMPLICE)
	private String currency;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
