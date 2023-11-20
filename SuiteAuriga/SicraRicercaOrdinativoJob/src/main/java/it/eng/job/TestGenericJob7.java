/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.dbpoolmanager.spring.FactorySpringDatasource;
import it.eng.gd.lucenemanager.LuceneSpringAppContext;
import it.eng.job.AurigaJobManager;
import it.eng.job.JobConfigHelper;
import it.eng.job.luceneindexer.SpringHelper;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.jobmanager.quartz.config.JobConfigBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;

public class TestGenericJob7 {

	private static Logger log = Logger.getLogger(TestGenericJob7.class);
	private static final String PACKAGE_REFLECTION = "it.eng.job";

	public static void main(String[] args) {
		
		System.setProperty("aurigajob.conf", "C:\\Users\\Bcsoft\\eclipse-workspace\\StiloJob\\target\\classes");
		
		try {
			HashMap<String, Class<?>> jobclassesmap = new HashMap<String, Class<?>>();
			Reflections reflections = new Reflections(PACKAGE_REFLECTION);
			Set<Class<?>> jobclasses = reflections.getTypesAnnotatedWith(Job.class);
			for (Class<?> classe : jobclasses) {
				String jobtype = ((Job) classe.getAnnotation(Job.class)).type();
				jobclassesmap.put(jobtype, classe);
			}
			log.info("TEST jobclassesmap " + jobclassesmap);

			// StorageService storageService = EasyMock.createMock(StorageService.class);
			// EasyMock.reset();
			// EasyMock.expect(storageService.extractFile(EasyMock.find("FS@"))).andReturn(new
			// File("C:\\Workspaces\\Eclipse_Workspace_ENG_LUNA\\AurigaJob\\src\\test\\resources\\template\\mail\\FATTNESPRESSO\\5993321_465.pdf"));
			// EasyMock.replay(storageService);
			LuceneSpringAppContext.setContext(SpringHelper.getLuceneApplicationContext());
			ApplicationContext context = SpringHelper.getMainApplicationContext();
			log.info("TEST context " + context);
			// it.eng.utility.jobmanager.SpringAppContext.setContext(context);
			SpringAppContext.setContext(context);
			// DenisBragato: spostato all'inizio del metodo load() del job LuceneIndexerJob
			// LuceneSpringAppContext.setContext(SpringHelper.getLuceneApplicationContext());
			// Istanzio il DBPoolManager
			FactorySpringDatasource.setAppContext(context);
			// Recupero il jobmanager e setto i job
			AurigaJobManager manager = SpringHelper.getJobManager();
			
			
			Map<String, JobConfigBean> app = manager.getJobs();
			JobConfigBean res = app.get("LUCENE_INDEXER_JOB_DEF_CTX_SO");
			JobConfigBean input = res;
			input.setType("StampaRegistroProtocollazioniJob");
			manager.getJobs().remove("LUCENE_INDEXER_JOB_DEF_CTX_SO");
			manager.getJobs().put("LUCENE_INDEXER_JOB_DEF_CTX_SO", input);
			
			JobConfigHelper.initialize(manager);
			manager.initialize(jobclassesmap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

}
