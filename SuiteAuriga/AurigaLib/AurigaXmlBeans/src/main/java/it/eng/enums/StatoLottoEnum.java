/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum StatoLottoEnum {

	INSERITO("inserito"), IN_VALIDAZIONE("in_validazione"), VALIDATO("validato"), OPERAZIONI_IN_CORSO("operazioni_in_corso"), ELABORATO("elaborato"), SCARTATO(
			"scartato");

	private StatoLottoEnum(String stato) {
		this.stato = stato;
	}

	private String stato;

	public String getStato() {
		return stato;
	}

	public static final StatoLottoEnum fromStato(String stato) {
		for (StatoLottoEnum statoLottoEnum : values()) {
			if (statoLottoEnum.getStato().equalsIgnoreCase(stato))
				return statoLottoEnum;
		}
		return null;
	}

}
