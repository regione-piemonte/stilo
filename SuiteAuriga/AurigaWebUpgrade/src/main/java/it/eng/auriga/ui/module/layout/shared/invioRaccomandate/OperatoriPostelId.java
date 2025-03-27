/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.shared.invioRaccomandate;

import com.smartgwt.client.types.ValueEnum;

public enum OperatoriPostelId implements ValueEnum {
	LessOrEqual("1"),
	GreaterOrEqual("2"),
	LessThan("3"),
	GreaterThan("4"),
	IEQUALS("5"),
	BetweenInclusive("6"),
	EQUALS("5");

    private String value;

    OperatoriPostelId(String value) {
        this.value = value;
    }
	@Override
	public String getValue() {
		return this.value;
	}

}
