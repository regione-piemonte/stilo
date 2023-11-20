/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlEnum
public enum RispostaInoltro {
	
	RISPOSTA("R"),
	NOTIFICA_CONFERMA("NC"),
	NOTIFICA_ECCEZIONE("NE"),
	INOLTRO("I");
	
	
	private String value;
	
	private RispostaInoltro(String value) {
		this.value = value;
	}
	
	public static RispostaInoltro valueOfValue(String name){
		for(RispostaInoltro stato:RispostaInoltro.values()){
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
