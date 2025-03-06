package it.eng.utility.jobmanager.quartz;

import java.lang.management.ManagementFactory;
import java.util.HashMap;

import javax.management.MBeanServer;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import it.eng.utility.jobmanager.quartz.bean.JobExecutionStatusEnum;
import it.eng.utility.jobmanager.quartz.bean.JobStatusUtility;

/**
 * Job di esecuzione, si occupa di reperire i dati dalla cache e eseguire
 * l'elaborazione
 * 
 * @author Rigo Michele
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ExecutorJob<T> implements Job {

	protected Logger log = Logger.getLogger(ExecutorJob.class);

	protected MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {

		// creo il job
		AbstractJob<T> job = createJob(context);

		// se non ci sono stati errori durante la creazione del job by
		// reflection, procedo con l'esecuzione
		if (job != null) {

			Thread currentThread = Thread.currentThread();

			try {

				JobStatusUtility.traceThreadExecution(JobExecutionStatusEnum.RUNNING, currentThread.getId(), job);

				job.doExecute((T) context.getJobDetail().getJobDataMap().get("VALUE"));

				job.doEnd((T) context.getJobDetail().getJobDataMap().get("VALUE"));

			} catch (Throwable e) {

				log.error("ERROR", e);
				throw new JobExecutionException(e);

			} finally {

				JobStatusUtility.traceThreadExecution(JobExecutionStatusEnum.COMPLETED, currentThread.getId(), job);

				// verifico se devo effettuare lo shutdown del job
				JobStatusUtility.checkAndShutdownJob(job, context);

			}
		}
	}

	/**
	 * Crea by reflection il job di cui si deve eseguire l'execute
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected AbstractJob<T> createJob(JobExecutionContext context) {

		AbstractJob<T> job = null;

		try {

			String classJobName = context.getJobDetail().getJobDataMap().getString(LoadJob.LOAD_CLASS_JOB);

			HashMap<String, Object> attributes = (HashMap<String, Object>) context.getJobDetail().getJobDataMap()
					.get("JOB_ATTRIBUTES");
			String parentLoader = (String) context.getJobDetail().getJobDataMap().get("PARENT_LOADER");

			String jobName = (String) context.getJobDetail().getJobDataMap().get("JOB_NAME");

			// Istanzio la classe per l'esecuzione del metodo execute
			Class<?> jobclass = Class.forName(classJobName);
			it.eng.utility.jobmanager.quartz.Job jobAnnotation = (it.eng.utility.jobmanager.quartz.Job) jobclass
					.getAnnotation(it.eng.utility.jobmanager.quartz.Job.class);

			job = (AbstractJob<T>) jobclass.newInstance();
			job.setName(context.getJobDetail().getKey().getName());
			job.setAttributes(attributes);
			job.setJobType(jobAnnotation.type());
			job.setParentLoader(parentLoader);
			job.setFireInstanceId(jobAnnotation.type() + "_E_" + context.getFireInstanceId());
			job.setScheduledFireTime(context.getScheduledFireTime());
			job.setNextFireTime(context.getNextFireTime());
			job.setName(jobName);

			log.debug("ID istanza: " + job.getFireInstanceId());

		} catch (Exception e) {

			log.error("Durante la creazione del job si è verificata la seguente eccezione", e);

		}

		return job;
	}

}