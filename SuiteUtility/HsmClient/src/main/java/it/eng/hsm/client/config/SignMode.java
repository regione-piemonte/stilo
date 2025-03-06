package it.eng.hsm.client.config;

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
