/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum AuthType {

	DB("db"),
	LDAP("ldap");

	private String value;

	AuthType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
