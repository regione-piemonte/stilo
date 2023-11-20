/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

public class StringaRicercaRistretta extends CustomDataSourceField {

	public StringaRicercaRistretta() {
		super(FieldType.TEXT);
	}

	public StringaRicercaRistretta(String name) {
		super(name, FieldType.TEXT);
	}

	public StringaRicercaRistretta(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public StringaRicercaRistretta(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public StringaRicercaRistretta(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}

	protected void init() {

		setAttribute("customType", "stringa_ricerca_ristretta");
	}

}
