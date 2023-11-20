/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum TipoEU {
	
	E, U;
    
    public String value() {
      return name();
    }
    
    public static TipoEU fromValue(String v) {
      return valueOf(v);
    }
	
}
