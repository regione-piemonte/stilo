/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum FormatoLottoEnum {
	DIRECTORY("CARTELLA", null), ZIP("ZIP", "zip"), SEVENZIP("7Z", "7z"), ATTACHMENT("ATTACHMENT", "xml"), REMOTE_DIR("REMOTE_DIR", null);

	private String codice;
	private String estensione;

	private FormatoLottoEnum(String codice, String estensione) {
		this.codice = codice;
		this.estensione = estensione;
	}

	public static final FormatoLottoEnum fromCodice(String codice) {
		for (FormatoLottoEnum formatoLottoEnum : values()) {
			if (formatoLottoEnum.getCodice().equals(codice))
				return formatoLottoEnum;
		}
		return null;
	}

	public String getCodice() {
		return this.codice;
	}

	public String getEstensione() {
		return estensione;
	}
}
