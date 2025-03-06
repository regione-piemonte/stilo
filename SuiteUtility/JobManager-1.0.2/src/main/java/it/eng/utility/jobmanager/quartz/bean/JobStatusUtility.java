package it.eng.utility.jobmanager.quartz.bean;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.impl.matchers.GroupMatcher;

import it.eng.utility.jobmanager.quartz.AbstractJob;

public class JobStatusUtility {

	private static Logger log = Logger.getLogger(JobStatusUtility.class);

	/**
	 * Aggiorna la mappa dei thread in esecuzione relativa al tipo di job
	 * specificato mediante l'ObjectName fornito
	 * 
	 * @param threadId
	 * @param name
	 */
	public static synchronized void traceThreadExecution(JobExecutionStatusEnum threadStatus, Long threadId,
			AbstractJob<?> job) {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		try {

			@SuppressWarnings("unchecked")
			Map<Long, JobExecutionStatusEnum> threadExecutionStatus = (ConcurrentHashMap<Long, JobExecutionStatusEnum>) mbs
					.getAttribute(job.createName(), "ThreadExecutionStatus");

			switch (threadStatus) {

			case RUNNING:

				threadExecutionStatus.put(threadId, threadStatus);
				mbs.setAttribute(job.createName(), new Attribute("Running", true));

				break;

			default:
				threadExecutionStatus.remove(threadId);

				// quando tutte le istanze del job sono completate, allora il
				// job può essere considerato come non in esecuzione
				if (threadExecutionStatus.isEmpty()) {
					mbs.setAttribute(job.createName(), new Attribute("Running", false));
				}
			}

			log.debug(String.format("Numero di thread registrati %1$s per %2$s", threadExecutionStatus.size(),
					job.createName().getDomain()));

		} catch (Exception e) {

			log.error("Durante l'aggiornamento dello stato del thread, si è verificata la seguente eccezione", e);

		}
	}

	/**
	 * Crea l'MBean associato allo stato di esecuzione del job
	 * 
	 * @param name
	 */
	public static synchronized void createMBean(String jobName) {

		ObjectName name = createName(jobName);

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		try {
			JobExecutionStatus mbean = new JobExecutionStatus();
			mbean.setRunning(Boolean.FALSE);
			mbs.registerMBean(mbean, name);
		} catch (Exception e) {
			log.error("Durante la registrazione dell'mbean di controllo si è verificata la seguente eccezione", e);
		}
	}

	/**
	 * Se non ci sono thread in esecuzione ed è stato richiesto, il metodo
	 * procede allo spegnimento del job, rimuovendo il job dallo scheduler, e
	 * con esso tutti i trigger associati;
	 * 
	 * @param beanName
	 * @param job
	 * @return
	 */
	public static synchronized void checkAndShutdownJob(AbstractJob<?> job, JobExecutionContext context) {

		ObjectName beanName = job.createName();

		try {

			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

			@SuppressWarnings("unchecked")
			Map<Long, JobExecutionStatusEnum> threadExecutionStatus = (Map<Long, JobExecutionStatusEnum>) mbs
					.getAttribute(beanName, "ThreadExecutionStatus");

			boolean shutdownRequested = (Boolean) mbs.getAttribute(beanName, "ScheduleShutdown");

			if (threadExecutionStatus.isEmpty() && 
					(shutdownRequested || job.checkOneShot())) {

				/*log.info(String.format("Shutdown del job id: %1$s, type: %2$s", job.getFireInstanceId(),
						job.getJobType()));

				Set<JobKey> jobKeys = context.getScheduler().getJobKeys(GroupMatcher.jobGroupEquals(job.getJobType()));

				log.info(String.format("Sono state trovate %1$s istanze del job", jobKeys.size()));

				for (JobKey jobKey : jobKeys) {

					boolean shutdownResult = context.getScheduler().deleteJob(jobKey);

					if (shutdownResult) {

						log.info(String.format("L'istanza %1$s del job %2$s è stata spenta correttamente",
								jobKey.getName(), job.getJobType()));

					} else {

						log.info(String.format("Lo scheduler non è riuscito a spegnere l'istanza %1$s del job %2$s",
								jobKey.getName(), job.getJobType()));

					}
				}*/
				
				JobKey jobKey = new JobKey(job.getName(), job.getJobType());
				
				boolean shutdownResult = context.getScheduler().deleteJob(jobKey);
				
				if (shutdownResult) {

					log.info(String.format("L'istanza %1$s del job %2$s è stata spenta correttamente",
							jobKey.getName(), job.getJobType()));

				} else {

					log.info(String.format("Lo scheduler non è riuscito a spegnere l'istanza %1$s del job %2$s",
							jobKey.getName(), job.getJobType()));

				}
			}
		} catch (Exception e) {
			log.error("Durante il check di spegnimento del job si è verificata la seguente eccezione", e);
		}
	}

	/**
	 * Crea l'ObjectName quando l'AbstractJob non è ancora disponibile
	 * 
	 * @param jobName
	 * @return
	 */
	public static synchronized ObjectName createName(String jobName) {

		ObjectName name = null;

		try {
			name = new ObjectName("it.eng.job:name=" + jobName);
		} catch (Exception e) {
			log.error(
					"Non è stato possibile creare la chiave di ricerca dell'mbean di tracciamento dello stato a causa della seguente eccezione",
					e);
		}

		return name;

	}

}
