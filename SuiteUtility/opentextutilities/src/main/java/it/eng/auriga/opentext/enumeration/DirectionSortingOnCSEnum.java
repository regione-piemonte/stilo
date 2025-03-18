/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
