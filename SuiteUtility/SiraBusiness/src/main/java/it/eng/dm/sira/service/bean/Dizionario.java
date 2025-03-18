/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum Dizionario {
	
	TIPOLOGIA_PROCEDIMENTO("TIPOLOGIA_PROCEDIMENTO"),
	URL_INFORMATIVO_PROCEDIMENTO("URL_INFORMATIVO_PROCEDIMENTO"),
	TIPO_AREA_TEMATICA("TIPO_AREA_TEMATICA");
	
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
