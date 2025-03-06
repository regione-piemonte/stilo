package it.eng.dm.sira.service.util;

import org.apache.axis.AxisProperties;

public class ProxySetter {

	private String host;

	private String port;

	private String user;

	private String password;

	public void initProxy() {
//		System.setProperty("http.proxyHost", host);
//		System.setProperty("http.proxyPort", port);
//		System.setProperty("http.proxyUser", user);
//		System.setProperty("http.proxyPassword", password);
		AxisProperties.setProperty("http.proxyHost", host);
		AxisProperties.setProperty("http.proxyPort", port);
		AxisProperties.setProperty("http.proxyUser", user);
		AxisProperties.setProperty("http.proxyPassword", password);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
