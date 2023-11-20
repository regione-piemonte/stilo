/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum PresenzaCommenti implements Serializable{

	PRESENTI_COMMENTI_DESTINATI_A_ME("SUT"),
	PRESENTI_COMMENTI_NON_PUBBLICI("SNP"),
	PRESENTI_COMMENTI("S");
	
	private String valore;
	
	private PresenzaCommenti(String val){
		valore = val;
	}
	
	public String getValore(){
		return valore;
	}
}
