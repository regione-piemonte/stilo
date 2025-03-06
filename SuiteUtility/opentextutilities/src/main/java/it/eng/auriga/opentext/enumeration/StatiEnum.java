package it.eng.auriga.opentext.enumeration;

/**
 * enum degli stati di un DWO
 * @author tbarbaro
 *
 */
public enum StatiEnum {
	RISERVATO("Riservato"), IN_ELABORAZIONE("In Elaborazione"), EMESSO("Emesso"), LANCIATO("Lanciato"),
	DISPONIBILE("Disponibile"), ARCHIVIATO("Archiviato"), ANNULLATO("Annullato"), PRESO_IN_CARICO ("Preso in carico");

	private String statoDWO;

	StatiEnum(String statoDWO) {
		this.statoDWO = statoDWO;
	}

	public String getStatoDWO() {
		return statoDWO;
	}

}
