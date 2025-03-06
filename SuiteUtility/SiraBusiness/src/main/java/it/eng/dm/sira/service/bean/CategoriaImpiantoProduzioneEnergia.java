package it.eng.dm.sira.service.bean;

public enum CategoriaImpiantoProduzioneEnergia {
	
	
	Impianto_produzione_energia_termica	("116"),
	Impianto_produzione_energia_termica_da_fonti_tradizionali("117"),	
	Impianto_termico("118"),	
	Impianto_produzione_energia_termica_da_fonti_energia_rinnovabile("119"),	
	Impianto_produzione_energia_termica_da_solare_termico("120"),	
	Impianto_produzione_energia_termica_da_geotermico("121"),	
	Impianto_produzione_energia_termica_da_aerotermico("122"),	
	Impianto_produzione_energia_termica_da_idrotermico("123");	
	
	
	private String value;
	
	private CategoriaImpiantoProduzioneEnergia(String value) {
		this.value = value;
	}
	
	public static CategoriaImpiantoProduzioneEnergia valueOfValue(String name){
		for(CategoriaImpiantoProduzioneEnergia stato:CategoriaImpiantoProduzioneEnergia.values()){
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
