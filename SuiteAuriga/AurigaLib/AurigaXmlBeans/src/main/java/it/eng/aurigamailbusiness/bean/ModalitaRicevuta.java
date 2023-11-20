/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Enumeration contenente le modalita della ricevuta PEC
 * @author jacopo
 *
 */
@XmlRootElement
public enum ModalitaRicevuta {
	
	 	COMPLETA("completa"),
		BREVE("breve"),
		SINTETICA("sintetica");

	    private String value;
	    
	    private ModalitaRicevuta(String value) {
			this.value = value;
		}
	        
		public static ModalitaRicevuta valueOfValue(String name){
			for(ModalitaRicevuta tiporicevuta:ModalitaRicevuta.values()){
				if(tiporicevuta.value.equals(name)){
					return tiporicevuta;
				}
			}
			return null;
		}
		
		
		public String getValue() {
			return value;
		}
	
}