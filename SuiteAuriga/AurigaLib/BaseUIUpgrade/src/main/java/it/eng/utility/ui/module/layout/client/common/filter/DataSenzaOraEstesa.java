/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.items.FilterDateEstesaItem;

public class DataSenzaOraEstesa extends CustomDataSourceField {

	public DataSenzaOraEstesa() {
		super(FieldType.CUSTOM);
	}

	public DataSenzaOraEstesa(String name) {
		super(name, FieldType.CUSTOM);
	}

	public DataSenzaOraEstesa(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public DataSenzaOraEstesa(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public DataSenzaOraEstesa(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}

	protected void init() {

		setAttribute("customType", "data_senza_ora_estesa");

		FilterDateEstesaItem filterDateItem = new FilterDateEstesaItem();
		filterDateItem.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setEditorType(filterDateItem);

	}

}
