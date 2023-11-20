/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author DANCRIST
 *
 */

public enum TipoOperazioneMail {

	PRESA_IN_CARICO("PIC"), MESSA_IN_CARICO("MIC"), MANDA_IN_APPROVAZIONE("MIA"), RILASCIA("R");

	private String value;

	TipoOperazioneMail(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
