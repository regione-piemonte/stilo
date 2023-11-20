/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum SearchType {
	
	TYPE_SEARCH_ALL_TERMS("1"),
	TYPE_SEARCH_AT_LEAST_ONE_TERM("0");

	
	private String value;
	
	private SearchType(String value){
		this.value=value;
	}
	
	public static SearchType valueOfValue(String name){
		for(SearchType stato:SearchType.values()){
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
