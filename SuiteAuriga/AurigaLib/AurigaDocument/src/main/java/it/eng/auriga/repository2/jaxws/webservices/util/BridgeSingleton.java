/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Antonio Vendramini: ricava quale sia l'ente al quale collegarsi in base ad una associazione codiceApplicazione.IstanzaApplicazione = CodiceEnte
 * definita all'interno di un file di properties.
 * 
 * 
 * 
 * 
 */

import java.util.Properties;

import org.apache.log4j.Logger;

public class BridgeSingleton {

    public static final String SEPARATORE_CODICE_ISTANZA = ".";
    
    private Properties properties = null;

    //logger
    static Logger aLogger = Logger.getLogger(BridgeSingleton.class.getName());    
    
    /**
     * Create a private constructor so that the singleton class
     * cannot be instantiated by the developer explicitly.
     */
    public BridgeSingleton() { }

    /**
     * Metodo che preleva le informazioni dell'ente
     */

    /**
     * Nel caso non ci sia l'istanza o non ci sia la voce della hashtable
     * dell'ente, allora viene richiamato l'inizializzatore
     */
    public String getDBPoolAlias(String codApplicazione, String istanzaApplicazione) throws Exception
    {
        
        String enteAssociatoStringa = null;
        
        aLogger.info("codApplicazione="+codApplicazione);
        aLogger.info("istanzaApplicazione="+istanzaApplicazione);
        
        String key = codApplicazione + SEPARATORE_CODICE_ISTANZA + istanzaApplicazione;
        
        enteAssociatoStringa = properties.getProperty(key);
        if (enteAssociatoStringa == null) {
        	aLogger.warn("Coppia appl.ist="+key+" NON CONFIGURATA, utilizzo la coppia di DEFAULT!");
        	enteAssociatoStringa = properties.getProperty("DEFAULT");
        	if (enteAssociatoStringa == null) 
        		throw new Exception("ALIAS di DEFAULT non trovato, controllare la configurazione dei WebService!!");
        	
        }
        return enteAssociatoStringa;//==null?-1:Integer.parseInt(enteAssociatoStringa);
    }

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
