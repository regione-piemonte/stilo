package it.eng.hsm.client.config;

import java.io.Serializable;

public enum TypeOtp implements Serializable {
	
	SMS("SMS"), 
	ARUBACALL("ARUBACALL");
	
	private String value;
	
	TypeOtp(String value){
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
