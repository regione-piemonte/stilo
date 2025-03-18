/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.concurrent.ConcurrentHashMap;

/**
 * Traccia lo stato di esecuzione di un job
 * @author massimo malvestio
 *
 */
public interface JobExecutionStatusMBean {

	public boolean getRunning();
	
	public void setRunning(boolean runningState);
	
	public ConcurrentHashMap<Long, JobExecutionStatusEnum> getThreadExecutionStatus();
	
	public void setScheduleShutdown(boolean scheduleShutdown);
	
	public boolean getScheduleShutdown(); 
}
