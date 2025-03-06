package it.eng.utility.jobmanager.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

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

	public static void initialize(String jobname, Class<?> jobclass, JobConfigBean config, JobManager managerconfig)
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

			JobDetail jobdetail = newJob(LoadJob.class).usingJobData(map).withIdentity(key).build();

			JobStatusUtility.createMBean(jobname);

			Trigger trigger = newTrigger().withIdentity("trigger_" + jobname, job.type())
					.withSchedule(CronScheduleBuilder.cronSchedule(config.getCronexpression())).build();
			scheduler.scheduleJob(jobdetail, trigger);
		}

		scheduler.start();
	}

	public static void shutdown() throws SchedulerException {
		scheduler.pauseAll();
		scheduler.shutdown(true);
	}

}