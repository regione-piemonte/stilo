package it.eng.utility.jobmanager.quartz.bean;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Traccia lo stato di esecuzione di un job
 * 
 * @author massimo malvestio
 *
 */
public class JobExecutionStatus implements JobExecutionStatusMBean {

	/**
	 * Indica se il job è in esecuzione o meno
	 */
	protected boolean running;

	/**
	 * Traccia tutte le istanze del job ed il loro stato di esecuzione
	 */
	protected ConcurrentHashMap<Long, JobExecutionStatusEnum> threadExecutionStatus;

	/**
	 * Indica se è stato richiesto lo shutdown del job
	 */
	protected boolean scheduleShutdown;

	public JobExecutionStatus() {
		threadExecutionStatus = new ConcurrentHashMap<Long, JobExecutionStatusEnum>();
	}

	@Override
	public boolean getRunning() {
		return running;
	}

	@Override
	public void setRunning(boolean runningState) {
		running = runningState;
	}

	@Override
	public ConcurrentHashMap<Long, JobExecutionStatusEnum> getThreadExecutionStatus() {
		return threadExecutionStatus;
	}

	@Override
	public void setScheduleShutdown(boolean scheduleShutdown) {
		this.scheduleShutdown = scheduleShutdown;
	}

	@Override
	public boolean getScheduleShutdown() {
		return this.scheduleShutdown;
	}

}
