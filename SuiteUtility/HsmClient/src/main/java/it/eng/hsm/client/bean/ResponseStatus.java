package it.eng.hsm.client.bean;

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
