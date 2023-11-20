/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum TipoDestinatario {

	TO("P"),
	CC("CC"),
	BCC("CCN");
	
	
	private String value;
	
	private TipoDestinatario(String value) {
		this.value = value;
	}
	
	public static TipoDestinatario valueOfValue(String name){
		for(TipoDestinatario stato:TipoDestinatario.values()){
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
