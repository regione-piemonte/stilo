/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum ResponseStatus {
	
	OK("OK"), 
	KO("KO");
	
	private String message;
	
	ResponseStatus(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
