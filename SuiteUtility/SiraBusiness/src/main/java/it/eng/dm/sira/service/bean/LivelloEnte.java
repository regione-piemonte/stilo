package it.eng.dm.sira.service.bean;


public enum LivelloEnte {


	FOGLIA("foglia"),
	NODO("nodo");
	
	private String value;
	
	private LivelloEnte(String value) {
		this.value = value;
	}
	
	public static LivelloEnte valueOfValue(String name){
		for(LivelloEnte stato:LivelloEnte.values()){
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
