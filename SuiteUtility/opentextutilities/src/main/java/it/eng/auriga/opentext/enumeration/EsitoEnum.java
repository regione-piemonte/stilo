package it.eng.auriga.opentext.enumeration;

public enum EsitoEnum {
	
	 ESITO_OK ("00", "Operazione avvenuta con successo per il work order "),
	 ESITO_KO_1("01", "Si è verificato un errore durante l'elaborazione del work order "),
	 ESITO_KO_2("02", "Si è verificato un errore durante l'interazione col sistema documentale ");
	
	private String descrizioneEsito, codiceEsito;
	
	EsitoEnum(String codice, String descrizione){
		this.codiceEsito=codice;
		this.descrizioneEsito = descrizione;
	}

	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}

	public String getCodiceEsito() {
		return codiceEsito;
	}
	
	
	

}
