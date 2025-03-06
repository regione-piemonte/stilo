package it.eng.utility.jobmanager.quartz;

public class DataSource {
	
	private String driver;
	private String URL;
	private String user;
	private String password;
	private String maxConnections;
	private String validationQuery;
	
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	public String getURL() {
		return URL;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setMaxConnections(String maxConnections) {
		this.maxConnections = maxConnections;
	}
	
	public String getMaxConnections() {
		return maxConnections;
	}
	
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	
	public String getValidationQuery() {
		return validationQuery;
	}

}
