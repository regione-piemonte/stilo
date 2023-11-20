/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Configurazioni generali 
 * 
 *
 */

//@XmlRootElement
public class ConfigurazioniProcessiAggiuntaMarca {

	private Map<String, ConfigurazioniProcessoAggiuntaMarca> mappaConfigurazioni;

	public Map<String, ConfigurazioniProcessoAggiuntaMarca> getMappaConfigurazioni() {
		return mappaConfigurazioni;
	}

	public void setMappaConfigurazioni(Map<String, ConfigurazioniProcessoAggiuntaMarca> mappaConfigurazioni) {
		this.mappaConfigurazioni = mappaConfigurazioni;
	}
	
	

}
