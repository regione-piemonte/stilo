/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum CodificaRuolo {
	
	RESPONSABILE("RESPONSABILE"),
	REFERENTE_TECNICO("REFERENTE TECNICO"),
	REFERENTE_AMMINISTRATIVO("REFERENTE AMMINISTRATIVO");
	
	private String value;
	
	private CodificaRuolo(String value) {
		this.value = value;
	}
	
	public static CodificaRuolo valueOfValue(String name){
		for(CodificaRuolo stato:CodificaRuolo.values()){
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
