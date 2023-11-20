/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.StorageConfig;
import it.eng.utility.storageutil.manager.entity.Storages;
import it.eng.utility.storageutil.manager.entity.UtilizzatoriStorage;
import it.eng.utility.storageutil.manager.entity.UtilizzatoriStorageH;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Classe che espone alcuni metodi di utilita per l'utilizzo id hibernate
 * 
 * @author Mattia Zanin
 *
 */
public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	static Logger log = Logger.getLogger(HibernateUtil.class);
	
	private HibernateUtil () {
		//RICHIESTO DA SONAR
	}
	
	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			
			String connectionUrl = StorageConfig.getConnectionUrl();
			if(StringUtils.isNotBlank(connectionUrl)) {
				configuration.setProperty("hibernate.connection.url", connectionUrl);
				configuration.setProperty("hibernate.hbm2ddl.auto", "update");
			}
			
			configuration.configure(StorageConfig.getHibernateCfgPath());
			
			// Mappo le entity
			configuration.addAnnotatedClass(Storages.class);	 
			configuration.addAnnotatedClass(UtilizzatoriStorage.class);	 
			configuration.addAnnotatedClass(UtilizzatoriStorageH.class);	 
			
			// Create the SessionFactory from hibernate.cfg.xml
			return configuration.buildSessionFactory();
			
		} catch (Exception ex) {
			// Make sure you log the exception, as it might be swallowed
			log.error("ERRORE", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
     * Metodo che ritorna la factory per recuperare la sessione di hibernate
     */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}