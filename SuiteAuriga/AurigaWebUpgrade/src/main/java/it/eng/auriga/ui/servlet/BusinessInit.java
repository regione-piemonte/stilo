/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

public class BusinessInit {
	
	private static Logger mLogger = Logger.getLogger(BusinessInit.class);

	public void configure(){
//		it.eng.util.SpringAppContext.setContext(SpringAppContext.getContext());
//		SessionFileConfigurator lSessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext().getBean("SessionFileConfigurator");
//		for (SessionFile lSessionFile : lSessionFileConfigurator.getSessions()){
//			mLogger.debug("Aggiungo la session per " + lSessionFile.getSessionName() + " mediante il file " + lSessionFile.getFileName());
//			Configuration configuration = new Configuration();
//			configuration.configure(lSessionFile.getFileName());
//			HibernateUtil.addEntityPackage("it.eng.auriga.module.business.entity");
//			ReflectionUtil.addEntityPackage("it.eng.auriga.module.business.entity");
//			configuration.setListener("save" , new AurigaPreInsertEventListener());
//			configuration.setListener("save-update" , new AurigaPreUpdateEventListener());
//			configuration.setListener("update" , new AurigaPreUpdateEventListener());
//			HibernateUtil.addSessionFactory(lSessionFile.getSessionName(), configuration);
//			HibernateUtil.addEnte(lSessionFile.getSessionName(), lSessionFile.getSessionName());
//		}
//		Configuration configuration = new Configuration();
//		configuration.configure("hibernate.cfg.xml");
//		HibernateUtil.addEntityPackage("it.eng.aurigamailbusiness.database.mail");
//		ReflectionUtil.addEntityPackage("it.eng.aurigamailbusiness.database.mail");
//		configuration.setListener("save" , new AurigaMailPreInsertEventListener());
//		configuration.setListener("save-update" , new AurigaMailPreUpdateEventListener());
//		configuration.setListener("update" , new AurigaMailPreUpdateEventListener());
//		HibernateUtil.addSessionFactory("trasversale", configuration);
//		HibernateUtil.addEnte("trasversale", "trasversale");
	}
}
