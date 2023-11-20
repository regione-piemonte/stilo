/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum TipoRichiedente {
		
	RICH_ESTERNO("E", "esterno"),
	RICH_INTERNO("I", "interno");
	
	private String value;
	private String descrizione;
	
	private TipoRichiedente(String value, String descrizione) {
		this.value = value;
		this.descrizione = descrizione;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
		
}
