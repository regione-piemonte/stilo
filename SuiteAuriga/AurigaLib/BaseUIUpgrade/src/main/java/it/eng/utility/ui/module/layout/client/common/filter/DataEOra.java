/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.items.FilterDateTimeItem;

public class DataEOra extends CustomDataSourceField {

	public DataEOra() {
		super(FieldType.CUSTOM);
	}

	public DataEOra(String name) {
		super(name, FieldType.CUSTOM);
	}

	public DataEOra(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public DataEOra(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public DataEOra(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}

	protected void init() {

		setAttribute("customType", "data_e_ora");

		FilterDateTimeItem filterDateTimeItem = new FilterDateTimeItem();
		setEditorType(filterDateTimeItem);

	}

}
