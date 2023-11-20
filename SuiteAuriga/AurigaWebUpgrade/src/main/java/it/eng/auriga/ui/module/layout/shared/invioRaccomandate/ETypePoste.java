/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum ETypePoste {
	
	
    RACCOMANDATA("1"),
    
    POSTA_PRIORITARIA("2");
	
    private final String value;
    
    ETypePoste(String v){
    	value = v;
    }
    
    public String value() {
        return value;
    }

    public static ETypePoste fromValue(String v) {
        for (ETypePoste c: ETypePoste.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    
}
