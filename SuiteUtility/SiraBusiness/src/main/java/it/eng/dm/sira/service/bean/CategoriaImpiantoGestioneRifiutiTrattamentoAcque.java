package it.eng.dm.sira.service.bean;

public enum CategoriaImpiantoGestioneRifiutiTrattamentoAcque {
	
	Impianto_Trattamento_Acque("14"),
	Impianto_Depurazione("15"),
	Impianto_Gestione_Rifiuti("16"),
	Inceneritore("17"),
	Discarica("18"),
	Coinceneritore("19"),
	Impianto_Compostaggio_Stabilizzazione_Aerobica("20"),
	Impianto_Trattamento_Anaerobico("21"),
	Altri_Impianti_Trattamento("22"),
	Altri_Impianti_Recupero("23"),
	Impianto_Stoccaggio("24"),
	Impianto_Selezione("25"),
	Impianto_Trattamento_Veicoli_Fuori_Uso("26"),
	Impianto_Trattamento_Recupero_RAFE("27"),
	Impianto_Mobile_Smaltimento_Recupero_Rifiuti("88");
	
	private String value;
	
	private CategoriaImpiantoGestioneRifiutiTrattamentoAcque(String value) {
		this.value = value;
	}
	
	public static CategoriaImpiantoGestioneRifiutiTrattamentoAcque valueOfValue(String name){
		for(CategoriaImpiantoGestioneRifiutiTrattamentoAcque stato:CategoriaImpiantoGestioneRifiutiTrattamentoAcque.values()){
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
