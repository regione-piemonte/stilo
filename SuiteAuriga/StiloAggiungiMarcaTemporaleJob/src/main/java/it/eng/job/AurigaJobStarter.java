/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import it.eng.utility.jobmanager.quartz.config.JobManager;

public class AurigaJobStarter extends AbstractStarter {

	private static final String PACKAGE_REFLECTION = "it.eng.job";

	final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) throws Throwable {
		// Avvio del thread
		(new Thread(new AurigaJobStarter(args))).start();
	}

	public AurigaJobStarter(String[] args) {
		setArgs(args);
	}

	@Override
	public String getPackage() {
		return PACKAGE_REFLECTION;
	}

	@Override
	public JobManager executeJobManager(HashMap<String, Class<?>> jobclassesmap) {

		// Recupero il jobmanager e setto i job
		AurigaJobManager manager = SpringHelper.getJobManager();
		JobConfigHelper.initialize(manager);

//		if ("API".equals(manager.getBusinessType())) {
//			/*
//			 * Se nell'aurigaJob/jobManager era impostato businessType = API allora vuol dire che è stata inclusa la classe(e il progetto)
//			 * BusinessIntegratedStarter che permette di eseguire la configurazione iniziale per l'avvio della business come API
//			 */
//			invokeIntegratedStarter("it.eng.businessintegrated.utility.BusinessIntegratedStarter");
//		}

		manager.initialize(jobclassesmap, getArgs());

		return manager;

	}

	private void invokeIntegratedStarter(String className) {
		try {

			// Creo un array con l'elenco dei parametri del metodo che verrà chiamato
			Class<?>[] cArg = new Class[1];
			cArg[0] = ApplicationContext.class;

			Class<?> clazz = Class.forName(className);

			// Ottengo un'istanza della classe *BusinessIntegratedStarter
			Object businessIntegratedStarterIstance = clazz.newInstance();

			// Ottengo il metodo da invocare
			Method methodToInvoke = clazz.getMethod("startConfigurationBusinessIntegrated", cArg);

			// Eseguo l'invocazione del metodo con il passaggio del parametro
			methodToInvoke.invoke(businessIntegratedStarterIstance, SpringHelper.getMainApplicationContext());
		} catch (InstantiationException e) {
			logger.error("BusinessIntegratedStarter: InstantiationException");
		} catch (IllegalAccessException e) {
			logger.error("BusinessIntegratedStarter: IllegalAccessException");
		} catch (ClassNotFoundException e) {
			logger.error("BusinessIntegratedStarter: ClassNotFoundException");
		} catch (IllegalArgumentException e) {
			logger.error("BusinessIntegratedStarter: IllegalArgumentException");
		} catch (InvocationTargetException e) {
			logger.error("BusinessIntegratedStarter: InvocationTargetException");
		} catch (NoSuchMethodException e) {
			logger.error("BusinessIntegratedStarter: NoSuchMethodException");
		} catch (SecurityException e) {
			logger.error("BusinessIntegratedStarter: SecurityException");
		}
	}

}
