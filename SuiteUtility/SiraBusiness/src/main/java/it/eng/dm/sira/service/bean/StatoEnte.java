package it.eng.dm.sira.service.bean;


public enum StatoEnte {


	ATTIVO("ATTIVO"),
	SCADUTO("SCADUTO"),
	BOZZA("BOZZA");
	
	private String value;
	
	private StatoEnte(String value) {
		this.value = value;
	}
	
	public static StatoEnte valueOfValue(String name){
		for(StatoEnte stato:StatoEnte.values()){
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
