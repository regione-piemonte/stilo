/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.config.ApplicationConfig;
import it.eng.auriga.module.business.listener.AurigaPreInsertEventListener;
import it.eng.auriga.module.business.listener.AurigaPreUpdateEventListener;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.ReflectionUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.storageutil.StorageConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Servlet che effettua il load delle configurazioni del modulo.
 * <br>
 * È la servlet da caricare allo startup del contesto in cui verrà inserito il presente modulo,
 * in modo da avviare la configurazione di tutto
 * quanto necessario all'utilizzo di AurigaBusinessModule.
 * 
 * @author upescato
 * 
 * TODO to be completed in base alle specifiche richieste, per adesso funziona nel contesto web di prova.
 * Se necessario, integrare con altri dati di configurazione
 */

//Per sovrascrivere questo funzionamento di default basterà cambiare, nel web.xml del progetto che farà ricorso a questo modulo, una versione custom
//di questa servlet.

public class ContextInitServlet extends HttpServlet {

	private static final long serialVersionUID = -8582648875251705962L;

	Logger log = Logger.getLogger(ContextInitServlet.class);

	private static String realPath;

	public static String getRealPath() {
		return realPath;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);
		SpringAppContext.setContext(new ClassPathXmlApplicationContext("business.xml"));
		
		
		log.debug(ApplicationConfig.getIdApplication());
		log.debug(StorageConfig.getConnectionUrl());
		log.debug(StorageConfig.getHibernateCfgPath());
		
			configureSchemas();
//			//prepara una connessione
//			Configuration configuration = new Configuration();
//			configuration.configure("hibernate-auriga.cfg.xml");
//
//			//Configuro il db dello storage
////			StorageConfig.configure("C:/data", "storage", "/storage-auriga.cfg.xml");
//
//			ConfigUtil.initialize();
//
//			HibernateUtil.setEntitypackage("it.eng.auriga.module.business.entity");
//			ReflectionUtil.setEntityPackage("it.eng.auriga.module.business.entity");
//
//			configuration.setListener("save" , new AurigaPreInsertEventListener());
//			configuration.setListener("save-update" , new AurigaPreUpdateEventListener());
//			configuration.setListener("update" , new AurigaPreUpdateEventListener());
//
//			HibernateUtil.addSessionFactory("trasversale", configuration);
//			HibernateUtil.addEnte("ente1", "trasversale");
			
		} catch (Throwable e) {
			log.error("Errore " + e.getMessage(), e);
		}
	}

	private void configureSchemas() throws Exception {
		SessionFileConfigurator lSessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext().getBean("SessionFileConfigurator");
		for (SessionFile lSessionFile : lSessionFileConfigurator.getSessions()){
			log.error("Aggiungo la session per " + lSessionFile.getSessionName() + " mediante il file " + lSessionFile.getFileName());
			Configuration configuration = new Configuration();
			configuration.configure(lSessionFile.getFileName());
//			ConfigUtil.initialize();
			HibernateUtil.setEntitypackage("it.eng.auriga.module.business.entity");
			ReflectionUtil.setEntityPackage("it.eng.auriga.module.business.entity");
			configuration.setListener("save" , new AurigaPreInsertEventListener());
			configuration.setListener("save-update" , new AurigaPreUpdateEventListener());
			configuration.setListener("update" , new AurigaPreUpdateEventListener());
			HibernateUtil.addSessionFactory(lSessionFile.getSessionName(), configuration);
			HibernateUtil.addEnte(lSessionFile.getSessionName(), lSessionFile.getSessionName());
		}
	}

}
