/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

public enum ContabiliaEsito {
	
	SUCCESSO,
    FALLIMENTO;
	
	public String value() {
        return name();
    }
	
    public static ContabiliaEsito fromValue(String v) {
        return valueOf(v);
    }
    
}
