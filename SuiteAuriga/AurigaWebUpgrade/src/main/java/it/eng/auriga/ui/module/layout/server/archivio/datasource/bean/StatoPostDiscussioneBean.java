/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class StatoPostDiscussioneBean extends VisualBean {

	@NumeroColonna(numero = "1")
	private String key;
	@NumeroColonna(numero = "2")
	private String value;
	@NumeroColonna(numero = "3")
	private Integer flgGeneraFileUnione;

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

	public Integer getFlgGeneraFileUnione() {
		return flgGeneraFileUnione;
	}

	public void setFlgGeneraFileUnione(Integer flgGeneraFileUnione) {
		this.flgGeneraFileUnione = flgGeneraFileUnione;
	}

}
