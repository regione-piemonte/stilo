/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum FilterLookupType {

	rubrica_soggetti("rubrica_soggetti"), indirizzi_viario("indirizzi_viario");

	private String value;

	FilterLookupType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
