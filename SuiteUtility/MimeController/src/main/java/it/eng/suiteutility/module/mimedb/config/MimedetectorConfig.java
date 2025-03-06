package it.eng.suiteutility.module.mimedb.config;

import it.eng.core.annotation.Module;
import it.eng.core.config.ConfigKey;
import it.eng.core.config.ConfigUtil;
import it.eng.core.config.IModuleConfigurator;
import it.eng.suiteutility.module.mimedb.HibernateUtil;

import java.util.Iterator;
import java.util.Properties;

import org.hibernate.cfg.Configuration;

/**
 * Configurazione  modulo mimedetector
 * carica la conf del db dove sono memorizzati i 
 * formati e le estensioni relative. AL momento usa un 
 * acceesso al DB custom (custom hibernateUtil) prendendo 
 * i par di config dal file di conf generale del core
 */
@Module(name=MimedetectorConfig.modulename, version="0.0.1-SNAPSHOT")
public class MimedetectorConfig implements IModuleConfigurator { 
	private static final String MIMEDETECTOR_DB_KEY="mimedetector.db";
	public static final String modulename="MimeDetector";
	
	public void init() throws Exception {
		//if(true) return;
		Configuration configuration = new Configuration();
		//imposto i par di connessione dal file di config
		org.apache.commons.configuration.Configuration dbpropConfig=ConfigUtil.getConfig().subset( MIMEDETECTOR_DB_KEY);
		Iterator<String> dbprops=dbpropConfig.getKeys();
		Properties props = new Properties();
		while (dbprops.hasNext()) {
			String dbpropKey = (String) dbprops.next();
			props.put(dbpropKey, dbpropConfig.getString(dbpropKey));
		}
		
		configuration.setProperties(props);
		
		// Mappo le entity
//		configuration.addAnnotatedClass(TAnagFormatiDig.class);	 
//		configuration.addAnnotatedClass(TEstensioniFmtDig.class);	
//		configuration.addAnnotatedClass(TEstensioniFmtDigId.class);	
//		configuration.addAnnotatedClass(TMimetypeFmtDig.class);
//		configuration.addAnnotatedClass(TMimetypeFmtDigId.class);
		// Create the SessionFactory  
		HibernateUtil.setEntitypackage("it.eng.suiteutility.module.mimedb.entity");
		 HibernateUtil.addSessionFactory(ConfigKey.DATABASE_DEFAULT, configuration);
		 //aggiungo un dominio con la stessa chiave dell'unica connessione configurata 
		 //potrrebbe essere anche diversa 
		HibernateUtil.addEnte(ConfigKey.DATABASE_DEFAULT, ConfigKey.DATABASE_DEFAULT);
		HibernateUtil.setOnlyDefault(true);
	}

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
	}
}