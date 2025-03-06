package it.eng.dm.sira.populate;


public enum TipoAttributo {
	
	TEXT_AREA("TEXT-AREA"),
	TEXT_BOX("TEXT-BOX"),
	DATE("DATE"),
	COMPLEX("COMPLEX"),
	COMBO_BOX("COMBO-BOX"),
	CHECK("CHECK"),
	DATE_TIME("DATETIME"),
	EURO("EURO"),
	NUMBER("NUMBER");
	
	private String value;
	
	private TipoAttributo(String value) {
		this.value = value;
	}
	
	public static TipoAttributo valueOfValue(String name){
		for(TipoAttributo stato:TipoAttributo.values()){
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
