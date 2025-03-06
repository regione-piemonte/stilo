package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class UanatacaConfig extends ClientConfig {

	@XmlVariabile(nome = "wsSignConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsSignConfig;
	@XmlVariabile(nome = "wsCertConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsCertConfig;
	@XmlVariabile(nome = "user", tipo = TipoVariabile.SEMPLICE)
	private String user;
	@XmlVariabile(nome = "password", tipo = TipoVariabile.SEMPLICE)
	private String password;
	@XmlVariabile(nome = "pin", tipo = TipoVariabile.SEMPLICE)
	private String pin;
	@XmlVariabile(nome = "otpPwd", tipo = TipoVariabile.SEMPLICE)
	private String otpPwd;
	@XmlVariabile(nome = "billingUsername", tipo = TipoVariabile.SEMPLICE)
	private String billingUsername;
	@XmlVariabile(nome = "billingPassword", tipo = TipoVariabile.SEMPLICE)
	private String billingPassword;
	@XmlVariabile(nome = "environment", tipo = TipoVariabile.SEMPLICE)
	private String environment;
	@XmlVariabile(nome = "identifier", tipo = TipoVariabile.SEMPLICE)
	private String identifier;
	@XmlVariabile(nome = "padesConfig", tipo = TipoVariabile.NESTED)
	private PadesConfig padesConfig;

	public WSConfig getWsSignConfig() {
		return wsSignConfig;
	}

	public void setWsSignConfig(WSConfig wsSignConfig) {
		this.wsSignConfig = wsSignConfig;
	}

	public WSConfig getWsCertConfig() {
		return wsCertConfig;
	}

	public void setWsCertConfig(WSConfig wsCertConfig) {
		this.wsCertConfig = wsCertConfig;
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

	public String getOtpPwd() {
		return otpPwd;
	}

	public void setOtpPwd(String otpPwd) {
		this.otpPwd = otpPwd;
	}
	
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	

	public String getBillingUsername() {
		return billingUsername;
	}

	public void setBillingUsername(String billingUsername) {
		this.billingUsername = billingUsername;
	}

	public String getBillingPassword() {
		return billingPassword;
	}

	public void setBillingPassword(String billingPassword) {
		this.billingPassword = billingPassword;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public PadesConfig getPadesConfig() {
		return padesConfig;
	}

	public void setPadesConfig(PadesConfig padesConfig) {
		this.padesConfig = padesConfig;
	}

}
