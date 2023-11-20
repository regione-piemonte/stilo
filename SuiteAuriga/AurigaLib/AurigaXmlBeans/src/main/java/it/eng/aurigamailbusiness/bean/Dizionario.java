/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum Dizionario {

	CAUSALE("causale"),
	DI_ARGOMENTO("di argomento"),
	AFFERENZA_ALLO_STESSO_PROCEDIMENTO("afferenza allo stesso procedimento"),
	RICEVUTA_PEC("ricevuta PEC"),
	DELIVERY_STATUS_NOTIFICATION("delivery status notification"),
	NOTIFICA_INTEROPERABILE("notifica interoperabile"),
	NOTIFICA_CONFERMA("notifica conferma"),
	NOTIFICA_ECCEZIONE("notifica eccezione"),
	RISPOSTA("risposta"),
	INOLTRO("inoltro");
	
	
	private String value;
	
	private Dizionario(String value) {
		this.value = value;
	}
	
	public static Dizionario valueOfValue(String name){
		for(Dizionario stato:Dizionario.values()){
			if(stato.value.equals(name)){
				return stato;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
	
}
