/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum PreferenceMenuBarraDesktop {

	BARRA("B", "solo sulla barra in alto"), DESKTOP("D", "sulla barra in alto e sul desktop");

	private final String valore;
	private final String descrizione;

	PreferenceMenuBarraDesktop(String valore, String descrizione) {
		this.valore = valore;
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getValore() {
		return valore;
	}
	
    public static PreferenceMenuBarraDesktop fromValore(String valore) {
	    for (PreferenceMenuBarraDesktop b : PreferenceMenuBarraDesktop.values()) {
	      if (b.valore.equalsIgnoreCase(valore)) {
	         return b;
	      }
	    }
	    return null;
	}

}
