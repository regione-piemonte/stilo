/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import it.eng.gd.lucenemanager.LuceneSpringAppContext;
import it.eng.job.AurigaJobManager;
import it.eng.job.JobConfigHelper;
import it.eng.job.luceneindexer.SpringHelper;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.jobmanager.quartz.config.JobConfigBean;

@Named
public class AvanzamentoJob extends it.eng.job.avanzamento.AvanzamentoJob{

	
	public String process()
		    throws Exception
		  {
		    System.out.println("process jobbbbb");
			if (super.getAttributes() == null) {
		      super.setAttributes((HashMap)SpringHelper.getMainApplicationContext().getBean("jobAttributes"));
		    }
		    AurigaJobManager manager = (AurigaJobManager)SpringHelper.getMainApplicationContext().getBean("jobmanager");
		    Map<String, JobConfigBean> app = manager.getJobs();
			JobConfigBean res = app.get("T_CODA_X_EXPORT_JOB");
			JobConfigBean input = res;
			input.setType("AvanzamentoJob");
			manager.getJobs().remove("T_CODA_X_EXPORT_JOB");
			manager.getJobs().put("T_CODA_X_EXPORT_JOB", input);
		    JobConfigHelper.initialize(manager);
		    SpringAppContext.setContext(SpringHelper.getMainApplicationContext());
		    LuceneSpringAppContext.setContext(SpringHelper.getLuceneApplicationContext());
		    return super.process();
		  }
}
