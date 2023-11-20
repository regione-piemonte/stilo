/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum StatoLettura implements Serializable{

	LETTA_DA_QUALCUNO(1),
	LETTA_DA_ME(2),
	NON_LETTA_DA_ME(0),
	NON_LETTA_DA_ALCUNO(-1);
	
	private int valore;
	
	private StatoLettura(int pIntValore){
		valore = pIntValore;
	}
	
	public int getValore(){
		return valore;
	}
}
