/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum SearchArea {
	
	SEARCH_TYPE_REPOSITORY_DOC_FOLDER("REP"),
	SEARCH_TYPE_UO("UO"),
	SEARCH_TYPE_TITOLARIO("TIT"),
	SEARCH_TYPE_PROCESS("PROC"),
	SEARCH_TYPE_RUBRICA("RUBR");
	
	private String value;
	
	private SearchArea(String value){
		this.value=value;
	}
	
	public static SearchArea valueOfValue(String name){
		for(SearchArea stato:SearchArea.values()){
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
