package it.eng.auriga.opentext.enumeration;

public enum DirectionSortingOnCSEnum {
	
	ASC("ascending"),
	DESC("descending");
	
	private String orientamento;
	
	
	
	private DirectionSortingOnCSEnum(String orientamentoOrdinamento) {
		this.orientamento = orientamentoOrdinamento;
	}

	public String getOrientamento() {
		return orientamento;
	}

	public void setOrientamento(String orientamento) {
		this.orientamento = orientamento;
	}
	


}
