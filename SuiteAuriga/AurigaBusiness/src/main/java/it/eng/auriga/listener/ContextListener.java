/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.config.ConfigUtil;
import it.eng.core.service.bean.IrisCall;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * Inizializzaione del contesto 
 * @author michele
 *
 */
public class ContextListener implements ServletContextListener {
	
	private static Logger log = Logger.getLogger(ContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ConfigUtil.initialize();
			IrisCall.clearReflections();
			IrisCall.getReflectionService();
		} catch (Exception e) {
			log.warn(e);
		}

	}



}
