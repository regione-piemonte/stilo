/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
