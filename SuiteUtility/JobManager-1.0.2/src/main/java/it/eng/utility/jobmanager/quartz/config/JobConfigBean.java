package it.eng.utility.jobmanager.quartz.config;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Bean di configurazione del singolo job
 * @author Rigo Michele
 *
 */
public class JobConfigBean implements Serializable {
	
	private static final long serialVersionUID = -6346237437062733538L;
	
	private String type;
	private Integer threadpool = 5;
	private String cronexpression;
	private HashMap<String,Object> attributes;

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}	
	
	public Integer getThreadpool() {
		return threadpool;
	}
	
	public void setThreadpool(Integer threadpool) {
		this.threadpool = threadpool;
	}
	
	public String getCronexpression() {
		return cronexpression;
	}
	
	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}

	public void setAttributes(HashMap<String,Object> attributes) {
		this.attributes = attributes;
	}

	public HashMap<String,Object> getAttributes() {
		return attributes;
	}
	
}