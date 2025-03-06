package it.eng.simplesendermail.service.bean;

import it.eng.simplesendermail.service.annotation.SmtpAnnotation;

public class SmtpSenderBean {
	@SmtpAnnotation(smptPropertyName="mail.smtp.host")
	private String smtpEndpoint;
	@SmtpAnnotation(smptPropertyName="mail.smtp.port")
	private int smtpPort;
	@SmtpAnnotation(smptPropertyName="mail.smtp.auth")
	private boolean auth;
	@SmtpAnnotation(smptPropertyName="mail.smtp.auth.login.disable")
	private boolean loginDisable;
	@SmtpAnnotation(smptPropertyName="mail.smtp.socketFactory.class")
	private String socketFactoryClass;
	@SmtpAnnotation(smptPropertyName="mail.smtp.socketFactory.fallback")
	private boolean socketFactoryFallback;
	@SmtpAnnotation(smptPropertyName="mail.smtp.ssl.enable")
	private boolean sslEnabled;
	@SmtpAnnotation(smptPropertyName="mail.username")
	private String usernameAccount;
	@SmtpAnnotation(smptPropertyName="mail.password")
	private String usernamePassword;
	@SmtpAnnotation(smptPropertyName="mail.account.ispec")
	private boolean isPec;
	@SmtpAnnotation(smptPropertyName="mail.smtp.starttls.enable")
	private boolean starttls;
	
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
	public boolean isStarttls() {
		return starttls;
	}
	public void setStarttls(boolean starttls) {
		this.starttls = starttls;
	}
	
}
