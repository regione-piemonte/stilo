/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.eng.aurigamailbusiness.database.listeners.AurigaMailPreInsertEventListener;
import it.eng.aurigamailbusiness.database.listeners.AurigaMailPreUpdateEventListener;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.ReflectionUtil;
import it.eng.gd.lucenemanager.LuceneSpringAppContext;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.storageutil.StorageConfig;

public class ContextInitServlet extends HttpServlet {

	private static final long serialVersionUID = -8582648875251705962L;

	Logger log = LogManager.getLogger(ContextInitServlet.class);

	private static String realPath;

	public static String getRealPath() {
		return realPath;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		try {

			super.init(config);
			// imposto il classloader, in alcuni casi si hanno errori nel parsing dei Multipart e nel caricamento della classi BouncyCastle
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

			// configurazioni comuni JavaMail da applicare al sistema
			Util.setJavaMailDefaultProperties();

			SpringAppContext.setContext(new ClassPathXmlApplicationContext("AurigaMail.xml"));
			LuceneSpringAppContext.setContext(SpringAppContext.getContext());
			if (log.isDebugEnabled()) {
				log.debug(StorageConfig.getConnectionUrl());
				log.debug(StorageConfig.getHibernateCfgPath());
			}
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			// ConfigUtil.initialize();
			HibernateUtil.setEntitypackage("it.eng.aurigamailbusiness.database.mail");
			ReflectionUtil.setEntityPackage("it.eng.aurigamailbusiness.database.mail");
			configuration.setListener("save", new AurigaMailPreInsertEventListener());
			configuration.setListener("save-update", new AurigaMailPreUpdateEventListener());
			configuration.setListener("update", new AurigaMailPreUpdateEventListener());
			HibernateUtil.addSessionFactory("trasversale", configuration);
			HibernateUtil.addEnte("trasversale", "trasversale");
			log.error("Inizializzazione AurigaMailBusiness completata");

		} catch (Exception e) {
			final String msg = "Errore di inizializzazione AurigaMailBusiness";
			log.error(msg, e);
		}
	}

}
