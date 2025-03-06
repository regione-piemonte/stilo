package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class PkBoxConfig extends ClientConfig {

	@XmlVariabile(nome = "wsSignConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsSignConfig;
	@XmlVariabile(nome = "wsCertConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsCertConfig;
	@XmlVariabile(nome = "wsOtpConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsOtpConfig;
	@XmlVariabile(nome = "user", tipo = TipoVariabile.SEMPLICE)
	private String user;
	@XmlVariabile(nome = "password", tipo = TipoVariabile.SEMPLICE)
	private String password;
	@XmlVariabile(nome = "alias", tipo = TipoVariabile.SEMPLICE)
	private String alias;
	@XmlVariabile(nome = "pin", tipo = TipoVariabile.SEMPLICE)
	private String pin;
	@XmlVariabile(nome = "otpPwd", tipo = TipoVariabile.SEMPLICE)
	private String otpPwd;
	
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

	

	public PadesConfig getPadesConfig() {
		return padesConfig;
	}

	public void setPadesConfig(PadesConfig padesConfig) {
		this.padesConfig = padesConfig;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public WSConfig getWsOtpConfig() {
		return wsOtpConfig;
	}

	public void setWsOtpConfig(WSConfig wsOtpConfig) {
		this.wsOtpConfig = wsOtpConfig;
	}

	public String getOtpPwd() {
		return otpPwd;
	}

	public void setOtpPwd(String otpPwd) {
		this.otpPwd = otpPwd;
	}



}
