/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.spring.utility.SpringAppContext;

/**
 * @author Federico Cacco
 *  
 * Restituisce un oggetto della classe TemplareStorage, del tipo definito tramite spring
 *
 */
public class TemplateStorageFactory {
	
	private static Logger logger = Logger.getLogger(TemplateStorageFactory.class);
	
	public static TemplateStorage getTemplateStorageImpl() throws Exception {
		try {
			return (TemplateStorage)SpringAppContext.getContext().getBean("TemplateStorageImpl");
		} catch (Exception e) {
			logger.error("Nessun TemplateStorageImpl devinito nelle configurazioni", e);
			throw new Exception("Nessun TemplateStorageImpl devinito nelle configurazioni", e);
		}		
	}
	
}


