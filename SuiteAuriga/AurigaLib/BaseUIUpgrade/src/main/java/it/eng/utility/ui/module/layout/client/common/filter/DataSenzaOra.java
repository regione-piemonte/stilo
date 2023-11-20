/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.items.FilterDateItem;

public class DataSenzaOra extends CustomDataSourceField {

	public DataSenzaOra() {
		super(FieldType.CUSTOM);
	}

	public DataSenzaOra(String name) {
		super(name, FieldType.CUSTOM);
	}

	public DataSenzaOra(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public DataSenzaOra(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public DataSenzaOra(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}

	protected void init() {

		setAttribute("customType", "data_senza_ora");

		FilterDateItem filterDateItem = new FilterDateItem();
		filterDateItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setEditorType(filterDateItem);

	}

}
