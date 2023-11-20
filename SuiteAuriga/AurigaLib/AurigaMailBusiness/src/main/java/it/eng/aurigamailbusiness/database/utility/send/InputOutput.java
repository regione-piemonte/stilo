/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum InputOutput {
	
	INGRESSO("I"),
	USCITA("O");
	
	
	private String value;
	
	private InputOutput(String value) {
		this.value = value;
	}
	
	public static InputOutput valueOfValue(String name){
		for(InputOutput stato:InputOutput.values()){
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
