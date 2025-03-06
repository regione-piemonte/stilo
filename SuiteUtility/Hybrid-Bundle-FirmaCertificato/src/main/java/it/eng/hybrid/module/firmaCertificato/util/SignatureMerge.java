package it.eng.hybrid.module.firmaCertificato.util;


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

