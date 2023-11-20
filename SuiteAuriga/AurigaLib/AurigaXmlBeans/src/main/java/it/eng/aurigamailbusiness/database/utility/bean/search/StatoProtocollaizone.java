/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum StatoProtocollaizone implements Serializable{

	NON_PROTOCOLLATA("N"),
	PROTOCOLLATA("S"),
	PROTOCOLLATA_CON_TUTTI_GLI_ALLEGATI("C"),
	PROTOCOLLATI_SOLO_ALCUNI_ALLEGATI("P")
	;
	
	private String valore;
	
	private StatoProtocollaizone(String value){
		valore = value;
	}
	
	public String getValore(){
		return valore;
	}
}
