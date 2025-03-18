/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum CategoriaSoggettoGiuridico{

	Unita_Locale("1"),
	Sede_Legale("2");
	
	private String value;
	
	private CategoriaSoggettoGiuridico(String value) {
		this.value = value;
	}
	
	public static CategoriaSoggettoGiuridico valueOfValue(String name){
		for(CategoriaSoggettoGiuridico stato:CategoriaSoggettoGiuridico.values()){
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
