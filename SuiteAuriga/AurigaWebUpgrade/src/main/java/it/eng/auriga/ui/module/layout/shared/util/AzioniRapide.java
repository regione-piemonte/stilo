/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum AzioniRapide {
	
	// Azioni specifiche
	METTI_IN_CARICO("MIC"),
	MANDA_IN_APPROVAZIONE("MIA"),
	ASSEGNA_UO_COMPETENTE("AUO"),
	ASSEGNA_DOC("AD"),
	ASSEGNA_FOLDER("AF"),
	INVIO_CC_DOC("ICCD"),
	INVIO_CC_FOLDER("ICCF"),
	SMISTA_DOC("SD"),
	SMISTA_FOLDER("SF");
	
	public String getValue() {
		return value;
	}

	private AzioniRapide(String value) {
		this.value = value;
	}

	private String value;
	
}
