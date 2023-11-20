/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

/**
 * Bean di configurazione che contiene tutti i parametri globali del modulo
 * @author upescato
 *
 */

public class ModuleConfig {
	
	private Map<String, String> storages;
	
	/**
	 * Identificativo del DB storage in cui verranno memorizzati i file
	 */
	private String idDbStorage;

	public String getIdDbStorage() {
		return idDbStorage;
	}

	public void setIdDbStorage(String idDbStorage) {
		this.idDbStorage = idDbStorage;
	}

	public void setStorages(Map<String, String> storages) {
		this.storages = storages;
	}

	public Map<String, String> getStorages() {
		return storages;
	}

}
