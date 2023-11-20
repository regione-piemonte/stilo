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
public class AggiungiMarcaTemporale extends it.eng.job.aggiungiMarca.AggiungiMarcaTemporaleJob {
	
	public String process() throws Exception {
		
		if (super.getAttributes() == null) {
			super.setAttributes((HashMap)SpringHelper.getMainApplicationContext().getBean("jobAttributes"));
		}
		
		AurigaJobManager manager = (AurigaJobManager)SpringHelper.getMainApplicationContext().getBean("jobmanager");
	    Map<String, JobConfigBean> app = manager.getJobs();
	    JobConfigBean res = app.get("T_CODA_X_EXPORT_JOB");
	    JobConfigBean input = res;
	    input.setType("AggiungiMarcaTemporaleJob");
	    input.getAttributes().put("oneShot", "true");
	    input.getAttributes().put("entityPackage", "it.eng.job.aggiungiMarca.entity");
	    input.getAttributes().put("hibernateConfigFile", "hibernate.cfg.xml");
	    input.getAttributes().put("locale", "it");
	    input.getAttributes().put("tipoDominio", "2");
	    input.getAttributes().put("schema", "OWNER_1");
	    input.getAttributes().put("token", "#RESERVED_1");
	    manager.getJobs().remove("AGGIUNGI_MARCA_TEMPORALE_JOB");
	    manager.getJobs().put("AGGIUNGI_MARCA_TEMPORALE_JOB", input);
	    try {
	      JobConfigHelper.initialize(manager);
	      SpringAppContext.setContext(SpringHelper.getMainApplicationContext());
	    } catch (Throwable e) {
	    }
	    
	    return super.process();
	}
	
}
