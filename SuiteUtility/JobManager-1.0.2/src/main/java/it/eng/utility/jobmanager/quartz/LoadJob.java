package it.eng.utility.jobmanager.quartz;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;

import it.eng.utility.jobmanager.quartz.bean.JobExecutionStatusEnum;

/**
 * Job che si occupa del caricamento dei dati nella cache, può esistere una sola
 * istanza di caricamento dei dati per gruppo di job
 * 
 * @author Rigo Michele
 */

public class LoadJob implements Job {

	protected Logger log = Logger.getLogger(LoadJob.class);

	protected MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	protected static final String LOAD_CLASS_JOB = "LOAD_CLASS_JOB";
	protected static final String PREFIX_CACHE_NAME = "PREFIX_CACHE_NAME";
	protected static final String NODE_TO_EXECUTE = "NODE_TO_EXECUTE";

	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {

			String classJobName = context.getJobDetail().getJobDataMap().getString(LOAD_CLASS_JOB);

			@SuppressWarnings("unchecked")
			HashMap<String, Object> attributes = (HashMap<String, Object>) context.getJobDetail().getJobDataMap()
					.get("JOB_ATTRIBUTES");

			log.debug("JOB IN ESECUZIONE:" + context.getScheduler().getCurrentlyExecutingJobs().size());

			// verifico che esista una sola schedulazione in atto per lo stesso
			// job
			if (context.getScheduler().getCurrentlyExecutingJobs().size() == 1) {

				String name = context.getJobDetail().getKey().getName();

				// Istanzio la classe
				Class<?> jobclass = Class.forName(classJobName);
				it.eng.utility.jobmanager.quartz.Job jobAnnotation = (it.eng.utility.jobmanager.quartz.Job) jobclass
						.getAnnotation(it.eng.utility.jobmanager.quartz.Job.class);

				AbstractJob<?> job = (AbstractJob<?>) jobclass.newInstance();
				job.setAttributes(attributes);
				job.setJobType(jobAnnotation.type());
				job.setFireInstanceId(jobAnnotation.type() + "_L_" + context.getFireInstanceId());
				job.setScheduledFireTime(context.getScheduledFireTime());
				job.setNextFireTime(context.getNextFireTime());
				job.setName(name);

				log.debug(String.format("Istanziato job %1$s con id %2$s", jobAnnotation.type(),
						job.getFireInstanceId()));

				ObjectName beanName = job.createName();

				boolean startNewExecution = !threadRunningExists(beanName);

				if (startNewExecution) {

					if (job.checkOneShot()) {
						log.warn("ONE-SHOT attivo per " + job.getJobType());
					}

					// eseguo il caricamento degli item specifici del job
					List<?> list = job.doLoad();

					// Lancio i job di esecuzione
					if (list != null && list.size() > 0) {

						for (int i = 0; i < list.size(); i++) {

							JobKey key = new JobKey("Execute_" + name + "_" + i,
									context.getJobDetail().getKey().getGroup());
							JobDataMap map = new JobDataMap();
							map.put("VALUE", list.get(i));
							map.put("JOB_TOTAL", list.size());
							map.put(LoadJob.LOAD_CLASS_JOB, classJobName);
							map.put("JOB_ATTRIBUTES", attributes);
							map.put("PARENT_LOADER", job.getFireInstanceId());
							map.put("JOB_NAME", name);

							JobDetail jobdetail = newJob(ExecutorJob.class).usingJobData(map).withIdentity(key)
									.requestRecovery().build();
							Trigger trigger = newTrigger()
									.withIdentity("trigger_" + name + "_" + i, context.getTrigger().getKey().getGroup())
									.startAt(futureDate(1, IntervalUnit.SECOND)).build();
							context.getScheduler().scheduleJob(jobdetail, trigger);
						}
						log.debug("Tutti i job sono in esecuzione");
					} else {
						log.info("il job non ha restituito elementi, niente da eseguire");
					}
				}
			}
		} catch (Exception e) {
			JobExecutionException e2 = new JobExecutionException(e);
			throw e2;
		}
	}

	/**
	 * Recupera l'minfo relativo all'ObjectName specificato
	 * 
	 * @param name
	 * @return
	 */
	protected MBeanInfo retrieveInfo(ObjectName name) {

		MBeanInfo info = null;

		try {

			info = mbs.getMBeanInfo(name);

		} catch (Exception e) {

			// l'eccezione di MBean non trovato viene sollevata alla prima
			// eccezione

		}

		return info;
	}

	/**
	 * Ritorna true se almeno un thread è in stato RUNNING
	 * 
	 * @param name
	 * @return
	 */
	private boolean threadRunningExists(ObjectName name) {

		boolean retValue = false;

		try {

			synchronized (this) {
				MBeanServer lMbs = ManagementFactory.getPlatformMBeanServer();

				@SuppressWarnings("unchecked")
				Map<Long, JobExecutionStatusEnum> threadExecutionStatus = (ConcurrentHashMap<Long, JobExecutionStatusEnum>) lMbs
						.getAttribute(name, "ThreadExecutionStatus");

				retValue = threadExecutionStatus.size() > 0;

			}

		} catch (Exception e) {
			log.error("Durante il check di esecuzione del thread, si è verificata la seguente eccezione", e);
		}

		return retValue;
	}

	/**
	 * Verifica se è stato richiesto lo spegnimento di tutti i job, ed in caso
	 * affermativo, schedula la rimozione di tutti i trigger, per evitare che
	 * nuove istanze dei vari job vengano create
	 * 
	 * @param context
	 * @return
	 */
	protected Boolean checkAndShutdownSystem(JobExecutionContext context) {

		ObjectName value = createSchedulerName();

		Boolean isRequestedShutdown = false;

		try {

			isRequestedShutdown = (Boolean) mbs.getAttribute(value, "RequestedShutdown");

			if (isRequestedShutdown) {

				// schedulo la rimozione di tutti i trigger istanziati,
				// permettendo però il completamento delle istanze di job in
				// esecuzione
				context.getScheduler().shutdown(Boolean.TRUE);
			}

		} catch (Exception e) {
			log.error(
					"Durante il recupero dell'mbean per determinare se spegnere il sistema, si è verificata la seguente eccezione",
					e);
		}

		return isRequestedShutdown;
	}

	protected void suspendJobs(JobExecutionContext context) {

		GroupMatcher<JobKey> matcher = GroupMatcher.groupContains("");

		try {
			context.getScheduler().pauseJobs(matcher);
		} catch (SchedulerException e) {
			log.error(e);
		}

	}

	/**
	 * Crea la chiave di ricerca dell'mbean associato a questo tipo di job
	 * 
	 * @return
	 */
	private ObjectName createSchedulerName() {

		ObjectName name = null;

		try {
			name = new ObjectName("it.eng:type=JobScheduler");
		} catch (Exception e) {
			log.error(
					"Non è stato possibile creare la chiave di ricerca dell'mbean di tracciamento dello stato a causa della seguente eccezione",
					e);
		}

		return name;
	}

}
