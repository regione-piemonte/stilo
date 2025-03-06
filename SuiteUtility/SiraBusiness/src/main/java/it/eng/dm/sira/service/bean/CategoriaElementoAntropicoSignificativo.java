package it.eng.dm.sira.service.bean;

public enum CategoriaElementoAntropicoSignificativo {
	
	Sorgente_Emissione_Puntuale("3"),
	Apparecchio_PCB("4"),
	Piezometro("5"),
	Punto_Prelievo_Idrico("6"),
	Pozzo("7"),
	Scarico_Idrico("8"),
	Punto_Controllo_Occasionale("9"),
	Stazione_Monitoraggio_Qualita_Aria("10"),
	Stazione_Monitoraggio_Qualita_Acque_Superficiali("11"),
	Sondaggio_Geognostico("39"),
	Opera_Captazione_Idrica("40"),
	Punto_Misura_O_Campionamento("41"),
	Punto_Monitoraggio_Periodico("42"),
	Punto_Monitoraggio_Continuo("43"),
	Stazione_Metereologica("55"),
	Stazione_Monitoraggio_Acque_Sotterranee("56"),
	Centralina_Fondazione_Bordoni("57"),
	Frantoio_OLeario("65"),
	Sorgente_Radiazioni_Ionizzanti("68"),
	Sorgente_Inquinamento_Acustico("69"),
	Sorgente_Inquinamento_Acustico_Puntuale("70"),
	Sorgente_Inquinamento_Acustico_Lineare("71"),
	Laghetti_Collinari("85"),
	Aree_Sosta("89"),
	Aree_Verdi_Attrezzate("90"),
	Sorgente_Emissione_Atmosfera("107"),
	Sorgente_Emissione_Areale("108"),
	Unita_Tecnica("110"),
	Sorgente_Illuminazione_Artificiale("111");
	
	private String value;
	
	private CategoriaElementoAntropicoSignificativo(String value) {
		this.value = value;
	}
	
	public static CategoriaElementoAntropicoSignificativo valueOfValue(String name){
		for(CategoriaElementoAntropicoSignificativo stato:CategoriaElementoAntropicoSignificativo.values()){
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
