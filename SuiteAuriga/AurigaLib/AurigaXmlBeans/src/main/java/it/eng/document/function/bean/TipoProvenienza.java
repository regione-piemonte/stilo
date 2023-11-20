/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoProvenienza implements Serializable, DBenum{

	
	ENTRATA("E","ENTRATA"), USCITA("U","USCITA"), INTERNA("I","INTERNA");
	
	private String dbValue;
	private String descrizione;
	
	public String toString(){
		return dbValue;
	}
	
	private TipoProvenienza(String value, String descrizione){
		setDbValue(value);
		setDescrizione(descrizione);
	}

	public void setDbValue(String dbValue) {
		this.dbValue = dbValue;
	}

	public String getDbValue() {
		return dbValue;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
