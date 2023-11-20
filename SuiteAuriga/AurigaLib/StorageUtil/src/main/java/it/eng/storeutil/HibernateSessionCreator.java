/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import it.eng.utility.storageutil.HibernateStorageConfig;
import it.eng.utility.storageutil.HibernateStorageConfig.HibernateSessionProperty;

public class HibernateSessionCreator {
	
	static Logger log = Logger.getLogger(HibernateSessionCreator.class);
	
//	private HibernateSessionCreator() {
//	}
	
	public SessionFactory buildSessionFactory(HibernateStorageConfig pHibernateStorageConfig) {
		try {
			Configuration configuration = new Configuration();
			
			for (HibernateSessionProperty pHibernateSessionProperty : pHibernateStorageConfig.getSessionFactory().getProperty()){
				configuration.setProperty(pHibernateSessionProperty.getName(), pHibernateSessionProperty.getValue());
			}	 
			
			// Create the SessionFactory from hibernate.cfg.xml
			return configuration.buildSessionFactory();
			
		} catch (Exception ex) {
			log.error("Errore nella generazione della SessionFactory", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
