package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class InfoCertConfig extends ClientConfig {

	@XmlVariabile(nome = "restConfig", tipo = TipoVariabile.NESTED)
	private RestConfig restConfig;
	@XmlVariabile(nome = "alias", tipo = TipoVariabile.SEMPLICE)
	private String alias;
	@XmlVariabile(nome = "otp", tipo = TipoVariabile.SEMPLICE)
	private String otp;
	@XmlVariabile(nome = "pin", tipo = TipoVariabile.SEMPLICE)
	private String pin;
	@XmlVariabile(nome = "auto", tipo = TipoVariabile.SEMPLICE)
	private boolean auto;
	
	private PadesConfig padesConfig;
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public PadesConfig getPadesConfig() {
		return padesConfig;
	}
	public void setPadesConfig(PadesConfig padesConfig) {
		this.padesConfig = padesConfig;
	}
	public RestConfig getRestConfig() {
		return restConfig;
	}
	public void setRestConfig(RestConfig restConfig) {
		this.restConfig = restConfig;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	
}
