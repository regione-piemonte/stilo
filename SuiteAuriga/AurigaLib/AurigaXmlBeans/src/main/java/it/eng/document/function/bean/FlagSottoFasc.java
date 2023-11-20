/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum FlagSottoFasc implements Serializable, DBenum{
	
	SOTTOFASCICOLO("S"), INSERTO("I");
	
	private String realValue;
	
	private FlagSottoFasc(String value){
		realValue = value;
	}
	
	@Override
	public String getDbValue() {
		return realValue;
	}

}
