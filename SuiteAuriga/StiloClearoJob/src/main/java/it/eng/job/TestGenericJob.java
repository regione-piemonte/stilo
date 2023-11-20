/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.dbpoolmanager.spring.FactorySpringDatasource;
import it.eng.job.AurigaJobManager;
import it.eng.job.JobConfigHelper;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.jobmanager.quartz.config.JobConfigBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;

public class TestGenericJob {

	private static Logger log = Logger.getLogger(TestGenericJob.class);
	private static final String PACKAGE_REFLECTION = "it.eng.job";

	public static void main(String[] args) {
		
		System.setProperty("aurigajob.conf", "C:\\Users\\ugopiconec\\workspace_aipo_clearo\\StiloClearoJob\\target\\classes");
		
		try {
			HashMap<String, Class<?>> jobclassesmap = new HashMap<String, Class<?>>();
			Reflections reflections = new Reflections(PACKAGE_REFLECTION);
			Set<Class<?>> jobclasses = reflections.getTypesAnnotatedWith(Job.class);
			for (Class<?> classe : jobclasses) {
				String jobtype = ((Job) classe.getAnnotation(Job.class)).type();
				jobclassesmap.put(jobtype, classe);
			}
			log.info("TEST jobclassesmap " + jobclassesmap);
			
			ApplicationContext context = SpringHelper.getMainApplicationContext();
			log.info("TEST context " + context);
			// it.eng.utility.jobmanager.SpringAppContext.setContext(context);
			SpringAppContext.setContext(context);
			// Istanzio il DBPoolManager
			FactorySpringDatasource.setAppContext(context);
			// Recupero il jobmanager e setto i job
			AurigaJobManager manager = SpringHelper.getJobManager();
			
			
			Map<String, JobConfigBean> app = manager.getJobs();
			JobConfigBean res = app.get("EXPORT_TRASPARENZA_JOB");
			JobConfigBean input = res;
			input.setType("TrasparenzaJob");
			manager.getJobs().remove("EXPORT_TRASPARENZA_JOB");
			manager.getJobs().put("EXPORT_TRASPARENZA_JOB", input);
			
			JobConfigHelper.initialize(manager);
			manager.initialize(jobclassesmap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

}
