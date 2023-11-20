/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */



import it.eng.core.business.HibernateUtil;
import it.eng.gd.lucenemanager.manager.entity.TAnagFormatiDig;
import it.eng.gd.lucenemanager.manager.entity.TMimetypeFmtDig;
import it.eng.gd.lucenemanager.manager.entity.TMimetypeFmtDigId;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Classe che espone alcuni metodi di utilita' per l'utilizzo di hibernate
 * 
 */
public class HibernateUtils {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	static Logger log = Logger.getLogger(HibernateUtil.class);
	
	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate-lucene.cfg.xml");
		
			
			configuration.addAnnotatedClass(TMimetypeFmtDig.class);	
			configuration.addAnnotatedClass(TMimetypeFmtDigId.class);
			configuration.addAnnotatedClass(TAnagFormatiDig.class);
			
			// Create the SessionFactory from hibernate.cfg.xml
			SessionFactory sessionFactory = configuration.buildSessionFactory();
			
			return sessionFactory;
		
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			log.error("ERRORE", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
     * Metodo che ritorna la factory per recuperare la sessione di hibernate
     *      
     */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}