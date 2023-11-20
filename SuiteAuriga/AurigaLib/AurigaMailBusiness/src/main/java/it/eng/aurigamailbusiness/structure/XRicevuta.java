/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum XRicevuta {

	NON_ACCETTAZIONE("non-accettazione"),
	ACCETTAZIONE("accettazione"),
	PREAVVISO_ERRORE_CONSEGNA("preavviso-errore-consegna"),
	PRESA_IN_CARICO("presa-in-carico"),
	RILEVAZIONE_VIRUS("rilevazione-virus"),
	ERRORE_CONSEGNA("errore-consegna"),
	AVVENUTA_CONSEGNA("avvenuta-consegna");
	
	private String value;
	
	private XRicevuta(String value) {
		this.value = value;
	}
	
	public static XRicevuta valueOfValue(String name){
		for(XRicevuta ricevuta:XRicevuta.values()){
			if(ricevuta.value.equals(name)){
				return ricevuta;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
	
}
