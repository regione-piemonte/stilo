/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum StatoConsolidamentoEmail {

	NON_ACCETTATA("non_accettata",3), ACCETTATA("accettata",3), AVVERTIMENTI_IN_CONSEGNA("avvertimenti_in_consegna",4), CONSEGNATA("consegnata",5), IN_BOZZA(
			"in_bozza",1), ERRORI_IN_CONSEGNA("errori_in_consegna",5), IN_INVIO("in_invio",2), CONSEGNATA_PARZIALMENTE("consegnata_parzialmente",5);

	private String value;
	private int level;

	private StatoConsolidamentoEmail(String value, int level) {
		this.value = value;
		this.level = level;
	}

	public static StatoConsolidamentoEmail valueOfValue(String name) {
		for (StatoConsolidamentoEmail stato : StatoConsolidamentoEmail.values()) {
			if (stato.value.equals(name)) {
				return stato;
			}
		}
		return null;
	}
	

	public String getValue() {
		return value;
	}
	
	public int getLevel() {
		return level;
	}

}
