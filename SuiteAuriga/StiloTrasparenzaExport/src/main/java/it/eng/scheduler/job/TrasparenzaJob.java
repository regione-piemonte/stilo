/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import it.eng.job.AurigaJobManager;
import it.eng.job.JobConfigHelper;
import it.eng.job.SpringHelper;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.jobmanager.quartz.config.JobConfigBean;

@Named
public class TrasparenzaJob extends it.eng.job.trasparenza.TrasparenzaJob {

	
	public String process() throws Exception {
		System.out.println("process jobbbbb");
		
		if (super.getAttributes() == null) {
			//super.setAttributes((HashMap)SpringHelper.getMainApplicationContext().getBean("jobAttributes"));
		}
		
		AurigaJobManager manager = (AurigaJobManager)SpringHelper.getMainApplicationContext().getBean("jobmanager");
		Map<String, JobConfigBean> app = manager.getJobs();
		JobConfigBean res = app.get("EXPORT_TRASPARENZA_REST_JOB");
		JobConfigBean input = res;
		input.setType("TrasparenzaJob");
		manager.getJobs().remove("EXPORT_TRASPARENZA_REST_JOB");
		manager.getJobs().put("EXPORT_TRASPARENZA_REST_JOB", input);
		JobConfigHelper.initialize(manager);
		SpringAppContext.setContext(SpringHelper.getMainApplicationContext());
		
		return super.process();
	}
}
