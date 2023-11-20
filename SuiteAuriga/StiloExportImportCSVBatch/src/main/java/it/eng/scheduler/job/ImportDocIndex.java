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
public class ImportDocIndex extends it.eng.job.importdocindex.ImportDocIndexJob {
	
	public String process() throws Exception {
		
		if (super.getAttributes() == null) {
			super.setAttributes((HashMap)SpringHelper.getMainApplicationContext().getBean("jobAttributes"));
		}
		
		AurigaJobManager manager = (AurigaJobManager)SpringHelper.getMainApplicationContext().getBean("jobmanager");
	    Map<String, JobConfigBean> app = manager.getJobs();
	    JobConfigBean res = app.get("IMPORT_DOC_INDEX_JOB");
	    JobConfigBean input = res;
	    //input.setType("ImportDocIndexJob");
	    manager.getJobs().remove("IMPORT_DOC_INDEX_JOB");
	    manager.getJobs().put("IMPORT_DOC_INDEX_JOB", input);
	    try {
	      JobConfigHelper.initialize(manager);
	      SpringAppContext.setContext(SpringHelper.getMainApplicationContext());
	    } catch (Throwable e) {
	    }
	    
	    return super.process();
	}
	
}
