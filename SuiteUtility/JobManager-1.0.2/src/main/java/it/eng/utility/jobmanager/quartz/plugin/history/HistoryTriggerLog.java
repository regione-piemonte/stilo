package it.eng.utility.jobmanager.quartz.plugin.history;

import java.io.Serializable;
import java.util.Date;

public class HistoryTriggerLog implements Serializable{	
	
	private String triggerId;	
	private String triggerName;	
	private String triggerGroup;	
	private String triggerDescription;	
	private String jobName;	
	private String jobGroup;	
	private Date tsStart;	
	private Date tsEnd;		
	private String state;
	
	
	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}
	
	public String getTriggerId() {
		return triggerId;
	}
	
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	
	public String getTriggerName() {
		return triggerName;
	}
	
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	
	public String getTriggerGroup() {
		return triggerGroup;
	}
	
	public void setTriggerDescription(String triggerDescription) {
		this.triggerDescription = triggerDescription;
	}
	
	public String getTriggerDescription() {
		return triggerDescription;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getJobName() {
		return jobName;
	}
	
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	
	public String getJobGroup() {
		return jobGroup;
	}
	
	public void setTsStart(Date tsStart) {
		this.tsStart = tsStart;
	}
	
	public Date getTsStart() {
		return tsStart;
	}
	
	public void setTsEnd(Date tsEnd) {
		this.tsEnd = tsEnd;
	}
	
	public Date getTsEnd() {
		return tsEnd;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}

}