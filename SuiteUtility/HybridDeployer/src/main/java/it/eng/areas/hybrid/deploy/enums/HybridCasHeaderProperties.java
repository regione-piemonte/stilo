package it.eng.areas.hybrid.deploy.enums;

public enum HybridCasHeaderProperties {
	
	ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"), 
	ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
	ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"), 
	ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age");
	
	private String codice;

	HybridCasHeaderProperties(String codice) {
		this.codice = codice;
	}

	public String getCodice() {
		return this.codice;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", this.name(), this.codice);
	}
	
}
