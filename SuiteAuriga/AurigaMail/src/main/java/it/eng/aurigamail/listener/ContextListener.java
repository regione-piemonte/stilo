/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import it.eng.core.config.ConfigUtil;
import it.eng.core.service.bean.IrisCall;

/**
 * Inizializzaione del contesto
 * 
 * @author michele
 *
 */
public class ContextListener implements ServletContextListener {

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
			throw new RuntimeException("Fatal contextInitialized", e);
		}

	}

}
