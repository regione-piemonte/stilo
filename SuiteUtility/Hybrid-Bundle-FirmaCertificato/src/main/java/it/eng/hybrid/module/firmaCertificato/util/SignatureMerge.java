/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum SignatureMerge {

	VERTICALE("verticale"),
	CONGIUNTA("congiunta");
	private final String value;

	SignatureMerge(String v) {
		value = v;
	}

	public String value() {
		return value;
	}	
	public static SignatureMerge fromValue(String v) {
		for (SignatureMerge c: SignatureMerge.values()) {
			if (c.name().equalsIgnoreCase(v)) {
				return c;
			}
		}
		return null;
	}
}

