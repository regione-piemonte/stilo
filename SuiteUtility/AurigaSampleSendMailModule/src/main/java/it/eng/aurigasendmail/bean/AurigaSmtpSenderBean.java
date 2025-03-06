package it.eng.aurigasendmail.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class AurigaSmtpSenderBean implements Serializable {
	private static final long serialVersionUID = -4921626098793128088L;
	private String smtpEndpoint;
	private int smtpPort;
	private boolean auth;
	private boolean loginDisable;
	private String socketFactoryClass;
	private boolean socketFactoryFallback;
	private boolean sslEnabled;
	private String usernameAccount;
	private String usernamePassword;
	private boolean isPec;
	public String getSmtpEndpoint() {
		return smtpEndpoint;
	}
	public void setSmtpEndpoint(String smtpEndpoint) {
		this.smtpEndpoint = smtpEndpoint;
	}
	public int getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	public boolean isLoginDisable() {
		return loginDisable;
	}
	public void setLoginDisable(boolean loginDisable) {
		this.loginDisable = loginDisable;
	}
	public String getSocketFactoryClass() {
		return socketFactoryClass;
	}
	public void setSocketFactoryClass(String socketFactoryClass) {
		this.socketFactoryClass = socketFactoryClass;
	}
	public boolean getSocketFactoryFallback() {
		return socketFactoryFallback;
	}
	public void setSocketFactoryFallback(boolean socketFactoryFallback) {
		this.socketFactoryFallback = socketFactoryFallback;
	}
	public boolean isSslEnabled() {
		return sslEnabled;
	}
	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}
	public String getUsernameAccount() {
		return usernameAccount;
	}
	public void setUsernameAccount(String usernameAccount) {
		this.usernameAccount = usernameAccount;
	}
	public String getUsernamePassword() {
		return usernamePassword;
	}
	public void setUsernamePassword(String usernamePassword) {
		this.usernamePassword = usernamePassword;
	}
	public boolean getIsPec() {
		return isPec;
	}
	public void setIsPec(boolean isPec) {
		this.isPec = isPec;
	}

	
}
