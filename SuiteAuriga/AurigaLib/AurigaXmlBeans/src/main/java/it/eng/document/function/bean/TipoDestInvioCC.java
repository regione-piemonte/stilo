/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoDestInvioCC implements Serializable{

	SCRIVANIA("SV"), UNITA_ORGANIZZATIVA("UO"), GRUPPO("G");
	
	private String dbValue;
	
	private TipoDestInvioCC(String value){
		dbValue = value;
	}
	
	public String toString(){
		return dbValue;
	}
}
