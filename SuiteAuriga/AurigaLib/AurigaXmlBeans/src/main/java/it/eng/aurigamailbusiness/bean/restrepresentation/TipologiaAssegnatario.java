/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlEnum;

/*
 * E' stata implementata in questo modo per far funzionare la generazione automatica del client.
 */
@XmlEnum(String.class)
public enum TipologiaAssegnatario {

	/*@XmlEnumValue("SV")*/ SCRIVANIA("SV", "Scrivania utente"), 
	/*@XmlEnumValue("UT")*/ UTENTE("UT", "Utente"), 
	/*@XmlEnumValue("UO")*/ UNITA_ORGANIZZATIVA("UO", "Unità Organizzativa/Ufficio"), 
	/*@XmlEnumValue("G")*/ GRUPPO("G", "Gruppo");
	
	private final String dbValue;
	private final String description;
	
	private TipologiaAssegnatario(String value, String description){
		this.dbValue = value;
		this.description = description;
	}
	
	public String getValue() {
		return dbValue;
	}

	public String getDescription() {
		return description;
	}

	@Override
	/* non modificare! */
	public final String toString() {
		return getValue();
	}
}
