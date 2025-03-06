package it.eng.hybrid.module.portScanner.ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.comm.CommPortIdentifier;

import org.apache.log4j.Logger;

public class PortScanner {
	
	public final static Logger logger = Logger.getLogger(PortScanner.class);
	
	/**
	 * Recupera la lista di tutte le porte disponibili
	 * @return Lista delle stampanti
	 */
	public List<String> portAvailable(){
		List<String> ports = new ArrayList<String>();
	    try {
			Enumeration enumer = CommPortIdentifier.getPortIdentifiers();
		    while (enumer != null && enumer.hasMoreElements()) {
		    	ports.add(((CommPortIdentifier) enumer.nextElement()).getName());
		    }
		} catch (Exception e) {
			logger.error("Errore nel metodo portAvailable() : " + e.getMessage(), e);
		}
	    return ports;
	}
	
}
