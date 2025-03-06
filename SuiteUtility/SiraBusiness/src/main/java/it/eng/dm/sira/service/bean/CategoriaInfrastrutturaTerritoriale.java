package it.eng.dm.sira.service.bean;

public enum CategoriaInfrastrutturaTerritoriale {
	
	Infrastruttura_Per_Radiocomunicazione("30"),	
	Sito_RC("31"),
	Impianto_RC("32"),
	Infrastruttura_Per_Distribuzione_Energia("33"),	
	Linea("34"),	
	Tronco("35"),	
	Tratta("36"),	
	Campata("37"),	
	Sostegno("38"),	
	Impianto("54"), 
	Infrastruttura_Trasporto_Combustibile("112"),	
	Gasdotto("113"),
	Infrastruttura_distribuzione_raccolta_acque("124"),
	Elemento_Acquedottistico("125"),	
	Condotta("126"),	
	Collettore_Fognario("127");	
	
	private String value;
	
	private CategoriaInfrastrutturaTerritoriale(String value) {
		this.value = value;
	}
	
	public static CategoriaInfrastrutturaTerritoriale valueOfValue(String name){
		for(CategoriaInfrastrutturaTerritoriale stato:CategoriaInfrastrutturaTerritoriale.values()){
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
