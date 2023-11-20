/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.stilo.util.file.FileHandler;

public class ActaEnte  {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String RESOURCE_FILE = "/res.properties";
	private static final String ENTE_PROPERTY = "ente";
	private static final String ENTE_DEFAULT = "RP";
	private String ente;
	private static final String DBKEY_TITOLARIO_PROPERTY = "dbKeyTitolario";
	private static final String DBKEY_TITOLARIO_DEFAULT = "6";
	private String dbKeyTitolario;


    public ActaEnte() {
    	ente = FileHandler.getInstance().getPropertyValue(ENTE_PROPERTY, RESOURCE_FILE, getClass()).orElse(ENTE_DEFAULT);
		logger.debug("Ente: " + ente);
		dbKeyTitolario = FileHandler.getInstance().getPropertyValue(DBKEY_TITOLARIO_PROPERTY, RESOURCE_FILE, getClass()).orElse(DBKEY_TITOLARIO_DEFAULT);
		logger.debug("dbKeyTitolario: " + dbKeyTitolario);
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

	public String getDbKeyTitolario() {
		return dbKeyTitolario;
	}

	public void setDbKeyTitolario(String dbKeyTitolario) {
		this.dbKeyTitolario = dbKeyTitolario;
	}

}
