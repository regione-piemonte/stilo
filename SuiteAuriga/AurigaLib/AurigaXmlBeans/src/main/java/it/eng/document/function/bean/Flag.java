/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlEnum
public enum Flag implements Serializable, DBenum{

	SETTED(1), NOT_SETTED(0), NULL(null);
	
	private Integer dbValue;
	
	private Flag(Integer value){
		dbValue = value;
	}
	
	public String toString(){
		if (dbValue==null) return null;
		return dbValue.toString();
	}
	
	public void setDbValue(Integer dbValue) {
		this.dbValue = dbValue;
	}

	public String getDbValue() {
		return dbValue+"";
	}
}
