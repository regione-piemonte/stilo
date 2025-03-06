package it.eng.dm.sira.service.bean;

public enum CategoriaSitiMinerari {

	Discarica_Mineraria	("58"),
	Bacino_Fanghi("59"),	
	Scavo_Cielo_Aperto("60"),	
	Galleria_Mineraria("61"),	
	Pozzo_Minerario("62"),	
	Struttura_Mineraria("63"),	
	Abbancamento_Fini("67");	
	
	private String value;
	
	private CategoriaSitiMinerari(String value) {
		this.value = value;
	}
	
	public static CategoriaSitiMinerari valueOfValue(String name){
		for(CategoriaSitiMinerari stato:CategoriaSitiMinerari.values()){
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
