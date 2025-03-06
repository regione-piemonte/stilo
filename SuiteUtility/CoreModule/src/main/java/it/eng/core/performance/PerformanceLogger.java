package it.eng.core.performance;

import java.util.Date;

import org.apache.log4j.Logger;

public class PerformanceLogger {
	
	private static Logger mLogger = Logger.getLogger(PerformanceLogger.class);
		
	private String userInfo;
	private String name;
	private Date startDate;
	
	public PerformanceLogger(String userInfo, String name) {
		this.userInfo = userInfo;
		if(name != null && !"".equals(name)) {
			this.name = name;
			start();					
		}
	}
	
	public PerformanceLogger(String name) {
		this.userInfo = "";
		if(name != null && !"".equals(name)) {
			this.name = name;
			start();					
		}
	}
		
	public void start() {	
		if(name != null && !"".equals(name)) {
			this.startDate = new Date();		
		}
	}	
	
	public void end() {	
		if(name != null && !"".equals(name)) {
			long start = startDate.getTime();
			long end = new Date().getTime();
			long delay = end - start;
			if(delay > 5000) {
				mLogger.debug(">>>>> " + userInfo + " " + name + " executed in " + (delay) + " ms");
			} else {
				mLogger.debug(userInfo + " " + name + " executed in " + (delay) + " ms");
			}
		}
	}
	
	public void error(String message) {	
		mLogger.error("ERROR in " + userInfo + " " + name + ": " + message);
	}
	
}
