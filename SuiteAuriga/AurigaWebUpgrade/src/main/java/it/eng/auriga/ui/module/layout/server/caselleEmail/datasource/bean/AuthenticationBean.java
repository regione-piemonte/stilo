/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class AuthenticationBean {
	
	private String host;
	private String port;
    private String username;
	private String password;
	private String auth;
	private String starttls;
	private String sslEnable;
	private String smtpSocketFactoryFallback;
	private String smtpSocketFactoryClass;
	
	
	
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
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getStarttls() {
		return starttls;
	}
	public void setStarttls(String starttls) {
		this.starttls = starttls;
	}
	public String getSslEnable() {
		return sslEnable;
	}
	public void setSslEnable(String sslEnable) {
		this.sslEnable = sslEnable;
	}
	public String getSmtpSocketFactoryClass() {
		return smtpSocketFactoryClass;
	}
	public void setSmtpSocketFactoryClass(String smtpSocketFactoryClass) {
		this.smtpSocketFactoryClass = smtpSocketFactoryClass;
	}
	public String getSmtpSocketFactoryFallback() {
		return smtpSocketFactoryFallback;
	}
	public void setSmtpSocketFactoryFallback(String smtpSocketFactoryFallback) {
		this.smtpSocketFactoryFallback = smtpSocketFactoryFallback;
	}
	
	
	
}

