package it.eng.hsm.client.config;

import java.io.Serializable;

public enum UanatacaEnvironment implements Serializable {
	
	CLOUD("CLOUD"), 
	BOX("BOX");
	
	private String value;
	
	UanatacaEnvironment(String value){
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
