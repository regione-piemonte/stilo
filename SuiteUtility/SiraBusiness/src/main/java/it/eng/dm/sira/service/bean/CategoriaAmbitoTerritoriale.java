package it.eng.dm.sira.service.bean;

public enum CategoriaAmbitoTerritoriale {
	
	Sito_Contaminato("28"),
	Lotto_Sito_Contaminato("29"),
	Sito_Contaminato_Amianto("64"),
	Sito_Spandimento_Acque_Vegetazione_E_Sanse_Umide("66"),
	Area_Sottoposta_Vincolo_Idrogeologico("72"),
	Area_Sottoposta_Pericolo_Idrogeologico("73"),
	Area_Sottoposta_Rischio_Idrogeologico("74"),
	Area_Estrattiva("75"),
	Spiaggia("76"),
	Costa_Antropizzata("77"),
	Costa_Rocciosa("78"),
	Unita_Fisiografica("79"),
	Sito_Interesse_Comunitario("91"),
	Zona_Protezione_Speciale("92"),
	Area_Protetta("93"),
	Zona_Umida("94"),
	Cava("95"),
	Area_Marina_Protetta("96"),
	Parco_Nazionale("97"),
	RIN("98"),
	Monumento_Naturale("99"),
	Grotta("100"),
	Specie_Endemiche("101"),
	Oasi_Permanenti_Protezione_Faunistica_Cattura("102"),
	Zone_Temporanee_Popolamento_Cattura("103"),
	Zona_Addestramento_Cani("104"),
	Zona_Concessione_Autogestita("105"),
	Allevamenti_Fauna_Selvatica("106"),
	Sito_Spandimento_Fanghi_Depurazione("109"),
	Area_Legge_31_89("114"),
	Zona_Omogenea_Acusticamente("115"),
	Miniera("130");
	
	private String value;
	
	private CategoriaAmbitoTerritoriale(String value) {
		this.value = value;
	}
	
	public static CategoriaAmbitoTerritoriale valueOfValue(String name){
		for(CategoriaAmbitoTerritoriale stato:CategoriaAmbitoTerritoriale.values()){
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
