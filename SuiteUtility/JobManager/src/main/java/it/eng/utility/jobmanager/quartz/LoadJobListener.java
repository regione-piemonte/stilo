package it.eng.utility.jobmanager.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

public class LoadJobListener implements JobListener {
	
	Logger log = Logger.getLogger(LoadJobListener.class);
	
	private String name;
	
	public LoadJobListener(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void jobExecutionVetoed(JobExecutionContext arg0) {
		log.debug("Job in veto");
	
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		//Job ok
		log.debug("Job iniziato");
	}

	public void jobWasExecuted(JobExecutionContext context,JobExecutionException arg1) {
		//Job in errore		
		try {
			log.debug("SIZE:"+context.getScheduler().getCurrentlyExecutingJobs().size());
		} catch (SchedulerException e) {
			log.error(e.getMessage(),e);
		}
				
	}

}
