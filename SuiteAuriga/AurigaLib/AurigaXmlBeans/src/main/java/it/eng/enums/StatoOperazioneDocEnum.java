/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum StatoOperazioneDocEnum {

	IN_CORSO("in_corso"), COMPLETATA("completata"), IN_ERRORE("in_errore");

	private final String codice;

	private StatoOperazioneDocEnum(String codice) {
		this.codice = codice;
	}

	public static final StatoOperazioneDocEnum fromCodice(String codice) {
		for (StatoOperazioneDocEnum value : values()) {
			if (value.getCodice().equals(codice))
				return value;
		}
		return null;
	}

	public String getCodice() {
		return this.codice;
	}

}
