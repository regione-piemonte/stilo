/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Configurazioni generali per la scansione e elaborazione di file indice partendo da una directory radice
 * 
 * @author Mattia Zanetti
 *
 */

@XmlRootElement
public class ConfigurazioniScansione {

	/**
	 * Id scansione
	 */

	private String id;

	/**
	 * Configurazione specifiche sulle directory da scansionare
	 */

	private ConfigurazioniDirectoryScansione configurazioniDirectoryScansione;

	/**
	 * Configurazioni sul tipo di file indice
	 */

	private ConfigurazioniFileIndice configurazioniFileIndice;

	/**
	 * Configurazione archiviazione file
	 */

	private ConfigurazioniArchiviazione configurazioniArchiviazione;

	private VincoliFileScansione vincoliFileScansione;

	public ConfigurazioniDirectoryScansione getConfigurazioniDirectoryScansione() {
		return configurazioniDirectoryScansione;
	}

	public void setConfigurazioniDirectoryScansione(ConfigurazioniDirectoryScansione configurazioniDirectoryScansione) {
		this.configurazioniDirectoryScansione = configurazioniDirectoryScansione;
	}

	public ConfigurazioniFileIndice getConfigurazioniFileIndice() {
		return configurazioniFileIndice;
	}

	public void setConfigurazioniFileIndice(ConfigurazioniFileIndice configurazioniFileIndice) {
		this.configurazioniFileIndice = configurazioniFileIndice;
	}

	public ConfigurazioniArchiviazione getConfigurazioniArchiviazione() {
		return configurazioniArchiviazione;
	}

	public void setConfigurazioniArchiviazione(ConfigurazioniArchiviazione configurazioniArchiviazione) {
		this.configurazioniArchiviazione = configurazioniArchiviazione;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public VincoliFileScansione getVincoliFileScansione() {
		return vincoliFileScansione;
	}

	public void setVincoliFileScansione(VincoliFileScansione vincoliFileScansione) {
		this.vincoliFileScansione = vincoliFileScansione;
	}

}
