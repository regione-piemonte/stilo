/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum StatoDocLottoEnum {

	VALIDAZIONE_SUPERATA("validazione_superata"), VALIDAZIONE_FALLITA("validazione_fallita"), OPERAZIONI_IN_CORSO("operazioni_in_corso"), OPERAZIONI_COMPLETATE(
			"operazioni_completate"), OPERAZIONI_IN_ERRORE("operazioni_in_errore");

	private final String codice;

	private StatoDocLottoEnum(String codice) {
		this.codice = codice;
	}

	public static final StatoDocLottoEnum fromCodice(String codice) {
		for (StatoDocLottoEnum value : values()) {
			if (value.getCodice().equals(codice))
				return value;
		}
		return null;
	}

	public String getCodice() {
		return this.codice;
	}

}
