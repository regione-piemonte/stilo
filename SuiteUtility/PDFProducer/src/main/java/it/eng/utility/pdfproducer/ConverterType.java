package it.eng.utility.pdfproducer;

@Deprecated
public enum ConverterType {

	FLYING_SOUCER_HTML_CLEANER(""), FLYING_SOUCER_JTIDY("");

	private final String description;

	private ConverterType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
