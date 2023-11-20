/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoAssegnatario implements Serializable{

	SCRIVANIA("SV"), UTENTE("UT"), UNITA_ORGANIZZATIVA("UO"), GRUPPO("G");
	
	private String dbValue;
	
	private TipoAssegnatario(String value){
		dbValue = value;
	}
	
	public String toString(){
		return dbValue;
	}
}
