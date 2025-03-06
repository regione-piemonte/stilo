package it.eng.dm.sira.service.bean;

public enum NaturaOST {

	Soggetto_Giuridico("1"),
	Elemento_Antropico_Significativo("2"),
	Impianto_Gestione_Rifiuti_Trattamento_Acque("3"),
	Oggetto_Idrografico("4"),
	Ambito_Territoriale("5"),
	Infrastruttura_Territoriale("6"),
	Siti_Minerari("7"),
	Impianto_Produzione_Energia("8"),
	Soggetto_Fisico("66");
	
	private String value;
	
	private NaturaOST(String value) {
		this.value = value;
	}
	
	public static NaturaOST valueOfValue(String name){
		for(NaturaOST stato:NaturaOST.values()){
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
