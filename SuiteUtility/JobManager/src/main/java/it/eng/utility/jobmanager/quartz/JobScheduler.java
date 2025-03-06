package it.eng.utility.jobmanager.quartz;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.utils.Key;

import it.eng.utility.jobmanager.quartz.bean.JobStatusUtility;
import it.eng.utility.jobmanager.quartz.config.JobConfigBean;
import it.eng.utility.jobmanager.quartz.config.JobManager;

/**
 * Classe che si occupa della schedulazione dei job
 * 
 * @author Rigo Michele
 *
 */
public class JobScheduler {

	protected static final String JOB_CACHE = "JOB_CACHE";

	private static Scheduler scheduler;

	protected static Logger log = Logger.getLogger(JobScheduler.class);

	public static void initialize(String jobname, Class<?> jobclass, JobConfigBean config, JobManager managerconfig, String[] args)
			throws Exception {

		Job job = (Job) jobclass.getAnnotation(Job.class);

		// Inizializzo lo scheduler
		Properties props = new Properties();
		props.load(JobScheduler.class.getClassLoader().getResourceAsStream("org/quartz/quartz.properties"));

		// se è in cluster utilizzo TerracottaJobStore, altrimenti di default è
		// impostato RAMJobStore
		if (managerconfig.getCluster()) {
			props.setProperty(StdSchedulerFactory.PROP_JOB_STORE_CLASS, "org.terracotta.quartz.TerracottaJobStore");
			props.setProperty("org.quartz.jobStore.tcConfigUrl", managerconfig.getUrlServer());
		}

		// se volessi utilizzare JDBCJobStore (JobStoreTx)
		/*
		 * props.setProperty("org.quartz.jobStore.class",
		 * "org.quartz.impl.jdbcjobstore.JobStoreTX");
		 * props.setProperty("org.quartz.jobStore.driverDelegateClass",
		 * "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		 * props.setProperty("org.quartz.jobStore.useProperties", "false");
		 * props.setProperty("org.quartz.jobStore.dataSource", "QRTZ_DS");
		 * props.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
		 * props.setProperty("org.quartz.jobStore.misfireThreshold", "60000");
		 * if(managerconfig.getCluster()){
		 * props.setProperty("org.quartz.jobStore.isClustered", "true"); } else
		 * { props.setProperty("org.quartz.jobStore.isClustered", "false"); }
		 */

		/*
		 * props.setProperty("org.quartz.dataSource.QRTZ_DS.driver",
		 * "com.mysql.jdbc.Driver");
		 * props.setProperty("org.quartz.dataSource.QRTZ_DS.URL",
		 * "jdbc:mysql://172.27.1.139:3306/job");
		 * props.setProperty("org.quartz.dataSource.QRTZ_DS.user", "root");
		 * props.setProperty("org.quartz.dataSource.QRTZ_DS.password",
		 * "password");
		 * props.setProperty("org.quartz.dataSource.QRTZ_DS.maxConnections",
		 * "50");
		 * props.setProperty("org.quartz.dataSource.QRTZ_DS.validationQuery",
		 * "select 1 from dual");
		 */
		if (managerconfig.getDataSource() != null) {
			props.setProperty("org.quartz.plugin.triggHistoryInDb.class",
					"it.eng.utility.jobmanager.quartz.plugin.history.LoggingTriggerHistoryInDbPlugin");
			props.setProperty("org.quartz.plugin.triggHistoryInDb.dataSource", "QRTZ_DS");
			props.setProperty("org.quartz.dataSource.QRTZ_DS.driver", managerconfig.getDataSource().getDriver());
			props.setProperty("org.quartz.dataSource.QRTZ_DS.URL", managerconfig.getDataSource().getURL());
			props.setProperty("org.quartz.dataSource.QRTZ_DS.user", managerconfig.getDataSource().getUser());
			props.setProperty("org.quartz.dataSource.QRTZ_DS.password", managerconfig.getDataSource().getPassword());
			props.setProperty("org.quartz.dataSource.QRTZ_DS.maxConnections",
					managerconfig.getDataSource().getMaxConnections());
			props.setProperty("org.quartz.dataSource.QRTZ_DS.validationQuery",
					managerconfig.getDataSource().getValidationQuery());
		}

		props.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		props.setProperty("org.quartz.threadPool.threadCount", config.getThreadpool().toString());
		props.setProperty("org.quartz.threadPool.threadPriority", "5");

		props.setProperty("org.quartz.scheduler.instanceName", jobname);
		props.setProperty("org.quartz.scheduler.instanceId", managerconfig.getNodename());
		props.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");
		// props.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
		// props.setProperty("org.quartz.threadPool.threadCount",config.getThreadpool().toString());
		// props.setProperty("org.quartz.threadPool.threadPriority","5");

		StdSchedulerFactory factory = new StdSchedulerFactory(props);
		scheduler = factory.getScheduler();

		JobKey key = new JobKey(jobname, job.type());

		if (managerconfig.getMaster()) {

			scheduler.clear();

			JobDataMap map = new JobDataMap();
			map.put(LoadJob.LOAD_CLASS_JOB, jobclass.getCanonicalName());
			map.put("JOB_ATTRIBUTES", config.getAttributes());
			map.put("JOB_ARGS", args);

			JobDetail jobdetail = newJob(LoadJob.class).usingJobData(map).withIdentity(key).build();

			JobStatusUtility.createMBean(jobname, job.type());

			Trigger trigger;
			if(managerconfig.isStartWithScheduler()) //Prelevo il valore del flag inserito nell'xml
			{
				//Avvia con la schedulazione impostata nel file di configurazione
				trigger = newTrigger().withIdentity("trigger_" + jobname, job.type())
					.withSchedule(CronScheduleBuilder.cronSchedule(config.getCronexpression())).build();
			}
			else
			{
				log.info("Starting the job ignoring scheduling");
				
				//Avvia senza una schedulazione 
				trigger = newTrigger().withIdentity("trigger_" + jobname, job.type())
					.startAt(futureDate(1, IntervalUnit.SECOND)).build();
			}			
			
			scheduler.scheduleJob(jobdetail, trigger);
		}

		if (log.isDebugEnabled()) {
			List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(key);
			for (Iterator<?> iterator = triggersOfJob.iterator(); iterator.hasNext();) {
				Trigger trigger = (Trigger) iterator.next();
				log.debug(String.format("job %s, trigger %s, next run %s", key, trigger, trigger.getNextFireTime()));
			}
		}
		
		scheduler.start();
	}

	public static void shutdown() throws SchedulerException {
		scheduler.pauseAll();
		scheduler.shutdown(true);
	}

	public static String fireJob(String jobName, String jobType) {
		String result = null;
		try {
			scheduler.triggerJob(new JobKey(jobName, jobType));
			result = String.format("Effettuata richiesta esecuzione %s [%s]", jobName, jobType);
		} catch (SchedulerException e) {
			result = e.toString();
		} catch (Exception e) {
			result = ExceptionUtils.getMessage(e);
		}
		return result;
	}

	public static String getNextFireTime(String jobName, String jobType) {
		String result = null;
		try {
			if (log.isDebugEnabled()) {
				for(String group: scheduler.getTriggerGroupNames()) {
				    // enumerate each trigger in group
					 GroupMatcher<TriggerKey> gm = GroupMatcher.triggerGroupEquals(group);
				    for(TriggerKey triggerKey : scheduler.getTriggerKeys(gm)) {
				        log.debug("Found trigger : " + scheduler.getTrigger(triggerKey));
				    }
				}
			}
			Trigger trigger = scheduler.getTrigger(new TriggerKey("trigger_" + jobName, jobType));
			Date nextFireTime = trigger.getNextFireTime();
			if (nextFireTime != null) result = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(nextFireTime);
		} catch (SchedulerException e) {
			result = e.toString();
		} catch (Exception e) {
			result = ExceptionUtils.getMessage(e);
		}
		return result;
	}
}