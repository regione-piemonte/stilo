/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoDestinatario implements Serializable, DBenum{

	ALTRO("1"), UNITA_DI_PERSONALE("1"), GIURIDICA("0");
	
	private String dbValue;
	
	private TipoDestinatario(String value){
		dbValue = value;
	}
	
	public String toString(){
		return dbValue;
	}

	@Override
	public String getDbValue() {
		return dbValue;
	}
}
