package it.eng.utility.jobmanager.quartz.bean;

import java.io.Serializable;

/**
 * Definisce gli stati che può assumere un job
 * @author massimo malvestio
 *
 */
public enum JobExecutionStatusEnum implements Serializable {

	RUNNING,
	COMPLETED,
	STOPPED
	
}
