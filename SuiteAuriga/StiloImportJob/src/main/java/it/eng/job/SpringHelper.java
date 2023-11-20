/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringHelper {

	final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private static ApplicationContext mainContext = null;

	private static JobDebugConfig jobDebugConfig = null;

	public static synchronized ApplicationContext getMainApplicationContext() {
		if (mainContext == null) {
			try {
				if( System.getProperty("aurigajob.conf")!= null ){
					String file = System.getProperty("aurigajob.conf") + File.separator + "aurigajob.xml";
					logger.debug("Recupero context da " + file);
					mainContext = new FileSystemXmlApplicationContext(file);
				} else {
					mainContext = new ClassPathXmlApplicationContext("aurigajob.xml");
				}
			} catch (Exception e) {
				mainContext = new ClassPathXmlApplicationContext("aurigajob.xml");
			}
		}
		return mainContext;
	}

	@SuppressWarnings("unchecked")
	public static synchronized <T> T getSpecializedBean(String baseName, String specName, Class<T> cls) {
		T bean = null;
		try {
			if (StringUtils.isNotBlank(specName))
				bean = (T) getMainApplicationContext().getBean(baseName + specName);
		} catch (Exception e) {
			logger.warn(String.valueOf(e));
		}
		if (bean == null) {
			bean = (T) getMainApplicationContext().getBean(baseName);
		}
		return bean;
	}

	public static AurigaJobManager getJobManager() {
		AurigaJobManager manager = null;
		try {
			manager = (AurigaJobManager) getMainApplicationContext().getBean("jobmanager");
		} catch (Exception e) {
			logger.error(String.valueOf(e));
		}
		return manager;
	}

	public static synchronized JobDebugConfig getJobDebugConfig() {
		if (jobDebugConfig == null) {
			try {
				jobDebugConfig = (JobDebugConfig) getMainApplicationContext().getBean("jobDebugConfig");
			} catch (Exception e) {
				jobDebugConfig = new JobDebugConfig();
				jobDebugConfig.setDebugAttivo(false);
			}
		}
		return jobDebugConfig;
	}

}
