/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class OrganiCollegialiBean {
	
	@NumeroColonna(numero = "1")
	private String key;
	
	@NumeroColonna(numero = "2")
	private String value;
	
	@NumeroColonna(numero = "4")
	private String storico;

	public String getStorico() {
		return storico;
	}

	public void setStorico(String storico) {
		this.storico = storico;
	}

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

}