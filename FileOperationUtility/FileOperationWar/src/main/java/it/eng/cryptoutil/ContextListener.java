/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

import it.eng.core.annotation.Module;
import it.eng.core.config.ConfigUtil;
import it.eng.core.service.bean.MetadataRegistry;
import it.eng.module.foutility.util.FileoperationContextProvider;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.oomanager.config.OpenOfficeConfiguration;
import it.eng.utility.oomanager.exception.OpenOfficeException;

/**
 * Inizializzaione del contesto 
 * @author michele
 *
 */
public class ContextListener implements ServletContextListener {
	
	static Logger log = Logger.getLogger(ContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try{
			Reflections lReflections = new Reflections("it.eng");
			log.debug("Cerco i moduli "+ lReflections.getTypesAnnotatedWith(Module.class).size());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		//Inizializzo il business client
		//		HibernateUtil.setEntitypackage("it.eng.iris.module.corebusiness.business.entity");
		//		
		//		org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
		//		HibernateUtil.settingDefaultListener(config);
		//		HibernateUtil.addSessionFactory("trasversale", config.configure());	
		//		HibernateUtil.addEnte("ente", "trasversale");
		//		// configuro la chiave per l'audit
		try {
			ConfigUtil.initialize(event.getServletContext());
			//test Registry
			System.out.println("**********************************************");
			System.out.println(MetadataRegistry.getInstance().getModuleMap());
			System.out.println("**********************************************");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("fatal initizalizion Business Runtime",e);
		}
		//
		//IrisCall.clearReflections();
		//IrisCall.getReflectionService();
		//configuro OpenOffice
		OpenOfficeConfiguration config = (OpenOfficeConfiguration)FileoperationContextProvider.getApplicationContext().getBean("oomanager");
		//test
		//		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		//		System.out.println("application context:"+applicationContext);
		//		OpenOfficeConfiguration config = (OpenOfficeConfiguration) applicationContext.getBean("oomanager");
		try {
			OpenOfficeConverter.configure(config);
		} catch (OpenOfficeException e) {
			e.printStackTrace();
		}	


	}



}
