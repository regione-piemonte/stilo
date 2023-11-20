/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Mappa fra id scansione e relative configurazioni <br>
 * Ogni configurazione è processata dal job
 * 
 * @author Mattia Zanetti
 *
 */

@XmlRootElement
public class ConfigurazioniScansioni {

	private Map<String, ConfigurazioniScansione> mappaConfigurazioniScansione;

	public Map<String, ConfigurazioniScansione> getMappaConfigurazioniScansione() {
		return mappaConfigurazioniScansione;
	}

	public void setMappaConfigurazioniScansione(Map<String, ConfigurazioniScansione> mappaConfigurazioniScansione) {
		this.mappaConfigurazioniScansione = mappaConfigurazioniScansione;
	}

}
