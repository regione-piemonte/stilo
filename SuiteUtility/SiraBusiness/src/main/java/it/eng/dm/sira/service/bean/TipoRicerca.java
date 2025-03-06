package it.eng.dm.sira.service.bean;


public enum TipoRicerca {
	
	PAROLA_CHIAVE("PAROLA_CHIAVE"),
	SETTORE_AMMINISTRATIVO("SETTORE_AMMINISTRATIVO"),
	AREA_TEMATICA("AREA_TEMATICA"),
	TIPOLOGIA("TIPOLOGIA");
	
	private String value;
	
	private TipoRicerca(String value) {
		this.value = value;
	}
	
	public static TipoRicerca valueOfValue(String name){
		for(TipoRicerca stato:TipoRicerca.values()){
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
