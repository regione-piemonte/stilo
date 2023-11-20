/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum CoordinatePattern {	
	GAUSS("0.00"),
	UTM("0.00"),
	GEO_GRADI("0"),
	GEO_PRIMI("0"),
	GEO_SECONDI("0.00");
		
	private String value;
		
	private CoordinatePattern(String value){
		this.setValue(value);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
