/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum SignMode {
	
	HASH("HASH"), 
	CADES("CADES"),
	PADES("PADES");
	
	private String type;
	
	SignMode(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
}
