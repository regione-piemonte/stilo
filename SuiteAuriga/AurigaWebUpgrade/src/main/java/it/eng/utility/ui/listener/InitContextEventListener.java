/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import org.wso2.carbon.identity.sso.agent.util.SSOAgentConfigs;

public class InitContextEventListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(InitContextEventListener.class);

	public void contextInitialized(final ServletContextEvent servletContextEvent) {
		final Properties properties = new Properties();
		try {
			properties.load(servletContextEvent.getServletContext().getClassLoader().getResourceAsStream("wso2.properties"));
			SSOAgentConfigs.initConfig(properties);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (SSOAgentException e2) {
			logger.error(e2.getMessage(), e2);
		}
		SSOAgentConfigs.setKeyStoreStream(servletContextEvent.getServletContext().getClassLoader().getResourceAsStream("wso2carbon.jks"));
	}

	public void contextDestroyed(final ServletContextEvent servletContextEvent) {
	}

}