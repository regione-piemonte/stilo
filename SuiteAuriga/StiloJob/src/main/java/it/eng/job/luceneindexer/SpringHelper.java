/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import it.eng.job.AurigaJobManager;
import it.eng.job.JobDebugConfig;

public class SpringHelper {

	private static final Logger log = Logger.getLogger(SpringHelper.class);

	private static ApplicationContext mainContext = null;

	private static ApplicationContext luceneContext = null;

	private static JobDebugConfig jobDebugConfig = null;

	public static synchronized ApplicationContext getMainApplicationContext() {
		if (mainContext == null) {
			try {
				if( System.getProperty("aurigajob.conf")!= null ){
					String file = System.getProperty("aurigajob.conf") + File.separator + "aurigajob.xml";
					log.debug("Recupero context da " + file);
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
			bean = (T) getMainApplicationContext().getBean(baseName + specName);
		} catch (Exception e) {
			log.warn(e);
		}
		if (bean == null) {
			bean = (T) getMainApplicationContext().getBean(baseName);
		}
		return bean;
	}

	public static synchronized ApplicationContext getLuceneApplicationContext() {

		if (luceneContext == null) {
			try {
				if( System.getProperty("aurigajob.conf")!= null ){
					String file = System.getProperty("aurigajob.conf") + File.separator + "config-lucene.xml";
					log.debug("Recupero context da " + file);
					luceneContext = new FileSystemXmlApplicationContext(file);
				} else {
					luceneContext = new ClassPathXmlApplicationContext("config-lucene.xml");
				}
			} catch (Exception e) {
				luceneContext = new ClassPathXmlApplicationContext("config-lucene.xml");
			}
		}
		return luceneContext;
	}

	public static AurigaJobManager getJobManager() {
		AurigaJobManager manager = null;
		try {
			manager = (AurigaJobManager) getMainApplicationContext().getBean("jobmanager");
		} catch (Exception e) {
			log.error(e);
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
