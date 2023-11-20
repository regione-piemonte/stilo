/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum StatoBmtJobEnum {

	INSERITO("I"), ELABORAZIONE_IN_CORSO("E"), COMPLETATO("R"), IN_ERRORE("X"), ANNULLATO("D"), DA_RITENTARE("XT");

	private final String codice;

	private StatoBmtJobEnum(String codice) {
		this.codice = codice;
	}

	public static final StatoBmtJobEnum fromCodice(String codice) {
		for (StatoBmtJobEnum value : values()) {
			if (value.getCodice().equals(codice))
				return value;
		}
		return null;
	}

	public String getCodice() {
		return this.codice;
	}

}
