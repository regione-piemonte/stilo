package it.eng.dm.sira.service.bean;

public enum CategoriaOggettoIdrografico {
	
	Formazione_Idrogeologica("12"),
	Sorgente_idrica("13"),
	Bacino_idrografico("44"),
	Bacino_idrogeologico("45"),
	Complesso_acquifero("46"),
	Corpo_idrico_superficiale("47"),
	Corso_acqua_naturale_o_artificiale("48"),
	Lago_o_serbatoio("49"),
	Acqua_di_transizione("50"),
	Acqua_marina_costiera("51"),
	Corpo_idrico_sotterraneo("52"),
	Acquifero("53"),
	Acque_sotterranee("80"),
	Parte_di_corso_acqua("81"),
	Parte_di_lago_o_invaso("82"),
	Parte_di_acqua_di_transizione("83"),
	Parte_di_acque_marino_costiere("84"),
	Diga_sbarramento("128");
	
	private String value;
	
	private CategoriaOggettoIdrografico(String value) {
		this.value = value;
	}
	
	public static CategoriaOggettoIdrografico valueOfValue(String name){
		for(CategoriaOggettoIdrografico stato:CategoriaOggettoIdrografico.values()){
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
