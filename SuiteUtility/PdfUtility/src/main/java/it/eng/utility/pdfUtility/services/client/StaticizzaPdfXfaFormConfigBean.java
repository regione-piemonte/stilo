package it.eng.utility.pdfUtility.services.client;

public class StaticizzaPdfXfaFormConfigBean {
	
	private String username;
	private String password;
	private String endpoint;

	private Integer timeout;
	
//	private String path;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return "StaticizzaPdfXfaFormConfigBean [username=" + username + ", password=" + password + ", endpoint="
				+ endpoint + ", timeout=" + timeout + "]";
	}

	
}
