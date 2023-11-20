/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoNotifica {
	
	RICEVUTA_NOTIFICA_SDI_FATTURA("RIC_NOT_SDI"),
	FATTURA_PRONTA_A_FIRMARE("FATT_DA_FIRMARE"),
	FATTURA_PRESA_IN_CARICO_FTP("FATT_PRESA_IN_CARICO_FTP"),
	FATTURA__NON_PRESA_IN_CARICO_FTP("FATT_NON_PRESA_IN_CARICO_FTP");
	
	private String value;
	
	private TipoNotifica(String value) {
		this.value = value;
	}
	
	public static TipoNotifica valueOfValue(String name){
		for(TipoNotifica stato:TipoNotifica.values()){
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
