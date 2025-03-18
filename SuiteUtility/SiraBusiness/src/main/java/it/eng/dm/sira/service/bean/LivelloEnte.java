/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum LivelloEnte {


	FOGLIA("foglia"),
	NODO("nodo");
	
	private String value;
	
	private LivelloEnte(String value) {
		this.value = value;
	}
	
	public static LivelloEnte valueOfValue(String name){
		for(LivelloEnte stato:LivelloEnte.values()){
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
