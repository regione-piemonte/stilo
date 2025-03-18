/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

@Deprecated
public enum ConverterType {

	FLYING_SOUCER_HTML_CLEANER(""), FLYING_SOUCER_JTIDY("");

	private final String description;

	private ConverterType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
