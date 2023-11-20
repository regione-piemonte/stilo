/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

public class StringaRicercaEstesaCaseInsensitive2 extends CustomDataSourceField {
	
	public StringaRicercaEstesaCaseInsensitive2() {
		super(FieldType.TEXT);
	}

	public StringaRicercaEstesaCaseInsensitive2(String name) {
		super(name, FieldType.TEXT);
	}

	public StringaRicercaEstesaCaseInsensitive2(String name, String title) {
		super(name, FieldType.TEXT, title);
	}

	public StringaRicercaEstesaCaseInsensitive2(String name, String title, int length) {
		super(name, FieldType.TEXT, title, length);
	}

	public StringaRicercaEstesaCaseInsensitive2(String name, String title, int length, boolean required) {
		super(name, FieldType.TEXT, title, length, required);
	}
	
	protected void init() {
		
		setAttribute("customType", "stringa_ricerca_estesa_case_insensitive_2");
        
	}

}
